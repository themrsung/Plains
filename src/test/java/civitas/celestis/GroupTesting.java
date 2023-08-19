package civitas.celestis;

import civitas.celestis.math.Vector3;

public class GroupTesting {
    public static void main(String[] args) {
        final Vector3 v = new Vector3(1, Double.POSITIVE_INFINITY, 3);

        System.out.println(v.isInfinite());
    }
}
