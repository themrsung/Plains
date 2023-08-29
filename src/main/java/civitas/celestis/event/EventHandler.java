package civitas.celestis.event;

import jakarta.annotation.Nonnull;

import java.lang.annotation.*;

/**
 * A marker annotation which marks a method as an event handler.
 * Event handlers must take only one parameter, which is a subtype of {@link Handleable}.
 *
 * @see Handleable
 * @see Event
 * @see Listener
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
    /**
     * Returns the execution priority of this event handler.
     * When no priority is specified, this will return {@link HandlerPriority#MEDIUM}.
     *
     * @return The execution priority of this event handler
     */
    @Nonnull
    HandlerPriority priority() default HandlerPriority.MEDIUM;
}
