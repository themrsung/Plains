package civitas.celestis.geometry;

import civitas.celestis.math.Numbers;
import civitas.celestis.math.Vector3;
import civitas.celestis.util.Triple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;

/**
 * An immutable three-dimensional face. Faces are defined by specifying three vertices,
 * following the right-handed coordinate system. (vertices follow a counter-clockwise order
 * when viewed from the outside)
 */
public class Face extends Triple<Vector3> {
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
     * Creates a new face.
     *
     * @param a The first vertex of this face
     * @param b The second vertex of this face
     * @param c The third vertex of this face
     */
    public Face(@Nonnull Vector3 a, @Nonnull Vector3 b, @Nonnull Vector3 c) {
        super(a, b, c);

        final Vector3 ab = b.subtract(a);
        final Vector3 ac = c.subtract(a);

        this.normal = ab.cross(ac);
        this.center = Numbers.avg(a, b, c);
    }

    /**
     * Creates a new face.
     *
     * @param f The face of which to copy vertices from
     */
    public Face(@Nonnull Face f) {
        super(f);
        this.normal = f.normal;
        this.center = f.center;
    }

    //
    // Variables
    //

    /**
     * The surface normal vertex of this face.
     */
    @Nonnull
    protected final Vector3 normal;

    /**
     * The geometric centroid of this face.
     */
    @Nonnull
    protected final Vector3 center;

    //
    // Getters
    //

    /**
     * Returns the normal vertex of this face.
     *
     * @return The normal vertex of this face
     */
    @Nonnull
    public Vector3 normal() {
        return normal;
    }

    /**
     * Returns the geometric centroid (the simple average of the three vertices) of this face.
     *
     * @return The geometric centroid of this face
     */
    @Nonnull
    public Vector3 center() {
        return center;
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this face and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a face, and the there vertices are equal
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Face f)) return false;
        return a.equals(f.a) && b.equals(f.b) && c.equals(f.c);
    }
}
