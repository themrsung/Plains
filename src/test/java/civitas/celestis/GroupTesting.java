package civitas.celestis;

import civitas.celestis.util.SafeArray;

public class GroupTesting {
    public static void main(String[] args) {
        final SafeArray<String> array = new SafeArray<>(10);
        array.fill("Hello");

        System.out.println(array);
    }
}
