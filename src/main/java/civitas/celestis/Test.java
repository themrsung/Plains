package civitas.celestis;


import civitas.celestis.math.complex.Quaternion;
import civitas.celestis.math.matrix.Matrix;

public class Test {
    public static void main(String[] args) {
        final Matrix identity = new Matrix(4, 4);

        identity.fill(0d);

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (r != c) continue;
                identity.set(r, c, 1d);
            }
        }

        final var q = identity.multiply(Quaternion.IDENTITY);
        System.out.println(q);
    }
}
