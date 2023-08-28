package civitas.celestis;

import civitas.celestis.util.array.AtomicArray;
import civitas.celestis.util.array.DoubleArray;
import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.array.SyncArray;

import java.util.Comparator;

public class Testing {
    public static void main(String[] args) {
        final DoubleArray array = DoubleArray.of(30, 29, 28, 27, 26, 25, 24, 23, 23, 2, 2, 2);

        final AtomicArray<Double> atomic = new AtomicArray<>(array.boxed());
        System.out.println(atomic);

        atomic.subArray(2, 5).sort(Comparator.reverseOrder());

        System.out.println(atomic);

        final SafeArray<Double> boxed = new SyncArray<>(atomic);

        System.out.println(atomic.equals(boxed));
    }
}
