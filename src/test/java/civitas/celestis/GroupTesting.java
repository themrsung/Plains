package civitas.celestis;


import civitas.celestis.util.tensoid.ArrayTensoid;
import civitas.celestis.util.tensoid.HashTensoid;
import civitas.celestis.util.tensoid.Tensoid;

public class GroupTesting {
    public static void main(String[] args) {
        final Tensoid<String> cube = new ArrayTensoid<>(30, 30, 30);
        cube.fill("Hello world");

        final Tensoid<String> t2 = new HashTensoid<>(30, 30, 30);
        t2.fill("Hello world");

        System.out.println(cube.dimensions());
        System.out.println(t2.dimensions());
        System.out.println(cube.equals(t2));
    }
}
