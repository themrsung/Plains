package civitas.celestis.task;

import civitas.celestis.task.lifecycle.Scheduler;
import jakarta.annotation.Nonnull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A delayed task which is only meant to be executed once. No matter
 * how many schedulers this task is registered to, it is guaranteed that
 * it will only be executed once throughout its lifetime.
 * <p>
 * Delayed tasks can be extended, but is not recommended. The intended
 * way of using a delayed task is by calling one of its constructors
 * and providing either a {@link Runnable} or a {@link Consumer}.
 * The latter will be provided with the task's delta. (the actual time
 * it took between registration and execution in milliseconds)
 * </p>
 *
 * @see Task
 */
public class DelayedTask implements Task {
    //
    // Constructors
    //

    /**
     * Creates a new delayed task.
     *
     * @param action The action to execute when the delay has passed
     * @param delay  The delay of this task in milliseconds
     */
    public DelayedTask(@Nonnull Runnable action, long delay) {
        this(delta -> action.run(), delay);
    }

    /**
     * Creates a new delayed task.
     *
     * @param action The action to execute when the delay has passed
     * @param delay  The delay of this task in milliseconds
     */
    public DelayedTask(@Nonnull Consumer<Long> action, long delay) {
        this.action = action;
        this.schedulers = new HashSet<>();
        this.executed = false;
        this.delay = delay;
    }

    //
    // Variables
    //

    /**
     * The action to execute when the delay has passed.
     */
    @Nonnull
    private final Consumer<Long> action;

    /**
     * The set of schedulers this task is registered to.
     */
    @Nonnull
    private final Set<Scheduler> schedulers;

    /**
     * Whether this task has been executed.
     */
    private volatile boolean executed;

    /**
     * The delay of this task in milliseconds.
     */
    private final long delay;

    //
    // Execution
    //

    /**
     * Handles the execution of this task. It first checks if this task has already
     * been executed. If it has not been executed, it executed the stored action, then
     * sets the executed flag to {@code true}. If not, it simply continues. It then
     * proceeds to unregister itself from all registered schedulers, as it no longer
     * requires to be registered to any scheduler. (it will do nothing)
     *
     * @param delta The duration between the last execution and now in milliseconds
     */
    @Override
    public final synchronized void execute(long delta) {
        if (!executed) { // Ensures that this task will only ever be executed once
            // Execute the stored action
            action.accept(delta);
            executed = true;
        }

        // Unregisters itself from all registered schedulers
        for (final Scheduler scheduler : Set.copyOf(schedulers)) {
            scheduler.unregister(this);
        }

        // Clear scheduler references, as they are no longer needed
        schedulers.clear();
    }

    //
    // Delay
    //

    /**
     * The interval will serve as the delay of this task.
     *
     * @return The delay of this task in milliseconds
     */
    @Override
    public final long interval() {
        return delay;
    }

    //
    // Registration
    //

    /**
     * Adds a reference to the scheduler to the internal set of schedulers.
     *
     * @param scheduler The scheduler this task was registered to
     */
    @Override
    public final void onRegistered(@Nonnull Scheduler scheduler) {
        schedulers.add(scheduler);
    }

    /**
     * Removes a reference to the scheduler from the internal set of schedulers.
     *
     * @param scheduler The scheduler this task was unregistered from
     */
    @Override
    public final void onUnregistered(@Nonnull Scheduler scheduler) {
        schedulers.remove(scheduler);
    }
}
