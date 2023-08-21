package civitas.celestis.event;

import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * An event processing thread.
 */
public class EventThread extends Thread implements EventManager {
    //
    // Constructors
    //

    /**
     * Creates a new event thread with a randomized name.
     */
    public EventThread() {
        this("EventThread-" + UUID.randomUUID());
    }

    /**
     * Creates a new event processing thread.
     *
     * @param name The name of this thread
     */
    public EventThread(@Nonnull String name) {
        this(name, new ConcurrentLinkedDeque<>(), new ArrayList<>());
    }

    /**
     * Creates a new event processing thread.
     *
     * @param name       The name of this thread
     * @param eventQueue The queue of events
     * @param handlers   The list of handlers
     */
    protected EventThread(
            @Nonnull String name,
            @Nonnull Queue<Handleable> eventQueue,
            @Nonnull List<HandlerReference> handlers
    ) {
        super(() -> {

            //
            // START OF EVENT THREAD
            //
            // Created: v0.1
            // Last Updated: v0.1
            //

            // Infinitely loop until interrupted
            while (!Thread.interrupted()) {
                // Poll next event
                final Handleable event = eventQueue.poll();

                // Continue if polled event is null
                if (event == null) continue;

                // Iterate through registered handlers
                for (final HandlerReference handler : List.copyOf(handlers)) {
                    // Continue if the handler does not accept this event
                    if (!handler.accepts(event)) continue;

                    try {
                        // Handle the event
                        handler.handle(event);
                    } catch (final Exception e) {
                        // Print the stack trace to the default print stream
                        e.printStackTrace();
                    }
                }
            }

            //
            // END OF EVENT THREAD
            //

        }, name);

        // Assign variables
        this.eventQueue = eventQueue;
        this.handlers = handlers;
    }

    //
    // Variables
    //

    /**
     * The queue of unhandled events.
     */
    @Nonnull
    protected final Queue<Handleable> eventQueue;

    /**
     * A list of handlers registered to this thread.
     */
    @Nonnull
    protected final List<HandlerReference> handlers;

    //
    // Methods
    //

    /**
     * {@inheritDoc}
     *
     * @param event The event of which to handle
     * @param <E>   {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public <E extends Handleable> boolean call(@Nonnull E event) {
        return eventQueue.add(event);
    }

    /**
     * {@inheritDoc}
     *
     * @param listener The listener to register to this event manager
     */
    @Override
    public void register(@Nonnull Listener listener) {
        handlers.addAll(listener.getHandlers());

        // Preemptively sort the list by execution priority
        handlers.sort(Comparator.comparing(HandlerReference::priority));
    }

    /**
     * {@inheritDoc}
     *
     * @param listeners An iterable object containing the listeners to register to this event manager
     */
    @Override
    public void register(@Nonnull Iterable<? extends Listener> listeners) {
        listeners.forEach(this::register);
    }

    /**
     * {@inheritDoc}
     *
     * @param listener The listener to unregister from this event manager
     */
    @Override
    public void unregister(@Nonnull Listener listener) {
        handlers.removeAll(listener.getHandlers());
    }

    /**
     * {@inheritDoc}
     *
     * @param listeners An iterable object containing the listeners to unregister from this event manager
     */
    @Override
    public void unregister(@Nonnull Iterable<? extends Listener> listeners) {
        listeners.forEach(this::unregister);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<HandlerReference> getRegisteredHandlers() {
        return Tuple.copyOf(handlers);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<? extends Thread> getThreads() {
        return Tuple.of(this);
    }
}
