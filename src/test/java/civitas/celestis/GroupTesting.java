package civitas.celestis;

import civitas.celestis.util.group.ArrayTuple;
import civitas.celestis.util.group.Group;

public class GroupTesting {
    public static void main(String[] args) {
        final Group<Double> doubleGroup = new ArrayTuple<>(1d, 2d, 3d, 4d);

        System.out.println(doubleGroup);
        System.out.println(doubleGroup.map(v -> null));
    }
}
