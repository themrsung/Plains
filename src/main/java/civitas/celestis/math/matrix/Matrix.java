package civitas.celestis.math.matrix;

import civitas.celestis.math.vector.*;
import civitas.celestis.util.grid.DoubleGrid;
import civitas.celestis.util.grid.Grid;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A specialized {@link DoubleGrid} used in a mathematical context.
 * Matrices provide various mathematical operations such as matrix-scalar,
 * matrix-vector, and matrix-matrix arithmetic.
 *
 * @see Grid
 * @see DoubleGrid
 */
public class Matrix extends DoubleGrid {
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
     * Creates a new matrix from a 2D array of values.
     *
     * @param values The values of which to contain in the grid
     * @return The constructed matrix
     */
    @Nonnull
    public static Matrix of(@Nonnull double[][] values) {
        final int rows = values.length;
        final int columns = rows > 0 ? values[0].length : 0;
        final Matrix matrix = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            System.arraycopy(values[r], 0, matrix.values[r], 0, columns);
        }

        return matrix;
    }

    //
    // Identity
    //

    /**
     * Returns a new {@code n*n} identity matrix.
     *
     * @param n The number of dimensions to create the identity matrix of
     * @return A new {@code n*n} identity matrix
     */
    @Nonnull
    public static Matrix newIdentity(int n) {
        return switch (n) {
            case 2 -> of(IDENTITY_2x2);
            case 3 -> of(IDENTITY_3x3);
            case 4 -> of(IDENTITY_4x4);
            default -> {
                final Matrix matrix = new Matrix(n, n);

                for (int r = 0; r < n; r++) {
                    for (int c = 0; c < n; c++) {
                        if (r != c) continue;
                        matrix.values[r][c] = 1;
                    }
                }

                yield matrix;
            }
        };
    }

    /**
     * The values of a 2x2 identity matrix.
     */
    private static final double[][] IDENTITY_2x2 = {{1, 0}, {0, 1}};

    /**
     * The values of a 3x3 identity matrix.
     */
    private static final double[][] IDENTITY_3x3 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};

    /**
     * The values of a 4x4 identity matrix.
     */
    private static final double[][] IDENTITY_4x4 = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};

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
        super(rows, columns);
    }

    /**
     * Creates a new matrix.
     *
     * @param g The numeric grid of which to copy values from
     */
    public Matrix(@Nonnull Grid<? extends Number> g) {
        super(g);
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this matrix, then returns the resulting matrix.
     *
     * @param s The scalar of which to add to this matrix
     * @return The resulting matrix
     */
    @Nonnull
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
     * Subtracts this matrix by a scalar, then returns the resulting matrix.
     *
     * @param s The scalar of which to subtract from this matrix
     * @return The resulting matrix
     */
    @Nonnull
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
     * Multiplies this matrix by a scalar, then returns the resulting matrix.
     *
     * @param s The scalar of this to multiply this matrix by
     * @return The resulting matrix
     */
    @Nonnull
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
     * Divides this matrix by a scalar, then returns the resulting matrix.
     *
     * @param s The scalar of which to divide this matrix by
     * @return THe resulting matrix
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    public Matrix divide(double s) throws ArithmeticException {
        if (s == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }

        final Matrix result = new Matrix(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c] / s;
            }
        }

        return result;
    }

    /**
     * Multiplies a vector by this matrix, then returns the resulting vector.
     *
     * @param v   The vector of which to multiply to this matrix
     * @param <V> The type of vector to multiply by this matrix
     * @return The resulting vector-matrix product
     * @throws ArithmeticException When the number of columns is not equal to the vector's component count
     */
    @Nonnull
    public <V extends Vector<V>> V multiply(@Nonnull V v) throws ArithmeticException {
        if (columns != v.dimensions()) {
            throw new ArithmeticException("The number of columns must match the vector's dimension count for multiplication.");
        }

        final double[] result = v.array();
        Arrays.fill(result, 0);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result[c] += values[r][c] * v.get(c);
            }
        }

        final AtomicInteger i = new AtomicInteger(0);
        return v.map(c -> result[i.getAndIncrement()]);
    }

    /**
     * Multiplies a vector by this matrix, then returns the resulting vector.
     *
     * @param v The vector of which to multiply to this matrix
     * @return The resulting vector-matrix product
     * @throws ArithmeticException When the number of columns is not equal to the vector's component count
     */
    @Nonnull
    public Vector1 multiply(@Nonnull Vector1 v) throws ArithmeticException {
        if (columns != 1) {
            throw new ArithmeticException("The number of columns must match the vector's dimension count for multiplication.");
        }

        final double[] result = new double[1];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result[c] += values[r][c] * v.get(c);
            }
        }

        return new Vector1(result);
    }

    /**
     * Multiplies a vector by this matrix, then returns the resulting vector.
     *
     * @param v The vector of which to multiply to this matrix
     * @return The resulting vector-matrix product
     * @throws ArithmeticException When the number of columns is not equal to the vector's component count
     */
    @Nonnull
    public Vector2 multiply(@Nonnull Vector2 v) throws ArithmeticException {
        if (columns != 2) {
            throw new ArithmeticException("The number of columns must match the vector's dimension count for multiplication.");
        }

        final double[] result = new double[2];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result[c] += values[r][c] * v.get(c);
            }
        }

        return new Vector2(result);
    }

    /**
     * Multiplies a vector by this matrix, then returns the resulting vector.
     *
     * @param v The vector of which to multiply to this matrix
     * @return The resulting vector-matrix product
     * @throws ArithmeticException When the number of columns is not equal to the vector's component count
     */
    @Nonnull
    public Vector3 multiply(@Nonnull Vector3 v) throws ArithmeticException {
        if (columns != 3) {
            throw new ArithmeticException("The number of columns must match the vector's dimension count for multiplication.");
        }

        final double[] result = new double[3];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result[c] += values[r][c] * v.get(c);
            }
        }

        return new Vector3(result);
    }

    /**
     * Multiplies a vector by this matrix, then returns the resulting vector.
     *
     * @param v The vector of which to multiply to this matrix
     * @return The resulting vector-matrix product
     * @throws ArithmeticException When the number of columns is not equal to the vector's component count
     */
    @Nonnull
    public Vector4 multiply(@Nonnull Vector4 v) throws ArithmeticException {
        if (columns != 4) {
            throw new ArithmeticException("The number of columns must match the vector's dimension count for multiplication.");
        }

        final double[] result = new double[4];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result[c] += values[r][c] * v.get(c);
            }
        }

        return new Vector4(result);
    }

    /**
     * Adds another matrix to this matrix, then returns the resulting matrix.
     *
     * @param m The matrix of which to add to this matrix
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
     * Subtracts another matrix from this matrix, then returns the resulting matrix.
     *
     * @param m The matrix of which to subtract from this matrix
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
     * Multiplies this matrix by another matrix, then returns the resulting matrix.
     *
     * @param m The matrix of which to multiply this matrix by
     * @return The resulting matrix
     * @throws ArithmeticException When the matrices' dimensions are incompatible for multiplication
     */
    @Nonnull
    public Matrix multiply(@Nonnull Matrix m) throws ArithmeticException {
        if (columns != m.rows) {
            throw new ArithmeticException("Matrix dimensions are incompatible for multiplication.");
        }

        final Matrix result = new Matrix(rows, m.columns);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.columns; j++) {
                double sum = 0;

                for (int k = 0; k < columns; k++) {
                    sum += values[i][k] * m.values[k][j];
                }

                result.values[i][j] = sum;
            }
        }

        return result;
    }

    //
    // Determinant
    //

    /**
     * Calculates and returns the determinant of this matrix.
     *
     * @return The determinant of this matrix
     * @throws IllegalStateException When this matrix is not a square matrix (the row count and
     *                               column count are different)
     */
    public double det() throws IllegalStateException {
        if (rows != columns) {
            throw new IllegalStateException("The determinant can only be calculated for square matrices.");
        }

        return switch (rows) {
            case 0 -> 0;
            case 1 -> values[0][0];
            case 2 -> values[0][0] * values[1][1] - values[0][1] * values[1][0];
            default -> throw new UnsupportedOperationException("3x3 and larger matrices are not supported yet.");
        };
    }
}
