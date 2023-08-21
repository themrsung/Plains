package civitas.celestis.geometry;

import civitas.celestis.math.Vector3;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Contains geometric utilities.
 */
public final class Geometry {
    //
    // Intersection
    //

    /**
     * Given a tetrahedron in the form of its four corners,
     * this returns the signed volume of the tetrahedron.
     *
     * @param a The first corner of the tetrahedron
     * @param b The second corner of the tetrahedron
     * @param c The third corner of the tetrahedron
     * @param d The fourth corner of the tetrahedron
     * @return The singed volume of the tetrahedron
     */
    public static double signedVolume(@Nonnull Vector3 a, @Nonnull Vector3 b, @Nonnull Vector3 c, @Nonnull Vector3 d) {
        final Vector3 ab = b.subtract(a);
        final Vector3 ac = c.subtract(a);
        final Vector3 ad = d.subtract(a);

        return (1d / 6d) * ab.cross(ac).dot(ad);
    }

    /**
     * Checks if the face and ray have an intersection.
     *
     * @param face The face to check
     * @param ray  The ray to check
     * @return {@code true} if the face and ray have an intersection
     */
    public static boolean intersects(@Nonnull Face face, @Nonnull Ray ray) {
        final Vector3 p1 = face.a();
        final Vector3 p2 = face.b();
        final Vector3 p3 = face.c();

        final Vector3 q1 = ray.origin;
        final Vector3 q2 = ray.destination(face.center.distance2(q1));

        // These must have different signs
        final double v1 = signedVolume(q1, p1, p2, p3);
        final double v2 = signedVolume(q2, p1, p2, p3);

        // These must have the same sign
        final double v3 = signedVolume(q1, q2, p1, p2);
        final double v4 = signedVolume(q1, q2, p2, p3);
        final double v5 = signedVolume(q1, q2, p3, p1);

        return (v1 * v2) < 0 && (v3 * v4 * v5) >= 0;
    }

    /**
     * Calculates the point of intersection between a face and ray.
     *
     * @param face The face
     * @param ray  The ray
     * @return The intersection if found, {@code null} if not
     */
    @Nullable
    public static Vector3 intersection(@Nonnull Face face, @Nonnull Ray ray) {
        // Obtain references to face's vertices
        final Vector3 p1 = face.a();
        final Vector3 p2 = face.b();
        final Vector3 p3 = face.c();

        // Declare local variables
        final Vector3 normal = face.normal;
        final Vector3 rayOrigin = ray.origin;
        final Vector3 rayDirection = ray.direction;

        // Calculate determinant to check if the face and ray are parallel
        final double determinant = rayDirection.dot(normal);
        if (Math.abs(determinant) < 1e-6) {
            return null; // Ray is parallel to face
        }

        // Calculate the intersection point
        final double t = p1.subtract(rayOrigin).dot(normal) / determinant;
        final Vector3 intersectionPoint = ray.destination(t);

        // Calculate the barycentric coordinates of the intersection point
        final Vector3 v0 = p2.subtract(p1);
        final Vector3 v1 = p3.subtract(p1);
        final Vector3 v2 = intersectionPoint.subtract(p1);

        final double dot00 = v0.dot(v0);
        final double dot01 = v0.dot(v1);
        final double dot11 = v1.dot(v1);
        final double dot20 = v2.dot(v0);
        final double dot21 = v2.dot(v1);

        final double invDenom = 1.0 / (dot00 * dot11 - dot01 * dot01);
        final double u = (dot11 * dot20 - dot01 * dot21) * invDenom;
        final double v = (dot00 * dot21 - dot01 * dot20) * invDenom;

        if (u >= 0 && v >= 0 && u + v <= 1) {
            return intersectionPoint; // Intersection point lies inside the triangle
        }

        return null; // Intersection point is outside the triangle
    }

    /**
     * Given an incident vector and the surface normal, this calculates the reflection vector
     * if the incident vector were to collide with the surface. The surface normal is assumed to be normalized.
     *
     * @param i The incident vector
     * @param n The surface normal
     * @return The reflection vector
     */
    @Nonnull
    public static Vector3 reflect(@Nonnull Vector3 i, @Nonnull Vector3 n) {
        return i.subtract(n.multiply(i.dot(n) * 2));
    }

}
