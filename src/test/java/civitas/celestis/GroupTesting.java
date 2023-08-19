package civitas.celestis;

import civitas.celestis.util.ArrayGrid;
import civitas.celestis.util.Grid;

public class GroupTesting {
    public static void main(String[] args) {
        final Double[][] array = new Double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = 3d;
            }
        }

        final Grid<Double> test = Grid.of(array);

        test.apply(c -> Math.random());
        System.out.println(test);

        final Grid<Double> test2 = new ArrayGrid<>(test);

        System.out.println(test2);

        final Grid<String> test3 = test2.map(x -> " " + x);
        System.out.println(test3);
    }
}
