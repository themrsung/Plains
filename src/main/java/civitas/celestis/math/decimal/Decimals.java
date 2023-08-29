package civitas.celestis.math.decimal;

import civitas.celestis.exception.IllegalInstanceException;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Contains utilities related to {@link BigDecimal}s.
 */
public final class Decimals {
    //
    // Constants
    //

    public static final int RUNTIME_PRECISION = 50;
    public static final RoundingMode RUNTIME_ROUNDING_MODE = RoundingMode.HALF_UP;
    public static final MathContext RUNTIME_CONTEXT = new MathContext(RUNTIME_PRECISION, RUNTIME_ROUNDING_MODE);

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
    public static BigDecimal clamp(@Nonnull BigDecimal val, @Nonnull BigDecimal min, @Nonnull BigDecimal max) {
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
    private Decimals() throws IllegalInstanceException {
        throw new IllegalInstanceException(this);
    }
}
