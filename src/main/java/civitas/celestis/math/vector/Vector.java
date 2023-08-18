package civitas.celestis.math.vector;

import civitas.celestis.util.Transformable;
import civitas.celestis.util.collection.Listable;
import civitas.celestis.util.group.Groupable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;

/**
 * A mathematical vector. Vectors are a one-dimensional array of numbers.
 * <p>
 * Like {@link civitas.celestis.util.group.Tuple Tuple}s, vectors are immutable,
 * but are different in that they can hold primitive types in their raw unboxed form.
 * Vectors can be converted into tuples by calling {@link #group()}.
 * </p>
 *
 * @param <N> The type of number this vector is composed of
 * @param <V> The vector itself (the result and parameter of various operations)
 */
public interface Vector<N extends Number, V extends Vector<N, V>>
        extends Transformable<N>, Groupable<N>, Listable<N>, Serializable {
    //
    // Properties
    //

    /**
     * Returns whether this vector has no magnitude. (every component is zero)
     *
     * @return {@code true} if this vector has no magnitude
     */
    boolean isZero();

    //
    // Validation
    //

    /**
     * Requires that this vector must be zero.
     * If this vector is zero, this will simply return a reference to itself.
     * If not, this will throw an {@link IllegalStateException}.
     *
     * @return {@code this}
     * @throws IllegalStateException When this vector is not zero
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    default V requireZero() throws IllegalStateException {
        if (!isZero()) throw new IllegalStateException("This vector is not zero.");
        return (V) this;
    }

    //
    // Arithmetic
    //

    /**
     * Adds another vector to this vector.
     *
     * @param v The vector to add to this vector
     * @return The resulting vector
     */
    @Nonnull
    V add(@Nonnull V v);

    /**
     * Subtracts another vector from this vector.
     *
     * @param v The vector to subtract from this vector
     * @return The resulting vector
     */
    @Nonnull
    V subtract(@Nonnull V v);

    //
    // Negation
    //

    /**
     * Returns a negated vector which has the same magnitude,
     * but faces the opposing direction.
     *
     * @return The negation of this vector
     */
    @Nonnull
    V negate();

    //
    // Normalization
    //

    /**
     * Normalizes this vector to have a magnitude of {@code 1}.
     * If this vector has a magnitude of zero, this will throw an {@link ArithmeticException}.
     *
     * @return The normalized vector of this vector
     * @throws ArithmeticException When the magnitude of this vector is zero,
     *                             and thus cannot be normalized to have a magnitude of {@code 1}
     */
    @Nonnull
    V normalize() throws ArithmeticException;

    /**
     * Normalizes this vector to have a magnitude of {@code 1}.
     * If this vector has a magnitude of zero, meaning every component of this vector is zero,
     * this will return itself.
     *
     * @return The normalized vector of this vector if normalization is possible,
     * a reference to this vector itself if not
     */
    @Nonnull
    V normalizeOrZero();

    //
    // Clamping
    //

    /**
     * Between this vector and the provided boundary vector,
     * this will return the minimum vector.
     * This is achieved by taking the minimum of each corresponding component,
     * then packaging the components into a new vector.
     *
     * @param v The boundary vector to respect
     * @return The minimum vector between the two vectors
     */
    @Nonnull
    V min(@Nonnull V v);

    /**
     * Between this vector and the provided minimum boundary vector,
     * this will return the maximum vector.
     * This is achieved by taking the maximum of each corresponding component,
     * then packaging the components into a new vector.
     *
     * @param v The boundary vector to respect
     * @return The maximum vector between the two vectors
     */
    @Nonnull
    V max(@Nonnull V v);

    /**
     * Clamps this vector to fit between the range of {@code [min, max]}.
     * This is achieved by clamping each corresponding component,
     * then packaging the clamped components into a new vector.
     *
     * @param min The minimum boundary vector
     * @param max The maximum boundary vector
     * @return The clamped vector which respects the range of {@code [min, max]}
     */
    @Nonnull
    V clamp(@Nonnull V min, @Nonnull V max);

    //
    // Equality
    //

    /**
     * Checks for equality between this vector and the provided object {@code obj}.
     * This will check if the other object is also a vector, and then compare the length and components.
     * <p>
     * If the two vectors have the same length and every component has the same numerical value,
     * this will return {@code true}.
     * </p>
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a vector, and they represent the same numerical value
     */
    @Override
    boolean equals(@Nullable Object obj);

    /**
     * Checks for equality with another vector of the same type.
     *
     * @param v The vector to compare to
     * @return {@code true} if every component's value is equal
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
    @Nonnull
    String toString();
}
