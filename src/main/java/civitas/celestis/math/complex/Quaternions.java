package civitas.celestis.math.complex;

import civitas.celestis.exception.IllegalInstanceException;
import civitas.celestis.math.Scalars;
import civitas.celestis.math.matrix.Matrix;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.tuple.Double3;
import civitas.celestis.util.tuple.Double4;
import jakarta.annotation.Nonnull;

/**
 * Contains utility methods related to quaternions.
 */
public final class Quaternions {
    //
    // Randomization
    //

    /**
     * Returns a random rotation quaternion.
     * @return A random rotation quaternion
     */
    @Nonnull
    public static Quaternion random() {
        return new Quaternion(
                Scalars.random(-1, 1),
                Scalars.random(-1, 1),
                Scalars.random(-1, 1),
                Scalars.random(-1, 1)
        ).normalizeOrIdentity();
    }

    //
    // Euler Angles
    //

    /**
     * Creates a new rotation quaternion from Euler angle representation. This method
     * uses the right-handed coordinate system.
     *
     * @param pyr A tuple of doubles containing the Euler angles in pitch-yaw-roll order
     * @return The constructed quaternion
     */
    @Nonnull
    public static Quaternion quaternion(@Nonnull Double3 pyr) {
        return quaternion(pyr.x(), pyr.y(), pyr.z());
    }

    /**
     * Creates a new rotation quaternion from Euler angle representation. This method
     * uses the right-handed coordinate system.
     *
     * @param pitch The pitch of the rotation in radians (rotation along X axis)
     * @param yaw   The yaw of the rotation in radians (rotation along Y axis)
     * @param roll  The roll of the rotation in radians (rotation along Z axis)
     * @return The constructed quaternion
     */
    @Nonnull
    public static Quaternion quaternion(double pitch, double yaw, double roll) {
        final double cy = Math.cos(yaw * 0.5);
        final double sy = Math.sin(yaw * 0.5);
        final double cr = Math.cos(roll * 0.5);
        final double sr = Math.sin(roll * 0.5);
        final double cp = Math.cos(pitch * 0.5);
        final double sp = Math.sin(pitch * 0.5);

        final double w = cy * cr * cp + sy * sr * sp;
        final double x = cy * sr * cp - sy * cr * sp;
        final double y = cy * cr * sp + sy * sr * cp;
        final double z = sy * cr * cp - cy * sr * sp;

        return new Quaternion(w, x, y, z);
    }

    /**
     * Given a rotation quaternion {@code q}, this returns a tuple of doubles containing
     * the pitch, yaw, and roll angles of the quaternion in pitch-yaw-roll order.
     *
     * @param q The quaternion of which to get the Euler angles of
     * @return The Euler angles of the quaternion, denoted in radians
     */
    @Nonnull
    public static Double3 eulerAngles(@Nonnull Quaternion q) {
        return new Double3(pitch(q), yaw(q), roll(q));
    }

    /**
     * Given a rotation quaternion {@code q}, this returns the pitch of the rotation.
     * Pitch is defined as rotation along the X axis, and follows a right-handed coordinate system.
     *
     * @param q The quaternion of which to get the pitch of
     * @return The pitch of the quaternion in radians
     */
    public static double pitch(@Nonnull Quaternion q) {
        final double sinP = 2 * (q.y() * q.z() + q.w() * q.x());
        final double cosP = 1 - 2 * (q.x() * q.x() + q.y() * q.y());
        return Math.atan2(sinP, cosP);
    }

    /**
     * Given a rotation quaternion {@code q}, this returns the yaw of the rotation.
     * Yaw is defined as rotation along the Y axis, and follows a right-handed coordinate system.
     *
     * @param q The quaternion of which to get the yaw of
     * @return The yaw of the quaternion in radians
     */
    public static double yaw(@Nonnull Quaternion q) {
        final double sinY = 2 * (q.x() * q.z() + q.w() * q.y());
        final double cosY = 1 - 2 * (q.y() * q.y() + q.z() * q.z());
        return Math.atan2(sinY, cosY);
    }

    /**
     * Given a rotation quaternion {@code q}, this returns the roll of this rotation.
     * Roll is defined as rotation along the Z axis, and follows a right-handed coordinate system.
     *
     * @param q The quaternion of which to get the roll of
     * @return The roll of the quaternion in radians
     */
    public static double roll(@Nonnull Quaternion q) {
        final double sinR = 2 * (q.x() * q.y() + q.w() * q.z());
        final double cosR = 1 - 2 * (q.y() * q.y() + q.z() * q.z());
        return Math.atan2(sinR, cosR);
    }

    //
    // Axis Angle
    //

    /**
     * Creates a new rotation quaternion from an axis/angle notation. This method uses
     * the right-handed coordinate system.
     *
     * @param axisAngle The axis/angle representation of the rotation
     * @return The constructed quaternion
     */
    @Nonnull
    public static Quaternion quaternion(@Nonnull Double4 axisAngle) {
        return quaternion(new Vector3(axisAngle.x(), axisAngle.y(), axisAngle.z()), axisAngle.w());
    }

    /**
     * Creates a new rotation quaternion from an axis/angle notation. This method uses
     * the right-handed coordinate system.
     *
     * @param axis  The axis of rotation as a unit vector
     * @param angle The angle of rotation in radians
     * @return The constructed quaternion
     */
    @Nonnull
    public static Quaternion quaternion(@Nonnull Vector3 axis, double angle) {
        final double halfAngle = angle * 0.5;
        final double sinHalfAngle = Math.sin(halfAngle);

        final double w = Math.cos(halfAngle);
        final double x = axis.x() * sinHalfAngle;
        final double y = axis.y() * sinHalfAngle;
        final double z = axis.z() * sinHalfAngle;

        return new Quaternion(w, x, y, z);
    }

    /**
     * Given a rotation quaternion {@code q}, this returns an axis/angle pair denoting
     * the rotation of the quaternion. Axis/angle notation follows the right-handed coordinate system.
     * The W component represents the angle of rotation in radians, and the XYZ components
     * represent the axis of rotation.
     *
     * @param q The quaternion of which to convert to axis/angle notation
     * @return The axis/angle representation of the quaternion
     */
    @Nonnull
    public static Double4 axisAngle(@Nonnull Quaternion q) {
        final double magnitude = q.norm();
        if (magnitude < Scalars.EPSILON) {
            // Angle: 0, Axis: positive Y
            return new Double4(0, 0, 1, 0);
        }

        final double w = Math.acos(q.w());
        final double x = q.x() / magnitude;
        final double y = q.y() / magnitude;
        final double z = q.z() / magnitude;

        return new Double4(w, x, y, z);
    }

    /**
     * Given a rotation quaternion {@code q}, this returns the angle of the rotation,
     * in the context of axis/angle notation. Axis/angle notation follows the right-handed coordinate system.
     *
     * @param q The quaternion of which to get the angle of
     * @return The angle of rotation of the quaternion in radians
     */
    public static double angle(@Nonnull Quaternion q) {
        return 2 * Math.acos(q.w());
    }

    /**
     * Given a rotation quaternion {@code q}, this returns the axis of the rotation.
     * If the quaternion represents no rotation (the Euclidean norm is nearly zero),
     * this will return a fallback value {@link Vector3#POSITIVE_Y}.
     *
     * @param q The quaternion of which to get the axis of
     * @return The axis of rotation of the quaternion as a unit vector
     */
    @Nonnull
    public static Vector3 axis(@Nonnull Quaternion q) {
        final double magnitude = q.norm();
        if (magnitude < Scalars.EPSILON) {
            return Vector3.POSITIVE_Y; // Default to Y axis if the quaternion is nearly zero
        }
        return new Vector3(q.x() / magnitude, q.y() / magnitude, q.z() / magnitude);
    }

    //
    // Matrix Representation
    //

    /**
     * Creates a new quaternion from a 3x3 rotation matrix.
     *
     * @param m The rotation matrix which represents the rotation
     * @return The constructed quaternion
     * @throws IllegalArgumentException When the matrix's dimensions is not 3x3
     */
    @Nonnull
    public static Quaternion quaternion(@Nonnull Matrix m) throws IllegalArgumentException {
        if (m.rows() != 3 || m.columns() != 3) {
            throw new IllegalArgumentException("Only a 3x3 matrix can be converted into a quaternion.");
        }

        final double trace = m.get(0, 0) + m.get(1, 1) + m.get(2, 2);
        final double w, x, y, z;

        if (trace > 0) {
            final double S = 0.5 / Math.sqrt(trace + 1.0);
            w = 0.25 / S;
            x = (m.get(2, 1) - m.get(1, 2)) * S;
            y = (m.get(0, 2) - m.get(2, 0)) * S;
            z = (m.get(1, 0) - m.get(0, 1)) * S;
        } else if (m.get(0, 0) > m.get(1, 1) && m.get(0, 0) > m.get(2, 2)) {
            final double S = 2.0 * Math.sqrt(1.0 + m.get(0, 0) - m.get(1, 1) - m.get(2, 2));
            w = (m.get(2, 1) - m.get(1, 2)) / S;
            x = 0.25 * S;
            y = (m.get(0, 1) + m.get(1, 0)) / S;
            z = (m.get(0, 2) + m.get(2, 0)) / S;
        } else if (m.get(1, 1) > m.get(2, 2)) {
            final double S = 2.0 * Math.sqrt(1.0 + m.get(1, 1) - m.get(0, 0) - m.get(2, 2));
            w = (m.get(0, 2) - m.get(2, 0)) / S;
            x = (m.get(0, 1) + m.get(1, 0)) / S;
            y = 0.25 * S;
            z = (m.get(1, 2) + m.get(2, 1)) / S;
        } else {
            final double S = 2.0 * Math.sqrt(1.0 + m.get(2, 2) - m.get(0, 0) - m.get(1, 1));
            w = (m.get(1, 0) - m.get(0, 1)) / S;
            x = (m.get(0, 2) + m.get(2, 0)) / S;
            y = (m.get(1, 2) + m.get(2, 1)) / S;
            z = 0.25 * S;
        }

        return new Quaternion(w, x, y, z);
    }

    /**
     * Given a rotation quaternion {@code q}, this converts the rotation into a 3x3 rotation matrix.
     *
     * @param q The quaternion of which to convert into a rotation matrix
     * @return The matrix representation of the provided quaternion {@code q}
     */
    @Nonnull
    public static Matrix matrix(@Nonnull Quaternion q) {
        final double[][] values = new double[3][3];

        final double w = q.w();
        final double x = q.x();
        final double y = q.y();
        final double z = q.z();

        values[0][0] = 1 - 2 * y * y - 2 * z * z;
        values[0][1] = 2 * x * y - 2 * w * z;
        values[0][2] = 2 * x * z + 2 * w * y;

        values[1][0] = 2 * x * y + 2 * w * z;
        values[1][1] = 1 - 2 * x * x - 2 * z * z;
        values[1][2] = 2 * y * z - 2 * w * x;

        values[2][0] = 2 * x * z - 2 * w * y;
        values[2][1] = 2 * y * z + 2 * w * x;
        values[2][2] = 1 - 2 * x * x - 2 * y * y;

        return Matrix.of(values);
    }

    //
    // Miscellaneous
    //

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalInstanceException Always
     */
    private Quaternions() throws IllegalInstanceException {
        throw new IllegalInstanceException(this);
    }
}
