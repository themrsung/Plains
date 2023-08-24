package civitas.celestis.task.lifecycle;

import civitas.celestis.task.Task;
import civitas.celestis.util.Module;
import jakarta.annotation.Nonnull;

/**
 * A scheduler. Handles the registration and execution of tasks.
 * Scheduler can execute tasks on a regular basis. Scheduler are categorized
 * by two criteria: whether it is synchronous, and whether it is strict.
 * <p>
 * Synchronous schedulers only have one thread, (usually the scheduler itself)
 * and thus can only execute tasks sequentially. On the other hand, asynchronous
 * schedulers are capable of multithreaded operation.
 * </p>
 * <p>
 * Strict schedulers have no delay between each iteration, adhering to the
 * interval of the tasks in a strict manner. Non-strict or lazy schedulers sleep
 * after each iteration, which can help to avoid unnecessary iterations of
 * looping through the list of tasks pointlessly. (since a task will only be
 * executed after the interval of the task has passed)
 * </p>
 * <p>
 * Which implementation is used should vary between applications, selecting
 * the appropriate implementation depending on the application's nature.
 * </p>
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
