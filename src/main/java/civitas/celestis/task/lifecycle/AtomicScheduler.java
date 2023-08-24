package civitas.celestis.task.lifecycle;

import civitas.celestis.task.Task;
import jakarta.annotation.Nonnull;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * An asynchronous multithreaded scheduler with multiple threads, which each have
 * their own list of tasks and map of execution times. Atomic schedulers are atomized
 * in that the failure of one thread will not interfere with the operation of other threads.
 * <p>
 * While atomic schedulers can be resilient compared to other asynchronous schedulers,
 * the added overhead of managing a queue of scheduler threads can make it slower than
 * other asynchronous implementations, especially if tasks are registered frequently.
 * </p>
 *
 * @see Scheduler
 */
public class AtomicScheduler implements Scheduler {
    //
    // Constructors
    //

    /**
     * Creates a new atomic scheduler with the default thread count.
     */
    public AtomicScheduler() {
        this(4);
    }

    /**
     * Creates a new atomic scheduler with {@code n} threads.
     *
     * @param n The number of threads to initialize
     */
    public AtomicScheduler(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("An atomic scheduler cannot have fewer than 1 thread.");
        }

        this.threads = new ConcurrentLinkedDeque<>();

        for (int i = 0; i < n; i++) {
            threads.add(new SchedulerThread("AtomicScheduler-" + (i + 1)));
        }
    }

    /**
     * Creates a new atomic scheduler by directly assigning the queue of threads.
     * This is a dangerous constructor, and only should ever be used by subclasses of this class.
     *
     * @param threads The queue of threads to directly assign
     */
    protected AtomicScheduler(@Nonnull Deque<SchedulerThread> threads) {
        this.threads = threads;
    }

    //
    // Variables
    //

    /**
     * The internal queue of scheduler threads.
     */
    @Nonnull
    private final Deque<SchedulerThread> threads;


    //
    // Methods
    //

    /**
     * Polls a thread from the queue, stores a reference to it temporarily,
     * adds it back to the end of the queue, then returns the stored reference.
     * This is the equivalent of a {@code ++} operator on a circular queue.
     *
     * @return The next thread in the queue
     */
    protected synchronized SchedulerThread nextThread() {
        final SchedulerThread next = threads.pollFirst();
        threads.addLast(next);
        return next;
    }

    /**
     * {@inheritDoc}
     *
     * @param task The task to register to this scheduler
     */
    @Override
    public synchronized void register(@Nonnull Task task) {
        nextThread().register(task);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks The iterable object containing the tasks to register
     */
    @Override
    public synchronized void registerSync(@Nonnull Iterable<? extends Task> tasks) {
        final SchedulerThread next = nextThread();
        next.registerSync(tasks);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks The iterable object containing the tasks to register
     */
    @Override
    public synchronized void registerAsync(@Nonnull Iterable<? extends Task> tasks) {
        tasks.forEach(this::register);
    }

    /**
     * {@inheritDoc}
     *
     * @param task The task to unregister from this scheduler
     */
    @Override
    public synchronized void unregister(@Nonnull Task task) {
        threads.forEach(t -> t.unregister(task));
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks The iterable object containing the tasks to unregister
     */
    @Override
    public synchronized void unregister(@Nonnull Iterable<? extends Task> tasks) {
        threads.forEach(t -> t.unregister(tasks));
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
    public synchronized void start() {
        threads.forEach(SchedulerThread::start);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void interrupt() {
        threads.forEach(SchedulerThread::interrupt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void terminate() {
        threads.forEach(SchedulerThread::interrupt);
    }
}
