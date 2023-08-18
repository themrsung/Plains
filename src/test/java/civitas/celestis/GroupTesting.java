package civitas.celestis;

import civitas.celestis.math.matrix.Matrix;
import civitas.celestis.util.collection.NumericArrayList;
import civitas.celestis.util.group.Grid;

public class GroupTesting {
    public static void main(String[] args) {
        Matrix m1 = new Matrix(10, 10);

        m1 = m1.transform(v -> Math.random());

        final NumericArrayList<Double> list = new NumericArrayList<>(m1);

        System.out.println(list);
    }
}
