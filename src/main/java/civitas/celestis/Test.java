package civitas.celestis;


import civitas.celestis.event.lifecycle.EventManager;
import civitas.celestis.event.lifecycle.SyncEventManager;
import civitas.celestis.event.notification.NotificationEvent;
import civitas.celestis.listener.notification.NotificationListener;

public class Test {
    private static final EventManager eventManager = new SyncEventManager();

    public static void main(String[] args) {
        eventManager.register(new NotificationListener(System.out));
        eventManager.start();

        eventManager.call(new NotificationEvent("Hello world!"));

        try {
            Thread.sleep(1000);
        } catch (final InterruptedException ignored) {}

        eventManager.terminate();
    }
}
