package civitas.celestis.task.lifecycle;

import civitas.celestis.task.Task;
import civitas.celestis.util.Module;
import jakarta.annotation.Nonnull;

/**
 * A scheduler. Handles the registration and execution of tasks.
 *
 * @see Task
 * @see SyncScheduler
 * @see AtomicScheduler
 */
public interface Scheduler extends Module {
    /**
     * Registers a task to this scheduler, instructing it to start
     * executing it as soon as possible.
     *
     * @param task The task to register to this scheduler
     */
    void register(@Nonnull Task task);

    /**
     * Registers multiple tasks synchronously to this scheduler, instructing it to
     * put every provided task into a single thread to ensure sequential execution.
     *
     * @param tasks The iterable object containing the tasks to register
     */
    void registerSync(@Nonnull Iterable<? extends Task> tasks);

    /**
     * Registers multiple tasks asynchronously to this scheduler, instructing it to
     * distribute the tasks across multiple threads however it sees fit.
     * Sequential execution is not guaranteed, and this is equivalent to iteratively
     * calling {@link #register(Task)} for each task.
     *
     * @param tasks The iterable object containing the tasks to register
     */
    void registerAsync(@Nonnull Iterable<? extends Task> tasks);

    /**
     * Unregisters a task from this scheduler, instructing it to stop executing it.
     *
     * @param task The task to unregister from this scheduler
     */
    void unregister(@Nonnull Task task);

    /**
     * Unregisters multiple tasks from this scheduler, instructing it to stop executing
     * every task within the provided iterable object of tasks.
     *
     * @param tasks The iterable object containing the tasks to unregister
     */
    void unregister(@Nonnull Iterable<? extends Task> tasks);
}
