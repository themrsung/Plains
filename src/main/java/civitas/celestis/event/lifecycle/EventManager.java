package civitas.celestis.event.lifecycle;

import civitas.celestis.event.Handleable;
import civitas.celestis.event.Listener;
import civitas.celestis.util.Module;
import jakarta.annotation.Nonnull;

/**
 * An event manager handles the lifecycle of events and event listeners.
 * Events are processed by calling an event by means of {@link #call(Handleable)},
 * and event listeners can be registered and unregistered by their respective methods.
 *
 * @see SyncEventManager
 */
public interface EventManager extends Module {
    /**
     * Calls an event to this event manager, instructing it to process the event
     * as soon as possible.
     *
     * @param event The event of which to handle
     * @param <E>   The type of event to handle
     */
    <E extends Handleable> void call(@Nonnull E event);

    /**
     * Calls an event to this event manager, instructing it to prioritize this event
     * over all other events currently in the event queue.
     *
     * @param event THe event of which to prioritize
     * @param <E>   The type of event to prioritize
     */
    <E extends Handleable> void priorityCall(@Nonnull E event);

    /**
     * Registers an event listener to this event manager.
     *
     * @param listener The event listener to register to this event manager
     */
    void register(@Nonnull Listener listener);

    /**
     * Registers multiple event listeners to this event manager.
     *
     * @param listeners The iterable object containing the listeners to register
     */
    void register(@Nonnull Iterable<? extends Listener> listeners);

    /**
     * Unregisters an event listener from this event manager.
     *
     * @param listener The event listener to unregister from this event manager
     */
    void unregister(@Nonnull Listener listener);

    /**
     * Unregisters multiple event listeners from this event manager.
     *
     * @param listeners The iterable object containing the listeners to unregister
     */
    void unregister(@Nonnull Iterable<? extends Listener> listeners);
}
