package civitas.celestis;

import civitas.celestis.task.lifecycle.AtomicScheduler;
import civitas.celestis.task.lifecycle.Scheduler;
import civitas.celestis.util.concurrent.Threads;

public class GroupTesting {
    static final Scheduler scheduler = new AtomicScheduler();
    public static void main(String[] args) {
        scheduler.register(delta -> System.out.println("The program is running."));
        scheduler.start();

        Threads.execute(scheduler::terminate, 1000);
    }
}
