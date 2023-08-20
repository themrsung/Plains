package civitas.celestis;

import civitas.celestis.util.array.FastArray;

public class GroupTesting {
    public static void main(String[] args) {
        final FastArray<String> array = new FastArray<>(10);
        array.fill("Hello World");

        for (int i = 0; i < 10; i++) {
            if (i % 3 != 0) continue;
            array.set(i, "Foo Bar");
        }

        array.apply(s -> s.replaceAll("World", "World!"));

        System.out.println(array);
    }
}
