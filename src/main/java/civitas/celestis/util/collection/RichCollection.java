package civitas.celestis.util.collection;

import civitas.celestis.util.array.FastArray;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A collection with advanced features.
 *
 * @param <E> The type of element this collection should hold
 */
public interface RichCollection<E> extends Collection<E> {
    //
    // Bulk Operation
    //

    /**
     * Fills this collection with the provided value {@code v}.
     *
     * @param v The value to assign to every element of this collection
     * @throws UnsupportedOperationException When this collection is an unmodifiable collection
     */
    void fill(E v);

    //
    // Filtration
    //

    /**
     * Applies the provided filter function {@code f} to each element of this collection,
     * collects every element which the filter function has returned {@code true} to,
     * then returns a new collection containing the filtered elements.
     *
     * @param f The filter function of which to apply to each element of this collection
     * @return The filtered collection
     */
    @Nonnull
    RichCollection<E> filter(@Nonnull Predicate<? super E> f);

    //
    // Transformation
    //

    /**
     * Applies the provided transformation function {@code f} to each element of this
     * collection, then returns a new collection containing the transformed values.
     * This operation preserves the type bounds of this collection.
     *
     * @param f The transformation function to apply to each element of this collection
     * @return The resulting collection
     */
    @Nonnull
    RichCollection<E> transform(@Nonnull Function<? super E, E> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this collection,
     * then returns a new collection containing the mapped values. This operation does
     * not preserve the type bounds of this collection.
     *
     * @param f   The mapper function to apply to each element of this collection
     * @param <F> The type of element to map this collection to (does not require to be a
     *            subtype of {@code E})
     * @return The resulting collection
     */
    @Nonnull
    <F> RichCollection<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Between this collection and the provided collection {@code c}, this applies the merger
     * function {@code f} to each corresponding pair of elements, then returns a new collection
     * containing the resulting elements. This operation does not preserve the type bounds
     * of this collection.
     *
     * @param c   The collection of which to merge this collection with
     * @param f   The merger function to handle the merging of the two collections
     * @param <F> The type of element to merge this collection with
     * @param <G> The type of element to merge the two collections to (does not require that it
     *            is a subtype of {@code E} or the other collection's generic component type)
     * @return The resulting collection
     * @throws IllegalArgumentException When the two collections' sizes are different
     */
    @Nonnull
    <F, G> RichCollection<F> merge(@Nonnull Collection<F> c, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException;

    //
    // Conversion
    //

    /**
     * Converts this collection into a type-safe array, then returns the converted array.
     * Changes in the array will not be reflected to this collection.
     *
     * @return The array representation of this collection
     */
    @Nonnull
    FastArray<E> array();

    /**
     * Converts this collection into a tuple, then returns the converted tuple.
     *
     * @return The tuple representation of this collection
     * @see Tuple
     */
    @Nonnull
    Tuple<E> tuple();
}
