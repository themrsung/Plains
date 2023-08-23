package civitas.celestis.event.lifecycle;

import jakarta.annotation.Nonnull;

import java.io.PrintStream;

/**
 * A synchronous event manager with one processing thread, (the manager itself)
 * one event queue, and one list of event handlers. All core logic is inherited
 * from {@link EventThread}.
 *
 * @see EventManager
 * @see EventThread
 */
public class SyncEventManager extends EventThread {
    //
    // Constructors
    //

    /**
     * Creates a new synchronous event manager.
     */
    public SyncEventManager() {
        this(System.out);
    }

    /**
     * Creates a new synchronous event manager.
     *
     * @param printStream The print stream to print error messages to
     */
    public SyncEventManager(@Nonnull PrintStream printStream) {
        super("SyncEventManager", printStream);
    }
}
