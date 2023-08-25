package civitas.celestis;

import civitas.celestis.gpu.GPU;
import civitas.celestis.gpu.KernelReference;
import civitas.celestis.gpu.Kernels;
import org.jocl.cl_mem;

public class GPUTesting {
    public static void main(String[] args) {
        GPU.initialize();

        final double[] result = new double[1];

        GPU.createKernel(Kernels.DIVIDE_DOUBLES)
                .setInput(0, 21)
                .setInput(1, 23)
                .setOutput(2, new double[1])
                .execute(1)
                .readOutput(2, result)
                .dispose();

        System.out.println(result[0]);

        GPU.createKernel(Kernels.SQRT_DOUBLE)
                        .setInput(0, 10)
                        .setOutput(1, new double[1])
                        .execute(1)
                        .readOutput(1, result)
                        .dispose();

        System.out.println(result[0]);

        GPU.dispose();
    }
}
