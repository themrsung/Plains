package civitas.celestis.graphics;

import civitas.celestis.util.compression.Packable32;
import civitas.celestis.util.compression.Packable64;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.awt.*;
import java.io.Serializable;

/**
 * An 8-bit RGBA color with a total of 32 significant bits.
 * <p>
 * This interface defines the contract for an 8-bit Plains color class.
 * The RGBA components should be returned in the format of {@code int},
 * and respect the range of {@code 0-255}.
 * </p>
 * <p>
 * Colors can be packed into either 32 bits or 64 bits.
 * </p>
 * <p>
 * The former will effectively round the RGBA components to be discrete
 * integers between {@code 0-255}. (in the same format as an AWT {@link Color})
 * </p>
 * <p>
 * The latter allows for 16 bits of precision for each component.
 * Each part is treated as an unsigned 16 bit integer, allowing it to accurately
 * represent 7-8 digits of decimal places. For the encoding format,
 * check the corresponding subclass's {@link Color8#pack64()} method.
 * </p>
 *
 * @see SimpleColor
 * @see LinearColor
 */
public interface Color8 extends Packable32, Packable64, Serializable {
    //
    // Validation
    //

    /**
     * Checks if given color is a valid color.
     * (the components are within the range of {@code 0-255})
     *
     * @param c The color to validate
     * @return {@code true} if the provided color {@code c} is a valid color
     */
    static boolean isValid(@Nonnull Color8 c) {
        if (c instanceof LinearColor lc) {
            return lc.isValid();
        }

        return true;
    }

    /**
     * Checks if given color is a valid color (the components are within the range of {@code 0-255}),
     * then returns a reference to the color. If the color is invalid,
     * this will throw an {@link IllegalStateException}.
     *
     * @param c The color to validate
     * @return A reference to the provided color {@code c}
     * @throws IllegalStateException When the color is invalid
     */
    @Nonnull
    static Color8 requireValid(@Nonnull Color8 c) throws IllegalStateException {
        if (c instanceof LinearColor lc) {
            return lc.requireValid();
        }

        return c;
    }

    //
    // RGBA
    //

    /**
     * Returns the red component of this color.
     *
     * @return The red component of this color
     */
    int red();

    /**
     * Returns the green component of this color.
     *
     * @return The green component of this color
     */
    int green();

    /**
     * Returns the blue component of this color.
     *
     * @return The blue component of this color
     */
    int blue();

    /**
     * Returns the alpha component of this color.
     *
     * @return The alpha component of this color
     */
    int alpha();

    //
    // Packing
    //

    /**
     * Packs this color into 24 bits. The alpha component is lost.
     * The return value of this method should be in the following format.
     * <p>
     * Bits 16-23 represent the red component of this color as an unsigned {@code byte}.<br>
     * Bits 8-15 represent the green component of this color as an unsigned {@code byte}.<br>
     * Bits 0-7 represent the blue component of this color as an unsigned {@code byte}.
     * </p>
     *
     * @return The 24 bit representation of this color
     * @see Color8#pack24(int, int, int)
     */
    int pack24();

    /**
     * Packs this color into 32 bits.
     * The return value of this method should be in the following format.
     * <p>
     * Bits 24-31 represent the alpha component of this color as an unsigned {@code byte}.<br>
     * Bits 16-23 represent the red component of this color as an unsigned {@code byte}.<br>
     * Bits 8-15 represent the green component of this color as an unsigned {@code byte}.<br>
     * Bits 0-7 represent the blue component of this color as an unsigned {@code byte}.
     * </p>
     * This is the same format that the AWT {@link Color} class uses.
     *
     * @return The 32 bit representation of this color
     * @see Color8#pack32(int, int, int, int)
     */
    @Override
    int pack32();

    /**
     * Packs this color into 64 bits.
     * The return value of this method should be in the following format.
     * <p>
     * Bits 48-65 represent the red component of this color.<br>
     * Bits 32-47 represent the green component of this color.<br>
     * Bits 16-31 represent the blue component of this color.<br>
     * Bits 0-15 represent the alpha component of this color.
     * </p>
     *
     * @return The 64 bit representation of this color
     * @see Color8#pack64(float, float, float, float)
     */
    @Override
    long pack64();

    /**
     * Packs the provided RGB components into 24 bits.
     *
     * @param r The red component to pack
     * @param g The green component to pack
     * @param b The blue component to pack
     * @return The packed color
     */
    static int pack24(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    /**
     * Unpacks a 24-bit representation of a color.
     *
     * @param rgb24 The 24-bit representation to unpack
     * @return The unpacked components in RGB order
     */
    @Nonnull
    static int[] unpack24(int rgb24) {
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
    static int pack32(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Unpacks a 32-bit representation of a color.
     *
     * @param rgba32 The 32-bit representation to unpack
     * @return The unpacked components in RGBA order
     */
    @Nonnull
    static int[] unpack32(int rgba32) {
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
    static int unpack32R(int rgba32) {
        return (rgba32 >> 16) & 0xFF;
    }

    /**
     * Unpacks the green component from a 32-bit representation.
     *
     * @param rgba32 The 32-bit representation to unpack
     * @return The extracted green component value
     */
    static int unpack32G(int rgba32) {
        return (rgba32 >> 8) & 0xFF;
    }

    /**
     * Unpacks the blue component from a 32-bit representation.
     *
     * @param rgba32 The 32-bit representation to unpack
     * @return The extracted blue component value
     */
    static int unpack32B(int rgba32) {
        return rgba32 & 0xFF;
    }

    /**
     * Unpacks the alpha component from a 32-bit representation.
     *
     * @param rgba32 The 32-bit representation to unpack
     * @return The extracted alpha component value
     */
    static int unpack32A(int rgba32) {
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
    static long pack64(float r, float g, float b, float a) {
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
    static float[] unpack64(long rgba64) {
        return new float[]{
                ((rgba64 >> R_COMPONENT_SHIFT) & 0xFFFF) * COMPONENT_UNPACK_FACTOR,
                ((rgba64 >> G_COMPONENT_SHIFT) & 0xFFFF) * COMPONENT_UNPACK_FACTOR,
                ((rgba64 >> B_COMPONENT_SHIFT) & 0xFFFF) * COMPONENT_UNPACK_FACTOR,
                (rgba64 & 0xFFFF) * COMPONENT_UNPACK_FACTOR
        };
    }

    /**
     * The red component shift for 64-bit representation.
     */
    long R_COMPONENT_SHIFT = 48;

    /**
     * The green component shift for 64-bit representation.
     */
    long G_COMPONENT_SHIFT = 32;

    /**
     * The blue component shift for 64-bit representation.
     */
    long B_COMPONENT_SHIFT = 16;

    /**
     * The component scale factor to apply before packing.
     */
    float COMPONENT_PACK_FACTOR = (float) (Math.pow(2, 16) - 1) / 255;

    /**
     * The inverse of {@link Color8#COMPONENT_PACK_FACTOR}.
     */
    float COMPONENT_UNPACK_FACTOR = 1 / COMPONENT_PACK_FACTOR;

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
    static int convert32to24(int rgba32) {
        return (((rgba32 >> 16) & 0xFF) << 16) | (((rgba32 >> 8) & 0xFF) << 8) | rgba32 & 0xFF;
    }

    /**
     * Converts a 24-bit representation to a 32-bit representation.
     * The alpha component is populated with {@code 255}. (opaque)
     *
     * @param rgb24 The 24-bit RGB representation
     * @return The 32-bit RGBA representation
     */
    static int convert24to32(int rgb24) {
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
    static int convert24to32(int rgb24, int a) {
        return (a << 24) | (rgb24 & 0xFFFFFF);
    }

    /**
     * Converts a 32-bit representation to a 64-bit representation.
     *
     * @param rgba32 The 32-bit RGBA representation
     * @return The 64-bit RGBA representation
     */
    static long convert32to64(int rgba32) {
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
    static int convert64to32(long rgba64) {
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
    static long convert24to64(int rgb24) {
        final int[] unpacked = unpack24(rgb24);
        return pack64(unpacked[0], unpacked[1], unpacked[2], 255);
    }

    /**
     * Converts a 64-bit RGBA representation to a 24-bit RGB representation.
     * The alpha component is lost.
     *
     * @param rgba64 The 64-bit RGBA representation
     * @return The 24-bit RGB representation
     */
    static int convert64to24(int rgba64) {
        final float[] unpacked = unpack64(rgba64);
        return pack24((int) unpacked[0], (int) unpacked[1], (int) unpacked[2]);
    }

    //
    // Conversion
    //

    /**
     * Converts an AWT {@link Color} into a {@link Color8}.
     *
     * @param awt The AWT color to convert
     * @return The converted {@link Color8}
     */
    @Nonnull
    static Color8 fromAWT(@Nonnull Color awt) {
        return SimpleColor.from24(awt.getRGB(), awt.getAlpha());
    }

    /**
     * Converts this color into an AWT {@link Color}.
     * This packs the color into 32 bits, preserving the alpha component.
     *
     * @return The AWT representation of this color
     */
    @Nonnull
    Color awt32();

    /**
     * Converts this color into an AWT {@link Color}.
     * This packs the color into 24 bits, dropping the alpha component.
     * The alpha value will be {@code 255}. (opaque)
     *
     * @return The AWT representation of this color
     */
    @Nonnull
    Color awt24();

    //
    // Equality
    //

    /**
     * Checks for equality between this color and the provided object {@code obj}.
     * This compares the 32-bit RGBA representation of the colors, and any precision
     * beyond the representable range will be ignored.
     *
     * @param obj The object to compare to
     * @return {@code true} if the object is also a color, and the two colors represent
     * the same color (the 32-bit RGBA representation is equal)
     */
    boolean equals(@Nullable Object obj);

    //
    // Serialization
    //

    /**
     * Serializes this color into a string.
     *
     * @return The string representation of this color
     */
    @Nonnull
    String toString();
}
