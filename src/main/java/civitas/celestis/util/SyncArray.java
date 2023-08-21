package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * A synchronized thread-safe and type-safe array suitable for multithreaded applications.
 *
 * @param <E> The type of element this array should hold
 * @see SafeArray
 * @see FastArray
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
    @SuppressWarnings("unchecked")
    public static <E> SyncArray<E> of(@Nonnull E... elements) {
        return new SyncArray<>((E[]) Arrays.stream(elements).toArray(), false);
    }

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
     * @param a The array of which to copy elements from
     */
    public SyncArray(@Nonnull SafeArray<? extends E> a) {
        super(a);
    }

    /**
     * Creates a new synchronized array by directly assigning the internal array.
     * This is a dangerous constructor, and thus is hidden as protected.
     *
     * @param elements The array to assign as the elements of this array
     * @param ignored  Ignored
     */
    protected SyncArray(@Nonnull E[] elements, boolean ignored) {
        super(elements, ignored);
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
     * @param i The iterable object containing the values to check for containment
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
     * @param i        The index of the element to get
     * @param fallback The fallback value to return instead of {@code null}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized E getOrDefault(int i, @Nonnull E fallback) throws IndexOutOfBoundsException {
        return super.getOrDefault(i, fallback);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to set
     * @param v The value to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void set(int i, E v) throws IndexOutOfBoundsException {
        super.set(i, v);
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
     * @param i1 The starting index at which to start assigning values from
     * @param i2 The ending index at which to stop assigning values at
     * @param v  The value of which to assign to every slot within the specified range
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void fillRange(int i1, int i2, E v) throws IndexOutOfBoundsException {
        super.fillRange(i1, i2, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public synchronized void apply(@Nonnull UnaryOperator<E> f) {
        super.apply(f);
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
     *
     * @param i1 The starting index at which to start copying elements from
     * @param i2 The ending index at which to stop copying elements at
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized SafeArray<E> subArray(int i1, int i2) throws IndexOutOfBoundsException {
        return super.subArray(i1, i2);
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The starting index at which to start assigning elements from
     * @param i2 The ending index at which to stop assigning elements at
     * @param a  The sub-array of which to copy elements from
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void setRange(int i1, int i2, @Nonnull SafeArray<? extends E> a) throws IndexOutOfBoundsException {
        super.setRange(i1, i2, a);
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
    // Sorting
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void sort() {
        super.sort();
    }

    /**
     * {@inheritDoc}
     *
     * @param f The comparator function to sort this array with
     */
    @Override
    public synchronized void sort(@Nonnull Comparator<? super E> f) {
        super.sort(f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void shuffle() {
        super.shuffle();
    }

    //
    // Filtration
    //

    /**
     * {@inheritDoc}
     *
     * @param f The filter function to handle the filtration of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized SafeArray<E> filter(@Nonnull Predicate<? super E> f) {
        return super.filter(f);
    }

    //
    // Mapping
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> SafeArray<F> map(@Nonnull Function<? super E, F> f) {
        return super.map(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> Collection<F> mapToCollection(@Nonnull Function<? super E, F> f) {
        return super.mapToCollection(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> List<F> mapToList(@Nonnull Function<? super E, F> f) {
        return super.mapToList(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> Set<F> mapToSet(@Nonnull Function<? super E, F> f) {
        return super.mapToSet(f);
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
    public <F, G> SafeArray<G> merge(@Nonnull SafeArray<F> a, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        return super.merge(a, f);
    }

    /**
     * Explicitly casts each element of this array to the provided class {@code c}, then returns
     * the resulting array. The provided class {@code c} must be a superclass of this array in order
     * for this operation to succeed.
     * <p>
     * Unlike {@link FastArray}, a shallow copy is performed in the process, and changes in the
     * new array will not be reflected to this array.
     * </p>
     *
     * @param c   The class of which to cast the elements of this array to
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized <F> SafeArray<F> cast(@Nonnull Class<F> c) throws ClassCastException {
        return of((F[]) elements);
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
    public synchronized Collection<E> collect() {
        return super.collect();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Set<E> set() {
        return super.set();
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
    public synchronized ArrayList<E> arrayList() {
        return super.arrayList();
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
    // Iteration
    //

    /**
     * {@inheritDoc}
     * This returns a copied iterator to prevent concurrency issues.
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Iterator<E> iterator() {
        return super.copy().iterator();
    }

    //
    // Copying
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized SafeArray<E> copy() {
        return super.copy();
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
