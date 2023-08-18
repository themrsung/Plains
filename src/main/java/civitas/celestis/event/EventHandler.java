package civitas.celestis.event;

import jakarta.annotation.Nonnull;

import java.lang.annotation.*;

/**
 * A marker annotation which marks a method as an event handler method.
 * Event handler methods should only have one parameter which is assignable
 * from the type {@link Handleable}.
 * @see Event
 * @see Listener
 * @see HandlerPriority
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
    /**
     * Returns the priority of this event handler. Defaults to {@link HandlerPriority#MEDIUM}.
     * @return The execution priority of this event handler
     */
    @Nonnull
    HandlerPriority priority() default HandlerPriority.MEDIUM;
}
