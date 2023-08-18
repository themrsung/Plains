package civitas.celestis;

import civitas.celestis.math.matrix.Matrix;

import java.lang.ref.Reference;
import java.util.concurrent.atomic.AtomicReference;

public class GroupTesting {
    public static void main(String[] args) {
        final Matrix m = new Matrix(10, 10);
        final AtomicReference<Matrix> atomic = new AtomicReference<>(m);

        atomic.get().fill(3d);

    }
}
