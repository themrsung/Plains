package civitas.celestis.event.lifecycle;

import civitas.celestis.event.EventHandler;
import civitas.celestis.event.Handleable;
import civitas.celestis.event.HandlerPriority;
import civitas.celestis.event.Listener;
import jakarta.annotation.Nonnull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A transient intermediary reference to an event handler.
 * This stores all relevant information required to invoke an event handler.
 *
 * @param listener The event listener object this handler is contained within
 * @param method   The event handler method to invoke
 */
public record HandlerReference(@Nonnull Listener listener, @Nonnull Method method) {
    /**
     * Returns the priority of this handler.
     *
     * @return The priority of this handler
     */
    @Nonnull
    public HandlerPriority priority() {
        return method.getAnnotation(EventHandler.class).priority();
    }

    /**
     * Checks if this handler accepts the provided event {@code e}.
     *
     * @param e   The event to check for acceptance
     * @param <E> The type of event to check for acceptance
     * @return {@code true} if this handler accepts the event as a parameter
     */
    public <E extends Handleable> boolean accepts(@Nonnull E e) {
        return method.getParameterTypes()[0].isAssignableFrom(e.getClass());
    }

    /**
     * Called to handle the provided event {@code e}.
     *
     * @param e   The event to handle
     * @param <E> The type of event to handle
     * @throws IllegalAccessException    When the method is inaccessible by the invoker
     * @throws InvocationTargetException When an exception occurs during the handling of the event
     */
    public <E extends Handleable> void handle(@Nonnull E e)
            throws IllegalAccessException, InvocationTargetException {
        method.invoke(listener, e);
    }
}
