package civitas.celestis.task;

/**
 * Defines the contract of a task, which can be regularly executed by a scheduler.
 *
 * @see Scheduler
 */
public interface Task {
    /**
     * Executes this task.
     *
     * @param delta The duration between the last execution and now in milliseconds
     * @throws RuntimeException When an exception occurs during the execution of this task
     */
    void execute(long delta) throws RuntimeException;

    /**
     * Returns the interval of this task in milliseconds.
     *
     * @return The interval of this task in milliseconds
     */
    default long interval() {
        return DEFAULT_INTERVAL;
    }

    /**
     * The default interval of a task in milliseconds.
     */
    long DEFAULT_INTERVAL = 50;
}
