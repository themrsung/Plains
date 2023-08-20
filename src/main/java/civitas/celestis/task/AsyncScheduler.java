package civitas.celestis.task;

import civitas.celestis.util.collection.CircularArrayQueue;
import civitas.celestis.util.collection.CircularQueue;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.util.Collection;

/**
 * An asynchronous scheduler with multiple threads.
 *
 * @see Task
 * @see Scheduler
 */
public class AsyncScheduler implements Scheduler {
    //
    // Constructors
    //

    /**
     * Creates a new asynchronous scheduler.
     *
     * @param n The number of threads to initialize
     */
    public AsyncScheduler(int n) {
        this("AsyncScheduler", n);
    }

    /**
     * Creates a new asynchronous scheduler.
     *
     * @param name The name of this module
     * @param n    The number of threads to initialize
     */
    public AsyncScheduler(@Nonnull String name, int n) {
        this.threads = new CircularArrayQueue<>(n);

        for (int i = 0; i < n; i++) {
            threads.add(new SchedulerThread(name + "-" + (i + 1)));
        }
    }

    /**
     * Direct assignment constructor. Use at your own risk.
     *
     * @param threads The circular queue of threads allocated to this scheduler
     */
    protected AsyncScheduler(@Nonnull CircularQueue<SchedulerThread> threads) {
        this.threads = threads;
    }

    //
    // Variables
    //

    /**
     * The circular queue of threads.
     */
    @Nonnull
    private final CircularQueue<SchedulerThread> threads;

    //
    // Methods
    //

    /**
     * {@inheritDoc}
     *
     * @param task The task to register to this scheduler
     */
    @Override
    public synchronized void register(@Nonnull Task task) {
        threads.next().register(task);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks A tuple containing the tasks to register synchronously
     */
    @Override
    public synchronized void registerSync(@Nonnull Tuple<? extends Task> tasks) {
        final SchedulerThread next = threads.next();
        next.registerSync(tasks);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks A collection containing the tasks to register synchronously
     */
    @Override
    public synchronized void registerSync(@Nonnull Collection<? extends Task> tasks) {
        final SchedulerThread next = threads.next();
        next.registerSync(tasks);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks A tuple containing the tasks to register asynchronously
     */
    @Override
    public synchronized void registerAsync(@Nonnull Tuple<? extends Task> tasks) {
        tasks.forEach(this::register);
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks A collection containing the tasks to register asynchronously
     */
    @Override
    public synchronized void registerAsync(@Nonnull Collection<? extends Task> tasks) {
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
     * @param tasks A tuple containing the tasks to unregister from this scheduler
     */
    @Override
    public synchronized void unregister(@Nonnull Tuple<? extends Task> tasks) {
        threads.forEach(t -> t.unregister(tasks));
    }

    /**
     * {@inheritDoc}
     *
     * @param tasks A collection containing the tasks to unregister from this scheduler
     */
    @Override
    public synchronized void unregister(@Nonnull Collection<? extends Task> tasks) {
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
        return threads.tuple();
    }
}
