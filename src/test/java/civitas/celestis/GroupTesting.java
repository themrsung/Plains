package civitas.celestis;


import civitas.celestis.math.Complex;
import civitas.celestis.math.Vector3;

public class GroupTesting {
    public static void main(String[] args) {
        System.out.println(new Vector3("[2, 3, 10]"));
        System.out.println(new Vector3(new Vector3(1, 2, 3).toString()));
    }
}
