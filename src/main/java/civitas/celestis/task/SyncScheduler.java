package civitas.celestis.task;

import jakarta.annotation.Nonnull;

/**
 * A perfectly synchronized scheduler with only one thread.
 *
 * @see Task
 * @see Scheduler
 */
public class SyncScheduler extends SchedulerThread {
    /**
     * Creates a new synchronous scheduler.
     */
    public SyncScheduler() {
        super("SyncScheduler");
    }

    /**
     * Creates a new synchronous scheduler.
     *
     * @param name The name of this module
     */
    public SyncScheduler(@Nonnull String name) {
        super(name);
    }
}
