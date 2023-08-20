package civitas.celestis;


import civitas.celestis.math.Numbers;
import civitas.celestis.util.SafeArray;

public class GroupTesting {
    public static void main(String[] args) {
        final SafeArray<Double> array1 = SafeArray.of(1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d);
        final SafeArray<Double> array2 = SafeArray.of(10d, 9d, 8d, 7d, 6d, 5d, 4d, 3d, 2d, 1d);
        final SafeArray<Double> array3 = array1.merge(array2, (x, y) -> Numbers.factorial(x) / y);

        System.out.println(array3);
        array3.shuffle();
        System.out.println(array3);
    }
}
