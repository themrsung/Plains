package civitas.celestis.util.array;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The base class for all type-safe arrays, including primitive specialized arrays.
 *
 * @param <E> The type of element this array should hold
 */
public interface BaseArray<E> extends Iterable<E>, Serializable {
    //
    // Static Methods
    //

    /**
     * Checks for equality between two instances of {@link BaseArray arrays}. This will return
     * {@code true} if the length, order of elements, and the elements' values are all equal.
     * In other words, this returns {@code true} if the list representations are equal. (or
     * both arrays are {@code null}, in which case {@code null == null})
     *
     * @param a1 The first array to compare
     * @param a2 The second array to compare
     * @return {@code true} if the arrays are considered equal according to the criteria mentioned above
     */
    static boolean equals(@Nullable BaseArray<?> a1, @Nullable BaseArray<?> a2) {
        if (a1 == null) return a2 == null;
        if (a2 == null) return false;

        return a1.list().equals(a2.list());
    }

    //
    // Properties
    //

    /**
     * Returns the length of this array.
     *
     * @return The number of elements this array contains
     */
    int length();

    //
    // Ordering
    //

    /**
     * Shuffles this array, randomizing its elements' order.
     */
    void shuffle();

    /**
     * Sorts this array by its natural ascending order. This operation will only succeed
     * if the element {@code E} is an instance of {@link Comparable}.
     *
     * @throws UnsupportedOperationException When at least one element cannot be cast to {@link Comparable}
     */
    void sort() throws UnsupportedOperationException;

    /**
     * Sorts this array using the provided comparator function {@code c}.
     *
     * @param c The comparator function of which to sort this array with
     */
    void sort(@Nonnull Comparator<? super E> c);

    //
    // Iteration
    //

    /**
     * Returns an iterator over every element of this array.
     *
     * @return An iterator over every element of this array
     */
    @Nonnull
    @Override
    Iterator<E> iterator();

    /**
     * Executes the provided action {@code a} once for each element of this array.
     * A reference to the element is provided as the first parameter of the action.
     *
     * @param a The action to be performed for each element
     */
    @Override
    void forEach(@Nonnull Consumer<? super E> a);

    /**
     * Executes the provided action {@code a} once for each element of this array.
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
     * Returns an unmodifiable list containing the elements of this array in their proper order.
     *
     * @return The list representation of this array
     * @throws NullPointerException When this array contains at least one instance of {@code null}
     */
    @Nonnull
    List<E> list();
}
