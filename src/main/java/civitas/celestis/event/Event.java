package civitas.celestis.event;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

/**
 * The base class for an event.
 *
 * @see Handleable
 * @see EventHandler
 * @see Listener
 * @see EventManager
 */
public abstract class Event implements Handleable {
    //
    // Constructors
    //

    /**
     * Creates a new event with a random unique identifier and no cause.
     */
    public Event() {
        this(UUID.randomUUID(), null);
    }

    /**
     * Creates a new event by specifying a unique identifier with no cause.
     *
     * @param uniqueId The unique identifier of this event
     */
    public Event(@Nonnull UUID uniqueId) {
        this(uniqueId, null);
    }

    /**
     * Creates a new event with a random unique identifier and a specified cause.
     *
     * @param cause The cause of this event
     */
    public Event(@Nullable Handleable cause) {
        this(UUID.randomUUID(), cause);
    }

    /**
     * Creates a new event by specifying a unique identifier and a cause.
     *
     * @param uniqueId The unique identifier of this event
     * @param cause    The cause of this event
     */
    public Event(@Nonnull UUID uniqueId, @Nullable Handleable cause) {
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
     * The cause of this event if specified, {@code null} if not.
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
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return getClass().getSimpleName() +
                "{uniqueId=" + getUniqueId() + ", " +
                "cause=" + getCause() +
                "}";
    }
}
