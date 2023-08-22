package civitas.celestis.math.complex;

import civitas.celestis.math.vector.Vector;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * A specialized quaternion which adds utility features such as construction
 * from and conversion to Euler angles and axis/angle notation.
 * @see Quaternion
 */
public class RotationQuaternion extends Quaternion {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    /**
     * Creates a new rotation quaternion.
     * @param w The W component of this quaternion
     * @param x The X component of this quaternion
     * @param y The Y component of this quaternion
     * @param z The Z component of this quaternion
     */
    public RotationQuaternion(double w, double x, double y, double z) {
        super(w, x, y, z);
    }

    /**
     * Creates a new rotation quaternion.
     * @param s The scalar part (the W component) of this quaternion
     * @param v The vector part (the XYZ components) of this quaternion
     */
    public RotationQuaternion(double s, @Nonnull Vector3 v) {
        super(s, v);
    }

    /**
     * Creates a new rotation quaternion.
     * @param components An array containing the components of this quaternion in WXYZ order
     * @throws IllegalArgumentException When the array's length is not {@code 4}
     */
    public RotationQuaternion(@Nonnull double[] components) {
        super(components);
    }

    /**
     * Creates a new rotation quaternion.
     * @param v The vector of which to copy component values from
     * @throws IllegalArgumentException When the vector is not four-dimensional
     */
    public RotationQuaternion(@Nonnull Vector<?> v) {
        super(v);
    }

    /**
     * Creates a new rotation quaternion.
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 4}
     */
    public RotationQuaternion(@Nonnull Tuple<? extends Number> t) {
        super(t);
    }

    //
    // Euler Angles
    //

    public double pitch() {
        return 0;
    }

    public double yaw() {
        return 0;
    }

    public double roll() {
        return 0;
    }

    //
    // Axis Angle
    //

    public double angle() {
        return 0;
    }

    @Nonnull
    public Vector3 axis() {
        return Vector3.ZERO;
    }


}
