package civitas.celestis.util.grid;

import civitas.celestis.util.function.ToFloatFunction;
import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A variant of the array grid which features synchronization. Synchronized
 * grids can be used concurrently across multiple threads.
 *
 * @param <E> The type of element this grid should hold
 * @see ArrayGrid
 */
public class SyncGrid<E> extends ArrayGrid<E> {
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
     * Creates a new synchronized grid from a 2D array of values.
     *
     * @param values The values to contain within the grid
     * @param <E>    The type of element to contain
     * @return The constructed grid
     */
    @Nonnull
    public static <E> SyncGrid<E> of(@Nonnull E[][] values) {
        final int rows = values.length;
        final int columns = rows > 0 ? values[0].length : 0;
        final SyncGrid<E> grid = new SyncGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            System.arraycopy(values[r], 0, grid.values[r], 0, columns);
        }

        return grid;
    }

    //
    // Constructors
    //

    /**
     * Creates a new synchronized grid.
     *
     * @param rows    The number of rows to initialize
     * @param columns The number of columns to initialize
     */
    public SyncGrid(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Creates a new synchronized grid.
     *
     * @param g The grid of which to copy component values from
     */
    public SyncGrid(@Nonnull Grid<? extends E> g) {
        super(g);
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
     * @param r The index of the row to get
     * @param c The index of the column to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized E get(int r, int c) throws IndexOutOfBoundsException {
        return super.get(r, c);
    }

    /**
     * {@inheritDoc}
     *
     * @param r        The index of the row to get
     * @param c        The index of the column to get
     * @param fallback The fallback value of which to return if the element is {@code null}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized E getOrDefault(int r, int c, E fallback) throws IndexOutOfBoundsException {
        return super.getOrDefault(r, c, fallback);
    }

    /**
     * {@inheritDoc}
     *
     * @param r The index of the row to set
     * @param c The index of the column to set
     * @param v The value of which to assign to the specified index
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(int r, int c, E v) throws IndexOutOfBoundsException {
        super.set(r, c, v);
    }

    //
    // Bulk Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param v The value of which to fill this grid with
     */
    @Override
    public synchronized void fill(E v) {
        super.fill(v);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The values of which to selectively fill this grid with
     */
    @Override
    public synchronized void fillEmpty(E v) {
        super.fillEmpty(v);
    }

    /**
     * {@inheritDoc}
     *
     * @param r1 The starting position's row index
     * @param c1 The starting position's column index
     * @param r2 The ending position's row index
     * @param c2 The ending position's column index
     * @param v  The value of which to fill the specified range with
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void fillRange(int r1, int c1, int r2, int c2, E v) throws IndexOutOfBoundsException {
        super.fillRange(r1, c1, r2, c2, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     */
    @Override
    public synchronized void update(@Nonnull Function<? super E, E> f) {
        super.update(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     */
    @Override
    public synchronized void update(@Nonnull TriFunction<? super Integer, ? super Integer, ? super E, E> f) {
        super.update(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value of which to replace
     * @param newValue The new value of which to replace to
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
     * @param r1 The starting position's row index
     * @param c1 The starting position's column index
     * @param r2 The ending position's row index
     * @param c2 The ending position's column index
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Grid<E> subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException {
        return super.subGrid(r1, c1, r2, c2);
    }

    /**
     * {@inheritDoc}
     *
     * @param r1 The starting position's row index
     * @param c1 The starting position's column index
     * @param r2 The ending position's row index
     * @param c2 The ending position's column index
     * @param g  The sub-grid of this grid containing the values to assign
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void setRange(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends E> g)
            throws IndexOutOfBoundsException {
        super.setRange(r1, c1, r2, c2, g);
    }

    //
    // Resizing
    //

    /**
     * {@inheritDoc}
     *
     * @param rows    The number of rows the resized grid should have
     * @param columns The number of columns the resized grid should have
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Grid<E> resize(int rows, int columns) {
        return super.resize(rows, columns);
    }

    //
    // Transposition
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Grid<E> transpose() {
        return super.transpose();
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this grid
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> Grid<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return super.map(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized DoubleGrid mapToDouble(@Nonnull ToDoubleFunction<? super E> f) {
        return super.mapToDouble(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized FloatGrid mapToFloat(@Nonnull ToFloatFunction<? super E> f) {
        return super.mapToFloat(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized LongGrid mapToLong(@Nonnull ToLongFunction<? super E> f) {
        return super.mapToLong(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized IntGrid mapToInt(@Nonnull ToIntFunction<? super E> f) {
        return super.mapToInt(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param g   The grid of which to merge this grid with
     * @param f   The merger function to handle the merging of the two grids
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F, G> Grid<G> merge(@Nonnull Grid<F> g, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        return super.merge(g, f);
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
     * @param a The action of which to execute for each element of this grid
     */
    @Override
    public synchronized void forEach(@Nonnull TriConsumer<Integer, Integer, ? super E> a) {
        super.forEach(a);
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

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized int hashCode() {
        return super.hashCode();
    }
}
