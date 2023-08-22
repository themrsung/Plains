package civitas.celestis;


import civitas.celestis.util.array.AtomicArray;

import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        final AtomicArray<Double> doubles = AtomicArray.of(1d, 2d, 3d, 4d, 5d, 6d);
        System.out.println(doubles);

        doubles.shuffle();
        System.out.println(doubles);

        doubles.sort(Comparator.reverseOrder());
        System.out.println(doubles);
    }
}
