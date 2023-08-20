package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An immutable set of elements. Tuples have a fixed size, and are shallowly immutable,
 * meaning that the component variables cannot be reassigned after instantiation.
 *
 * @param <E> The type of element this tuple should hold
 */
public interface Tuple<E> extends Iterable<E>, Serializable {
    //
    // Factory
    //

    /**
     * Given an array of elements, this creates a new tuple instance containing
     * the provided array of elements.
     *
     * @param elements The array of elements to contain in the tuple
     * @param <E>      The type of element to contain in the tuple
     * @return The constructed tuple
     */
    @Nonnull
    @SafeVarargs
    static <E> Tuple<E> of(@Nonnull E... elements) {
        return new ArrayTuple<>(elements);
    }

    //
    // Properties
    //

    /**
     * Returns the number of elements this tuple contains.
     *
     * @return The number of elements this tuple contains
     */
    int size();

    //
    // Retrieval
    //

    /**
     * Returns the {@code i}th element of this tuple. If there is no
     * element present at the {@code i}th position of this tuple,
     * this will return {@code null}.
     *
     * @param i The index of the element to retrieve
     * @return The element at the specified position if there is one,
     * {@code null} if not
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    E get(int i) throws IndexOutOfBoundsException;

    //
    // Containment
    //

    /**
     * Checks if this tuple contains the provided object {@code obj}.
     *
     * @param obj The object of which to check for containment
     * @return {@code true} if at least one of this tuple's elements are
     * equal to the provided object {@code obj}
     */
    boolean contains(@Nullable Object obj);

    /**
     * Checks if this tuple contains every element of another tuple.
     *
     * @param t The tuple of which to check for containment
     * @return {@code true} if this tuple contains every element of the other tuple
     */
    boolean containsAll(@Nonnull Tuple<?> t);

    //
    // Sub-operation
    //

    /**
     * Given two indices {@code i1} and {@code i2}, this returns a sub-tuple of
     * this tuple from the first index to the second index. The resulting tuple will have
     * a size of {@code i2 - i1}.
     *
     * @param i1 The starting index of the sub-tuple to get
     * @param i2 The ending index of the sub-tuple to get
     * @return The sub-tuple of the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    default Tuple<E> subTuple(int i1, int i2) throws IndexOutOfBoundsException {
        final E[] subArray = (E[]) new Object[i2 - i1];

        for (int i = i1; i < i2; i++) {
            subArray[i - i1] = get(i);
        }

        return Tuple.of(subArray);
    }

    //
    // Filtration
    //

    /**
     * Tests each element of this tuple with the provided filter function {@code f},
     * collects each element which the filter function has returned {@code true} to,
     * then returns a new tuple containing only the filtered elements of this tuple.
     *
     * @param f The filter function to handle the filtration of this tuple
     * @return The resulting tuple
     */
    @Nonnull
    Tuple<E> filter(@Nonnull Predicate<? super E> f);

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to each element of this tuple,
     * then returns a new tuple containing the resulting elements.
     *
     * @param f   The function of which to apply to each element of this tuple
     * @param <F> The type of element to map this tuple to
     * @return The resulting tuple
     */
    @Nonnull
    <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Between this tuple and the provided tuple {@code t}, this applies the merger function
     * {@code f} to each corresponding pair of elements, then returns a new tuple containing the
     * resulting elements.
     *
     * @param t   The tuple of which to merge this tuple with
     * @param f   The merger function to handle the merging of the two tuples
     * @param <F> The type of element to merge this tuple with
     * @param <G> The type of element to merge the two tuples to
     * @throws IllegalArgumentException When the provided tuple's size is
     *                                  not equal to this tuple's size
     */
    @Nonnull
    <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException;

    //
    // Iteration
    //

    /**
     * Returns an iterator of every element within this tuple, it its correct order.
     *
     * @return An iterator of every element within this tuple
     */
    @Override
    Iterator<E> iterator();

    //
    // Conversion
    //

    /**
     * Returns an array representing the elements of this tuple.
     *
     * @return The array representation of this tuple
     */
    @Nonnull
    SafeArray<E> array();

    /**
     * Converts this tuple into an unmodifiable list, then returns the converted list.
     *
     * @return An unmodifiable list representing the elements of this tuple
     * @see List
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
     * @return {@code true} if the provided object is also a tuple,
     * and the size, composition and order of elements are all equal
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
    @Override
    @Nonnull
    String toString();
}
