package civitas.celestis.math;

import civitas.celestis.util.SafeArray;
import jakarta.annotation.Nonnull;

/**
 * Contains mathematical utilities related to vectors.
 * @see Vector
 */
public final class Vectors {
    //
    //
    //
    // Randomization
    //
    //
    //


    /**
     * Returns a random unit vector. (a vector whose Euclidean norm is {@code 1})
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector1 randomVector1() {
        return Scalars.random() < 0.5 ? Vector1.POSITIVE_X : Vector1.NEGATIVE_X;
    }

    /**
     * Returns a random unit vector. (a vector whose Euclidean norm is {@code 1})
     * When all random scalar components are zero, this will fall back to {@link Vector2#POSITIVE_X}.
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector2 randomVector2() {
        return new Vector2(Scalars.random(-1, 1), Scalars.random(-1, 1)).normalizeOrDefault(Vector2.POSITIVE_X);
    }

    /**
     * Returns a random unit vector. (a vector whose Euclidean norm is {@code 1})
     * When all random scalar components are zero, this will fall back to {@link Vector3#POSITIVE_X}.
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector3 randomVector3() {
        return new Vector3(Scalars.random(-1, 1), Scalars.random(-1, 1), Scalars.random(-1, 1))
                .normalizeOrDefault(Vector3.POSITIVE_X);
    }

    /**
     * Returns a random unit vector. (a vector whose Euclidean norm is {@code 1})
     * When all random scalar components are zero, this will fall back to {@link Vector4#POSITIVE_X}.
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector4 randomVector4() {
        return new Vector4(Scalars.random(-1, 1), Scalars.random(-1, 1), Scalars.random(-1, 1), Scalars.random(-1, 1))
                .normalizeOrDefault(Vector4.POSITIVE_X);
    }

    /**
     * Returns a random rotation quaternion. (a quaternion whose Euclidean norm is {@code 1})
     * When all random scalar components are zero, this will fall back to {@link Quaternion#IDENTITY}.
     *
     * @return A random rotation quaternion
     */
    @Nonnull
    public static Quaternion randomQuaternion() {
        return new Quaternion(Scalars.random(-1, 1), Scalars.random(-1, 1), Scalars.random(-1, 1), Scalars.random(-1, 1))
                .normalizeOrDefault(Quaternion.IDENTITY);
    }

    //
    //
    //
    // Range Validation
    //
    //
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
        if (val.dimensions() != min.dimensions() || min.dimensions() != max.dimensions()) return false;

        for (int i = 0; i < val.dimensions(); i++) {
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
    public static boolean isInRange(@Nonnull Vector1 val, @Nonnull Vector1 min, @Nonnull Vector1 max) {
        return Scalars.isInRange(val.x(), min.x(), max.x());
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
    //
    //
    // Vector Arithmetic
    //
    //
    //

    /**
     * Returns the sum of the two values.
     *
     * @param v1  The first value to add
     * @param v2  The second value to add
     * @param <V> The type of vector to use for this operation
     * @return The sum of the provided values
     * @throws IllegalArgumentException When the provided array's vectors do not have the same dimension count
     */
    @Nonnull
    public static <V extends Vector<V>> V sum(@Nonnull V v1, @Nonnull V v2) {
        return v1.add(v2);
    }

    /**
     * Returns the sum of the provided values.
     *
     * @param values The values to sum
     * @param <V>    The type of vector to use for this operation
     * @return The sum of the provided values
     * @throws IllegalArgumentException When the provided array's vectors do not have the same dimension count
     */
    @Nonnull
    @SafeVarargs
    public static <V extends Vector<V>> Vector<?> sum(@Nonnull V... values) {
        if (values.length == 0) {
            return Vector.of();
        }

        final SafeArray<Double> components = values[0].array();

        for (V value : values) {
            if (components.length() != value.dimensions()) {
                throw new IllegalArgumentException("Cannot sum vectors of different dimensions.");
            }

            for (int j = 1; j < value.dimensions(); j++) {
                components.set(j, components.get(j) + value.get(j));
            }
        }

        return Vector.copyOf(components);
    }

    /**
     * Returns the sum of the two values.
     *
     * @param v1 The first value to add
     * @param v2 The second value to add
     * @return The sum of the provided values
     */
    @Nonnull
    public static Vector1 sum(@Nonnull Vector1 v1, @Nonnull Vector1 v2) {
        return v1.add(v2);
    }

    /**
     * Returns the sum of the provided values.
     *
     * @param values The values to sum
     * @return The sum of the provided values
     */
    @Nonnull
    public static Vector1 sum(@Nonnull Vector1... values) {
        double x = 0;
        for (final Vector1 value : values) {
            x += value.x();
        }
        return new Vector1(x);
    }

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
     * @param v1  The first vector to average
     * @param v2  The second vector to average
     * @param <V> The type of vector to use for this operation
     * @return The simple average of the two vectors
     * @throws IllegalArgumentException When the provided array's vectors do not have the same dimension count
     */
    @Nonnull
    public static <V extends Vector<V>> Vector<?> avg(@Nonnull V v1, @Nonnull V v2) {
        return v1.add(v2).divide(2);
    }

    /**
     * Returns the simple average of the provided vectors.
     * This is equivalent to the centroid in a geometric context.
     *
     * @param values The vectors to average
     * @param <V>    The type of vector to use for this operation
     * @return The simple average of the provided vectors
     * @throws IllegalArgumentException When the provided array's vectors do not have the same dimension count
     */
    @Nonnull
    @SafeVarargs
    public static <V extends Vector<V>> Vector<?> avg(@Nonnull V... values) {
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
    public static Vector1 avg(@Nonnull Vector1 v1, @Nonnull Vector1 v2) {
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
    public static Vector1 avg(@Nonnull Vector1... values) {
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
    //
    //
    // Linear Interpolation (LERP)
    //
    //
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
    public static Vector1 lerp(@Nonnull Vector1 s, @Nonnull Vector1 e, double t) {
        final double s1 = s.x();
        final double e1 = e.x();

        return new Vector1(s1 + (e1 - s1) * t);
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

    /**
     * Performs linear interpolation (LERP) between the starting value {@code s} and the ending value {@code e}.
     *
     * @param s The starting value
     * @param e The ending value
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The interpolated value between the starting and ending values {@code s} and {@code e}
     */
    @Nonnull
    public static Quaternion lerp(@Nonnull Quaternion s, @Nonnull Quaternion e, double t) {
        final double s1 = s.w();
        final double e1 = e.w();
        final double s2 = s.x();
        final double e2 = e.x();
        final double s3 = s.y();
        final double e3 = e.y();
        final double s4 = s.z();
        final double e4 = e.z();

        return new Quaternion(
                s1 + (e1 - s1) * t,
                s2 + (e2 - s2) * t,
                s3 + (e3 - s3) * t,
                s4 + (e4 - s4) * t
        );
    }

    //
    // Spherical Linear Interpolation (SLERP)
    //

    /**
     * Performs spherical linear interpolation (SLERP) between two quaternions.
     * This assumes that the input quaternions are already normalized.
     *
     * @param start The starting quaternion
     * @param end   The end quaternion
     * @param t     The interpolation parameter {@code t} ({@code 0-1})
     * @return The interpolated quaternion
     */
    @Nonnull
    public static Quaternion slerp(@Nonnull Quaternion start, @Nonnull Quaternion end, double t) {
        // Get the dot product of the two quaternions
        double dot = start.dot(end);

        // Determine direction and adjust end quaternion if required
        if (dot < 0) {
            end = end.negate();
            dot = -dot;
        }

        if (1 - dot < Scalars.EPSILON) {
            // Quaternions are very close, use linear interpolation
            return lerp(start, end, t);
        }

        // Calculate the angle between the quaternions
        final double theta0 = Math.acos(dot);
        final double theta1 = theta0 * t;

        // Calculate the interpolation coefficients
        final double s0 = Math.sin((1 - t) * theta0) / Math.sin(theta0);
        final double s1 = Math.sin(theta1) / Math.sin(theta0);

        // Perform spherical linear interpolation
        return start.multiply(s0).add(end.multiply(s1));
    }

    //
    //
    //
    // Vector Clamping
    //
    //
    //

    /**
     * Returns the collective minimum vector between the provided vectors.
     *
     * @param values The vectors of which to get the minimum vector of
     * @param <V>    The type of vector to use for this operation
     * @return The minimum vector of the provided vectors
     */
    @Nonnull
    @SafeVarargs
    public static <V extends Vector<V>> Vector<?> min(@Nonnull V... values) {
        if (values.length == 0) {
            return Vector.of();
        }

        final SafeArray<Double> components = values[0].array();

        for (final V value : values) {
            if (components.length() != value.dimensions()) {
                throw new IllegalArgumentException("Cannot derive the minimum vector of vectors of different dimensions");
            }

            for (int i = 0; i < value.dimensions(); i++) {
                components.set(i, Math.min(components.get(i), value.get(i)));
            }
        }

        return Vector.copyOf(components);
    }

    /**
     * Returns the collective minimum vector between the provided vectors.
     *
     * @param values The vectors of which to get the minimum vector of
     * @return The minimum vector of the provided vectors
     */
    @Nonnull
    public static Vector1 min(@Nonnull Vector1... values) {
        double x = Double.MAX_VALUE;

        for (final Vector1 value : values) {
            x = Math.min(x, value.x());
        }

        return new Vector1(x);
    }

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
     * @param <V>    The type of vector to use for this operation
     * @return The maximum vector of the provided vectors
     */
    @Nonnull
    @SafeVarargs
    public static <V extends Vector<V>> Vector<?> max(@Nonnull V... values) {
        if (values.length == 0) {
            return Vector.of();
        }

        final SafeArray<Double> components = values[0].array();

        for (final V value : values) {
            if (components.length() != value.dimensions()) {
                throw new IllegalArgumentException("Cannot derive the minimum vector of vectors of different dimensions");
            }

            for (int i = 0; i < value.dimensions(); i++) {
                components.set(i, Math.max(components.get(i), value.get(i)));
            }
        }

        return Vector.copyOf(components);
    }

    /**
     * Returns the collective maximum vector between the provided vectors.
     *
     * @param values The vectors of which to get the maximum vector of
     * @return The maximum vector of the provided vectors
     */
    @Nonnull
    public static Vector1 max(@Nonnull Vector1... values) {
        double x = -Double.MAX_VALUE;

        for (final Vector1 value : values) {
            x = Math.max(x, value.x());
        }

        return new Vector1(x);
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
}
