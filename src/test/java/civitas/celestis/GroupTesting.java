package civitas.celestis;

import civitas.celestis.math.vector.ArrayVector;
import civitas.celestis.math.vector.Vector4;
import civitas.celestis.util.group.Group;
import civitas.celestis.util.group.Tuple;

public class GroupTesting {
    public static void main(String[] args) {
        final Group<Double> group = Tuple.of(1d, 2d, 3d, 4d);
        final ArrayVector v = new ArrayVector(group);

        System.out.println(ArrayVector.parseVector(v.toString()));
    }
}
