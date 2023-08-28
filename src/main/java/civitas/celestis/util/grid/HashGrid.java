package civitas.celestis.util.grid;

import civitas.celestis.util.function.ToFloatFunction;
import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A grid whose data is stored as a {@link HashMap}.
 *
 * @param <E> The type of element this grid should hold
 * @see Grid
 * @see DynamicGrid
 */
public class HashGrid<E> implements DynamicGrid<E> {
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
     * Creates a new hash grid from a 2D array of values.
     *
     * @param values The values of which to contain in the grid
     * @param <E>    The type of element to contain
     * @return The constructed grid
     */
    @Nonnull
    public static <E> HashGrid<E> of(@Nonnull E[][] values) {
        final int rows = values.length;
        final int columns = rows > 0 ? values[0].length : 0;

        final HashGrid<E> result = new HashGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final E value = values[r][c];
                if (value == null) continue;

                final int i = r * columns + c;
                result.values.put(i, value);
            }
        }

        return result;
    }

    //
    // Constructors
    //

    /**
     * Creates a new 0x0 hash grid.
     */
    public HashGrid() {
        this(0, 0);
    }

    /**
     * Creates a new hash grid.
     *
     * @param rows    The number of rows to initialize
     * @param columns The number of columns to initialize
     */
    public HashGrid(int rows, int columns) {
        if (rows < 0 || columns < 0) {
            throw new IllegalArgumentException("A grid cannot have negative dimensions.");
        }

        this.rows = rows;
        this.columns = columns;
        this.values = new HashMap<>();
    }

    /**
     * Creates a new hash grid. Entries with the value {@code null} will be ignored.
     *
     * @param g The grid of which to copy component values from
     */
    public HashGrid(@Nonnull Grid<? extends E> g) {
        this(g.rows(), g.columns());

        g.forEach((r, c, v) -> {
            if (v == null) return;
            values.put(r * columns + c, v);
        });
    }

    //
    // Variables
    //

    /**
     * The internal map of values.
     */
    protected final HashMap<Integer, E> values;

    /**
     * The last-known number of rows.
     */
    protected volatile int rows;

    /**
     * The last-known number of columns.
     */
    protected volatile int columns;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized int rows() {
        return rows;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized int columns() {
        return columns;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized int size() {
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
        return values.containsValue(obj);
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
    public synchronized E get(int r, int c) throws IndexOutOfBoundsException {
        if (r >= rows || c >= columns) {
            throw new IndexOutOfBoundsException("Indices " + r + ", " + c + " are out of bounds for this grid.");
        }

        return values.get(r * columns + c);
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
        if (r >= rows || c >= columns) {
            throw new IndexOutOfBoundsException("Indices " + r + ", " + c + " are out of bounds for this grid.");
        }

        return values.getOrDefault(r * columns + c, fallback);
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
        if (r >= rows || c >= columns) {
            throw new IndexOutOfBoundsException("Indices " + r + ", " + c + " are out of bounds for this grid.");
        }

        values.put(r * columns + c, v);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        values.clear();
    }

    /**
     * {@inheritDoc}
     *
     * @param r The index of the row to remove
     * @param c The index of the column to remove
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean remove(int r, int c) {
        return values.remove(r * columns + c) != null;
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
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values.put(r * columns + c, v);
            }
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
                final int i = r * columns + c;
                if (values.get(i) != null) continue;
                values.put(i, v);
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
                if (r >= rows || c >= columns) {
                    throw new IndexOutOfBoundsException("Index is out of bounds.");
                }

                values.put(r * columns + c, v);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     */
    @Override
    public void update(@Nonnull Function<? super E, E> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final int i = r * columns + c;
                values.put(i, f.apply(values.get(i)));
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     */
    @Override
    public void update(@Nonnull TriFunction<? super Integer, ? super Integer, ? super E, E> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final int i = r * columns + c;
                values.put(i, f.apply(r, c, values.get(i)));
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
    public void replaceAll(E oldValue, E newValue) {
        values.replaceAll((i, v) -> Objects.equals(v, oldValue) ? newValue : v);
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
        final HashGrid<E> result = new HashGrid<>(r2 - r1, c2 - c1);

        for (int r = r1; r < r2; r++) {
            for (int c = 0; c < c2; c++) {
                if (r >= rows || c >= columns) {
                    throw new IndexOutOfBoundsException("Range is out of bounds");
                }

                final int i = r * columns + c;
                if (!values.containsKey(i)) continue;
                result.values.put(i, values.get(i));
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
    public void setRange(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends E> g) throws IndexOutOfBoundsException {
        for (int r = r1; r < r2; r++) {
            for (int c = c1; c < c2; c++) {
                if (r >= rows || c >= columns) {
                    throw new IndexOutOfBoundsException("Range is out of bounds");
                }

                values.put(r * columns + c, g.get(r, c));
            }
        }
    }

    //
    // Resizing
    //

    /**
     * Calculates the minimum required dimensions to hold all elements of this grid.
     *
     * @return The minimum dimensions in rows-columns order
     */
    protected int[] calculateRequiredDimensions() {
        int minRows = Integer.MAX_VALUE;
        int minCols = Integer.MAX_VALUE;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!values.containsKey(r * columns + c)) continue;
                minRows = Math.min(minRows, r);
                minCols = Math.min(minCols, c);
            }
        }

        if (minRows == Integer.MAX_VALUE) minRows = 0;
        if (minCols == Integer.MAX_VALUE) minCols = 0;

        return new int[]{minRows, minCols};
    }

    /**
     * Removes all entries which are outside the current bounds.
     */
    protected synchronized void removeUncontainableEntries() {
        final Set<Integer> indices = new HashSet<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                indices.add(r * columns + c);
            }
        }

        final HashMap<Integer, E> temp = new HashMap<>(values);

        temp.forEach((i, v) -> {
            if (indices.contains(i)) return;
            values.remove(i);
        });
    }

    /**
     * Given a new column count, this remaps the value map of this grid.
     *
     * @param newColumns The new column count
     */
    protected synchronized void remapValues(int newColumns) {
        final HashMap<Integer, E> temp = new HashMap<>(values);
        values.clear();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final int i1 = r * columns + c;
                final int i2 = r * newColumns + c;

                if (!temp.containsKey(i1)) continue;
                values.put(i2, temp.get(i1));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clean() {
        final HashMap<Integer, E> temp = new HashMap<>(values);
        temp.forEach((i, v) -> {
            if (v != null) return;
            values.remove(i);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void trim() {
        final int[] dimensions = calculateRequiredDimensions();

        rows = dimensions[0];
        columns = dimensions[1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void cleanAndTrim() {
        clean();
        trim();
    }

    /**
     * {@inheritDoc}
     *
     * @param rows    The number rows to resize to
     * @param columns The number of columns to resize to
     */
    @Override
    public synchronized void setSize(int rows, int columns) {
        // Validate the new size
        if (rows < 0 || columns < 0) {
            throw new IllegalArgumentException("A grid cannot have negative dimensions.");
        }

        // Remap values to the new indexing system
        remapValues(columns);

        // Whether this is an expansive operation (the size is getting larger)
        final boolean expansive = rows >= this.rows && columns >= this.columns;

        // Set variables
        this.rows = rows;
        this.columns = columns;

        // No need to remove entries
        if (expansive) return;

        // Remove uncontainable entries
        removeUncontainableEntries();
    }

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
        final HashGrid<E> result = new HashGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final int i = r * columns + c;

                final E value = values.get(i);
                if (value == null) continue;

                result.values.put(i, value);
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
    public synchronized Grid<E> transpose() {
        final HashGrid<E> result = new HashGrid<>(columns, rows);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final int i1 = r * columns + c;
                final int i2 = c * rows + r;

                if (!values.containsKey(i1)) continue;
                result.values.put(i2, values.get(i1));
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
     * @param f   The function of which to apply to each element of this grid
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> Grid<F> map(@Nonnull Function<? super E, ? extends F> f) {
        final HashGrid<F> result = new HashGrid<>(rows, columns);
        values.forEach((i, v) -> result.values.put(i, f.apply(v)));
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
        forEach((r, c, v) -> result.values[r][c] = f.applyAsDouble(v));
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
        forEach((r, c, v) -> result.values[r][c] = f.applyAsFloat(v));
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
        forEach((r, c, v) -> result.values[r][c] = f.applyAsLong(v));
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
        forEach((r, c, v) -> result.values[r][c] = f.applyAsInt(v));
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
    public synchronized <F, G> Grid<G> merge(@Nonnull Grid<F> g, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        final HashGrid<G> result = new HashGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final int i = r * columns + c;
                result.values.put(i, f.apply(values.get(i), g.get(r, c)));
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
        return (E[]) values.values().toArray();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<E> stream() {
        return values.values().stream();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return List.copyOf(values.values());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Set<E> set() {
        return Set.copyOf(values.values());
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
        return values.values().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> a) {
        values.values().forEach(a);
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action of which to execute for each element of this grid
     */
    @Override
    public synchronized void forEach(@Nonnull TriConsumer<Integer, Integer, ? super E> a) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                a.accept(r, c, values.get(r * columns + c));
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
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Grid<?> g)) return false;
        if (rows != g.rows() || columns != g.columns()) return false;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!Objects.equals(values.get(r * columns + c), g.get(r, c))) return false;
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
    @SuppressWarnings("unchecked")
    public synchronized String toString() {
        final StringBuilder out = new StringBuilder("{");

        final E[][] values = (E[][]) new Object[rows][columns];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                final int i = r * columns + c;
                if (!this.values.containsKey(i)) continue;
                values[r][c] = this.values.get(i);
            }
        }

        for (final E[] row : values) {
            out.append("\n").append("  ").append(Arrays.toString(row)).append(",");
        }

        out.replace(out.length() - 1, out.length(), "").append("\n");

        return out.append("}").toString();
    }
}
