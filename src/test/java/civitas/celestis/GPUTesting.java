package civitas.celestis;

import civitas.celestis.gpu.GPU;
import civitas.celestis.gpu.GPUMath;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.concurrent.Threads;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class GPUTesting {
    public static void main(String[] args) {

        final Vector3 v = new Vector3(1390, 2320, 232);

        final int n = 100;


        long t1, t2;

        t1 = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            double value = 1 + 2;
        }

        t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);

        GPU.initialize();
        final GPUMath math = new GPUMath();

        t1 = System.currentTimeMillis();

        for (int i = 0; i < n; i++) {
            math.add(1, 2);
        }

        t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);

        GPU.dispose();
    }
}
