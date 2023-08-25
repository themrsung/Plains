package civitas.celestis.util.concurrent;

import jakarta.annotation.Nonnull;

import java.util.function.Consumer;

/**
 * Contains utility methods related to {@link Thread}s.
 */
public final class Threads {
    /**
     * Creates a new thread with the provided action, starts the thread, then returns
     * a reference to the thread which was started.
     *
     * @param action The action to be executed by the thread
     * @return A reference to the newly created and started thread
     */
    @Nonnull
    public static Thread execute(@Nonnull Runnable action) {
        final Thread thread = new Thread(action);
        thread.start();
        return thread;
    }

    /**
     * Creates a new thread with the provided action, starts the thread, then returns
     * a reference to the thread which was started. The current system time when the thread
     * has started is provided as the first parameter of the function. If the thread is
     * interrupted before it can execute the action, it will terminate silently.
     *
     * @param action The action to be executed by the thread
     * @return A reference to the newly created and started thread
     */
    @Nonnull
    public static Thread execute(@Nonnull Consumer<Long> action) {
        final Thread thread = new Thread(() -> action.accept(System.currentTimeMillis()));
        thread.start();
        return thread;
    }

    /**
     * Creates a new thread with the provided action, starts the thread, then returns
     * a reference to the thread which was started. The thread will sleep for the specified
     * amount of time before executing the action. If the thread is interrupted before it
     * can execute the action, it will terminate silently.
     *
     * @param action The action to be executed by the thread
     * @param delay  The delay to wait for before executing the action in milliseconds
     * @return A reference to the newly created and started thread
     */
    @Nonnull
    public static Thread execute(@Nonnull Runnable action, long delay) {
        final Thread thread = new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (final InterruptedException e) {
                return;
            }

            action.run();
        });

        thread.start();
        return thread;
    }

    /**
     * Creates a new thread with the provided action, starts the thread, then returns
     * a reference to the thread which was started. The current system time when the thread
     * has started is provided as the first parameter of the function. The thread will sleep
     * for the specified amount of time before executing the action.
     *
     * @param action The action to be executed by the thread
     * @param delay  The delay to wait for before executing the action in milliseconds
     * @return A reference to the newly created and started thread
     */
    @Nonnull
    public static Thread execute(@Nonnull Consumer<Long> action, long delay) {
        final Thread thread = new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (final InterruptedException e) {
                return;
            }

            action.accept(System.currentTimeMillis());
        });

        thread.start();
        return thread;
    }
}
