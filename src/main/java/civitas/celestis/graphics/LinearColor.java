package civitas.celestis.graphics;

import civitas.celestis.math.Vector;
import civitas.celestis.math.Vector4;
import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.awt.*;
import java.io.Serial;

/**
 * An 8-bit color represented by 32 bits of precision. The RGBA components are stored
 * individually, making arithmetic operations such as linear interpolation (LERP)
 * faster than the more compact {@link SimpleColor}.
 *
 * @see Color8
 * @see SimpleColor
 */
public class LinearColor implements Color8 {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 10L;

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
     * Creates a new linear color. The alpha component will be populated with {@code 255}.
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     */
    public LinearColor(float r, float g, float b) {
        this(r, g, b, 255);
    }

    /**
     * Creates a new linear color.
     *
     * @param r The red component of this color
     * @param g The green component of this color
     * @param b The blue component of this color
     * @param a The alpha component of this color
     */
    public LinearColor(float r, float g, float b, float a) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
    }

    /**
     * Creates a new linear color.
     *
     * @param c The color of which to copy component values from
     */
    public LinearColor(@Nonnull Color8 c) {
        this.red = c.red();
        this.green = c.green();
        this.blue = c.blue();
        this.alpha = c.alpha();
    }

    /**
     * Creates a new linear color. Components will be copied in RGBA order.
     *
     * @param t The tuple of which to copy component values from
     */
    public LinearColor(@Nonnull Tuple<? extends Number> t) {
        if (t.size() != 4) {
            throw new IllegalArgumentException("The provided tuple's size is not 4.");
        }

        this.red = t.get(0).floatValue();
        this.green = t.get(1).floatValue();
        this.blue = t.get(2).floatValue();
        this.alpha = t.get(3).floatValue();
    }

    /**
     * Creates a new linear color. Components will be copied in RGBA order.
     *
     * @param v The vector of which to copy component values from
     */
    public LinearColor(@Nonnull Vector<?> v) {
        if (v.dimensions() != 4) {
            throw new IllegalArgumentException("The provided vector does not have four components.");
        }

        this.red = (float) v.get(0);
        this.green = (float) v.get(1);
        this.blue = (float) v.get(2);
        this.alpha = (float) v.get(3);
    }

    /**
     * Creates a new linear color. Components will be copied in RGBA order.
     *
     * @param v The vector of which to copy component values from
     */
    public LinearColor(@Nonnull Vector4 v) {
        this.red = (float) v.w();
        this.green = (float) v.x();
        this.blue = (float) v.y();
        this.alpha = (float) v.z();
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
    public int red() {
        return (int) red;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int green() {
        return (int) green;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int blue() {
        return (int) blue;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int alpha() {
        return (int) alpha;
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
        return Color8.pack24((int) red, (int) green, (int) blue);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int pack32() {
        return Color8.pack32((int) red, (int) green, (int) blue, (int) alpha);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public long pack64() {
        return Color8.pack64((int) red, (int) green, (int) blue, (int) alpha);
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
        return new Color(pack32(), true);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Color awt24() {
        return new Color(pack24());
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
    public Color8 inverse() {
        return new LinearColor(255 - red, 255 - green, 255 - blue, alpha);
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
        if (!(obj instanceof Color8 c)) return false;
        return pack32() == c.pack32();
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return "LinearColor{" +
                "r=" + red +
                ", g=" + green +
                ", b=" + blue +
                ", a=" + alpha +
                '}';
    }
}
