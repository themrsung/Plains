package civitas.celestis;

import civitas.celestis.util.Tuple;

public class GroupTesting {
    public static void main(String[] args) {
        final Tuple<String> test = Tuple.of("Hello", "world", "foo", "bar", "yes", "no");
        System.out.println(test.subTuple(2, 4));
    }
}
