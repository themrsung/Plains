package civitas.celestis.event.application;

import civitas.celestis.Application;
import civitas.celestis.event.Event;
import civitas.celestis.event.Handleable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An event called by the application to notify classes that there was a
 * significant change in the application's state.
 *
 * @see ApplicationStartedEvent
 * @see ApplicationStoppingEvent
 */
public abstract class ApplicationEvent extends Event {
    //
    // Constructors
    //

    /**
     * Creates a new application event.
     *
     * @param application The application which invoked this event
     */
    public ApplicationEvent(@Nonnull Application application) {
        this.application = application;
    }

    /**
     * Creates a new application event.
     *
     * @param application The application which invoked this event
     * @param cause       The cause of this event's invocation
     */
    public ApplicationEvent(@Nonnull Application application, @Nullable Handleable cause) {
        super(cause);
        this.application = application;
    }

    //
    // Variables
    //

    /**
     * The application which triggered this event.
     */
    @Nonnull
    protected final Application application;

    //
    // Getters
    //

    /**
     * Returns the application which triggered this event.
     *
     * @return The application which triggered this event
     */
    @Nonnull
    public Application getApplication() {
        return application;
    }
}
