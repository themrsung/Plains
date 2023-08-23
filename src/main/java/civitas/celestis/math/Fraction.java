package civitas.celestis.math;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;

/**
 * A mathematical fraction with a numerator {@code n} and a denominator {@code d}.
 */
public class Fraction extends Number implements Comparable<Number> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * The fraction which represents {@code 0}.
     */
    public static final Fraction ZERO = new Fraction(0);

    /**
     * The fraction which represents {@link Double#NaN}.
     */
    public static final Fraction NaN = new Fraction(0, 0);

    /**
     * The fraction which represents PI.
     */
    public static final Fraction PI = new Fraction(Math.PI);

    /**
     * The fraction which represents TAU.
     */
    public static final Fraction TAU = new Fraction(2 * Math.PI);

    //
    // Constructors
    //

    /**
     * Creates a new fraction.
     *
     * @param n The numerator of this fraction
     * @param d The denominator of this fraction
     */
    public Fraction(double n, double d) {
        this.n = n;
        this.d = d;
    }

    /**
     * Creates a new fraction.
     *
     * @param n The number of which this fraction should represent
     */
    public Fraction(@Nonnull Number n) {
        this.n = n.doubleValue();
        this.d = 1;
    }

    /**
     * Creates a new fraction.
     *
     * @param f The fraction of which to copy values from
     */
    public Fraction(@Nonnull Fraction f) {
        this.n = f.n;
        this.d = f.d;
    }

    /**
     * Creates a new fraction. The required format is "{@code n / d}".
     *
     * @param notation The notation of which to parse
     * @throws NumberFormatException When the format is invalid
     */
    public Fraction(@Nonnull String notation) {
        final double[] components = parseFractionalNotation(notation);
        this.n = components[0];
        this.d = components[1];
    }

    //
    // Internal
    //

    /**
     * Reads a fractional string in the format of "{@code n / d}" into a primitive array of {@code double}s.
     *
     * @param fractionalString The fractional string to parse
     * @return The parsed components in numerator-denominator order
     */
    protected static double[] parseFractionalNotation(@Nonnull String fractionalString) {
        final String[] strings = fractionalString.replaceAll(" ", "").split("/");
        if (strings.length != 2) {
            throw new NumberFormatException("The provided string is not a fraction.");
        }

        final double[] components = new double[2];

        components[0] = Double.parseDouble(strings[0]);
        components[1] = Double.parseDouble(strings[1]);

        return components;
    }

    //
    // Variables
    //

    /**
     * The numerator of this fraction.
     */
    protected final double n;

    /**
     * The denominator of this fraction.
     */
    protected final double d;

    //
    // Getters
    //

    /**
     * Returns the numerator of this fraction.
     *
     * @return The numerator of this fraction
     */
    public double numerator() {
        return n;
    }

    /**
     * Returns the denominator of this fraction.
     *
     * @return The denominator of this fraction
     */
    public double denominator() {
        return d;
    }

    /**
     * Returns the sign of this fraction. For special cases, see {@link Math#signum(double)}.
     *
     * @return {@code 0} if this fraction represents zero, {@code 1} if it is positive,
     * {@code -1} if it is negative
     * @see Math#signum(double)
     */
    public double signum() {
        return Math.signum(n / d);
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this fraction.
     *
     * @param s Scalar to add to this fraction
     * @return The resulting fraction
     */
    @Nonnull
    public Fraction add(@Nonnull Number s) {
        return new Fraction(n + (s.doubleValue() / d), d);
    }

    /**
     * Subtracts a scalar from this fraction.
     *
     * @param s Scalar to subtract from this fraction
     * @return The resulting fraction
     */
    @Nonnull
    public Fraction subtract(@Nonnull Number s) {
        return new Fraction(n - (s.doubleValue() / d), d);
    }

    /**
     * Multiplies this fraction by a scalar.
     *
     * @param s Scalar to multiply this fraction by
     * @return The resulting fraction
     */
    @Nonnull
    public Fraction multiply(@Nonnull Number s) {
        return new Fraction(n * s.doubleValue(), d);
    }

    /**
     * Divides this fraction by a scalar.
     *
     * @param s Scalar to divide this fraction by
     * @return The resulting fraction
     */
    @Nonnull
    public Fraction divide(@Nonnull Number s) {
        return new Fraction(n, d * s.doubleValue());
    }

    /**
     * Adds another fraction to this fraction.
     *
     * @param f The fraction to add to this fraction
     * @return The resulting fraction
     * @throws ArithmeticException When at least one of the fraction's denominator is zero
     */
    @Nonnull
    public Fraction add(@Nonnull Fraction f) throws ArithmeticException {
        final double lcm = Scalars.lcm(d, f.d);

        final double n1 = n * (lcm / d);
        final double n2 = f.n * (lcm / f.d);

        return new Fraction(n1 + n2, lcm);
    }

    /**
     * Subtracts another fraction from this fraction.
     *
     * @param f The fraction to subtract from this fraction
     * @return The resulting fraction
     * @throws ArithmeticException When at least one of the fraction's denominator is zero
     */
    @Nonnull
    public Fraction subtract(@Nonnull Fraction f) throws ArithmeticException {
        final double lcm = Scalars.lcm(d, f.d);

        final double n1 = n * (lcm / d);
        final double n2 = f.n * (lcm / f.d);

        return new Fraction(n1 - n2, lcm);
    }

    /**
     * Multiplies this fraction by another fraction.
     *
     * @param f The fraction to multiply this fraction with
     * @return The resulting fraction
     */
    @Nonnull
    public Fraction multiply(@Nonnull Fraction f) {
        return new Fraction(n * f.n, d * f.d);
    }

    /**
     * Divides this fraction by another fraction.
     *
     * @param f The fraction to divide this fraction by
     * @return The resulting fraction
     */
    @Nonnull
    public Fraction divide(@Nonnull Fraction f) {
        return new Fraction(n / f.n, d / f.d);
    }

    /**
     * Scales this fraction by multiplying the given scalar to both the numerator and denominator.
     *
     * @param s The scale factor to apply
     * @return The scaled fraction
     */
    @Nonnull
    public Fraction scale(double s) {
        return new Fraction(n * s, d * s);
    }

    /**
     * Returns the {@code e}th power of this fraction.
     *
     * @param e The exponent to raise to
     * @return The {@code e}th power of this fraction
     */
    @Nonnull
    public Fraction pow(double e) {
        return new Fraction(Math.pow(n, e), Math.pow(d, e));
    }

    /**
     * Returns the estimated square root of this fraction.
     *
     * @return The estimate of the square root of this fraction
     */
    @Nonnull
    public Fraction sqrt() {
        // Cannot invoke doubleValue() as this fraction could be non-finite
        return new Fraction(Math.sqrt(n), Math.sqrt(d));
    }

    /**
     * Returns the sin of this fraction.
     *
     * @return The sin of this fraction
     */
    @Nonnull
    public Fraction sin() {
        return new Fraction(Math.sin(n), Math.sin(d));
    }

    /**
     * Returns the cosine of this fraction.
     *
     * @return The cosine of this fraction
     */
    @Nonnull
    public Fraction cos() {
        return new Fraction(Math.cos(n), Math.cos(d));
    }

    /**
     * Returns the tangent of this fraction.
     *
     * @return The tangent of this fraction
     */
    @Nonnull
    public Fraction tan() {
        return new Fraction(Math.tan(n), Math.tan(d));
    }

    /**
     * Returns the arc sine of this fraction.
     *
     * @return The arc sine of this fraction
     */
    @Nonnull
    public Fraction asin() {
        return new Fraction(Math.asin(n), Math.asin(d));
    }

    /**
     * Returns the arc cosine of this fraction.
     *
     * @return The arc cosine of this fraction
     */
    @Nonnull
    public Fraction acos() {
        return new Fraction(Math.acos(n), Math.acos(d));
    }

    /**
     * Returns the arc tangent of this fraction.
     *
     * @return The arc tangent of this fraction
     */
    @Nonnull
    public Fraction atan() {
        return new Fraction(Math.atan(n), Math.atan(d));
    }

    /**
     * Negates this fraction, then returns the negated fraction.
     *
     * @return The negation of this fraction
     */
    @Nonnull
    public Fraction negate() {
        return new Fraction(-n, d);
    }

    /**
     * Returns the inverse of this fraction.
     *
     * @return The inverse of this fraction
     */
    @Nonnull
    public Fraction inverse() {
        return new Fraction(d, n);
    }

    /**
     * Normalizes this fraction to have a denominator of {@code 1}.
     *
     * @return The normalized fraction
     * @throws ArithmeticException When normalization is impossible (the denominator is {@code 0})
     */
    @Nonnull
    public Fraction normalize() throws ArithmeticException {
        if (n == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Fraction(d / n, 1);
    }

    /**
     * Calculates the reciprocal fraction of this fraction
     *
     * @return The unit fraction representation of this fraction
     * @throws ArithmeticException When the numerator is zero
     */
    @Nonnull
    public Fraction reciprocal() throws ArithmeticException {
        if (d == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Fraction(1, n / d);
    }

    //
    // Numeric Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws ArithmeticException When the denominator of this fraction is zero
     */
    @Override
    public int intValue() throws ArithmeticException {
        return (int) (n / d);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     * @throws ArithmeticException When the denominator of this fraction is zero
     */
    @Override
    public long longValue() throws ArithmeticException {
        return (long) (n / d);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public float floatValue() {
        return (float) (n / d);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double doubleValue() {
        return n / d;
    }

    //
    // Comparison
    //

    /**
     * Compares this fraction to another number.
     *
     * @param n The number to compare to
     * @return {@code 0} if the values they represent are equal, {@code 1} if this fraction's value is
     * larger, {@code -1} otherwise
     */
    @Override
    public int compareTo(@Nonnull Number n) {
        return Double.compare(doubleValue(), n.doubleValue());
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this fraction and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a number, and the value it represents it equal
     * to that of this fraction's value
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Number num) return equals(num);
        return false;
    }

    /**
     * Checks for equality between this fraction and the provided number {@code n}.
     *
     * @param n The number to compare to
     * @return {@code true} if the values they represent are equal
     */
    public boolean equals(@Nullable Number n) {
        return n != null && n.doubleValue() == doubleValue();
    }

    //
    // Serialization
    //

    /**
     * Serializes this fraction into a string.
     *
     * @return The string representation of this fraction
     */
    @Nonnull
    @Override
    public String toString() {
        return n + " / " + d;
    }
}
