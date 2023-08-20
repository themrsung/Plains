package civitas.celestis;

import civitas.celestis.math.Numbers;
import civitas.celestis.util.array.FastArray;

public class GroupTesting {
    public static void main(String[] args) {
        final FastArray<Long> array = FastArray.of(
                new long[]{
                        0,
                        1,
                        2,
                        3,
                        4,
                        5,
                        6,
                        7,
                        8,
                        9,
                        10,
                        11,
                        12,
                        13,
                        14,
                        15,
                        16,
                        17,
                        18,
                        19,
                        20
                }
        );

        final FastArray<Double> factorials = array.map(v -> Numbers.factorial((double) v));
        factorials.forEach((i, v) -> System.out.println(v + "L,"));
    }
}
