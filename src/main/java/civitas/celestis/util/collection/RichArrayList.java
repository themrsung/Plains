package civitas.celestis.util.collection;

import civitas.celestis.util.array.FastArray;
import civitas.celestis.util.tuple.Tuple;
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
 * An enriched array list with additional features.
 *
 * @param <E> The type of element this list should hold
 */
public class RichArrayList<E> extends ArrayList<E> implements RichList<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 51331387713426288L;

    //
    // Static Initializers
    //

    /**
     * Creates a new rich array list from an array of entries.
     *
     * @param entries The entries to contain in the list
     * @param <E>     The type of element the list should hold
     * @return A list constructed from the provided array of entries
     */
    @Nonnull
    @SafeVarargs
    public static <E> RichArrayList<E> of(@Nonnull E... entries) {
        final RichArrayList<E> result = new RichArrayList<>(entries.length);
        result.addAll(Arrays.asList(entries));
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
     * @param c The collection of which to copy entries from
     */
    public RichArrayList(@Nonnull Collection<? extends E> c) {
        super(c);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The filter function of which to apply to each element of this collection
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized RichCollection<E> filter(@Nonnull Predicate<? super E> f) {
        return of((E[]) stream().filter(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The transformation function to apply to each element of this collection
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized RichCollection<E> transform(@Nonnull Function<? super E, E> f) {
        return of((E[]) stream().map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function to apply to each element of this collection
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized <F> RichCollection<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return of((F[]) stream().map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param l   The list of which to merge this collection with
     * @param f   The merger function to handle the merging of the two collections
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F, G> RichCollection<G> merge(
            @Nonnull List<F> l,
            @Nonnull BiFunction<? super E, ? super F, G> f
    ) throws IllegalArgumentException {
        // Cache size to compensate for concurrency issues
        final int size = size();

        if (size != l.size()) {
            throw new IllegalArgumentException("Collection sizes must match for this operation.");
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
    @SuppressWarnings("unchecked")
    public synchronized FastArray<E> array() {
        return FastArray.of((E[]) toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized Tuple<E> tuple() {
        return Tuple.of((E[]) toArray());
    }
}
