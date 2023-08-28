package civitas.celestis.util.array;

import civitas.celestis.util.tuple.LongTuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.*;
import java.util.stream.LongStream;

/**
 * A type-safe array of primitive {@code long}s.
 *
 * @see SafeArray
 */
public interface LongArray extends BaseArray<Long> {
    //
    // Factory
    //

    /**
     * Creates a new type-safe array from the provided array of values.
     *
     * @param values The values to contain in the array
     * @return A new type-safe array containing the provided values
     */
    @Nonnull
    static LongArray of(@Nonnull long... values) {
        return LongFastArray.of(values);
    }

    /**
     * Creates a new type-safe reference array from the provided array of values.
     * Changes in the type-safe array will be reflected to the original array, as well
     * as from the original array to the type-safe array.
     *
     * @param values The values the array should reference
     * @return A new type-safe reference array referencing the provided values
     */
    @Nonnull
    static LongArray referenceOf(@Nonnull long... values) {
        return LongFastArray.referenceOf(values);
    }

    /**
     * Creates a new type-safe array from the provided stream of values.
     *
     * @param s The stream of which to use as the source of the array
     * @return A new type-safe array containing the provided stream's values
     */
    @Nonnull
    static LongArray from(@Nonnull LongStream s) {
        return LongFastArray.referenceOf(s.toArray());
    }


    //
    // Properties
    //

    /**
     * Returns the length of this array.
     *
     * @return The number of elements this array contains
     */
    @Override
    int length();

    //
    // Containment
    //

    /**
     * Returns whether this array contains the provided value {@code v}.
     *
     * @param v The value to check for containment
     * @return {@code true} if at least one element of this array is equal to the provided value {@code v}
     */
    boolean contains(long v);

    /**
     * Returns whether this array contains multiple objects.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this array contains every element of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<Long> i);

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
    long get(int i) throws IndexOutOfBoundsException;

    /**
     * Sets the {@code i}th element of this array.
     *
     * @param i The index of the element to set
     * @param e The element of which to set to
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    void set(int i, long e) throws IndexOutOfBoundsException;

    /**
     * Updates the {@code i}th element of this array with the provided update function {@code f}.
     *
     * @param i The index of the element to update
     * @param f The update function of which to apply to the element
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    void update(int i, @Nonnull LongUnaryOperator f) throws IndexOutOfBoundsException;

    //
    // Bulk Operation
    //

    /**
     * Fills this array with the provided value {@code v}. Every element will be reassigned.
     *
     * @param v The value to fill this array with
     */
    void fill(long v);

    /**
     * Fills every slot of this array between the range of {@code [s, e)} with the provided value {@code v}.
     *
     * @param s The starting index at which to start assigning values from
     * @param e The ending index at which to stop assigning values at
     * @param v The value of which to assign to every slot within the specified range
     * @throws IndexOutOfBoundsException When the indices are out of bounds
     */
    void fillRange(int s, int e, long v);


    /**
     * Applies the provided update function {@code f} to each element of this array, then assigns
     * the return value of the function to the corresponding index of this array.
     *
     * @param f The function of which to apply to each element of this array
     */
    void update(@Nonnull LongUnaryOperator f);


    /**
     * Applies the provided update function {@code f} to each element of this array, then assigns
     * the return value of the function to the corresponding index of this array. The first
     * parameter is the index of the value which was provided.
     *
     * @param f The function of which to apply to each element of this array
     */
    void update(@Nonnull BiFunction<? super Integer, ? super Long, Long> f);

    /**
     * Replaces all instances of the old value to the new value.
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceAll(long oldValue, long newValue);

    /**
     * Replaces only the first instance of the old value to the new value.
     * (starting from index {@code 0}, and incrementing upwards)
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceFirst(long oldValue, long newValue);

    /**
     * Replaces only the last instance of the old value to the new value.
     * (starting from the last index, and decrementing downwards)
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceLast(long oldValue, long newValue);

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
    LongArray subArray(int s, int e) throws IndexOutOfBoundsException;

    /**
     * Sets a sub-array of this array to the values of the provided sub-array {@code a}.
     *
     * @param s The starting index at which to start copying values from (inclusive)
     * @param e The ending index at which to stop copying values from (exclusive)
     * @param a The sub-array containing the values to assign to this array
     * @throws IndexOutOfBoundsException When the range is invalid, or is out of bounds
     */
    void setRange(int s, int e, @Nonnull LongArray a) throws IndexOutOfBoundsException;

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
    LongArray resize(int size);

    //
    // Ordering
    //

    /**
     * Shuffles this array, randomizing its elements' order.
     */
    @Override
    void shuffle();

    /**
     * Sorts this array by its natural ascending order.
     */
    @Override
    void sort();

    /**
     * Sorts this array using the provided comparator function {@code c}.
     *
     * @param c The comparator function of which to sort this array with
     */
    @Override
    void sort(@Nonnull Comparator<? super Long> c);

    //
    // Filtration
    //

    /**
     * Tests each element of this array using the provided predicate {@code f},
     * collects all elements the predicate returns {@code true} to, then returns a new
     * array containing only the filtered elements.
     *
     * @param f The predicate to use to filter this array
     * @return The filtered array
     */
    @Nonnull
    default LongArray filter(@Nonnull LongPredicate f) {
        return LongArray.from(stream().filter(f));
    }

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new array containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each element of this array
     * @return The resulting array
     */
    @Nonnull
    LongArray map(@Nonnull LongUnaryOperator f);

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new array containing the return values of the function {@code f}.
     *
     * @param f   The function of which to apply to each element of this array
     * @param <F> The type of element to map this array to
     * @return The resulting array
     */
    @Nonnull
    <F> SafeArray<F> mapToObj(@Nonnull LongFunction<? extends F> f);

    /**
     * Between this array and the provided array {@code a}, this applies the merger function {@code f}
     * to each corresponding pair of elements, then returns a new array containing the return values
     * of the merger function {@code f}. In other words, this merges the two arrays into a new array
     * using the provided merger function {@code f}.
     *
     * @param a The array of which to merge this array with
     * @param f The merger function to handle the merging of the two arrays
     * @return The resulting array
     * @throws IllegalArgumentException When the provided array {@code a}'s length is different from
     *                                  that of this array's length
     */
    @Nonnull
    LongArray merge(@Nonnull LongArray a, @Nonnull LongBinaryOperator f) throws IllegalArgumentException;

    /**
     * Append the provided array {@code a} to the end of this array, then returns the resulting array.
     *
     * @param a The array of which to append to the end of this array
     * @return The appended array
     */
    @Nonnull
    default LongArray append(@Nonnull LongArray a) {
        final int l1 = length();
        final int l2 = a.length();

        final LongArray result = resize(l1 + l2);
        result.setRange(l1, l1 + l2, a);
        return result;
    }

    /**
     * Prepends the provided array {@code a} to the front of this array, then returns the resulting array.
     *
     * @param a The array of which to prepend to the front of this array
     * @return The prepended array
     */
    @Nonnull
    default LongArray prepend(@Nonnull LongArray a) {
        return a.append(this);
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
    Iterator<Long> iterator();

    /**
     * Executes the provided action {@code a} once for each element of this array.
     * A reference to the element is provided as the first parameter of the action.
     *
     * @param a The action to be performed for each element
     */
    @Override
    void forEach(@Nonnull Consumer<? super Long> a);

    /**
     * Executes the provided action {@code a} once for each element of this array.
     * The index of the element is provided as the first parameter of the action,
     * and a reference to the element is provided as the second parameter of the action.
     *
     * @param a The action to be performed for each element
     */
    @Override
    void forEach(@Nonnull BiConsumer<? super Integer, ? super Long> a);

    //
    // Conversion
    //

    /**
     * Returns a primitive array containing the elements of this array in their proper order.
     *
     * @return The primitive array representation of this array
     */
    @Nonnull
    long[] array();

    /**
     * Returns a stream whose source is the elements of this array.
     *
     * @return A stream whose source is the elements of this array
     * @see LongStream
     */
    @Nonnull
    LongStream stream();

    /**
     * Returns an unmodifiable list containing the elements of this array in their proper order.
     *
     * @return The list representation of this array
     * @throws NullPointerException When this array contains at least one instance of {@code null}
     * @see List
     */
    @Nonnull
    @Override
    List<Long> list();

    /**
     * Returns a tuple containing the elements of this array in their proper order.
     * Unlike {@link #list()}, this operation cannot fail as tuples inherently support the
     * usage of {@code null} as their values.
     *
     * @return The tuple representation of this array
     * @see LongTuple
     */
    @Nonnull
    LongTuple tuple();

    /**
     * Returns an array containing the elements of this array in their boxed form.
     *
     * @return The boxed object representation of this array
     * @see SafeArray
     */
    @Nonnull
    SafeArray<Long> boxed();

    //
    // Equality
    //

    /**
     * Checks for equality between this array and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the object is also a long array, and the number of elements,
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
