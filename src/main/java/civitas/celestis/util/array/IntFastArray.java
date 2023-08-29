package civitas.celestis.util.array;

import civitas.celestis.util.tuple.IntTuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;

/**
 * A basic int array with no built-in synchronization or thread-safety measures.
 *
 * @see IntArray
 */
public class IntFastArray implements IntArray {
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
     * Creates a new fast int array from the provided values.
     *
     * @param values The values of which to contain in the array
     * @return The constructed array
     */
    @Nonnull
    static IntFastArray of(@Nonnull int... values) {
        return new IntFastArray(Arrays.copyOf(values, values.length));
    }

    /**
     * Creates a new fast int reference array from the provided array of values.
     * Changes in the fast int reference array will be reflected to the original array, as well
     * as from the original array to the fast int reference array.
     *
     * @param values The values the fast int reference array should reference
     * @return A new fast int reference array referencing the provided values
     */
    @Nonnull
    static IntFastArray referenceOf(@Nonnull int... values) {
        return new IntFastArray(values);
    }

    //
    // Constructors
    //

    /**
     * Creates a new fast int array.
     *
     * @param length The length to initialize this array to
     */
    public IntFastArray(int length) {
        this.values = new int[length];
    }

    /**
     * Creates a new fast int array.
     *
     * @param a The array of which to copy elements from
     */
    public IntFastArray(@Nonnull IntArray a) {
        this.values = a.array();
    }

    /**
     * Creates a new fast int array. This is a direct assignment constructor, and thus
     * is hidden to ensure safe usage.
     *
     * @param array The array of which to directly assign as the internal array
     */
    protected IntFastArray(@Nonnull int[] array) {
        this.values = array;
    }

    //
    // Variables
    //

    /**
     * The internal array of values.
     */
    @Nonnull
    protected final int[] values;

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
    public boolean contains(int v) {
        for (final int value : values) {
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
    public boolean containsAll(@Nonnull Iterable<Integer> i) {
        for (final Integer o : i) {
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
    public int get(int i) throws IndexOutOfBoundsException {
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
    public void set(int i, int e) throws IndexOutOfBoundsException {
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
    public void update(int i, @Nonnull IntUnaryOperator f) throws IndexOutOfBoundsException {
        values[i] = f.applyAsInt(values[i]);
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
    public void fill(int v) {
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
    public void fillRange(int s, int e, int v) {
        Arrays.fill(values, s, e, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull IntUnaryOperator f) {
        for (int i = 0; i < values.length; i++) {
            values[i] = f.applyAsInt(values[i]);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull BiFunction<? super Integer, ? super Integer, Integer> f) {
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
    public void replaceAll(int oldValue, int newValue) {
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
    public void replaceFirst(int oldValue, int newValue) {
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
    public void replaceLast(int oldValue, int newValue) {
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
    public IntArray subArray(int s, int e) throws IndexOutOfBoundsException {
        return new IntSubArray(values, s, e);
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
    public void setRange(int s, int e, @Nonnull IntArray a) throws IndexOutOfBoundsException {
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
    public IntArray resize(int size) {
        return new IntFastArray(Arrays.copyOf(values, size));
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
            final int temp = values[i];

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
    public void sort(@Nonnull Comparator<? super Integer> c) {
        final int[] sortedArray = Arrays.stream(values)
                .boxed()
                .sorted(c)
                .mapToInt(Integer::intValue)
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
    public IntArray map(@Nonnull IntUnaryOperator f) {
        return new IntFastArray(stream().map(f).toArray());
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
    public <F> SafeArray<F> mapToObj(@Nonnull IntFunction<? extends F> f) {
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
    public IntArray merge(@Nonnull IntArray a, @Nonnull IntBinaryOperator f)
            throws IllegalArgumentException {
        if (values.length != a.length()) {
            throw new IllegalArgumentException("Array lengths must match for this operation.");
        }

        final IntFastArray result = new IntFastArray(values.length);

        for (int i = 0; i < values.length; i++) {
            result.values[i] = f.applyAsInt(values[i], a.get(i));
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
    public Iterator<Integer> iterator() {
        return stream().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull Consumer<? super Integer> a) {
        for (final int value : values) {
            a.accept(value);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super Integer> a) {
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
    public int[] array() {
        return Arrays.copyOf(values, values.length);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntStream stream() {
        return Arrays.stream(values);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Integer> list() {
        return stream().boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntTuple tuple() {
        return IntTuple.of(values);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<Integer> boxed() {
        return new FastArray<>(stream().boxed().toArray(Integer[]::new));
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
        if (!(obj instanceof IntArray a)) return false;
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
