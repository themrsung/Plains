package civitas.celestis;


import civitas.celestis.math.Vector3;
import civitas.celestis.util.FastArray;
import civitas.celestis.util.SafeArray;

import java.util.Comparator;

public class GroupTesting {
    public static void main(String[] args) {
        final SafeArray<Vector3> test = new FastArray<>(10);
        test.apply(v -> new Vector3(Math.random(), Math.random(), Math.random()));

        System.out.println(test);

        test.shuffle();
        System.out.println(test);

        test.sort(Comparator.comparing(Vector3::norm2));
        System.out.println(test);
    }
}
