package civitas.celestis;

import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.tuple.BaseTuple;
import civitas.celestis.util.tuple.DoubleTuple;
import civitas.celestis.util.tuple.Tuple;

public class Testing {
    public static void main(String[] args) {
        final DoubleTuple doubles = new Vector3(1, 2, 3);
        final Tuple<Double> boxed = Tuple.of(3d, 2d, 1d);

        System.out.println(BaseTuple.equalsIgnoreOrder(doubles, boxed));
    }
}
