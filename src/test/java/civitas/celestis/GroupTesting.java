package civitas.celestis;

import civitas.celestis.util.array.SafeArray;

public class GroupTesting {
    public static void main(String[] args) {
        final SafeArray<String> array = SafeArray.of("reds", "aslddask", "rredkr", "asdsdsddklk");
        System.out.println(array.cast(Object.class));

    }
}
