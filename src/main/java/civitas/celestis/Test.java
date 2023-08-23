package civitas.celestis;


import civitas.celestis.math.matrix.Matrix;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.grid.DynamicGrid;
import civitas.celestis.util.grid.HashGrid;

public class Test {
    public static void main(String[] args) {
        final Matrix m = Matrix.newIdentity(3);
        final Vector3 v = new Vector3(23, -33, 10);
        final Vector3 p = m.multiply(v);

        System.out.println(p);
    }
}
