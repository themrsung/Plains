package civitas.celestis.math.matrix;

import civitas.celestis.util.grid.ArrayGrid;
import civitas.celestis.util.grid.Grid;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;

/**
 * A two-dimensional grid of {@code double}s.
 */
public class Matrix implements Grid<Double> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -3908175401554769093L;

    //
    // Constructors
    //

    /**
     * Creates a new matrix.
     *
     * @param rows    The number of rows to initialize
     * @param columns The number of columns to initialize
     */
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.values = new double[rows][columns];
    }

    /**
     * Creates a new matrix.
     *
     * @param size The size of which to initialize this matrix to
     */
    public Matrix(@Nonnull Index size) {
        this.rows = size.row();
        this.columns = size.column();
        this.values = new double[rows][columns];
    }

    /**
     * Creates a new matrix.
     *
     * @param g The grid of which to copy values from
     */
    public Matrix(@Nonnull Grid<? extends Double> g) {
        this.rows = g.rows();
        this.columns = g.columns();
        this.values = new double[rows][columns];
        setAll(0, 0, rows, columns, g);
    }

    //
    // Variables
    //

    /**
     * The internal 2D array of {@code double}s.
     */
    @Nonnull
    protected final double[][] values;

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
     * @param t The tuple of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Tuple<?> t) {
        for (final Object o : t) {
            if (!contains(o)) return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @param c The collection of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Collection<?> c) {
        for (final Object o : c) {
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
    public Double get(int r, int c) throws IndexOutOfBoundsException {
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
    public Double get(@Nonnull Index i) throws IndexOutOfBoundsException {
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
    public void set(int r, int c, Double v) throws IndexOutOfBoundsException {
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
    public void set(@Nonnull Index i, Double v) throws IndexOutOfBoundsException {
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
    public void apply(@Nonnull UnaryOperator<Double> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = f.apply(values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The element to fill this grid with
     */
    @Override
    public void fill(Double v) {
        for (int r = 0; r < rows; r++) {
            Arrays.fill(values[r], v);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The element to fill empty slots of this grid with
     */
    @Override
    public void fillEmpty(Double v) {
        replaceAll(null, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The element to fill this grid selectively with
     * @param f The filter function of which to test each original element with
     */
    @Override
    public void fillIf(Double v, @Nonnull Predicate<? super Double> f) {
        apply(old -> f.test(old) ? v : old);
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceAll(Double oldValue, Double newValue) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (!Objects.equals(values[r][c], oldValue)) continue;

                values[r][c] = newValue;
            }
        }
    }

    //
    // Sub-operation
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
    public Matrix subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException {
        final Matrix result = new Matrix(r2 - r1, c2 - c1);
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
    public Matrix subGrid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException {
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
    public void setAll(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends Double> g) throws IndexOutOfBoundsException {
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
    public void setAll(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Grid<? extends Double> g) throws IndexOutOfBoundsException {
        setAll(i1.row(), i1.column(), i2.row(), i2.column(), g);
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this matrix, then returns the resulting matrix.
     *
     * @param s The scalar to add to each component of this matrix
     * @return The resulting matrix
     */
    @Nonnull
    public Matrix add(double s) {
        return transform(v -> v + s);
    }

    /**
     * Subtracts a scalar from this matrix, then returns the resulting matrix.
     *
     * @param s The scalar to subtract from each component of this matrix
     * @return The resulting matrix
     */
    @Nonnull
    public Matrix subtract(double s) {
        return transform(v -> v - s);
    }

    /**
     * Multiplies this matrix by a scalar, then returns the resulting matrix.
     *
     * @param s The scalar of which to multiply this matrix by
     * @return The resulting matrix
     */
    @Nonnull
    public Matrix multiply(double s) {
        return transform(v -> v * s);
    }

    /**
     * Divides this matrix by a scalar, then returns the resulting matrix.
     *
     * @param s The scalar of which to divide this matrix by
     * @return The resulting matrix
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    public Matrix divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return transform(v -> v / s);
    }

    /**
     * Adds another grid of numbers to this matrix, then returns the resulting matrix.
     *
     * @param g The grid of numbers to add to this matrix
     * @return The resulting matrix
     * @throws ArithmeticException When the grids' dimensions are different
     */
    @Nonnull
    public Matrix add(@Nonnull Grid<? extends Number> g) throws ArithmeticException {
        if (!dimensions().equals(g.dimensions())) {
            throw new ArithmeticException("Grid dimensions must match for this operation.");
        }

        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c] + g.get(r, c).doubleValue();
            }
        }

        return result;
    }

    /**
     * Adds another matrix to this matrix, then returns the resulting matrix.
     *
     * @param m The matrix to add to this matrix
     * @return The resulting matrix
     * @throws ArithmeticException When the matrices' dimensions are different
     */
    @Nonnull
    public Matrix add(@Nonnull Matrix m) throws ArithmeticException {
        if (rows != m.rows || columns != m.columns) {
            throw new ArithmeticException("Matrix dimensions must match for this operation.");
        }

        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c] + m.values[r][c];
            }
        }

        return result;
    }

    /**
     * Subtracts another grid of numbers from this matrix, then returns the resulting matrix.
     *
     * @param g The grid of numbers to subtract from this matrix
     * @return The resulting matrix
     * @throws ArithmeticException When the grids' dimensions are different
     */
    @Nonnull
    public Matrix subtract(@Nonnull Grid<? extends Number> g) throws ArithmeticException {
        if (!dimensions().equals(g.dimensions())) {
            throw new ArithmeticException("Grid dimensions must match for this operation.");
        }

        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c] - g.get(r, c).doubleValue();
            }
        }

        return result;
    }

    /**
     * Subtracts another matrix from this matrix, then returns the resulting matrix.
     *
     * @param m The matrix to subtract from this matrix
     * @return The resulting matrix
     * @throws ArithmeticException When the matrices' dimensions are different
     */
    @Nonnull
    public Matrix subtract(@Nonnull Matrix m) throws ArithmeticException {
        if (rows != m.rows || columns != m.columns) {
            throw new ArithmeticException("Matrix dimensions must match for this operation.");
        }

        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c] - m.values[r][c];
            }
        }

        return result;
    }

    /**
     * Multiplies this matrix by another grid of numbers, then returns the resulting matrix.
     *
     * @param g The grid of numbers to multiply this matrix by
     * @return The resulting matrix
     * @throws ArithmeticException When the grids' dimensions are incompatible
     */
    @Nonnull
    public Matrix multiply(@Nonnull Grid<? extends Number> g) throws ArithmeticException {
        if (columns != g.columns()) {
            throw new ArithmeticException("Matrix and grid dimensions are incompatible for multiplication.");
        }

        final Matrix result = new Matrix(rows, g.columns());

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < g.columns(); c++) {
                double sum = 0;

                for (int i = 0; i < columns; i++) {
                    sum += values[r][i] * g.get(i, c).doubleValue();
                }

                result.values[r][c] = sum;
            }
        }

        return result;
    }

    /**
     * Multiplies this matrix by another matrix, then returns the resulting matrix.
     *
     * @param m The matrix to multiply this matrix by
     * @return The resulting matrix
     * @throws ArithmeticException When the matrices' dimensions are incompatible
     */
    @Nonnull
    public Matrix multiply(@Nonnull Matrix m) throws ArithmeticException {
        if (columns != m.rows) {
            throw new ArithmeticException("Matrix dimensions are incompatible for multiplication.");
        }

        final Matrix result = new Matrix(rows, m.columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < m.columns; c++) {
                double sum = 0;

                for (int i = 0; i < columns; i++) {
                    sum += values[r][i] * m.values[i][c];
                }

                result.values[r][c] = sum;
            }
        }

        return result;
    }

    //
    // Negation
    //

    /**
     * Negates every component of this matrix, then returns anew matrix containing the resulting values.
     *
     * @return The negation of this matrix
     */
    @Nonnull
    public Matrix negate() {
        return transform(v -> -v);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function to apply to each element of this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix transform(@Nonnull Function<? super Double, Double> f) {
        final Matrix result = new Matrix(rows, columns);

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
     * @param f   The function of which to apply to each element of this grid
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Grid<F> map(@Nonnull Function<? super Double, ? extends F> f) {
        final Grid<F> result = new ArrayGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.set(r, c, f.apply(values[r][c]));
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
    public <F, G> Grid<G> merge(@Nonnull Grid<F> g, @Nonnull BiFunction<? super Double, ? super F, G> f) throws IllegalArgumentException {
        if (!dimensions().equals(g.dimensions())) {
            throw new IllegalArgumentException("Grid dimensions must match for this operation.");
        }

        final Grid<G> result = new ArrayGrid<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.set(r, c, f.apply(values[r][c], g.get(r, c)));
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
    public Matrix transpose() {
        final Matrix result = new Matrix(columns, rows);

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
    public Matrix resize(int r, int c) {
        final Matrix result = new Matrix(r, c);

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
    public Matrix resize(@Nonnull Index size) {
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
    public Iterator<Double> iterator() {
        return Arrays.stream(array()).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to perform for each element of this grid
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Index, ? super Double> a) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                a.accept(Grid.newIndex(r, c), values[r][c]);
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
    public Double[] array() {
        final Double[] array = new Double[size()];

        for (int r = 0; r < rows; r++) {
            System.arraycopy(values[r], 0, array, r * columns, columns);
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
    public Tuple<Double> tuple() {
        return Tuple.of(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<Double> collect() {
        return List.of(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Map<Index, Double> map() {
        final Map<Index, Double> map = new HashMap<>();

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
                if (!(Objects.equals(values[r][c], g.get(r, c)) ||
                        (g.get(r, c) instanceof Number n && n.doubleValue() == values[r][c]))) return false;
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
