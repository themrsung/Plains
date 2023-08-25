package civitas.celestis.graphics;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;

/**
 * A 16-bit color with 16 bits of precision. Colors are stored in their packed
 * 64-bit RGBA format, adding no precision upon the 16-bit RGBA standard.
 * Packed colors are memory efficient in that they do not occupy more space
 * than is absolutely necessary.
 *
 * @see Color
 * @see LinearColor
 * @see RichColor
 */
public class PackedColor implements Color {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    /**
     * Creates a new color.
     *
     * @param rgba64 The 64-bit RGBA representation of this color
     */
    public PackedColor(long rgba64) {
        this.rgba64 = rgba64;
    }

    /**
     * Creates a new color. All components must be within the range of {@code [0, 255]}.
     * The alpha component will be populated with {@code 255}.
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     */
    public PackedColor(float r, float g, float b) {
        this(r, g, b, 255);
    }

    /**
     * Creates a new color. All components must be within the range of {@code [0, 255]}.
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     * @param a The alpha component of this color
     */
    public PackedColor(float r, float g, float b, float a) {

        /*
         * There is no need to validate the range of [0, 255] as any outliers
         * will be ignored and converted into RGBA format regardless of their range.
         */

        this.rgba64 = Colors.pack64(r, g, b, a);
    }

    /**
     * Creates a new color.
     *
     * @param c The color of which to copy component values from
     */
    public PackedColor(@Nonnull Color c) {
        this.rgba64 = c.rgba64();
    }

    //
    // Variables
    //

    /**
     * The RGBA 64-bit representation of this color.
     */
    protected final long rgba64;

    //
    // RGBA
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float red() {
        return Colors.unpack64R(rgba64);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float green() {
        return Colors.unpack64G(rgba64);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float blue() {
        return Colors.unpack64B(rgba64);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float alpha() {
        return Colors.unpack64A(rgba64);
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
    public int argb32() {
        return Colors.convert64to32(rgba64);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public long rgba64() {
        return rgba64;
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
        if (!(obj instanceof Color c)) return false;
        return rgba64 == c.rgba64();
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return Colors.toString(this);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toReadableString() {
        return Colors.toReadableString(this);
    }
}
