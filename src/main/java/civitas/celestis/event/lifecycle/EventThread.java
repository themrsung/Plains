package civitas.celestis.event.lifecycle;

import civitas.celestis.event.Handleable;
import civitas.celestis.event.HandlerReference;
import civitas.celestis.event.Listener;
import civitas.celestis.exception.event.HandlerException;
import jakarta.annotation.Nonnull;

import java.io.PrintStream;
import java.util.*;

/**
 * A thread which processes events. Event threads can either have an independent queue
 * and list of handlers, or share the resources with other threads. The sharing of event
 * queues or handler lists can be achieved by extending this class and using the protected
 * constructor to initialize the instance. The constructor is not public to ensure that any
 * multithreading is properly handled by a subclass which has proper synchronization measures.
 *
 * @see EventManager
 */
public class EventThread extends Thread implements EventManager {
    //
    // Constructors
    //

    /**
     * Creates a new event thread.
     */
    public EventThread() {
        this("EventThread", System.out);
    }

    /**
     * Creates a new event thread.
     *
     * @param name The name of this thread
     */
    public EventThread(@Nonnull String name) {
        this(name, System.out);
    }

    /**
     * Creates a new event thread.
     *
     * @param name        The name of this thread
     * @param printStream The print stream to print error messages to
     */
    public EventThread(@Nonnull String name, @Nonnull PrintStream printStream) {

        /*
         * Since this constructor is used to create an event thread which uses an independent
         * event queue and an independent handler list, the deque and list instance do not
         * have to be concurrent. This ensures that there is no unnecessary overhead.
         */

        this(name, new ArrayDeque<>(), new ArrayList<>(), printStream);
    }

    /**
     * Creates a new event thread. If the event queue or list of handler references are shared
     * across threads, they must be a thread-safe collection. They must also not be immutable.
     *
     * @param name        The name of this thread
     * @param eventQueue  The event queue this thread should manage
     * @param handlers    The list of handlers this thread should manage
     * @param printStream The print stream to use to print error messages to
     */
    protected EventThread(
            @Nonnull String name,
            @Nonnull Deque<Handleable> eventQueue,
            @Nonnull List<HandlerReference> handlers,
            @Nonnull PrintStream printStream
    ) {
        super(() -> {

            //
            // START OF EVENT THREAD
            //
            // Created: v0.4
            // Last Modified: v0.4
            //

            // Enter infinite loop while thread is active
            while (!Thread.interrupted()) {

                // Poll the first event in the queue
                final Handleable nextEvent = eventQueue.pollFirst();

                // Continue looping if the event is null
                if (nextEvent == null) continue;

                // Iterate through copy of list to prevent concurrent modification
                for (final HandlerReference handler : List.copyOf(handlers)) {
                    if (!handler.accepts(nextEvent)) continue;
                    try {

                        // Invoke the event handler
                        handler.handle(nextEvent);

                    } catch (final HandlerException e) {

                        // Print the stack trace to the provided print stream
                        e.printStackTrace(printStream);

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
     * The queue of events.
     */
    @Nonnull
    private final Deque<Handleable> eventQueue;

    /**
     * The list of handlers.
     */
    @Nonnull
    private final List<HandlerReference> handlers;

    //
    // Methods
    //

    /**
     * {@inheritDoc}
     * @param event The event of which to handle
     * @param <E> {@inheritDoc}
     */
    @Override
    public <E extends Handleable> void call(@Nonnull E event) {
        eventQueue.offerLast(event);
    }

    /**
     * {@inheritDoc}
     * @param event THe event of which to prioritize
     * @param <E> {@inheritDoc}
     */
    @Override
    public <E extends Handleable> void priorityCall(@Nonnull E event) {
        eventQueue.addFirst(event);
    }

    /**
     * {@inheritDoc}
     * @param listener The event listener to register to this event manager
     */
    @Override
    public void register(@Nonnull Listener listener) {
        Objects.requireNonNull(listener);
        handlers.addAll(listener.getHandlerReferences());
    }

    /**
     * {@inheritDoc}
     * @param listeners The iterable object containing the listeners to register
     */
    @Override
    public void register(@Nonnull Iterable<? extends Listener> listeners) {
        Objects.requireNonNull(listeners);
        listeners.forEach(this::register);
    }

    /**
     * {@inheritDoc}
     * @param listener The event listener to unregister from this event manager
     */
    @Override
    public void unregister(@Nonnull Listener listener) {
        Objects.requireNonNull(listener);
        handlers.removeAll(listener.getHandlerReferences());
    }

    /**
     * {@inheritDoc}
     * @param listeners The iterable object containing the listeners to unregister
     */
    @Override
    public void unregister(@Nonnull Iterable<? extends Listener> listeners) {
        Objects.requireNonNull(listeners);
        listeners.forEach(this::unregister);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        // Nothing to do here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void terminate() {
        interrupt();
    }
}
