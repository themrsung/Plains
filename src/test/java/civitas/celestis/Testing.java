package civitas.celestis;

import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.tuple.BaseTuple;
import civitas.celestis.util.tuple.DoubleTuple;
import civitas.celestis.util.tuple.Tuple;

public class Testing {
    public static void main(String[] args) {
        final Vector3 vector = new Vector3(1, 2, 3);
        final DoubleTuple tuple = vector;
        final BaseTuple<Double> base1 = tuple;

        final Tuple<Double> boxed = tuple.boxed();
        final BaseTuple<Double> base2 = boxed;

        System.out.println(BaseTuple.equals(base1, base2));
    }
}
