package civitas.celestis.task;

import civitas.celestis.task.lifecycle.Scheduler;
import jakarta.annotation.Nonnull;

/**
 * Defines the contract of a task which can be registered and executed
 * by a scheduler. Tasks are provided with a delta each execution, which is
 * the amount of time between the last execution and now in milliseconds.
 * <p>
 * All methods excluding {@link #execute(long)} are default, making this
 * interface declarable by use of a lambda expression. This is an intended feature,
 * and this will continue to be the only abstract method in the future.
 * </p>
 */
public interface Task {
    //
    // Execution
    //

    /**
     * Executes this task.
     *
     * @param delta The duration between the last execution and now in milliseconds
     */
    void execute(long delta);

    //
    // Interval
    //

    /**
     * The default interval of a task in milliseconds.
     */
    long DEFAULT_INTERVAL = 50;

    /**
     * Returns the interval of this task.
     *
     * @return The interval of this task in milliseconds
     */
    default long interval() {
        return DEFAULT_INTERVAL;
    }

    //
    // Events
    //

    /**
     * Called upon registration to a scheduler.
     *
     * @param scheduler The scheduler this task was registered to
     */
    default void onRegistered(@Nonnull Scheduler scheduler) {}

    /**
     * Called upon unregistration from a scheduler.
     *
     * @param scheduler The scheduler this task was unregistered from
     */
    default void onUnregistered(@Nonnull Scheduler scheduler) {}
}
