package civitas.celestis;

import civitas.celestis.util.ArrayGrid;
import civitas.celestis.util.Grid;

public class GroupTesting {
    public static void main(String[] args) {
        final Grid<String> grid = new ArrayGrid<>(10, 10);
        grid.fill("Hello");

        System.out.println(grid.subGrid(3, 3, 6, 6));
    }
}
