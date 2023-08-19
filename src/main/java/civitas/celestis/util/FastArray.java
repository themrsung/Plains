package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * A lightweight type-safe array of elements. This class is not thread-safe.
 * @param <E> The type of element to contain
 */
public class FastArray<E> implements Iterable<E>, Serializable {
    //
    // Factory
    //

    /**
     * Creates a new type-safe array from the provided primitive array of elements.
     * @param elements The elements to contain in the type-safe array
     * @return A new type-safe array constructed from the provided elements
     * @param <E> The type of element to contain
     */
    @Nonnull
    @SafeVarargs
    static <E> FastArray<E> of(@Nonnull E... elements) {
        return new FastArray<>(elements);
    }

    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 436854502580923552L;

    //
    // Constructors
    //

    /**
     * Creates a new array.
     * @param length The length of this array
     */
    @SuppressWarnings("unchecked")
    public FastArray(int length) {
        this.elements = (E[]) new Object[length];
    }

    /**
     * Creates a new array.
     * @param a The type-safe array of which to copy elements from
     */
    @SuppressWarnings("unchecked")
    public FastArray(@Nonnull FastArray<? extends E> a) {
        this.elements = (E[]) Arrays.stream(a.elements).toArray();
    }

    /**
     * Creates a new array. This constructor is private to prevent
     * ambiguity with other constructors. Use {@link #of(E...)} for public access.
     * @param elements The values to contain in this array
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    private FastArray(@Nonnull E... elements) {
        this.elements = (E[]) Arrays.stream(elements).toArray();
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
    // Conversion
    //

    /**
     * Converts this array into a tuple.
     *
     * @return The tuple representation of this array
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
     */
    @Nonnull
    public List<E> list() {
        return List.copyOf(Arrays.asList(elements));
    }

    //
    // Iteration
    //

    /**
     * Returns an iterator of every element of this array.
     * @return An iterator of every element of this array
     */
    @Override
    @Nonnull
    public Iterator<E> iterator() {
        return Arrays.stream(elements).iterator();
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
