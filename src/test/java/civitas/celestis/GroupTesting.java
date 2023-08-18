package civitas.celestis;

import civitas.celestis.util.collection.GroupableArrayList;
import civitas.celestis.util.collection.GroupableList;
import civitas.celestis.util.group.Group;
import civitas.celestis.util.group.SafeArray;

public class GroupTesting {
    public static void main(String[] args) {
        final GroupableList<String> groupList = new GroupableArrayList<>();

        for (int i = 0; i < 10; i++) {
            groupList.add("Hello world!");
        }

        final Group<String> group = Group.fromCollection(groupList);
        System.out.println(group);

        ((SafeArray<String>) group).set(0, "test");

        System.out.println(groupList);
    }
}
