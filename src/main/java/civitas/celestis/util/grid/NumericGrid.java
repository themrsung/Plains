package civitas.celestis.util.grid;

import civitas.celestis.math.matrix.LongGrid;
import civitas.celestis.math.matrix.Matrix;
import jakarta.annotation.Nonnull;

import java.util.Arrays;

/**
 * A numerical grid of numbers.
 *
 * @param <N> The type of number this matrix contains
 * @param <M> The grid itself (used as the parameter and return type of various operations)
 * @see Grid
 * @see Matrix
 * @see LongGrid
 */
public interface NumericGrid<N extends Number, M extends NumericGrid<N, M>> extends Grid<N> {
    //
    // Factory
    //

    /**
     * Creates a new matrix from a two-dimensional array of {@link Number}s.
     *
     * @param values The 2D array of values to map into a matrix
     * @param <N>    The type of number to map into a matrix
     * @return A new matrix instance whose components are populated from that of the provided array
     */
    @Nonnull
    static <N extends Number> Matrix of(@Nonnull N[][] values) {
        final int rows = values.length;
        final int columns = rows > 0 ? values[0].length : 0;

        if (rows == 0 || columns == 0) {
            return Matrix.of(new double[0][0]);
        }

        final double[][] unboxed = new double[rows][columns];

        for (int r = 0; r < rows; r++) {
            unboxed[r] = Arrays.stream(values[r]).mapToDouble(Number::doubleValue).toArray();
        }

        return of(unboxed);
    }

    /**
     * Creates a new matrix from a two-dimensional array of {@code double}s.
     *
     * @param values The 2D array of values to map into a matrix
     * @return A new matrix instance whose components are populated from that of the provided array
     */
    @Nonnull
    static Matrix of(@Nonnull double[][] values) {
        return Matrix.of(values);
    }

    /**
     * Creates a new grid of {@code long}s from a two-dimensional array of {@code long}s.
     *
     * @param values The 2D array of values to map into a grid
     * @return A new {@link LongGrid} instance whose components are populated from that of the provided array
     */
    @Nonnull
    static LongGrid of(@Nonnull long[][] values) {
        return LongGrid.of(values);
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this matrix, then returns the resulting matrix.
     *
     * @param s The scalar to add to this matrix
     * @return The resulting matrix
     */
    @Nonnull
    M add(@Nonnull N s);

    /**
     * Subtracts a scalar from this matrix, then returns the resulting matrix.
     *
     * @param s The scalar to subtract from this matrix
     * @return The resulting matrix
     */
    @Nonnull
    M subtract(@Nonnull N s);

    /**
     * Multiplies this matrix by a scalar, then returns the resulting matrix.
     *
     * @param s The scalar to multiply this matrix by
     * @return The resulting matrix
     */
    @Nonnull
    M multiply(@Nonnull N s);

    /**
     * Divides this matrix by a scalar, then returns the resulting matrix.
     *
     * @param s The scalar to divide this matrix by
     * @return The resulting matrix
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    M divide(@Nonnull N s) throws ArithmeticException;

    /**
     * Adds another matrix to this matrix, then returns the resulting matrix.
     *
     * @param m The matrix to add to this matrix
     * @return The resulting matrix
     * @throws ArithmeticException When the matrices' dimensions are different
     */
    @Nonnull
    M add(@Nonnull M m) throws ArithmeticException;

    /**
     * Subtracts another matrix from this matrix, then returns the resulting matrix.
     *
     * @param m The matrix to subtract from this matrix
     * @return The resulting matrix
     * @throws ArithmeticException When the matrices' dimensions are different
     */
    @Nonnull
    M subtract(@Nonnull M m) throws ArithmeticException;

    /**
     * Multiplies this matrix by another matrix, then returns the resulting matrix.
     *
     * @param m The matrix to multiply this matrix by
     * @return The resulting matrix
     * @throws ArithmeticException When the matrices' dimensions are incompatible
     */
    @Nonnull
    M multiply(@Nonnull M m) throws ArithmeticException;

    //
    // Negation
    //

    /**
     * Negates this matrix, then returns the negated matrix.
     *
     * @return The negation of this matrix
     */
    @Nonnull
    M negate();
}
