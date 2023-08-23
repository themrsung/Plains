package civitas.celestis.task.lifecycle;

import jakarta.annotation.Nonnull;

import java.io.PrintStream;

/**
 * A synchronous scheduler with one processing thread, (the scheduler itself)
 * one list of tasks, and one map of execution times. All core logic is inherited
 * from {@link SchedulerThread}.
 * @see Scheduler
 * @see SchedulerThread
 */
public class SyncScheduler extends SchedulerThread {
    //
    // Constructors
    //

    /**
     * Creates a new synchronous scheduler.
     */
    public SyncScheduler() {
        this(System.out);
    }

    /**
     * Creates a new synchronous scheduler.
     * @param printStream The print stream to print error messages to
     */
    public SyncScheduler(@Nonnull PrintStream printStream) {
        super("SyncScheduler", printStream);
    }
}
