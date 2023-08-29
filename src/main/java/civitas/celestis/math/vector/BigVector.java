package civitas.celestis.math.vector;

import civitas.celestis.util.tuple.BaseTuple;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * A mathematical vector which uses an arbitrary numeric type {@code N}.
 *
 * @param <N>  The type of number to use
 * @param <D>  The type of decimal number to use
 * @param <V>  The vector itself (the parameter and result of various operations)
 * @param <DV> The type of decimal vector to use (the result of normalization)
 * @param <PV> The primitive vector of which this vector can be converted to
 */
public interface BigVector<N, D, V extends BigVector<N, D, V, DV, PV>,
        DV extends BigVector<D, D, DV, DV, ? extends BaseTuple<?>>, PV extends BaseTuple<? extends Number>>
        extends Tuple<N> {
    //
    // Properties
    //

    /**
     * Returns the Euclidean norm (the magnitude) of this vector.
     *
     * @return The Euclidean norm of this vector
     */
    @Nonnull
    D norm();

    /**
     * Returns the squared Euclidean norm (the squared magnitude) of this vector.
     *
     * @return The squared Euclidean norm of this vector
     */
    @Nonnull
    N norm2();

    /**
     * Returns the Manhattan norm of this vector.
     *
     * @return The Manhattan norm of this vector
     */
    @Nonnull
    N normManhattan();

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this vector component-wise, then returns the resulting vector.
     *
     * @param s The scalar of which to add to each component of this vector
     * @return The resulting vector
     */
    @Nonnull
    V add(@Nonnull N s);

    /**
     * Subtracts this vector by a scalar component-wise, then returns the resulting vector.
     *
     * @param s The scalar of which to subtract from each component of this vector
     * @return The resulting vector
     */
    @Nonnull
    V subtract(@Nonnull N s);

    /**
     * Multiplies this vector by a scalar component-wise, then returns the resulting vector.
     *
     * @param s The scalar of which to multiply each component of this vector by
     * @return The resulting vector
     */
    @Nonnull
    V multiply(@Nonnull N s);

    /**
     * Divides this vector by a scalar component-wise, then returns the resulting vector.
     *
     * @param s The scalar of which to divide each component of this vector by
     * @return The resulting vector
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    V divide(@Nonnull N s) throws ArithmeticException;

    /**
     * Adds another vector to this vector, then returns the resulting vector.
     *
     * @param v The vector of which to add to this vector
     * @return The resulting vector
     */
    @Nonnull
    V add(@Nonnull V v);

    /**
     * Subtracts another vector from this vector, then returns the resulting vector.
     *
     * @param v The vector of which to subtract from this vector
     * @return The resulting vector
     */
    @Nonnull
    V subtract(@Nonnull V v);

    /**
     * Returns the dot product between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the dot product between
     * @return The dot product between the two vectors
     */
    @Nonnull
    N dot(@Nonnull V v);

    //
    // Normalization
    //

    /**
     * Normalizes this vector to have a Euclidean norm (magnitude) of {@code 1}.
     *
     * @return The normalized vector of this vector
     * @throws ArithmeticException When the Euclidean norm of this vector is zero
     */
    @Nonnull
    DV normalize() throws ArithmeticException;

    /**
     * Normalizes this vector ot have a Euclidean norm (magnitude) of {@code 1}.
     * If this vector's Euclidean norm is zero, this will return itself. (which is guaranteed to be a
     * vector where all components are zero, as only zero vectors have a Euclidean norm of zero)
     *
     * @return The normalized vector of this vector if successful, zero otherwise
     */
    @Nonnull
    DV normalizeOrZero();

    /**
     * Normalizes this vector to have a Euclidean norm (magnitude) of {@code 1}.
     * If this vector's Euclidean norm is zero, this will return the provided fallback value {@code v}.
     *
     * @param v The value of which to return in case of division by zero
     * @return The normalized vector of this vector if successful, the fallback value {@code v} otherwise
     */
    @Nonnull
    DV normalizeOrDefault(@Nonnull DV v);

    //
    // Negation
    //

    /**
     * Negates all components of this vector, then returns the resulting vector.
     *
     * @return The negation of this vector
     */
    @Nonnull
    V negate();

    //
    // Clamping
    //

    /**
     * Between this vector and the provided boundary vector {@code v}, this takes the minimum of
     * each corresponding pair of components, then returns a new vector whose components are populated
     * from that of the minimum values between each pair of components.
     *
     * @param v The boundary vector of which to compare to
     * @return The minimum vector of the two vectors
     */
    @Nonnull
    V min(@Nonnull V v);

    /**
     * Between this vector and the provided boundary vector {@code v}, this takes the maximum of
     * each corresponding pair of components, then returns a new vector whose components are populated
     * from that of the maximum values between each pair of components.
     *
     * @param v The boundary vector of which to compare to
     * @return The maximum vector of the two vectors
     */
    @Nonnull
    V max(@Nonnull V v);

    /**
     * Between this vector and the provided boundary vectors {@code min} and {@code max}, this returns
     * a new vector whose components are clamped to respect the range of {@code [min, max]}. This is the
     * equivalent of clamping each component to the corresponding pair of minimum and maximum values.
     *
     * @param min The minimum boundary vector to compare to
     * @param max The maximum boundary vector to compare to
     * @return The clamped vector which respects the boundaries of {@code [min, max]}
     */
    @Nonnull
    V clamp(@Nonnull V min, @Nonnull V max);

    //
    // Distance
    //

    /**
     * Returns the Euclidean distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which get the Euclidean distance between
     * @return The Euclidean distance between the two vectors
     */
    @Nonnull
    D distance(@Nonnull V v);

    /**
     * Returns the squared Euclidean distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the squared Euclidean distance between
     * @return The squared Euclidean distance between the two vectors
     */
    @Nonnull
    N distance2(@Nonnull V v);

    /**
     * Returns the Manhattan distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the Manhattan distance between
     * @return The Manhattan distance between the two vectors
     */
    @Nonnull
    N distanceManhattan(@Nonnull V v);

    //
    // Transformation
    //

    /**
     * Applies the provided transformation function {@code f} to each component of this vector,
     * then returns a new vector containing the return values of the function f.
     *
     * @param f The function of which to apply to each component of this vector
     * @return The resulting vector
     */
    @Nonnull
    V transform(@Nonnull UnaryOperator<N> f);

    /**
     * Between this vector and the provided vector {@code v}, this applies the merger function
     * {@code f} to each corresponding pair of components, then returns a new vector whose components
     * are populated from the return values of the function {@code f}.
     *
     * @param v The vector of which to merge this vector with
     * @param f The merger function to handle the merging of the two vectors
     * @return The resulting vector
     */
    @Nonnull
    V merge(@Nonnull V v, @Nonnull BinaryOperator<N> f);

    //
    // Conversion
    //

    /**
     * Converts this vector into a primitive vector. Precision and/or scale may be lost.
     *
     * @return The primitive representation of this vector
     */
    @Nonnull
    PV primValue();

    //
    // Equality
    //

    /**
     * Checks for equality between this vector and the provided vector {@code v}.
     *
     * @param v The vector to compare to
     * @return {@code true} if the component values are equal
     */
    boolean equals(@Nullable V v);
}
