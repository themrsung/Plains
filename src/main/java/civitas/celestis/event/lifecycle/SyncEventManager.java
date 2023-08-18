package civitas.celestis.event.lifecycle;

/**
 * A queued synchronous implementation of an event manager.
 * @see EventManager
 */
public class SyncEventManager extends EventThread {
    //
    // Constructors
    //

    /**
     * Creates a new synchronous event manager.
     */
    public SyncEventManager() {
        super("SyncEventManager");
    }
}
