package civitas.celestis.listener;

import civitas.celestis.event.EventHandler;
import civitas.celestis.event.Handleable;
import civitas.celestis.event.HandlerReference;
import civitas.celestis.event.Listener;
import jakarta.annotation.Nonnull;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * The contract for a dedicated listener class. Although there is no requirement
 * for event handlers other than to implement the {@link Listener marker interface},
 * having a minimal amount of declared methods helps to improve the event manager's
 * performance. This class serves as a framework to create a dedicated listener class.
 * <p><b>
 * No matter how many methods are declared in a subclass of this listener,
 * only {@link #handle(Handleable)} will be considered an event handler method.
 * In order to declare multiple handler methods, directly implement the {@link Listener}
 * interface, and create a custom subclass.
 * </b></p>
 *
 * @param <E> The type of event this listener handles
 */
public abstract class EventListener<E extends Handleable> implements Listener {
    /**
     * Called to handle the event.
     *
     * @param event The event which was called
     */
    @EventHandler
    public abstract void handle(@Nonnull E event);

    /**
     * Declared as final to seal the method from subclasses.
     *
     * @return A collection only containing one element: the reference to {@link #handle(Handleable)}
     */
    @Nonnull
    @Override
    public final Collection<HandlerReference> getHandlerReferences() {
        final Method method;

        try {
            method = getClass().getMethod("handle", Handleable.class);
        } catch (final NoSuchMethodException e) {
            throw new UnknownError("The handler method in " + getClass().getSimpleName() + " was not found.");
        }

        if (!method.isAnnotationPresent(EventHandler.class)) {
            throw new RuntimeException(
                    "The event handler annotation was not found in the default handle() method of " +
                            getClass().getSimpleName() + ". The default execution priority will be used."
            );
        }

        return List.of(new HandlerReference(this, method));
    }
}
