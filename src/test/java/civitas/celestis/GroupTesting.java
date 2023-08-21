package civitas.celestis;


import civitas.celestis.util.grid.Grid;
import civitas.celestis.util.tensoid.ArrayTensoid;
import civitas.celestis.util.tensoid.Tensoid;

public class GroupTesting {
    public static void main(String[] args) {
        final Tensoid<Grid<String>> something = new ArrayTensoid<>(10, 10, 10);
        something.fill(Grid.of(new String[][] {
                {"Hello", "world"},
                {"foo", "bar"}
        }));


    }
}
