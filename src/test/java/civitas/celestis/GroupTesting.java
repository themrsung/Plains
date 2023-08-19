package civitas.celestis;

import civitas.celestis.math.matrix.Matrix;
import civitas.celestis.util.collection.GroupableArrayList;

public class GroupTesting {
    public static void main(String[] args) {
        Matrix m = new Matrix(5, 5);
        m = m.transform(o -> Math.random());

        System.out.println(new GroupableArrayList<>(m));
    }
}
