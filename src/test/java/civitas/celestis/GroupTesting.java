package civitas.celestis;

import civitas.celestis.event.Event;
import civitas.celestis.event.Events;
import civitas.celestis.event.notification.NotificationEvent;
import civitas.celestis.task.DelayedTask;
import civitas.celestis.task.lifecycle.AtomicScheduler;
import civitas.celestis.task.lifecycle.Scheduler;

public class GroupTesting {
    private static final Scheduler scheduler = new AtomicScheduler();
    public static void main(String[] args) {
        scheduler.register(System.out::println);
        scheduler.start();
        scheduler.register(new DelayedTask(scheduler::interrupt, 1000));
    }
}
