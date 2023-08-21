package civitas.celestis;


import civitas.celestis.util.Int3;
import civitas.celestis.util.Tuple;

public class GroupTesting {
    public static void main(String[] args) {
        final Int3 i3 = new Int3("[1, 2, 3]");

        System.out.println(i3);
        System.out.println(new Int3(i3.toString()));
    }
}
