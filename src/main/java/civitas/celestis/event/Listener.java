package civitas.celestis.event;

import jakarta.annotation.Nonnull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A marker interface which marks an object as an event listener.
 * Methods annotated with the {@link EventHandler} annotation, and which take
 * one parameter which is a subtype of {@link Handleable} will be considered event handler methods.
 *
 * @see Event
 * @see EventHandler
 */
public interface Listener {
    /**
     * Returns a collection containing references to all event handlers of this listener.
     * Changing this will affect the behavior of the event manager. Modify this at your own risk.
     *
     * @return A collection containing references to all event handlers of this listener
     */
    @Nonnull
    default Collection<HandlerReference> getHandlers() {
        final List<HandlerReference> references = new ArrayList<>();

        // Iterate through declared methods
        for (final Method method : getClass().getDeclaredMethods()) {

            // Check if the method is a valid event handler
            if (!method.isAnnotationPresent(EventHandler.class)) continue;
            if (method.getParameterCount() != 1) continue;

            // Add a reference to the handler
            references.add(new HandlerReference(this, method));

        }

        return references;
    }
}
