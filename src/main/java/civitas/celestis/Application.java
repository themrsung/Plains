package civitas.celestis;

import civitas.celestis.annotation.application.ApplicationCritical;
import civitas.celestis.event.application.ApplicationStartedEvent;
import civitas.celestis.event.application.ApplicationStoppingEvent;
import civitas.celestis.event.lifecycle.EventManager;
import civitas.celestis.event.lifecycle.SyncEventManager;
import civitas.celestis.listener.application.ApplicationStartedListener;
import civitas.celestis.listener.application.ApplicationStoppingListener;
import civitas.celestis.listener.notification.NotificationListener;
import civitas.celestis.task.lifecycle.AtomicScheduler;
import civitas.celestis.task.lifecycle.Scheduler;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * Defines the contract for a generic application using the Plains library.
 * All modules are configured to use {@link System#out} as its print stream
 * unless the module instances are specified using the protected constructor
 * by a customized subclass.
 * <p>
 * Using this class directly should only be done for testing and debugging
 * purposes, and the final application should be a subclass which properly
 * handles the lifecycle of its specific implementation.
 * </p>
 */
public class Application {
    //
    // Constants
    //

    /**
     * The exit code for normal graceful termination of the application.
     *
     * @since 0.4
     */
    public static final int EXIT_CODE_NORMAL = 0;

    /**
     * The exit code for forceful termination of the application by means of
     * directly calling {@link #terminate(int)}. (without proper exit procedure)
     *
     * @since 0.4
     */
    public static final int EXIT_CODE_TERMINATED = -1;

    /**
     * The exit code for forceful termination of the application caused
     * by a fatal error, which makes the application no longer operable.
     *
     * @since 0.4
     */
    public static final int EXIT_CODE_FATAL_ERROR = 66;

    //
    // Lifecycle
    //

    /**
     * Initiates this application, initializing all modules and starting the program.
     */
    @ApplicationCritical(created = "0.4", lastUpdated = "0.4")
    public void start() {
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
    }

    /**
     * Gracefully stops the application, giving modules time to finalize their operations.
     *
     * @see ApplicationStoppingListener
     */
    @ApplicationCritical(created = "0.4", lastUpdated = "0.4")
    public void stop() {

        /*
         * This will lead to the listener terminating the application.
         * The event manager and scheduler cannot be stopped yet, as listeners to
         * the application stopping event may still require them to be operational.
         */

        eventManager.call(new ApplicationStoppingEvent(this));
    }

    /**
     * Forcefully terminates the application, regardless of its current state.
     * This is a destructive action, and only should be called when a fatal error occurs.
     *
     * @param exitCode The exit code to send to {@link System#exit(int)}
     * @see #EXIT_CODE_NORMAL
     * @see #EXIT_CODE_TERMINATED
     * @see #EXIT_CODE_FATAL_ERROR
     */
    @ApplicationCritical(created = "0.4", lastUpdated = "0.4")
    public void terminate(int exitCode) {
        // Terminate modules
        eventManager.terminate();
        scheduler.terminate();

        // Exit program
        System.exit(exitCode);
    }

    //
    // Constructors
    //

    /**
     * Creates a new application with the default module configuration.
     *
     * @param name    The name of this application
     * @param version The version of this application
     */
    public Application(@Nonnull String name, @Nonnull String version) {
        this(name, version, new SyncEventManager(), new AtomicScheduler());
    }

    /**
     * Creates a new application.
     *
     * @param name         The name of this application
     * @param version      The version of this application
     * @param eventManager The event manager instance of this application
     * @param scheduler    The scheduler instance of this application
     */
    protected Application(
            @Nonnull String name,
            @Nonnull String version,
            @Nonnull @ApplicationCritical EventManager eventManager,
            @Nonnull @ApplicationCritical Scheduler scheduler
    ) {
        this.name = name;
        this.version = version;
        this.eventManager = eventManager;
        this.scheduler = scheduler;
    }

    //
    // Identification
    //

    /**
     * The name of this application.
     */
    @Nonnull
    protected final String name;

    /**
     * The version of this application.
     */
    @Nonnull
    protected final String version;

    //
    // Modules
    //

    /**
     * The event manager instance.
     */
    @Nonnull
    protected final EventManager eventManager;

    /**
     * The scheduler instance.
     */
    @Nonnull
    protected final Scheduler scheduler;

    //
    // Getters
    //

    /**
     * Returns the name of this application.
     *
     * @return The name of this application
     */
    @Nonnull
    public String getName() {
        return name;
    }

    /**
     * Returns the version of this application.
     *
     * @return The version of this application
     */
    @Nonnull
    public String getVersion() {
        return version;
    }

    /**
     * Returns the event manager of this application.
     *
     * @return The event manager of this application
     */
    @Nonnull
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Returns the scheduler of this application.
     *
     * @return The scheduler of this application
     */
    @Nonnull
    public Scheduler getScheduler() {
        return scheduler;
    }
}
