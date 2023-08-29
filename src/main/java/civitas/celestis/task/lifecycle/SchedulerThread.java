package civitas.celestis.task.lifecycle;

import civitas.celestis.task.Task;
import jakarta.annotation.Nonnull;

import java.io.PrintStream;
import java.util.*;

/**
 * A thread which handles the registration and execution of tasks.
 * This is a strict scheduler, meaning it has no delay between each iteration
 * of looping through the list of tasks allocated to this thread.
 */
public class SchedulerThread extends Thread implements Scheduler {
    //
    // Constructors
    //

    /**
     * Creates a new scheduler thread.
     */
    public SchedulerThread() {
        this("SchedulerThread", System.out);
    }

    /**
     * Creates a new scheduler thread.
     *
     * @param name The name of this thread
     */
    public SchedulerThread(@Nonnull String name) {
        this(name, System.out);
    }

    /**
     * Creates a new scheduler thread.
     *
     * @param name        The name of this thread
     * @param printStream The print stream to print error messages to
     */
    public SchedulerThread(@Nonnull String name, @Nonnull PrintStream printStream) {

        /*
         * Since this constructor is used to create a scheduler thread which uses an independent
         * task list and execution time map, the list and map instances do not
         * have to be concurrent. This ensures that there is no unnecessary overhead.
         */

        this(name, new ArrayList<>(), new HashMap<>(), printStream);
    }

    /**
     * Creates a new scheduler thread. If this scheduler shares the list
     * of tasks and/or the map of execution times with another thread, the
     * list and map instances must be thread-safe. They must also be mutable.
     *
     * @param name           The name of this thread
     * @param tasks          The list of tasks allocated to this thread
     * @param executionTimes The map of recent execution times
     * @param printStream    The print stream to print error messages to
     */
    protected SchedulerThread(
            @Nonnull String name,
            @Nonnull List<Task> tasks,
            @Nonnull Map<Task, Long> executionTimes,
            @Nonnull PrintStream printStream
    ) {
        super(() -> {

            //
            // START OF SCHEDULER THREAD
            //
            // Created: v0.4
            // Modified: v0.4
            //

            // Enter infinite loop while thread is active
            while (!Thread.interrupted()) {

                // Iterate through list of tasks
                for (final Task task : List.copyOf(tasks)) {

                    // Calculate the delta of this task
                    final long now = System.currentTimeMillis();
                    final long previous = executionTimes.getOrDefault(task, now);
                    final long delta = now - previous;

                    // Respect the task's interval
                    if (delta < task.interval()) continue;

                    try {

                        // Execute the task and keep time
                        task.execute(delta);
                        executionTimes.put(task, now);

                    } catch (final Throwable e) {

                        // Print errors to print stream
                        e.printStackTrace(printStream);

                    }
                }
            }

            //
            // END OF SCHEDULER THREAD
            //

        }, name);

        // Assign the variables
        this.tasks = tasks;
        this.executionTimes = executionTimes;
    }


    //
    // Variables
    //

    /**
     * The list of tasks.
     */
    @Nonnull
    private final List<Task> tasks;

    /**
     * The map of execution times.
     */
    @Nonnull
    private final Map<Task, Long> executionTimes;

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
        Objects.requireNonNull(task);

        tasks.add(task);
        executionTimes.put(task, System.currentTimeMillis());

        task.onRegistered(this);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks The iterable object containing the tasks to register
     */
    @Override
    public void registerSync(@Nonnull Iterable<? extends Task> tasks) {
        tasks.forEach(this::register);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks The iterable object containing the tasks to register
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
        Objects.requireNonNull(task);

        tasks.remove(task);
        executionTimes.remove(task);

        task.onUnregistered(this);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks The iterable object containing the tasks to unregister
     */
    @Override
    public void unregister(@Nonnull Iterable<? extends Task> tasks) {
        Objects.requireNonNull(tasks);
        tasks.forEach(this::unregister);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        // Nothing to do here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void terminate() {
        interrupt();
    }
}
