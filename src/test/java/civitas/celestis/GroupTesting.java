package civitas.celestis;


import civitas.celestis.math.Numbers;
import civitas.celestis.math.complex.Quaternion;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.grid.ArrayGrid;
import civitas.celestis.util.grid.Grid;

import java.util.function.Function;

public class GroupTesting {
    public static void main(String[] args) {
        final Grid<Vector3> vectors = new ArrayGrid<>(3, 3);
        vectors.apply(v -> Numbers.randomVector3());
        vectors.apply(v -> v.multiply(100));

        final Grid<Quaternion> quaternions = new ArrayGrid<>(3, 3);
        quaternions.apply(q -> Numbers.randomQuaternion());

        final Grid<Vector3> rotated = vectors.merge(quaternions, Vector3::rotate);
        System.out.println(vectors);
        System.out.println(rotated);

        System.out.println(vectors.map(Vector3::norm));
        System.out.println(rotated.map(Vector3::norm));

    }
}
