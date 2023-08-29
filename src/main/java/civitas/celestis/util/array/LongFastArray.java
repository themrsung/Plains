package civitas.celestis.util.array;

import civitas.celestis.util.tuple.LongTuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;
import java.util.stream.LongStream;

/**
 * A basic long array with no built-in synchronization or thread-safety measures.
 *
 * @see LongArray
 */
public class LongFastArray implements LongArray {
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
     * Creates a new fast long array from the provided values.
     *
     * @param values The values of which to contain in the array
     * @return The constructed array
     */
    @Nonnull
    static LongFastArray of(@Nonnull long... values) {
        return new LongFastArray(Arrays.copyOf(values, values.length));
    }

    /**
     * Creates a new fast long reference array from the provided array of values.
     * Changes in the fast long reference array will be reflected to the original array, as well
     * as from the original array to the fast long reference array.
     *
     * @param values The values the fast long reference array should reference
     * @return A new fast long reference array referencing the provided values
     */
    @Nonnull
    static LongFastArray referenceOf(@Nonnull long... values) {
        return new LongFastArray(values);
    }

    //
    // Constructors
    //

    /**
     * Creates a new fast long array.
     *
     * @param length The length to initialize this array to
     */
    public LongFastArray(int length) {
        this.values = new long[length];
    }

    /**
     * Creates a new fast long array.
     *
     * @param a The array of which to copy elements from
     */
    public LongFastArray(@Nonnull LongArray a) {
        this.values = a.array();
    }

    /**
     * Creates a new fast long array. This is a direct assignment constructor, and thus
     * is hidden to ensure safe usage.
     *
     * @param array The array of which to directly assign as the internal array
     */
    protected LongFastArray(@Nonnull long[] array) {
        this.values = array;
    }

    //
    // Variables
    //

    /**
     * The internal array of values.
     */
    @Nonnull
    protected final long[] values;

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

    /**
     * {@inheritDoc}
     *
     * @param v The value to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(long v) {
        for (final long value : values) {
            if (value == v) return true;
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
    public boolean containsAll(@Nonnull Iterable<Long> i) {
        for (final Long o : i) {
            if (o == null) return false;
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
    public long get(int i) throws IndexOutOfBoundsException {
        return values[i];
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to set
     * @param e The element of which to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(int i, long e) throws IndexOutOfBoundsException {
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
    public void update(int i, @Nonnull LongUnaryOperator f) throws IndexOutOfBoundsException {
        values[i] = f.applyAsLong(values[i]);
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
    public void fill(long v) {
        Arrays.fill(values, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The starting index at which to start assigning values from
     * @param e The ending index at which to stop assigning values at
     * @param v The value of which to assign to every slot within the specified range
     */
    @Override
    public void fillRange(int s, int e, long v) {
        Arrays.fill(values, s, e, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull LongUnaryOperator f) {
        for (int i = 0; i < values.length; i++) {
            values[i] = f.applyAsLong(values[i]);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull BiFunction<? super Integer, ? super Long, Long> f) {
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
    public void replaceAll(long oldValue, long newValue) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] != oldValue) continue;
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
    public void replaceFirst(long oldValue, long newValue) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] != oldValue) continue;
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
    public void replaceLast(long oldValue, long newValue) {
        for (int i = (values.length - 1); i >= 0; i--) {
            if (values[i] != oldValue) continue;
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
    public LongArray subArray(int s, int e) throws IndexOutOfBoundsException {
        return new LongSubArray(values, s, e);
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
    public void setRange(int s, int e, @Nonnull LongArray a) throws IndexOutOfBoundsException {
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
    public LongArray resize(int size) {
        return new LongFastArray(Arrays.copyOf(values, size));
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
            final long temp = values[i];

            values[i] = values[j];
            values[j] = temp;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort() {
        Arrays.sort(values);
    }

    /**
     * {@inheritDoc}
     *
     * @param c The comparator function of which to sort this array with
     */
    @Override
    public void sort(@Nonnull Comparator<? super Long> c) {
        final long[] sortedArray = Arrays.stream(values)
                .boxed()
                .sorted(c)
                .mapToLong(Long::longValue)
                .toArray();

        // Update the elements array with the sorted values
        System.arraycopy(sortedArray, 0, values, 0, sortedArray.length);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongArray map(@Nonnull LongUnaryOperator f) {
        return new LongFastArray(stream().map(f).toArray());
    }

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
    public <F> SafeArray<F> mapToObj(@Nonnull LongFunction<? extends F> f) {
        return new FastArray<>((F[]) stream().mapToObj(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param a The array of which to merge this array with
     * @param f The merger function to handle the merging of the two arrays
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongArray merge(@Nonnull LongArray a, @Nonnull LongBinaryOperator f)
            throws IllegalArgumentException {
        if (values.length != a.length()) {
            throw new IllegalArgumentException("Array lengths must match for this operation.");
        }

        final LongFastArray result = new LongFastArray(values.length);

        for (int i = 0; i < values.length; i++) {
            result.values[i] = f.applyAsLong(values[i], a.get(i));
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
    public Iterator<Long> iterator() {
        return stream().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull Consumer<? super Long> a) {
        for (final long value : values) {
            a.accept(value);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super Long> a) {
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
    public long[] array() {
        return Arrays.copyOf(values, values.length);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongStream stream() {
        return Arrays.stream(values);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Long> list() {
        return stream().boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongTuple tuple() {
        return LongTuple.of(values);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<Long> boxed() {
        return new FastArray<>(stream().boxed().toArray(Long[]::new));
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
        if (!(obj instanceof LongArray a)) return false;
        return Arrays.equals(values, a.array());
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
