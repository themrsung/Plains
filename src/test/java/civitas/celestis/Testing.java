package civitas.celestis;

import civitas.celestis.util.array.DoubleArray;

public class Testing {
    public static void main(String[] args) {
        final DoubleArray array = DoubleArray.of(30, 29, 28, 27, 26, 25, 24, 23, 23, 2, 2, 2);
        array.subArray(3, 6).sort();
        System.out.println(array);

    }
}
