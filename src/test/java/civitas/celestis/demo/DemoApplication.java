package civitas.celestis.demo;

import civitas.celestis.Application;
import civitas.celestis.annotation.application.ApplicationCritical;
import civitas.celestis.event.application.ApplicationStartedEvent;
import civitas.celestis.event.application.ApplicationStoppingEvent;
import civitas.celestis.event.notification.NotificationEvent;
import civitas.celestis.listener.application.ApplicationStartedListener;
import civitas.celestis.listener.application.ApplicationStoppingListener;
import civitas.celestis.listener.notification.NotificationListener;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * A demo application to demonstrate the usage of the application class.
 * The fields and methods are organized in the natural temporal order of
 * this application's lifecycle in order to increase readability.
 * This is not a common convention, and is simply used to for easier reading.
 *
 * @see Application
 * @see ApplicationCritical
 */
public class DemoApplication extends Application {
    //
    // Instance
    //

    /*
     * Using a Singleton design pattern is recommended for Plains applications,
     * as having static references to modules is important for inter-module operations.
     */

    /**
     * The instance of this application.
     */
    @ApplicationCritical
    private static final DemoApplication application = new DemoApplication();

    /**
     * Returns the application instance.
     * @return The application instance
     */
    @Nonnull
    public static DemoApplication getApplication() {
        return application;
    }

    //
    // Initialization
    //

    /**
     * Creates a new demo application. This constructor is private to ensure
     * that the Singleton design pattern is enforced. (there is only one instance of this application)
     */
    private DemoApplication() {
        super("PlainsDemoApplication", "0.4");
    }

    //
    // Startup
    //

    /**
     * Called by the system. This delegates to {@link #start()}.
     * @param ignored The array of arguments provided by the system
     *             (ignored for this application)
     */
    @ApplicationCritical
    public static void main(@Nonnull String[] ignored) {
        getApplication().start();
    }

    /**
     * Starts the application. Startup logic is inlined as-is from version {@code 0.4}.
     */
    @Override
    @ApplicationCritical(created = "0.4", lastUpdated = "0.4")
    public void start() {
        //
        // START OF STARTUP LOGIC FROM v0.4
        //

        // Initialize modules
        eventManager.initialize();
        scheduler.initialize();

        // Register event listeners

        eventManager.register(new NotificationListener());

        eventManager.register(List.of(
                new ApplicationStartedListener(),
                new ApplicationStoppingListener()
        ));

        // Start modules
        eventManager.start();
        scheduler.start();

        // Notify classes that the application has fully initialized
        eventManager.call(new ApplicationStartedEvent(this));

        //
        // END OF STARTUP LOGIC FROM v0.4
        //

        // Calls an event to print hello world to the console
        getEventManager().call(new NotificationEvent("Hello world!"));

        /*
         * Sleep for 1 second to allow listeners to process the event.
         * Event managers have their own thread, and thus stopping the application
         * immediately may result in the event not being processed before termination.
         */
        try {
            Thread.sleep(1000);
        } catch (final InterruptedException ignored) {}

        // Stop the program gracefully
        stop();
    }

    /**
     * Stops the application. The super method is inlined as-is from version {@code 0.4}.
     */
    @Override
    @ApplicationCritical(created = "0.4", lastUpdated = "0.4")
    public void stop() {

        /*
         * This will lead to the listener terminating the application.
         * The event manager and scheduler cannot be stopped yet, as listeners to
         * the application stopping event may still require them to be operational.
         */

        eventManager.call(new ApplicationStoppingEvent(this));

        /*
         * Alternatively, eventManager.priorityCall(Handleable) can be used to
         * prioritize the stopping of the application over other events which are
         * already in the queue.
         */
    }

    /**
     * Terminates the application. This should not be called directly, as it is handled
     * by the final listener of the {@link ApplicationStoppingEvent}. The super method
     * is inlined as-is from version {@code 0.4}.
     *
     * @param exitCode The exit code to send to {@link System#exit(int)}
     * @see ApplicationStoppingListener
     */
    @Override
    @ApplicationCritical(created = "0.4", lastUpdated = "0.4")
    public void terminate(int exitCode) {
        // Terminate modules
        eventManager.terminate();
        scheduler.terminate();

        // Exit program
        System.exit(exitCode);
    }
}
