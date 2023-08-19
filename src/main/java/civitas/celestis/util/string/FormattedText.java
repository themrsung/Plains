package civitas.celestis.util.string;

import civitas.celestis.graphics.Color8;
import jakarta.annotation.Nonnull;

/**
 * A formatable {@link Text} object.
 * @see Text
 */
public interface FormattedText extends Text {
    //
    // Getters
    //

    /**
     * Returns the color of this text.
     *
     * @return The color of this text
     */
    @Nonnull
    Color8 color();


}
