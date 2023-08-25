package civitas.celestis;

import civitas.celestis.event.CancellableEvent;
import civitas.celestis.graphics.Color;
import civitas.celestis.graphics.Colors;
import civitas.celestis.graphics.LinearColor;
import civitas.celestis.graphics.PackedColor;
import civitas.celestis.task.lifecycle.AtomicScheduler;
import civitas.celestis.task.lifecycle.Scheduler;

public class GroupTesting {
    static final Scheduler scheduler = new AtomicScheduler();

    public static void main(String[] args) {
        final Color c1 = new LinearColor(23.40203f, 42.320302f, 44.2039230f, 200.329032f);
        final long rgba = c1.rgba64();
        final Color c2 = new PackedColor(rgba);

        System.out.println(c1.toReadableString());
        System.out.println(c2.toReadableString());

        System.out.println(c1.equals(c2));
    }
}
