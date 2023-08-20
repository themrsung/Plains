package civitas.celestis;

import civitas.celestis.math.Quaternion;
import civitas.celestis.math.Vector3;
import civitas.celestis.math.VectorMatrix;
import civitas.celestis.util.Pair;
import civitas.celestis.util.Tuple;
import civitas.celestis.util.VectorGrid;

public class GroupTesting {
    public static void main(String[] args) {
        final VectorGrid<Vector3> grid = new VectorMatrix<>(10, 10);

        grid.fill(Vector3.ZERO);
        grid.apply(v -> v.add(new Vector3(Math.random(), Math.random(), Math.random())).normalize());
        grid.apply(v -> v.rotate(
                new Quaternion(
                    new Quaternion(Math.random(), Math.random(), Math.random(), Math.random()).normalize()
                )
        ));

        System.out.println(grid);
    }
}
