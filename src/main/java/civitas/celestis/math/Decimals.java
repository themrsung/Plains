package civitas.celestis.math;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * A numerical utility class which contains mathematical
 * functions on {@link BigDecimal} numbers.
 */
public final class Decimals {
    //
    // Constants
    //

    /**
     * A very small constant value used in various applications.
     */
    public static final BigDecimal EPSILON = new BigDecimal("0.000001");

    /**
     * The square root of two.
     */
    public static final BigDecimal SQRT_2;

    /**
     * The amount of decimal places to calculate at runtime.
     */
    public static final int RUNTIME_PRECISION = 32;

    /**
     * The rounding mode to use at runtime.
     */
    public static final RoundingMode RUNTIME_ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * The mathematical context to use at runtime.
     */
    public static final MathContext RUNTIME_CONTEXT = new MathContext(RUNTIME_PRECISION, RUNTIME_ROUNDING_MODE);

    /**
     * The mathematical context to use at compile time.
     */
    public static final MathContext COMPILE_CONTEXT = new MathContext(RUNTIME_PRECISION + 2, RUNTIME_ROUNDING_MODE);

    //
    // Range Validation
    //

    /**
     * Checks if the provided value is within the range of {@code [min, max]}.
     *
     * @param value The value to check
     * @param min   The minimum allowed value
     * @param max   The maximum allowed value
     * @return {@code true} if the value is within the range of {@code [min, max]}
     */
    public static boolean isInRange(@Nonnull BigDecimal value, @Nonnull BigDecimal min, @Nonnull BigDecimal max) {
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    /**
     * Checks if the provided value is within the range of {@code (min, max)}.
     *
     * @param value The value to check
     * @param min   The minimum allowed value
     * @param max   The maximum allowed value
     * @return {@code true} if the value is within the range of {@code (min, max)}
     */
    public static boolean isInRangeExclusive(@Nonnull BigDecimal value, @Nonnull BigDecimal min, @Nonnull BigDecimal max) {
        return value.compareTo(min) > 0 && value.compareTo(max) < 0;
    }

    //
    // Clamping
    //

    /**
     * Clamps the provided value to fit within the range of {@code [min, max]},
     *
     * @param value The value to clamp
     * @param min   The minimum acceptable value
     * @param max   The maximum acceptable value
     * @return The clamped value
     */
    @Nonnull
    public static BigDecimal clamp(@Nonnull BigDecimal value, @Nonnull BigDecimal min, @Nonnull BigDecimal max) {
        if (value.compareTo(min) < 0) return min;
        if (value.compareTo(max) > 0) return max;
        return value;
    }

    //
    // Compile Time Pre-computation
    //

    static {
        SQRT_2 = BigDecimal.TWO.sqrt(COMPILE_CONTEXT);
    }
}
