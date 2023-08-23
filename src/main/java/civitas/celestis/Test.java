package civitas.celestis;


import civitas.celestis.util.grid.AtomicGrid;
import civitas.celestis.util.grid.Grid;

public class Test {
    public static void main(String[] args) {
        final Grid<Double> g1 = Grid.of(new Double[][] {
                {1d, 2d},
                {3d, 4d}
        });

        final Grid<Double> g2 = Grid.ofDouble(new double[][] {
                {1, 2},
                {3, 4}
        });

        final Grid<Double> g3 = Grid.atomicCopyOf(g1);

        System.out.println(g1);
        System.out.println(g2);
        System.out.println(g3);
        System.out.println(g1.equals(g2));
        System.out.println(g2.equals(g3));
    }
}
