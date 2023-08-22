package civitas.celestis;


import civitas.celestis.util.array.AtomicArray;
import civitas.celestis.util.array.DoubleArray;
import civitas.celestis.util.array.SafeArray;

import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        final SafeArray<String> first = SafeArray.of("Hello", "world", "foo");
        final SafeArray<String> second = SafeArray.of("bar", "fuzz", "bizz");

        final SafeArray<String> a1 = first.append(second);
        final SafeArray<String> a2 = first.prepend(second);

        System.out.println(a1);
        System.out.println(a2);
    }
}
