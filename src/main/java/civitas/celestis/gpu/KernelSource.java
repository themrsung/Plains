package civitas.celestis.gpu;

import jakarta.annotation.Nonnull;

/**
 * A source object which can be used as a parameter of {@link GPU#createKernel(KernelSource)}.
 * {@link Kernels Predefined kernels} are packaged in the form of kernel source objects.
 * Source objects are immutable, and can be safely shared across multiple threads.
 */
public class KernelSource {
    //
    // Constructors
    //

    /**
     * Creates a new kernel source.
     *
     * @param source     The source code to use
     * @param name       The qualified method name of the kernel
     * @param paramCount The parameter count of the kernel
     */
    public KernelSource(@Nonnull String source, @Nonnull String name, int paramCount) {
        this.source = source;
        this.name = name;
        this.paramCount = paramCount;
    }

    /**
     * Creates a new kernel source.
     *
     * @param ks The source object of which to copy values from
     */
    protected KernelSource(@Nonnull KernelSource ks) {
        this.source = ks.source;
        this.name = ks.name;
        this.paramCount = ks.paramCount;
    }

    //
    // Variables
    //

    /**
     * The source code of this kernel.
     */
    @Nonnull
    protected final String source;

    /**
     * The name of this kernel.
     */
    @Nonnull
    protected final String name;

    /**
     * The parameter count of this kernel.
     */
    protected final int paramCount;
}
