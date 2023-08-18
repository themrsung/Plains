package civitas.celestis.math.vector;

import jakarta.annotation.Nonnull;

/**
 * A mathematical vector which uses a non-primitive object based number.
 *
 * @param <N> The type of number this vector uses
 * @param <V> The vector itself (the result and parameter of various operations)
 */
public interface BigVector<N extends Number, V extends BigVector<N, V>> extends Vector<N, V> {
    //
    // Getters
    //

    /**
     * Converts this vector into an array of numbers.
     *
     * @return The array representation of this vector
     */
    @Nonnull
    N[] array();

    //
    // Properties
    //

    /**
     * Returns the Euclidean norm of this vector.
     * Note that this operation involves taking the square root of this vector's components.
     * Using {@link #norm2()} is recommended for simple comparison between magnitudes.
     *
     * @return The Euclidean norm of this vector
     * @see #norm2()
     */
    @Nonnull
    N norm();

    /**
     * Returns the squared Euclidean norm of this vector.
     * The squared norm is useful as it does not involve a square root operation.
     *
     * @return The squared Euclidean norm of this vector
     */
    @Nonnull
    N norm2();

    /**
     * Returns the Manhattan norm of this vector.
     * This is achieved by summing the absolute values of every component of this vector.
     *
     * @return The Manhattan norm of this vector
     */
    @Nonnull
    N normManhattan();

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
    V add(@Nonnull N s);

    /**
     * Subtracts a scalar from this vector.
     *
     * @param s The scalar to subtract from this vector
     * @return The resulting vector
     */
    @Nonnull
    V subtract(@Nonnull N s);

    /**
     * Multiplies this vector by a scalar.
     *
     * @param s The scalar to multiply this vector by
     * @return The resulting vector
     */
    @Nonnull
    V multiply(@Nonnull N s);

    /**
     * Divides this vector by a scalar.
     *
     * @param s The scalar to divide this vector by
     * @return The resulting vector
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    V divide(@Nonnull N s) throws ArithmeticException;

    /**
     * Returns the dot product between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the dot product between
     * @return The dot product of the two vectors
     */
    @Nonnull
    N dot(@Nonnull V v);

    //
    // Distance
    //

    /**
     * Returns the Euclidean distance between this vector and the provided vector {@code v}.
     * Note that this operation involves square roots, and using {@link #distance2(BigVector)}
     * is recommended for simple comparisons.
     *
     * @param v The vector of which to get the Euclidean distance to
     * @return The Euclidean distance between the two vectors
     */
    @Nonnull
    N distance(@Nonnull V v);

    /**
     * Returns the squared Euclidean distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the squared Euclidean distance to
     * @return The squared Euclidean distance between the two vectors
     */
    @Nonnull
    N distance2(@Nonnull V v);

    /**
     * Returns the Manhattan distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the Manhattan distance to
     * @return The Manhattan distance between the two vectors
     */
    @Nonnull
    N distanceManhattan(@Nonnull V v);
}
