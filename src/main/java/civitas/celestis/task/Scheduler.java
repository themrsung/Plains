package civitas.celestis.task;

import civitas.celestis.util.ThreadedModule;
import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;

import java.util.Collection;

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
     * @param tasks A tuple containing the tasks to register synchronously
     */
    void registerSync(@Nonnull Tuple<? extends Task> tasks);

    /**
     * Registers multiple tasks to this scheduler synchronously, instructing it to register every
     * task provided to a single thread in order to guarantee sequential execution.
     *
     * @param tasks A collection containing the tasks to register synchronously
     */
    void registerSync(@Nonnull Collection<? extends Task> tasks);

    /**
     * Registers multiple tasks to this scheduler asynchronously, instructing it to distribute
     * every task provided evenly across every thread of this scheduler.
     *
     * @param tasks A tuple containing the tasks to register asynchronously
     */
    void registerAsync(@Nonnull Tuple<? extends Task> tasks);
    // TODO: 2023-08-21 Iterable

    /**
     * Registers multiple tasks to this scheduler asynchronously, instructing it to distribute
     * every task provided evenly across every thread of this scheduler.
     *
     * @param tasks A collection containing the tasks to register asynchronously
     */
    void registerAsync(@Nonnull Collection<? extends Task> tasks);

    /**
     * Unregisters a task from this scheduler.
     *
     * @param task The task to unregister from this scheduler
     */
    void unregister(@Nonnull Task task);

    /**
     * Unregisters multiple tasks from this scheduler.
     *
     * @param tasks A tuple containing the tasks to unregister from this scheduler
     */
    void unregister(@Nonnull Tuple<? extends Task> tasks);

    /**
     * Unregisters multiple tasks from this scheduler.
     *
     * @param tasks A collection containing the tasks to unregister from this scheduler
     */
    void unregister(@Nonnull Collection<? extends Task> tasks);
}
