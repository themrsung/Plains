package civitas.celestis.demo;

import civitas.celestis.gpu.GPU;
import civitas.celestis.gpu.KernelReference;
import civitas.celestis.gpu.Kernels;
import jakarta.annotation.Nonnull;

/**
 * This is a demo on how to offload work to the GPU using the Plains {@link GPU} API.
 * Note that this is a very trivial example, and should be done by the CPU in practice.
 *
 * @see GPU
 * @see KernelReference
 */
public class GPUDemo {
    /**
     * This program will perform {@code 1 + 2}, then print the result.
     *
     * @param args Ignored
     * @see Kernels#ADD_DOUBLES
     */
    public static void main(@Nonnull String[] args) {
        // Initialize the GPU interface
        GPU.initialize();

        final double a = 1;
        final double b = 2;
        final double[] result = new double[1];

        // Obtain a kernel instance
        final KernelReference kernel = GPU.createKernel(Kernels.ADD_DOUBLES);

        // Kernel setup
        kernel.setInput(0, a);
        kernel.setInput(1, b);
        kernel.setOutput(2, result);

        // Execute kernel
        kernel.execute(1);

        // Read results
        kernel.readOutput(2, result);

        // Print result
        System.out.println(result[0]);

        // Dispose resources
        kernel.dispose();
        GPU.dispose();
    }

    /**
     * In practice, GPU kernel operations should be chained to improve code readability.
     * This is the exact same program as {@link #main(String[])}.
     */
    public static void chained() {
        GPU.initialize();

        final double[] result = new double[1];
        GPU.createKernel(Kernels.ADD_DOUBLES)
                .setInput(0, 1)
                .setInput(1, 2)
                .setOutput(2, result)
                .execute(1)
                .readOutput(2, result)
                .dispose();

        System.out.println(result[0]);

        GPU.dispose();
    }
}
