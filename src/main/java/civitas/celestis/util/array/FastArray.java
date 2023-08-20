package civitas.celestis.util.array;

import civitas.celestis.util.grid.Grid;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A lightweight type-safe array of elements. This class is not thread-safe.
 *
 * @param <E> The type of element to contain
 */
public class FastArray<E> implements Iterable<E>, Serializable {
    //
    // Factory
    //

    /**
     * Creates a new type-safe array from the provided primitive array of elements.
     *
     * @param elements The elements to contain in the type-safe array
     * @param <E>      The type of element to contain
     * @return A new type-safe array constructed from the provided elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> FastArray<E> of(@Nonnull E... elements) {
        return new FastArray<>(elements);
    }

    /**
     * Creates a new type-safe array from the provided primitive array of {@code double}s.
     *
     * @param values The values to contain in the type-safe array
     * @return A new {@link Double}-typed array constructed from the provided values
     */
    @Nonnull
    public static FastArray<Double> of(@Nonnull double... values) {
        return new FastArray<>(Arrays.stream(values).boxed().toArray(Double[]::new));
    }

    /**
     * Creates a new type-safe array from the provided primitive array of {@code float}s.
     *
     * @param values The values to contain in the type-safe array
     * @return A new {@link Float}-typed array constructed from the provided values
     */
    @Nonnull
    public static FastArray<Float> of(@Nonnull float[] values) {
        final Float[] boxed = new Float[values.length];
        for (int i = 0; i < values.length; i++) boxed[i] = values[i];
        return new FastArray<>(boxed);
    }

    /**
     * Creates a new type-safe array from the provided primitive array of {@code long}s.
     *
     * @param values The values to contain in the type-safe array
     * @return A new {@link Long}-typed array constructed from the provided values
     */
    @Nonnull
    public static FastArray<Long> of(@Nonnull long[] values) {
        return new FastArray<>(Arrays.stream(values).boxed().toArray(Long[]::new));
    }

    /**
     * Creates a new type-safe array from the provided primitive array of {@code int}s.
     *
     * @param values The values to contain in the type-safe array
     * @return A new {@link Integer}-typed array constructed from the provided values
     */
    @Nonnull
    public static FastArray<Integer> of(@Nonnull int[] values) {
        return new FastArray<>(Arrays.stream(values).boxed().toArray(Integer[]::new));
    }

    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -2341017871277114974L;

    //
    // Constructors
    //

    /**
     * Creates a new array.
     *
     * @param length The length of this array
     */
    @SuppressWarnings("unchecked")
    public FastArray(int length) {
        this.elements = (E[]) new Object[length];
    }

    /**
     * Creates a new array.
     *
     * @param a The type-safe array of which to copy elements from
     */
    @SuppressWarnings("unchecked")
    public FastArray(@Nonnull FastArray<? extends E> a) {
        this.elements = (E[]) Arrays.stream(a.elements).toArray();
    }

    /**
     * Creates a new array.
     *
     * @param t The tuple of which to copy elements from
     */
    public FastArray(@Nonnull Tuple<? extends E> t) {
        this.elements = t.array();
    }

    /**
     * Creates a new array.
     *
     * @param g The grid of which to copy elements from
     */
    public FastArray(@Nonnull Grid<? extends E> g) {
        this.elements = g.array();
    }

    /**
     * Creates a new array.
     *
     * @param c The collection of which to copy elements from
     */
    @SuppressWarnings("unchecked")
    public FastArray(@Nonnull Collection<? extends E> c) {
        this.elements = (E[]) List.copyOf(c).toArray();
    }

    /**
     * Creates a new array. This constructor is private to prevent
     * ambiguity with other constructors. Use {@link #of(E...)} for public access.
     *
     * @param elements The values to contain in this array
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    private FastArray(@Nonnull E... elements) {
        this.elements = (E[]) Arrays.stream(elements).toArray();
    }

    /**
     * Direct assignment constructor. Use at your own risk.
     *
     * @param elements The primitive array of elements
     * @param ignored  Ignored
     */
    private FastArray(@Nonnull E[] elements, boolean ignored) {
        this.elements = elements;
    }

    //
    // Variables
    //

    /**
     * The array of elements.
     */
    @Nonnull
    protected final E[] elements;

    //
    // Properties
    //

    /**
     * Returns the length of this array.
     *
     * @return The number of elements this array contains
     */
    public final int length() {
        return elements.length;
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
    public boolean contains(@Nullable Object obj) {
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
    public E get(int i) throws IndexOutOfBoundsException {
        return elements[i];
    }

    /**
     * Sets the {@code i}th element of this array.
     *
     * @param i The index to put the provided value at
     * @param v The value of which to assign as the {@code i}th element of this array
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    public void set(int i, E v) throws IndexOutOfBoundsException {
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
    public void fill(E v) {
        apply(old -> v);
    }

    /**
     * Fills this array, but only replaces {@code null} values.
     *
     * @param v The value to fill empty slots of this array with
     */
    public void fillEmpty(E v) {
        replaceAll(null, v);
    }

    /**
     * Fills this array, but only does so if the filter function {@code f}
     * returns {@code true} for the existing element at each position.
     *
     * @param v The value to selectively fill this array with
     * @param f The filter of which to test existing values with
     */
    public void fillIf(E v, @Nonnull Predicate<? super E> f) {
        apply(old -> f.test(old) ? v : old);
    }

    /**
     * Applies the provided update function {@code f} to every element of this array,
     * then assigns the return value to the corresponding index of this array.
     *
     * @param f The function to apply to each element of this array
     */
    public void apply(@Nonnull UnaryOperator<E> f) {
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
    public void replaceFirst(E oldValue, E newValue) {
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
    public void replaceLast(E oldValue, E newValue) {
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
    public void replaceAll(E oldValue, E newValue) {
        apply(v -> Objects.equals(v, oldValue) ? newValue : oldValue);
    }

    //
    // Resizing
    //

    /**
     * Returns a resized array whose elements are mapped from that of this array's elements.
     * If the new array is larger, the oversized part will not be populated, leaving it
     * uninitialized as {@code null}.
     *
     * @param size The size to resize this array to
     * @return The resized array
     */
    @Nonnull
    public FastArray<E> resize(int size) {
        final FastArray<E> result = new FastArray<>(size);
        System.arraycopy(elements, 0, result.elements, 0, Math.min(elements.length, size));
        return result;
    }

    //
    // Sorting
    //

    /**
     * Sorts this array by the provided comparator function {@code c}.
     *
     * @param c The comparator function to sort this array with
     */
    public void sort(@Nonnull Comparator<? super E> c) {
        Arrays.sort(elements, c);
    }

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
    @SuppressWarnings("unchecked")
    public FastArray<E> filter(@Nonnull Predicate<? super E> f) {
        return new FastArray<>((E[]) Arrays.stream(elements).filter(f).toArray(), true);
    }


    //
    // Transformation
    //

    /**
     * Applies the provided transformation function {@code f} to each element of this
     * array, then returns a new array containing the resulting elements.
     * This operation preserves the type bounds of this array.
     *
     * @param f The function of which to apply to each element of this array
     * @return The resulting array
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public FastArray<E> transform(@Nonnull UnaryOperator<E> f) {
        return new FastArray<>((E[]) Arrays.stream(elements).map(f).toArray(), true);
    }

    /**
     * Applies the provided mapper function {@code f} to each element of this array,
     * then returns a new array containing the resulting elements. This operation
     * does not preserve the type bounds of this array.
     *
     * @param f   The function of which to apply to each element of this array
     * @param <F> The type of element to map this array to (does not require to be
     *            a subtype of {@code E})
     * @return The resulting array
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public <F> FastArray<F> map(@Nonnull Function<? super E, F> f) {
        return new FastArray<>((F[]) Arrays.stream(elements).map(f).toArray(), true);
    }

    /**
     * Between this array and the provided array {@code a}, this applies the merger function {@code f}
     * to each corresponding pair of elements, then returns a new array containing the resulting
     * elements. This operation does not preserve the type bounds of this array.
     *
     * @param a   The array of which to merge this array with
     * @param f   The merger function to handle the merging of the two arrays
     * @param <F> The type of element to merge this array with
     * @param <G> The type of element to merge the two arrays to (does not require that it
     *            is a subtype of {@code E} or the other array's generic component type)
     * @return The resulting array
     * @throws IllegalArgumentException When the provided array's length is not equal to
     *                                  this array's length
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public <F, G> FastArray<G> merge(@Nonnull FastArray<F> a, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        if (elements.length != a.elements.length) {
            throw new IllegalArgumentException("Array lengths must match for this operation.");
        }

        final G[] result = (G[]) new Object[elements.length];

        for (int i = 0; i < elements.length; i++) {
            result[i] = f.apply(elements[i], a.elements[i]);
        }

        return new FastArray<>(result, true);
    }

    //
    // Conversion
    //

    /**
     * Returns a primitive array with the same composition and order.
     * This is a shallow copy, and thus changes are not reflected to this array instance.
     *
     * @return The primitive array representation of this array
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public E[] array() {
        return (E[]) Arrays.stream(elements).toArray();
    }

    /**
     * Converts this array into a tuple.
     *
     * @return The tuple representation of this array
     * @see Tuple
     */
    @Nonnull
    public Tuple<E> tuple() {
        return Tuple.of(elements);
    }

    /**
     * Returns an unmodifiable list containing the elements of this array,
     * in the same order as this array is currently sorted in.
     *
     * @return An unmodifiable list containing the elements of this array in the proper order
     * @see List
     */
    @Nonnull
    public List<E> list() {
        return List.copyOf(Arrays.asList(elements));
    }

    /**
     * Returns a stream with this array being the source.
     *
     * @return A stream whose source is this array instance
     * @see Stream
     */
    @Nonnull
    public Stream<E> stream() {
        return Arrays.stream(elements);
    }

    //
    // Iteration
    //

    /**
     * Returns an iterator of every element of this array.
     *
     * @return An iterator of every element of this array
     */
    @Override
    @Nonnull
    public Iterator<E> iterator() {
        return Arrays.stream(elements).iterator();
    }

    /**
     * Performs the provided action {@code a} for each element of this array.
     * The index of the element is provided as the first parameter, and the element itself
     * is provided as the second parameter to the provided action {@code a}.
     *
     * @param a The action to perform for each element of this array
     */
    public void forEach(@Nonnull BiConsumer<Integer, ? super E> a) {
        for (int i = 0; i < elements.length; i++) {
            a.accept(i, elements[i]);
        }
    }

    //
    // Copying
    //

    /**
     * Performs a shallow copy of this array, then returns the copied array.
     *
     * @return A shallow copy of this array
     */
    @Nonnull
    public FastArray<E> copy() {
        return new FastArray<>(elements);
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this array and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a type-safe array,
     * and the size, order, and composition of elements are all equal
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof FastArray<?> a)) return false;
        if (elements.length != a.elements.length) return false;

        for (int i = 0; i < elements.length; i++) {
            if (!Objects.equals(get(i), a.get(i))) return false;
        }

        return true;
    }

    //
    // Serialization
    //

    /**
     * Serializes this array into a string.
     *
     * @return The string representation of this array
     */
    @Nonnull
    public String toString() {
        return Arrays.toString(elements);
    }
}