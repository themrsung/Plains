package civitas.celestis;

import civitas.celestis.event.CancellableEvent;
import civitas.celestis.graphics.*;
import civitas.celestis.task.lifecycle.AtomicScheduler;
import civitas.celestis.task.lifecycle.Scheduler;

public class GroupTesting {
    static final Scheduler scheduler = new AtomicScheduler();

    public static void main(String[] args) {
        final Point p = new Point(500, 500);
        final Point q = p.rotate(0.5 * Math.PI);
        System.out.println(q);
    }
}
