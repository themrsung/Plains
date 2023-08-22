package civitas.celestis.math.matrix;

import civitas.celestis.math.vector.Vector;
import civitas.celestis.math.vector.*;
import civitas.celestis.util.array.DoubleArray;
import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import civitas.celestis.util.grid.ArrayGrid;
import civitas.celestis.util.grid.Grid;
import civitas.celestis.util.grid.GridIndex;
import civitas.celestis.util.grid.NumericGrid;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;

/**
 * A two-dimensional grid of {@code double}s.
 *
 * @see NumericGrid
 */
public class Matrix implements NumericGrid<Double, Matrix> {
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
     * Creates a new matrix from a two-dimensional primitive {@code double} array.
     *
     * @param components The 2D array of {@code double}s to map into a matrix
     * @return A {@link Matrix} constructed from the provided components
     */
    @Nonnull
    public static Matrix of(@Nonnull double[][] components) {
        final int rows = components.length;
        final int columns = rows > 0 ? components[0].length : 0;

        final Matrix matrix = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            System.arraycopy(components[r], 0, matrix.values[r], 0, columns);
        }

        return matrix;
    }

    /**
     * Creates a new {@code n*n} identity matrix.
     *
     * @param n The number of dimensions to initialize
     * @return A new identity matrix of {@code n} dimensions
     */
    @Nonnull
    public static Matrix newIdentity(int n) {
        final Matrix matrix = new Matrix(n, n);

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (r != c) continue;
                matrix.values[r][c] = 1;
            }
        }

        return matrix;
    }

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
     * @param size An {@link Index Index} object representing the size of this matrix
     */
    public Matrix(@Nonnull Index size) {
        this.rows = size.row();
        this.columns = size.column();
        this.values = new double[rows][columns];
    }

    /**
     * Creates a new matrix.
     *
     * @param g The grid of which to copy component values from
     */
    public Matrix(@Nonnull Grid<? extends Number> g) {
        this.rows = g.rows();
        this.columns = g.columns();
        this.values = new double[rows][columns];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = g.get(r, c).doubleValue();
            }
        }
    }

    //
    // Variables
    //

    /**
     * The 2D array of values.
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
        return new GridIndex(rows, columns);
    }

    //
    // Properties
    //

    /**
     * Returns whether this matrix is an identity matrix. This is achieved by first checking
     * if this matrix is a square matrix, then checking if all diagonal components are {@code 1}
     * and every non-diagonal component is {@code 0}. If all conditions hold, this return {@code true}.
     *
     * @return {@code true} if this matrix is an identity matrix
     */
    public boolean isIdentity() {
        if (rows != columns) return false;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (r == c) {
                    if (values[r][c] != 1) return false;
                } else {
                    if (values[r][c] != 0) return false;
                }
            }
        }

        return true;
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
    // Arithmetic
    //

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to add to this matrix
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix add(@Nonnull Double s) {
        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c] + s;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract from this matrix
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix subtract(@Nonnull Double s) {
        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c] - s;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this matrix by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix multiply(@Nonnull Double s) {
        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c] * s;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to divide this matrix by
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix divide(@Nonnull Double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");

        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c] / s;
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param m The matrix to add to this matrix
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
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
     * {@inheritDoc}
     *
     * @param m The matrix to subtract from this matrix
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
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
     * {@inheritDoc}
     *
     * @param m The matrix to multiply this matrix by
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix multiply(@Nonnull Matrix m) throws ArithmeticException {
        if (columns != m.rows) {
            throw new ArithmeticException("Matrix dimensions are incompatible for multiplication.");
        }

        final Matrix result = new Matrix(rows, m.columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < m.columns; c++) {
                double sum = 0;

                for (int k = 0; k < columns; k++) {
                    sum += values[r][k] * m.values[k][c];
                }

                result.values[r][c] = sum;
            }
        }

        return result;
    }

    /**
     * Internal method used to multiply this matrix by a vector.
     *
     * @param v The vector of which to multiply by this matrix
     * @return The resulting vector in primitive array form
     * @throws ArithmeticException When the vector's dimensions are different from this matrix's column count
     */
    @Nonnull
    protected double[] multiply(@Nonnull double[] v) throws ArithmeticException {
        if (columns != v.length) {
            throw new ArithmeticException("Vector's dimensions must match the column count of the matrix for multiplication.");
        }

        final double[] result = new double[v.length];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result[r] += values[r][c] * v[c];
            }
        }

        return result;
    }

    /**
     * Applies this matrix to the provided vector {@code v}, then returns the resulting vector.
     *
     * @param v The vector of which to multiply by this matrix
     * @return The resulting vector
     * @throws ArithmeticException When the vector's dimensions are different from this matrix's column count
     */
    @Nonnull
    public Vector<?> multiply(@Nonnull Vector<?> v) throws ArithmeticException {
        return Vector.of(multiply(v.array()));
    }

    /**
     * Applies this matrix to the provided vector {@code v}, then returns the resulting vector.
     *
     * @param v The vector of which to multiply by this matrix
     * @return The resulting vector
     * @throws ArithmeticException When the vector's dimensions are different from this matrix's column count
     */
    @Nonnull
    public Vector1 multiply(@Nonnull Vector1 v) throws ArithmeticException {
        return new Vector1(multiply(v.array())[0]);
    }

    /**
     * Applies this matrix to the provided vector {@code v}, then returns the resulting vector.
     *
     * @param v The vector of which to multiply by this matrix
     * @return The resulting vector
     * @throws ArithmeticException When the vector's dimensions are different from this matrix's column count
     */
    @Nonnull
    public Vector2 multiply(@Nonnull Vector2 v) throws ArithmeticException {
        return new Vector2(multiply(v.array()));
    }

    /**
     * Applies this matrix to the provided vector {@code v}, then returns the resulting vector.
     *
     * @param v The vector of which to multiply by this matrix
     * @return The resulting vector
     * @throws ArithmeticException When the vector's dimensions are different from this matrix's column count
     */
    @Nonnull
    public Vector3 multiply(@Nonnull Vector3 v) throws ArithmeticException {
        return new Vector3(multiply(v.array()));
    }

    /**
     * Applies this matrix to the provided vector {@code v}, then returns the resulting vector.
     *
     * @param v The vector of which to multiply by this matrix
     * @return The resulting vector
     * @throws ArithmeticException When the vector's dimensions are different from this matrix's column count
     */
    @Nonnull
    public Vector4 multiply(@Nonnull Vector4 v) throws ArithmeticException {
        return new Vector4(multiply(v.array()));
    }

    /**
     * Applies this matrix to the provided vector {@code v}, then returns the resulting vector.
     *
     * @param v The vector of which to multiply by this matrix
     * @return The resulting vector
     * @throws ArithmeticException When the vector's dimensions are different from this matrix's column count
     */
    @Nonnull
    public ArrayVector multiply(@Nonnull ArrayVector v) throws ArithmeticException {
        return new ArrayVector(multiply(v.array()));
    }

    //
    // Negation
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix negate() {
        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = -values[r][c];
            }
        }

        return result;
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
    public void apply(@Nonnull Function<? super Double, Double> f) {
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
    public void apply(@Nonnull BiFunction<Index, Double, ? extends Double> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = f.apply(new GridIndex(r, c), values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this grid
     */
    @Override
    public void apply(@Nonnull TriFunction<Integer, Integer, Double, ? extends Double> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = f.apply(r, c, values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this grid with
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
     * @param v The value to fill empty slots of this grid with
     */
    @Override
    public void fillEmpty(Double v) {
        replaceAll(null, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this grid selectively with
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
    public void setRange(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends Double> g) throws IndexOutOfBoundsException {
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
    public void setRange(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Grid<? extends Double> g) throws IndexOutOfBoundsException {
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
    public <F> Grid<F> map(@Nonnull Function<? super Double, ? extends F> f) {
        final ArrayGrid<F> result = new ArrayGrid<>(rows, columns);

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

        final ArrayGrid<G> result = new ArrayGrid<>(rows, columns);

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
        return array().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull Consumer<? super Double> action) {
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
    public void forEach(@Nonnull BiConsumer<Index, ? super Double> action) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                action.accept(new GridIndex(r, c), values[r][c]);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    public void forEach(@Nonnull TriConsumer<Integer, Integer, ? super Double> action) {
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
    public SafeArray<Double> array() {
        final SafeArray<Double> array = new DoubleArray(rows * columns);

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
    public Tuple<Double> tuple() {
        return array().tuple();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<Double> collect() {
        return array().collect();
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
                map.put(new GridIndex(r, c), values[r][c]);
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
