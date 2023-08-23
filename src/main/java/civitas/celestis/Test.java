package civitas.celestis;


import civitas.celestis.util.grid.DynamicGrid;
import civitas.celestis.util.grid.HashGrid;

public class Test {
    public static void main(String[] args) {
        final DynamicGrid<String> grid = new HashGrid<>(10, 10);
        grid.fill("Hello World");

        System.out.println(grid);

        grid.clear();
        System.out.println(grid);
    }
}
