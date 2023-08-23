package civitas.celestis.event;

import civitas.celestis.util.Unique;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

/**
 * A marker interface which marks a class as being handleable by an event manager.
 */
public interface Handleable extends Unique<UUID> {
    /**
     * Returns the unique identifier of this event.
     *
     * @return The unique identifier of this event
     */
    @Nonnull
    @Override
    UUID getUniqueId();

    /**
     * Returns the cause of this event. If no cause was specified, this will return {@code null}.
     *
     * @return The cause of this event if specified, {@code null} if not
     */
    @Nullable
    Handleable getCause();
}
