package civitas.celestis;


import civitas.celestis.util.tensoid.ArrayTensoid;
import civitas.celestis.util.tensoid.HashTensoid;
import civitas.celestis.util.tensoid.Tensoid;

public class GroupTesting {
    public static void main(String[] args) {
        final int n = 500;

        final Tensoid<String> hash = new HashTensoid<>(n, n, n);

        hash.set(100, 100, 100, "Hello World");
        System.out.println(hash.get(100, 100, 100));
    }
}
