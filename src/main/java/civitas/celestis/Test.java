package civitas.celestis;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.tuple.Tuple;

import java.util.Comparator;

public class Test {
    public static void main(String[] args) {
        final SafeArray<String> strings = SafeArray.of("Hello", "World", null, "Foo", null, null, "bar");

        final Tuple<String> tuple = strings.tuple();
        System.out.println(tuple);

        strings.shuffle();
        System.out.println(tuple);
    }
}
