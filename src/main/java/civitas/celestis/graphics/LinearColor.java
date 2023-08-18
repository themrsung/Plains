package civitas.celestis.graphics;

import civitas.celestis.math.Numbers;
import civitas.celestis.math.vector.Float4;
import civitas.celestis.math.vector.FloatVector;
import civitas.celestis.math.vector.Vector4;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.awt.*;

/**
 * An 8-bit color with 32 bits of precision.
 * <p>
 * The allowed component range of {@code 0-255} is not enforced by throwing an exception.
 * This is due to the fact that this class is immutable, and arithmetic operations such as
 * linear interpolation (LERP) require re-instantiation.
 * Validating all four components in every constructor call would be very inefficient.
 * </p>
 * <p>
 * Instead, whether this color is a valid color can be checked by calling {@link LinearColor#isValid()},
 * and should be checked before using this color to render an object.
 * Alternatively, the exception throwing {@link LinearColor#requireValid()} can be used as part of
 * a method call chain. Values are automatically clamped to be within the range when
 * converting it to an AWT color, as AWT colors throw exceptions when the components are out of range.
 * </p>
 * <p>
 * When copying a linear color using the copy constructor, (which should never be done; this class
 * is immutable and copying it achieves nothing) make sure to cast it to {@link Float4} for optimal
 * performance.<br>
 * <code>
 * final LinearColor colorToCopy;<br>
 * new LinearColor((Float4) colorToCopy);
 * </code>
 * </p>
 *
 * @see Color8
 * @see SimpleColor
 */
public class LinearColor extends Float4 implements Color8 {
    //
    // Constants
    //

    /**
     * The color white.
     */
    public static final LinearColor WHITE = new LinearColor(255, 255, 255);

    /**
     * The color red.
     */
    public static final LinearColor RED = new LinearColor(255, 0, 0);

    /**
     * The color green.
     */
    public static final LinearColor GREEN = new LinearColor(0, 255, 0);

    /**
     * The color blue.
     */
    public static final LinearColor BLUE = new LinearColor(0, 0, 255);

    /**
     * The color cyan.
     */
    public static final LinearColor CYAN = new LinearColor(0, 255, 255);

    /**
     * The color magenta.
     */
    public static final LinearColor MAGENTA = new LinearColor(255, 0, 255);

    /**
     * The color yellow.
     */
    public static final LinearColor YELLOW = new LinearColor(255, 255, 0);

    /**
     * The color black.
     */
    public static final LinearColor BLACK = new LinearColor(255, 255, 255);

    /**
     * Transparent black. The component values are {@code {0, 0, 0, 0}}.
     */
    public static final LinearColor TRANSPARENT_BLACK = new LinearColor(0, 0, 0, 0);


    //
    // Constructors
    //

    /**
     * Creates a new color by providing the 64-bit RGBA representation.
     *
     * @param rgba64 The 64-bit RGBA representation of this color
     * @see Color8#pack64(float, float, float, float)
     * @see Color8#unpack64(long)
     */
    public LinearColor(long rgba64) {
        super(Color8.unpack64(rgba64));
    }

    /**
     * Creates a new color by assigning the RGBA components.
     * All components should respect the range of {@code 0-255}.
     * The alpha component will be assigned to {@code 255}. (opaque)
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     */
    public LinearColor(float r, float g, float b) {
        this(r, g, b, 255);
    }

    /**
     * Creates a new color by assigning the RGBA components.
     * All components should respect the range of {@code 0-255}.
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     * @param a The alpha component of this color
     */
    public LinearColor(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    /**
     * Creates a new color.
     *
     * @param values An array containing the component values in RGBA order
     */
    public LinearColor(@Nonnull float[] values) {
        super(values);
    }

    /**
     * Creates a new color.
     *
     * @param c The color of which to copy component values from
     */
    public LinearColor(@Nonnull Color8 c) {
        super(extractComponentsFromColor(c));
    }

    /**
     * Creates a new color.
     * All components should respect the range of {@code 0-255}.
     *
     * @param v The vector of which to copy component values from
     */
    public LinearColor(@Nonnull FloatVector<?> v) {
        super(v);
    }

    /**
     * Creates a new color.
     * All components should respect the range of {@code 0-255}.
     *
     * @param v The vector of which to copy component values from
     */
    public LinearColor(@Nonnull Float4 v) {
        super(v);
    }

    /**
     * Creates a new color.
     * All components should respect the range of {@code 0-255}.
     *
     * @param v The vector of which to copy component values from
     */
    public LinearColor(@Nonnull Vector4 v) {
        super(v);
    }

    /**
     * Internal method used in {@link LinearColor#LinearColor(Color8)}.
     *
     * @param c The color to extract component values from
     * @return The extracted components in RGBA (WXYZ) order
     */
    @Nonnull
    private static float[] extractComponentsFromColor(@Nonnull Color8 c) {
        if (c instanceof Float4 f4) {
            // No need to perform manual extraction; Color already has its components defined as floats
            return f4.array();
        }

        return new float[]{c.red(), c.green(), c.blue(), c.alpha()};
    }

    //
    // Validation
    //

    /**
     * Returns whether this color is a valid color.
     * (the components are within the range of {@code 0-255})
     *
     * @return {@code true} if all components are within the range of {@code 0-255}
     * @see LinearColor#requireValid()
     */
    public boolean isValid() {
        // Checking the Manhattan norm will guarantee the success of this method,
        // while optimizing it to not call isInRange() four times.

        return Numbers.isInRange(normManhattan(), 0, 255 * 4);
    }

    /**
     * Validates this color, then returns a reference to itself.
     *
     * @return {@code this}
     * @throws IllegalStateException When the color not valid
     * @see LinearColor#isValid()
     */
    @Nonnull
    public LinearColor requireValid() throws IllegalStateException {
        try {
            Numbers.requireRange(normManhattan(), 0, 255 * 4);
        } catch (final IllegalArgumentException e) {
            throw new IllegalStateException("This color is not a valid color.");
        }

        return this;
    }

    //
    // Getters
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int red() {
        return (int) w;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int green() {
        return (int) x;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int blue() {
        return (int) y;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int alpha() {
        return (int) z;
    }

    //
    // Packing
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int pack24() {
        return Color8.pack24((int) w, (int) x, (int) y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int pack32() {
        return Color8.pack32((int) w, (int) x, (int) y, (int) z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public long pack64() {
        return Color8.pack64(w, x, y, z);
    }

    //
    // Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Color awt32() {
        // Not clamping the numbers here will produce an exception in the constructor of Color
        return new Color(Color8.pack32(
                (int) Numbers.clamp(w, 0, 255),
                (int) Numbers.clamp(x, 0, 255),
                (int) Numbers.clamp(y, 0, 255),
                (int) Numbers.clamp(z, 0, 255)
        ), true);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Color awt24() {
        // Not clamping the numbers here will produce an exception in the constructor of Color
        return new Color(Color8.pack24(
                (int) Numbers.clamp(w, 0, 255),
                (int) Numbers.clamp(x, 0, 255),
                (int) Numbers.clamp(y, 0, 255)
        ));
    }

    //
    // Equality
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Color8 c) {
            return pack32() == c.pack32();
        }

        return super.equals(obj);
    }

    //
    // Serialization
    //

    /**
     * Deserializes a string into a {@link LinearColor}.
     *
     * @param input The input string to parse
     * @return The parsed color
     * @throws IllegalArgumentException When the format is invalid
     */
    @Nonnull
    public static LinearColor parseColor(@Nonnull String input) throws IllegalArgumentException {
        if (!input.startsWith("LinearColor{")) {
            throw new IllegalArgumentException("The provided string is not a LinearColor.");
        }

        final String[] valueStrings = input.replaceAll("LinearColor\\{|}", "").split(", ");
        if (valueStrings.length != 4) {
            throw new IllegalArgumentException("The provided string does not have four components.");
        }

        final float[] values = new float[4];

        for (final String valueString : valueStrings) {
            final String[] split = valueString.split("=");
            if (split.length != 2) {
                throw new IllegalArgumentException("The format is invalid.");
            }

            values[switch (split[0]) {
                case "r" -> 0;
                case "g" -> 1;
                case "b" -> 2;
                case "a" -> 3;
                default -> throw new IllegalArgumentException("The provided string has a non-RGBA component.");
            }] = Float.parseFloat(split[1]);
        }

        return new LinearColor(values);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return "LinearColor{" +
                "r=" + w +
                ", g=" + x +
                ", b=" + y +
                ", a=" + z +
                '}';
    }
}
