package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * A type-safe array of elements. This class is not thread-safe.
 * An unmodifiable copy can be obtained by {@link Group#copyOf(Group)} to ensure
 * thread safety, and effectively finalize this array's order and composition.
 *
 * @param <E> The type of element to hold
 */
public class SafeArray<E> implements ArrayGroup<E>, Iterable<E>, Serializable {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 5217862342156798234L;

    //
    // Constructors
    //

    /**
     * Creates a new type-safe array.
     *
     * @param length The length of this array
     */
    @SuppressWarnings("unchecked")
    public SafeArray(int length) {
        this.values = (E[]) new Object[length];
    }

    //
    // Variables
    //

    /**
     * The primitive array of values.
     */
    @Nonnull
    protected final E[] values;

    //
    // Properties
    //

    /**
     * Returns the size of this array. (the number of elements this array contains)
     *
     * @return The number of elements this array contains
     */
    @Override
    public int size() {
        return values.length;
    }

    //
    // Containment
    //

    /**
     * Checks if this array contains the provided object {@code obj}.
     *
     * @param obj The object of which to check for containment
     * @return {@code true} if this array contains the provided object {@code obj}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        for (final E value : values) {
            if (Objects.equals(value, obj)) return true;
        }

        return false;
    }

    /**
     * Checks if this array contains multiple objects.
     *
     * @param i The iterable object to check for containment
     * @return {@code true} if this array contains every element of the iterable object
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<?> i) {
        for (final Object o : i) {
            if (!contains(o)) return false;
        }

        return true;
    }

    //
    // Getters
    //

    /**
     * Returns the {@code i}th element of this array.
     *
     * @param i The index of element to get
     * @return The {@code i}th element of this array
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    public E get(int i) throws IndexOutOfBoundsException {
        return values[i];
    }

    //
    // Setters
    //

    /**
     * Sets the {@code i}th element of this array.
     *
     * @param i The index of element to set
     * @param v The value to set the element to
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    public void set(int i, E v) throws IndexOutOfBoundsException {
        values[i] = v;
    }

    //
    // Bulk Operation
    //

    /**
     * Fills this array with the provided value {@code v}.
     *
     * @param v The value to fill this array with
     */
    public void fill(E v) {
        Arrays.fill(values, v);
    }

    /**
     * Replaces the first instance of the old value to the new value.
     *
     * @param oldValue The value to replace
     * @param newValue The value to replace to
     */
    public void replaceFirst(E oldValue, E newValue) {
        for (int i = 0; i < values.length; i++) {
            if (Objects.equals(values[i], oldValue)) {
                values[i] = newValue;
                break;
            }
        }
    }

    /**
     * Replaces all instances of the old value to the new value.
     *
     * @param oldValue The value to replace
     * @param newValue The value to replace to
     */
    public void replaceAll(E oldValue, E newValue) {
        for (int i = 0; i < values.length; i++) {
            if (Objects.equals(values[i], oldValue)) {
                values[i] = newValue;
            }
        }
    }

    //
    // Sub-operation
    //

    /**
     * Returns a sub-array of the provided range.
     *
     * @param i1 The starting index of the sub-array
     * @param i2 The ending index of the sub-array
     * @return The sub-array of the provided range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    public SafeArray<E> subArray(int i1, int i2) throws IndexOutOfBoundsException {
        final SafeArray<E> result = new SafeArray<>(i2 - i1);
        if (i2 - i1 >= 0) System.arraycopy(values, i1, result.values, 0, i2 - i1);
        return result;
    }

    //
    // Resizing
    //

    /**
     * Returns a resized array with the elements of this array.
     * If the new length is greater than this array's length, the oversized part
     * will be populated with {@code null}.
     *
     * @param newSize The new size to resize to
     * @return A resized array
     */
    @Nonnull
    public SafeArray<E> resize(int newSize) {
        final SafeArray<E> result = new SafeArray<>(newSize);
        final int minLength = Math.min(values.length, newSize);

        if (minLength >= 0) System.arraycopy(values, 0, result.values, 0, minLength);

        return result;
    }

    //
    // Transformation
    //

    /**
     * Applies the provided function {@code f} to all elements of this array,
     * then returns the transformed array.
     *
     * @param f The function to apply to each element of this array
     * @return The transformed array
     */
    @Nonnull
    public SafeArray<E> transform(@Nonnull Function<? super E, E> f) {
        final SafeArray<E> result = new SafeArray<>(values.length);

        for (int i = 0; i < values.length; i++) {
            result.values[i] = f.apply(values[i]);
        }

        return result;
    }

    /**
     * Applies the provided mapper function {@code f} to all elements of this array,
     * then returns the mapped array.
     *
     * @param f   The mapper function to apply to each element of this group
     * @param <F> The type of which to map this array to
     * @return The mapped array
     */
    @Nonnull
    public <F> SafeArray<F> map(@Nonnull Function<? super E, F> f) {
        final SafeArray<F> result = new SafeArray<>(values.length);

        for (int i = 0; i < values.length; i++) {
            result.values[i] = f.apply(values[i]);
        }

        return result;
    }

    //
    // Iteration
    //

    /**
     * Returns an iterator of all elements of this type-safe array.
     *
     * @return An iterator of all elements of this array
     */
    @Override
    public Iterator<E> iterator() {
        return Arrays.stream(values).iterator();
    }


    //
    // Type Conversion
    //

    /**
     * {@inheritDoc}
     * <p>
     * Note that changes to the return value of this method will be reflected in this
     * type-safe array instance, as this is a direct reference to the values of this array.
     * </p>
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public E[] array() {
        return values;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Note that changes in the return value of this method will be reflected in this
     * type-safe array instance.
     * </p>
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> collect() {
        return Arrays.asList(values);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Note that changes in the return value of this method will be reflected in this
     * type-safe array instance.
     * </p>
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return Arrays.asList(values);
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this array and the provided object {@code obj}.
     * <p>
     * This will first check if the object is an array (either type-safe or primitive),
     * then compare every element for equality with regard to their order.
     * </p>
     * <p>
     * Tuples, {@link Listable} objects, and {@link List}s will be also compared for equality by
     * extracting their components and checking for equal composition and order.
     * </p>
     * <p>
     * This equality check is broader than that of which is described in
     * {@link civitas.celestis.util.group.Group#equals(Object) Group.equals(Object)}.
     * </p>
     *
     * @param obj The object to compare to
     * @return {@code true} if the object is considered equal to this array as described above
     */
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof SafeArray<?> a) {
            return Arrays.equals(values, a.values);
        }

        if (obj instanceof Object[] a) {
            return Arrays.equals(values, a);
        }

        if (obj instanceof Tuple<?> t) {
            return Arrays.equals(values, t.array());
        }

        if (obj instanceof List<?> l) {
            return list().equals(l);
        }

        if (obj instanceof Listable<?> l) {
            return list().equals(l.list());
        }

        return false;
    }

    //
    // Serialization
    //

    /**
     * Serializes this array into a string.
     *
     * @return The string representation of this array
     */
    @Override
    @Nonnull
    public String toString() {
        return Arrays.toString(values);
    }
}
