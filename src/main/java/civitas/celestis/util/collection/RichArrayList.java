package civitas.celestis.util.collection;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An extended {@link ArrayList} with advanced features.
 *
 * @param <E> The type of element this list should contain
 * @see RichList
 * @see ArrayList
 */
public class RichArrayList<E> extends ArrayList<E> implements RichList<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Static Initializers
    //

    /**
     * Creates a new rich array list containing the provided array of elements.
     *
     * @param elements The elements to contain in the new list
     * @param <E>      The type of element to contain
     * @return A new list constructed from the provided elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> RichArrayList<E> of(@Nonnull E... elements) {
        final RichArrayList<E> result = new RichArrayList<>(elements.length);
        result.addAll(Arrays.asList(elements));
        return result;
    }

    //
    // Constructors
    //

    /**
     * Creates a new rich array list.
     *
     * @param initialCapacity The initial capacity of this list
     */
    public RichArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Creates a new rich array list.
     */
    public RichArrayList() {
    }

    /**
     * Creates a new rich array list.
     *
     * @param c The collection of which to copy elements from
     */
    public RichArrayList(@Nonnull Collection<? extends E> c) {
        super(c);
    }

    //
    // Sub Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param i1 The index at which to start copying elements
     * @param i2 The index at which to stop copying elements
     * @param l  The sub-list of which to copy elements from
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void setRange(int i1, int i2, @Nonnull List<? extends E> l) throws IndexOutOfBoundsException {
        for (int i = i1; i < i2; i++) {
            set(i, l.get(i - i1));
        }
    }

    //
    // Filtration
    //

    /**
     * {@inheritDoc}
     *
     * @param f The filter function of which to use to test each element of this collection
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public RichList<E> filter(@Nonnull Predicate<? super E> f) {
        return of((E[]) stream().filter(f).toArray());
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this collection
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> RichList<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return of((F[]) stream().map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this collection
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> Tuple<F> mapToTuple(@Nonnull Function<? super E, ? extends F> f) {
        return Tuple.of((F[]) stream().map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this collection
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> SafeArray<F> mapToArray(@Nonnull Function<? super E, ? extends F> f) {
        return SafeArray.of((F[]) stream().map(f).toArray());
    }

    /**
     * {@inheritDoc}
     * Since this operation involves an unknown second list, it is synchronized to mitigate
     * the potential concurrency issues which may affect the operation.
     *
     * @param l   The list of which to merge this list with
     * @param f   The merger function to handle the merging of the two lists
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F, G> RichList<G> merge(@Nonnull List<F> l, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        if (size() != l.size()) {
            throw new IllegalArgumentException("List sizes must match for this operation.");
        }
        final RichArrayList<G> result = new RichArrayList<>(size());

        for (int i = 0; i < size(); i++) {
            result.set(i, f.apply(get(i), l.get(i)));
        }

        return result;
    }

    //
    // Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<E> array() {
        return SafeArray.of(toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray() {
        return (E[]) super.toArray();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<E> tuple() {
        return Tuple.of(toArray());
    }

    //
    // Iteration
    //

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this list
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Integer, ? super E> action) {
        for (int i = 0; i < size(); i++) {
            action.accept(i, get(i));
        }
    }
}

