package civitas.celestis.math;

import civitas.celestis.exception.IllegalInstanceException;

/**
 * A static utility class containing various constants and methods related
 * to mathematical scalars, primarily of the type {@code double}.
 */
public final class Scalars {
    //
    // Constants
    //

    /**
     * A very small constant used in various applications.
     */
    public static final double EPSILON = 1e-6;

    /**
     * The mathematical constant TAU. (2 * pi)
     */
    public static final double TAU = 2 * Math.PI;

    //
    // Randomization
    //

    /**
     * Returns a random value within the range of {@code [0, 1)}.
     *
     * @return A random value between {@code [0, 1)}
     */
    public static double random() {
        return Math.random();
    }

    /**
     * Returns a random value within the range of {@code [min, max)}.
     *
     * @param min The minimum allowed value (inclusive)
     * @param max The maximum allowed value (exclusive)
     * @return A random value between {@code [min, max)}
     */
    public static double random(double min, double max) {
        return min + random() * (max - min);
    }

    //
    // Range Validation
    //

    /**
     * Returns whether the provided value {@code val} is within the range of {@code [min, max]}.
     *
     * @param val The value to validate
     * @param min The minimum allowed value (inclusive)
     * @param max The maximum allowed value (inclusive)
     * @return {@code true} if the value is within the allowed range
     */
    public static boolean isInRange(double val, double min, double max) {
        return val >= min && val <= max;
    }

    //
    // Clamping
    //

    /**
     * Clamps the provided value to respect the boundaries of {@code [min, max]}.
     *
     * @param val The value of which to clamp
     * @param min The minimum allowed value (inclusive)
     * @param max The maximum allowed value (inclusive)
     * @return The clamped value
     */
    public static double clamp(double val, double min, double max) {
        if (val > max) return max;
        return Math.max(val, min);
    }

    //
    // Linear Interpolation (LERP)
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
     * Performs cubic Bezier interpolation between four scalar values,
     * using the interpolation parameter {@code t}.
     *
     * @param p0 The starting scalar value
     * @param p1 The first control point scalar value
     * @param p2 The second control point scalar value
     * @param p3 The ending scalar value
     * @param t  The interpolation parameter ({@code [0, 1]})
     * @return The interpolated scalar value
     */
    public static double bezier(double p0, double p1, double p2, double p3, double t) {
        final double u = 1.0 - t;
        final double tt = t * t;
        final double uu = u * u;
        final double uuu = uu * u;
        final double ttt = tt * t;

        double result = uuu * p0;
        result += 3 * uu * t * p1;
        result += 3 * u * tt * p2;
        result += ttt * p3;

        return result;
    }

    //
    // Gamma Function
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
    // Factorial
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

    //
    // Miscellaneous
    //

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalInstanceException Always
     */
    private Scalars() throws IllegalInstanceException {
        throw new IllegalInstanceException(this);
    }
}
