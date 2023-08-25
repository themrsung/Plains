package civitas.celestis.graphics;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;

/**
 * A 16-bit RGBA color. While the individual components have the same range
 * as an 8-bit color, ({@code [0, 255]}) the individual components are each represented
 * with 16 bits, adding a substantial amount of precision.
 * <p>
 * 16-bit RGBA colors can be packed into a single {@code long} for storage. ({@link #rgba64()})
 * They can also be converted into 8-bit ARGB form to ensure compatibility with hardware
 * or other graphics libraries. ({@link #argb32()})
 * </p>
 *
 * @see PackedColor
 * @see LinearColor
 * @see RichColor
 */
public interface Color extends Serializable {
    //
    // Factory
    //

    /**
     * Creates a new color from a 64-bit RGBA representation.
     * @param rgba64 The 64-bit RGBA representation of the color
     * @return The constructed color instance
     */
    @Nonnull
    static Color of(long rgba64) {
        return new PackedColor(rgba64);
    }

    /**
     * Creates a new color from individual RGBA components. All components must be
     * within the range of {@code [0, 255]}.
     *
     * @param r The red component of the color
     * @param g The green component of the color
     * @param b The blue component of the color
     * @param a The alpha component of the color
     * @return The constructed color instance
     */
    @Nonnull
    static Color of(float r, float g, float b, float a) {
        return new LinearColor(r, g, b, a);
    }

    //
    // Constants
    //

    /*
     * RGB Primary Colors
     */

    /**
     * The color white.
     */
    Color WHITE = new PackedColor(255, 255, 255);

    /**
     * The color red.
     */
    Color RED = new PackedColor(255, 0, 0);

    /**
     * The color green.
     */
    Color GREEN = new PackedColor(0, 255, 0);

    /**
     * The color blue.
     */
    Color BLUE = new PackedColor(0, 0, 255);

    /**
     * The color black.
     */
    Color BLACK = new PackedColor(0, 0, 0);

    /**
     * The color cyan.
     */
    Color CYAN = new PackedColor(0, 255, 255);

    /**
     * The color magenta.
     */
    Color MAGENTA = new PackedColor(255, 0, 255);

    /**
     * The color yellow.
     */
    Color YELLOW = new PackedColor(255, 255, 0);

    /*
     * RGBA Half Colors
     */

    /**
     * The color gray.
     */
    Color GRAY = new PackedColor(128, 128, 128);

    /**
     * The color maroon.
     */
    Color MAROON = new PackedColor(128, 0, 0);

    /**
     * The color dark green.
     */
    Color DARK_GREEN = new PackedColor(0, 128, 0);

    /**
     * The color navy.
     */
    Color NAVY = new PackedColor(0, 0, 128);

    /**
     * The color olive.
     */
    Color OLIVE = new PackedColor(128, 128, 0);

    /**
     * The color purple.
     */
    Color PURPLE = new PackedColor(128, 0, 128);

    /*
     * Decorative Colors
     */

    Color SILVER = new PackedColor(192, 192, 192);
    Color BROWN = new PackedColor(165, 42, 42);
    Color FIREBRICK = new PackedColor(178, 34, 34);
    Color CRIMSON = new PackedColor(220, 20, 60);
    Color TOMATO = new PackedColor(255, 99, 71);
    Color CORAL = new PackedColor(255, 127, 80);
    Color INDIAN_RED = new PackedColor(205, 92, 92);
    Color LIGHT_CORAL = new PackedColor(240, 128, 128);
    Color DARK_SALMON = new PackedColor(233, 150, 122);
    Color SALMON = new PackedColor(250, 128, 144);
    Color LIGHT_SALMON = new PackedColor(255, 160, 122);
    Color ORANGE_RED = new PackedColor(255, 69, 0);
    Color DARK_ORANGE = new PackedColor(255, 140, 0);
    Color ORANGE = new PackedColor(255, 165, 0);
    Color GOLD = new PackedColor(255, 215, 0);
    Color DARK_GOLDEN_ROD = new PackedColor(184, 134, 11);
    Color GOLDEN_ROD = new PackedColor(218, 165, 32);
    Color PALE_GOLDEN_ROD = new PackedColor(238, 232, 170);
    Color DARK_KHAKI = new PackedColor(189, 183, 107);
    Color KHAKI = new PackedColor(240, 230, 140);
    Color YELLOW_GREEN = new PackedColor(154, 205, 50);
    Color DARK_OLIVE_GREEN = new PackedColor(85, 107, 47);
    Color OLIVE_DRAB = new PackedColor(107, 142, 35);
    Color LAWN_GREEN = new PackedColor(124, 252, 0);
    Color CHARTREUSE = new PackedColor(127, 255, 0);
    Color GREEN_YELLOW = new PackedColor(173, 255, 47);
    Color FOREST_GREEN = new PackedColor(34, 139, 34);
    Color LIME = new PackedColor(0, 255, 0);
    Color LIME_GREEN = new PackedColor(50, 205, 50);
    Color LIGHT_GREEN = new PackedColor(144, 238, 144);
    Color PALE_GREEN = new PackedColor(152, 251, 152);
    Color DARK_SEA_GREEN = new PackedColor(143, 188, 143);
    Color MEDIUM_SPRING_GREEN = new PackedColor(0, 250, 154);
    Color SPRING_GREEN = new PackedColor(0, 255, 127);
    Color SEA_GREEN = new PackedColor(46, 139, 87);
    Color MEDIUM_AQUA_MARINE = new PackedColor(102, 205, 170);
    Color MEDIUM_SEA_GREEN = new PackedColor(60, 179, 113);
    Color LIGHT_SEA_GREEN = new PackedColor(32, 178, 170);
    Color DARK_SLATE_GRAY = new PackedColor(47, 79, 79);
    Color TEAL = new PackedColor(0, 128, 128);
    Color DARK_CYAN = new PackedColor(0, 139, 139);
    Color AQUA = new PackedColor(0, 255, 255);
    Color LIGHT_CYAN = new PackedColor(224, 255, 255);
    Color DARK_TURQUOISE = new PackedColor(0, 206, 209);
    Color TURQUOISE = new PackedColor(64, 224, 208);
    Color MEDIUM_TURQUOISE = new PackedColor(72, 209, 204);
    Color PALE_TURQUOISE = new PackedColor(175, 238, 238);
    Color AQUA_MARINE = new PackedColor(127, 255, 212);
    Color POWDER_BLUE = new PackedColor(176, 224, 230);
    Color CADET_BLUE = new PackedColor(95, 158, 160);
    Color STEEL_BLUE = new PackedColor(70, 130, 180);
    Color CORN_FLOWER_BLUE = new PackedColor(100, 149, 237);
    Color DEEP_SKY_BLUE = new PackedColor(0, 191, 255);
    Color DODGER_BLUE = new PackedColor(30, 144, 255);
    Color LIGHT_BLUE = new PackedColor(173, 216, 230);
    Color SKY_BLUE = new PackedColor(135, 206, 235);
    Color LIGHT_SKY_BLUE = new PackedColor(135, 206, 250);
    Color MIDNIGHT_BLUE = new PackedColor(25, 25, 112);
    Color DARK_BLUE = new PackedColor(0, 0, 139);
    Color MEDIUM_BLUE = new PackedColor(0, 0, 205);
    Color ROYAL_BLUE = new PackedColor(65, 105, 225);
    Color BLUE_VIOLET = new PackedColor(138, 43, 226);
    Color INDIGO = new PackedColor(75, 0, 130);
    Color DARK_SLATE_BLUE = new PackedColor(72, 61, 139);
    Color SLATE_BLUE = new PackedColor(106, 90, 205);
    Color MEDIUM_SLATE_BLUE = new PackedColor(123, 104, 238);
    Color MEDIUM_PURPLE = new PackedColor(147, 112, 219);
    Color DARK_MAGENTA = new PackedColor(139, 0, 139);
    Color DARK_VIOLET = new PackedColor(148, 0, 211);
    Color DARK_ORCHID = new PackedColor(153, 50, 204);
    Color MEDIUM_ORCHID = new PackedColor(186, 85, 211);
    Color THISTLE = new PackedColor(216, 191, 216);
    Color PLUM = new PackedColor(221, 160, 221);
    Color VIOLET = new PackedColor(238, 130, 238);
    Color MAGENTA_FUCHSIA = new PackedColor(255, 0, 255);
    Color ORCHID = new PackedColor(218, 112, 214);
    Color MEDIUM_VIOLET_RED = new PackedColor(199, 21, 133);
    Color PALE_VIOLET_RED = new PackedColor(219, 112, 147);
    Color DEEP_PINK = new PackedColor(255, 20, 147);
    Color HOT_PINK = new PackedColor(255, 105, 180);
    Color LIGHT_PINK = new PackedColor(255, 182, 193);
    Color PINK = new PackedColor(255, 192, 203);
    Color ANTIQUE_WHITE = new PackedColor(250, 235, 215);
    Color BEIGE = new PackedColor(245, 245, 220);
    Color BISQUE = new PackedColor(255, 228, 196);
    Color BLANCHED_ALMOND = new PackedColor(255, 235, 205);
    Color WHEAT = new PackedColor(245, 222, 179);
    Color CORN_SILK = new PackedColor(255, 248, 220);
    Color LEMON_CHIFFON = new PackedColor(255, 250, 205);
    Color LIGHT_GOLDEN_ROD_YELLOW = new PackedColor(250, 250, 210);
    Color LIGHT_YELLOW = new PackedColor(255, 255, 224);
    Color SADDLE_BROWN = new PackedColor(139, 69, 19);
    Color SIENNA = new PackedColor(160, 82, 45);
    Color CHOCOLATE = new PackedColor(210, 105, 30);
    Color PERU = new PackedColor(205, 133, 63);
    Color SANDY_BROWN = new PackedColor(244, 164, 96);
    Color BURLY_WOOD = new PackedColor(222, 184, 135);
    Color TAN = new PackedColor(210, 180, 140);
    Color ROSY_BROWN = new PackedColor(188, 143, 143);
    Color MOCCASIN = new PackedColor(255, 228, 181);
    Color NAVAJO_WHITE = new PackedColor(255, 222, 173);
    Color PEACH_PUFF = new PackedColor(255, 218, 185);
    Color MISTY_ROSE = new PackedColor(255, 228, 225);
    Color LAVENDER_BLUSH = new PackedColor(255, 240, 245);
    Color LINEN = new PackedColor(250, 240, 230);
    Color OLD_LACE = new PackedColor(253, 245, 230);
    Color PAPAYA_WHIP = new PackedColor(255, 239, 213);
    Color SEA_SHELL = new PackedColor(255, 245, 238);
    Color MINT_CREAM = new PackedColor(245, 255, 250);
    Color SLATE_GRAY = new PackedColor(112, 128, 144);
    Color LIGHT_SLATE_GRAY = new PackedColor(119, 136, 153);
    Color LIGHT_STEEL_BLUE = new PackedColor(176, 196, 222);
    Color LAVENDER = new PackedColor(230, 230, 250);
    Color FLORAL_WHITE = new PackedColor(255, 250, 240);
    Color ALICE_BLUE = new PackedColor(240, 248, 255);
    Color GHOST_WHITE = new PackedColor(248, 248, 255);
    Color HONEYDEW = new PackedColor(240, 255, 240);
    Color IVORY = new PackedColor(255, 255, 240);
    Color AZURE = new PackedColor(240, 255, 255);
    Color SNOW = new PackedColor(255, 250, 250);
    Color DIM_GRAY = new PackedColor(105, 105, 105);
    Color DARK_GRAY = new PackedColor(169, 169, 169);
    Color LIGHT_GRAY = new PackedColor(211, 211, 211);
    Color GAINSBORO = new PackedColor(220, 220, 220);
    Color WHITE_SMOKE = new PackedColor(245, 245, 245);

    /*
     * Special Values
     */

    /**
     * A transparent shade of black. This is often used in clamping operations
     * as they represent the lowest possible values RGBA components can have.
     */
    Color TRANSPARENT_BLACK = new PackedColor(0, 0, 0, 0);

    //
    // RGBA
    //

    /**
     * Returns the red component of this color.
     *
     * @return The red component of this color
     */
    float red();

    /**
     * Returns the green component of this color.
     *
     * @return The green component of this color
     */
    float green();

    /**
     * Returns the blue component of this color.
     *
     * @return The blue component of this color
     */
    float blue();

    /**
     * Returns the alpha component of this color.
     *
     * @return The alpha component of this color
     */
    float alpha();

    //
    // Packing
    //

    /**
     * Packs this color into 32 bits.
     * The return value of this method should be in the following format.
     * <p>
     * Bits 24-31 represent the alpha component of this color as an unsigned {@code byte}.<br>
     * Bits 16-23 represent the red component of this color as an unsigned {@code byte}.<br>
     * Bits 8-15 represent the green component of this color as an unsigned {@code byte}.<br>
     * Bits 0-7 represent the blue component of this color as an unsigned {@code byte}.
     * </p>
     *
     * @return The 32 bit representation of this color
     * @see Colors#pack32(int, int, int, int)
     */
    int argb32();

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
     * @see Colors#pack64(float, float, float, float)
     */
    long rgba64();

    //
    // Inversion
    //

    /**
     * Returns the inverse of this color. The RGB components will be inverted.
     * @return The inverse of this color
     */
    @Nonnull
    Color inverse();

    //
    // Equality
    //

    /**
     * Checks for equality between this color and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a color, and the RGBA 64-bit
     * representations are equal
     * @see #rgba64()
     */
    @Override
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
    @Override
    String toString();

    /**
     * Serializes this color into a human-readable string.
     *
     * @return The human-readable string representation of this color
     */
    @Nonnull
    String toReadableString();
}
