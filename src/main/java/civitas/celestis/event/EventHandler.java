package civitas.celestis.event;

import jakarta.annotation.Nonnull;

import java.lang.annotation.*;

/**
 * A marker annotation which marks a method as an event handler.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
    /**
     * Returns the execution priority of this event handler. Defaults to {@link HandlerPriority#MEDIUM}.
     *
     * @return The execution priority of this event handler
     * @see HandlerPriority
     */
    @Nonnull
    HandlerPriority priority() default HandlerPriority.MEDIUM;
}
