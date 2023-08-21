package civitas.celestis.util;

import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

/**
 * Defines the contract for modules which control one or more threads.
 */
public interface ThreadedModule {
    /**
     * Starts this module, instructing it to start every thread it controls.
     * This action will be applied immediately.
     */
    void start();

    /**
     * Stops this module, instructing it to interrupt every thread it controls.
     * If there are infinitely iterating threads, the interruption will be applied
     * after the current iteration has completed.
     */
    void interrupt();

    /**
     * Returns a tuple containing references to every thread this module controls.
     *
     * @return A tuple of all threads this module controls
     */
    @Nonnull
    Tuple<? extends Thread> getThreads();
}
