package civitas.celestis.util.tensoid;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.function.QuadConsumer;
import civitas.celestis.util.function.QuadFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.*;

/**
 * A thread-safe implementation of a tensoid.
 *
 * @param <E> The type of element this tensoid should hold
 * @see Tensoid
 * @see ArrayTensoid
 */
public class SyncTensoid<E> extends ArrayTensoid<E> {
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
     * Creates a new thread-safe tensoid from a three-dimensional primitive array.
     *
     * @param elements The 3D array of elements to map into a tensoid
     * @param <E>      The type of element to contain
     * @return An {@link SyncTensoid} constructed from the provided elements
     */
    @Nonnull
    public static <E> SyncTensoid<E> of(@Nonnull E[][][] elements) {
        final int width = elements.length;
        final int height = width > 0 ? elements[0].length : 0;
        final int depth = height > 0 ? elements[0][0].length : 0;

        final SyncTensoid<E> tensoid = new SyncTensoid<>(width, height, depth);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.arraycopy(elements[i][j], 0, tensoid.values[i][j], 0, depth);
            }
        }

        return tensoid;
    }

    //
    // Constructors
    //

    /**
     * Creates a new thread-safe tensoid.
     *
     * @param w The width (range of I) of this tensoid
     * @param h The height (range of J) of this tensoid
     * @param d The depth (range of K) of this tensoid
     */
    public SyncTensoid(int w, int h, int d) {
        super(w, h, d);
    }

    /**
     * Creates a new thread-safe tensoid.
     *
     * @param size The size to initialize this tensoid to
     */
    public SyncTensoid(@Nonnull Index size) {
        super(size);
    }

    /**
     * Creates a new thread-safe tensoid.
     *
     * @param t The tensoid of which to copy values from
     */
    public SyncTensoid(@Nonnull Tensoid<? extends E> t) {
        super(t);
    }

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object to check for containment
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
     * @param i The I coordinate of the element to get
     * @param j The J coordinate of the element to get
     * @param k The K coordinate of the element to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized E get(int i, int j, int k) throws IndexOutOfBoundsException {
        return super.get(i, j, k);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the slot to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized E get(@Nonnull Index i) throws IndexOutOfBoundsException {
        return super.get(i);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The I coordinate of the slot to set
     * @param j The J coordinate of the slot to set
     * @param k The K coordinate of the slot to set
     * @param v The value to assign to the specified slot
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void set(int i, int j, int k, E v) throws IndexOutOfBoundsException {
        super.set(i, j, k, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the slot to set
     * @param v The value to assign to the specified slot
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void set(@Nonnull Index i, E v) throws IndexOutOfBoundsException {
        super.set(i, v);
    }

    //
    // Bulk Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    @Override
    public synchronized void apply(@Nonnull Function<? super E, E> f) {
        super.apply(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    @Override
    public synchronized void apply(@Nonnull BiFunction<Index, ? super E, E> f) {
        super.apply(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    @Override
    public synchronized void apply(@Nonnull QuadFunction<Integer, Integer, Integer, ? super E, E> f) {
        super.apply(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this tensoid with
     */
    @Override
    public synchronized void fill(E v) {
        super.fill(v);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill empty slots of this tensoid with
     */
    @Override
    public synchronized void fillEmpty(E v) {
        super.fillEmpty(v);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this tensoid selectively with
     * @param f The filter function of which to test each original element with
     */
    @Override
    public synchronized void fillIf(E v, @Nonnull Predicate<? super E> f) {
        super.fillIf(v, f);
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

    //
    // Sub Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param i1 The I coordinate of the starting point
     * @param j1 The J coordinate of the starting point
     * @param k1 The K coordinate of the starting point
     * @param i2 The I coordinate of the ending point
     * @param j2 The J coordinate of the ending point
     * @param k2 The K coordinate of the ending point
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Tensoid<E> subTensoid(int i1, int j1, int k1, int i2, int j2, int k2) throws IndexOutOfBoundsException {
        return super.subTensoid(i1, j1, k1, i2, j2, k2);
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Tensoid<E> subTensoid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException {
        return super.subTensoid(i1, i2);
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The I coordinate of the starting point
     * @param j1 The J coordinate of the starting point
     * @param k1 The K coordinate of the starting point
     * @param i2 The I coordinate of the ending point
     * @param j2 The J coordinate of the ending point
     * @param k2 The K coordinate of the ending point
     * @param t  The tensoid containing the values to assign
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void setRange(int i1, int j1, int k1, int i2, int j2, int k2, @Nonnull Tensoid<? extends E> t)
            throws IndexOutOfBoundsException {
        super.setRange(i1, j1, k1, i2, j2, k2, t);
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @param t  The tensoid containing the values to assign
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void setRange(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Tensoid<? extends E> t)
            throws IndexOutOfBoundsException {
        super.setRange(i1, i2, t);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this tensoid
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> Tensoid<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return super.map(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param t   The tensoid of which to merge this tensoid with
     * @param f   The merger function to handle the merging of the two tensoids
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F, G> Tensoid<G> merge(@Nonnull Tensoid<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        return super.merge(t, f);
    }

    //
    // Resizing
    //

    /**
     * {@inheritDoc}
     *
     * @param width  The resulting tensoid's width
     * @param height The resulting tensoid's height
     * @param depth  The resulting tensoid's depth
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Tensoid<E> resize(int width, int height, int depth) {
        return super.resize(width, height, depth);
    }

    /**
     * {@inheritDoc}
     *
     * @param size The size the resized tensoid should have
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Tensoid<E> resize(@Nonnull Index size) {
        return super.resize(size);
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
     * @param action The action of which to execute for each element of this tensoid
     * @return {@inheritDoc}
     */
    @Override
    public synchronized void forEach(@Nonnull Consumer<? super E> action) {
        super.forEach(action);
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tensoid
     * @return {@inheritDoc}
     */
    @Override
    public synchronized void forEach(@Nonnull BiConsumer<Index, ? super E> action) {
        super.forEach(action);
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tensoid
     * @return {@inheritDoc}
     */
    @Override
    public synchronized void forEach(@Nonnull QuadConsumer<Integer, Integer, Integer, ? super E> action) {
        super.forEach(action);
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
    public synchronized SafeArray<E> array() {
        return super.array();
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
    public synchronized Map<Index, E> map() {
        return super.map();
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
