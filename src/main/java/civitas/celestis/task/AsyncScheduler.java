package civitas.celestis.task;

import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * An asynchronous scheduler with multiple threads.
 *
 * @see Task
 * @see Scheduler
 */
public class AsyncScheduler implements Scheduler {
    //
    // Constants
    //

    /**
     * The default number of threads initialized when no parameter is specified.
     */
    private static final int DEFAULT_THREAD_COUNT = 4;

    //
    // Constructors
    //

    /**
     * Creates a new asynchronous scheduler.
     *
     * @see #DEFAULT_THREAD_COUNT
     */
    public AsyncScheduler() {
        this(DEFAULT_THREAD_COUNT);
    }

    /**
     * Creates a new asynchronous scheduler.
     *
     * @param n The number of threads to initialize
     */
    public AsyncScheduler(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("A scheduler cannot have " + n + " threads.");
        }

        this.threads = new ConcurrentLinkedDeque<>();

        for (int i = 0; i < n; i++) {
            threads.add(new SchedulerThread("AsyncScheduler-" + (i + 1)));
        }
    }

    //
    // Variables
    //

    /**
     * The internally used queue of threads.
     */
    @Nonnull
    private final Deque<SchedulerThread> threads;

    //
    // Thread Management
    //

    /**
     * Polls the next thread from the queue, adds it back to the queue,
     * then returns a reference to the polled thread.
     *
     * @return The next thread in the queue
     */
    protected synchronized SchedulerThread nextThread() {
        final SchedulerThread next = threads.pollFirst();
        threads.addLast(next);
        return next;
    }

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
        nextThread().register(task);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks An iterable object containing the tasks to register synchronously
     */
    @Override
    public void registerSync(@Nonnull Iterable<? extends Task> tasks) {
        nextThread().registerSync(tasks);
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
        threads.forEach(t -> t.unregister(task));
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks An iterable object containing the tasks to unregister from this scheduler
     */
    @Override
    public void unregister(@Nonnull Iterable<? extends Task> tasks) {
        threads.forEach(t -> t.unregister(tasks));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        threads.forEach(SchedulerThread::start);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void interrupt() {
        threads.forEach(SchedulerThread::interrupt);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<? extends Thread> getThreads() {
        return Tuple.copyOf(threads);
    }
}
