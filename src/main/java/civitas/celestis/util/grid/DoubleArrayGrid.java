package civitas.celestis.util.grid;

import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * A static grid implemented using primitive arrays. An array grid's size
 * cannot be changed after instantiation. Since the underlying data structure
 * is a fixed-size array, array grids are memory-efficient and random access
 * operations tend to be faster than other grid implementations.
 *
 * @see DoubleGrid
 */
public class DoubleArrayGrid implements DoubleGrid {
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
     * Creates a new array grid from a 2D array of values.
     *
     * @param values The values of which to contain in the grid
     * @return The constructed grid
     */
    @Nonnull
    public static DoubleArrayGrid of(@Nonnull double[][] values) {
        final int rows = values.length;
        final int columns = rows > 0 ? values[0].length : 0;
        final DoubleArrayGrid grid = new DoubleArrayGrid(rows, columns);

        for (int r = 0; r < rows; r++) {
            System.arraycopy(values[r], 0, grid.values[r], 0, columns);
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
    public DoubleArrayGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.values = new double[rows][columns];
    }

    /**
     * Creates a new array grid.
     *
     * @param g The grid of which to copy component values from
     */
    public DoubleArrayGrid(@Nonnull DoubleGrid g) {
        this(g.rows(), g.columns());
        setRange(0, 0, rows, columns, g);
    }

    //
    // Variables
    //

    /**
     * The internal 2D array of values.
     */
    protected final double[][] values;

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
     * @param v The value of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(double v) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (values[r][c] == v) return true;
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
    public boolean containsAll(@Nonnull Iterable<Double> i) {
        for (final Double o : i) {
            if (o == null) return false;
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
    public double get(int r, int c) throws IndexOutOfBoundsException {
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
    public void set(int r, int c, double v) throws IndexOutOfBoundsException {
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
    public void fill(double v) {
        for (final double[] row : values) {
            Arrays.fill(row, v);
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
    public void fillRange(int r1, int c1, int r2, int c2, double v) throws IndexOutOfBoundsException {
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
    public void update(@Nonnull DoubleUnaryOperator f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = f.applyAsDouble(values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this grid
     */
    @Override
    public void update(@Nonnull TriFunction<? super Integer, ? super Integer, ? super Double, Double> f) {
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
    public void replaceAll(double oldValue, double newValue) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (values[r][c] != oldValue) continue;
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
    public DoubleGrid subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException {
        final DoubleArrayGrid result = new DoubleArrayGrid(r2 - r1, c2 - c1);

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
    public void setRange(int r1, int c1, int r2, int c2, @Nonnull DoubleGrid g) throws IndexOutOfBoundsException {
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
    public DoubleGrid resize(int rows, int columns) {
        final DoubleArrayGrid result = new DoubleArrayGrid(rows, columns);

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
    public DoubleGrid transpose() {
        final DoubleArrayGrid result = new DoubleArrayGrid(columns, rows);

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
     * @param f The function of which to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleGrid map(@Nonnull DoubleUnaryOperator f) {
        final DoubleArrayGrid result = new DoubleArrayGrid(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.applyAsDouble(values[r][c]);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this grid
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Grid<F> mapToObj(@Nonnull DoubleFunction<? extends F> f) {
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
     * @param g The grid of which to merge this grid with
     * @param f The merger function to handle the merging of the two grids
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleGrid merge(@Nonnull DoubleGrid g, @Nonnull DoubleBinaryOperator f) throws IllegalArgumentException {
        if (rows != g.rows() || columns != g.columns()) {
            throw new IllegalArgumentException("Grid sizes must match for this operation.");
        }

        final DoubleArrayGrid result = new DoubleArrayGrid(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.applyAsDouble(values[r][c], g.get(r, c));
            }
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
    public Iterator<Double> iterator() {
        return stream().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull Consumer<? super Double> a) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                a.accept(values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull TriConsumer<Integer, Integer, ? super Double> a) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                a.accept(r, c, values[r][c]);
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
    public double[] array() {
        final double[] result = new double[rows * columns];

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
    public DoubleStream stream() {
        return Arrays.stream(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<Double> collect() {
        return stream().boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Set<Double> set() {
        return stream().boxed().collect(Collectors.toUnmodifiableSet());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Grid<Double> boxed() {
        final ArrayGrid<Double> result = new ArrayGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.set(r, c, values[r][c]);
            }
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
        if (!(obj instanceof DoubleGrid g)) return false;
        if (rows != g.rows() || columns != g.columns()) return false;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (values[r][c] != g.get(r, c)) return false;
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

        for (final double[] row : values) {
            out.append("\n").append("  ").append(Arrays.toString(row)).append(",");
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
