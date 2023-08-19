package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A thread-safe array of elements. This array also provides type-safety via generics.
 * For-each iteration is not supported by design. Copy the array into a {@link Tuple} or
 * a {@link List} in order to use for-each iteration. ({@link #tuple()}, {@link #list()})
 *
 * @param <E> The type of element to contain
 */
public class SafeArray<E> {
    //
    // Factory
    //

    /**
     * Creates a new thread-safe array from the provided primitive array of elements.
     *
     * @param elements The elements to contain in the thread-safe array
     * @param <E>      The type of element to contain
     * @return A new thread-safe array constructed from the provided elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> SafeArray<E> of(@Nonnull E... elements) {
        final SafeArray<E> instance = new SafeArray<>(elements.length);
        System.arraycopy(elements, 0, instance.elements, 0, elements.length);
        return instance;
    }

    //
    // Constructors
    //

    /**
     * Creates a new thread-safe array.
     *
     * @param length The length of this array
     */
    @SuppressWarnings("unchecked")
    public SafeArray(int length) {
        this.elements = (E[]) new Object[length];
    }

    //
    // Variables
    //

    /**
     * The array of elements this container class is holding.
     * It is important that this stays private in order to ensure thread safety.
     */
    @Nonnull
    private final E[] elements;

    //
    // Properties
    //

    /**
     * Returns the length of this array.
     *
     * @return The number of elements this array contains
     */
    public final int length() {
        /*
         * There is no need for synchronization here.
         */
        return elements.length;
    }

    //
    // Copy
    //

    /**
     * Performs a shallow copy of this array, then returns the new array instance.
     *
     * @return A shallow copy of this array instance
     */
    @Nonnull
    public final synchronized SafeArray<E> copy() {
        final SafeArray<E> copied = new SafeArray<>(elements.length);
        System.arraycopy(elements, 0, copied.elements, 0, elements.length);
        return copied;
    }

    //
    // Containment
    //

    /**
     * Checks if this array contains the provided object {@code obj}.
     *
     * @param obj The object to check for containment
     * @return {@code true} if at least one of this tuple's elements are equal
     * to the provided object {@code obj}
     */
    public final synchronized boolean contains(@Nullable Object obj) {
        for (final E element : elements) {
            if (Objects.equals(element, obj)) return true;
        }

        return false;
    }

    //
    // Accessors
    //

    /**
     * Returns the {@code i}th element of this array.
     *
     * @param i The index of the element to get
     * @return The {@code i}th element of this array
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    public final synchronized E get(int i) throws IndexOutOfBoundsException {
        return elements[i];
    }

    /**
     * Sets the {@code i}th element of this array.
     *
     * @param i The index to put the provided value at
     * @param v The value of which to assign as the {@code i}th element of this array
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    public final synchronized void set(int i, E v) throws IndexOutOfBoundsException {
        elements[i] = v;
    }

    //
    // Bulk-Operation
    //

    /**
     * Fills this array with the provided value. Every element will be
     * set to the provided value {@code v}.
     *
     * @param v The value to fill this array with
     */
    public final synchronized void fill(E v) {
        update(old -> v);
    }

    /**
     * Fills this array, but only replaces {@code null} values.
     *
     * @param v The value to fill empty slots of this array with
     */
    public final synchronized void fillEmpty(E v) {
        replaceAll(null, v);
    }

    /**
     * Fills this array, but only does so if the filter function {@code f}
     * returns {@code true} for the existing element at each position.
     *
     * @param v The value to selectively fill this array with
     * @param f The filter of which to test existing values with
     */
    public final synchronized void fillIf(E v, @Nonnull Predicate<? super E> f) {
        update(old -> f.test(old) ? v : old);
    }

    /**
     * Applies the provided update function {@code f} to every element of this array,
     * then assigns the return value to the corresponding index of this array.
     *
     * @param f The function to apply to each element of this array
     */
    public final synchronized void update(@Nonnull UnaryOperator<E> f) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = f.apply(elements[i]);
        }
    }

    /**
     * Replaces the first instance of the old value to the new value, starting from index {@code 0}.
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    public final synchronized void replaceFirst(E oldValue, E newValue) {
        for (int i = 0; i < elements.length; i++) {
            if (Objects.equals(elements[i], oldValue)) {
                elements[i] = newValue;
                return;
            }
        }
    }

    /**
     * Replaces the last instance of the old value to the new value, starting from the last index.
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    public final synchronized void replaceLast(E oldValue, E newValue) {
        for (int i = (elements.length - 1); i >= 0; i--) {
            if (Objects.equals(elements[i], oldValue)) {
                elements[i] = newValue;
                return;
            }
        }
    }

    /**
     * Replaces all instances of the old value to the new value.
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    public final synchronized void replaceAll(E oldValue, E newValue) {
        update(v -> Objects.equals(v, oldValue) ? newValue : oldValue);
    }

    //
    // Sub-operation
    //

    /**
     * Given two indices {@code i1} and {@code i2}, this returns a sub-array of
     * this array from the first index to the second index. The resulting array will have
     * a size of {@code i2 - i1}.
     *
     * @param i1 The starting index of the sub-array to get
     * @param i2 The ending index of the sub-array to get
     * @return The sub-array of the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    public final synchronized SafeArray<E> subArray(int i1, int i2) throws IndexOutOfBoundsException {
        final SafeArray<E> subArray = new SafeArray<>(i2 - i1);

        /*
         * Direct array manipulation here is fine.
         * No other class will have access to this sub-array yet.
         */

        System.arraycopy(elements, i1, subArray.elements, 0, i2 - i1);
        return subArray;
    }

    //
    // Conversion
    //

    /**
     * Converts this array into a tuple.
     *
     * @return The tuple representation of this array
     */
    @Nonnull
    public final synchronized Tuple<E> tuple() {
        /*
         * While tuples are immutable, streams are still used in the process of creating them.
         * Thus, synchronization is still required.
         */
        return Tuple.of(elements);
    }

    /**
     * Returns an unmodifiable list containing the elements of this array,
     * in the same order as this array is currently sorted in.
     *
     * @return An unmodifiable list containing the elements of this array in the proper order
     */
    @Nonnull
    public final synchronized List<E> list() {
        return List.copyOf(Arrays.asList(elements));
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this array and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a thread-safe array,
     * and the size, order, and composition of elements are all equal
     */
    @Override
    public synchronized boolean equals(@Nullable Object obj) {
        if (!(obj instanceof SafeArray<?> a)) return false;
        if (elements.length != a.elements.length) return false;

        for (int i = 0; i < elements.length; i++) {
            if (!Objects.equals(get(i), a.get(i))) return false;
        }

        return true;
    }
}
