package civitas.celestis.util;

import civitas.celestis.math.Vector;
import jakarta.annotation.Nonnull;

/**
 * An integer-typed tuple whose values are stored using primitive {@code int}s.
 * Integer tuples have similar characteristics to {@link Vector}s in that they
 * natively support various arithmetic operations.
 *
 * @param <T> The tuple itself (used as the parameter and result of various operations)
 * @see Tuple
 * @see Int2
 * @see Int3
 * @see Int4
 */
public interface IntTuple<T extends IntTuple<T>> extends Tuple<Integer> {
    //
    // Properties
    //

    /**
     * Returns whether this tuple is zero. (every component of this tuple is zero)
     *
     * @return {@code true} if every component of this tuple is zero
     */
    boolean isZero();

    /**
     * Returns the Euclidean norm (the magnitude) of this tuple.
     * Note that this operation involves calculating square roots, and
     * {@link #norm2()} is recommended for simple comparison operations.
     *
     * @return The Euclidean norm (the magnitude) of this tuple
     * @see #norm2()
     */
    double norm();

    /**
     * Returns the squared Euclidean norm (the squared magnitude) of this tuple.
     * This operation does not involve calculating square roots, and thus is
     * a faster way of comparing magnitudes between tuples efficiently.
     *
     * @return The squared Euclidean norm (the squared magnitude) of this tuple
     * @see #norm()
     */
    int norm2();

    /**
     * Returns the Manhattan norm of this tuple. This is a sum of every components'
     * absolute value within this tuple.
     *
     * @return The Manhattan norm of this tuple
     */
    int normManhattan();

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this tuple.
     *
     * @param s The scalar to add to this tuple
     * @return The resulting tuple
     */
    @Nonnull
    T add(int s);

    /**
     * Subtracts a scalar from this tuple, then returns the resulting tuple/
     *
     * @param s The scalar to subtract from this tuple
     * @return The resulting tuple
     */
    @Nonnull
    T subtract(int s);

    /**
     * Multiplies this tuple by a scalar, then returns the resulting tuple.
     *
     * @param s The scalar to multiply this tuple by
     * @return The resulting tuple
     */
    @Nonnull
    T multiply(int s);

    /**
     * Divides this tuple by a scalar, then returns the resulting tuple.
     *
     * @param s The scalar to divide this tuple by
     * @return The resulting tuple
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    T divide(int s) throws ArithmeticException;

    /**
     * Adds another tuple to this tuple, then returns the resulting tuple.
     *
     * @param t The tuple of which to add to this tuple
     * @return The resulting tuple
     */
    @Nonnull
    T add(@Nonnull T t);

    /**
     * Subtracts another tuple from this tuple, then returns the resulting tuple.
     *
     * @param t The tuple of which to subtract from this tuple
     * @return The resulting tuple
     */
    @Nonnull
    T subtract(@Nonnull T t);

    /**
     * Returns the dot product between this tuple and the provided tuple {@code t}.
     *
     * @param t The tuple of which to get the dot product between
     * @return The dot product between the two tuples
     */
    int dot(@Nonnull T t);

    //
    // Distance
    //

    /**
     * Returns the Euclidean distance between this tuple and the provided tuple {@code t}.
     * Note that using the squared distance is recommended for simple comparison operations.
     *
     * @param t The tuple of which to get the Euclidean distance to
     * @return The Euclidean distance between the two tuples
     * @see #distance2(IntTuple)
     * @see #norm()
     */
    double distance(@Nonnull T t);

    /**
     * Returns the squared Euclidean distance between this tuple and the provided tuple {@code t}.
     *
     * @param t The tuple of which to get the squared Euclidean distance to
     * @return The squared Euclidean distance between the two tuples
     * @see #norm2()
     */
    int distance2(@Nonnull T t);

    /**
     * Returns the Manhattan distance between this tuple and the provided tuple {@code t}.
     *
     * @param t The tuple of which to get the Manhattan distance to
     * @return The Manhattan distance between the two tuples
     * @see #normManhattan()
     */
    int distanceManhattan(@Nonnull T t);

    //
    // Clamping
    //

    /**
     * Between this tuple and the provided boundary tuple {@code t}, this returns a tuple whose
     * components are the minimum value between the two tuples. Each corresponding component is compared,
     * and the smaller value is chosen for the new tuple.
     *
     * @param t The boundary tuple to compare to
     * @return The minimum tuple between the two tuples
     */
    @Nonnull
    T min(@Nonnull T t);

    /**
     * Between this tuple and the provided boundary tuple {@code t}, this returns a tuple whose
     * components are the maximum value between the two tuples. Each corresponding component is compared,
     * and the larger value is chosen for the new tuple.
     *
     * @param t The boundary tuple to compare to
     * @return The maximum tuple between the two tuples
     */
    @Nonnull
    T max(@Nonnull T t);

    /**
     * Between this tuple and the provided boundary tuples {@code min} and {@code max},
     * this returns a new tuple whose components are the clamped value between the two tuples.
     *
     * @param min The minimum boundary tuple to compare to
     * @param max The maximum boundary tuple to compare to
     * @return The clamped tuple whose components respect the provided boundaries
     */
    @Nonnull
    T clamp(@Nonnull T min, @Nonnull T max);

    //
    // Negation
    //

    /**
     * Negates each component of this tuple, then returns a new tuple composed
     * of the negated components.
     *
     * @return The negation of this tuple
     */
    @Nonnull
    T negate();

    //
    // Conversion
    //

    /**
     * Converts this tuple into a vector, then returns the converted vector.
     *
     * @return The vector representation of this tuple
     */
    @Nonnull
    Vector<?> vector();
}
