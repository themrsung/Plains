package civitas.celestis;

import civitas.celestis.util.tuple.Tuple;

public class Test {
    public static void main(String[] args) {
        final Tuple<String> tuple = Tuple.of("Hello", "world", "foo", "bar", "fifth", "sixth");
        tuple.stream();
    }
}
