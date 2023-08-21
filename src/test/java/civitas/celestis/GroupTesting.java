package civitas.celestis;


import civitas.celestis.math.Vector3;
import civitas.celestis.util.FastArray;
import civitas.celestis.util.SafeArray;

import java.util.Comparator;

public class GroupTesting {
    public static void main(String[] args) {
        final SafeArray<String> source = SafeArray.of("Hello", "world");
        final SafeArray<Object> unsafe = source.cast(Object.class);
        final SafeArray<Object> safe = source.map(Object.class::cast);

        System.out.println(source);

        safe.fill("Test");
        System.out.println(source);

        unsafe.fill("Test");
        System.out.println(source);
    }
}
