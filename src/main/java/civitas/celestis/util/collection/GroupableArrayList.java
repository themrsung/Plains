package civitas.celestis.util.collection;

import civitas.celestis.util.Filterable;
import civitas.celestis.util.Transformable;
import civitas.celestis.util.group.Group;
import civitas.celestis.util.group.SafeArray;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An {@link ArrayList} which can be converted into a {@link Group}.
 *
 * @param <E> The type of element this list should hold
 * @see ArrayList
 * @see GroupableList
 */
public class GroupableArrayList<E> extends ArrayList<E>
        implements GroupableList<E>, Transformable<E>, Filterable<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 928085825613956822L;

    //
    // Static Initializers
    //

    /**
     * Creates a new groupable array list from an array of values.
     *
     * @param values The values to contain
     * @param <E>    The type of element to hold
     * @return A groupable array list constructed from the array of values
     */
    @Nonnull
    @SafeVarargs
    public static <E> GroupableArrayList<E> of(@Nonnull E... values) {
        final GroupableArrayList<E> result = new GroupableArrayList<>(values.length);
        result.addAll(Arrays.asList(values));
        return result;
    }

    //
    // Constructors
    //

    /**
     * Creates a new groupable array list.
     *
     * @param initialCapacity The initial capacity of this list
     */
    public GroupableArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Creates a new groupable array list.
     */
    public GroupableArrayList() {
    }

    /**
     * Creates a new groupable array list.
     *
     * @param c The collection of which to copy elements from
     */
    public GroupableArrayList(@Nonnull Collection<? extends E> c) {
        super(c);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function to apply to each element of this object
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public GroupableArrayList<E> transform(@Nonnull Function<? super E, E> f) {
        return new GroupableArrayList<>(stream().map(f).toList());
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function to apply to each element of this object
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> GroupableArrayList<F> map(@Nonnull Function<? super E, F> f) {
        return new GroupableArrayList<>(stream().map(f).toList());
    }

    //
    // Filtration
    //

    /**
     * {@inheritDoc}
     *
     * @param f The filter function to apply to sub-objects of this object
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public GroupableArrayList<E> filter(@Nonnull Predicate<? super E> f) {
        return new GroupableArrayList<>(stream().filter(f).toList());
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
    @SuppressWarnings("unchecked")
    public Group<E> group() {
        return SafeArray.of((E[]) toArray(Object[]::new));
    }
}
