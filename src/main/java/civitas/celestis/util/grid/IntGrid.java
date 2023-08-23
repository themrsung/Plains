package civitas.celestis.util.grid;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * An array-typed static grid which uses the type {@code int}.
 *
 * @see Grid
 */
public class IntGrid implements Grid<Integer> {
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
     * Creates a new integer grid from a 2D array of values.
     *
     * @param values The values of which to contain in the grid
     * @return The constructed grid
     */
    @Nonnull
    public static IntGrid of(@Nonnull int[][] values) {
        final int rows = values.length;
        final int columns = rows > 0 ? values[0].length : 0;
        final IntGrid grid = new IntGrid(rows, columns);

        for (int r = 0; r < rows; r++) {
            System.arraycopy(values[r], 0, grid.values[r], 0, columns);
        }

        return grid;
    }

    //
    // Constructors
    //

    /**
     * Creates a new integer grid.
     *
     * @param rows    The number of rows to initialize
     * @param columns The number of columns to initialize
     */
    public IntGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.values = new int[rows][columns];
    }

    /**
     * Creates a new integer grid.
     *
     * @param g The grid of which to copy component values from
     */
    public IntGrid(@Nonnull Grid<? extends Number> g) {
        this(g.rows(), g.columns());

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = g.get(r, c).intValue();
            }
        }
    }

    //
    // Variables
    //

    /**
     * The internal 2D array of values.
     */
    protected final int[][] values;

    /**
     * The number of rows this grid has.
     */
    protected final int rows;

    /**
     * The number of columns this grid has.
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
    public Integer get(int r, int c) throws IndexOutOfBoundsException {
        return values[r][c];
    }

    /**
     * Since primitive grids cannot contain {@code null}, this method has the same effect
     * as simply calling {@link #get(int, int)}.
     *
     * @param r       The index of the row to get
     * @param c       The index of the column to get
     * @param ignored Ignored
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Integer getOrDefault(int r, int c, Integer ignored) throws IndexOutOfBoundsException {
        return values[r][c];
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
    public void set(int r, int c, Integer v) throws IndexOutOfBoundsException {
        values[r][c] = v;
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
    public void fill(Integer v) {
        for (final int[] row : values) {
            Arrays.fill(row, v);
        }
    }

    /**
     * Since primitive arrays cannot contain {@code null}, this method does nothing.
     *
     * @param ignored Ignored
     */
    @Override
    public void fillEmpty(Integer ignored) {}

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
    public void fillRange(int r1, int c1, int r2, int c2, Integer v) throws IndexOutOfBoundsException {
        for (int r = r1; r < r2; r++) {
            for (int c = c1; c < c2; c++) {
                values[r][c] = v;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     */
    @Override
    public void apply(@Nonnull Function<? super Integer, Integer> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = f.apply(values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     */
    @Override
    public void apply(@Nonnull TriFunction<Integer, Integer, ? super Integer, Integer> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = f.apply(r, c, values[r][c]);
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
    public void replaceAll(Integer oldValue, Integer newValue) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!(Objects.equals(values[r][c], oldValue))) continue;
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
     * @param r1 The starting position's row index
     * @param c1 The starting position's column index
     * @param r2 The ending position's row index
     * @param c2 The ending position's column index
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Grid<Integer> subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException {
        final IntGrid result = new IntGrid(r2 - r1, c2 - c1);

        for (int r = r1; r < r2; r++) {
            System.arraycopy(values[r], c1, result.values[r - r1], 0, c2 - c1);
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
    public void setRange(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends Integer> g)
            throws IndexOutOfBoundsException {
        for (int r = r1; r < r2; r++) {
            for (int c = c1; c < c2; c++) {
                values[r][c] = g.get(r - r1, c - c1);
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
    public Grid<Integer> resize(int rows, int columns) {
        final IntGrid result = new IntGrid(rows, columns);

        final int minRows = Math.min(this.rows, rows);
        final int minCols = Math.min(this.columns, columns);

        for (int r = 0; r < minRows; r++) {
            System.arraycopy(values[r], 0, result.values[r], 0, minCols);
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
    public Grid<Integer> transpose() {
        final IntGrid result = new IntGrid(columns, rows);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[c][r] = values[r][c];
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
    public <F> Grid<F> map(@Nonnull Function<? super Integer, ? extends F> f) {
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
    public <F, G> Grid<G> merge(@Nonnull Grid<F> g, @Nonnull BiFunction<? super Integer, ? super F, G> f)
            throws IllegalArgumentException {
        if (rows != g.rows() || columns != g.columns()) {
            throw new IllegalArgumentException("Grid sizes must match for this operation.");
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
    // Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Integer[] array() {
        final Integer[] result = new Integer[rows * columns];

        /*
         * This is faster than calculating r * columns * c each iteration
         */
        int i = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result[i++] = values[r][c];
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
    public SafeArray<Integer> safeArray() {
        final int[] array = new int[rows * columns];

        /*
         * This is faster than calculating r * columns * c each iteration
         */
        int i = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                array[i++] = values[r][c];
            }
        }

        return SafeArray.ofInt(array);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<Integer> stream() {
        return Arrays.stream(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<Integer> collect() {
        return List.of(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Set<Integer> set() {
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
    public Iterator<Integer> iterator() {
        return stream().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull Consumer<? super Integer> action) {
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
    public void forEach(@Nonnull TriConsumer<Integer, Integer, ? super Integer> action) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                action.accept(r, c, values[r][c]);
            }
        }
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
    public Grid<Integer> copy() {
        final IntGrid result = new IntGrid(rows, columns);

        for (int r = 0; r < rows; r++) {
            System.arraycopy(values[r], 0, result.values[r], 0, columns);
        }

        return result;
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
    @Nonnull
    @Override
    public String toString() {
        final StringBuilder out = new StringBuilder("{");

        for (final int[] row : values) {
            out.append("\n").append("  ").append(Arrays.toString(row)).append(",");
        }

        out.replace(out.length() - 1, out.length(), "").append("\n");

        return out.append("}").toString();
    }
}