package civitas.celestis.util.array;

import civitas.celestis.util.function.ToFloatFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A generic type-safe array which directly references its parent array's internal array.
 * A predefined offset is added to the start of the array, and an arbitrary length is set
 * as the limit of this array. This class is designed to be used internally, and thus is
 * package-private.
 *
 * @param <E> The type of element to contain
 * @see SafeArray
 */
class AtomicSubArray<E> implements SafeArray<E> {
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
     * @param references      The original internal array to reference
     * @param startingIndex The index at which to start the reference at (inclusive)
     * @param endingIndex   The index at which to stop the reference at (exclusive)
     */
    AtomicSubArray(@Nonnull AtomicReference<E>[] references, int startingIndex, int endingIndex) {
        if (startingIndex >= endingIndex) {
            throw new ArrayIndexOutOfBoundsException("Range [" + startingIndex + ", " + endingIndex + ") is invalid.");
        }

        if (endingIndex - startingIndex > references.length) {
            throw new ArrayIndexOutOfBoundsException("Range [" + startingIndex + ", " + endingIndex + ") is out of bounds.");
        }

        this.references = references;
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
    private final AtomicReference<E>[] references;

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
     * @param obj The object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        for (int i = startingIndex; i < endingIndex; i++) {
            if (Objects.equals(references[i].get(), obj)) return true;
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
        final int adjusted = i + startingIndex;
        if (adjusted >= endingIndex) throw new ArrayIndexOutOfBoundsException(i);
        return references[adjusted].get();
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
        final int adjusted = i + startingIndex;
        if (adjusted >= endingIndex) throw new ArrayIndexOutOfBoundsException(i);
        final E value = references[adjusted].get();
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
        final int adjusted = i + startingIndex;
        if (adjusted >= endingIndex) throw new ArrayIndexOutOfBoundsException(i);
        references[adjusted].set(e);
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
        final int adjusted = i + startingIndex;
        if (adjusted >= endingIndex) throw new ArrayIndexOutOfBoundsException(i);
        references[adjusted].getAndUpdate(f);
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
        for (int i = startingIndex; i < endingIndex; i++) {
            references[i].set(v);
        }
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
        final int start = s + startingIndex;
        final int end = e + startingIndex;

        if (start <= startingIndex) throw new ArrayIndexOutOfBoundsException(s);
        if (end >= endingIndex) throw new ArrayIndexOutOfBoundsException(e);

        for (int i = start; i < end; i++) {
            references[i].set(v);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull UnaryOperator<E> f) {
        for (int i = startingIndex; i < endingIndex; i++) {
            references[i].getAndUpdate(f);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull BiFunction<? super Integer, ? super E, E> f) {
        for (int i = startingIndex; i < endingIndex; i++) {
            final int index = i;
            references[i].getAndUpdate(v -> f.apply(index, v));
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
        for (int i = startingIndex; i < endingIndex; i++) {
            if (!Objects.equals(references[i].get(), oldValue)) continue;
            references[i].set(newValue);
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
        for (int i = startingIndex; i < endingIndex; i++) {
            if (!Objects.equals(references[i].get(), oldValue)) continue;
            references[i].set(newValue);
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
        for (int i = (endingIndex - 1); i >= startingIndex; i--) {
            if (!Objects.equals(references[i].get(), oldValue)) continue;
            references[i].set(newValue);
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
        return new AtomicSubArray<>(references, s + startingIndex, e + startingIndex);
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
        for (int i = (s + startingIndex); i < (e + startingIndex); i++) {
            references[i].set(a.get(i - (s + startingIndex)));
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
        final FastArray<E> result = new FastArray<>(size);

        for (int i = startingIndex; i < endingIndex; i++) {
            result.values[i - startingIndex] = references[i].get();
        }

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
            final E temp = references[i].get();

            references[i].set(references[j].get());
            references[j].set(temp);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sort() throws UnsupportedOperationException {
        try {
            Arrays.asList(references).subList(startingIndex, endingIndex).sort((r1, r2) -> {
                final Comparable<E> v1 = (Comparable<E>) r1.get();
                final E v2 = r2.get();

                return v1.compareTo(v2);
            });
        } catch (final ClassCastException e) {
            throw new UnsupportedOperationException("Non-comparable objects cannot be sorted by natural order.", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param c The comparator function of which to sort this array with
     */
    @Override
    public void sort(@Nonnull Comparator<? super E> c) {
        Arrays.asList(references).subList(startingIndex, endingIndex).sort((r1, r2) -> c.compare(r1.get(), r2.get()));
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
    public <F> SafeArray<F> map(@Nonnull Function<? super E, ? extends F> f) {
        final FastArray<F> result = new FastArray<>(length());
        for (int i = startingIndex; i < endingIndex; i++) {
            result.values[i - startingIndex] = f.apply(references[i].get());
        }
        return result;
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
        final DoubleFastArray result = new DoubleFastArray(length());
        for (int i = startingIndex; i < endingIndex; i++) {
            result.values[i - startingIndex] = f.applyAsDouble(references[i].get());
        }
        return result;
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
        final FloatFastArray result = new FloatFastArray(length());
        for (int i = startingIndex; i < endingIndex; i++) {
            result.values[i - startingIndex] = f.applyAsFloat(references[i].get());
        }
        return result;
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
        final LongFastArray result = new LongFastArray(length());
        for (int i = startingIndex; i < endingIndex; i++) {
            result.values[i - startingIndex] = f.applyAsLong(references[i].get());
        }
        return result;
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
        final IntFastArray result = new IntFastArray(length());
        for (int i = startingIndex; i < endingIndex; i++) {
            result.values[i - startingIndex] = f.applyAsInt(references[i].get());
        }
        return result;
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
        final int length = length();

        if (length != a.length()) {
            throw new IllegalArgumentException("Array lengths must match for this operation.");
        }

        final FastArray<G> result = new FastArray<>(length);
        for (int i = startingIndex; i < endingIndex; i++) {
            result.values[i - startingIndex] = f.apply(references[i].get(), a.get(i - startingIndex));
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
        for (int i = startingIndex; i < endingIndex; i++) {
            a.accept(references[i].get());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super E> a) {
        for (int i = startingIndex; i < endingIndex; i++) {
            a.accept(i - startingIndex, references[i].get());
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
    @SuppressWarnings("unchecked")
    public E[] array() {
        final E[] result = (E[]) new Object[endingIndex - startingIndex];
        for (int i = startingIndex; i < endingIndex; i++) {
            result[i - startingIndex] = references[i].get();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<E> stream() {
        return Stream.of(array());
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
        if (length() != a.length()) return false;

        for (int i = startingIndex; i < endingIndex; i++) {
            if (!Objects.equals(references[i].get(), a.get(i - startingIndex))) return false;
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
}
