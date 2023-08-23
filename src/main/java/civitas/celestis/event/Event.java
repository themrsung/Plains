package civitas.celestis.event;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

/**
 * The default implementation of {@link Handleable}. This class serves as the
 * superclass for all events.
 *
 * @see Handleable
 * @see Listener
 * @see EventHandler
 */
public class Event implements Handleable {
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
     * Creates a new event with no cause.
     *
     * @param uniqueId The unique identifier of this event
     */
    public Event(@Nonnull UUID uniqueId) {
        this(uniqueId, null);
    }

    /**
     * Creates a new event with a random unique identifier.
     *
     * @param cause The cause of this event
     */
    public Event(@Nullable Handleable cause) {
        this(UUID.randomUUID(), cause);
    }

    /**
     * Creates a new event.
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
     * The cause of this event.
     */
    @Nullable
    protected final Handleable cause;

    //
    // Methods
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
     * Serializes this event into a string for debugging purposes.
     * This should be customized for events which add extra parameters.
     *
     * @return The string representation of this event
     */
    @Nonnull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "uniqueId=" + uniqueId + ", " +
                "cause=" + cause +
                "}";
    }
}
