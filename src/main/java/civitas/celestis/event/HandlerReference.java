package civitas.celestis.event;

import jakarta.annotation.Nonnull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A reference to an event handler.
 *
 * @param listener The listener object
 * @param method   The handler method
 * @see EventHandler
 * @see Listener
 */
public record HandlerReference(@Nonnull Listener listener, @Nonnull Method method) {
    /**
     * Returns the event listener object this handler is referencing to.
     *
     * @return The event listener object this handler is referencing to
     */
    @Override
    @Nonnull
    public Listener listener() {
        return listener;
    }

    /**
     * Returns the method this handler is referencing.
     *
     * @return The method this handler is referencing
     */
    @Override
    @Nonnull
    public Method method() {
        return method;
    }

    /**
     * Returns the execution priority of this handler.
     *
     * @return The execution priority of this handler
     * @see HandlerPriority
     */
    @Nonnull
    public HandlerPriority priority() {
        return method.getAnnotation(EventHandler.class).priority();
    }

    /**
     * Returns whether the provided event can be safely handled by this handler.
     *
     * @param event The event of which to check for assignment safety
     * @param <E>   The type of event to check for type-safety
     * @return {@code true} if this handler accepts the provided event as its event parameter
     */
    public <E extends Handleable> boolean accepts(@Nonnull E event) {
        return method.getParameterTypes()[0].isAssignableFrom(event.getClass());
    }

    /**
     * Called to handle an event.
     *
     * @param event The event of which to handle
     * @param <E>   The type of event to handle
     * @throws IllegalAccessException    When the event handler method is inaccessible
     *                                   (the access modifier is not {@code public})
     * @throws InvocationTargetException When an exception occurs within the event handler method
     */
    public <E extends Handleable> void handle(@Nonnull E event)
            throws IllegalAccessException, InvocationTargetException {
        method.invoke(listener, event);
    }
}
