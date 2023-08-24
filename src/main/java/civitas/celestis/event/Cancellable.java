package civitas.celestis.event;

/**
 * An event which can be cancelled by a lower priority listener, which flags
 * the event as cancelled, notifying higher priority listeners that this event
 * should be ignored as if it had not happened in the first place.
 * <p>
 * A {@link CancellableEvent default implementation} is provided for easier usage.
 * </p>
 *
 * @see Handleable
 * @see Event
 * @see CancellableEvent
 */
public interface Cancellable extends Handleable {
    /**
     * Returns whether this event has been flagged as cancelled.
     *
     * @return {@code true} if this event has been flagged as cancelled
     */
    boolean isCancelled();

    /**
     * Sets whether this event has been flagged as cancelled.
     *
     * @param cancelled Whether this event has been cancelled
     */
    void setCancelled(boolean cancelled);
}
