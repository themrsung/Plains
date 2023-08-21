package civitas.celestis;


import civitas.celestis.math.Vector2;
import civitas.celestis.math.Vector3;
import civitas.celestis.util.Tuple;

public class GroupTesting {
    public static void main(String[] args) {
        final Tuple<Integer> int2 = Tuple.ofInt(1, 2);
        final Tuple<Integer> int3 = Tuple.ofInt(1, 2, 3);

        System.out.println(int2);
        System.out.println(int3);
        System.out.println(int2.getClass());
        System.out.println(int3.getClass());
    }
}
