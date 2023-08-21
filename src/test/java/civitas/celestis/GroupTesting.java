package civitas.celestis;


import civitas.celestis.math.Complex;
import civitas.celestis.math.Vector3;

public class GroupTesting {
    public static void main(String[] args) {
        final Complex c1 = new Complex("23 - 2i");
        final Complex c2 = new Complex(c1.toString());

        System.out.println(c1);
        System.out.println(c2);
    }
}
