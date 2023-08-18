package civitas.celestis;

import civitas.celestis.math.vector.ArrayVector;
import civitas.celestis.math.vector.Decimal3;
import civitas.celestis.math.vector.DecimalArrayVector;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.group.Group;
import civitas.celestis.util.map.GroupMapper;

import java.math.BigDecimal;

public class GroupTesting {
    public static void main(String[] args) {
        final Vector3 v1 = new Vector3(23, -32, 11);
        final ArrayVector v2 = new ArrayVector(v1);
        final Decimal3 v3 = new Decimal3(v2);
        final DecimalArrayVector v4 = new DecimalArrayVector((Group<BigDecimal>) v3);

        System.out.println(v1);
        System.out.println(v4);
        System.out.println(v1.equals(new Vector3(v4)));

        System.out.println(GroupMapper.map(v4));
    }
}
