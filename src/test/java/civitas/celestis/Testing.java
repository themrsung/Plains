package civitas.celestis;

import civitas.celestis.math.matrix.Matrix;
import civitas.celestis.util.grid.ArrayGrid;
import civitas.celestis.util.grid.Grid;

public class Testing {
    public static void main(String[] args) {
        final Grid<Double> doubleGrid = new ArrayGrid<>(10, 10);
        doubleGrid.fill(10d);

        final Matrix m = new Matrix(10, 10);
        m.fill(10);
    }
}
