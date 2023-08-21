package civitas.celestis.task;

import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A scheduler thread.
 */
public class SchedulerThread extends Thread implements Scheduler {
    //
    // Constructors
    //

    /**
     * Creates a new scheduler thread with a randomized name.
     */
    public SchedulerThread() {
        this("SchedulerThread-" + UUID.randomUUID());
    }

    /**
     * Creates a new scheduler thread.
     *
     * @param name The name of this thread
     */
    public SchedulerThread(@Nonnull String name) {
        this(name, new ArrayList<>());
    }

    /**
     * Creates a new scheduler thread.
     *
     * @param name  The name of this thread
     * @param tasks The list of tasks of which this thread should handle
     */
    protected SchedulerThread(@Nonnull String name, @Nonnull List<TaskRegistry> tasks) {
        super(() -> {

            //
            // START OF SCHEDULER THREAD
            //
            // Created: v0.1
            // Last Updated: v0.1
            //

            // Infinitely loop until interrupted
            while (!Thread.interrupted()) {
                // Iterate through tasks
                for (final TaskRegistry task : List.copyOf(tasks)) {

                    try {
                        // Execute task if possible
                        task.execute();
                    } catch (final RuntimeException e) {
                        // Print the stack trace to the default print stream
                        e.printStackTrace();
                    }

                    // Check for interruption
                    if (Thread.interrupted()) return;
                }
            }

            //
            // END OF SCHEDULER THREAD
            //

        }, name);

        // Assign variables
        this.tasks = tasks;
    }

    //
    // Variables
    //

    /**
     * A list of registered tasks.
     */
    @Nonnull
    protected final List<TaskRegistry> tasks;

    //
    // Methods
    //

    /**
     * {@inheritDoc}
     *
     * @param task The task to register to this scheduler
     */
    @Override
    public void register(@Nonnull Task task) {
        tasks.add(new TaskRegistry(task));
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks An iterable object containing the tasks to register synchronously
     */
    @Override
    public void registerSync(@Nonnull Iterable<? extends Task> tasks) {
        tasks.forEach(this::register);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks An iterable object containing the tasks to register asynchronously
     */
    @Override
    public void registerAsync(@Nonnull Iterable<? extends Task> tasks) {
        tasks.forEach(this::register);
    }

    /**
     * {@inheritDoc}
     *
     * @param task The task to unregister from this scheduler
     */
    @Override
    public void unregister(@Nonnull Task task) {
        tasks.removeIf(r -> Objects.equals(r.task, task));
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks An iterable object containing the tasks to unregister from this scheduler
     */
    @Override
    public void unregister(@Nonnull Iterable<? extends Task> tasks) {
        tasks.forEach(this::unregister);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<? extends Thread> getThreads() {
        return Tuple.of(this);
    }
}
