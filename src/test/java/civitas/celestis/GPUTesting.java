package civitas.celestis;

import civitas.celestis.gpu.GPU;
import civitas.celestis.gpu.KernelReference;
import civitas.celestis.gpu.Kernels;
import civitas.celestis.math.vector.Vector3;

public class GPUTesting {
    public static void main(String[] args) {
        GPU.initialize();

        final Vector3 v = new Vector3(12, 310, 200);
        final Vector3 p = v.normalize();
        final Vector3 q;

        final double[] result = new double[3];

        GPU.createKernel(Kernels.NORMALIZE_VECTOR_3)
                        .setInput(0, v.array())
                        .setOutput(1, new double[3])
                        .execute(3)
                        .readOutput(1, result)
                        .dispose();

        q = new Vector3(result);


        System.out.println(p);
        System.out.println(q);

        final KernelReference kernel = GPU.createKernel(Kernels.NORMALIZE_VECTOR_3)
                .setInput(0, v.array())
                .setOutput(1, new double[3]);

        long t1, t2;
        t1 = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            kernel.execute(3);
        }
        t2 = System.currentTimeMillis();

        System.out.println(t2 - t1);

        kernel.dispose();
        GPU.dispose();


        t1 = System.currentTimeMillis();

        for (int i = 0; i < 100000; i++) {
            v.normalize();
        }

        t2 = System.currentTimeMillis();


        System.out.println(t2 - t1);
    }
}
