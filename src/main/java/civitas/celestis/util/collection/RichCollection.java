package civitas.celestis.util.collection;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An extended {@link Collection}. Note that using rich collections adds overhead
 * to the interface which may not be an acceptable tradeoff in certain applications.
 *
 * @param <E> The type of element this collection should contain
 * @see Collection
 * @see RichList
 */
public interface RichCollection<E> extends Collection<E> {
    //
    // Filtration
    //

    /**
     * Applies the provided filter function {@code f} to every element of this collection,
     * collects the filtered values, then returns a new collection which only contains the
     * values filtered by the filter function.
     *
     * @param f The filter function of which to use to test each element of this collection
     * @return The filtered collection
     */
    @Nonnull
    RichCollection<E> filter(@Nonnull Predicate<? super E> f);

    //
    // Transformation
    //

    /**
     * Applies the provided function {@code f} to every element of this collection, then
     * returns a new collection whose values are populated from that of the resulting values.
     *
     * @param f   The function of which to apply to each element of this collection
     * @param <F> The type of element to map this collection to
     * @return The resulting collection
     */
    @Nonnull
    <F> RichCollection<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Applies the provided function {@code f} to every element of this collection, then
     * returns a new tuple whose values are populated from that of the resulting values.
     *
     * @param f   The function of which to apply to each element of this collection
     * @param <F> The type of element to map this collection to
     * @return The resulting tuple
     */
    @Nonnull
    <F> Tuple<F> mapToTuple(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Applies the provided function {@code f} to every element of this collection, then
     * returns a new array whose values are populated from that of the resulting values.
     *
     * @param f   The function of which to apply to each element of this collection
     * @param <F> The type of element to map this collection to
     * @return The resulting array
     */
    @Nonnull
    <F> SafeArray<F> mapToArray(@Nonnull Function<? super E, ? extends F> f);

    //
    // Conversion
    //

    /**
     * Returns an array whose elements are populated with that of this collection's elements.
     * If this collection has a defined order, the order will be retained. A shallow copy
     * is performed in the process, and changes to the return value will not be reflected
     * to this collection.
     *
     * @return The array representation of this collection
     */
    @Nonnull
    SafeArray<E> array();

    /**
     * Returns an array whose elements are populated with that of this collection's elements.
     * If this collection has a defined order, the order will be retained. A shallow copy
     * is performed in the process, and changes to the return value will not be reflected
     * to this collection.
     *
     * @return The array representation of this collection
     */
    @Nonnull
    @Override
    E[] toArray();

    /**
     * Returns a tuple whose elements are populated with that of this collection's elements.
     * If this collection has a defined order, the order will be retained.
     *
     * @return The tuple representation of this collection
     */
    @Nonnull
    Tuple<E> tuple();
}
