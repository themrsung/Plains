package civitas.celestis.math.vector;

import civitas.celestis.exception.IllegalInstanceException;
import civitas.celestis.math.Scalars;
import civitas.celestis.math.complex.Quaternion;
import jakarta.annotation.Nonnull;

/**
 * A static utility class which contains methods related to {@link Vector vectors}.
 *
 * @see Vector
 * @see Vector2
 * @see Vector3
 * @see Vector4
 * @see ArrayVector
 */
public final class Vectors {
    //
    // Randomization
    //

    /**
     * Returns a random unit vector. (a vector with a Euclidean norm of {@code 1})
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector2 random2() {
        return new Vector2(Scalars.random(-1, 1), Scalars.random(-1, 1)).normalizeOrDefault(Vector2.POSITIVE_X);
    }

    /**
     * Returns a random unit vector. (a vector with a Euclidean norm of {@code 1})
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector3 random3() {
        return new Vector3(Scalars.random(-1, 1), Scalars.random(-1, 1), Scalars.random(-1, 1))
                .normalizeOrDefault(Vector3.POSITIVE_X);
    }

    /**
     * Returns a random unit vector. (a vector with a Euclidean norm of {@code 1})
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector4 random4() {
        return new Vector4(
                Scalars.random(-1, 1),
                Scalars.random(-1, 1),
                Scalars.random(-1, 1),
                Scalars.random(-1, 1)
        ).normalizeOrDefault(Vector4.POSITIVE_X);
    }

    //
    // Range Validation
    //

    /**
     * Checks if the provided value {@code val} is within the range of {@code [min, max]}.
     *
     * @param val The value to compare
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @param <V> The type of vector to use for this operation
     * @return {@code true} if the value is within the range of {@code [min, max]}
     */
    public static <V extends Vector<V>> boolean isInRange(@Nonnull V val, @Nonnull V min, @Nonnull V max) {
        if (val.size() != min.size() || min.size() != max.size()) return false;

        for (int i = 0; i < val.size(); i++) {
            if (!Scalars.isInRange(val.get(i), min.get(i), max.get(i))) return false;
        }

        return true;
    }

    /**
     * Checks if the provided value {@code val} is within the range of {@code [min, max]}.
     *
     * @param val The value to compare
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @return {@code true} if the value is within the range of {@code [min, max]}
     */
    public static boolean isInRange(@Nonnull Vector2 val, @Nonnull Vector2 min, @Nonnull Vector2 max) {
        return Scalars.isInRange(val.x(), min.x(), max.x()) && Scalars.isInRange(val.y(), min.y(), max.y());
    }

    /**
     * Checks if the provided value {@code val} is within the range of {@code [min, max]}.
     *
     * @param val The value to compare
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @return {@code true} if the value is within the range of {@code [min, max]}
     */
    public static boolean isInRange(@Nonnull Vector3 val, @Nonnull Vector3 min, @Nonnull Vector3 max) {
        return Scalars.isInRange(val.x(), min.x(), max.x()) &&
                Scalars.isInRange(val.y(), min.y(), max.y()) &&
                Scalars.isInRange(val.z(), min.z(), max.z());
    }

    /**
     * Checks if the provided value {@code val} is within the range of {@code [min, max]}.
     *
     * @param val The value to compare
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @return {@code true} if the value is within the range of {@code [min, max]}
     */
    public static boolean isInRange(@Nonnull Vector4 val, @Nonnull Vector4 min, @Nonnull Vector4 max) {
        return Scalars.isInRange(val.w(), min.w(), max.w()) &&
                Scalars.isInRange(val.x(), min.x(), max.x()) &&
                Scalars.isInRange(val.y(), min.y(), max.y()) &&
                Scalars.isInRange(val.z(), min.z(), max.z());
    }

    //
    // Vector Arithmetic
    //

    /**
     * Returns the sum of the two values.
     *
     * @param v1 The first value to add
     * @param v2 The second value to add
     * @return The sum of the provided values
     */
    @Nonnull
    public static Vector2 sum(@Nonnull Vector2 v1, @Nonnull Vector2 v2) {
        return v1.add(v2);
    }

    /**
     * Returns the sum of the provided values.
     *
     * @param values The values to sum
     * @return The sum of the provided values
     */
    @Nonnull
    public static Vector2 sum(@Nonnull Vector2... values) {
        double x = 0, y = 0;
        for (final Vector2 value : values) {
            x += value.x();
            y += value.y();
        }
        return new Vector2(x, y);
    }

    /**
     * Returns the sum of the two values.
     *
     * @param v1 The first value to add
     * @param v2 The second value to add
     * @return The sum of the provided values
     */
    @Nonnull
    public static Vector3 sum(@Nonnull Vector3 v1, @Nonnull Vector3 v2) {
        return v1.add(v2);
    }

    /**
     * Returns the sum of the provided values.
     *
     * @param values The values to sum
     * @return The sum of the provided values
     */
    @Nonnull
    public static Vector3 sum(@Nonnull Vector3... values) {
        double x = 0, y = 0, z = 0;
        for (final Vector3 value : values) {
            x += value.x();
            y += value.y();
            z += value.z();
        }
        return new Vector3(x, y, z);
    }

    /**
     * Returns the sum of the two values.
     *
     * @param v1 The first value to add
     * @param v2 The second value to add
     * @return The sum of the provided values
     */
    @Nonnull
    public static Vector4 sum(@Nonnull Vector4 v1, @Nonnull Vector4 v2) {
        return v1.add(v2);
    }

    /**
     * Returns the sum of the provided values.
     *
     * @param values The values to sum
     * @return The sum of the provided values
     */
    @Nonnull
    public static Vector4 sum(@Nonnull Vector4... values) {
        double w = 0, x = 0, y = 0, z = 0;
        for (final Vector4 value : values) {
            w += value.w();
            x += value.x();
            y += value.y();
            z += value.z();
        }
        return new Vector4(w, x, y, z);
    }

    /**
     * Returns the simple average of the two vectors.
     * This is equivalent to the centroid in a geometric context.
     *
     * @param v1 The first vector to average
     * @param v2 The second vector to average
     * @return The simple average of the two vectors
     */
    @Nonnull
    public static Vector2 avg(@Nonnull Vector2 v1, @Nonnull Vector2 v2) {
        return v1.add(v2).divide(2);
    }

    /**
     * Returns the simple average of the provided vectors.
     * This is equivalent to the centroid in a geometric context.
     *
     * @param values The vectors to average
     * @return The simple average of the provided vectors
     */
    @Nonnull
    public static Vector2 avg(@Nonnull Vector2... values) {
        return sum(values).divide(values.length);
    }

    /**
     * Returns the simple average of the two vectors.
     * This is equivalent to the centroid in a geometric context.
     *
     * @param v1 The first vector to average
     * @param v2 The second vector to average
     * @return The simple average of the two vectors
     */
    @Nonnull
    public static Vector3 avg(@Nonnull Vector3 v1, @Nonnull Vector3 v2) {
        return v1.add(v2).divide(2);
    }

    /**
     * Returns the simple average of the provided vectors.
     * This is equivalent to the centroid in a geometric context.
     *
     * @param values The vectors to average
     * @return The simple average of the provided vectors
     */
    @Nonnull
    public static Vector3 avg(@Nonnull Vector3... values) {
        return sum(values).divide(values.length);
    }

    /**
     * Returns the simple average of the two vectors.
     * This is equivalent to the centroid in a geometric context.
     *
     * @param v1 The first vector to average
     * @param v2 The second vector to average
     * @return The simple average of the two vectors
     */
    @Nonnull
    public static Vector4 avg(@Nonnull Vector4 v1, @Nonnull Vector4 v2) {
        return v1.add(v2).divide(2);
    }

    /**
     * Returns the simple average of the provided vectors.
     * This is equivalent to the centroid in a geometric context.
     *
     * @param values The vectors to average
     * @return The simple average of the provided vectors
     */
    @Nonnull
    public static Vector4 avg(@Nonnull Vector4... values) {
        return sum(values).divide(values.length);
    }

    //
    // Linear Interpolation (LERP)
    //

    /**
     * Performs linear interpolation (LERP) between the starting value {@code s} and the ending value {@code e}.
     *
     * @param s   The starting value
     * @param e   The ending value
     * @param <V> The type of vector to use for this operation
     * @param t   The interpolation parameter ({@code [0, 1]})
     * @return The interpolated value between the starting and ending values {@code s} and {@code e}
     * @throws IllegalArgumentException When the starting vector and the ending vector have a different
     *                                  dimension count (arithmetic is impossible)
     */
    @Nonnull
    public static <V extends Vector<V>> V lerp(@Nonnull V s, @Nonnull V e, double t) {
        return s.add(s.subtract(e).multiply(t));
    }

    /**
     * Performs linear interpolation (LERP) between the starting value {@code s} and the ending value {@code e}.
     *
     * @param s The starting value
     * @param e The ending value
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The interpolated value between the starting and ending values {@code s} and {@code e}
     */
    @Nonnull
    public static Vector2 lerp(@Nonnull Vector2 s, @Nonnull Vector2 e, double t) {
        final double s1 = s.x();
        final double e1 = e.x();
        final double s2 = s.y();
        final double e2 = e.y();

        return new Vector2(
                s1 + (e1 - s1) * t,
                s2 + (e2 - s2) * t
        );
    }

    /**
     * Performs linear interpolation (LERP) between the starting value {@code s} and the ending value {@code e}.
     *
     * @param s The starting value
     * @param e The ending value
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The interpolated value between the starting and ending values {@code s} and {@code e}
     */
    @Nonnull
    public static Vector3 lerp(@Nonnull Vector3 s, @Nonnull Vector3 e, double t) {
        final double s1 = s.x();
        final double e1 = e.x();
        final double s2 = s.y();
        final double e2 = e.y();
        final double s3 = s.z();
        final double e3 = e.z();

        return new Vector3(
                s1 + (e1 - s1) * t,
                s2 + (e2 - s2) * t,
                s3 + (e3 - s3) * t
        );
    }

    /**
     * Performs linear interpolation (LERP) between the starting value {@code s} and the ending value {@code e}.
     *
     * @param s The starting value
     * @param e The ending value
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The interpolated value between the starting and ending values {@code s} and {@code e}
     */
    @Nonnull
    public static Vector4 lerp(@Nonnull Vector4 s, @Nonnull Vector4 e, double t) {
        final double s1 = s.w();
        final double e1 = e.w();
        final double s2 = s.x();
        final double e2 = e.x();
        final double s3 = s.y();
        final double e3 = e.y();
        final double s4 = s.z();
        final double e4 = e.z();

        return new Vector4(
                s1 + (e1 - s1) * t,
                s2 + (e2 - s2) * t,
                s3 + (e3 - s3) * t,
                s4 + (e4 - s4) * t
        );
    }

    //
    // Vector Clamping
    //

    /**
     * Returns the collective minimum vector between the provided vectors.
     *
     * @param values The vectors of which to get the minimum vector of
     * @return The minimum vector of the provided vectors
     */
    @Nonnull
    public static Vector2 min(@Nonnull Vector2... values) {
        double x = Double.MAX_VALUE, y = Double.MAX_VALUE;

        for (final Vector2 value : values) {
            x = Math.min(x, value.x());
            y = Math.min(y, value.y());
        }

        return new Vector2(x, y);
    }

    /**
     * Returns the collective minimum vector between the provided vectors.
     *
     * @param values The vectors of which to get the minimum vector of
     * @return The minimum vector of the provided vectors
     */
    @Nonnull
    public static Vector3 min(@Nonnull Vector3... values) {
        double x = Double.MAX_VALUE, y = Double.MAX_VALUE, z = Double.MAX_VALUE;

        for (final Vector3 value : values) {
            x = Math.min(x, value.x());
            y = Math.min(y, value.y());
            z = Math.min(z, value.z());
        }

        return new Vector3(x, y, z);
    }

    /**
     * Returns the collective minimum vector between the provided vectors.
     *
     * @param values The vectors of which to get the minimum vector of
     * @return The minimum vector of the provided vectors
     */
    @Nonnull
    public static Vector4 min(@Nonnull Vector4... values) {
        double w = Double.MAX_VALUE, x = Double.MAX_VALUE, y = Double.MAX_VALUE, z = Double.MAX_VALUE;

        for (final Vector4 value : values) {
            w = Math.min(w, value.w());
            x = Math.min(x, value.x());
            y = Math.min(y, value.y());
            z = Math.min(z, value.z());
        }

        return new Vector4(w, x, y, z);
    }

    /**
     * Returns the collective maximum vector between the provided vectors.
     *
     * @param values The vectors of which to get the maximum vector of
     * @return The maximum vector of the provided vectors
     */
    @Nonnull
    public static Vector2 max(@Nonnull Vector2... values) {
        double x = -Double.MAX_VALUE, y = -Double.MAX_VALUE;

        for (final Vector2 value : values) {
            x = Math.max(x, value.x());
            y = Math.max(y, value.y());
        }

        return new Vector2(x, y);
    }

    /**
     * Returns the collective maximum vector between the provided vectors.
     *
     * @param values The vectors of which to get the maximum vector of
     * @return The maximum vector of the provided vectors
     */
    @Nonnull
    public static Vector3 max(@Nonnull Vector3... values) {
        double x = -Double.MAX_VALUE, y = -Double.MAX_VALUE, z = -Double.MAX_VALUE;

        for (final Vector3 value : values) {
            x = Math.max(x, value.x());
            y = Math.max(y, value.y());
            z = Math.max(z, value.z());
        }

        return new Vector3(x, y, z);
    }

    /**
     * Returns the collective maximum vector between the provided vectors.
     *
     * @param values The vectors of which to get the maximum vector of
     * @return The maximum vector of the provided vectors
     */
    @Nonnull
    public static Vector4 max(@Nonnull Vector4... values) {
        double w = -Double.MAX_VALUE, x = -Double.MAX_VALUE, y = -Double.MAX_VALUE, z = -Double.MAX_VALUE;

        for (final Vector4 value : values) {
            w = Math.max(w, value.w());
            x = Math.max(x, value.x());
            y = Math.max(y, value.y());
            z = Math.max(z, value.z());
        }

        return new Vector4(w, x, y, z);
    }

    //
    // Miscellaneous
    //

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalInstanceException Always
     */
    private Vectors() throws IllegalInstanceException {
        throw new IllegalInstanceException(this);
    }
}
