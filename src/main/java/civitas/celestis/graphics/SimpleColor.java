package civitas.celestis.graphics;

import jakarta.annotation.Nonnull;

import java.awt.*;
import java.io.Serial;

/**
 * An 8-bit color with no extra precision.
 *
 * @see Color8
 * @see LinearColor
 */
public class SimpleColor implements Color8 {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 960954011708678214L;

    /**
     * The color white.
     */
    public static final SimpleColor WHITE = new SimpleColor(255, 255, 255);

    /**
     * The color red.
     */
    public static final SimpleColor RED = new SimpleColor(255, 0, 0);

    /**
     * The color green.
     */
    public static final SimpleColor GREEN = new SimpleColor(0, 255, 0);

    /**
     * The color blue.
     */
    public static final SimpleColor BLUE = new SimpleColor(0, 0, 255);

    /**
     * The color cyan.
     */
    public static final SimpleColor CYAN = new SimpleColor(0, 255, 255);

    /**
     * The color magenta.
     */
    public static final SimpleColor MAGENTA = new SimpleColor(255, 0, 255);

    /**
     * The color yellow.
     */
    public static final SimpleColor YELLOW = new SimpleColor(255, 255, 0);

    /**
     * The color black.
     */
    public static final SimpleColor BLACK = new SimpleColor(255, 255, 255);

    //
    // Constructors
    //

    /**
     * Creates a new color by manually designating the RGBA bits.
     *
     * @param rgba32 The 32-bit representation of this color
     * @see Color8#pack32()
     */
    public SimpleColor(int rgba32) {
        this.rgba32 = rgba32;
    }

    /**
     * Create a new color by assigning the RGB components separately.
     * All components should be within the range of {@code 0-255}.
     * The alpha component is assigned to {@code 255}. (opaque)
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     */
    public SimpleColor(int r, int g, int b) {
        this.rgba32 = Color8.pack32(r, g, b, 255);
    }

    /**
     * Create a new color by assigning the RGBA components separately.
     * All components should be within the range of {@code 0-255}.
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     * @param a The alpha component of this color
     */
    public SimpleColor(int r, int g, int b, int a) {
        this.rgba32 = Color8.pack32(r, g, b, a);
    }

    /**
     * Creates a new color.
     *
     * @param rgba An array containing the components of this color in RGBA order
     * @throws IllegalArgumentException When the provided array's length is not {@code 4}
     */
    public SimpleColor(@Nonnull int[] rgba) {
        if (rgba.length != 4) {
            throw new IllegalArgumentException("The provided array's length is not 4.");
        }

        this.rgba32 = Color8.pack32(rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    /**
     * Creates a new color.
     *
     * @param rgba An array containing the components of this color in RGBA order
     * @throws IllegalArgumentException When the provided array's length is not {@code 4}
     */
    public SimpleColor(@Nonnull float[] rgba) {
        if (rgba.length != 4) {
            throw new IllegalArgumentException("The provided array's length is not 4.");
        }

        this.rgba32 = Color8.pack32((int) rgba[0], (int) rgba[1], (int) rgba[2], (int) rgba[3]);
    }

    //
    // Static Initialization
    //

    /**
     * Creates a new color from a 24-bit RGB representation.
     *
     * @param rgb24 The 24-bit RGB representation of this color
     * @return The converted color
     */
    @Nonnull
    public static SimpleColor from24(int rgb24) {
        return new SimpleColor(Color8.convert24to32(rgb24));
    }

    /**
     * Creates a new color from a 24-bit RGB representation.
     *
     * @param rgb24 The 24-bit RGB representation of this color
     * @param a     The alpha component to use
     * @return The converted color
     */
    @Nonnull
    public static SimpleColor from24(int rgb24, int a) {
        return new SimpleColor(Color8.convert24to32(rgb24, a));
    }

    /**
     * Creates a new color from a 64-bit RGBA representation.
     *
     * @param rgba64 The 64-bit RGBA representation of this color
     * @return The converted color
     */
    @Nonnull
    public static SimpleColor from64(int rgba64) {
        return new SimpleColor(Color8.convert64to32(rgba64));
    }

    //
    // Variables
    //

    /**
     * The 32-bit RGBA representation.
     */
    protected final int rgba32;

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
        return Color8.unpack32R(rgba32);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int green() {
        return Color8.unpack32G(rgba32);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int blue() {
        return Color8.unpack32B(rgba32);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int alpha() {
        return Color8.unpack32A(rgba32);
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
        return Color8.convert32to24(rgba32);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Since {@link SimpleColor} is already packaged in 32 bits,
     * this is simply a getter method with no conversion involved.
     * </p>
     *
     * @return {@inheritDoc}
     */
    @Override
    public int pack32() {
        return rgba32; // Color is already packed in this encoding
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public long pack64() {
        return Color8.convert32to64(rgba32);
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
        return new Color(rgba32, true);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Color awt24() {
        return new Color(Color8.convert32to24(rgba32));
    }

    //
    // Utility
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SimpleColor inverse() {
        // Extract the alpha component
        final int alpha = (rgba32 >> 24) & 0xFF;

        // Extract the red, green, and blue components
        int red = (rgba32 >> 16) & 0xFF;
        int green = (rgba32 >> 8) & 0xFF;
        int blue = rgba32 & 0xFF;

        // Invert the RGB components
        red = 255 - red;
        green = 255 - green;
        blue = 255 - blue;

        // Repack the inverted components with the original alpha component
        return new SimpleColor((alpha << 24) | (red << 16) | (green << 8) | blue);
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
    public boolean equals(Object obj) {
        if (!(obj instanceof Color8 c)) return false;
        return rgba32 == c.pack32();
    }


    //
    // Serialization
    //

    /**
     * Deserializes a string into a {@link SimpleColor}.
     *
     * @param input The input string to parse
     * @return The parsed color
     * @throws IllegalArgumentException When the format is invalid
     */
    @Nonnull
    public static SimpleColor parseColor(@Nonnull String input) throws IllegalArgumentException {
        return new SimpleColor(Integer.parseInt(input.replaceAll("SimpleColor\\{rgba32=|}", "")));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return "SimpleColor{" +
                "rgba32=" + rgba32 +
                '}';
    }

    /**
     * Serializes this color into a human-readable string.
     *
     * @return The human-readable string representation of this color
     */
    @Nonnull
    public String toReadableString() {
        final int r = red();
        final int g = green();
        final int b = blue();
        final int a = alpha();

        return "SimpleColor{" +
                "r=" + r + ", " +
                "g=" + g + ", " +
                "b=" + b + ", " +
                "a=" + a +
                '}';
    }
}
