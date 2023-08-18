package civitas.celestis.util.string;

import civitas.celestis.graphics.Color8;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;

/**
 * An object which is not a {@link String}, but represents
 * a formatted string and can be easily converted to one.
 *
 * @see Format Format
 * @see RichString
 */
public interface Text extends Serializable {
    //
    // Content
    //

    /**
     * Returns the raw content of this text as a string.
     *
     * @return The raw content of this text
     */
    @Nonnull
    String content();

    //
    // Coloring
    //

    /**
     * Returns the color of this text.
     *
     * @return The color of this text
     */
    @Nonnull
    Color8 color();

    //
    // Formatting
    //

    /**
     * Returns the formatting of this text.
     *
     * @return The formatting of this text
     */
    @Nonnull
    Format format();

    //
    // Equality
    //

    /**
     * Checks for equality between this text and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is a string and the contents are equal,
     * or if the other object is a {@link Text} and the
     * content and all attributes are equal
     */
    boolean equals(@Nullable Object obj);

    //
    // Serialization
    //

    /**
     * Serializes this object into a string.
     * Note that this is the representation of this text as a string,
     * not the actual content of this text. To get the raw content of
     * this text, use {@link #content()}.
     *
     * @return The string <b>representation</b> of this text
     */
    @Override
    @Nonnull
    String toString();

    //
    // Format
    //

    /**
     * A plain text.
     */
    Format PLAIN = new Format(false, false, false, false);

    /**
     * A bold text.
     */
    Format BOLD = new Format(true, false, false, false);

    /**
     * An italic text.
     */
    Format ITALIC = new Format(false, true, false, false);

    /**
     * An underlined text.
     */
    Format UNDERLINED = new Format(false, false, true, false);

    /**
     * A text with a strike.
     */
    Format STRIKE = new Format(false, false, false, true);

    /**
     * The formatting of a text object.
     *
     * @param bold       Whether this text is bold
     * @param italic     Whether this text is italic
     * @param underlined Whether this text is underlined
     * @param strike     Whether this text as a strike
     */
    record Format(boolean bold, boolean italic, boolean underlined, boolean strike) {
        /**
         * Toggles whether this text is bold.
         *
         * @return The toggled format
         */
        @Nonnull
        public Format toggleBold() {
            return new Format(!bold, italic, underlined, strike);
        }

        /**
         * Toggles whether this text is italic.
         *
         * @return The toggled format
         */
        @Nonnull
        public Format toggleItalic() {
            return new Format(bold, !italic, underlined, strike);
        }

        /**
         * Toggles whether this text is underlined.
         *
         * @return The toggled format
         */
        @Nonnull
        public Format toggleUnderlined() {
            return new Format(bold, italic, !underlined, strike);
        }

        /**
         * Toggles whether this text is strike.
         *
         * @return The toggled format
         */
        @Nonnull
        public Format toggleStrike() {
            return new Format(bold, italic, underlined, !strike);
        }
    }
}
