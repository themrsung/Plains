package civitas.celestis.event;

import civitas.celestis.util.Unique;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

/**
 * A handleable object can be thrown into an event manager to be handled.
 * This interface provides the contract for an event.
 *
 * @see Event
 */
public interface Handleable extends Unique<UUID> {
    /**
     * Returns the unique identifier of this event.
     * Unique identifiers should be unique within the lifecycle of this application instance.
     *
     * @return The unique identifier of this event
     */
    @Nonnull
    @Override
    UUID getUniqueId();

    /**
     * Returns the cause of this event.
     * If no cause was specified, this will return {@code null}.
     *
     * @return The cause of this event if specified, {@code null} if not
     */
    @Nullable
    Handleable getCause();
}
