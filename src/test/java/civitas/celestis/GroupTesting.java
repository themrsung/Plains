package civitas.celestis;

import civitas.celestis.math.vector.Float3;
import civitas.celestis.math.vector.Vector3;

public class GroupTesting {
    public static void main(String[] args) {
        final Vector3 v1 = new Vector3(1, Double.NaN, 2);
        final Vector3 v2 = new Vector3(23, 13, -20);
        final Vector3 v3 = new Vector3(11, -999, 1000);

        System.out.println(v1.add(v2).add(v3).requireFinite());
    }
}
