package civitas.celestis.math;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * A numerical utility class which contains mathematical functions.
 */
public final class Numbers {
    //
    // Constants
    //

    /**
     * A very small constant value used in various applications.
     */
    public static final double EPSILON = 1e-6;

    //
    // Range Validation
    //

    /**
     * Checks if the provided value is within the range of {@code [min, max]}.
     *
     * @param value The value to check
     * @param min   The minimum allowed value
     * @param max   The maximum allowed value
     * @return {@code true} if the value is within the range of {@code [min, max]}
     */
    public static boolean isInRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * Checks if the provided value is within the range of {@code (min, max)}.
     *
     * @param value The value to check
     * @param min   The minimum allowed value
     * @param max   The maximum allowed value
     * @return {@code true} if the value is within the range of {@code (min, max)}
     */
    public static boolean isInRangeExclusive(double value, double min, double max) {
        return value > min && value < max;
    }

    //
    // Clamping
    //

    /**
     * Clamps the provided value to fit within the range of {@code [min, max]}.
     *
     * @param value The value to clamp
     * @param min   The minimum acceptable value
     * @param max   The maximum acceptable value
     * @return The clamped value
     */
    public static double clamp(double value, double min, double max) {
        return max(min(value, max), min);
    }
}
