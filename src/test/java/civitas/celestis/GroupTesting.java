package civitas.celestis;


import civitas.celestis.math.*;

public class GroupTesting {
    public static void main(String[] args) {
        final Vector3 v1 = new Vector3(1, 2, 3);
        final Vector3 min = new Vector3(-1, -1, -1);
        final Vector3 max = new Vector3(10, 10, 10);

        System.out.println(Numbers.isInRange(v1, min, max));

    }
}
