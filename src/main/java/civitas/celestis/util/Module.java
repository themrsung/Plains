package civitas.celestis.util;

/**
 * A superinterface for an application's module. Modules are defined as
 * transient container objects which handle the control flow of a certain part
 * of the application's lifecycle.
 */
public interface Module {
    /**
     * Initializes this module. This is expected to be called
     * before any other method of this module is called.
     */
    void initialize();

    /**
     * Starts this module, instructing it to start operating.
     */
    void start();

    /**
     * Interrupts this module, instructing it to stop operating as soon as possible.
     */
    void interrupt();

    /**
     * Forcefully stops this module, instructing it to interrupt all threads,
     * and stop operating immediately regardless of its current state.
     */
    void terminate();
}
