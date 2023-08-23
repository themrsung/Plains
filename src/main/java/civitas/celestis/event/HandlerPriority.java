package civitas.celestis.event;

/**
 * The execution priority of an event handler. Lower enum ordinals are
 * guaranteed to be executed earlier than higher enum ordinals.
 *
 * @see Handleable
 * @see Event
 * @see EventHandler
 * @see Listener
 */
public enum HandlerPriority {
    /**
     * <b>A reserved priority for application critical tasks.</b>
     * <p>
     * This priority should only be used by event handlers which alter the
     * control flow of the main thread.
     * </p>
     */
    INITIALIZATION,

    /**
     * The lowest priority trivial tasks should have.
     */
    PREEMPTIVE,

    PRE_EARLY,

    EARLY,

    POST_EARLY,

    /**
     * The default priority when no priority is specified.
     */
    MEDIUM,

    PRE_LATE,

    LATE,

    POST_LATE,

    /**
     * The highest priority trivial tasks should have.
     */
    PERMISSIVE,

    /**
     * <b>A reserved priority for application critical tasks.</b>
     * <p>
     * This priority should only be used for event handlers which alter the
     * control flow of the main thread.
     * </p>
     */
    TERMINAL
}
