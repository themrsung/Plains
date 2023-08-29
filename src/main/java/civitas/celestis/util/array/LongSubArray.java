package civitas.celestis.util.array;

import civitas.celestis.util.tuple.LongTuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;
import java.util.stream.LongStream;

/**
 * A long array which directly references its parent array's internal array.
 * A predefined offset is added to the start of the array, and an arbitrary length is set
 * as the limit of this array. This class is designed to be used internally, and thus is
 * package-private.
 *
 * @see LongArray
 */
class LongSubArray implements LongArray {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    /**
     * Creates a new sub-array.
     *
     * @param original      The original internal array to reference
     * @param startingIndex The index at which to start the reference at (inclusive)
     * @param endingIndex   The index at which to stop the reference at (exclusive)
     */
    LongSubArray(@Nonnull long[] original, int startingIndex, int endingIndex) {
        if (startingIndex >= endingIndex) {
            throw new ArrayIndexOutOfBoundsException("Range [" + startingIndex + ", " + endingIndex + ") is invalid.");
        }

        if (endingIndex - startingIndex > original.length) {
            throw new ArrayIndexOutOfBoundsException("Range [" + startingIndex + ", " + endingIndex + ") is out of bounds.");
        }

        this.original = original;
        this.startingIndex = startingIndex;
        this.endingIndex = endingIndex;
    }

    /*
     * No copy constructor by design. This should be a transient utility class.
     */

    //
    // Variables
    //

    /**
     * The original array to reference.
     */
    @Nonnull
    private final long[] original;

    /**
     * The starting index of this array.
     */
    private final int startingIndex;

    /**
     * The ending index of this array.
     */
    private final int endingIndex;


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
        return endingIndex - startingIndex;
    }

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param v The object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(long v) {
        for (int i = startingIndex; i < endingIndex; i++) {
            if (Objects.equals(original[i], v)) return true;
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
        final int adjusted = i + startingIndex;
        if (adjusted >= endingIndex) throw new ArrayIndexOutOfBoundsException(i);
        return original[adjusted];
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
        final int adjusted = i + startingIndex;
        if (adjusted >= endingIndex) throw new ArrayIndexOutOfBoundsException(i);
        original[adjusted] = e;
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
        final int adjusted = i + startingIndex;
        if (adjusted >= endingIndex) throw new ArrayIndexOutOfBoundsException(i);
        original[adjusted] = f.applyAsLong(original[adjusted]);
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
        for (int i = startingIndex; i < endingIndex; i++) {
            original[i] = v;
        }
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
        final int start = s + startingIndex;
        final int end = e + startingIndex;

        if (start <= startingIndex) throw new ArrayIndexOutOfBoundsException(s);
        if (end >= endingIndex) throw new ArrayIndexOutOfBoundsException(e);

        for (int i = start; i < end; i++) {
            original[i] = v;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull LongUnaryOperator f) {
        for (int i = startingIndex; i < endingIndex; i++) {
            original[i] = f.applyAsLong(original[i]);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull BiFunction<? super Integer, ? super Long, Long> f) {
        for (int i = startingIndex; i < endingIndex; i++) {
            original[i] = f.apply(i, original[i]);
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
        for (int i = startingIndex; i < endingIndex; i++) {
            if (original[i] != oldValue) continue;
            original[i] = newValue;
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
        for (int i = startingIndex; i < endingIndex; i++) {
            if (original[i] != oldValue) continue;
            original[i] = newValue;
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
        for (int i = (endingIndex - 1); i >= startingIndex; i--) {
            if (original[i] != oldValue) continue;
            original[i] = newValue;
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
        return new LongSubArray(original, s + startingIndex, e + startingIndex);
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
        for (int i = (s + startingIndex); i < (e + startingIndex); i++) {
            original[i] = a.get(i - (s + startingIndex));
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
        final LongFastArray result = new LongFastArray(size);
        System.arraycopy(original, startingIndex, result.values, 0, endingIndex - startingIndex);
        return result;
    }

    //
    // Ordering
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffle() {
        final Random random = new Random();

        for (int i = endingIndex - 1; i > startingIndex; i--) {
            final int j = random.nextInt(i + 1);

            // Swap elements at i and j
            final long temp = original[i];

            original[i] = original[j];
            original[j] = temp;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort() {
        final long[] sorted = stream().sorted().toArray();
        System.arraycopy(sorted, 0, original, startingIndex, endingIndex - startingIndex);
    }

    /**
     * {@inheritDoc}
     *
     * @param c The comparator function of which to sort this array with
     */
    @Override
    public void sort(@Nonnull Comparator<? super Long> c) {
        final long[] sorted = stream().boxed().sorted(c).mapToLong(Long::longValue).toArray();
        System.arraycopy(sorted, 0, original, startingIndex, endingIndex - startingIndex);
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
    public <F> SafeArray<F> mapToObj(@Nonnull LongFunction<? extends F> f) {
        final FastArray<F> result = new FastArray<>(length());
        for (int i = startingIndex; i < endingIndex; i++) {
            result.values[i - startingIndex] = f.apply(original[i]);
        }
        return result;
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
        final int length = length();

        if (length != a.length()) {
            throw new IllegalArgumentException("Array lengths must match for this operation.");
        }

        final LongFastArray result = new LongFastArray(length);
        for (int i = startingIndex; i < endingIndex; i++) {
            result.values[i - startingIndex] = f.applyAsLong(original[i], a.get(i - startingIndex));
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
        for (int i = startingIndex; i < endingIndex; i++) {
            a.accept(original[i]);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super Long> a) {
        for (int i = startingIndex; i < endingIndex; i++) {
            a.accept(i - startingIndex, original[i]);
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
        final long[] result = new long[endingIndex - startingIndex];
        System.arraycopy(original, startingIndex, result, 0, endingIndex - startingIndex);
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongStream stream() {
        return LongStream.of(array());
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
        return LongTuple.of(array());
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
        if (length() != a.length()) return false;

        for (int i = startingIndex; i < endingIndex; i++) {
            if (original[i] != a.get(i - startingIndex)) return false;
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
        return Arrays.toString(array());
    }

    /**
     * Returns the hash code of this instance.
     *
     * @return The hash code of this instance
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(array());
    }
}
