package civitas.celestis.event;

/**
 * The execution priority of an event handler.
 * @see EventHandler
 */
public enum HandlerPriority {
    PREEMPTIVE,
    EARLY,
    MEDIUM,
    LATE,
    PERMISSIVE,

    /**
     * <b>A RESERVED PRIORITY FOR TERMINATION RELATED TASKS</b>
     * <p>
     *     This priority should only be used for event handlers which process
     *     the termination of an application after every other handler has
     *     finished processing the event of interest.
     * </p>
     */
    TERMINATION;
}
