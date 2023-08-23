package civitas.celestis.event.notification;

import civitas.celestis.event.Event;
import civitas.celestis.event.Handleable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An event called to notify the entire application that a certain action
 * has been performed. All subclasses of this event will be sent to every
 * listener which listens to {@link NotificationEvent}.
 *
 * @see Event
 */
public class NotificationEvent extends Event {
    //
    // Constructors
    //

    /**
     * Creates a new notification event with no message and no cause.
     */
    public NotificationEvent() {
        this("");
    }

    /**
     * Creates a new notification event with no message.
     *
     * @param cause The cause of this event
     */
    public NotificationEvent(@Nullable Handleable cause) {
        this("", cause);
    }

    /**
     * Creates a new notification event with no cause.
     *
     * @param message The message of this notification
     */
    public NotificationEvent(@Nonnull String message) {
        this.message = message;
    }

    /**
     * Creates a new notification event.
     *
     * @param message The message of this notification
     * @param cause   The cause of this event
     */
    public NotificationEvent(@Nonnull String message, @Nullable Handleable cause) {
        super(cause);
        this.message = message;
    }

    //
    // Variables
    //

    /**
     * The message of this notification. If no message is specified,
     * the value will be an empty string. ("")
     */
    @Nonnull
    protected final String message;

    //
    // Getters
    //

    /**
     * Returns the message of this notification. If no message is specified,
     * this will return "".
     *
     * @return The message of this notification
     */
    @Nonnull
    public String getMessage() {
        return message;
    }


    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "uniqueId=" + uniqueId + ", " +
                "cause=" + (cause != null ? cause.getUniqueId() : "null") + ", " +
                "message='" + message + "'" +
                "}";
    }
}
