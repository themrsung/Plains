package civitas.celestis.util.string;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;

/**
 * A text which encapsulates additional information regarding a {@link String},
 * along with the raw content itself.
 * @see FormattedText
 */
public interface Text extends Serializable {
    //
    // Getters
    //

    /**
     * Returns the raw unformatted content of this text.
     *
     * @return The raw content of this text
     */
    @Nonnull
    String content();

    //
    // Equality
    //

    /**
     * Checks for equality between this text and the provided object {@code obj}.
     * This only tests for matching content, both between {@link String}s and {@link Text}s.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is a string and the contents are equal,
     * or if the other object is a {@link Text} and contents ar equal
     */
    @Override
    boolean equals(@Nullable Object obj);

    /**
     * Checks for equality between this text and a string without regard to casing.
     *
     * @param str The string to compare to
     * @return {@code true} if {@link String#equalsIgnoreCase(String)} returns {@code true}
     */
    boolean equalsIgnoreCase(@Nonnull String str);

    /**
     * Checks for equality between this text and another text without regard to casing or formatting.
     *
     * @param text The text to compare to
     * @return {@code true} if {@link String#equalsIgnoreCase(String)} returns {@code true}
     */
    boolean equalsIgnoreCase(@Nonnull Text text);

    //
    // Serialization
    //

    /**
     * Returns a string which accurately depicts the current state of this object.
     * For the raw content of this text, call {@link #content()}.
     *
     * @return The string <b>representation</b> of this object
     */
    @Override
    @Nonnull
    String toString();
}
