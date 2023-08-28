package civitas.celestis.graphics;

import civitas.celestis.util.tuple.Triple;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.awt.*;
import java.io.Serial;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * A three-dimensional face. Faces are partially immutable. The vertices of a
 * face cannot be changed after instantiation, while other transient properties
 * such as the color can be changed.
 * @see Vertex
 */
public class Face extends Triple<Vertex> {
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
     * @param a The first vertex of this face
     * @param b The second vertex of this face
     * @param c The third vertex of this face
     * @param color The initial color of this face
     */
    public Face(@Nonnull Vertex a, @Nonnull Vertex b, @Nonnull Vertex c, @Nonnull Color color) {
        super(a, b, c);
        this.color = color;
    }

    /**
     * Creates a new face.
     * @param f The face of which to copy properties from
     */
    public Face(@Nonnull Face f) {
        super(f);
        this.color = f.color;
        this.center = f.center;
        this.normal = f.normal;
    }

    //
    // Variables
    //

    /**
     * The current color of this face.
     */
    @Nonnull
    private volatile Color color;

    /**
     * The geometric centroid of this face.
     */
    @Nullable
    private transient Vertex center;

    /**
     * The surface normal of this face.
     */
    @Nullable
    private transient Vertex normal;

    //
    // Internal
    //

    /**
     * Calculates the geometric centroid of this face.
     */
    private void calculateCenter() {
        final double sx = a.x() + b.x() + c.x();
        final double sy = a.y() + b.y() + c.y();
        final double sz = a.z() + b.z() + c.z();

        center = new Vertex(sx / 3, sy / 3, sz / 3);
    }

    /**
     * Calculates the surface normal of this face.
     */
    private void calculateNormal() {
        final Vertex ab = b.subtract(a);
        final Vertex ac = c.subtract(a);

        normal = ab.cross(ac).normalizeOrZero();
    }

    //
    // Getters
    //

    /**
     * Returns the first vertex of this face.
     * @return The first vertex of this face
     */
    @Nonnull
    @Override
    public Vertex a() {
        return a;
    }

    /**
     * Returns the second vertex of this face.
     * @return The second vertex of this face
     */
    @Nonnull
    @Override
    public Vertex b() {
        return b;
    }

    /**
     * Returns the third vertex of this face.
     * @return The third vertex of this face
     */
    @Nonnull
    @Override
    public Vertex c() {
        return c;
    }

    /**
     * Returns the geometric centroid of this face.
     * @return The geometric centroid of this face
     */
    @Nonnull
    public Vertex center() {
        if (center == null) calculateCenter();
        return center;
    }

    /**
     * Returns the surface normal of this face.
     * @return The surface normal of this face
     */
    @Nonnull
    public Vertex normal() {
        if (normal == null) calculateNormal();
        return normal;
    }

    /**
     * Returns the current color of this face.
     * @return The color of this face
     */
    @Nonnull
    public synchronized Color getColor() {
        return color;
    }

    /**
     * Sets the color of this face.
     * @param color The color of this face
     */
    public synchronized void setColor(@Nonnull Color color) {
        this.color = color;
    }

    //
    // Transformation
    //

    /**
     * Applies the provided function {@code f} to every vertex of this face, then returns a new
     * face whose vertices are populated from that of the return values of the provided function {@code f}.
     * All other components including color are copied as-is.
     * @param f The function of which to apply to each vertex of this face
     * @return The resulting face
     */
    @Nonnull
    public Face transform(@Nonnull Function<? super Vertex, ? extends Vertex> f) {
        return new Face(f.apply(a), f.apply(b), f.apply(c), color);
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this face and the provided object {@code obj}.
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a face, and the vertices and colors are equal
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Face f)) return false;
        return a.equals(f.a) && b.equals(f.b) && c.equals(f.c) && color.equals(f.color);
    }

    //
    // Serialization
    //

    /**
     * Serializes this face into a string.
     * @return The string representation of this face
     */
    @Nonnull
    @Override
    public String toString() {
        return "Face{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", color=" + Colors.toString(color) +
                '}';
    }
}
