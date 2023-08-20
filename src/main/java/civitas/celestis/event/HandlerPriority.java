package civitas.celestis.event;

/**
 * The priority of an event handler.
 *
 * @see EventHandler
 */
public enum HandlerPriority {
    PREEMPTIVE,
    EARLY,
    MEDIUM,
    LATE,
    PERMISSIVE,

    //
    //
    // OFF LIMITS
    //
    //

    /**
     * <b>THIS IS A RESERVED PRIORITY FOR TERMINATION RELATED TASKS</b>
     * <p>
     * Do not use this priority for trivial event processing. This is reserved for
     * event handlers which handle the termination of an application which need
     * to be called after every other handler has finished processing the event.
     * </p>
     */
    TERMINATION
}
