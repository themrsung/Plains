package civitas.celestis;

import civitas.celestis.math.complex.Quaternions;
import civitas.celestis.math.matrix.Matrix;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.IO;

public class Testing {
    public static void main(String[] args) {
        final Vector3 v = new Vector3(23, 40, -20);
        final Matrix m = Quaternions.matrix(Quaternions.random());

        final Vector3 p = v.rotate(Quaternions.random());

        System.out.println(p);
    }
}
