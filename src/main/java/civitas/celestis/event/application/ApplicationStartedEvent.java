package civitas.celestis.event.application;

import civitas.celestis.Application;
import civitas.celestis.event.Handleable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An event called by the application to notify other classes that the application has
 * been fully initialized, and the application is now fully operational.
 *
 * @see ApplicationEvent
 */
public class ApplicationStartedEvent extends ApplicationEvent {
    //
    // Constructors
    //

    /**
     * Creates a new application started event.
     *
     * @param application The application which invoked this event
     */
    public ApplicationStartedEvent(@Nonnull Application application) {
        super(application);
    }

    /**
     * Creates a new application started event.
     *
     * @param application The application which invoked this event
     * @param cause       The cause of this event's invocation
     */
    public ApplicationStartedEvent(@Nonnull Application application, @Nullable Handleable cause) {
        super(application, cause);
    }
}
