package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * An ordered shallowly immutable set of objects.
 *
 * @param <E> The type of element this tuple should hold
 * @see ArrayTuple
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
    // Transformation
    //

    /**
     * Applies the provided transformation function {@code f} to each element
     * of this tuple, then returns a new tuple containing the resulting elements.
     * This operation preserves the type bounds of this tuple.
     *
     * @param f The function of which to apply to each element of this tuple
     * @return The resulting tuple
     */
    @Nonnull
    Tuple<E> transform(@Nonnull UnaryOperator<E> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this tuple,
     * then returns a new tuple containing the resulting elements. This operation
     * does not preserve the type bounds of this tuple.
     *
     * @param f   The function of which to apply to each element of this tuple
     * @param <F> The type of element to map this tuple to (does not require that
     *            it is a subtype of {@code E})
     * @return The resulting tuple
     */
    @Nonnull
    <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Between this tuple and the provided tuple {@code t}, this applies the merger function
     * {@code f} to each corresponding pair of elements, then returns a new tuple containing the
     * resulting elements. This operation does not preserve the type bounds of this tuple.
     *
     * @param t   The tuple of which to merge this tuple with
     * @param f   The merger function to handle the merging of the two tuples
     * @param <F> The type of element to merge the two tuples to (does not require that
     *            it is a subtype of {@code E} or the other tuple's generic component type)
     * @return The resulting tuple
     * @throws IllegalArgumentException When the provided tuple's length is
     *                                  not equal to this tuple's length
     */
    @Nonnull
    <F> Tuple<F> merge(@Nonnull Tuple<? extends E> t, @Nonnull BiFunction<? super E, ? super E, F> f)
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
     * This returns a copied array, and thus changes in the return value will not
     * be reflected to this tuple.
     *
     * @return The array representation of this tuple
     */
    @Nonnull
    E[] array();

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