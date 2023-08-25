package civitas.celestis.graphics;

import civitas.celestis.math.vector.Vector4;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Contains utility methods related to {@link Color}s.
 */
public final class Colors {
    //
    // Alternate Notation
    //

    /**
     * Creates a new color from a 24-bit RGB hex notation. ({@code "#RRGGBB"} or {@code "RRGGBB"})
     *
     * @param rgb24 The 24-bit RGB hex notation of the color
     * @return The converted color
     */
    @Nonnull
    public static Color fromHexString(@Nonnull String rgb24) {
        return new PackedColor(convert24to64(
                Integer.parseInt(rgb24.replaceAll("#", ""), 16)));
    }

    /**
     * Creates a new color from a 32-bit ARGB notation.
     *
     * @param argb32 The 32-bit ARGB representation of the color
     * @return The converted color
     */
    @Nonnull
    public static Color fromARGB32(int argb32) {
        return new PackedColor(convert32to64(argb32));
    }

    /**
     * Creates a new color from a 24-bit RGB representation.
     * The alpha component will be populated with {@code 255}. (opaque)
     *
     * @param rgb24 The 24-bit RGB representation of the color
     * @return The converted color
     */
    @Nonnull
    public static Color fromRGB24(int rgb24) {
        return new PackedColor(convert24to64(rgb24));
    }

    /**
     * Creates a new color from a 24-bit RGBA representation.
     *
     * @param rgb24 The 24-bit RGB representation of the color
     * @param alpha The alpha component of the color ({@code [0, 255]})
     * @return The converted color
     */
    @Nonnull
    public static Color fromRGB24(int rgb24, float alpha) {
        return new PackedColor(convert24to64(rgb24, alpha));
    }

    /**
     * Creates a new color from a 10-bit RGBA representation.
     * All components must be within the range of {@code [0, 1023]}.
     *
     * @param r The red component of the color
     * @param g The green component of the color
     * @param b The blue component of the color
     * @param a The alpha component of the color
     * @return The converted color
     */
    @Nonnull
    public static Color fromRGBA10(int r, int g, int b, int a) {
        final float red = (float) r * (255f / 1023f);
        final float green = (float) g * (255f / 1023f);
        final float blue = (float) b * (255f / 1023f);
        final float alpha = (float) a * (255f / 1023f);

        return new PackedColor(red, green, blue, alpha);
    }

    //
    // Linear Interpolation (LERP)
    //

    /**
     * Performs linear interpolation between the starting color {@code s} and the ending color {@code e}.
     * The interpolation parameter {@code t} determines the amount of interpolation.
     *
     * @param s The starting color of which to interpolate from
     * @param e The ending color of which to interpolate towards
     * @param t The interpolation parameter ({@code [0, 1]})
     * @return The interpolated color
     */
    @Nonnull
    public static Color lerp(@Nonnull Color s, @Nonnull Color e, float t) {
        final float r1 = s.red();
        final float g1 = s.green();
        final float b1 = s.blue();
        final float a1 = s.alpha();

        final float r2 = e.red();
        final float g2 = e.green();
        final float b2 = e.blue();
        final float a2 = e.alpha();

        return new LinearColor(
                r1 + (r2 - r1) * t,
                g1 + (g2 - g1) * t,
                b1 + (b2 - b1) * t,
                a1 + (a2 - a1) * t
        );
    }

    //
    // Vector Notation
    //

    /**
     * Creates a color from a four-dimensional vector. The WXYZ components are mapped
     * to the RGBA components respectively, and they must be within the range of {@code [0, 255]}.
     *
     * @param v The vector of which to convert to color form
     * @return The converted color
     */
    @Nonnull
    public static Color fromVector(@Nonnull Vector4 v) {
        return new RichColor(v.w(), v.x(), v.y(), v.z());
    }

    /**
     * Converts a color into a four-dimensional vector for enhanced arithmetic.
     *
     * @param c The color of which to convert to a vector form
     * @return The vector representation of the provided color {@code c}
     */
    @Nonnull
    public static Vector4 toVector(@Nonnull Color c) {
        return new Vector4(c.red(), c.green(), c.blue(), c.alpha());
    }

    /**
     * Converts a color into a four-dimensional vector for enhanced arithmetic.
     *
     * @param c The color of which to convert to a vector form
     * @return The vector representation of the provided color {@code c}
     */
    @Nonnull
    public static Vector4 toVector(@Nonnull RichColor c) {
        return new Vector4(c.red, c.green, c.blue, c.alpha);
    }

    //
    // Tuple Notation
    //

    /**
     * Creates a color from a numeric tuple. The components are mapped in 0-1-2-3 order
     * into RGBA respectively. The tuple must have a size of {@code 4}, and the components
     * must be within the range of {@code [0, 255]}.
     *
     * @param t The tuple of which to convert to color form
     * @return THe converted color
     */
    @Nonnull
    public static Color fromTuple(@Nonnull Tuple<? extends Number> t) {
        if (t.size() != 4) {
            throw new IllegalArgumentException("The provided tuple's size is not 4.");
        }

        return new RichColor(
                t.get(0).doubleValue(),
                t.get(1).doubleValue(),
                t.get(2).doubleValue(),
                t.get(3).doubleValue()
        );
    }

    //
    // Packing
    //

    /**
     * The red component shift for 64-bit representation.
     */
    private static final long R_COMPONENT_SHIFT = 48;

    /**
     * The green component shift for 64-bit representation.
     */
    private static final long G_COMPONENT_SHIFT = 32;

    /**
     * The blue component shift for 64-bit representation.
     */
    private static final long B_COMPONENT_SHIFT = 16;

    /**
     * The component scale factor to apply before packing.
     */
    private static final float COMPONENT_PACK_FACTOR = (float) (Math.pow(2, 16) - 1) / 255;

    /**
     * The inverse of {@link Colors#COMPONENT_PACK_FACTOR}.
     */
    private static final float COMPONENT_UNPACK_FACTOR = 1 / COMPONENT_PACK_FACTOR;

    /**
     * Packs the provided RGB components into 24 bits.
     *
     * @param r The red component to pack
     * @param g The green component to pack
     * @param b The blue component to pack
     * @return The packed color
     */
    public static int pack24(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    /**
     * Unpacks a 24-bit representation of a color.
     *
     * @param rgb24 The 24-bit representation to unpack
     * @return The unpacked components in RGB order
     */
    @Nonnull
    public static int[] unpack24(int rgb24) {
        int[] components = new int[3];

        components[0] = (rgb24 >> 16) & 0xFF; // Red component
        components[1] = (rgb24 >> 8) & 0xFF;  // Green component
        components[2] = rgb24 & 0xFF;         // Blue component

        return components;
    }

    /**
     * Packs the provided RGBA components into 32 bits.
     *
     * @param r The red component to pack
     * @param g The green component to pack
     * @param b The blue component to pack
     * @param a The alpha component to pack
     * @return The packed color
     */
    public static int pack32(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Unpacks a 32-bit representation of a color.
     *
     * @param rgba32 The 32-bit representation to unpack
     * @return The unpacked components in RGBA order
     */
    @Nonnull
    public static int[] unpack32(int rgba32) {
        int[] components = new int[4];

        components[3] = (rgba32 >> 24) & 0xFF; // Alpha component
        components[0] = (rgba32 >> 16) & 0xFF; // Red component
        components[1] = (rgba32 >> 8) & 0xFF;  // Green component
        components[2] = rgba32 & 0xFF;         // Blue component

        return components;
    }

    /**
     * Unpacks the red component from a 32-bit representation.
     *
     * @param rgba32 The 32-bit representation to unpack
     * @return The extracted red component value
     */
    public static int unpack32R(int rgba32) {
        return (rgba32 >> 16) & 0xFF;
    }

    /**
     * Unpacks the green component from a 32-bit representation.
     *
     * @param rgba32 The 32-bit representation to unpack
     * @return The extracted green component value
     */
    public static int unpack32G(int rgba32) {
        return (rgba32 >> 8) & 0xFF;
    }

    /**
     * Unpacks the blue component from a 32-bit representation.
     *
     * @param rgba32 The 32-bit representation to unpack
     * @return The extracted blue component value
     */
    public static int unpack32B(int rgba32) {
        return rgba32 & 0xFF;
    }

    /**
     * Unpacks the alpha component from a 32-bit representation.
     *
     * @param rgba32 The 32-bit representation to unpack
     * @return The extracted alpha component value
     */
    public static int unpack32A(int rgba32) {
        return (rgba32 >> 24) & 0xFF;
    }

    /**
     * Packs the provided RGBA components into 64 bits.
     *
     * @param r The red component to pack
     * @param g The green component to pack
     * @param b The blue component to pack
     * @param a The alpha component to pack
     * @return The packed color
     */
    public static long pack64(float r, float g, float b, float a) {
        return (((long) (r * COMPONENT_PACK_FACTOR)) << R_COMPONENT_SHIFT)
                | (((long) (g * COMPONENT_PACK_FACTOR)) << G_COMPONENT_SHIFT)
                | (((long) (b * COMPONENT_PACK_FACTOR)) << B_COMPONENT_SHIFT)
                | ((long) (a * COMPONENT_PACK_FACTOR));
    }

    /**
     * Unpacks a 64-bit representation of a color.
     *
     * @param rgba64 The 64-bit representation to unpack
     * @return The unpacked components in RGBA order
     */
    @Nonnull
    public static float[] unpack64(long rgba64) {
        return new float[]{
                ((rgba64 >> R_COMPONENT_SHIFT) & 0xFFFF) * COMPONENT_UNPACK_FACTOR,
                ((rgba64 >> G_COMPONENT_SHIFT) & 0xFFFF) * COMPONENT_UNPACK_FACTOR,
                ((rgba64 >> B_COMPONENT_SHIFT) & 0xFFFF) * COMPONENT_UNPACK_FACTOR,
                (rgba64 & 0xFFFF) * COMPONENT_UNPACK_FACTOR
        };
    }

    /**
     * Unpacks only the red component from a 64-bit representation.
     *
     * @param rgba64 The 64-bit representation to unpack
     * @return The extracted red component value
     */
    public static float unpack64R(long rgba64) {
        return ((rgba64 >> R_COMPONENT_SHIFT) & 0xFFFF) * COMPONENT_UNPACK_FACTOR;
    }

    /**
     * Unpacks only the green component from a 64-bit representation.
     *
     * @param rgba64 The 64-bit representation to unpack
     * @return The extracted green component value
     */
    public static float unpack64G(long rgba64) {
        return ((rgba64 >> G_COMPONENT_SHIFT) & 0xFFFF) * COMPONENT_UNPACK_FACTOR;
    }

    /**
     * Unpacks only the blue component from a 64-bit representation.
     *
     * @param rgba64 The 64-bit representation to unpack
     * @return The extracted blue component value
     */
    public static float unpack64B(long rgba64) {
        return ((rgba64 >> B_COMPONENT_SHIFT) & 0xFFFF) * COMPONENT_UNPACK_FACTOR;
    }

    /**
     * Unpacks only the alpha component from a 64-bit representation.
     *
     * @param rgba64 The 64-bit representation to unpack
     * @return The extracted alpha component value
     */
    public static float unpack64A(long rgba64) {
        return (rgba64 & 0xFFFF) * COMPONENT_UNPACK_FACTOR;
    }

    //
    // Conversion
    //

    /**
     * Converts a 32-bit representation to a 24-bit representation.
     * The alpha component is lost.
     *
     * @param rgba32 The 32-bit RGBA representation
     * @return The 24-bit RGB representation
     */
    public static int convert32to24(int rgba32) {
        return (((rgba32 >> 16) & 0xFF) << 16) | (((rgba32 >> 8) & 0xFF) << 8) | rgba32 & 0xFF;
    }

    /**
     * Converts a 24-bit representation to a 32-bit representation.
     * The alpha component is populated with {@code 255}. (opaque)
     *
     * @param rgb24 The 24-bit RGB representation
     * @return The 32-bit RGBA representation
     */
    public static int convert24to32(int rgb24) {
        return (0xFF << 24) | (rgb24 & 0xFFFFFF);
    }

    /**
     * Converts a 24-bit representation to a 32-bit representation.
     * The alpha component is populated with the provided value {@code a}.
     *
     * @param rgb24 The 24-bit RGB representation
     * @param a     The alpha component to pack
     * @return The 32-bit RGBA representation
     */
    public static int convert24to32(int rgb24, int a) {
        return (a << 24) | (rgb24 & 0xFFFFFF);
    }

    /**
     * Converts a 32-bit representation to a 64-bit representation.
     *
     * @param rgba32 The 32-bit RGBA representation
     * @return The 64-bit RGBA representation
     */
    public static long convert32to64(int rgba32) {
        final float r = unpack32R(rgba32);
        final float g = unpack32G(rgba32);
        final float b = unpack32B(rgba32);
        final float a = unpack32A(rgba32);

        return pack64(r, g, b, a);
    }

    /**
     * Converts a 64-bit representation to a 32-bit representation.
     * Half of the precision is lost.
     *
     * @param rgba64 The 64-bit RGBA representation
     * @return The 32-bit RGB representation
     */
    public static int convert64to32(long rgba64) {
        final float[] unpacked = unpack64(rgba64);
        return pack32((int) unpacked[0], (int) unpacked[1], (int) unpacked[2], (int) unpacked[3]);
    }

    /**
     * Converts a 24-bit RGB representation to a 64-bit RGBA representation.
     * The alpha component is populated with {@code 255}. (opaque)
     *
     * @param rgb24 The 24-bit RGB representation
     * @return The 64-bit RGBA representation
     */
    public static long convert24to64(int rgb24) {
        final int[] unpacked = unpack24(rgb24);
        return pack64(unpacked[0], unpacked[1], unpacked[2], 255);
    }

    /**
     * Converts a 24-bit RGB representation to a 64-bit RGBA representation.
     *
     * @param rgb24 The 24-bit RGB representation
     * @param alpha The alpha component to use
     * @return The 64-bit RGBA representation
     */
    public static long convert24to64(int rgb24, float alpha) {
        final int[] unpacked = unpack24(rgb24);
        return pack64(unpacked[0], unpacked[1], unpacked[2], alpha);
    }

    /**
     * Converts a 64-bit RGBA representation to a 24-bit RGB representation.
     * The alpha component is lost.
     *
     * @param rgba64 The 64-bit RGBA representation
     * @return The 24-bit RGB representation
     */
    public static int convert64to24(int rgba64) {
        final float[] unpacked = unpack64(rgba64);
        return pack24((int) unpacked[0], (int) unpacked[1], (int) unpacked[2]);
    }

    //
    // Serialization
    //

    /**
     * Serializes a color into a string.
     *
     * @param color The color of which to serialize
     * @return The string representation of the provided color
     */
    @Nonnull
    public static String toString(@Nullable Color color) {
        if (color == null) return "null";

        /*
         * DO NOT CALL Color.toString() ANYWHERE IN THIS METHOD.
         * IT WILL RESULT IN AN INFINITE LOOP.
         */

        return Long.toString(color.rgba64());
    }

    /**
     * Serializes a color into a human-readable string.
     *
     * @param color The color of which to serialize
     * @return The human-readable string representation of the provided color
     */
    @Nonnull
    public static String toReadableString(@Nullable Color color) {
        if (color == null) return "null";

        /*
         * DO NOT CALL Color.toReadableString() ANYWHERE IN THIS METHOD.
         * IT WILL RESULT IN AN INFINITE LOOP.
         */

        return "Color" + "{" +
                "r=" + color.red() + ", " +
                "g=" + color.green() + ", " +
                "b=" + color.blue() + ", " +
                "a=" + color.alpha() +
                "}";
    }
}
