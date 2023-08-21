package civitas.celestis;


import civitas.celestis.math.Complex;

public class GroupTesting {
    public static void main(String[] args) {
        final Complex c = new Complex(23, -3);

        System.out.println(c.multiply(c));
        System.out.println(c.pow(2));


    }
}
