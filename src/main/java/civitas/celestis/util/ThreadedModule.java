package civitas.celestis.util;

/**
 * A module which controls one or more threads.
 */
public interface ThreadedModule {
    /**
     * Starts this module, instructing it to start every thread it controls.
     */
    void start();

    /**
     * Stops this module, instructing it to interrupt every thread it controls.
     */
    void interrupt();
}
