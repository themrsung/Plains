package civitas.celestis.event;

import civitas.celestis.event.lifecycle.HandlerReference;
import jakarta.annotation.Nonnull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A marker interface which defines the contract of an event listener.
 * @see Event
 */
public interface Listener {
    /**
     * Returns a list containing references to every valid
     * handler of this event listener object. Modify this method at your own risk.
     * @return A list of valid handler references within this object
     */
    @Nonnull
    default List<HandlerReference> getHandlerReferences() {
        final List<HandlerReference> refs = new ArrayList<>();

        for (final Method method : getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(EventHandler.class)) continue;
            if (method.getParameterCount() != 1) continue;
            if (!method.getParameterTypes()[0].isAssignableFrom(Handleable.class)) continue;

            refs.add(new HandlerReference(this, method));
        }

        return refs;
    }
}
