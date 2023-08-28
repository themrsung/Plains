package civitas.celestis.util.container;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * A container which can hold any type of object.
 *
 * @param <E> The type of element this container should hold
 * @see Container
 */
public class ObjectContainer<E> implements Container<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    /**
     * Initializes a new object container with the provided initial value.
     *
     * @param value The initial value of this container
     */
    public ObjectContainer(@Nullable E value) {
        this.value = value;
    }

    /**
     * Initializes an empty object container with {@code null} as its value.
     */
    public ObjectContainer() {
        this.value = null;
    }

    //
    // Variables
    //

    /**
     * The value of this container
     */
    private E value;

    //
    // Methods
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public E get() {
        return value;
    }

    /**
     * {@inheritDoc}
     *
     * @param e The value to assign to this container
     */
    @Override
    public void set(E e) {
        value = e;
    }

    /**
     * {@inheritDoc}
     *
     * @param f The update function to apply to this container
     */
    @Override
    public void update(@Nonnull UnaryOperator<E> f) {
        value = f.apply(value);
    }

    //
    // Equality
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Container<?> c)) return false;
        return Objects.equals(value, c.get());
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return Objects.toString(value);
    }
}
