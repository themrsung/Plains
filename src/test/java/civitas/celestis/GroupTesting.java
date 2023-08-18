package civitas.celestis;

import civitas.celestis.math.atomic.AtomicVector3;
import civitas.celestis.math.vector.Vector3;

public class GroupTesting {
    public static void main(String[] args) {
        final Vector3 v = new Vector3(123, -494, 3333);
        final AtomicVector3 av = new AtomicVector3(v);
        av.clamp(Vector3.ZERO, new Vector3(1000, 1000, 1000));
        av.normalize();
        System.out.println(av.get());
    }
}
