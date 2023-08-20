package civitas.celestis;


import civitas.celestis.math.Matrix;
import civitas.celestis.math.Quaternion;
import civitas.celestis.math.Vector3;

public class GroupTesting {
    public static void main(String[] args) {
        final Quaternion quat = new Quaternion(13302, -32493, 23923, -20239).normalize();
        final Matrix matrix = quat.matrix();

        final Vector3 test = new Vector3(23, -13, 99);

        final Vector3 v1 = test.rotate(quat);
        final Vector3 v2 = (Vector3) matrix.multiply(test);

        System.out.println(v1);
        System.out.println(v2);

        final Matrix m2 = matrix.resize(6, 3);
        m2.setRange(3, 0, 6, 3, matrix.transpose());
        System.out.println(m2.multiply(test));

    }
}
