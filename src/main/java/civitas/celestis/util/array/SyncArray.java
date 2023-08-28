package civitas.celestis.util.array;

import civitas.celestis.util.function.ToFloatFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A synchronized instance of {@link FastArray}. Synchronized arrays offer
 * thread-safety by locking the entire array instance while one thread is accessing
 * the array's data, regardless of whether it is a read or write access.
 *
 * @param <E> The type of element to contain
 */
public class SyncArray<E> extends FastArray<E> {
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
     * Creates a new synchronized array containing the provided values.
     *
     * @param values The value of which to contain in the synchronized array
     * @param <E>    The type of element to contain
     * @return The constructed synchronized array
     */
    @Nonnull
    @SafeVarargs
    public static <E> SyncArray<E> of(@Nonnull E... values) {
        return new SyncArray<>(Arrays.copyOf(values, values.length));
    }

    /*
     * Referencing is not supported for synchronized arrays.
     */

    //
    // Constructors
    //

    /**
     * Creates a new synchronized array.
     *
     * @param length The length of this array
     */
    public SyncArray(int length) {
        super(length);
    }

    /**
     * Creates a new synchronized array.
     *
     * @param a The array of which to copy values from
     */
    public SyncArray(@Nonnull SafeArray<? extends E> a) {
        super(a);
    }

    /**
     * Creates a new synchronized array. This is a direct assignment constructor,
     * and thus is hidden as private to ensure proper usage.
     *
     * @param array The array of which to directly assign to this instance
     */
    private SyncArray(@Nonnull E[] array) {
        super(array);
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
    public synchronized boolean contains(@Nullable Object obj) {
        return super.contains(obj);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean containsAll(@Nonnull Iterable<?> i) {
        return super.containsAll(i);
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
    public synchronized E get(int i) throws IndexOutOfBoundsException {
        return super.get(i);
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
    public synchronized E getOrDefault(int i, E e) throws IndexOutOfBoundsException {
        return super.getOrDefault(i, e);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to set
     * @param e The element of which to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void set(int i, E e) throws IndexOutOfBoundsException {
        super.set(i, e);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to update
     * @param f The update function of which to apply to the element
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void update(int i, @Nonnull UnaryOperator<E> f) throws IndexOutOfBoundsException {
        super.update(i, f);
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
    public synchronized void fill(E v) {
        super.fill(v);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill empty slots of this array with
     */
    @Override
    public synchronized void fillEmpty(E v) {
        super.fillEmpty(v);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The starting index at which to start assigning values from
     * @param e The ending index at which to stop assigning values at
     * @param v The value of which to assign to every slot within the specified range
     */
    @Override
    public synchronized void fillRange(int s, int e, E v) {
        super.fillRange(s, e, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public synchronized void update(@Nonnull UnaryOperator<E> f) {
        super.update(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public synchronized void update(@Nonnull BiFunction<? super Integer, ? super E, E> f) {
        super.update(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public synchronized void replaceAll(E oldValue, E newValue) {
        super.replaceAll(oldValue, newValue);
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public synchronized void replaceFirst(E oldValue, E newValue) {
        super.replaceFirst(oldValue, newValue);
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public synchronized void replaceLast(E oldValue, E newValue) {
        super.replaceLast(oldValue, newValue);
    }

    //
    // Sub Operation
    //

    /**
     * {@inheritDoc}
     * <p>
     * Note that usage of sub-arrays with synchronized arrays can be potentially dangerous
     * if concurrently used across multiples threads. Use at your own risk.
     * </p>
     *
     * @param s The starting index at which to start creating the sub-array (inclusive)
     * @param e The ending index at which to stop creating the sub-array (exclusive)
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized SafeArray<E> subArray(int s, int e) throws IndexOutOfBoundsException {
        return super.subArray(s, e);
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
    public synchronized void setRange(int s, int e, @Nonnull SafeArray<? extends E> a) throws IndexOutOfBoundsException {
        super.setRange(s, e, a);
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
    public synchronized SafeArray<E> resize(int size) {
        return super.resize(size);
    }

    //
    // Ordering
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void shuffle() {
        super.shuffle();
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     */
    @Override
    public synchronized void sort() throws UnsupportedOperationException {
        super.sort();
    }

    /**
     * {@inheritDoc}
     *
     * @param c The comparator function of which to sort this array with
     */
    @Override
    public synchronized void sort(@Nonnull Comparator<? super E> c) {
        super.sort(c);
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
    public synchronized <F> SafeArray<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return super.map(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized DoubleArray mapToDouble(@Nonnull ToDoubleFunction<? super E> f) {
        return super.mapToDouble(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized FloatArray mapToFloat(@Nonnull ToFloatFunction<? super E> f) {
        return super.mapToFloat(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized LongArray mapToLong(@Nonnull ToLongFunction<? super E> f) {
        return super.mapToLong(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized IntArray mapToInt(@Nonnull ToIntFunction<? super E> f) {
        return super.mapToInt(f);
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
    public synchronized <F, G> SafeArray<G> merge(@Nonnull SafeArray<F> a, @Nonnull BiFunction<? super E, ? super F, ? extends G> f) throws IllegalArgumentException {
        return super.merge(a, f);
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
    public synchronized Iterator<E> iterator() {
        return super.iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public synchronized void forEach(@Nonnull Consumer<? super E> a) {
        super.forEach(a);
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public synchronized void forEach(@Nonnull BiConsumer<? super Integer, ? super E> a) {
        super.forEach(a);
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
    public synchronized E[] array() {
        return super.array();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Stream<E> stream() {
        return super.stream();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized List<E> list() {
        return super.list();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Tuple<E> tuple() {
        return super.tuple();
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
    public synchronized boolean equals(@Nullable Object obj) {
        return super.equals(obj);
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
    public synchronized String toString() {
        return super.toString();
    }
}
