package civitas.celestis.listener.notification;

import civitas.celestis.event.EventHandler;
import civitas.celestis.event.HandlerPriority;
import civitas.celestis.event.Listener;
import civitas.celestis.event.notification.NotificationEvent;
import jakarta.annotation.Nonnull;

import java.io.PrintStream;

/**
 * A listener which exclusively listens to {@link NotificationEvent}s,
 * and prints them to the specified print stream.
 */
public class NotificationListener implements Listener {
    //
    // Constructors
    //

    /**
     * Creates a new notification listener. The print stream is
     * designated to {@link System#out} by default.
     */
    public NotificationListener() {
        this(System.out);
    }

    /**
     * Creates a new notification listener.
     *
     * @param printStream The print stream to print messages to
     */
    public NotificationListener(@Nonnull PrintStream printStream) {
        this.printStream = printStream;
    }

    //
    // Variables
    //

    /**
     * The print stream to print messages to.
     */
    @Nonnull
    private final PrintStream printStream;

    //
    // Methods
    //

    /**
     * Prints the notification's message to the specified print stream.
     *
     * @param event The event which was called
     */
    @EventHandler(priority = HandlerPriority.PERMISSIVE)
    public void onNotification(@Nonnull NotificationEvent event) {
        printStream.println(event.getMessage());
    }
}
