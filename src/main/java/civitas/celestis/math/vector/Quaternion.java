package civitas.celestis.math.vector;

import civitas.celestis.math.Numbers;
import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * A specialized four-dimensional vector which is mostly used to represent
 * the rotation, or rate of rotation of three-dimensional vectors.
 */
public class Quaternion extends Vector4 {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -2722908372259345904L;

    /**
     * The identity quaternion. This produces no rotation.
     */
    public static final Quaternion IDENTITY = new Quaternion(1, 0, 0, 0);

    //
    // Constructors
    //

    /**
     * Creates a new quaternion.
     *
     * @param w The W component of this quaternion
     * @param x The X component of this quaternion
     * @param y The Y component of this quaternion
     * @param z The Z component of this quaternion
     */
    public Quaternion(double w, double x, double y, double z) {
        super(w, x, y, z);
    }

    /**
     * Creates a new quaternion.
     *
     * @param values An array containing the values of this quaternion in WXYZ order
     */
    public Quaternion(@Nonnull double[] values) {
        super(values);
    }

    /**
     * Creates a new quaternion.
     *
     * @param s The scalar part (the W component) of this quaternion
     * @param v The vector part (the XYZ components) of this quaternion
     */
    public Quaternion(double s, @Nonnull Vector3 v) {
        super(s, v.x, v.y, v.z);
    }

    /**
     * Creates a new quaternion.
     *
     * @param v The vector of which to copy component values from
     */
    public Quaternion(@Nonnull Vector<?, ?> v) {
        super(v);
    }

    /**
     * Creates a new quaternion.
     *
     * @param v The vector of which to copy component values from
     */
    public Quaternion(@Nonnull DoubleVector<?> v) {
        super(v);
    }

    /**
     * Creates a new quaternion.
     *
     * @param v The vector of which to copy component values from
     */
    public Quaternion(@Nonnull Vector4 v) {
        super(v);
    }

    //
    // Properties
    //

    /**
     * Returns the scalar part (the W component) of this quaternion.
     *
     * @return The scalar part of this quaternion
     */
    public final double scalar() {
        return w;
    }

    /**
     * Returns the vector part of this quaternion.
     *
     * @return The vector part of this quaternion
     */
    @Nonnull
    public final Vector3 vector() {
        return new Vector3(x, y, z);
    }

    /**
     * Returns the conjugate of this quaternion.
     *
     * @return The conjugate of this quaternion
     */
    @Nonnull
    public Quaternion conjugate() {
        return new Quaternion(w, -x, -y, -z);
    }

    /**
     * Returns the inverse of this quaternion.
     * If an inverse cannot be derived (this quaternion has a Euclidean norm of zero),
     * this will throw an {@link ArithmeticException}.
     *
     * @return The inverse of this quaternion
     * @throws ArithmeticException When the Euclidean norm of this quaternion is zero
     */
    @Nonnull
    public Quaternion inverse() throws ArithmeticException {
        final double n = Math.sqrt(w * w + x * x + y * y + z * z);

        if (n == 0) {
            throw new ArithmeticException("Cannot derive the inverse of a quaternion whose magnitude is zero.");
        }

        final double invN = 1.0 / n;
        return new Quaternion(w * invN, -x * invN, -y * invN, -z * invN);
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this quaternion.
     *
     * @param s The scalar to add to this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion add(double s) {
        return new Quaternion(w + s, x, y, z);
    }

    /**
     * Subtracts a scalar from this quaternion.
     *
     * @param s The scalar to subtract from this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion subtract(double s) {
        return new Quaternion(w - s, x, y, z);
    }

    /**
     * Multiplies this quaternion by a scalar.
     *
     * @param s The scalar to multiply this quaternion by
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion multiply(double s) {
        return new Quaternion(w * s, x * s, y * s, z * s);
    }

    /**
     * Divides this quaternion by a scalar.
     *
     * @param s The scalar to divide this quaternion by
     * @return The resulting quaternion
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    @Override
    public Quaternion divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero");
        return new Quaternion(w / s, x / s, y / s, z / s);
    }

    /**
     * Divides this quaternion by a scalar, but allows division by zero.
     *
     * @param s The scalar to divide this quaternion by
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion divideAllowZero(double s) {
        return new Quaternion(w / s, x / s, y / s, z / s);
    }

    /**
     * Adds another vector to this quaternion. This operation accepts any
     * {@link Vector4} as its parameter, but treats it as if it were a quaternion.
     *
     * @param v The vector to add to this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion add(@Nonnull Vector4 v) {
        return new Quaternion(w + v.w, x + v.x, y + v.y, z + v.z);
    }

    /**
     * Subtracts another vector from this quaternion. This operation accepts any
     * {@link Vector4} as its parameter, but treats it as if it were a quaternion.
     *
     * @param v The vector to subtract from this quaternion
     * @return The resulting quaternion
     */
    @Nonnull
    @Override
    public Quaternion subtract(@Nonnull Vector4 v) {
        return new Quaternion(w - v.w, x - v.x, y - v.y, z - v.z);
    }

    /**
     * Performs quaternion left-multiplication. This operation accepts
     * any {@link Vector4} as its parameter, but treats it as if it were a quaternion.
     *
     * @param v The vector of which to get the quaternion product between
     * @return The quaternion left-product between the two values
     */
    @Nonnull
    @Override
    public Quaternion multiply(@Nonnull Vector4 v) {
        return new Quaternion(
                w * v.w - x * v.x - y * v.y - z * v.z,
                w * v.x + x * v.w + y * v.z - z * v.y,
                w * v.y - x * v.z + y * v.w + z * v.x,
                w * v.z + x * v.y - y * v.x + z * v.w
        );
    }

    /**
     * Scales the rotation of this quaternion.
     * This will only work properly if this quaternion is a rotation quaternion.
     * (a quaternion with a Euclidean norm of {@code 1})
     *
     * @param s The scale factor to apply to the rotation
     * @return The scaled quaternion
     */
    @Nonnull
    public Quaternion scale(double s) {
        if (Math.abs(w() - 1) < Numbers.EPSILON) {
            return IDENTITY;
        }

        final double halfAngle = Math.acos(w());
        final double newHalfAngle = halfAngle * s;

        final double sinHalfAngle = Math.sin(halfAngle);
        final double sinNewHalfAngle = Math.sin(newHalfAngle);

        final double scaleFactor = sinNewHalfAngle / sinHalfAngle;

        return new Quaternion(
                Math.cos(newHalfAngle),
                x * scaleFactor,
                y * scaleFactor,
                z * scaleFactor
        );
    }

    //
    // Serialization
    //

    /**
     * Deserializes a string into a quaternion.
     *
     * @param input The input string to parse
     * @return The parsed quaternion
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static Quaternion parseQuaternion(@Nonnull String input) throws NumberFormatException {
        if (!input.startsWith("Vector{")) {
            throw new NumberFormatException("The provided string is not a vector.");
        }

        final String[] valueStrings = input.replaceAll("Vector\\{|}", "").split(", ");
        if (valueStrings.length != 4) {
            throw new NumberFormatException("The provided string does not have four components.");
        }

        final double[] values = new double[4];

        for (final String valueString : valueStrings) {
            final String[] split = valueString.split("=");
            if (split.length != 2) {
                throw new NumberFormatException("The format is invalid.");
            }

            values[switch (split[0]) {
                case "w" -> 0;
                case "x" -> 1;
                case "y" -> 2;
                case "z" -> 3;
                default -> throw new NumberFormatException("The provided string has a non-WXYZ component.");
            }] = Double.parseDouble(split[1]);
        }

        return new Quaternion(values);
    }
}
