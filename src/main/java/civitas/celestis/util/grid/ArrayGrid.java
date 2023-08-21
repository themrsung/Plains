package civitas.celestis.util.grid;

import civitas.celestis.util.array.FastArray;
import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;

/**
 * A two-dimensional grid implemented using a 2D array.
 *
 * @param <E> The type of element this grid should hold
 * @see Grid
 */
public class ArrayGrid<E> implements Grid<E> {
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
     * Creates a new grid from a two-dimensional primitive array.
     *
     * @param elements The 2D array of elements to map into a grid
     * @param <E>      The type of element to contain
     * @return An {@link ArrayGrid} constructed from the provided elements
     */
    @Nonnull
    public static <E> ArrayGrid<E> of(@Nonnull E[][] elements) {
        final int rows = elements.length;
        final int columns = rows > 0 ? elements[0].length : 0;

        final ArrayGrid<E> grid = new ArrayGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            System.arraycopy(elements[r], 0, grid.values[r], 0, columns);
        }

        return grid;
    }

    //
    // Constructors
    //

    /**
     * Creates a new array grid.
     *
     * @param rows    The number of rows to initialize
     * @param columns The number of columns to initialize
     */
    @SuppressWarnings("unchecked")
    public ArrayGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.values = (E[][]) new Object[rows][columns];
    }

    /**
     * Creates a new array grid.
     *
     * @param size The size to initialize this grid to
     */
    @SuppressWarnings("unchecked")
    public ArrayGrid(@Nonnull Index size) {
        this.rows = size.row();
        this.columns = size.column();
        this.values = (E[][]) new Object[rows][columns];
    }

    /**
     * Creates a new array grid.
     *
     * @param g The grid of which to copy values from
     */
    @SuppressWarnings("unchecked")
    public ArrayGrid(@Nonnull Grid<? extends E> g) {
        this.rows = g.rows();
        this.columns = g.columns();
        this.values = (E[][]) new Object[rows][columns];
        setRange(0, 0, rows, columns, g);
    }

    //
    // Variables
    //

    /**
     * The internal array of elements.
     */
    @Nonnull
    protected final E[][] values;

    /**
     * The number of rows.
     */
    protected final int rows;

    /**
     * The number of columns.
     */
    protected final int columns;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return rows * columns;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int rows() {
        return rows;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int columns() {
        return columns;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Index dimensions() {
        return Grid.newIndex(rows, columns);
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
    public boolean contains(@Nullable Object obj) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (Objects.equals(values[r][c], obj)) return true;
            }
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
     * @param r The index of the row to get
     * @param c The index of the column to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int r, int c) throws IndexOutOfBoundsException {
        return values[r][c];
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the slot to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(@Nonnull Index i) throws IndexOutOfBoundsException {
        return values[i.row()][i.column()];
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
    public void set(int r, int c, E v) throws IndexOutOfBoundsException {
        values[r][c] = v;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the slot to set
     * @param v The value to assign to the specified slot
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(@Nonnull Index i, E v) throws IndexOutOfBoundsException {
        values[i.row()][i.column()] = v;
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
    public void apply(@Nonnull Function<? super E, E> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = f.apply(values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this grid
     */
    @Override
    public void apply(@Nonnull BiFunction<Index, E, ? extends E> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = f.apply(Grid.newIndex(r, c), values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this grid
     */
    @Override
    public void apply(@Nonnull TriFunction<Integer, Integer, E, ? extends E> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = f.apply(r, c, values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param e The element to fill this grid with
     */
    @Override
    public void fill(E e) {
        for (int r = 0; r < rows; r++) {
            Arrays.fill(values[r], e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param e The element to fill empty slots of this grid with
     */
    @Override
    public void fillEmpty(E e) {
        replaceAll(null, e);
    }

    /**
     * {@inheritDoc}
     *
     * @param e The element to fill this grid selectively with
     * @param f The filter function of which to test each original element with
     */
    @Override
    public void fillIf(E e, @Nonnull Predicate<? super E> f) {
        apply(old -> f.test(old) ? e : old);
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceAll(E oldValue, E newValue) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!Objects.equals(values[r][c], oldValue)) continue;

                values[r][c] = newValue;
            }
        }
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
    public Grid<E> subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException {
        final ArrayGrid<E> result = new ArrayGrid<>(r2 - r1, c2 - c1);
        for (int r = r1; r < r2; r++) {
            System.arraycopy(values[r], c1, result.values[r - r1], 0, c2 - c1);
        }
        return result;
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
    public Grid<E> subGrid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException {
        return subGrid(i1.row(), i1.column(), i2.row(), i2.column());
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
    public void setRange(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends E> g) throws IndexOutOfBoundsException {
        for (int r = r1; r < r2; r++) {
            for (int c = c1; c < c2; c++) {
                values[r][c] = g.get(r - r1, c - c1);
            }
        }
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
    public void setRange(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Grid<? extends E> g) throws IndexOutOfBoundsException {
        setRange(i1.row(), i1.column(), i2.row(), i2.column(), g);
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
    public <F> Grid<F> map(@Nonnull Function<? super E, ? extends F> f) {
        final ArrayGrid<F> result = new ArrayGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.apply(values[r][c]);
            }
        }

        return result;
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
    public <F, G> Grid<G> merge(@Nonnull Grid<F> g, @Nonnull BiFunction<? super E, ? super F, G> f) throws IllegalArgumentException {
        if (!dimensions().equals(g.dimensions())) {
            throw new IllegalArgumentException("Grid dimensions must match for this operation.");
        }

        final ArrayGrid<G> result = new ArrayGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.apply(values[r][c], g.get(r, c));
            }
        }

        return result;
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
    public Grid<E> transpose() {
        final ArrayGrid<E> result = new ArrayGrid<>(columns, rows);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[c][r] = values[r][c];
            }
        }

        return result;
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
    public Grid<E> resize(int r, int c) {
        final ArrayGrid<E> result = new ArrayGrid<>(r, c);

        final int minRows = Math.min(rows, r);
        final int minCols = Math.min(columns, c);

        for (int i = 0; i < minRows; i++) {
            System.arraycopy(values[i], 0, result.values[i], 0, minCols);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param size The size the resized grid should have
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Grid<E> resize(@Nonnull Index size) {
        return resize(size.row(), size.column());
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
        return array().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> action) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                action.accept(values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Index, ? super E> action) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                action.accept(Grid.newIndex(r, c), values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull TriConsumer<Integer, Integer, ? super E> action) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                action.accept(r, c, values[r][c]);
            }
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
    public SafeArray<E> array() {
        final SafeArray<E> array = new FastArray<>(rows * columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                array.set(r * columns + c, values[r][c]);
            }
        }

        return array;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<E> tuple() {
        return array().tuple();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return array().collect();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Map<Index, E> map() {
        final Map<Index, E> map = new HashMap<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                map.put(Grid.newIndex(r, c), values[r][c]);
            }
        }

        return map;
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
        if (!(obj instanceof Grid<?> g)) return false;
        if (!dimensions().equals(g.dimensions())) return false;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!Objects.equals(values[r][c], g.get(r, c))) return false;
            }
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
    @Override
    @Nonnull
    public String toString() {
        final StringBuilder out = new StringBuilder("{\n");

        for (int r = 0; r < rows; r++) {
            out.append("  ").append(Arrays.toString(values[r])).append(",\n");
        }

        out.replace(out.length() - 2, out.length() - 1, "");

        return out.append("}").toString();
    }
}
