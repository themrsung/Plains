package civitas.celestis.math.vector;

import jakarta.annotation.Nonnull;

/**
 * A mathematical vector which uses the primitive numerical type {@code float}.
 *
 * @param <V> The vector itself (the result and parameter of various operations)
 */
public interface FloatVector<V extends FloatVector<V>> extends Vector<Float, V> {
    //
    // Getters
    //

    /**
     * Converts this vector into an array of {@code float}s.
     *
     * @return The array representation of this vector
     */
    @Nonnull
    float[] array();

    //
    // Properties
    //

    /**
     * Checks if this vector contains not-a-number values.
     *
     * @return {@code true} if at least one of the components are not a number
     * @see Float#isNaN(float)
     */
    boolean isNaN();

    /**
     * Checks if this vector is finite.
     *
     * @return {@code true} if every component of this vector is of a finite value
     * @see Float#isFinite(float)
     */
    boolean isFinite();

    /**
     * Checks if this vector is infinite.
     *
     * @return {@code true} if at least one component of this vector has an infinite magnitude
     * @see Float#isInfinite(float)
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
    float norm();

    /**
     * Returns the squared Euclidean norm of this vector.
     * The squared norm is useful as it does not involve a square root operation.
     *
     * @return The squared Euclidean norm of this vector
     */
    float norm2();

    /**
     * Returns the Manhattan norm of this vector.
     * This is achieved by summing the absolute values of every component of this vector.
     *
     * @return The Manhattan norm of this vector
     */
    float normManhattan();

    //
    // Validation
    //

    /**
     * Requires that this vector is not a number.
     * If this vector is not a number, this will simply return a reference to itself.
     * If not, this will throw an {@link IllegalStateException}.
     *
     * @return {@code this}
     * @throws IllegalStateException When this vector is not NaN (not-a-number)
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    default V requireNaN() throws IllegalStateException {
        if (!isNaN()) throw new IllegalStateException("This vector is not NaN (not-a-number).");
        return (V) this;
    }

    /**
     * Requires that this vector is finite.
     * If this vector is finite, this will simply return a reference to itself.
     * If not, this will throw an {@link IllegalStateException}.
     *
     * @return {@code this}
     * @throws IllegalStateException When this vector is non-finite (NaN or infinity)
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    default V requireFinite() throws IllegalStateException {
        if (!isFinite()) throw new IllegalStateException("This vector is non-finite. (NaN or infinity)");
        return (V) this;
    }

    /**
     * Requires that this vector is infinite.
     * If this vector is infinite, this will simply return a reference to itself.
     * If not, this will throw an {@link IllegalStateException}.
     *
     * @return {@code this}
     * @throws IllegalStateException When this vector is not infinite
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    default V requireInfinite() throws IllegalStateException {
        if (!isInfinite()) throw new IllegalStateException("This vector is not infinite.");
        return (V) this;
    }

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
    V add(float s);

    /**
     * Subtracts a scalar from this vector.
     *
     * @param s The scalar to subtract from this vector
     * @return The resulting vector
     */
    @Nonnull
    V subtract(float s);

    /**
     * Multiplies this vector by a scalar.
     *
     * @param s The scalar to multiply this vector by
     * @return The resulting vector
     */
    @Nonnull
    V multiply(float s);

    /**
     * Divides this vector by a scalar.
     *
     * @param s The scalar to divide this vector by
     * @return The resulting vector
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    V divide(float s) throws ArithmeticException;

    /**
     * Divides this vector by a scalar, but allow division by zero.
     *
     * @param s The scalar to divide this vector by
     * @return The resulting vector
     */
    @Nonnull
    V divideAllowZero(float s);

    /**
     * Returns the dot product between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the dot product between
     * @return The dot product of the two vectors
     */
    float dot(@Nonnull V v);

    //
    // Distance
    //

    /**
     * Returns the Euclidean distance between this vector and the provided vector {@code v}.
     * Note that this operation involves square roots, and using {@link #distance2(FloatVector)}
     * is recommended for simple comparisons.
     *
     * @param v The vector of which to get the Euclidean distance to
     * @return The Euclidean distance between the two vectors
     */
    float distance(@Nonnull V v);

    /**
     * Returns the squared Euclidean distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the squared Euclidean distance to
     * @return The squared Euclidean distance between the two vectors
     */
    float distance2(@Nonnull V v);

    /**
     * Returns the Manhattan distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the Manhattan distance to
     * @return The Manhattan distance between the two vectors
     */
    float distanceManhattan(@Nonnull V v);
}
