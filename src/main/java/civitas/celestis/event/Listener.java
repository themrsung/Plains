package civitas.celestis.event;

import jakarta.annotation.Nonnull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A marker interface which marks an object as an event handler.
 * Methods marked with the {@link EventHandler} annotation will be treated as
 * event handler methods.
 *
 * @see Handleable
 * @see Event
 * @see EventHandler
 */
public interface Listener {
    /**
     * Searches through all declared methods of this event listener, collects all valid
     * event handlers, then returns the collection of references to the event handlers.
     * <p><b>Modify this method at your own risk.</b></p>
     *
     * @return A collection containing references to every valid handler method of this class
     */
    @Nonnull
    default Collection<HandlerReference> getHandlerReferences() {
        final List<HandlerReference> handlers = new ArrayList<>();

        for (final Method method : getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class)) continue;
            if (method.getParameterCount() != 1) continue;
            if (!method.getParameterTypes()[0].isAssignableFrom(Handleable.class)) continue;

            handlers.add(new HandlerReference(this, method));
        }

        return handlers;
    }
}
