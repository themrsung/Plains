package civitas.celestis.util.container;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.function.UnaryOperator;

/**
 * A simple object container with no built-in thread safety. Containers support the
 * usage of {@code null} values. Containers can be used to make immutable objects
 * effectively mutable by re-assigning their references within the container object.
 *
 * @param <E> The type of element to contain
 * @see ObjectContainer
 */
public interface Container<E> extends Serializable {
    //
    // Factory
    //

    /**
     * Creates a new container with the provided value {@code v}.
     *
     * @param v   The value of which to initialize the container with
     * @param <E> The type of element to contain
     * @return A new container initialized from the provided value {@code v}
     */
    @Nonnull
    static <E> Container<E> of(@Nullable E v) {
        return new ObjectContainer<>(v);
    }

    /**
     * Creates a new container with an initial value of {@code null}.
     *
     * @param <E> The type of element to contain
     * @return A new container initialized with the value {@code null}
     */
    @Nonnull
    static <E> Container<E> of() {
        return new ObjectContainer<>(null);
    }

    //
    // Value
    //

    /**
     * Returns the current value this container is holding.
     *
     * @return The current value of this container
     */
    E get();

    /**
     * Sets the value of this container.
     *
     * @param e The value to assign to this container
     */
    void set(E e);

    /**
     * Applies the provided update function {@code f} to this container,
     * assigning the return value of the function as its new value.
     *
     * @param f The update function to apply to this container
     */
    void update(@Nonnull UnaryOperator<E> f);

    //
    // Equality
    //

    /**
     * Checks for equality between this container and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a container, and the values are equal
     */
    boolean equals(@Nullable Object obj);

    //
    // Serialization
    //

    /**
     * Serializes this container into a string. This delegates to the value's {@link E#toString()}.
     *
     * @return The string representation of the value of this container
     */
    @Nonnull
    @Override
    String toString();
}
