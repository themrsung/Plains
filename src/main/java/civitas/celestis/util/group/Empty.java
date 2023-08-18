package civitas.celestis.util.group;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * A tuple containing zero elements.
 * This is useful for an alternative placeholder value to {@code null}.
 *
 * @param <E> The type of element to (not) contain
 */
public class Empty<E> implements Tuple<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 4798459615199750921L;

    //
    // Constructors
    //

    /**
     * Creates a new empty tuple.
     */
    public Empty() {
    }

    /**
     * Creates a new empty tuple. Validates that the provided group {@code g}'s size is zero.
     *
     * @param g The group of which to (not) copy values from
     * @throws IllegalArgumentException When the provided group {@code g}'s size is not zero
     */
    public Empty(@Nonnull Group<E> g) {
        if (g.size() != 0) {
            throw new IllegalArgumentException("The size of the provided group is not 0.");
        }
    }

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@code 0}
     */
    @Override
    public int size() {
        return 0;
    }

    //
    // Containment
    //

    /**
     * An empty tuple cannot contain anything, not even {@code null}.
     *
     * @param obj The object to check for containment
     * @return {@code false}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        return false;
    }

    /**
     * An empty tuple cannot contain anything, not even {@code null}.
     *
     * @param i The iterable object containing the elements to check for containment
     * @return {@code false}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<?> i) {
        return false;
    }

    //
    // Getters
    //

    /**
     * There is no element contained in an empty tuple,
     * thus this method cannot return a value.
     *
     * @param i The index of the element to get
     * @return Cannot return a value
     * @throws IndexOutOfBoundsException Always throws an exception
     */
    @Nonnull
    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 0.");
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public E[] array() {
        return (E[]) new Object[0];
    }

    //
    // Transformation
    //

    /**
     * This is an empty tuple. No changes can be made, and thus this simply
     * returns a reference to itself.
     *
     * @param f Ignored
     * @return {@code this}
     */
    @Nonnull
    @Override
    public Empty<E> transform(@Nonnull Function<? super E, E> f) {
        return this;
    }

    /**
     * This is an empty tuple. No changes can be made, and thus this simply
     * returns a new empty instance.
     *
     * @param f   Ignored
     * @param <F> {@inheritDoc}
     * @return A new empty instance of the return type of {@code f}
     */
    @Nonnull
    @Override
    public <F> Empty<F> map(@Nonnull Function<? super E, F> f) {
        return new Empty<>();
    }

    //
    // Iterator
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public Iterator<E> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public E next() {
                return null;
            }
        };
    }

    //
    // Type Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return List.of();
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this empty tuple and another object.
     * This will return {@code true} if either the other object is another empty
     * tuple, or another instance of {@link Group} with a size of {@code 0}.
     *
     * @param obj The object to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Empty<?>) return true;
        if (!(obj instanceof Group<?> g)) return false;
        return g.size() == 0;
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return "[]";
    }
}
