package civitas.celestis;

import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.grid.*;
import civitas.celestis.util.tuple.ArrayTuple;
import civitas.celestis.util.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class Testing {
    public static void main(String[] args) {
        final Grid<Double> g1 = new ArrayGrid<>(10, 10);
        g1.fill(Math.PI);

        final DoubleGrid g2 = new DoubleArrayGrid(10, 10);
        g2.fill(Math.PI);

        System.out.println(g1.hashCode());
        System.out.println(g2.hashCode());
    }
}
