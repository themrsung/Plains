package civitas.celestis;


import civitas.celestis.util.SafeArray;

import java.util.Comparator;

public class GroupTesting {
    public static void main(String[] args) {
        final SafeArray<Double> boxed = SafeArray.of(1d, 2d, 3d, 4d);
        final SafeArray<Double> prim = SafeArray.ofDouble(1, 2, 3, 4);

        boxed.shuffle();
        prim.shuffle();

        System.out.println(boxed);
        System.out.println(prim);

        boxed.sort();
        prim.sort(Comparator.reverseOrder());

        System.out.println(boxed);
        System.out.println(prim);
    }
}
