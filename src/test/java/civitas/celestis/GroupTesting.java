package civitas.celestis;

import civitas.celestis.util.ArrayGrid;
import civitas.celestis.util.FastArray;
import civitas.celestis.util.Grid;

public class GroupTesting {
    public static void main(String[] args) {
        final Grid<Double> g1 = new ArrayGrid<>(5, 5);
        final Grid<Double> g2 = new ArrayGrid<>(5, 5);

        g1.apply(x -> Math.random());
        g2.fill(10d);

        final Grid<Double> g3 = g1.merge(g2, (x, y) -> (double) x * y);

        System.out.println(g3);
        System.out.println(g3.transpose());
    }
}
