package civitas.celestis.util.grid;

import civitas.celestis.util.function.ToFloatFunction;
import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A grid which stores atomic references to objects instead of the
 * objects themselves. Atomic grids can be considered thread-safe as
 * long as the underlying elements do not have thread-unsafe properties,
 * such as being modifiable concurrently by non-synchronized code.
 *
 * @param <E> The type of element this grid should hold
 * @see Grid
 */
public class AtomicGrid<E> implements Grid<E> {
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
     * Creates a new atomic grid from a 2D array of values.
     *
     * @param values The values of which to contain in the grid
     * @param <E>    The type of element to contain
     * @return The constructed grid
     */
    @Nonnull
    public static <E> AtomicGrid<E> of(@Nonnull E[][] values) {
        final int rows = values.length;
        final int columns = rows > 0 ? values[0].length : 0;
        final AtomicGrid<E> grid = new AtomicGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                grid.references[r][c].set(values[r][c]);
            }
        }

        return grid;
    }

    //
    // Constructors
    //

    /**
     * Creates a new atomic grid.
     *
     * @param rows    The number of rows to initialize
     * @param columns The number of columns to initialize
     */
    @SuppressWarnings("unchecked")
    public AtomicGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.references = (AtomicReference<E>[][]) Array.newInstance(AtomicReference.class, rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                references[r][c] = new AtomicReference<>();
            }
        }
    }

    /**
     * Creates a new atomic grid.
     *
     * @param g The grid of which to copy component values from
     */
    public AtomicGrid(@Nonnull Grid<? extends E> g) {
        this(g.rows(), g.columns());
        setRange(0, 0, rows, columns, g);
    }

    //
    // Variables
    //

    /**
     * The internal two-dimensional array of references.
     */
    @Nonnull
    protected final AtomicReference<E>[][] references;

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
    @Override
    public int size() {
        return rows * columns;
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
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (Objects.equals(references[r][c].get(), obj)) return true;
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
        return references[r][c].get();
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
    public E getOrDefault(int r, int c, E fallback) throws IndexOutOfBoundsException {
        final E value = references[r][c].get();
        return value != null ? value : fallback;
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
    public synchronized void set(int r, int c, E v) throws IndexOutOfBoundsException {
        references[r][c].set(v);
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
        for (final AtomicReference<E>[] row : references) {
            Arrays.stream(row).forEach(r -> r.set(v));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The values of which to selectively fill this grid with
     */
    @Override
    public synchronized void fillEmpty(E v) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (references[r][c].get() != null) continue;
                references[r][c].set(v);
            }
        }
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
        for (int r = r1; r < r2; r++) {
            for (int c = c1; c < c2; c++) {
                references[r][c].set(v);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     */
    @Override
    public synchronized void update(@Nonnull Function<? super E, E> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                references[r][c].getAndUpdate(f::apply);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     */
    @Override
    public synchronized void update(@Nonnull TriFunction<? super Integer, ? super Integer, ? super E, E> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final int row = r;
                final int column = c;

                references[r][c].getAndUpdate(v -> f.apply(row, column, v));
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value of which to replace
     * @param newValue The new value of which to replace to
     */
    @Override
    public synchronized void replaceAll(E oldValue, E newValue) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!Objects.equals(references[r][c].get(), oldValue)) continue;
                references[r][c].set(newValue);
            }
        }
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
    public Grid<E> subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException {
        final SyncGrid<E> result = new SyncGrid<>(r2 - r1, c2 - c1);

        for (int r = r1; r < r2; r++) {
            for (int c = c1; c < c2; c++) {
                result.values[r][c] = references[r][c].get();
            }
        }

        return result;
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
        for (int r = r1; r < r2; r++) {
            for (int c = c1; c < c2; c++) {
                references[r][c].set(g.get(r - r1, c - c1));
            }
        }
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
    public Grid<E> resize(int rows, int columns) {
        final SyncGrid<E> result = new SyncGrid<>(rows, columns);

        final int minRows = Math.min(this.rows, rows);
        final int minCols = Math.min(this.columns, columns);

        for (int r = 0; r < minRows; r++) {
            for (int c = 0; c < minCols; c++) {
                result.values[r][c] = references[r][c].get();
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
        final SyncGrid<E> result = new SyncGrid<>(columns, rows);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[c][r] = references[r][c].get();
            }
        }

        return result;
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
        final SyncGrid<F> result = new SyncGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.apply(references[r][c].get());
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleGrid mapToDouble(@Nonnull ToDoubleFunction<? super E> f) {
        final DoubleArrayGrid result = new DoubleArrayGrid(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.applyAsDouble(references[r][c].get());
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public FloatGrid mapToFloat(@Nonnull ToFloatFunction<? super E> f) {
        final FloatArrayGrid result = new FloatArrayGrid(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.applyAsFloat(references[r][c].get());
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongGrid mapToLong(@Nonnull ToLongFunction<? super E> f) {
        final LongArrayGrid result = new LongArrayGrid(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.applyAsLong(references[r][c].get());
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntGrid mapToInt(@Nonnull ToIntFunction<? super E> f) {
        final IntArrayGrid result = new IntArrayGrid(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.applyAsInt(references[r][c].get());
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
    public <F, G> Grid<G> merge(@Nonnull Grid<F> g, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        if (rows != g.rows() || columns != g.columns()) {
            throw new IllegalArgumentException("Grid sizes must match for this operation.");
        }

        final SyncGrid<G> result = new SyncGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.apply(references[r][c].get(), g.get(r, c));
            }
        }

        return result;
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
        final E[] result = (E[]) new Object[rows * columns];

        int i = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result[i++] = references[r][c].get();
            }
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
        return Arrays.stream(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return List.of(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Set<E> set() {
        return Set.of(array());
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
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                a.accept(references[r][c].get());
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull TriConsumer<Integer, Integer, ? super E> a) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                a.accept(r, c, references[r][c].get());
            }
        }
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
        if (rows != g.rows() || columns != g.columns()) return false;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!Objects.equals(references[r][c].get(), g.get(r, c))) return false;
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
    @Nonnull
    @Override
    public String toString() {
        final StringBuilder out = new StringBuilder("{");

        for (final AtomicReference<E>[] row : references) {
            out.append("\n").append("  ")
                    .append(Arrays.toString(Arrays.stream(row).map(AtomicReference::get).toArray()))
                    .append(",");
        }

        out.replace(out.length() - 1, out.length(), "").append("\n");

        return out.append("}").toString();
    }

    /**
     * Returns the hash code of this instance.
     *
     * @return The hash code of this instance
     */
    @Override
    public int hashCode() {
        return Grids.hash(this);
    }
}
