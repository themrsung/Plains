package civitas.celestis.task;

import jakarta.annotation.Nonnull;

/**
 * A registry of a task. Holds contextual data required to process tasks,
 * as well as a reference to the task itself.
 *
 * @see Task
 */
public class TaskRegistry {
    //
    // Constructors
    //

    /**
     * Creates a new task registry with the current system time as its last execution time.
     *
     * @param task The task of which to hold in this registry
     */
    public TaskRegistry(@Nonnull Task task) {
        this(task, System.currentTimeMillis());
    }

    /**
     * Creates a new task registry.
     *
     * @param task              The task of which to hold in this registry
     * @param lastExecutionTime The last execution time of this task
     */
    public TaskRegistry(@Nonnull Task task, long lastExecutionTime) {
        this.task = task;
        this.lastExecutionTime = lastExecutionTime;
    }

    //
    // Variables
    //

    /**
     * A reference to the task this registry holds.
     */
    @Nonnull
    protected final Task task;

    /**
     * The time at which this task was most recently executed at.
     */
    protected long lastExecutionTime;

    //
    // Methods
    //

    /**
     * Returns a reference to the task this registry is holding.
     *
     * @return The task this registry is holding
     */
    @Nonnull
    public Task getTask() {
        return task;
    }

    /**
     * Returns the last execution time of this task.
     *
     * @return The last execution time of this task
     */
    public long getLastExecutionTime() {
        return lastExecutionTime;
    }

    /**
     * Execute this task if possible. (the delta is greater than or equal to the task's interval)
     *
     * @throws RuntimeException When an exception occurs during the task's execution
     */
    public void execute() throws RuntimeException {
        // Calculate delta
        final long now = System.currentTimeMillis();
        final long delta = now - lastExecutionTime;

        // Respect the task's interval
        if (delta < task.interval()) return;

        // Execute task and keep time
        task.execute(delta);
        lastExecutionTime = now;
    }

    /**
     * Forcefully executes this task, without respecting its interval.
     *
     * @throws RuntimeException When an exception occurs during the task's execution
     */
    public void forceExecute() throws RuntimeException {
        // Calculate delta
        final long now = System.currentTimeMillis();
        final long delta = now - lastExecutionTime;

        // Execute task and keep time
        task.execute(delta);
        lastExecutionTime = now;
    }
}
