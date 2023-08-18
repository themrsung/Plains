package civitas.celestis.event;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

/**
 * This class defines the contract for an event.
 *
 * @see Handleable
 * @see EventHandler
 * @see Listener
 */
public class Event implements Handleable {
    //
    // Constructors
    //

    /**
     * Creates a new event with a random unique identifier.
     *
     * @param cause The cause of this event
     */
    public Event(@Nullable Handleable cause) {
        this(UUID.randomUUID(), cause);
    }

    /**
     * Creates a new event with a random unique identifier and no specified cause.
     */
    protected Event() {
        this(UUID.randomUUID(), null);
    }

    /**
     * Creates a new event with a specific unique identifier and no cause.
     *
     * @param uniqueId The unique identifier of this event
     */
    protected Event(@Nonnull UUID uniqueId) {
        this(uniqueId, null);
    }

    /**
     * Creates a new event.
     *
     * @param uniqueId The unique identifier of this event
     * @param cause    The cause of this event
     */
    protected Event(@Nonnull UUID uniqueId, @Nullable Handleable cause) {
        this.uniqueId = uniqueId;
        this.cause = cause;
    }

    //
    // Variables
    //

    /**
     * The unique identifier of this event.
     */
    @Nonnull
    protected final UUID uniqueId;

    /**
     * The cause of this event.
     */
    @Nullable
    protected final Handleable cause;

    //
    // Getters
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public UUID getUniqueId() {
        return uniqueId;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nullable
    public Handleable getCause() {
        return cause;
    }

    //
    // Serialization
    //

    /**
     * Serializes this event into a string.
     *
     * @return The string representation of this event
     */
    @Override
    @Nonnull
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "uniqueId=" + uniqueId +
                ", cause=" + cause +
                '}';
    }
}
