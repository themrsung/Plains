package civitas.celestis.gpu;

import civitas.celestis.exception.gpu.GraphicsException;
import jakarta.annotation.Nonnull;
import org.jocl.*;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface which handles the back-end operations of OpenCL.
 * This API is dependent on JOCL. Unlike other classes of the Plains API,
 * this class does not statically initialize itself. Thus, {@link #initialize()}
 * must be called before any other method is to be invoked.
 */
public final class GPU {
    //
    // Initialization
    //

    /*
     * As is clearly visible in this APIs source code, sending data to the GPU and
     * retrieving the calculated results has significant overhead, and it may not be worth
     * the effort for simple operations. Users must be aware of the overhead involved
     * in managing GPU resources and transferring data before delegating work to the GPU.
     * To put it simply, trivial tasks are better suited for CPUs.
     */

    /**
     * Initializes this interface. This must be called before any other method.
     */
    public synchronized static void initialize() {
        /*
         * The order of resource acquisition is important, as they must be released
         * in reverse order in release().
         */

        // Retrieve platform ID
        final cl_platform_id[] platforms = new cl_platform_id[1];
        CL.clGetPlatformIDs(1, platforms, null);
        final cl_platform_id platform = platforms[0];

        // Retrieve device ID
        final cl_device_id[] devices = new cl_device_id[1];
        CL.clGetDeviceIDs(platform, CL.CL_DEVICE_TYPE_GPU, 1, devices, null);
        device = devices[0];

        // Create context and command queue
        context = CL.clCreateContext(null, 1, new cl_device_id[]{device},
                null, null, null);
        commandQueue = CL.clCreateCommandQueueWithProperties(context, device, null, null);

        // Initialize kernel and program list
        kernels.clear();
        programs.clear();

        // Mark state as initialized
        initialized = true;
    }

    //
    // Release
    //

    /**
     * Releases all resources this interface is holding. This disposes every object reference,
     * resetting the state of this interface. Re-initialization is required in order to use the interface again.
     *
     * @throws GraphicsException When the {@link GPU} interface has not been initialized
     */
    public synchronized static void dispose() {
        requireInitialized();

        /*
         * Resources must be released in the reverse order of acquisition.
         */

        // Release kernels and programs
        kernels.forEach(CL::clReleaseKernel);
        programs.forEach(CL::clReleaseProgram);

        kernels.clear();
        programs.clear();

        // Release references
        CL.clReleaseCommandQueue(commandQueue);
        CL.clReleaseContext(context);
        CL.clReleaseDevice(device);

        device = null;
        context = null;
        commandQueue = null;

        // Mark state as uninitialized
        initialized = false;
    }

    //
    // Kernel Acquisition
    //

    /**
     * Creates a new kernel, then returns a reference to the created kernel.
     *
     * @param source     The source code of the kernel program
     * @param name       The qualified name of the kernel's method
     * @param paramCount The number of parameters the kernel takes (including output)
     * @return A reference object to the created kernel
     * @throws GraphicsException When the {@link GPU} interface has not been initialized
     * @see KernelReference
     */
    @Nonnull
    public static KernelReference createKernel(@Nonnull String source, @Nonnull String name, int paramCount) {
        requireInitialized();

        final cl_program program = CL.clCreateProgramWithSource(context, 1, new String[]{source}, null, null);
        CL.clBuildProgram(program, 0, null, null, null, null);
        final cl_kernel kernel = CL.clCreateKernel(program, name, null);

        programs.add(program);
        kernels.add(kernel);

        return new KernelReference(program, kernel, paramCount);
    }

    /**
     * Creates a new kernel, then returns a reference to the created kernel.
     *
     * @param source The source of the kernel
     * @return A reference object to the created kernel
     * @throws GraphicsException When the {@link GPU} interface has not been initialized
     * @see KernelReference
     * @see KernelSource
     * @see Kernels#ADD_DOUBLES
     * @see Kernels#SUBTRACT_DOUBLES
     * @see Kernels#MULTIPLY_DOUBLES
     * @see Kernels#DIVIDE_DOUBLES
     * @see Kernels#SQRT_DOUBLE
     */
    @Nonnull
    public static KernelReference createKernel(@Nonnull KernelSource source) {
        return createKernel(source.source, source.name, source.paramCount);
    }

    //
    // Kernel Lifecycle
    //

    /*
     * These methods are designed to be called internally by the KernelReference class,
     * and thus are package-private.
     */

    /**
     * Creates a read-only {@code double[]} buffer.
     *
     * @param array The array to reference
     * @return The created buffer object
     * @throws GraphicsException When the {@link GPU} interface has not been initialized
     */
    @Nonnull
    static cl_mem createReadOnlyBuffer(@Nonnull double[] array) {
        requireInitialized();
        return CL.clCreateBuffer(context, CL.CL_MEM_READ_ONLY | CL.CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_double * (long) array.length, Pointer.to(array), null);
    }

    /**
     * Creates a write-only {@code double[]} buffer.
     *
     * @param array The array to reference
     * @return The created buffer object
     * @throws GraphicsException When the {@link GPU} interface has not been initialized
     */
    @Nonnull
    static cl_mem createWriteOnlyBuffer(@Nonnull double[] array) {
        requireInitialized();
        return CL.clCreateBuffer(context, CL.CL_MEM_WRITE_ONLY,
                Sizeof.cl_double * (long) array.length, null, null);
    }

    /**
     * Sets a kernel's argument.
     *
     * @param k The kernel of which to set the argument of
     * @param i The index of the argument to set
     * @param s The size of the memory buffer
     * @param p The pointer to the value to set to
     * @throws GraphicsException When the {@link GPU} interface has not been initialized
     */
    static void setKernelArgument(@Nonnull cl_kernel k, int i, long s, @Nonnull Pointer p) {
        requireInitialized();
        CL.clSetKernelArg(k, i, s, p);
    }

    /**
     * Executes the provided kernel {@code k}.
     *
     * @param k        The kernel of which to execute
     * @param workSize The work size of the kernel
     */
    static void executeKernel(@Nonnull cl_kernel k, long workSize) {
        requireInitialized();
        CL.clEnqueueNDRangeKernel(commandQueue, k, 1, null, new long[]{workSize},
                null, 0, null, null);
    }

    /**
     * Reads a memory buffer object.
     *
     * @param m The memory buffer of which to read
     * @param s The size of the memory buffer
     * @param p The pointer of which to put the result in
     */
    static void readBuffer(@Nonnull cl_mem m, long s, @Nonnull Pointer p) {
        requireInitialized();
        CL.clEnqueueReadBuffer(commandQueue, m, CL.CL_TRUE, 0, s, p, 0, null, null);
    }

    /**
     * Releases the resources of a kernel.
     *
     * @param p The program of which to release
     * @param k The kernel of which to release
     */
    static void releaseKernelResources(@Nonnull cl_program p, @Nonnull cl_kernel k) {
        requireInitialized();

        // Release resources
        CL.clReleaseKernel(k);
        CL.clReleaseProgram(p);

        // Remove references
        kernels.remove(k);
        programs.remove(p);
    }

    //
    // References
    //

    /*
     * JOCL API object references. These must be stored in order to be
     * properly released after usage.
     */

    private static cl_device_id device;
    private static cl_context context;
    private static cl_command_queue commandQueue;

    /*
     * Kernels & Programs
     */

    private static final List<cl_kernel> kernels = new ArrayList<>();
    private static final List<cl_program> programs = new ArrayList<>();

    //
    // Miscellaneous
    //

    /**
     * Requires that this interface is initialized. If this interface has not been
     * initialized, this will throw a {@link GraphicsException}.
     */
    private synchronized static void requireInitialized() {
        if (initialized) return;
        throw new GraphicsException("The GPU interface has not been initialized.");
    }

    /**
     * Whether this interface is initialized.
     */
    private volatile static boolean initialized = false;

    /**
     * Private constructor to ensure this class is not instantiated.
     * It is important to prevent instantiation of this class to maintain
     * proper usage of its {@code private static} variables.
     *
     * @throws Exception Always throws an exception
     */
    private GPU() throws Exception {
        throw new Exception("This is a static interface class, and thus cannot be instantiated.");
    }
}
