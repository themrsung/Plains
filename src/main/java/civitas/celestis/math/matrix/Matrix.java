package civitas.celestis.math.matrix;

import civitas.celestis.util.group.ArrayGrid;
import civitas.celestis.util.group.Grid;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A numerical grid structure of raw primitive {@code double}s.
 * This class is preferred over grids of boxed {@link Double} due to less
 * overhead in dealing with objects.
 */
public class Matrix implements RawDoubleMatrix<Matrix> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 4928781143416180021L;

    //
    // Constructors
    //

    /**
     * Creates a new matrix.
     *
     * @param r The number of rows to initialize
     * @param c The number of columns to initialize
     */
    public Matrix(int r, int c) {
        this.rows = r;
        this.columns = c;
        this.values = new double[r][c];
    }

    /**
     * Creates a new matrix.
     *
     * @param size The size to initialize this matrix to
     */
    public Matrix(@Nonnull Index size) {
        this(size.row(), size.column());
    }

    /**
     * Creates a new grid.
     *
     * @param values The values to contain in this grid
     */
    public Matrix(@Nonnull double[][] values) {
        this.rows = values.length;
        this.columns = rows > 0 ? values[0].length : 0;
        this.values = values;
    }

    /**
     * Creates a new grid.
     *
     * @param g The grid of which to copy elements from
     */
    public Matrix(@Nonnull Grid<Double> g) {
        this.rows = g.rows();
        this.columns = g.columns();
        this.values = new double[rows][columns];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = g.get(r, c);
            }
        }
    }

    //
    // Variables
    //

    /**
     * The 2D array containing the values of this grid.
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
    // Dimensions
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

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Index dimensions() {
        return Grid.indexOf(rows, columns);
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
     * @param i The iterable object containing the elements to check for containment
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
    // Getters
    //

    /**
     * {@inheritDoc}
     *
     * @param r The index of row to get
     * @param c The index of column to get
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
     * @param i The index of element to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Double get(@Nonnull Index i) throws IndexOutOfBoundsException {
        return values[i.row()][i.column()];
    }

    //
    // Setters
    //

    /**
     * {@inheritDoc}
     *
     * @param r The index of row to set
     * @param c The index of column to set
     * @param v The value to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(int r, int c, Double v) throws IndexOutOfBoundsException {
        values[r][c] = v;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of element to set
     * @param v The value to assign to the {@code i}th position of this grid
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
    public Matrix add(double s) {
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
    public Matrix subtract(double s) {
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
    public Matrix multiply(double s) {
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
    public Matrix divide(double s) throws ArithmeticException {
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
     * @param s The scalar to divide this matrix by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix divideAllowZero(double s) {
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
     * @param v The value to fill this grid with
     */
    @Override
    public void fill(Double v) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                values[r][c] = v;
            }
        }
    }

    /**
     * As primitive {@code double}s cannot be {@code null},
     * this method does nothing.
     *
     * @param ignored Ignored
     */
    @Override
    public void fillEmpty(Double ignored) {}

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this grid with
     * @param f The filter function to use
     */
    @Override
    public void fillIf(Double v, @Nonnull Predicate<? super Double> f) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (f.test(values[r][c])) values[r][c] = v;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The value to replace
     * @param newValue The value to replace to
     */
    @Override
    public void replaceAll(Double oldValue, Double newValue) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (Objects.equals(values[r][c], oldValue)) values[r][c] = newValue;
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
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix subGrid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException {
        final int r1 = i1.row();
        final int c1 = i1.column();
        final int r2 = i2.row();
        final int c2 = i2.column();

        final Matrix result = new Matrix(r2 - r1, c2 - c1);

        for (int r = r1; r < r2; r++) {
            System.arraycopy(values[r], c1, result.values[r - r1], 0, c2 - c1);
        }

        return result;
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
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @param g  The grid containing the values to assign
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void setAll(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Grid<? extends Double> g) throws IndexOutOfBoundsException {
        final int r1 = i1.row();
        final int c1 = i1.column();
        final int r2 = i2.row();
        final int c2 = i2.column();

        for (int r = r1; r < r2; r++) {
            for (int c = c1; c < c2; c++) {
                values[r][c] = g.get(r - r1, c - c1);
            }
        }
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function to apply to each element of this object
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
     * @param f   The function to apply to each element of this object
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> ArrayGrid<F> map(@Nonnull Function<? super Double, F> f) {
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

    /**
     * {@inheritDoc}
     *
     * @param r The number of rows the new grid should have
     * @param c The number of columns the new grid should have
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
     * @param size The size of the new grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix resize(@Nonnull Index size) {
        final int r = size.row();
        final int c = size.column();

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
     * @param g The other grid to merge with
     * @param f The merger function to apply
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Matrix merge(
            @Nonnull Grid<Double> g,
            @Nonnull BiFunction<? super Double, ? super Double, Double> f
    ) throws IllegalArgumentException {
        if (!dimensions().equals(g.dimensions())) {
            throw new IllegalArgumentException("Grid dimensions must match for this operation.");
        }

        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = f.apply(values[r][c], g.get(r, c));
            }
        }

        return result;
    }

    //
    // Type Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<Double> collect() {
        final List<Double> unwrapped = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                unwrapped.add(values[r][c]);
            }
        }

        return unwrapped;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Map<Index, Double> map() {
        final Map<Index, Double> result = new HashMap<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.put(Grid.indexOf(r, c), values[r][c]);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Iterator<Double> iterator() {
        final double[] unwrapped = new double[rows * columns];

        for (int r = 0; r < rows; r++) {
            if (columns >= 0) System.arraycopy(values[r], 0, unwrapped, r * columns, columns);
        }

        return Arrays.stream(unwrapped).iterator();
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
     * Parses a string into a matrix.
     *
     * @param s String to parse
     * @return Matrix parsed from given string
     * @throws NumberFormatException When the string is not parsable to a matrix
     */
    @Nonnull
    public static Matrix parseMatrix(@Nonnull String s) throws NumberFormatException {
        final String[] lines = s.replaceAll("\\{\n|}", "").split(",\n");

        final int rows = lines.length;
        final int columns;
        try {
            columns = lines[0].replaceAll("\\[|]", "").trim().split(", ").length;
        } catch (IndexOutOfBoundsException e) {
            throw new NumberFormatException("String is not a grid.");
        }

        final double[][] values = new double[rows][columns];

        for (int r = 0; r < lines.length; r++) {
            final String cleanLine = lines[r].replaceAll("\\[|]", "").trim();
            final String[] elements = cleanLine.split(", ");

            for (int c = 0; c < columns; c++) {
                values[r][c] = Double.parseDouble(elements[c]);
            }
        }

        return new Matrix(values);
    }

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
            out.append("  ");
            out.append(Arrays.toString(values[r]));
            out.append(",\n");
        }

        out.replace(out.length() - 2, out.length() - 1, "");

        return out.append("}").toString();
    }
}
