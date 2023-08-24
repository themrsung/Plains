package civitas.celestis.graphics;

import civitas.celestis.math.Scalars;
import civitas.celestis.math.vector.Vector4;
import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.awt.*;
import java.io.Serial;

/**
 * An 8-bit RGBA color with linear transition capabilities.
 */
public class LinearColor extends Color {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * The color white.
     */
    public static final LinearColor WHITE = new LinearColor(Color.WHITE);

    /**
     * The color light gray.
     */
    public static final LinearColor LIGHT_GRAY = new LinearColor(Color.LIGHT_GRAY);

    /**
     * The color gray.
     */
    public static final LinearColor GRAY = new LinearColor(Color.GRAY);

    /**
     * The color dark gray.
     */
    public static final LinearColor DARK_GRAY = new LinearColor(Color.DARK_GRAY);

    /**
     * The color black.
     */
    public static final LinearColor BLACK = new LinearColor(Color.BLACK);

    /**
     * The color red.
     */
    public static final LinearColor RED = new LinearColor(Color.RED);

    /**
     * The color pink.
     */
    public static final LinearColor PINK = new LinearColor(Color.PINK);

    /**
     * The color orange.
     */
    public static final LinearColor ORANGE = new LinearColor(Color.ORANGE);

    /**
     * The color yellow.
     */
    public static final LinearColor YELLOW = new LinearColor(Color.YELLOW);

    /**
     * The color magenta.
     */
    public static final LinearColor MAGENTA = new LinearColor(Color.MAGENTA);

    /**
     * The color cyan.
     */
    public static final LinearColor CYAN = new LinearColor(Color.CYAN);

    /**
     * The color blue.
     */
    public static final LinearColor BLUE = new LinearColor(Color.BLUE);

    //
    // Constructors
    //

    /**
     * Creates a new linear color. The 24-bit RGB representation is also known
     * as the hex code. The format should be either "{@code RRGGBB}" or "{@code #RRGGBB}".
     *
     * @param rgb24 The 24-bit RGB representation of this color
     */
    public LinearColor(@Nonnull String rgb24) {
        super(Integer.parseInt(rgb24.replaceAll("#", ""), 16));
    }

    /**
     * Creates a new linear color.
     *
     * @param argb32 The 32-bit ARGB representation of this color
     */
    public LinearColor(int argb32) {
        super(argb32, true);
    }

    /**
     * Creates a new linear color. All components must respect the range of {@code [0, 255]}.
     * The alpha component will be populated with {@code 255}. (opaque)
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     */
    public LinearColor(int r, int g, int b) {
        this(r, g, b, 255);
    }

    /**
     * Creates a new linear color. All components must respect the range of {@code [0, 255]}.
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     * @param a The alpha component of this color
     */
    public LinearColor(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    /**
     * Creates a new linear color.
     *
     * @param c The color of which to copy component values from
     */
    public LinearColor(@Nonnull Color c) {
        super((c.getAlpha() << 24) | (c.getRGB() & 0xFFFFFF), true);
    }

    /**
     * Creates a new linear color. All components must respect the range of {@code [0, 255]}.
     *
     * @param t A tuple containing the components of this color in RGBA order
     */
    public LinearColor(@Nonnull Tuple<? extends Number> t) {
        super(t.get(0).intValue(), t.get(1).intValue(), t.get(2).intValue(), t.get(3).intValue());
    }

    /**
     * Creates a new linear color. All components must respect the range of {@code [0, 255]}.
     *
     * @param v A vector containing the components of this color in RGBA order
     */
    public LinearColor(@Nonnull Vector4 v) {
        super((int) v.w(), (int) v.x(), (int) v.y(), (int) v.z());
    }

    //
    // 32-bit ARGB
    //

    /**
     * Returns the 32-bit ARGB representation of this color.
     *
     * @return The 32-bit ARGB representation of this color
     */
    public int argb32() {
        return (getAlpha() << 24) | (getRGB() & 0xFFFFFF);
    }

    //
    // Linear Interpolation (LERP)
    //

    /**
     * Linearly interpolates (LERP) between this color and the provided color {@code c},
     * according to the provided interpolation parameter {@code t}. This method differs from
     * {@link Colors#lerp(Color, Color, double)} in that the interpolation parameter is not
     * forcefully clamped to be within the acceptable range.
     *
     * @param c The color of which to interpolate towards
     * @param t The interpolation parameter
     * @return The interpolated color
     * @throws IllegalArgumentException When the interpolation will result in at least one
     *                                  of the RGBA components to be outside the range of {@code [0, 255]}
     */
    @Nonnull
    public LinearColor lerp(@Nonnull Color c, double t) {
        final double r1 = getRed();
        final double g1 = getGreen();
        final double b1 = getBlue();
        final double a1 = getAlpha();

        final double r2 = c.getRed();
        final double g2 = c.getGreen();
        final double b2 = c.getBlue();
        final double a2 = c.getAlpha();

        return new LinearColor(
                (int) Scalars.lerp(r1, r2, t),
                (int) Scalars.lerp(b1, b2, t),
                (int) Scalars.lerp(g1, g2, t),
                (int) Scalars.lerp(a1, a2, t)
        );
    }

    //
    // Conversion
    //

    /**
     * Converts this color into a four-dimensional vector notation for easier arithmetic.
     *
     * @return The {@link Vector4} representation of this color
     * @see Vector4
     */
    @Nonnull
    public Vector4 vector() {
        return new Vector4(getRed(), getGreen(), getBlue(), getAlpha());
    }

    /**
     * Converts this color into a tuple of size {@code 4} mapped in RGBA order.
     *
     * @return The tuple representation of this color
     * @see Tuple
     */
    @Nonnull
    public Tuple<Integer> tuple() {
        return Tuple.of(getRed(), getGreen(), getBlue(), getAlpha());
    }

    /**
     * Converts this color into an array containing the components of this color in RGBA order.
     *
     * @return The array representation of this color
     * @see SafeArray
     */
    @Nonnull
    public SafeArray<Integer> safeArray() {
        return SafeArray.of(getRed(), getGreen(), getBlue(), getAlpha());
    }

    /**
     * Converts this color into a raw primitive array containing the components of this color
     * in RGBA order.
     *
     * @return The array representation of this color
     */
    @Nonnull
    public int[] array() {
        final int argb32 = argb32();
        final int[] components = new int[4];

        components[3] = (argb32 >> 24) & 0xFF; // Alpha component
        components[0] = (argb32 >> 16) & 0xFF; // Red component
        components[1] = (argb32 >> 8) & 0xFF;  // Green component
        components[2] = argb32 & 0xFF;         // Blue component

        return components;
    }

    //
    // Serialization
    //

    /**
     * Serializes this color into a string.
     *
     * @return The string representation of this color
     */
    @Nonnull
    @Override
    public String toString() {
        return "Color{" +
                "argb32=" + argb32() +
                "}";
    }

    /**
     * Serializes this color into a human-readable string.
     *
     * @return The human-readable string representation of this color
     */
    @Nonnull
    public String toReadableString() {
        return "Color{" +
                "r=" + getRed() + ", " +
                "g=" + getGreen() + ", " +
                "a=" + getBlue() + ", " +
                "b=" + getAlpha() +
                "}";
    }
}
