package civitas.celestis;

import civitas.celestis.util.collection.GroupableArrayList;
import civitas.celestis.util.collection.NumericArrayList;

import java.util.List;

public class GroupTesting {
    public static void main(String[] args) {
        final NumericArrayList<Double> l1 = NumericArrayList.of(1d, 1d, 1d, 1d, 1d, 1d, 1d);
        final NumericArrayList<Double> l2 = NumericArrayList.of(2d, 2d, 2d, 2d, 2d, 2d, 2d);
        final GroupableArrayList<Double> sum = l1.merge(l2, Double::sum);

        sum.add(null);
        sum.fillEmpty(Math.PI);

        final List<Double> l3 = new GroupableArrayList<>(l1.group());

        System.out.println(l3);
    }
}
