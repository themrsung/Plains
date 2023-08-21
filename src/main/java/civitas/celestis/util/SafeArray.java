package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A type-safe array. Type-safe arrays have multiple implementations with different
 * approaches when it comes to thread safety. {@link FastArray}s have no built-in thread
 * safety measures and are designed to be very fast, while {@link SyncArray}s have
 * synchronization measures in place to enable multithreaded usage.
 * <p>
 * Arrays can be preferred to {@link Collection}s in that they have a fixed size,
 * which cannot be changed without re-instantiation. This makes the dataset more efficient
 * when it comes to memory management. Type-safe arrays can always be converted to
 * various subtypes of collections by built-in conversion methods such as {@link #collect()},
 * {@link #list()}, {@link #arrayList()}, or {@link #set()}.
 * </p>
 *
 * @param <E> The type of element this array should hold
 * @see FastArray
 * @see SyncArray
 */
public interface SafeArray<E> extends Iterable<E>, Serializable {
    //
    // Factory
    //

    /**
     * Creates a new type-safe array from the provided array of elements,
     * then returns the newly created array instance.
     *
     * @param elements The elements this array should contain
     * @param <E>      The type of element the array should hold
     * @return The new array instance created from the provided elements
     */
    @Nonnull
    @SafeVarargs
    static <E> SafeArray<E> of(@Nonnull E... elements) {
        return FastArray.of(elements);
    }

    /**
     * Creates a new thread-safe array from the provided array of elements,
     * then returns the newly created array instance.
     *
     * @param elements The elements this array should contain
     * @param <E>      The type of element the array should hold
     * @return The new array instance created from the provided elements
     */
    @Nonnull
    @SafeVarargs
    static <E> SafeArray<E> syncOf(@Nonnull E... elements) {
        return SyncArray.of(elements);
    }

    /**
     * Returns a new type-safe array whose elements are populated from
     * that of the provided collection {@code c}.
     *
     * @param c   The collection of which to copy elements from
     * @param <E> The type of element the array should hold
     * @return A new type-safe array containing the elements of the provided collection {@code c}
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    static <E> SafeArray<E> copyOf(@Nonnull Collection<E> c) {
        return FastArray.of((E[]) c.toArray());
    }

    /**
     * Returns a new type-safe array whose elements are populated from
     * that of the provided array {@code a}.
     *
     * @param a   The array of which to copy elements from
     * @param <E> The type of element the array should hold
     * @return A new type-safe array containing the elements of the provided array {@code a}
     */
    @Nonnull
    static <E> SafeArray<E> copyOf(@Nonnull SafeArray<E> a) {
        return new FastArray<>(a);
    }

    /**
     * Returns a new thread-safe array whose elements are populated from
     * that of the provided collection {@code c}.
     *
     * @param c   The collection of which to copy elements from
     * @param <E> The type of element the array should hold
     * @return A new type-safe array containing the elements of the provided collection {@code c}
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    static <E> SafeArray<E> syncCopyOf(@Nonnull Collection<E> c) {
        return SyncArray.of((E[]) c.toArray());
    }

    /**
     * Returns a new thread-safe array whose elements are populated from
     * that of the provided array {@code a}.
     *
     * @param a   The array of which to copy elements from
     * @param <E> The type of element the array should hold
     * @return A new type-safe array containing the elements of the provided array {@code a}
     */
    @Nonnull
    static <E> SafeArray<E> syncCopyOf(@Nonnull SafeArray<E> a) {
        return new SyncArray<>(a);
    }

    //
    // Properties
    //

    /**
     * Returns the length of this array.
     *
     * @return The length of this array
     */
    int length();

    //
    // Containment
    //

    /**
     * Checks if this array contains the provided object {@code obj}.
     *
     * @param obj The object of which to check for containment
     * @return {@code true} if this array contains the provided object
     */
    boolean contains(@Nullable Object obj);

    /**
     * Checks if this array contains multiple objects.
     *
     * @param i The iterable object containing the values to check for containment
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
     * Returns the {@code i}th element of this array, but returns the fallback value instead
     * if the value at the specified index is {@code null}.
     *
     * @param i        The index of the element to get
     * @param fallback The fallback value to return instead of {@code null}
     * @return The {@code i}th element of this array if present, the fallback value otherwise
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    E getOrDefault(int i, E fallback) throws IndexOutOfBoundsException;

    /**
     * Sets the {@code i}th element of this array to the provided value {@code v}.
     *
     * @param i The index of the element to set
     * @param v The value to set to
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    void set(int i, E v) throws IndexOutOfBoundsException;


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
     * Fills every slot of this array between the range of {@code [i1, i2)} with the provided value {@code v}.
     *
     * @param i1 The starting index at which to start assigning values from
     * @param i2 The ending index at which to stop assigning values at
     * @param v  The value of which to assign to every slot within the specified range
     * @throws IndexOutOfBoundsException When the indices are out of bounds
     */
    void fillRange(int i1, int i2, E v) throws IndexOutOfBoundsException;

    /**
     * Applies the provided update function {@code f} to each element of this array, then assigns
     * the return value of the function to the corresponding index of this array.
     *
     * @param f The function of which to apply to each element of this array
     */
    void apply(@Nonnull UnaryOperator<E> f);

    /**
     * Replaces all instances of the old value to the new value.
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceAll(E oldValue, E newValue);

    /**
     * Replaces only the first instance of the old value to the new value. (starting from index {@code 0})
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceFirst(E oldValue, E newValue);

    /**
     * Replaces only the last instance of the old value to the new value. (starting from the last index)
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceLast(E oldValue, E newValue);

    //
    // Sub Operation
    //

    /**
     * Returns a sub-array of this array whose elements are populated from that of this array
     * between the range of {@code [i1, i2)}.
     *
     * @param i1 The starting index at which to start copying elements from
     * @param i2 The ending index at which to stop copying elements at
     * @return The sub-array of this array of the specified range
     * @throws IndexOutOfBoundsException When the range is invalid, or the indices are out of bounds
     */
    @Nonnull
    SafeArray<E> subArray(int i1, int i2) throws IndexOutOfBoundsException;


    /**
     * Sets a sub-array of this array, populating the elements between the range of {@code [i1, i2)}
     * to the elements of the provided sub-array {@code a}.
     *
     * @param i1 The starting index at which to start assigning elements from
     * @param i2 The ending index at which to stop assigning elements at
     * @param a  The sub-array of which to copy elements from
     * @throws IndexOutOfBoundsException When the range is invalid, or the indices are out of bounds
     */
    void setRange(int i1, int i2, @Nonnull SafeArray<? extends E> a) throws IndexOutOfBoundsException;

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
    // Sorting
    //

    /**
     * Sorts this array in the natural ascending order of elements. The type of this array's elements
     * {@code E} must be an instance of {@link Comparable} in order for this operation to complete successfully.
     *
     * @throws UnsupportedOperationException When the generic type {@code E} does not implement the {@link Comparable}
     *                                       interface, and thus is not sortable by the default comparator
     * @see Arrays#sort(Object[])
     */
    void sort();

    /**
     * Sorts this array by the provided comparator function {@code f}.
     *
     * @param f The comparator function to sort this array with
     */
    void sort(@Nonnull Comparator<? super E> f);

    /**
     * Randomizes this array's order, rearranging the elements in a randomized manner.
     */
    void shuffle();

    //
    // Filtration
    //

    /**
     * Tests each element of this array with the provided filter function {@code f},
     * collects every element which the filter function returns {@code true} to, then
     * returns a new array containing only the filtered elements of this array.
     *
     * @param f The filter function to handle the filtration of this array
     * @return The filtered array
     */
    @Nonnull
    SafeArray<E> filter(@Nonnull Predicate<? super E> f);

    //
    // Mapping
    //

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new array containing the resulting elements.
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> The type of element to map this array to
     * @return The resulting array
     */
    @Nonnull
    <F> SafeArray<F> map(@Nonnull Function<? super E, F> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new collection containing the resulting elements.
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> The type of element to map this array to
     * @return The resulting collection
     * @see Collection
     */
    @Nonnull
    <F> Collection<F> mapToCollection(@Nonnull Function<? super E, F> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new list containing the resulting elements.
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> The type of element to map this array to
     * @return The resulting list
     * @see List
     */
    @Nonnull
    <F> List<F> mapToList(@Nonnull Function<? super E, F> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new set containing the resulting elements.
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> The type of element to map this array to
     * @return The resulting set
     * @see Set
     */
    @Nonnull
    <F> Set<F> mapToSet(@Nonnull Function<? super E, F> f);


    /**
     * Between this array and the provided array {@code a}, this applies the merger
     * function {@code f} to each corresponding pair of elements, then returns
     * a new array containing the resulting elements.
     *
     * @param a   The array of which to merge this array with
     * @param f   The merger function to handle the merging of the two arrays
     * @param <F> The type of element to merge this array with
     * @param <G> The type of element to merge the two arrays to
     * @return The merged array
     * @throws IllegalArgumentException When the provided array {@code a}'s length is not equal
     *                                  to that of this array's length
     */
    @Nonnull
    <F, G> SafeArray<G> merge(@Nonnull SafeArray<F> a, @Nonnull BiFunction<? super E, ? super F, G> f);

    //
    // Conversion
    //

    /**
     * Returns a primitive array with the same composition and order of this array.
     * A shallow copy is taken in the process, and thus changes will not be reflected between
     * the primitive array and this type-safe array.
     *
     * @return The primitive array representation of this array
     */
    @Nonnull
    E[] array();

    /**
     * Returns a stream whose source is the elements of this array.
     *
     * @return A stream whose source is this type-safe array instance
     * @see Stream
     */
    @Nonnull
    Stream<E> stream();

    /**
     * Returns an unmodifiable collection whose elements are populated with that of this array's elements,
     * having the same composition and order as this array.
     *
     * @return The collection representation of this array
     * @throws NullPointerException When this array contains at least one element whose value is {@code null}
     */
    @Nonnull
    Collection<E> collect();

    /**
     * Returns an unmodifiable set whose elements are populated with that of this array's elements,
     * having the same composition and order as this array. (excluding duplicates)
     *
     * @return The set representation of this array
     * @throws NullPointerException When this array contains at least one element whose value is {@code null}
     * @see Set
     */
    @Nonnull
    Set<E> set();

    /**
     * Returns an unmodifiable list whose elements are populated with that of this array's elements,
     * having the same composition and order as this array.
     *
     * @return The list representation of this array
     * @throws NullPointerException When this array contains at least one element whose value is {@code null}
     * @see List
     */
    @Nonnull
    List<E> list();


    /**
     * Returns a modifiable array list whose elements are populated with that of this array's elements,
     * having the same composition and order as this array. A shallow copy is performed in the process,
     * and changes will not be reflected to this array instance.
     *
     * @return The array list representation of this array
     * @see ArrayList
     */
    @Nonnull
    ArrayList<E> arrayList();

    /**
     * Returns a tuple whose elements are populated with that of this array's elements, having the same
     * composition and order as this array. Since tuples are immutable by definition, this effectively
     * finalizes the current composition and order of this array into an immutable dataset.
     *
     * @return The tuple representation of this array
     */
    @Nonnull
    Tuple<E> tuple();

    //
    // Iteration
    //

    /**
     * Returns an iterator object whose source is this array instance.
     *
     * @return The iterator of this array
     */
    @Nonnull
    @Override
    Iterator<E> iterator();

    //
    // Copying
    //

    /**
     * Returns a new array whose composition and order of elements are equal to that of this array.
     *
     * @return A shallow copy of this array
     */
    @Nonnull
    SafeArray<E> copy();

    //
    // Equality
    //

    /**
     * Checks for equality between this array and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a type-safe array, and the composition
     * and order of elements are both equal to that of this array
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
