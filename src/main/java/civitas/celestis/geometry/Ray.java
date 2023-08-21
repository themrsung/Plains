package civitas.celestis.geometry;

import civitas.celestis.math.Numbers;
import civitas.celestis.math.Vector3;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.io.Serializable;

/**
 * An immutable three-dimensional ray. Rays are defined by specifying a point of origin,
 * and a direction (which is defined by a unit vector)
 */
public class Ray implements Serializable {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    /**
     * Creates a new ray.
     *
     * @param origin    The origin of this ray
     * @param direction The direction of this ray
     */
    public Ray(@Nonnull Vector3 origin, @Nonnull Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     * Creates a new ray.
     *
     * @param origin                The origin of this ray
     * @param direction             The direction of this ray
     * @param requiresNormalization Whether the direction requires normalization
     * @throws ArithmeticException When the direction is not normalizable (the Euclidean norm is zero)
     */
    public Ray(@Nonnull Vector3 origin, @Nonnull Vector3 direction, boolean requiresNormalization) {
        this(origin, requiresNormalization ? direction.normalize() : direction);
    }

    /**
     * Creates a new ray.
     *
     * @param r The ray of which to copy properties from
     */
    public Ray(@Nonnull Ray r) {
        this.origin = r.origin;
        this.direction = r.direction;
    }

    //
    // Variables
    //

    /**
     * The origin of this ray.
     */
    @Nonnull
    protected final Vector3 origin;

    /**
     * The direction of this ray.
     */
    @Nonnull
    protected final Vector3 direction;

    //
    // Validation
    //

    /**
     * Checks if the directional vector of this ray has been properly normalized. This
     * accounts for potential floating point imprecision.
     *
     * @return {@code true} if the direction of this ray is a normalized unit vector
     * @see Numbers#EPSILON
     */
    public boolean isNormalized() {
        return Math.abs(direction.norm2() - 1) < Numbers.EPSILON;
    }

    /**
     * Requires that this ray's direction has been normalized. If the direction does not
     * have a Euclidean norm (magnitude) of {@code 1}, this will throw an exception.
     *
     * @return A reference to the ray itself ({@code this}) if normalized
     * @throws IllegalStateException When the direction of this ray is not a unit vector
     * @see #isNormalized()
     */
    @Nonnull
    public Ray requireNormalized() {
        if (!isNormalized()) {
            throw new IllegalStateException("The direction of this ray has not been normalized.");
        }

        return this;
    }

    //
    // Getters
    //

    /**
     * Returns the origin of this ray.
     *
     * @return The origin of this ray
     */
    @Nonnull
    public Vector3 origin() {
        return origin;
    }

    /**
     * Returns the direction of this ray.
     *
     * @return The direction of this ray
     */
    @Nonnull
    public Vector3 direction() {
        return direction;
    }

    /**
     * Given a distance parameter {@code t}, this calculates and returns the destination of
     * this ray if it were to travel {@code t} units forward.
     *
     * @param t The distance parameter
     * @return The destination of this ray
     */
    @Nonnull
    public Vector3 destination(double t) {
        final double dx = direction.x() * t;
        final double dy = direction.y() * t;
        final double dz = direction.z() * t;

        return new Vector3(origin.x() + dx, origin.y() + dy, origin.z() + dz);
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this ray and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a ray, and the origin and direction are equal
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Ray r)) return false;
        return origin.equals(r.origin) && direction.equals(r.direction);
    }

    //
    // Serialization
    //

    /**
     * Serializes this ray into a string.
     *
     * @return The string representation of this ray
     */
    @Nonnull
    @Override
    public String toString() {
        return "Ray{" +
                "origin=" + origin +
                ", direction=" + direction +
                '}';
    }
}
