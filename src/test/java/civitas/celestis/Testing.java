package civitas.celestis;

import civitas.celestis.math.complex.Quaternions;
import civitas.celestis.math.decimal.Decimal3;
import civitas.celestis.math.decimal.Decimal4;
import civitas.celestis.math.decimal.DecimalQuaternion;
import civitas.celestis.math.vector.Vector3;

public class Testing {
    public static void main(String[] args) {
        final Decimal3 v = new Decimal3(new Vector3(23, -40, 22));
        final Decimal3 p = v.rotate(new DecimalQuaternion(Quaternions.random()));

        System.out.println(v);
        System.out.println(p);

        System.out.println(v.norm());
        System.out.println(p.norm());
    }
}
