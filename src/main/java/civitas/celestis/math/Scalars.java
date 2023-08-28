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
