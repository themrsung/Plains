package civitas.celestis.math;

import civitas.celestis.util.SafeArray;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Deque;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Contains utility methods and constants related to mathematics.
 */
public final class Numbers {
    /**
     * A very small constant used in various applications.
     */
    public static final double EPSILON = 1e-6;

    /**
     * Ths square root of {@code 2}.
     */
    public static final double SQRT_2 = Math.sqrt(2);

    /**
     * The inverse square root of {@code 2}.
     */
    public static final double INV_SQRT_2 = 1 / Math.sqrt(2);

    /**
     * The mathematical constant TAU. (2 * pi)
     */
    public static final double TAU = 2 * Math.PI;

    //
    //
    //
    // Equality
    //
    //
    //

    /**
     * Checks for equality between two numbers {@code n1} and {@code n2}.
     * This is a null-safe operation.
     *
     * @param n1 The first number to compare
     * @param n2 The second number to compare
     * @return {@code true} if the numbers are equal
     */
    public static boolean equals(@Nullable Number n1, @Nullable Number n2) {
        if (n1 == null) return n2 == null;
        if (n2 == null) return false;
        return n1.equals(n2) || n2.equals(n1);
    }

    /**
     * Checks for equality between the provided values {@code v1} and {@code v2}.
     *
     * @param v1 The first value to compare
     * @param v2 The second value to compare
     * @return {@code true} if the values are equal
     */
    public static boolean equals(double v1, double v2) {
        return v1 == v2;
    }

    /**
     * Checks for equality between the provided values {@code v1} and {@code v2}.
     *
     * @param v1 The first value to compare
     * @param v2 The second value to compare
     * @return {@code true} if the values are equal
     */
    public static boolean equals(float v1, float v2) {
        return v1 == v2;
    }

    /**
     * Checks for equality between the provided values {@code v1} and {@code v2}.
     *
     * @param v1 The first value to compare
     * @param v2 The second value to compare
     * @return {@code true} if the values are equal
     */
    public static boolean equals(long v1, long v2) {
        return v1 == v2;
    }

    /**
     * Checks for equality between the provided values {@code v1} and {@code v2}.
     *
     * @param v1 The first value to compare
     * @param v2 The second value to compare
     * @return {@code true} if the values are equal
     */
    public static boolean equals(int v1, int v2) {
        return v1 == v2;
    }

    //
    //
    //
    // Randomization
    //
    //
    //

    /**
     * An internally used queue of random generators
     */
    private static final Deque<Random> randomGenerators = new ConcurrentLinkedDeque<>();

    /**
     * Polls the next random generator, then adds it back to the queue.
     *
     * @return The next random generator in the queue
     */
    private synchronized static Random nextRandom() {
        final Random next = randomGenerators.pollFirst();
        randomGenerators.addLast(next);
        return next;
    }

    static {
        // Initialize four random generators
        randomGenerators.add(new Random());
        randomGenerators.add(new Random());
        randomGenerators.add(new Random());
        randomGenerators.add(new Random());
    }

    /**
     * Returns a random value between the range of {@code [0, 1)}.
     *
     * @return A random value between {@code [0, 1)}
     */
    public static double random() {
        return nextRandom().nextDouble();
    }

    /**
     * Returns a random value between the range of {@code [min, max)}.
     *
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @return A random value between the two values
     */
    public static double random(double min, double max) {
        return min + nextRandom().nextDouble() * (max - min);
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
     * @return {@code true} if the value is within the range of {@code [min, max]}
     */
    public static boolean isInRange(double val, double min, double max) {
        return val >= min && val <= max;
    }

    /**
     * Checks if the provided value {@code val} is within the range of {@code [min, max]}.
     *
     * @param val The value to compare
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @return {@code true} if the value is within the range of {@code [min, max]}
     * @param <V> The type of vector to use for this operation
     */
    public static <V extends Vector<V>> boolean isInRange(@Nonnull V val, @Nonnull V min, @Nonnull V max) {
        if (val.dimensions() != min.dimensions() || min.dimensions() != max.dimensions()) return false;

        for (int i = 0; i < val.dimensions(); i++) {
            if (!isInRange(val.get(i), min.get(i), max.get(i))) return false;
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
        return isInRange(val.x, min.x, max.x);
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
        return isInRange(val.x, min.x, max.x) && isInRange(val.y, min.y, max.y);
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
        return isInRange(val.x, min.x, max.x) && isInRange(val.y, min.y, max.y) && isInRange(val.z, min.z, max.z);
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
        return isInRange(val.w, min.w, max.w) &&
                isInRange(val.x, min.x, max.x) &&
                isInRange(val.y, min.y, max.y) &&
                isInRange(val.z, min.z, max.z);
    }

    //
    //
    //
    // Scalar Arithmetic
    //
    //
    //

    /**
     * Returns the sum of the two values.
     *
     * @param v1 The first value to add
     * @param v2 The second value to add
     * @return The sum of the provided values
     */
    public static double sum(double v1, double v2) {
        return v1 + v2;
    }

    /**
     * Returns the sum of three values.
     *
     * @param v1 The first value to add
     * @param v2 The second value to add
     * @param v3 The third value to add
     * @return The sum of the provided values
     */
    public static double sum(double v1, double v2, double v3) {
        return v1 + v2 + v3;
    }

    /**
     * Returns the sum of the four values.
     *
     * @param v1 The first value to add
     * @param v2 The second value to add
     * @param v3 The third value to add
     * @param v4 The fourth value to add
     * @return The sum of the provided values
     */
    public static double sum(double v1, double v2, double v3, double v4) {
        return v1 + v2 + v3 + v4;
    }

    /**
     * Returns the sum of the provided values.
     *
     * @param values The values to sum
     * @return The sum of the provided values
     */
    public static double sum(@Nonnull double... values) {
        double sum = 0;
        for (final double value : values) sum += value;
        return sum;
    }

    /**
     * Returns the product of the two values.
     *
     * @param v1 The first value to multiply
     * @param v2 The second value to multiply
     * @return THe product of the provided values
     */
    public static double product(double v1, double v2) {
        return v1 * v2;
    }

    /**
     * Returns the product of the three values.
     *
     * @param v1 The first value to multiply
     * @param v2 The second value to multiply
     * @param v3 The third value to multiply
     * @return The product of the provided values
     */
    public static double product(double v1, double v2, double v3) {
        return v1 * v2 * v3;
    }

    /**
     * Returns the product of the four values.
     *
     * @param v1 The first value to multiply
     * @param v2 The second value to multiply
     * @param v3 The third value to multiply
     * @param v4 The fourth value to multiply
     * @return The product of the provided values
     */
    public static double product(double v1, double v2, double v3, double v4) {
        return v1 * v2 * v3 * v4;
    }

    /**
     * Returns the product of the provided values.
     *
     * @param values The values to multiply
     * @return The product of the provided values
     */
    public static double product(@Nonnull double... values) {
        double product = 1;
        for (final double value : values) product *= value;
        return product;
    }

    /**
     * Returns the simple average of the two values.
     *
     * @param v1 The first value to average
     * @param v2 The second value to average
     * @return The simple average of the two values
     */
    public static double avg(double v1, double v2) {
        return (v1 + v2) / 2;
    }

    /**
     * Returns the simple average of the three values.
     *
     * @param v1 The first value to average
     * @param v2 The second value to average
     * @param v3 The third value to average
     * @return The simple average of the three values
     */
    public static double avg(double v1, double v2, double v3) {
        return (v1 + v2 + v3) / 3;
    }

    /**
     * Returns the simple average of the four values.
     *
     * @param v1 The first value to average
     * @param v2 The second value to average
     * @param v3 The third value to average
     * @param v4 The fourth value to average
     * @return The simple average of the four values
     */
    public static double avg(double v1, double v2, double v3, double v4) {
        return (v1 + v2 + v3 + v4) / 4;
    }

    /**
     * Returns the simple average of the provided values.
     *
     * @param values The values to average
     * @return The simple average of the provided values
     */
    public static double avg(@Nonnull double... values) {
        return sum(values) / values.length;
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
     * @param v1 The first value to add
     * @param v2 The second value to add
     * @return The sum of the provided values
     * @throws IllegalArgumentException When the provided array's vectors do not have the same dimension count
     * @param <V> The type of vector to use for this operation
     */
    @Nonnull
    public static <V extends Vector<V>> V sum(@Nonnull V v1, @Nonnull V v2) {
        return v1.add(v2);
    }

    /**
     * Returns the sum of the provided values.
     *
     * @param values The values to sum
     * @param <V> The type of vector to use for this operation
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
     * @param v1 The first vector to average
     * @param v2 The second vector to average
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
     * @return The simple average of the provided vectors
     * @param <V> The type of vector to use for this operation
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
     * @param s The starting value
     * @param e The ending value
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The interpolated value between the starting and ending values {@code s} and {@code e}
     */
    public static double lerp(double s, double e, double t) {
        return s + (e - s) * t;
    }

    /**
     * Performs linear interpolation (LERP) between the starting value {@code s} and the ending value {@code e}.
     *
     * @param s The starting value
     * @param e The ending value
     * @param <V> The type of vector to use for this operation
     * @param t The interpolation parameter ({@code [0, 1]})
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

        if (1 - dot < Numbers.EPSILON) {
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
    // Scalar Clamping
    //
    //
    //

    /**
     * Given a value and two boundary values {@code min} and {@code max},
     * this clamps the value to respect the range of {@code [min, max]}.
     *
     * @param val The value to clamp
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @return The inclusively clamped value of the provided value {@code val}
     */
    public static double clamp(double val, double min, double max) {
        return val > max ? max : (Math.max(val, min));
    }

    /**
     * Returns the minimum value of the provided values.
     *
     * @param values The values of which to get the minimum value of
     * @return The minimum of the provided values
     */
    public static double min(@Nonnull double... values) {
        double min = Double.MAX_VALUE;
        for (final double value : values) min = Math.min(value, min);
        return min;
    }

    /**
     * Returns the maximum value of the provided values.
     *
     * @param values The values of which to get the maximum value of
     * @return The maximum value of the provided values
     */
    public static double max(@Nonnull double... values) {
        double max = -Double.MAX_VALUE;
        for (final double value : values) max = Math.max(value, max);
        return max;
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
     * @return The minimum vector of the provided vectors
     * @param <V> The type of vector to use for this operation
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
            x = Math.min(x, value.x);
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
            x = Math.min(x, value.x);
            y = Math.min(y, value.y);
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
            x = Math.min(x, value.x);
            y = Math.min(y, value.y);
            z = Math.min(z, value.z);
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
            w = Math.min(w, value.w);
            x = Math.min(x, value.x);
            y = Math.min(y, value.y);
            z = Math.min(z, value.z);
        }

        return new Vector4(w, x, y, z);
    }

    /**
     * Returns the collective maximum vector between the provided vectors.
     *
     * @param values The vectors of which to get the maximum vector of
     * @return The maximum vector of the provided vectors
     * @param <V> The type of vector to use for this operation
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
            x = Math.max(x, value.x);
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
            x = Math.max(x, value.x);
            y = Math.max(y, value.y);
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
            x = Math.max(x, value.x);
            y = Math.max(y, value.y);
            z = Math.max(z, value.z);
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
            w = Math.max(w, value.w);
            x = Math.max(x, value.x);
            y = Math.max(y, value.y);
            z = Math.max(z, value.z);
        }

        return new Vector4(w, x, y, z);
    }

    //
    //
    //
    // Gamma Function
    //
    //
    //

    // Lanczos parameters
    private static final double LANCZOS_G = 7;
    private static final double[] LANCZOS_COEFFICIENTS = {
            0.99999999999980993,
            676.5203681218851,
            -1259.1392167224028,
            771.32342877765313,
            -176.61502916214059,
            12.507343278686905,
            -0.13857109526572012,
            9.9843695780195716e-6,
            1.5056327351493116e-7
    };

    /**
     * Returns the gamma of the given input {@code n}.
     *
     * @param n The value to get the gamma of
     * @return The gamma function value of {@code n}
     */
    public static double gamma(double n) {
        // Copy value for manipulation
        double x = n;

        if (x <= 0.5) {
            return Math.PI / (Math.sin(Math.PI * x) * gamma(1 - x));
        }

        x -= 1;
        double result = LANCZOS_COEFFICIENTS[0];
        for (int i = 1; i < LANCZOS_COEFFICIENTS.length; i++) {
            result += LANCZOS_COEFFICIENTS[i] / (x + i);
        }

        final double t = x + LANCZOS_G + 0.5;
        return Math.sqrt(2 * Math.PI) * Math.pow(t, x + 0.5) * Math.exp(-t) * result;
    }

    //
    //
    //
    // Factorial
    //
    //
    //

    /**
     * Calculates the factorial of the provided input {@code n}.
     * Note that this requires a non-negative input.
     *
     * @param n The number to get the factorial of
     * @return The factorial of {@code n}
     */
    public static double factorial(double n) {
        // Estimate factorial using the Gamma function
        return gamma(n + 1);
    }

    /**
     * Calculates the factorial of the provided input {@code n}.
     * Note that this requires a non-negative input. Values over {@code 20} will trigger
     * an integer overflow.
     *
     * @param n The number to get the factorial of
     * @return The factorial of {@code n}
     */
    public static long factorial(long n) {
        return n < FACTORIALS.length ? FACTORIALS[(int) n] : (long) factorial((double) n);
    }

    // The pre-computed table of integer factorials.
    private static final long[] FACTORIALS = {
            1L,
            1L,
            2L,
            6L,
            24L,
            120L,
            720L,
            5040L,
            40320L,
            362880L,
            3628800L,
            39916800L,
            479001600L,
            6227020800L,
            87178291200L,
            1307674368000L,
            20922789888000L,
            355687428096000L,
            6402373705728000L,
            121645100408832000L,
            2432902008176640000L
    };
}
