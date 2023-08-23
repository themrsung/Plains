package civitas.celestis.event;

import civitas.celestis.exception.event.HandlerException;
import jakarta.annotation.Nonnull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A reference to an event handler. Contains contextual information required
 * to invoke the handler method, and retrieve its properties.
 *
 * @param listener The event listener instance
 * @param method   The event handler method
 * @see Listener
 * @see EventHandler
 */
public record HandlerReference(@Nonnull Listener listener, @Nonnull Method method) {
    /**
     * Returns the execution priority of this event handler.
     *
     * @return The execution priority of this event handler
     * @see HandlerPriority
     */
    @Nonnull
    public HandlerPriority priority() {
        try {
            return method.getAnnotation(EventHandler.class).priority();
        } catch (final NullPointerException e) {
            return HandlerPriority.MEDIUM;
        }
    }

    /**
     * Returns whether this handler accepts the provided event as its parameter.
     *
     * @param event The event of which to check for acceptance
     * @param <E>   The type of event to check for acceptance
     * @return {@code true} if this event handler accepts the event
     */
    public <E extends Handleable> boolean accepts(@Nonnull E event) {
        try {
            return method.getParameterTypes()[0].isAssignableFrom(event.getClass());
        } catch (final IndexOutOfBoundsException e) {
            throw new RuntimeException("An invalid method was referenced as an event handler.", e);
        }
    }

    /**
     * Invokes the handler to process the event.
     *
     * @param event The event of which to handle
     * @param <E>   The type of event to handle
     * @throws HandlerException When an exception occurs during the processing of the event
     */
    public <E extends Handleable> void handle(@Nonnull E event) throws HandlerException {
        try {
            method.invoke(listener, event);
        } catch (final IllegalAccessException e) {
            throw new HandlerException("This handler is not accessible from the event processing thread.", e);
        } catch (final InvocationTargetException e) {
            throw new HandlerException("The handler method threw an exception during the processing of the event.", e);
        }
    }
}
