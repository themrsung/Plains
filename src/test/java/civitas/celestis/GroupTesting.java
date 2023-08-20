package civitas.celestis;

import civitas.celestis.util.tuple.Tuple;

public class GroupTesting {
    public static void main(String[] args) {
        final Tuple<String> tuple = Tuple.of("Hello", "world", "foo", "bar", "biz", "text");
        System.out.println(tuple.containsAll(tuple));
    }
}
