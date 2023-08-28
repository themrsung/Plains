package civitas.celestis.util.tuple;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

/**
 * The base class for all tuples, including primitive specialized tuples.
 *
 * @param <E> The type of element this tuple should hold
 */
public interface BaseTuple<E> extends Serializable {
    //
    // Static Methods
    //

    /**
     * Checks for equality between two instances of {@link BaseTuple tuples}. This will return
     * {@code true} if the size, order of elements, and the elements' values are all equal.
     * In other words, this returns {@code true} if the list representations are equal. (or
     * both tuples are {@code null}, in which case {@code null == null})
     *
     * @param t1 The first tuple to compare
     * @param t2 The second tuple to compare
     * @return {@code true} if the tuples are considered equal according to the criteria mentioned above
     */
    static boolean equals(@Nullable BaseTuple<?> t1, @Nullable BaseTuple<?> t2) {
        if (t1 == null) return t2 == null;
        if (t2 == null) return false;

        return t1.list().equals(t2.list());
    }

    /**
     * Checks for equality between two instances of {@link BaseTuple tuples} without regard
     * to their elements' order. This will return {@code true} if the sizes are equal, and the
     * first tuple contains every element of the second tuple. (or both tuples are {@code null},
     * in which case {@code null == null})
     *
     * @param t1 The first tuple to compare
     * @param t2 The second tuple to compare
     * @return {@code true} if the tuples are considered equal according to the criteria mentioned above
     */
    @SuppressWarnings("all")
    static boolean equalsIgnoreOrder(@Nullable BaseTuple<?> t1, @Nullable BaseTuple<?> t2) {
        if (t1 == null) return t2 == null;
        if (t2 == null) return false;

        if (t1.size() != t2.size()) return false;

        final List<?> l1 = t1.list();
        final List<?> l2 = t2.list();

        return new HashSet<>(l1).containsAll(l2);
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
    // Conversion
    //

    /**
     * Returns an unmodifiable list containing the components of this tuple in their proper order.
     *
     * @return The list representation of this tuple
     */
    @Nonnull
    List<E> list();
}
