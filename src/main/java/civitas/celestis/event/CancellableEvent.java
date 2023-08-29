package civitas.celestis.event;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.UUID;

/**
 * An event which can be cancelled by a lower priority listener, which flags
 * the event as cancelled, notifying higher priority listeners that this event
 * should be ignored as if it had not happened in the first place.
 *
 * @see Cancellable
 * @see Event
 */
public class CancellableEvent extends Event implements Cancellable {
    //
    // Constructors
    //

    /**
     * Creates a new cancellable event with a random unique identifier and no cause.
     */
    public CancellableEvent() {
    }

    /**
     * Creates a new cancellable event with no cause.
     *
     * @param uniqueId The unique identifier of this event
     */
    public CancellableEvent(@Nonnull UUID uniqueId) {
        super(uniqueId);
    }

    /**
     * Creates a new cancellable event with a random unique identifier.
     *
     * @param cause The cause of this event
     */
    public CancellableEvent(@Nullable Handleable cause) {
        super(cause);
    }

    /**
     * Creates a new cancellable event.
     *
     * @param uniqueId The unique identifier of this event
     * @param cause    The cause of this event
     */
    public CancellableEvent(@Nonnull UUID uniqueId, @Nullable Handleable cause) {
        super(uniqueId, cause);
    }

    //
    // Variables
    //

    /**
     * Whether this event has been marked as cancelled.
     */
    private volatile boolean cancelled = false;

    //
    // Getters
    //

    /**
     * Returns whether this event has been flagged as cancelled.
     *
     * @return {@code true} if this event has been flagged as cancelled
     */
    @Override
    public synchronized boolean isCancelled() {
        return cancelled;
    }

    //
    // Setters
    //

    /**
     * Sets whether this event has been flagged as cancelled.
     *
     * @param cancelled Whether this event has been cancelled
     */
    @Override
    public synchronized void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
