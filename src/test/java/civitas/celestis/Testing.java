package civitas.celestis;

import civitas.celestis.util.tuple.ArrayTuple;
import civitas.celestis.util.tuple.Tuple;

public class Testing {
    public static void main(String[] args) {
        final Tuple<String> e1 = Tuple.of();
        final Tuple<Integer> e2 = Tuple.of();
        final Tuple<String> e3 = new ArrayTuple<>();

        System.out.println(e1.hashCode());
        System.out.println(e2.hashCode());
        System.out.println(e3.hashCode());
    }
}
