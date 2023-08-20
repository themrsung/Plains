package civitas.celestis;

import civitas.celestis.math.Vector;
import civitas.celestis.util.Tuple;

public class GroupTesting {
    public static void main(String[] args) {
        System.out.println(Vector.of(1, 2, 3));
        System.out.println(Vector.copyOf(Tuple.of(1d, 2d, 3d)).getClass());
    }
}
