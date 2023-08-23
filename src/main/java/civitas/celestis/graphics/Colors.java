package civitas.celestis.graphics;

import civitas.celestis.math.Scalars;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.awt.*;

/**
 * Contains utility methods and constants related to AWT {@link Color}s.
 */
public final class Colors {
    //
    // String Conversion
    //

    /**
     * Converts a color into a human-readable string for debugging purposes.
     *
     * @param c The color of which to represent as a string
     * @return The string representation of the provided color {@code c}
     */
    @Nonnull
    public static String toString(@Nullable Color c) {
        if (c == null) return "";
        return c.getClass().getSimpleName() + "{" +
                "r=" + c.getRed() + ", " +
                "g=" + c.getGreen() + ", " +
                "b=" + c.getBlue() + ", " +
                "a=" + c.getAlpha() +
                "}";
    }

    //
    // Randomization
    //

    /**
     * Returns a random opaque color.
     *
     * @return A random opaque color
     */
    @Nonnull
    public static LinearColor random() {
        return new LinearColor(
                (int) Scalars.random(0, 255),
                (int) Scalars.random(0, 255),
                (int) Scalars.random(0, 255)
        );
    }

    //
    // Linear Interpolation (LERP)
    //

    /**
     * The minimum LERP interval which when provided as the interpolation parameter {@code t},
     * will yield the same result as {@code 0}.
     */
    public static final double MIN_LERP_INTERVAL = (1d / 255d) - Double.MIN_VALUE;

    /**
     * Performs linear interpolation (LERP) between the starting color {@code s} and the
     * ending color {@code e}. The interpolation parameter {@code t} represents the amount of interpolation.
     *
     * @param s The starting color of this operation
     * @param e The ending color of this operation
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The interpolated color
     */
    @Nonnull
    public static Color lerp(@Nonnull Color s, @Nonnull Color e, double t) {
        if (t <= MIN_LERP_INTERVAL) {
            return s;
        }

        if (t >= (1 - MIN_LERP_INTERVAL)) {
            return e;
        }

        final double r1 = s.getRed();
        final double g1 = s.getGreen();
        final double b1 = s.getBlue();
        final double a1 = s.getAlpha();

        final double r2 = e.getRed();
        final double g2 = e.getGreen();
        final double b2 = e.getBlue();
        final double a2 = e.getAlpha();

        return new LinearColor(
                (int) Scalars.lerp(r1, r2, t),
                (int) Scalars.lerp(b1, b2, t),
                (int) Scalars.lerp(g1, g2, t),
                (int) Scalars.lerp(a1, a2, t)
        );
    }
}
