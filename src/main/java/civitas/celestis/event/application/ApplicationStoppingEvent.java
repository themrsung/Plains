package civitas.celestis.event.application;

import civitas.celestis.Application;
import civitas.celestis.event.Handleable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An event called by the application to notify other classes that the application is
 * about to stop, and all modules must prepare for termination.
 *
 * @see ApplicationEvent
 */
public class ApplicationStoppingEvent extends ApplicationEvent {
    //
    // Constructors
    //

    /**
     * Creates a new application stopping event.
     *
     * @param application The application which invoked this event
     */
    public ApplicationStoppingEvent(@Nonnull Application application) {
        super(application);
    }

    /**
     * Creates a new application stopping event.
     *
     * @param application The application which invoked this event
     * @param cause       The cause of this event's invocation
     */
    public ApplicationStoppingEvent(@Nonnull Application application, @Nullable Handleable cause) {
        super(application, cause);
    }
}
