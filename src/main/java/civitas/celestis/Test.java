package civitas.celestis;


import civitas.celestis.math.Fraction;
import civitas.celestis.math.complex.Complex;

public class Test {
    public static void main(String[] args) {
        final Fraction pi1 = Fraction.PI;
        final Complex pi2 = Complex.PI;

        System.out.println(pi1);
        System.out.println(pi2);

        System.out.println(pi1.add(pi2));
        System.out.println(Complex.I.add(pi1.multiply(pi2)));
    }
}
