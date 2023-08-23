package civitas.celestis.task.lifecycle;

import civitas.celestis.task.Task;
import jakarta.annotation.Nonnull;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An asynchronous scheduler with one shared list of tasks and map of execution times.
 * While the execution of tasks are distributed across multiple threads, only one central
 * list of tasks and their corresponding contextual data exists.
 * <p>
 * Communal schedulers are less resilient compare to atomic implementations due to the
 * central resources being shared across multiple threads.
 * </p>
 */
public class CommunalScheduler implements Scheduler {
    //
    // Constructors
    //

    public CommunalScheduler(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("A communal scheduler cannot have fewer than 1 thread.");
        }

        this.tasks = new CopyOnWriteArrayList<>();
        this.executionTimes = new ConcurrentHashMap<>();

        final List<SchedulerThread> temp = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            temp.add(new CommunalSchedulerThread("CommunalScheduler-" + (i + 1), tasks, executionTimes, System.out));
        }

        this.threads = List.copyOf(temp);
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
     * The map of the most recent execution times.
     */
    @Nonnull
    private final Map<Task, Long> executionTimes;

    /**
     * The internal list of threads.
     */
    @Nonnull
    private final List<SchedulerThread> threads;

    //
    // Methods
    //

    @Override
    public void register(@Nonnull Task task) {
        // FIXME: 2023-08-24 fixmefixme fixfjifajfdifdjifdijfsdjoiiiiiiiiiiii
    }

    @Override
    public void registerSync(@Nonnull Iterable<? extends Task> tasks) {

    }

    @Override
    public void registerAsync(@Nonnull Iterable<? extends Task> tasks) {

    }

    @Override
    public void unregister(@Nonnull Task task) {

    }

    @Override
    public void unregister(@Nonnull Iterable<? extends Task> tasks) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void interrupt() {

    }

    @Override
    public void terminate() {

    }


    //
    // Communal Thread
    //

    private static final class CommunalSchedulerThread extends SchedulerThread {
        private CommunalSchedulerThread(
                @Nonnull String name,
                @Nonnull List<Task> tasks,
                @Nonnull Map<Task, Long> executionTimes,
                @Nonnull PrintStream printStream
        ) {
            super(name, tasks, executionTimes, printStream);
        }
    }
}
