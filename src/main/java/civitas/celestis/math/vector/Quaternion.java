package civitas.celestis.math.vector;

import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;

public class Quaternion extends Vector4 {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 1016371168055826681L;

    /**
     * The identity quaternion. This will produce no rotation.
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
     * @param s The scalar part (the W component) of this quaternion
     * @param v The vector part (the XYZ components) of this quaternion
     */
    public Quaternion(double s, @Nonnull Vector3 v) {
        super(s, v.x, v.y, v.z);
    }

    /**
     * Creates a new quaternion.
     *
     * @param components An array containing the components of this quaternion in WXYZ order
     * @throws IllegalArgumentException When the array's length is not {@code 4}
     */
    public Quaternion(@Nonnull double[] components) {
        super(components);
    }

    /**
     * Creates a new quaternion.
     *
     * @param v The vector of which to copy component values from
     * @throws IllegalArgumentException When the vector is not four-dimensional
     */
    public Quaternion(@Nonnull Vector<?> v) {
        super(v);
    }

    /**
     * Creates a new quaternion.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the tuple's size is not {@code 4}
     */
    public Quaternion(@Nonnull Tuple<Double> t) {
        super(t);
    }

    //
    // Properties
    //

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
     *
     * @return The inverse of this quaternion
     * @throws ArithmeticException When the squared Euclidean norm of this quaternion is zero
     */
    @Nonnull
    public Quaternion inverse() throws ArithmeticException {
        return conjugate().divide(w * w + x * x + y * y + z * z);
    }

    //
    // Getters
    //

    /**
     * Returns the scalar part (the W component) of this quaternion.
     *
     * @return The scalar part of this quaternion
     */
    public double scalar() {
        return w;
    }

    /**
     * Returns the vector part (the XYZ components) of this quaternion.
     *
     * @return The vector part of this quaternion
     */
    @Nonnull
    public Vector3 vector() {
        return new Vector3(x, y, z);
    }

    //
    // Arithmetic
    //

    // TODO: 2023-08-19 Re-write the comments here to suit quaternions.

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Quaternion add(double s) {
        return new Quaternion(w + s, x, y, z);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Quaternion subtract(double s) {
        return new Quaternion(w - s, x, y, z);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this vector by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Quaternion multiply(double s) {
        return new Quaternion(w * s, x * s, y * s, z * s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to divide this vector by
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Quaternion divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        return new Quaternion(w / s, x / s, y / s, z / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to add to this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Quaternion add(@Nonnull Vector4 v) {
        return new Quaternion(w + v.w, x + v.x, y + v.y, z + v.z);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to subtract from this vector
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Quaternion subtract(@Nonnull Vector4 v) {
        return new Quaternion(w - v.w, x - v.x, y - v.y, z - v.z);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector of which to multiply this vector by
     * @return {@inheritDoc}
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
}
