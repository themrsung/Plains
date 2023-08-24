package civitas.celestis.event;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

/**
 * The default implementation of {@link Handleable}. This class serves as the
 * superclass for all events.<br><br>
 * <b>Serialization Standards</b>
 * <p>
 * This section summarizes the standards subclasses of this class should meet
 * in order to be properly serialized by {@link Events#toString(Handleable)}.
 * If these standards are to be ignored, {@link #toString()} must be overridden
 * to properly print a string representation of the specific event. The default
 * serialization standards will be used if not overridden.
 * </p>
 * <p>
 * All fields will be serialized, along with their values separated by an
 * equals sign. ({@code "="}) If the field is not public, the serializer will
 * attempt to find its getter method. The getter method should be in the format
 * of {@code "get"} followed by its field's name with its first letter capitalized.
 * Getter methods must also have no parameters.
 * </p>
 * <p>
 * For example, the getter method for a variable {@code content} would be {@code getContent()},
 * which takes no parameters and returns the type of {@code content}.<br>
 * <code>
 * protected final String content;<br>
 * public String getContent() {<br>
 * &nbsp; return content;<br>
 * }
 * </code>
 * </p>
 * <p>
 * For {@code boolean}s, the getter convention is {@code "is"} followed by the field's
 * name with its first letter capitalized. (e.g. {@code cancelled} will be {@code isCancelled()})
 * </p>
 * <p>
 * As long as these standards are met, the serializer will automatically serialize
 * the event, eliminating the need to implement {@link #toString()} for every event.
 * </p>
 *
 * @see Handleable
 * @see CancellableEvent
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
        return Events.toString(this);
    }
}
