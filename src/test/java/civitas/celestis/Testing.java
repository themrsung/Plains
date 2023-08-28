package civitas.celestis;

import civitas.celestis.math.matrix.Matrix;
import civitas.celestis.util.grid.ArrayGrid;
import civitas.celestis.util.grid.Grid;

public class Testing {
    public static void main(String[] args) {
        final Grid<String> g1 = new ArrayGrid<>(10, 10);
        g1.fill("Hello world");

        System.out.println(g1);

        final Matrix m = new Matrix(10, 10);
        m.fill(Math.PI);
        System.out.println(m);


    }
}
