package civitas.celestis.task;

import civitas.celestis.util.ThreadedModule;
import jakarta.annotation.Nonnull;

/**
 * A scheduler. Handles the lifecycle of tasks.
 *
 * @see Task
 * @see SyncScheduler
 * @see AsyncScheduler
 */
public interface Scheduler extends ThreadedModule {
    //
    // Tasks
    //

    /**
     * Registers a task to this scheduler.
     *
     * @param task The task to register to this scheduler
     */
    void register(@Nonnull Task task);

    /**
     * Registers multiple tasks to this scheduler synchronously, instructing it to register every
     * task provided to a single thread in order to guarantee sequential execution.
     *
     * @param tasks An iterable object containing the tasks to register synchronously
     */
    void registerSync(@Nonnull Iterable<? extends Task> tasks);

    /**
     * Registers multiple tasks to this scheduler asynchronously, instructing it to distribute
     * every task provided evenly across every thread of this scheduler.
     *
     * @param tasks An iterable object containing the tasks to register asynchronously
     */
    void registerAsync(@Nonnull Iterable<? extends Task> tasks);

    /**
     * Unregisters a task from this scheduler.
     *
     * @param task The task to unregister from this scheduler
     */
    void unregister(@Nonnull Task task);

    /**
     * Unregisters multiple tasks from this scheduler.
     *
     * @param tasks An iterable object containing the tasks to unregister from this scheduler
     */
    void unregister(@Nonnull Iterable<? extends Task> tasks);
}
