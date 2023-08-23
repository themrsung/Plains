package civitas.celestis;


import civitas.celestis.util.grid.AtomicGrid;
import civitas.celestis.util.grid.Grid;

public class Test {
    public static void main(String[] args) {
        final Grid<String> g = new AtomicGrid<>(3, 3);
        g.fill("Hello world");
        System.out.println(Grid.copyOf(g));
    }
}
