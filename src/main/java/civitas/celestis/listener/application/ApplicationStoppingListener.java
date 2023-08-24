package civitas.celestis.listener.application;

import civitas.celestis.Application;
import civitas.celestis.annotation.application.ApplicationCritical;
import civitas.celestis.event.EventHandler;
import civitas.celestis.event.HandlerPriority;
import civitas.celestis.event.application.ApplicationStoppingEvent;
import civitas.celestis.listener.EventListener;
import jakarta.annotation.Nonnull;

/**
 * An event listener which exclusively listens to {@link ApplicationStoppingEvent}s.
 * @see ApplicationStoppingEvent
 */
public class ApplicationStoppingListener extends EventListener<ApplicationStoppingEvent> {
    /**
     * Handles the graceful termination of an application.
     * @param event The event which was called
     */
    @Override
    @ApplicationCritical(created = "0.4", lastUpdated = "0.4")
    @EventHandler(priority = HandlerPriority.TERMINAL)
    public void handle(@Nonnull ApplicationStoppingEvent event) {
        event.getApplication().terminate(Application.EXIT_CODE_NORMAL);
    }
}
