package civitas.celestis.math;

/**
 * Contains utility methods and constants related to mathematics.
 */
public final class Numbers {
    /**
     * A very small constant used in various applications.
     */
    public static final double EPSILON = 1e-6;

    //
    // Clamping
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
}
