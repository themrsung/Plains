package civitas.celestis.util.array;

import civitas.celestis.util.function.ToFloatFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A type-safe array. Different implementations of type-safe arrays have different
 * approaches to thread safety. {@link FastArray Fast arrays} drop all thread-safety
 * measures in pursuit of the best possible performance, while {@link SyncArray synchronized}
 * and {@link AtomicArray atomized arrays} provide thread-safety at the cost of reduced
 * performance. The implementation should be chosen according to the specific needs of
 * the corresponding application.
 * <p>
 * Primitive types are supported by specialized array instances such as {@link DoubleArray}
 * or {@link FloatArray}. Primitive array instances can be obtained either through factory
 * methods such as {@link DoubleArray#of(double...)} or primitive mapper methods wuch as
 * {@link #mapToDouble(ToDoubleFunction)}} or {@link #mapToFloat(ToFloatFunction)}.
 * </p>
 *
 * @param <E> The type of element this array should hold
 * @see FastArray
 * @see SyncArray
 * @see AtomicArray
 * @see DoubleArray
 * @see FloatArray
 * @see LongArray
 * @see IntArray
 */
public interface SafeArray<E> extends Iterable<E>, Serializable {
    //
    // Factory
    //

    /**
     * Creates a new type-safe array from the provided array of elements.
     *
     * @param elements The elements to contain in the array
     * @param <E>      The type of element to contain in the array
     * @return A new type-safe array containing the provided elements
     */
    @Nonnull
    @SafeVarargs
    static <E> SafeArray<E> of(@Nonnull E... elements) {
        return FastArray.of(elements);
    }

    /**
     * Creates a new synchronized array from the provided array of elements.
     * @param elements The elements to contain in the array
     * @return A new thread-safe array containing the provided elements
     * @param <E> The type of element to contain in the array
     */
    @Nonnull
    @SafeVarargs
    static <E> SafeArray<E> syncOf(@Nonnull E... elements) {
        return SyncArray.of(elements);
    }

    /**
     * Creates a new atomic array from the provided array of elements.
     * @param elements The elements to contain in the array
     * @return A new thread-safe array containing the provided elements
     * @param <E> The type of element to contain in the array
     */
    @Nonnull
    @SafeVarargs
    static <E> SafeArray<E> atomicOf(@Nonnull E... elements) {
        return AtomicArray.of(elements);
    }

    /**
     * Creates a new type-safe reference array from the provided array of elements.
     * Changes in the type-safe array will be reflected to the original array, as well
     * as from the original array to the type-safe array.
     *
     * @param elements The elements the array should reference
     * @param <E>      The type of element to reference
     * @return A new type-safe reference array referencing the provided elements
     */
    @Nonnull
    @SafeVarargs
    static <E> SafeArray<E> referenceOf(@Nonnull E... elements) {
        return FastArray.referenceOf(elements);
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
    // Containment
    //

    /**
     * Returns whether this array contains the provided object {@code obj}.
     *
     * @param obj The object of which to check for containment
     * @return {@code true} if at least one element of this array is equal to the provided object {@code obj}
     */
    boolean contains(@Nullable Object obj);

    /**
     * Returns whether this array contains multiple objects.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this array contains every element of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<?> i);

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
    E get(int i) throws IndexOutOfBoundsException;

    /**
     * Returns the {@code i}th element of this array, but returns the provided fallback value {@code e}
     * instead of the value at the specified index is {@code null}.
     *
     * @param i The index of the element to get
     * @param e The fallback value to default to when the value is {@code null}
     * @return The {@code i}th element of this array if present, the fallback value {@code e} otherwise
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    E getOrDefault(int i, E e) throws IndexOutOfBoundsException;

    /**
     * Sets the {@code i}th element of this array.
     *
     * @param i The index of the element to set
     * @param e The element of which to set to
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    void set(int i, E e) throws IndexOutOfBoundsException;

    /**
     * Updates the {@code i}th element of this array with the provided update function {@code f}.
     *
     * @param i The index of the element to update
     * @param f The update function of which to apply to the element
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    void update(int i, @Nonnull UnaryOperator<E> f) throws IndexOutOfBoundsException;

    //
    // Bulk Operation
    //

    /**
     * Fills this array with the provided value {@code v}. Every element will be reassigned.
     *
     * @param v The value to fill this array with
     */
    void fill(E v);

    /**
     * Fills every empty slot of this array with the provided value {@code v}. All occurrences of
     * {@code null} will be replaced with the provided value.
     *
     * @param v The value to fill empty slots of this array with
     */
    void fillEmpty(E v);

    /**
     * Fills every slot of this array between the range of {@code [s, e)} with the provided value {@code v}.
     *
     * @param s The starting index at which to start assigning values from
     * @param e The ending index at which to stop assigning values at
     * @param v The value of which to assign to every slot within the specified range
     * @throws IndexOutOfBoundsException When the indices are out of bounds
     */
    void fillRange(int s, int e, E v);

    /**
     * Applies the provided update function {@code f} to each element of this array, then assigns
     * the return value of the function to the corresponding index of this array.
     *
     * @param f The function of which to apply to each element of this array
     */
    void update(@Nonnull UnaryOperator<E> f);

    /**
     * Applies the provided update function {@code f} to each element of this array, then assigns
     * the return value of the function to the corresponding index of this array. The first
     * parameter is the index of the value which was provided.
     *
     * @param f The function of which to apply to each element of this array
     */
    void update(@Nonnull BiFunction<? super Integer, ? super E, E> f);

    /**
     * Replaces all instances of the old value to the new value.
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceAll(E oldValue, E newValue);

    /**
     * Replaces only the first instance of the old value to the new value.
     * (starting from index {@code 0}, and incrementing upwards)
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceFirst(E oldValue, E newValue);

    /**
     * Replaces only the last instance of the old value to the new value.
     * (starting from the last index, and decrementing downwards)
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceLast(E oldValue, E newValue);

    //
    // Sub Operation
    //

    /**
     * Returns a sub-array of this array which represents a portion of this array between
     * the range of {@code [s, e)}. Changes in the sub-array will be reflected to this array.
     * For example, calling {@code a.subArray(1, 3).fill(null);} will have the same effect
     * as setting the values of indices {@code 1, 2} of the original array to {@code null}.
     *
     * @param s The starting index at which to start creating the sub-array (inclusive)
     * @param e The ending index at which to stop creating the sub-array (exclusive)
     * @return The sub-array representing the index range of {@code [s, e)}
     * @throws IndexOutOfBoundsException When the range is invalid, or is out of bounds
     */
    @Nonnull
    SafeArray<E> subArray(int s, int e) throws IndexOutOfBoundsException;

    /**
     * Sets a sub-array of this array to the values of the provided sub-array {@code a}.
     *
     * @param s The starting index at which to start copying values from (inclusive)
     * @param e The ending index at which to stop copying values from (exclusive)
     * @param a The sub-array containing the values to assign to this array
     * @throws IndexOutOfBoundsException When the range is invalid, or is out of bounds
     */
    void setRange(int s, int e, @Nonnull SafeArray<? extends E> a) throws IndexOutOfBoundsException;

    //
    // Resizing
    //

    /**
     * Returns a resized array whose elements are mapped from that of this array's elements.
     * If the new array is larger than this array, the unmappable part will not be populated,
     * leaving it uninitialized as {@code null}.
     *
     * @param size The size to resize this array to
     * @return The resized array
     */
    @Nonnull
    SafeArray<E> resize(int size);

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
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new array containing the return values of the function {@code f}.
     *
     * @param f   The function of which to apply to each element of this array
     * @param <F> The type of element to map this array to
     * @return The resulting array
     */
    @Nonnull
    <F> SafeArray<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new double array containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each element of this array
     * @return The resulting array
     */
    @Nonnull
    DoubleArray mapToDouble(@Nonnull ToDoubleFunction<? super E> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new float array containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each element of this array
     * @return The resulting array
     */
    @Nonnull
    FloatArray mapToFloat(@Nonnull ToFloatFunction<? super E> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new long array containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each element of this array
     * @return The resulting array
     */
    @Nonnull
    LongArray mapToLong(@Nonnull ToLongFunction<? super E> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new integer array containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each element of this array
     * @return The resulting array
     */
    @Nonnull
    IntArray mapToInt(@Nonnull ToIntFunction<? super E> f);

    /**
     * Between this array and the provided array {@code a}, this applies the merger function {@code f}
     * to each corresponding pair of elements, then returns a new array containing the return values
     * of the merger function {@code f}. In other words, this merges the two arrays into a new array
     * using the provided merger function {@code f}.
     *
     * @param a   The array of which to merge this array with
     * @param f   The merger function to handle the merging of the two arrays
     * @param <F> The type of element to merge this array with
     * @param <G> The type of element to merge the two arrays to
     * @return The resulting array
     * @throws IllegalArgumentException When the provided array {@code a}'s length is different from
     *                                  that of this array's length
     */
    @Nonnull
    <F, G> SafeArray<G> merge(@Nonnull SafeArray<F> a, @Nonnull BiFunction<? super E, ? super F, ? extends G> f)
            throws IllegalArgumentException;

    /**
     * Append the provided array {@code a} to the end of this array, then returns the resulting array.
     * @param a The array of which to append to the end of this array
     * @return The appended array
     */
    @Nonnull
    default SafeArray<E> append(@Nonnull SafeArray<? extends E> a) {
        final int l1 = length();
        final int l2 = a.length();

        final SafeArray<E> result = resize(l1 + l2);
        result.setRange(l1, l1 + l2, a);
        return result;
    }

    /**
     * Prepends the provided array {@code a} to the front of this array, then returns the resulting array.
     * @param a The array of which to prepend to the front of this array
     * @return The prepended array
     */
    @Nonnull
    default SafeArray<E> prepend(@Nonnull SafeArray<? extends E> a) {
        return a.map(v -> (E) v).append(this);
    }

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
     * Returns a primitive array containing the elements of this array in their proper order.
     *
     * @return The primitive array representation of this array
     */
    @Nonnull
    E[] array();

    /**
     * Returns a stream whose source is the elements of this array.
     *
     * @return A stream whose source is the elements of this array
     */
    @Nonnull
    Stream<E> stream();

    /**
     * Returns an unmodifiable list containing the elements of this array in their proper order.
     *
     * @return The list representation of this array
     * @throws NullPointerException When this array contains at least one instance of {@code null}
     */
    @Nonnull
    List<E> list();

    /**
     * Returns a tuple containing the elements of this array in their proper order.
     * Unlike {@link #list()}, this operation cannot fail as tuples inherently support the
     * usage of {@code null} as their values.
     *
     * @return The tuple representation of this array
     */
    @Nonnull
    Tuple<E> tuple();

    //
    // Equality
    //

    /**
     * Checks for equality between this array and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the object is also a type-safe array, and the number of elements,
     * the order of the elements, and the elements' values are all equal
     */
    @Override
    boolean equals(@Nullable Object obj);

    //
    // Serialization
    //

    /**
     * Serializes this array into a string.
     *
     * @return The string representation of this array
     */
    @Nonnull
    @Override
    String toString();
}
