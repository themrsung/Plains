package civitas.celestis.util.array;

import civitas.celestis.util.function.ToFloatFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A basic type-safe array with no built-in synchronization or thread-safety measures.
 * Fast arrays are designed for speed and efficiency.
 *
 * @param <E> The type of element to contain
 * @see SafeArray
 */
public class FastArray<E> implements SafeArray<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Static Initializers
    //

    /**
     * Creates a new fast array from the provided values.
     *
     * @param values The values of which to contain in the array
     * @param <E>    The type of element to contain in the array
     * @return The constructed array
     */
    @Nonnull
    @SafeVarargs
    static <E> FastArray<E> of(@Nonnull E... values) {
        return new FastArray<>(Arrays.copyOf(values, values.length));
    }

    /**
     * Creates a new fast reference array from the provided array of values.
     * Changes in the fast reference array will be reflected to the original array, as well
     * as from the original array to the fast reference array.
     *
     * @param values The values the fast reference array should reference
     * @param <E>    The type of element to reference
     * @return A new fast reference array referencing the provided values
     */
    @Nonnull
    @SafeVarargs
    static <E> FastArray<E> referenceOf(@Nonnull E... values) {
        return new FastArray<>(values);
    }

    //
    // Constructors
    //

    /**
     * Creates a new fast array.
     *
     * @param length The length to initialize this array to
     */
    @SuppressWarnings("unchecked")
    public FastArray(int length) {
        this.values = (E[]) new Object[length];
    }

    /**
     * Creates a new fast array.
     *
     * @param a The array of which to copy elements from
     */
    public FastArray(@Nonnull SafeArray<? extends E> a) {
        this.values = a.array();
    }

    /**
     * Creates a new fast array. This is a direct assignment constructor, and thus
     * is hidden to ensure safe usage.
     *
     * @param array The array of which to directly assign as the internal array
     */
    protected FastArray(@Nonnull E[] array) {
        this.values = array;
    }

    //
    // Variables
    //

    /**
     * The internal array of values.
     */
    @Nonnull
    protected final E[] values;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int length() {
        return values.length;
    }

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        for (final E value : values) {
            if (Objects.equals(value, obj)) return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<?> i) {
        for (final Object o : i) {
            if (!contains(o)) return false;
        }

        return true;
    }

    //
    // Accessors
    //

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        return values[i];
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to get
     * @param e The fallback value to default to when the value is {@code null}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E getOrDefault(int i, E e) throws IndexOutOfBoundsException {
        final E value = values[i];
        return value != null ? value : e;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to set
     * @param e The element of which to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(int i, E e) throws IndexOutOfBoundsException {
        values[i] = e;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to update
     * @param f The update function of which to apply to the element
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void update(int i, @Nonnull UnaryOperator<E> f) throws IndexOutOfBoundsException {
        values[i] = f.apply(values[i]);
    }

    //
    // Bulk Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this array with
     */
    @Override
    public void fill(E v) {
        Arrays.fill(values, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill empty slots of this array with
     */
    @Override
    public void fillEmpty(E v) {
        replaceAll(null, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The starting index at which to start assigning values from
     * @param e The ending index at which to stop assigning values at
     * @param v The value of which to assign to every slot within the specified range
     */
    @Override
    public void fillRange(int s, int e, E v) {
        Arrays.fill(values, s, e, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull UnaryOperator<E> f) {
        for (int i = 0; i < values.length; i++) {
            values[i] = f.apply(values[i]);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull BiFunction<? super Integer, ? super E, E> f) {
        for (int i = 0; i < values.length; i++) {
            values[i] = f.apply(i, values[i]);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceAll(E oldValue, E newValue) {
        for (int i = 0; i < values.length; i++) {
            if (!Objects.equals(values[i], oldValue)) continue;
            values[i] = newValue;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceFirst(E oldValue, E newValue) {
        for (int i = 0; i < values.length; i++) {
            if (!Objects.equals(values[i], oldValue)) continue;
            values[i] = newValue;
            return;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceLast(E oldValue, E newValue) {
        for (int i = (values.length - 1); i >= 0; i--) {
            if (!Objects.equals(values[i], oldValue)) continue;
            values[i] = newValue;
            return;
        }
    }

    //
    // Sub Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param s The starting index at which to start creating the sub-array (inclusive)
     * @param e The ending index at which to stop creating the sub-array (exclusive)
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<E> subArray(int s, int e) throws IndexOutOfBoundsException {
        return new SubArray<>(values, s, e);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The starting index at which to start copying values from (inclusive)
     * @param e The ending index at which to stop copying values from (exclusive)
     * @param a The sub-array containing the values to assign to this array
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void setRange(int s, int e, @Nonnull SafeArray<? extends E> a) throws IndexOutOfBoundsException {
        for (int i = s; i < e; i++) {
            values[i] = a.get(i - s);
        }
    }

    //
    // Resizing
    //

    /**
     * {@inheritDoc}
     *
     * @param size The size to resize this array to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<E> resize(int size) {
        return new FastArray<>(Arrays.copyOf(values, size));
    }

    //
    // Ordering
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffle() {
        final int n = values.length;
        final Random random = new Random();

        for (int i = n - 1; i > 0; i--) {
            final int j = random.nextInt(i + 1);

            // Swap elements at i and j
            final E temp = values[i];

            values[i] = values[j];
            values[j] = temp;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     */
    @Override
    public void sort() throws UnsupportedOperationException {
        try {
            Arrays.sort(values);
        } catch (final ClassCastException e) {
            throw new UnsupportedOperationException("Non-comparable objects cannot be naturally sorted.", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param c The comparator function of which to sort this array with
     */
    @Override
    public void sort(@Nonnull Comparator<? super E> c) {
        Arrays.sort(values, c);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> SafeArray<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return new FastArray<>((F[]) stream().map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleArray mapToDouble(@Nonnull ToDoubleFunction<? super E> f) {
        return new DoubleFastArray(stream().mapToDouble(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public FloatArray mapToFloat(@Nonnull ToFloatFunction<? super E> f) {
        return new FloatFastArray(stream().map(f::applyAsFloat).toArray(Float[]::new));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongArray mapToLong(@Nonnull ToLongFunction<? super E> f) {
        return new LongFastArray(stream().mapToLong(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntArray mapToInt(@Nonnull ToIntFunction<? super E> f) {
        return new IntFastArray(stream().mapToInt(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param a   The array of which to merge this array with
     * @param f   The merger function to handle the merging of the two arrays
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F, G> SafeArray<G> merge(@Nonnull SafeArray<F> a, @Nonnull BiFunction<? super E, ? super F, ? extends G> f)
            throws IllegalArgumentException {
        if (values.length != a.length()) {
            throw new IllegalArgumentException("Array lengths must match for this operation.");
        }

        final FastArray<G> result = new FastArray<>(values.length);

        for (int i = 0; i < values.length; i++) {
            result.values[i] = f.apply(values[i], a.get(i));
        }

        return result;
    }

    //
    // Iteration
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return stream().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> a) {
        for (final E value : values) {
            a.accept(value);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super E> a) {
        for (int i = 0; i < values.length; i++) {
            a.accept(i, values[i]);
        }
    }

    //
    // Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public E[] array() {
        return Arrays.copyOf(values, values.length);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<E> stream() {
        return Arrays.stream(values);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return stream().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<E> tuple() {
        return Tuple.of(array());
    }

    //
    // Equality
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof SafeArray<?> a)) return false;
        if (values.length != a.length()) return false;
        for (int i = 0; i < values.length; i++) {
            if (!Objects.equals(values[i], a.get(i))) return false;
        }

        return true;
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return Arrays.toString(values);
    }

    /**
     * Returns the hash code of this instance.
     *
     * @return The hash code of this instance
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}
