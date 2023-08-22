package civitas.celestis.math.complex;

import civitas.celestis.math.Scalars;
import civitas.celestis.math.matrix.Matrix;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.tuple.Pair;
import jakarta.annotation.Nonnull;

/**
 * Contains utility methods related to quaternions.
 */
public final class Quaternions {
    //
    //
    //
    // Euler Angles
    //
    //
    //

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
    //
    //
    // Axis Angle
    //
    //
    //

    /**
     * Given a rotation quaternion {@code q}, this returns an axis/angle pair denoting
     * the rotation of the quaternion. Axis/angle notation follows the right-handed coordinate system.
     *
     * @param q The quaternion of which to convert to axis/angle notation
     * @return The axis/angle representation of the quaternion
     */
    @Nonnull
    public static Pair.BiPair<Vector3, Double> axisAngle(@Nonnull Quaternion q) {
        return Pair.of(axis(q), angle(q));
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
    //
    //
    // Matrix Representation
    //
    //
    //

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
}
