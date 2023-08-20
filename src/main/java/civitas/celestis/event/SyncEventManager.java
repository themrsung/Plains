package civitas.celestis.event;

import jakarta.annotation.Nonnull;

/**
 * A perfectly synchronized event manager with only one thread.
 *
 * @see Event
 * @see EventHandler
 * @see Listener
 * @see EventThread
 */
public class SyncEventManager extends EventThread {
    /**
     * Creates a new synchronous event manager.
     */
    public SyncEventManager() {
        super("SyncEventManager");
    }

    /**
     * Creates a new synchronous event manager.
     *
     * @param name The name of this module
     */
    public SyncEventManager(@Nonnull String name) {
        super(name);
    }
}
