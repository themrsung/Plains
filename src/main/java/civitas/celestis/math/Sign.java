package civitas.celestis.math;

import civitas.celestis.util.compression.Packable8;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The sign of a number.
 */
public enum Sign implements Packable8 {
    //
    // The order here matters; Do not change it.
    //
    // Sorting by this enum should give you a quasi-accurate representation of the dataset's order.
    //

    /**
     * When the value is either {@link Double#POSITIVE_INFINITY} ot {@link Float#POSITIVE_INFINITY}.
     */
    NEGATIVE_INFINITY,

    /**
     * When the value is below zero.
     */
    NEGATIVE,

    /**
     * When the value is zero.
     */
    ZERO,

    /**
     * When the value is above zero.
     */
    POSITIVE,

    /**
     * When the value is either {@link Double#POSITIVE_INFINITY} or {@link Float#POSITIVE_INFINITY}.
     */
    POSITIVE_INFINITY,

    /**
     * When the value is either {@link Double#NaN} or {@link Float#NaN}.
     */
    NaN;

    //
    // Factory
    //

    /**
     * Returns the sign of the provided number {@code n}.
     *
     * @param n The number of which to get the sign of
     * @return The sign of the number {@code n}
     */
    @Nonnull
    public static Sign ofNumber(@Nonnull Number n) {
        if (n instanceof BigDecimal bd) return of(bd);
        if (n instanceof BigInteger bi) return of(bi);
        if (n instanceof Double d) return of(d);
        if (n instanceof Float f) return of(f);
        if (n instanceof Long l) return of(l);
        if (n instanceof Integer i) return of(i);

        return of(n.doubleValue());
    }

    /**
     * Returns the sign of the provided value {@code v}.
     *
     * @param v The value of which to get the sign of
     * @return The sign of the value {@code v}
     */
    @Nonnull
    public static Sign of(@Nonnull BigDecimal v) {
        return switch (v.signum()) {
            case 1 -> POSITIVE;
            case 0 -> ZERO;
            case -1 -> NEGATIVE;
            default -> NaN;
        };
    }

    /**
     * Returns the sign of the provided value {@code v}.
     *
     * @param v The value of which to get the sign of
     * @return The sign of the value {@code v}
     */
    @Nonnull
    public static Sign of(@Nonnull BigInteger v) {
        return switch (v.signum()) {
            case 1 -> POSITIVE;
            case 0 -> ZERO;
            case -1 -> NEGATIVE;
            default -> NaN;
        };
    }

    /**
     * Returns the sign of the provided value {@code v}.
     *
     * @param v The value of which to get the sign of
     * @return The sign of the value {@code v}
     */
    @Nonnull
    public static Sign of(double v) {
        if (Double.isNaN(v)) return NaN;
        if (v > Double.MAX_VALUE) return POSITIVE_INFINITY;
        if (v < -Double.MAX_VALUE) return NEGATIVE_INFINITY;

        return v == 0 ? ZERO : (v > 0 ? POSITIVE : NEGATIVE);
    }

    /**
     * Returns the sign of the provided value {@code v}.
     *
     * @param v The value of which to get the sign of
     * @return The sign of the value {@code v}
     */
    @Nonnull
    public static Sign of(float v) {
        if (Float.isNaN(v)) return NaN;
        if (v > Float.MAX_VALUE) return POSITIVE_INFINITY;
        if (v < -Float.MAX_VALUE) return NEGATIVE_INFINITY;

        return v == 0 ? ZERO : (v > 0 ? POSITIVE : NEGATIVE);
    }

    /**
     * Returns the sign of the provided value {@code v}.
     *
     * @param v The value of which to get the sign of
     * @return The sign of the value {@code v}
     */
    @Nonnull
    public static Sign of(long v) {
        return v == 0 ? ZERO : (v > 0 ? POSITIVE : NEGATIVE);
    }

    /**
     * Returns the sign of the provided value {@code v}.
     *
     * @param v The value of which to get the sign of
     * @return The sign of the value {@code v}
     */
    @Nonnull
    public static Sign of(int v) {
        return v == 0 ? ZERO : (v > 0 ? POSITIVE : NEGATIVE);
    }

    /**
     * Returns the sign of the provided value {@code v}.
     *
     * @param v The value of which to get the sign of
     * @return The sign of the value {@code v}
     */
    @Nonnull
    public static Sign of(short v) {
        return v == 0 ? ZERO : (v > 0 ? POSITIVE : NEGATIVE);
    }

    /**
     * Returns the sign of the provided value {@code v}.
     *
     * @param v The value of which to get the sign of
     * @return The sign of the value {@code v}
     */
    @Nonnull
    public static Sign of(byte v) {
        return v == 0 ? ZERO : (v > 0 ? POSITIVE : NEGATIVE);
    }

    //
    // Properties
    //

    /**
     * Returns whether this sign is not a number.
     *
     * @return {@code true} if this sign is {@link Sign#NaN}
     */
    public boolean isNaN() {
        return this == NaN;
    }

    /**
     * Returns whether this sign is finite.
     *
     * @return {@code true} if this sign is finite
     */
    public boolean isFinite() {
        return this != POSITIVE_INFINITY && this != NEGATIVE_INFINITY && this != NaN;
    }

    /**
     * Returns whether this sign is infinite.
     * Note that this returns {@code false} for {@link Sign#NaN}.
     *
     * @return {@code true} if this sign is infinite
     */
    public boolean isInfinite() {
        return this == POSITIVE_INFINITY || this == NEGATIVE_INFINITY;
    }

    /**
     * Returns the inverse of this sign.
     *
     * @return The inverse of this sign
     */
    @Nonnull
    public Sign inverse() {
        return switch (this) {
            case NaN -> NaN;
            case POSITIVE_INFINITY -> NEGATIVE_INFINITY;
            case NEGATIVE_INFINITY -> POSITIVE_INFINITY;
            case POSITIVE -> NEGATIVE;
            case NEGATIVE -> POSITIVE;
            case ZERO -> ZERO;
        };
    }

    //
    // Packing
    //

    /**
     * {@inheritDoc}
     * <p>
     * When the data is invalid, this will fallback to {@link Sign#NaN} instead
     * of throwing an exception.
     * </p>
     *
     * @param sign8 The packed 8-bit representation of this sign
     * @return The unpacked sign
     */
    @Nonnull
    public static Sign unpack8(byte sign8) {
        try {
            return values()[sign8];
        } catch (final IndexOutOfBoundsException e) {
            return NaN;
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * This simply casts the constant ordinal (obtainable by {@link Sign#ordinal()}) to a {@code byte}.
     * </p>
     *
     * @return {@inheritDoc}
     */
    @Override
    public byte pack8() {
        return (byte) ordinal();
    }
}
