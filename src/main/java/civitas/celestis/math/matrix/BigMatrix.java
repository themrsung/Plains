package civitas.celestis.math.matrix;

import civitas.celestis.util.group.Grid;
import jakarta.annotation.Nonnull;

/**
 * A grid of {@link Number}s.
 * (most likely a numerical object which represents larger
 * numbers than primitive numbers can represent)
 *
 * @param <N> The type of number to contain in this matrix
 * @param <M> The matrix itself (the parameter and result of arithmetic operations)
 * @see Grid
 */
public interface BigMatrix<N extends Number, M extends BigMatrix<N, M>> extends Grid<N> {
    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this matrix.
     *
     * @param s The scalar to add to this matrix
     * @return The resulting matrix
     */
    @Nonnull
    M add(@Nonnull N s);

    /**
     * Subtracts a scalar from this matrix.
     *
     * @param s The scalar to subtract from this matrix
     * @return The resulting matrix
     */
    @Nonnull
    M subtract(@Nonnull N s);

    /**
     * Multiplies this matrix by a scalar.
     *
     * @param s The scalar to multiply this matrix by
     * @return The resulting matrix
     */
    @Nonnull
    M multiply(@Nonnull N s);

    /**
     * Divides this matrix by a scalar.
     *
     * @param s The scalar to divide this matrix by
     * @return The resulting matrix
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    M divide(@Nonnull N s) throws ArithmeticException;

    /**
     * Adds a matrix to this matrix.
     *
     * @param m The matrix to add to this matrix
     * @return The resulting matrix
     * @throws ArithmeticException When the two matrices have different dimensions
     */
    @Nonnull
    M add(@Nonnull M m) throws ArithmeticException;

    /**
     * Subtracts a matrix from this matrix.
     *
     * @param m The matrix to subtract from this matrix
     * @return The resulting matrix
     * @throws ArithmeticException When the two matrices have different dimensions
     */
    @Nonnull
    M subtract(@Nonnull M m) throws ArithmeticException;

    /**
     * Multiplies this matrix by another matrix.
     *
     * @param m The matrix to multiply this matrix by
     * @return The resulting matrix
     * @throws ArithmeticException When the two matrices' dimensions are incompatible
     */
    @Nonnull
    M multiply(@Nonnull M m) throws ArithmeticException;

    //
    // Transformation
    //

    /**
     * Negates every component of this matrix, then returns a new matrix
     * composed of the negated values.
     *
     * @return The negation of this matrix
     */
    @Nonnull
    M negate();
}
