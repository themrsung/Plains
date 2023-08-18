package civitas.celestis;

import civitas.celestis.util.group.Quad;

public class GroupTesting {
    public static void main(String[] args) {
        final Quad<String> quad = new Quad<>("Hello", "world", "Foo", "bar");

        System.out.println(quad);
    }
}
