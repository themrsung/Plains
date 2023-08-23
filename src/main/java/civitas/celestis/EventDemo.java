package civitas.celestis;

import civitas.celestis.event.lifecycle.EventManager;
import civitas.celestis.event.lifecycle.SyncEventManager;
import civitas.celestis.event.notification.NotificationEvent;
import civitas.celestis.listener.notification.NotificationListener;
import jakarta.annotation.Nonnull;

/**
 * A program which demonstrates the usage of an event manager to print a message to the console.
 *
 * @see EventManager
 */
public final class EventDemo {
    /**
     * The event manager instance this program uses.
     */
    private static final EventManager eventManager = new SyncEventManager();

    /**
     * The main method of this program. The program should print
     * {@code "Hello world!"} to the console, then automatically terminate.
     *
     * @param args The array of arguments
     */
    public static void main(@Nonnull String[] args) {

        // This is not required for SyncEventManager,
        // but other event managers may require it to be called.
        eventManager.initialize();

        // Sets up the program
        eventManager.register(new NotificationListener(System.out));
        eventManager.start();

        // Calls the event
        eventManager.call(new NotificationEvent("Hello world!"));

        // Sleeps for 1 second to allow the event manager to process the event
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException ignored) {}

        // Terminates the program
        eventManager.terminate();

        // This is not required, as all threads have already been terminated.
        // Even then, it is a good practice to call this at the end of your program.
        System.exit(0);

    }
}
