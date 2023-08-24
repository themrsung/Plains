package civitas.celestis.listener.application;

import civitas.celestis.Application;
import civitas.celestis.annotation.application.ApplicationCritical;
import civitas.celestis.event.EventHandler;
import civitas.celestis.event.HandlerPriority;
import civitas.celestis.event.application.ApplicationStartedEvent;
import civitas.celestis.listener.EventListener;
import jakarta.annotation.Nonnull;

/**
 * An event listener which exclusively listens to {@link ApplicationStartedEvent}s.
 * @see ApplicationStartedEvent
 */
public class ApplicationStartedListener extends EventListener<ApplicationStartedEvent> {
    /**
     * Handles the post-initialization operations of the application.
     * @param event The event which was called
     */
    @Override
    @ApplicationCritical(created = "0.4", lastUpdated = "0.4")
    @EventHandler(priority = HandlerPriority.INITIALIZATION)
    public void handle(@Nonnull ApplicationStartedEvent event) {
        final Application application = event.getApplication();
        System.out.println(application.getName() + " v" + application.getVersion() + " has started.");
    }
}
