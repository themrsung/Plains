package civitas.celestis.event.lifecycle;

import civitas.celestis.event.Handleable;
import civitas.celestis.event.Listener;
import jakarta.annotation.Nonnull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * A queue-based event processing thread.
 */
public class EventThread extends Thread implements EventManager {
    //
    // Constructors
    //

    /**
     * Creates a new event thread.
     * @param name The name of this thread
     */
    public EventThread(@Nonnull String name) {
        this(name, new ConcurrentLinkedDeque<>(), new ArrayList<>());
    }

    /**
     * Creates a new event thread.
     * @param name The name of this thread
     * @param queue The queue object to use to store events
     * @param handlers The list of handlers registered to this thread
     */
    private EventThread(@Nonnull String name, @Nonnull Deque<Handleable> queue, @Nonnull List<HandlerReference> handlers) {
        super(() -> {
            //
            // Event Thread Logic
            //
            // Created: v0.1
            // Last updated: v0.1
            //

            // Infinitely loop while not interrupted
            while (!Thread.interrupted()) {
                // Poll next event
                final Handleable event = queue.pollFirst();

                // Continue if null
                if (event == null) continue;

                // Iterate through copy of handlers to prevent concurrency issues
                for (final HandlerReference handler : List.copyOf(handlers)) {

                    // Continue if the handler does not accept the event
                    if (!handler.accepts(event)) continue;

                    try {
                        // Send the event to the handler
                        handler.handle(event);
                    } catch (final IllegalAccessException | InvocationTargetException e) {
                        // Print stack traces if exceptions occur
                        e.printStackTrace();
                    }
                }
            }

        }, name);

        this.queue = queue;
        this.handlers = handlers;
    }

    //
    // Variables
    //

    /**
     * The queue of events.
     */
    protected final Deque<Handleable> queue;

    /**
     * The list of handlers.
     */
    protected final List<HandlerReference> handlers;

    //
    // Methods
    //

    /**
     * {@inheritDoc}
     * @param event {@inheritDoc}
     * @param <E> {@inheritDoc}
     */
    @Override
    public <E extends Handleable> void call(@Nonnull E event) {
        queue.addLast(event);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Handleable> getQueuedEvents() {
        return List.copyOf(queue);
    }

    /**
     * {@inheritDoc}
     * @param listener The event listener to register to this manager
     */
    @Override
    public void register(@Nonnull Listener listener) {
        handlers.addAll(listener.getHandlerReferences());
    }

    /**
     * {@inheritDoc}
     * @param listeners The iterable object of listeners to register
     */
    @Override
    public void registerAll(@Nonnull Iterable<Listener> listeners) {
        listeners.forEach(this::register);
    }

    /**
     * {@inheritDoc}
     * @param listener The event listener to unregister from this manager
     */
    @Override
    public void unregister(@Nonnull Listener listener) {
        handlers.removeAll(listener.getHandlerReferences());
    }

    /**
     * {@inheritDoc}
     * @param listeners The iterable object of listeners to unregister
     */
    @Override
    public void unregisterAll(@Nonnull Iterable<Listener> listeners) {
        listeners.forEach(this::unregister);
    }

    /**
     * {@inheritDoc}
     * @param listener The listener of which to check for registration
     * @return {@inheritDoc}
     */
    @Override
    public boolean isRegistered(@Nonnull Listener listener) {
        return new HashSet<>(handlers).containsAll(listener.getHandlerReferences());
    }
}
