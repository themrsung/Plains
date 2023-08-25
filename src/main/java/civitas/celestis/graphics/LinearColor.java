package civitas.celestis.graphics;

import civitas.celestis.math.Scalars;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;

/**
 * A 16-bit color with 32 bits of precision. Colors are stored by their individual
 * component values in the format of {@code float}. Linear colors are less efficient
 * compared to packed color variants, but offer incredibly smooth linear transition
 * capabilities, which may be more important than conserving memory in some instances.
 *
 * @see Color
 * @see PackedColor
 * @see RichColor
 */
public class LinearColor implements Color {
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
    public LinearColor(long rgba64) {

        /*
         * Range validation not required here.
         * Colors.unpack64() cannot return an invalid value.
         */

        this.red = Colors.unpack64R(rgba64);
        this.green = Colors.unpack64G(rgba64);
        this.blue = Colors.unpack64B(rgba64);
        this.alpha = Colors.unpack64A(rgba64);
    }

    /**
     * Creates a new color. All components must be within the range of {@code [0, 255]}.
     * The alpha component will be populated with {@code 255}.
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     */
    public LinearColor(float r, float g, float b) {
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
    public LinearColor(float r, float g, float b, float a) {
        this.red = Scalars.requireRange(r, 0, 255);
        this.green = Scalars.requireRange(g, 0, 255);
        this.blue = Scalars.requireRange(b, 0, 255);
        this.alpha = Scalars.requireRange(a, 0, 255);
    }

    /**
     * Creates a new color.
     *
     * @param c The color of which to copy component values from
     */
    public LinearColor(@Nonnull Color c) {
        this.red = c.red();
        this.green = c.green();
        this.blue = c.blue();
        this.alpha = c.alpha();
    }

    //
    // Variables
    //

    /**
     * The red component of this color.
     */
    protected final float red;

    /**
     * The green component of this color.
     */
    protected final float green;

    /**
     * The blue component of this color.
     */
    protected final float blue;

    /**
     * The alpha component of this color.
     */
    protected final float alpha;

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
        return red;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float green() {
        return green;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float blue() {
        return blue;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float alpha() {
        return alpha;
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
        return Colors.pack32((int) red, (int) green, (int) blue, (int) alpha);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public long rgba64() {
        return Colors.pack64(red, green, blue, alpha);
    }

    //
    // Inversion
    //

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Color inverse() {
        return new LinearColor(255 - red, 255 - green, 255 - blue, 255 - alpha);
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
        return rgba64() == c.rgba64();
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
