package civitas.celestis.event;

import civitas.celestis.util.Unique;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

/**
 * A marker interface which makes a class callable into an event manager.
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
     * Returns the cause of this event. (the handleable object which triggered the
     * invocation of this event) If no cause was specified, this will return {@code null}.
     *
     * @return The cause of this event if specified, {@code null} if not
     */
    @Nullable
    Handleable getCause();

    /**
     * Serializes this event into a string.
     *
     * @return The string representation of this event
     */
    @Nonnull
    @Override
    String toString();
}
