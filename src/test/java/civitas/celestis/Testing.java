package civitas.celestis;

import civitas.celestis.util.array.DoubleArray;
import civitas.celestis.util.array.SafeArray;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Testing {
    public static void main(String[] args) {
        final DoubleArray a1 = DoubleArray.of(1, 2, 3, 4, 5, 6, 7);
        final DoubleArray a2 = DoubleArray.of(8, 9, 10, 11, 12);

        final SafeArray<String> a3 = a1.append(a2).mapToObj(Double::toString);
        final SafeArray<BigDecimal> a4 = a3.map(BigDecimal::new);

        System.out.println(a4);
    }
}
