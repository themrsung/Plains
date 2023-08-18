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
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An {@link ArrayList} which can be converted into a {@link Group}.
 * This list also adds various functionality upon the {@link List} interface.
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
    // Bulk Operation
    //

    /**
     * Fills this list with the provided value {@code v}.
     *
     * @param v The value to fill this list with
     */
    public void fill(E v) {
        replaceAll(o -> v);
    }

    /**
     * Fills this list, but only replaces {@code null} values to the provided value {@code v}.
     *
     * @param v The value to replace {@code null} values with
     */
    public void fillEmpty(E v) {
        replaceAll(o -> o == null ? v : o);
    }

    /**
     * Fills this list, but only assigns the value if the filter function {@code f} returns
     * {@code true} for the existing value at the corresponding index.
     *
     * @param v The value to fill this list with
     * @param f The filter function to use
     */
    public void fillIf(E v, @Nonnull Predicate<? super E> f) {
        replaceAll(o -> f.test(o) ? v : o);
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

    /**
     * Merges this list with another list of the same size.
     * The merger function {@code f} is applied to each component,
     * and the resulting list is returned.
     * <p>
     * For example, if a sum function {@code (x, y) -> x + y} was provided,
     * this would return the sum of the two lists of numbers.
     * </p>
     *
     * @param l The other list to merge with
     * @param f The merger function to apply
     * @return The resulting list
     * @throws IllegalArgumentException When the provided list {@code l}'s size is different from that of this list's
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public GroupableArrayList<E> merge(@Nonnull List<E> l, @Nonnull BiFunction<? super E, ? super E, E> f)
            throws IllegalArgumentException {
        if (size() != l.size()) throw new IllegalArgumentException("List sizes must match for this operation.");

        final E[] result = (E[]) new Object[size()];

        for (int i = 0; i < size(); i++) {
            result[i] = f.apply(get(i), l.get(i));
        }

        return of(result);
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
