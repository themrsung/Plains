package civitas.celestis.event;

import civitas.celestis.util.ThreadedModule;
import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;

import java.util.Collection;

/**
 * An event manager. Handles the lifecycle of events and listeners.
 *
 * @see SyncEventManager
 * @see Event
 * @see Listener
 */
public interface EventManager extends ThreadedModule {
    //
    // Events
    //

    /**
     * Calls an event to this event manager, instructing it to handle the event.
     *
     * @param event The event of which to handle
     * @param <E>   The type of event to handle
     * @return {@code true} if the event was successfully added to the event queue
     */
    <E extends Handleable> boolean call(@Nonnull E event);

    //
    // Listeners
    //

    /**
     * Registers a listener to this event manager.
     *
     * @param listener The listener to register to this event manager
     */
    void register(@Nonnull Listener listener);

    /**
     * Registers multiple listeners to this event manager.
     *
     * @param listeners A collection containing the listeners to register to this event manager
     */
    void register(@Nonnull Collection<? extends Listener> listeners);

    /**
     * Registers multiple listeners to this event manager.
     *
     * @param listeners A tuple containing the listeners to register to this event manager
     */
    void register(@Nonnull Tuple<? extends Listener> listeners);

    /**
     * Unregisters a listener from this event manager.
     *
     * @param listener The listener to unregister from this event manager
     */
    void unregister(@Nonnull Listener listener);

    /**
     * Unregisters multiple listeners from this event manager.
     *
     * @param listeners A collection containing the listeners to unregister from this event manager
     */
    void unregister(@Nonnull Collection<? extends Listener> listeners);

    /**
     * Unregisters multiple listeners from this event manager.
     *
     * @param listeners A tuple containing the listeners to unregister from this event manager
     */
    void unregister(@Nonnull Tuple<? extends Listener> listeners);

    //
    // Handlers
    //

    /**
     * Returns a tuple containing every handler currently registered to this event manager.
     *
     * @return A tuple of every handler currently registered to this event manager
     */
    Tuple<HandlerReference> getRegisteredHandlers();
}
