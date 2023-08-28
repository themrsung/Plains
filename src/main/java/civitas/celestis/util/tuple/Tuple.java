package civitas.celestis.util.tuple;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A shallowly immutable set of elements. Tuples cannot be resized after
 * instantiation, and support the usage of {@code null} values.
 *
 * @param <E> The type of element this tuple should hold
 */
public interface Tuple<E> extends Iterable<E>, Serializable {
    //
    // Factory
    //

    /**
     * Creates a new tuple from the provided array of elements.
     *
     * @param elements The elements of which to construct the tuple from
     * @param <E>      The type of element to contain
     * @return A tuple constructed from the provided elements
     */
    @Nonnull
    @SafeVarargs
    static <E> Tuple<E> of(E... elements) {
        return new ArrayTuple<>(elements);
    }

    //
    // Properties
    //

    /**
     * Returns the size of this tuple. (the number of components it has)
     *
     * @return The number of components this tuple has
     */
    int size();

    //
    // Containment
    //

    /**
     * Returns whether this tuple contains the provided object {@code obj}.
     *
     * @param obj The object of which to check for containment
     * @return {@code true} if at least one element of this tuple is equal to the provided object {@code obj}
     */
    boolean contains(@Nullable Object obj);

    /**
     * Returns whether this tuple contains multiple objects.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this tuple contains every element of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<?> i);

    //
    // Getters
    //

    /**
     * Returns the {@code i}th element of this tuple.
     *
     * @param i The index of the element to get
     * @return The {@code i}th element of this tuple
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    E get(int i) throws IndexOutOfBoundsException;

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to each element of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f   The function of which to apply to each element of this tuple
     * @param <F> The type of component to map this tuple to
     * @return The resulting tuple
     */
    @Nonnull
    <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each element of this tuple
     * @return The resulting {@code double} tuple
     */
    @Nonnull
    DoubleTuple mapToDouble(@Nonnull ToDoubleFunction<? super E> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each element of this tuple
     * @return The resulting {@code long} tuple
     */
    @Nonnull
    LongTuple mapToLong(@Nonnull ToLongFunction<? super E> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each element of this tuple
     * @return The resulting {@code int} tuple
     */
    @Nonnull
    IntTuple mapToInt(@Nonnull ToIntFunction<? super E> f);

    /**
     * Between this tuple and the provided tuple {@code t}, this applies the merger function {@code f}
     * to each corresponding pair of elements, then returns a new tuple containing the return values
     * of the merger function {@code f}. In other words, this merges the two tuples into a new tuple
     * using the provided merger function {@code f}.
     *
     * @param t   The tuple of which to merge this tuple with
     * @param f   The merger function to handle the merging of the two tuples
     * @param <F> The type of element to merge this tuple with
     * @param <G> The type of element to merge the two tuples to
     * @return The resulting tuple
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size os different from
     *                                  that of this tuple's size
     */
    @Nonnull
    <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, ? extends G> f)
            throws IllegalArgumentException;

    //
    // Iteration
    //

    /**
     * Returns an iterator over every element of this tuple.
     *
     * @return An iterator over every element of this tuple
     */
    @Nonnull
    @Override
    Iterator<E> iterator();

    /**
     * Executes the provided action {@code a} once for each element of this tuple.
     * A reference to the element is provided as the first parameter of the action.
     *
     * @param a The action to be performed for each element
     */
    @Override
    void forEach(@Nonnull Consumer<? super E> a);

    /**
     * Executes the provided action {@code a} once for each element of this tuple.
     * The index of the element is provided as the first parameter of the action,
     * and a reference to the element is provided as the second parameter of the action.
     *
     * @param a The action to be performed for each element
     */
    void forEach(@Nonnull BiConsumer<? super Integer, ? super E> a);

    //
    // Conversion
    //

    /**
     * Returns an array containing the elements of this tuple in their proper order.
     *
     * @return The array representation of this tuple
     */
    @Nonnull
    E[] array();

    /**
     * Returns a stream whose source is the elements of this tuple.
     *
     * @return A stream whose source is the elements of this tuple
     */
    @Nonnull
    Stream<E> stream();

    /**
     * Returns an unmodifiable list containing the elements of this tuple in their proper order.
     *
     * @return The list representation of this tuple
     * @throws IllegalArgumentException When this tuple contains at least one instance of {@code null}
     */
    @Nonnull
    List<E> list();

    //
    // Equality
    //

    /**
     * Checks for equality between this tuple and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a tuple, and the number of elements,
     * the order of the elements, and the elements' values are all equal
     */
    @Override
    boolean equals(@Nullable Object obj);

    //
    // Serialization
    //

    /**
     * Serializes this tuple into a string.
     *
     * @return The string representation of this tuple
     */
    @Nonnull
    @Override
    String toString();
}
