package civitas.celestis;

import civitas.celestis.util.array.SafeArray;

public class Testing {
    public static void main(String[] args) {
        final String[] primitive = new String[1];
        SafeArray.referenceOf(primitive).fill("Hello world");
        System.out.println(primitive[0]);
    }
}
