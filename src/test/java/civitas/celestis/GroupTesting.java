package civitas.celestis;

import civitas.celestis.event.CancellableEvent;
import civitas.celestis.task.lifecycle.AtomicScheduler;
import civitas.celestis.task.lifecycle.Scheduler;

public class GroupTesting {
    static final Scheduler scheduler = new AtomicScheduler();

    public static void main(String[] args) {
        final CancellableEvent event = new CancellableEvent();
        System.out.println(event);
    }
}
