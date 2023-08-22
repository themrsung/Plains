package civitas.celestis.math;

import jakarta.annotation.Nonnull;

import java.util.Deque;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Contains mathematical utilities related to scalars.
 */
public final class Scalars {
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
