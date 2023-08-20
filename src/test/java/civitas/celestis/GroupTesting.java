package civitas.celestis;


import civitas.celestis.math.Vector3;
import civitas.celestis.util.SafeArray;
import civitas.celestis.util.SyncArray;

public class GroupTesting {
    public static void main(String[] args) {
        SafeArray<Vector3> array = new SyncArray<>(10);
        array.fill(new Vector3(1, 2, 3));
        array.forEach(System.out::println);
    }
}
