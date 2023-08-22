package civitas.celestis;


import civitas.celestis.math.complex.Quaternions;
import civitas.celestis.math.vector.Vector3;

public class Test {
    public static void main(String[] args) {
        final Vector3 v1 = new Vector3(0, 0, 100);
        final Vector3 v2 = v1.rotate(Quaternions.quaternion(0, Math.toRadians(90), 0));
        System.out.println(v2);
    }
}
