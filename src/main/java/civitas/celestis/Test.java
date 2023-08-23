package civitas.celestis;


import civitas.celestis.util.grid.DynamicGrid;
import civitas.celestis.util.grid.HashGrid;

public class Test {
    public static void main(String[] args) {
        final DynamicGrid<String> grid = new HashGrid<>();
        grid.setSize(10, 10);
        grid.set(2, 3, "Hello world");
        grid.setSize(4, 7);

        grid.setSize(0, 0);
        grid.setSize(10, 10);

        System.out.println(grid.get(2, 3));
    }
}
