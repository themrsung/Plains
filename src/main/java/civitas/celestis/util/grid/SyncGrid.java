package civitas.celestis.util.grid;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.*;

/**
 * A thread-safe implementation of a grid.
 *
 * @param <E> The type of element this grid should hold
 * @see Grid
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
    // Constructors
    //

    /**
     * Creates a new thread-safe array grid.
     *
     * @param rows    The number of rows to initialize
     * @param columns The number of columns to initialize
     */
    public SyncGrid(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Creates a new thread-safe array grid.
     *
     * @param size The size to initialize this grid to
     */
    public SyncGrid(@Nonnull Index size) {
        super(size);
    }

    /**
     * Creates a new thread-safe grid.
     *
     * @param g The grid of which to copy values from
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
     * @param r The index of the row to set
     * @param c The index of the column to set
     * @param v The value to assign to the specified slot
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void set(int r, int c, E v) throws IndexOutOfBoundsException {
        super.set(r, c, v);
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
     * @param f The function of which to apply to each slot of this grid
     */
    @Override
    public synchronized void apply(@Nonnull UnaryOperator<E> f) {
        super.apply(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this grid
     */
    @Override
    public synchronized void apply(@Nonnull BiFunction<Index, E, ? extends E> f) {
        super.apply(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this grid
     */
    @Override
    public synchronized void apply(@Nonnull TriFunction<Integer, Integer, E, ? extends E> f) {
        super.apply(f);
    }

    /**
     * {@inheritDoc}
     *
     * @param e The element to fill this grid with
     */
    @Override
    public synchronized void fill(E e) {
        super.fill(e);
    }

    /**
     * {@inheritDoc}
     *
     * @param e The element to fill empty slots of this grid with
     */
    @Override
    public synchronized void fillEmpty(E e) {
        super.fillEmpty(e);
    }

    /**
     * {@inheritDoc}
     *
     * @param e The element to fill this grid selectively with
     * @param f The filter function of which to test each original element with
     */
    @Override
    public synchronized void fillIf(E e, @Nonnull Predicate<? super E> f) {
        super.fillIf(e, f);
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
     * @param r1 The starting point's row index
     * @param c1 The starting point's column index
     * @param r2 The ending point's row index
     * @param c2 The ending point's column index
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
     * @param i1 The starting index of the sub-grid
     * @param i2 The ending index of the sub-grid
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Grid<E> subGrid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException {
        return super.subGrid(i1, i2);
    }

    /**
     * {@inheritDoc}
     *
     * @param r1 The starting point's row index
     * @param c1 The starting point's column index
     * @param r2 The ending point's row index
     * @param c2 The ending point's column index
     * @param g  The grid containing the values to assign
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void setRange(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends E> g) throws IndexOutOfBoundsException {
        super.setRange(r1, c1, r2, c2, g);
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The starting index at which to start assigning values
     * @param i2 The ending index at which to stop assigning values
     * @param g  The grid containing the values to assign
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void setRange(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Grid<? extends E> g) throws IndexOutOfBoundsException {
        super.setRange(i1, i2, g);
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
    // Resizing
    //

    /**
     * {@inheritDoc}
     *
     * @param r The number of rows the resized grid should have
     * @param c The number of columns the resized grid should have
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Grid<E> resize(int r, int c) {
        return super.resize(r, c);
    }

    /**
     * {@inheritDoc}
     *
     * @param size The size the resized grid should have
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Grid<E> resize(@Nonnull Index size) {
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
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> action) {
        super.forEach(action);
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Index, ? super E> action) {
        super.forEach(action);
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull TriConsumer<Integer, Integer, ? super E> action) {
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
