package civitas.celestis;

import civitas.celestis.event.EventManager;
import civitas.celestis.event.SyncEventManager;
import civitas.celestis.task.Scheduler;
import civitas.celestis.task.SyncScheduler;

public class ModuleTesting {
    static final EventManager eventManager = new SyncEventManager();
    static final Scheduler scheduler = new SyncScheduler();


    public static void main(String[] args) {
        eventManager.register(new TestListener());

        eventManager.start();
        scheduler.start();

        eventManager.call(new TestEvent());
        scheduler.register(System.out::println);
    }

}
