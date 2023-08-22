package civitas.celestis.math.complex;

import civitas.celestis.math.vector.Vector;
import civitas.celestis.math.vector.Vector2;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * An immutable complex number with a real component {@code r}
 * and an imaginary component {@code i}.
 */
public class Complex extends Number implements Comparable<Number> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * The complex number whose components are both zero.
     */
    public static final Complex ZERO = new Complex(0, 0);

    /**
     * The complex number equivalent of {@code 1}.
     */
    public static final Complex ONE = new Complex(1, 0);

    /**
     * The square root of {@code -1}.
     */
    public static final Complex I = new Complex(0, 1);

    /**
     * The complex number equivalent of PI.
     */
    public static final Complex PI = new Complex(Math.PI, 0);

    /**
     * The complex number equivalent of TAU.
     */
    public static final Complex TAU = new Complex(2 * Math.PI, 0);

    //
    // Constructors
    //

    /**
     * Creates a new complex number.
     *
     * @param r The real part of this number
     * @param i The imaginary part of this number
     */
    public Complex(double r, double i) {
        this.r = r;
        this.i = i;
    }

    /**
     * Creates a new complex number.
     *
     * @param n The real value this complex number should represent
     */
    public Complex(@Nonnull Number n) {
        this(n.doubleValue(), 0);
    }

    /**
     * Creates a new complex number.
     *
     * @param c The complex number of which to copy component values from
     */
    public Complex(@Nonnull Complex c) {
        this.r = c.r;
        this.i = c.i;
    }

    /**
     * Creates a new complex number. The X component is mapped to the real part,
     * and the Y component is mapped to the imaginary part.
     *
     * @param v A two-dimensional vector representing the components of this number
     * @throws IllegalArgumentException When the vector is not a two-dimensional vector
     */
    public Complex(@Nonnull Vector<?> v) {
        if (v.dimensions() != 2) {
            throw new IllegalArgumentException("The provided vector is not two-dimensional.");
        }
        this.r = v.get(0);
        this.i = v.get(1);
    }

    /**
     * Creates a new complex number. The required format is "{@code x + yi}"
     *
     * @param notation The notation of which to parse
     * @throws NumberFormatException When the format is invalid
     */
    public Complex(@Nonnull String notation) {
        final double[] components = parseComplexNotation(notation);
        if (components.length != 2) {
            throw new NumberFormatException("The format is invalid.");
        }

        this.r = components[0];
        this.i = components[1];
    }

    //
    // Internal
    //

    /**
     * Requires that this number is real. (the imaginary part is zero)
     * If the imaginary part is not zero, this will throw an exception.
     *
     * @throws UnsupportedOperationException When the imaginary part of this number is not zero
     */
    protected void requireRealNumber() {
        if (i != 0) {
            throw new UnsupportedOperationException("A complex number whose imaginary part is not 0 cannot be represented as a scalar.");
        }
    }

    /**
     * Reads a complex string in the format of "{@code x + yi}" into a primitive array of {@code double}s.
     *
     * @param complexString The complex number string to parse
     * @return The parse components in real-imaginary order
     */
    protected static double[] parseComplexNotation(@Nonnull String complexString) {
        // Remove any leading or trailing spaces
        complexString = complexString.replaceAll(" ", "");

        // Split the string into real and imaginary parts
        String[] parts = complexString.split("[+-]", 2);

        double[] components = new double[2];

        // Parse the real component
        components[0] = Double.parseDouble(parts[0]);

        // Parse the imaginary component
        if (complexString.contains("-")) {
            components[1] = -Double.parseDouble(parts[1].replace("i", ""));
        } else {
            components[1] = Double.parseDouble(parts[1].replace("i", ""));
        }

        return components;
    }

    //
    // Variables
    //

    /**
     * The real part of this complex number.
     */
    protected final double r;

    /**
     * The imaginary part of this complex number.
     */
    protected final double i;

    //
    // Properties
    //

    /**
     * Returns whether this complex number is safely convertible into a real number.
     * (the imaginary part is zero)
     *
     * @return {@code true} if the imaginary part of this complex number is zero
     */
    public boolean isReal() {
        return i == 0;
    }

    /**
     * Returns the magnitude of this number. (the distance to {@code 0, 0})
     *
     * @return The magnitude of this number
     */
    public double abs() {
        return Math.sqrt(r * r + i * i);
    }

    /**
     * Returns the squared magnitude of this number.
     *
     * @return The squared magnitude of this number
     */
    public double abs2() {
        return r * r + i + i;
    }

    //
    // Getters
    //

    /**
     * Returns the real part of this complex number.
     *
     * @return The real part of this complex number
     */
    public double real() {
        return r;
    }

    /**
     * Returns the imaginary part of this complex number.
     *
     * @return The imaginary part of this complex number
     */
    public double imaginary() {
        return i;
    }

    //
    // Arithmetic
    //

    /**
     * Adds a number to this complex number.
     *
     * @param n The number to add to this number
     * @return The resulting number
     */
    @Nonnull
    public Complex add(@Nonnull Number n) {
        if (n instanceof Complex c) return add(c);
        return new Complex(r + n.doubleValue(), i);
    }

    /**
     * Subtracts a number from this complex number.
     *
     * @param n The number to subtract from this number
     * @return The resulting number
     */
    @Nonnull
    public Complex subtract(@Nonnull Number n) {
        if (n instanceof Complex c) return subtract(c);
        return new Complex(r - n.doubleValue(), i);
    }

    /**
     * Multiplies this number by another number.
     *
     * @param n The number to multiply this number by
     * @return The resulting number
     */
    @Nonnull
    public Complex multiply(@Nonnull Number n) {
        if (n instanceof Complex c) return multiply(c);
        return new Complex(r * n.doubleValue(), i * n.doubleValue());
    }

    /**
     * Divides this number by another number.
     *
     * @param n The number to divide this number by
     * @return The resulting number
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    public Complex divide(@Nonnull Number n) throws ArithmeticException {
        if (n instanceof Complex c) return divide(c);
        return new Complex(r / n.doubleValue(), i / n.doubleValue());
    }

    /**
     * Adds another complex number to this number.
     *
     * @param c The complex number of which to add to this number
     * @return The resulting number
     */
    @Nonnull
    public Complex add(@Nonnull Complex c) {
        return new Complex(r + c.r, i + c.i);
    }

    /**
     * Subtracts another complex number from this number.
     *
     * @param c The complex number of which to subtract from this number
     * @return The resulting number
     */
    @Nonnull
    public Complex subtract(@Nonnull Complex c) {
        return new Complex(r - c.r, i - c.i);
    }

    /**
     * Multiplies this number by another complex number.
     *
     * @param c The complex number of which to multiply this number by
     * @return The resulting number
     */
    @Nonnull
    public Complex multiply(@Nonnull Complex c) {
        return new Complex(r * c.r - i * c.i, r * c.i + i * c.r);
    }

    /**
     * Divides this complex number by another complex number.
     *
     * @param c The complex number of which to divide this number by
     * @return The resulting number
     * @throws ArithmeticException When the provided complex number {@code c} is zero
     */
    @Nonnull
    public Complex divide(@Nonnull Complex c) throws ArithmeticException {
        double n2 = c.abs2();
        if (n2 == 0) throw new ArithmeticException("Cannot divide by zero");
        return new Complex(r * c.r - i * c.i, r * c.i + i * c.r);
    }

    /**
     * Raises this complex number to the {@code e}th power, then returns the exponentiation.
     *
     * @param e The exponent to raise this number to
     * @return The {@code e}th power of this complex number
     * @throws IllegalArgumentException When the exponent {@code e} is smaller than zero ({@code e < 0})
     */
    @Nonnull
    public Complex pow(long e) {
        if (e < 0) throw new IllegalArgumentException("The exponent cannot be smaller than zero.");

        Complex result = Complex.ONE; // Initialize result to 1 + 0i
        Complex base = this; // Initialize base to the current complex number

        while (e > 0) {
            if ((e & 1) == 1) {
                // If the least significant bit of e is 1, multiply result by base
                result = result.multiply(base);
            }
            // Square the base for the next iteration
            base = base.multiply(base);
            e >>= 1; // Shift e to the right by 1 (equivalent to dividing by 2)
        }

        return result;
    }

    /**
     * Returns the square roots of this complex number.
     *
     * @return A tuple containing the two square roots of this complex number
     */
    @Nonnull
    public double[] sqrt() {
        final double magnitude = Math.sqrt(r * r + i * i);
        return new double[]{
                Math.sqrt((magnitude + r) / 2),
                Math.sqrt((magnitude - r) / 2) * Math.signum(i)
        };
    }

    //
    // Negation
    //

    /**
     * Negates this complex number, then returns the resulting number.
     *
     * @return The negation of this number
     */
    @Nonnull
    public Complex negate() {
        return new Complex(-r, -i);
    }

    //
    // Inversion
    //

    /**
     * Returns the inverse of this complex number.
     *
     * @return The inverse of this complex number
     * @throws ArithmeticException When the absolute value (the Euclidean norm) of this number is zero
     */
    @Nonnull
    public Complex inverse() throws ArithmeticException {
        double n2 = abs2();
        if (n2 == 0) throw new ArithmeticException("Cannot divide by zero");
        return new Complex(r / n2, i / n2);
    }

    //
    // Rotation
    //

    /**
     * Rotates this complex number counter-clockwise by the provided angle {@code t}
     * on the real-complex number plane. Angle is denoted in radians.
     *
     * @param t The angle of rotation to apply in radians
     * @return The rotated complex number whose magnitude is equal to that of this number's magnitude
     * @see Math#toRadians(double)
     */
    @Nonnull
    public Complex rotate(double t) {
        final double c = Math.cos(t);
        final double s = Math.sin(t);

        return new Complex(r * c - i * s, r * s + i * c);
    }

    //
    // Transformation
    //

    /**
     * Applies the provided function {@code f} to each component of this complex number, then
     * returns a new complex number containing the resulting elements.
     *
     * @param f The function of which to apply to each component of this complex number
     * @return The resulting complex number
     */
    @Nonnull
    public Complex map(@Nonnull UnaryOperator<Double> f) {
        return new Complex(f.apply(r), f.apply(i));
    }

    /**
     * Between this number and the provided complex number {@code c}, this applies the merger function
     * {@code f} to each corresponding pair of elements, then returns a new complex number containing
     * the resulting elements.
     *
     * @param c The complex number of which to merge this number with
     * @param f The merger function to handle the merging of the two numbers
     * @return The resulting complex number
     */
    @Nonnull
    public Complex merge(@Nonnull Complex c, @Nonnull BinaryOperator<Double> f) {
        return new Complex(f.apply(r, c.r), f.apply(i, c.i));
    }

    //
    // Numeric Conversion
    //

    /**
     * Returns the integer value of this complex number.
     *
     * @return The integer value of this complex number
     * @throws UnsupportedOperationException When the imaginary part of this number is not {@code 0}
     */
    @Override
    public int intValue() {
        requireRealNumber();
        return (int) r;
    }

    /**
     * Returns the {@code long} value of this complex number.
     *
     * @return The {@code long} value of this complex number
     * @throws UnsupportedOperationException When the imaginary part of this number is not {@code 0}
     */
    @Override
    public long longValue() {
        requireRealNumber();
        return (long) r;
    }

    /**
     * Returns the {@code float} value of this complex number.
     *
     * @return The {@code float} value of this complex number
     * @throws UnsupportedOperationException When the imaginary part of this number is not {@code 0}
     */
    @Override
    public float floatValue() {
        requireRealNumber();
        return (float) r;
    }

    /**
     * Returns the {@code double} value of this complex number.
     *
     * @return The {@code double} value of this complex number
     * @throws UnsupportedOperationException When the imaginary part of this number is not {@code 0}
     */
    @Override
    public double doubleValue() {
        requireRealNumber();
        return r;
    }

    //
    // Type Conversion
    //

    /**
     * Returns an array whose components are populated with that of this number's components.
     *
     * @return An array containing the components of this complex number in real-imaginary order
     */
    @Nonnull
    public double[] array() {
        return new double[]{r, i};
    }

    /**
     * Returns an unmodifiable list whose elements are populated with that of this number's components.
     *
     * @return An unmodifiable list containing the components of this complex number in real-imaginary order
     */
    @Nonnull
    public List<Double> list() {
        return List.of(r, i);
    }

    /**
     * Returns a vector whose XY components are populated with the real and imaginary part of
     * this number respectively.
     *
     * @return The vector representation of this complex number
     */
    @Nonnull
    public Vector2 vector() {
        return new Vector2(r, i);
    }

    //
    // Comparison
    //

    /**
     * Compares this number to another number.
     *
     * @param n The number to compare to
     * @return {@code 0} if the values they represent are equal, {@code 1} if this number's value is
     * greater, {@code -1} otherwise
     * @throws UnsupportedOperationException When the other number is a real number,
     *                                       and this number's imaginary part is not {@code 0}
     */
    @Override
    public int compareTo(@Nonnull Number n) {
        if (n instanceof Complex c) return compareTo(c);
        return Double.compare(doubleValue(), n.doubleValue());
    }

    /**
     * Compares this number to another complex number.
     *
     * @param c The complex number to compare to
     * @return {@code 0} if the values they represent are equal, {@code 1} if this number's value is
     * greater, {@code -1} otherwise
     */
    public int compareTo(@Nonnull Complex c) {
        final int real = Double.compare(r, c.r);
        if (real != 0) return real;

        return Double.compare(i, c.i);
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this number and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the object is also a number, and the value it represents is equal
     * to that of this number's value
     */
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Complex c) return equals(c);
        if (obj instanceof Number n) return equals(n);
        return false;
    }

    /**
     * Checks for equality between this number and the provided number {@code n}.
     *
     * @param n The number to compare to
     * @return {@code true} if the values of the numbers are equal
     */
    public boolean equals(@Nullable Number n) {
        if (n instanceof Complex c) return equals(c);
        return n != null && i == 0 && r == n.doubleValue();
    }

    /**
     * Checks for equality between this number and the provided complex number {@code c}.
     *
     * @param c The complex number to compare to
     * @return {@code true} if both the real and imaginary parts are equal
     */
    public boolean equals(@Nullable Complex c) {
        return c != null && r == c.r && i == c.i;
    }

    //
    // Serialization
    //

    /**
     * Serializes this number into a string.
     *
     * @return The string representation of this number
     */
    @Nonnull
    @Override
    public String toString() {
        return Math.signum(i) == -1 ? r + "-" + -i + "i" : r + "+" + i + "i";
    }
}
