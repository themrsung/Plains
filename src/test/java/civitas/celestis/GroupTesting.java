package civitas.celestis;

import civitas.celestis.math.atomic.AtomicVector3;
import civitas.celestis.math.vector.Quaternion;
import civitas.celestis.math.vector.Vector3;

public class GroupTesting {
    public static void main(String[] args) {
        final Vector3 original = new Vector3(1223, -4949, 30303);
        final AtomicVector3 atomic = new AtomicVector3(original);

        atomic.rotate(new Quaternion(232, -404, -333, 2).normalize());
        System.out.println(atomic);
    }
}
