package civitas.celestis.math;

import civitas.celestis.util.SafeArray;
import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * A mathematical vector represented by an arbitrary {@code n} number of
 * {@code double} components. This interface defines the contract of a vector.
 *
 * @param <V> The vector itself (used as the parameter and return type of various operations)
 */
public interface Vector<V extends Vector<V>> extends Serializable {
    //
    // Properties
    //

    /**
     * Returns the number of components this vector has. In other words,
     * this returns the number {@code n} for an {@code n}-dimensional vector.
     * @return The number of scalar components this vector has
     */
    int dimensions();

    /**
     * Returns whether this vector has no direction. (every component is zero)
     *
     * @return {@code true} if every component of this vector is zero
     */
    boolean isZero();

    /**
     * Returns whether this vector is not a number. (has at least one component
     * whose value is not-a-number).
     *
     * @return {@code true} if this vector has at least one not-a-number component
     * @see Double#isNaN(double)
     */
    boolean isNaN();

    /**
     * Returns whether this vector is finite. (has no non-finite components)
     *
     * @return {@code true} if every component is verified to be finite
     * @see Double#isFinite(double)
     */
    boolean isFinite();

    /**
     * Returns whether this vector is infinite. (has at least one infinite component)
     *
     * @return {@code true} if this vector has at least one infinite component
     * @see Double#isInfinite(double)
     */
    boolean isInfinite();

    /**
     * Returns the Euclidean norm (the magnitude) of this vector.
     * Note that this operation involves calculating square roots, and
     * {@link #norm2()} is recommended for simple comparison operations.
     *
     * @return The Euclidean norm (the magnitude) of this vector
     * @see #norm2()
     */
    double norm();

    /**
     * Returns the squared Euclidean norm (the squared magnitude) of this vector.
     * This operation does not involve calculating square roots, and thus
     * is a faster way of comparing magnitudes between vectors efficiently.
     *
     * @return The squared Euclidean norm (the squared magnitude) of this vector
     * @see #norm()
     */
    double norm2();

    /**
     * Returns the Manhattan norm of this vector. This is a sum of every components'
     * absolute value within this vector.
     *
     * @return The Manhattan norm of this vector
     */
    double normManhattan();

    //
    // Getters
    //

    /**
     * Returns the {@code i}th component of this vector.
     *
     * @param i The index of the component to get
     * @return The component at the specified index
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    double get(int i) throws IndexOutOfBoundsException;

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this vector, then returns the resulting vector.
     *
     * @param s The scalar to add to this vector
     * @return The resulting vector
     */
    @Nonnull
    V add(double s);

    /**
     * Subtracts this vector by a scalar, then returns the resulting vector.
     *
     * @param s The scalar to subtract this vector by
     * @return The resulting vector
     */
    @Nonnull
    V subtract(double s);

    /**
     * Multiplies this vector by a scalar, then returns the resulting vector.
     *
     * @param s The scalar to multiply this vector by
     * @return The resulting vector
     */
    @Nonnull
    V multiply(double s);

    /**
     * Divides this vector by a scalar, then returns the resulting vector.
     *
     * @param s The scalar to divide this vector by
     * @return The resulting vector
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    V divide(double s) throws ArithmeticException;

    /**
     * Adds another vector to this vector, then returns the resulting vector.
     *
     * @param v The vector of which to add to this vector
     * @return The resulting vector
     */
    @Nonnull
    V add(@Nonnull V v);

    /**
     * Subtracts this vector by another vector, then returns the resulting vector.
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
    double dot(@Nonnull V v);

    //
    // Distance
    //

    /**
     * Returns the Euclidean distance between this vector and the provided vector {@code v}.
     * Note that using the squared distance is recommended for simple comparison operations.
     *
     * @param v The vector of which to get the Euclidean distance to
     * @return The Euclidean distance between the two vectors
     * @see #distance2(Vector)
     * @see #norm()
     */
    double distance(@Nonnull V v);

    /**
     * Returns the squared Euclidean distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the squared Euclidean distance to
     * @return The squared Euclidean distance between the two vectors
     * @see #norm2()
     */
    double distance2(@Nonnull V v);

    /**
     * Returns the Manhattan distance between this vector and the provided vector {@code v}.
     *
     * @param v The vector of which to get the Manhattan distance to
     * @return The Manhattan distance between the two vectors
     * @see #normManhattan()
     */
    double distanceManhattan(@Nonnull V v);

    //
    // Clamping
    //

    /**
     * Between this vector and the provided boundary vector {@code v}, this returns a vector whose
     * components are the minimum value between the two vectors. Each corresponding component is compared,
     * and the smaller value is chosen for the new vector.
     *
     * @param v The boundary vector to compare to
     * @return The minimum vector between the two vectors
     */
    @Nonnull
    V min(@Nonnull V v);

    /**
     * Between this vector and the provided boundary vector {@code v}, this returns a vector whose
     * components are the maximum value between the two vectors. Each corresponding component is compared,
     * and the larger value is chosen for the new vector.
     *
     * @param v The boundary vector to compare to
     * @return The maximum vector between the two vectors
     */
    @Nonnull
    V max(@Nonnull V v);

    /**
     * Between this vector and the provided boundary vectors {@code min} and {@code max},
     * this returns a new vector whose components are the clamped value between the two vectors.
     *
     * @param min The minimum boundary vector to compare to
     * @param max The maximum boundary vector to compare to
     * @return The clamped vector whose components respect the provided boundaries
     */
    @Nonnull
    V clamp(@Nonnull V min, @Nonnull V max);

    //
    // Negation
    //

    /**
     * Negates each component of this vector, then returns a new vector composed of the
     * negated components.
     *
     * @return The negation of this vector
     */
    @Nonnull
    V negate();

    //
    // Normalization
    //

    /**
     * Normalizes this vector to have a Euclidean norm (magnitude) of {@code 1}.
     * If this vector has no direction (the Euclidean norm is zero), this will throw
     * an {@link ArithmeticException}, as normalization is not possible.
     *
     * @return The normalized vector of this vector
     * @throws ArithmeticException When the Euclidean norm of this vector is zero
     * @see #normalizeOrZero()
     * @see #normalizeOrDefault(Vector)
     */
    @Nonnull
    V normalize() throws ArithmeticException;

    /**
     * Normalizes this vector to have a Euclidean norm (magnitude) of {@code 1}.
     * If this vector has no direction (the Euclidean norm is zero), this will return
     * itself. (which is zero, since only zero vectors can have a magnitude of zero)
     *
     * @return The normalized vector of this vector if successful, a vector representing
     * origin (zero) otherwise
     * @see #normalize()
     * @see #normalizeOrDefault(Vector)
     */
    @Nonnull
    V normalizeOrZero();

    /**
     * Normalizes this vector to have a Euclidean norm (magnitude) of {@code 1}.
     * If this vector has no direction (the Euclidean norm is zero), this will return the
     * provided fallback value {@code v} instead of throwing an exception.
     *
     * @param v The fallback value to default to when normalization is impossible
     * @return The normalized vector of this vector if successful, the fallback value otherwise
     * @see #normalize()
     * @see #normalizeOrZero()
     */
    @Nonnull
    V normalizeOrDefault(@Nonnull V v);

    //
    // Transformation
    //

    /**
     * Applies the provided function {@code f} to each component of this vector,
     * then returns a new vector containing the resulting elements.
     *
     * @param f The function of which to apply to each element of this vector
     * @return The resulting vector
     */
    @Nonnull
    V transform(@Nonnull UnaryOperator<Double> f);

    /**
     * Applies the provided mapper function {@code f} to each element of this vector,
     * then returns a new tuple containing the resulting elements. This operation
     * does not preserve the type bounds of {@link Double}, and thus requires conversion
     * into tuple form in order to work properly.
     *
     * @param f   The function of which to apply to each element of this vector
     * @param <T> The type of element to map this vector to (does not require that it
     *            is a numeric type)
     * @return A tuple containing the resulting values in the proper order
     */
    @Nonnull
    <T> Tuple<T> map(@Nonnull Function<Double, T> f);

    /**
     * Between this vector and the provided vector {@code v}, this applies the merger function
     * {@code f} to each corresponding pair of components, then returns a new vector containing
     * the resulting components.
     *
     * @param v The vector of which to merge this vector with
     * @param f The merger function to handle the merging of the two vectors
     * @return The resulting vector
     */
    @Nonnull
    V merge(@Nonnull V v, @Nonnull BinaryOperator<Double> f);

    //
    // Conversion
    //

    /**
     * Returns an array representing the components of this vector.
     *
     * @return The array representation of this vector
     * @see SafeArray
     */
    @Nonnull
    SafeArray<Double> array();

    /**
     * Converts this vector into a tuple, then returns the converted tuple.
     *
     * @return The tuple representation of this vector
     * @see Tuple
     */
    @Nonnull
    Tuple<Double> tuple();

    /**
     * Converts this vector into an unmodifiable list, then returns the converted list.
     *
     * @return An unmodifiable list representing the elements of this vector
     * @see List
     */
    @Nonnull
    List<Double> list();

    //
    // Equality
    //

    /**
     * Checks for equality between this vector and the provided object {@code obj}.
     * This will check if the other object is also a vector, and that the dimension count,
     * composition, and order of components are all equal. In other words, this checks
     * if the other object is also a vector of the same numerical value.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a vector, and they have the same number
     * of dimensions, the same component values in the same order
     */
    @Override
    boolean equals(@Nullable Object obj);

    /**
     * Checks for equality between this vector and another vector of the same type.
     *
     * @param v The vector to compare to
     * @return {@code true} if the provided vector {@code v} is not {@code null},
     * and the components are equal that of this vector's components
     */
    boolean equals(@Nullable V v);

    //
    // Serialization
    //

    /**
     * Serializes this vector into a string.
     *
     * @return The string representation of this vector
     */
    @Override
    @Nonnull
    String toString();
}
