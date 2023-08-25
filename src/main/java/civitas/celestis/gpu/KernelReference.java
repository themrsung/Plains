package civitas.celestis.gpu;

import jakarta.annotation.Nonnull;
import org.jocl.*;

/**
 * A reference object to a GPU kernel. This object is handed out to external
 * classes by the {@link GPU} interface in order to provide an object-oriented
 * interface for handling GPU kernel operations.
 */
public final class KernelReference {
    /*
     * It is important that all non-public methods are package-private.
     * Due to its usage as an interface between external classes and the GPU
     * class, public members are listed at the top.
     */

    //
    // Public Methods
    //

    /**
     * Sets the {@code i}th parameter as an input.
     *
     * @param i     The index of the parameter to set
     * @param value The value to use as input
     * @return A reference to itself ({@code this})
     */
    @Nonnull
    public KernelReference setInput(int i, double value) {
        return setInput(i, new double[]{value});
    }

    /**
     * Sets the {@code i}th parameter as an input.
     *
     * @param i     The index of the parameter to set
     * @param value The value to use as input
     * @return A reference to itself ({@code this})
     */
    @Nonnull
    public KernelReference setInput(int i, @Nonnull double[] value) {
        final cl_mem mem = GPU.createReadOnlyBuffer(value);
        memoryBuffers[i] = mem;
        GPU.setKernelArgument(kernel, i, Sizeof.cl_mem, Pointer.to(mem));
        return this;
    }

    /**
     * Sets the {@code i}th parameter as an output.
     *
     * @param i   The index of the parameter to set
     * @param out The array to use as output
     * @return A reference to itself ({@code this})
     */
    @Nonnull
    public KernelReference setOutput(int i, @Nonnull double[] out) {
        final cl_mem mem = GPU.createWriteOnlyBuffer(out);
        memoryBuffers[i] = mem;
        GPU.setKernelArgument(kernel, i, Sizeof.cl_mem, Pointer.to(mem));
        return this;
    }

    /**
     * Executes this kernel. The work size is the number of elements the
     * input and output arrays contain.
     *
     * @param workSize The work size of this kernel (the input and output arrays' length)
     * @return A reference to itself ({@code this})
     */
    @Nonnull
    public KernelReference execute(long workSize) {
        GPU.executeKernel(kernel, workSize);
        return this;
    }

    /**
     * Reads the output of this kernel to an array of {@code double}s.
     *
     * @param i   The index of the argument to read
     * @param out The output array
     */
    @Nonnull
    public KernelReference readOutput(int i, @Nonnull double[] out) {
        GPU.readBuffer(memoryBuffers[i], Sizeof.cl_mem, Pointer.to(out));
        return this;
    }

    /**
     * Disposes this reference. The reference to the kernel will be lost.
     */
    public void dispose() {
        for (int i = (memoryBuffers.length - 1); i >= 0; i--) {
            CL.clReleaseMemObject(memoryBuffers[i]);
        }

        GPU.releaseKernelResources(program, kernel);

        kernel = null;
        program = null;
    }

    //
    // Constructors
    //

    /**
     * Creates a new reference to a kernel.
     *
     * @param program  The program of which to reference
     * @param kernel   The kernel of which to reference
     * @param argCount The number of arguments this kernel takes
     */
    KernelReference(@Nonnull cl_program program, @Nonnull cl_kernel kernel, int argCount) {
        this.program = program;
        this.kernel = kernel;
        this.memoryBuffers = new cl_mem[argCount];
    }

    //
    // Variables
    //

    /**
     * The kernel to reference.
     */
    cl_kernel kernel;

    /**
     * The program to reference.
     */
    cl_program program;

    /**
     * The list of memory buffers.
     */
    final cl_mem[] memoryBuffers;
}
