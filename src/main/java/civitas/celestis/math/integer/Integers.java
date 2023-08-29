package civitas.celestis.math.integer;

import civitas.celestis.exception.IllegalInstanceException;
import jakarta.annotation.Nonnull;

import java.math.BigInteger;

/**
 * Contains utilities related to {@link BigInteger}s.
 */
public final class Integers {
    //
    // Clamping
    //

    /**
     * Clamps the provided value {@code val} to respect the range of {@code [min, max]}.
     *
     * @param val The value of which to clamp
     * @param min The minimum allowed value (inclusive)
     * @param max The maximum allowed value (inclusive)
     * @return The clamped value
     */
    @Nonnull
    public static BigInteger clamp(@Nonnull BigInteger val, @Nonnull BigInteger min, @Nonnull BigInteger max) {
        if (val.compareTo(min) <= 0) return min;
        if (val.compareTo(max) >= 0) return max;
        return val;
    }

    //
    // Miscellaneous
    //

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalInstanceException Always
     */
    private Integers() throws IllegalInstanceException {
        throw new IllegalInstanceException(this);
    }
}
