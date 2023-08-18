package civitas.celestis.event.lifecycle;

import civitas.celestis.event.Handleable;
import civitas.celestis.event.Listener;
import civitas.celestis.util.ThreadedModule;
import jakarta.annotation.Nonnull;

/**
 * Defines the contract for an event manager module.
 * @see Handleable
 * @see civitas.celestis.event.Event Event
 * @see Listener
 * @see civitas.celestis.event.EventHandler EventHandler
 * @see SyncEventManager
 */
public interface EventManager extends ThreadedModule {
    //
    // Events
    //

    /**
     * Calls an event, adding it to the event queue. (or processing it directly,
     * depending on the implementation of the event manager)
     */
    <E extends Handleable> void call(@Nonnull E event);

    /**
     * Returns an iterable object containing the queued events of this event manager.
     * This object is a shallow copy, and thus changes
     * will not be reflected in both directions.
     * @return An iterable object containing the queued events of this event manager
     */
    @Nonnull
    Iterable<? extends Handleable> getQueuedEvents();

    //
    // Listeners
    //

    /**
     * Registers an event listener to this event manager.
     * @param listener The event listener to register to this manager
     */
    void register(@Nonnull Listener listener);

    /**
     * Registers multiple event listeners to this event manager.
     * @param listeners The iterable object of listeners to register
     */
    void registerAll(@Nonnull Iterable<Listener> listeners);

    /**
     * Unregisters an event listener from this event manager.
     * @param listener The event listener to unregister from this manager
     */
    void unregister(@Nonnull Listener listener);

    /**
     * Unregisters multiple event listeners from this event manager.
     * @param listeners The iterable object of listeners to unregister
     */
    void unregisterAll(@Nonnull Iterable<Listener> listeners);

    /**
     * Returns whether the provided listener is currently
     * registered to this event manager.
     * @param listener The listener of which to check for registration
     * @return {@code true} if the provided listener instance
     * is registered to this event manager
     */
    boolean isRegistered(@Nonnull Listener listener);
}
