package civitas.celestis.math.vector;

import jakarta.annotation.Nonnull;

/**
 * A mathematical vector which uses the primitive numerical type {@code double}.
 *
 * @param <V> The vector itself (the result and parameter of various operations)
 */
public interface DoubleVector<V extends DoubleVector<V>> extends Vector<Double, V> {
    //
    // Getters
    //

    /**
     * Converts this vector into an array of {@code double}s.
     *
     * @return The array representation of this vector
     */
    @Nonnull
    double[] array();

    //
    // Properties
    //

    /**
     * Checks if this vector contains not-a-number values.
     *
     * @return {@code true} if at least one of the components are not a number
     * @see Double#isNaN(double)
     */
    boolean isNaN();

    /**
     * Checks if this vector is finite.
     *
     * @return {@code true} if every component of this vector is of a finite value
     * @see Double#isFinite(double)
     */
    boolean isFinite();

    /**
     * Checks if this vector is infinite.
     *
     * @return {@code true} if at least one component of this vector has an infinite magnitude
     * @see Double#isInfinite(double)
     */
    boolean isInfinite();

    /**
     * Returns the Euclidean norm of this vector.
     * Note that this operation involves taking the square root of this vector's components.
     * Using {@link #norm2()} is recommended for simple comparison between magnitudes.
     *
     * @return The Euclidean norm of this vector
     * @see #norm2()
     */
    double norm();

    /**
     * Returns the squared Euclidean norm of this vector.
     * The squared norm is useful as it does not involve a square root operation.
     *
     * @return The squared Euclidean norm of this vector
     */
    double norm2();

    /**
     * Returns the Manhattan norm of this vector.
     * This is achieved by summing the absolute values of every component of this vector.
     *
     * @return The Manhattan norm of this vector
     */
    double normManhattan();

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this vector.
     *
     * @param s The scalar to add to this vector
     * @return The resulting vector
     */
    @Nonnull
    V add(double s);

    /**
     * Subtracts a scalar from this vector.
     *
     * @param s The scalar to subtract from this vector
     * @return The resulting vector
     */
    @Nonnull
    V subtract(double s);

    /**
     * Multiplies this vector by a scalar.
     *
     * @param s The scalar to multiply this vector by
     * @return The resulting vector
     */
    @Nonnull
    V multiply(double s);

    /**
     * Divides this vector by a scalar.
     *
     * @param s The scalar to divide this vector by
     * @return The resulting vector
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    V divide(double s) throws ArithmeticException;

    /**
     * Divides this vector by a scalar, but allow division by zero.
     *
     * @param s The scalar to divide this vector by
     * @return The resulting vector
     */
    @Nonnull
    V divideAllowZero(double s);

    /**
     * Returns the dot product between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the dot product between
     * @return The dot product of the two vectors
     */
    double dot(@Nonnull V v);

    //
    // Distance
    //

    /**
     * Returns the Euclidean distance between this vector and the provided vector {@code v}.
     * Note that this operation involves square roots, and using {@link #distance2(DoubleVector)}
     * is recommended for simple comparisons.
     *
     * @param v The vector of which to get the Euclidean distance to
     * @return The Euclidean distance between the two vectors
     */
    double distance(@Nonnull V v);

    /**
     * Returns the squared Euclidean distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the squared Euclidean distance to
     * @return The squared Euclidean distance between the two vectors
     */
    double distance2(@Nonnull V v);

    /**
     * Returns the Manhattan distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the Manhattan distance to
     * @return The Manhattan distance between the two vectors
     */
    double distanceManhattan(@Nonnull V v);
}
