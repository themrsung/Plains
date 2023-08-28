package civitas.celestis.graphics;

import civitas.celestis.math.Scalars;
import civitas.celestis.math.complex.Quaternion;
import civitas.celestis.math.vector.Vector;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * A specialized {@link Vector3} used in a graphical context. Vertices
 * add graphical functionality such as contextual transformation between
 * coordinate systems.
 *
 * @see Vector3
 */
public class Vertex extends Vector3 {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * A vertex of no direction or magnitude. Represents origin.
     */
    public static final Vertex ZERO = new Vertex(0, 0, 0);

    /**
     * The positive X unit vertex.
     */
    public static final Vertex POSITIVE_X = new Vertex(1, 0, 0);

    /**
     * The positive Y unit vertex.
     */
    public static final Vertex POSITIVE_Y = new Vertex(0, 1, 0);

    /**
     * The positive Z unit vertex.
     */
    public static final Vertex POSITIVE_Z = new Vertex(0, 0, 1);

    /**
     * The negative X unit vertex.
     */
    public static final Vertex NEGATIVE_X = new Vertex(-1, 0, 0);

    /**
     * The negative Y unit vertex.
     */
    public static final Vertex NEGATIVE_Y = new Vertex(0, -1, 0);

    /**
     * The negative Z unit vertex.
     */
    public static final Vertex NEGATIVE_Z = new Vertex(0, 0, -1);

    //
    // Constructors
    //

    /**
     * Creates a new vertex.
     *
     * @param x The X coordinate of this vertex
     * @param y The Y coordinate of this vertex
     * @param z The Z coordinate of this vertex
     */
    public Vertex(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Creates a new vertex.
     *
     * @param components An array containing the coordinates of this vertex in XYZ order
     * @throws IllegalArgumentException When the provided array {@code a}'s length is not {@code 3}
     */
    public Vertex(@Nonnull double[] components) {
        super(components);
    }

    /**
     * Creates a new vertex.
     *
     * @param v The vector of which to copy coordinates from
     * @throws IllegalArgumentException When the provided vector {@code v} is not three-dimensional
     */
    public Vertex(@Nonnull Vector<?> v) {
        super(v);
    }

    /**
     * Creates a new vertex.
     *
     * @param t The tuple of which to copy coordinates from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 3}
     */
    public Vertex(@Nonnull Tuple<? extends Number> t) {
        super(t);
    }

    /**
     * Creates a new vertex.
     *
     * @param a The array of which to copy coordinates from
     * @throws IllegalArgumentException When the provided array {@code a}'s length is not {@code 3}
     */
    public Vertex(@Nonnull SafeArray<? extends Number> a) {
        super(a);
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this vertex, then returns the resulting vertex.
     *
     * @param s The scalar to add to this vertex
     * @return The resulting vertex
     */
    @Nonnull
    @Override
    public Vertex add(double s) {
        return new Vertex(x + s, y + s, z + s);
    }

    /**
     * Subtracts this vertex by a scalar, then returns the resulting vertex.
     *
     * @param s The scalar to subtract this vertex by
     * @return The resulting vertex
     */
    @Nonnull
    @Override
    public Vertex subtract(double s) {
        return new Vertex(x - s, y - s, z - s);
    }

    /**
     * Multiplies this vertex by a scalar, then returns the resulting vertex.
     *
     * @param s The scalar to multiply this vertex by
     * @return The resulting vertex
     */
    @Nonnull
    @Override
    public Vertex multiply(double s) {
        return new Vertex(x * s, y * s, z * s);
    }

    /**
     * Divides this vertex by a scalar, then returns the resulting vertex.
     *
     * @param s The scalar to divide this vertex by
     * @return The resulting vertex
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    @Override
    public Vertex divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero");
        return new Vertex(x / s, y / s, z / s);
    }

    /**
     * Adds another vertex to this vertex, then returns the resulting vertex.
     *
     * @param v The vertex of which to add to this vertex
     * @return The resulting vertex
     */
    @Nonnull
    public Vertex add(@Nonnull Vertex v) {
        return new Vertex(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Subtracts this vertex by another vertex, then returns the resulting vertex.
     *
     * @param v The vertex of which to subtract from this vertex
     * @return The resulting vertex
     */
    @Nonnull
    public Vertex subtract(@Nonnull Vertex v) {
        return new Vertex(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Between this vertex and the provided vertex {@code v}, this returns the
     * cross product of the two vertices
     *
     * @param v The vertex of which to get the cross product between
     * @return The cross product of the two vertices
     */
    @Nonnull
    public Vertex cross(@Nonnull Vertex v) {
        return new Vertex(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    //
    // Clamping
    //

    /**
     * Between this vertex and the provided boundary vertex {@code v}, this returns a vertex whose
     * components are the minimum value between the two vertices. Each corresponding component is compared,
     * and the smaller value is chosen for the new vertex.
     *
     * @param v The boundary vertex to compare to
     * @return The minimum vertex between this vector and {@code v}
     */
    @Nonnull
    public Vertex min(@Nonnull Vertex v) {
        return new Vertex(Math.min(x, v.x), Math.min(y, v.y), Math.min(z, v.z));
    }

    /**
     * Between this vertex and the provided boundary vertex {@code v}, this returns a vertex whose
     * components are the maximum value between the two vertices. Each corresponding component is compared,
     * and the larger value is chosen for the new vertex.
     *
     * @param v The boundary vertex to compare to
     * @return The maximum vertex between the two vectors
     */
    @Nonnull
    public Vertex max(@Nonnull Vertex v) {
        return new Vertex(Math.max(x, v.x), Math.max(y, v.y), Math.max(z, v.z));
    }

    /**
     * Between this vertex and the provided boundary vertices {@code min} and {@code max},
     * this returns a new vertex whose components are the clamped value between the two vertices.
     *
     * @param min The minimum boundary vertex to compare to
     * @param max The maximum boundary vertex to compare to
     * @return The clamped vertex whose components respect the provided boundaries
     */
    @Nonnull
    public Vertex clamp(@Nonnull Vertex min, @Nonnull Vertex max) {
        return new Vertex(
                Scalars.clamp(x, min.x, max.x),
                Scalars.clamp(y, min.y, max.y),
                Scalars.clamp(z, min.z, max.z)
        );
    }

    //
    // Negation
    //

    /**
     * Negates this vertex, then returns the resulting vertex.
     *
     * @return The negation of this vertex
     */
    @Nonnull
    @Override
    public Vertex negate() {
        return new Vertex(-x, -y, -z);
    }

    //
    // Normalization
    //

    /**
     * Normalizes this vertex to have a Euclidean norm (magnitude) of {@code 1}. If this vertex
     * has no direction (the Euclidean norm is zero), this will throw an {@link ArithmeticException},
     * as normalization is not possible.
     *
     * @return The normalized vertex of this vertex
     * @throws ArithmeticException When the Euclidean norm of this vertex is zero
     */
    @Nonnull
    @Override
    public Vertex normalize() throws ArithmeticException {
        final double s = Math.sqrt(x * x + y * y + z * z);
        if (s == 0) throw new ArithmeticException("Cannot divide by zero");
        return new Vertex(x / s, y / s, z / s);
    }

    /**
     * Normalizes this vertex to have a Euclidean norm (magnitude) of {@code 1}. If this vertex
     * has no direction (the Euclidean norm is zero), this will return itself. (which is zero, since
     * only zero vertices can have a magnitude of zero)
     *
     * @return The normalized vertex of this vertex if successful,
     * a vertex representing origin (zero) otherwise
     */
    @Nonnull
    @Override
    public Vertex normalizeOrZero() {
        final double s = Math.sqrt(x * x + y * y + z * z);
        if (s == 0) return this;
        return new Vertex(x / s, y / s, z / s);
    }

    /**
     * Normalizes this vertex to have a Euclidean norm (magnitude) of {@code 1}. If this vertex
     * has no direction (the Euclidean norm is zero), this will return the provided fallback
     * value {@code v} instead of throwing an exception.
     *
     * @return The normalized vertex of this vertex if successful, the fallback value otherwise
     */
    @Nonnull
    public Vertex normalizeOrDefault(@Nonnull Vertex v) {
        final double s = Math.sqrt(x * x + y * y + z * z);
        if (s == 0) return v;
        return new Vertex(x / s, y / s, z / s);
    }

    //
    // Rotation
    //

    /**
     * Rotates this vertex by the provided rotation quaternion {@code q},
     * then returns the rotated vertex.
     *
     * @param q The rotation quaternion representing the rotation to apply to this vertex
     * @return The rotated vertex
     */
    @Nonnull
    @Override
    public Vertex rotate(@Nonnull Quaternion q) {
        final Quaternion qpq = q.multiply(quaternion()).multiply(q.conjugate());
        return new Vertex(qpq.x(), qpq.y(), qpq.z());
    }

    //
    // Transformation
    //

    /**
     * Applies the provided function {@code f} to every element of this vertex, then returns
     * a new vertex whose elements are composed of the return values of the provided function {@code f}.
     *
     * @param f The function of which to apply to each element of this vertex
     * @return The resulting vertex
     */
    @Nonnull
    @Override
    public Vertex map(@Nonnull UnaryOperator<Double> f) {
        return new Vertex(f.apply(x), f.apply(y), f.apply(z));
    }

    /**
     * Between this vertex and the provided vertex {@code v}, this applies the merger function {@code f}
     * to each corresponding pair of elements, then returns a new vertex whose elements are composed
     * of the return values of the merger function {@code f}.
     *
     * @param v The vertex of which to merge this vertex with
     * @param f The merger function to handle the merging of the two vertices
     * @return The resulting vertex
     */
    @Nonnull
    public Vertex merge(@Nonnull Vertex v, @Nonnull BinaryOperator<Double> f) {
        return new Vertex(f.apply(x, v.x), f.apply(y, v.y), f.apply(z, v.z));
    }

    //
    // Contextual Transformation
    //

    /**
     * Transforms this vertex into a relative coordinate system.
     *
     * @param offset   The current offset of this vertex compared to the new point of origin
     * @param rotation The relative rotation of this vertex compared to the new identity rotation
     * @return The transformed relative coordinate vertex
     */
    @Nonnull
    public Vector3 relative(@Nonnull Vector3 offset, @Nonnull Quaternion rotation) {
        return subtract(offset).rotate(rotation);
    }

    /**
     * Transforms this vertex into a relative coordinate system.
     *
     * @param offset   The current offset of this vertex compared to the new point of origin
     * @param rotation The relative rotation of this vertex compared to the new identity rotation
     * @return The transformed relative coordinate vertex
     */
    @Nonnull
    public Vertex relative(@Nonnull Vertex offset, @Nonnull Quaternion rotation) {
        return subtract(offset).rotate(rotation);
    }

    /**
     * Transforms this vertex into an absolute coordinate system.
     *
     * @param offset   The current offset of this vertex compared to absolute origin
     * @param rotation The relative rotation of this vertex compared to absolute identity
     * @return The transformed absolute coordinate vertex
     */
    @Nonnull
    public Vector3 absolute(@Nonnull Vector3 offset, @Nonnull Quaternion rotation) {
        return rotate(rotation).add(offset);
    }

    /**
     * Transforms this vertex into an absolute coordinate system.
     *
     * @param offset   The current offset of this vertex compared to absolute origin
     * @param rotation The relative rotation of this vertex compared to absolute identity
     * @return The transformed absolute coordinate vertex
     */
    @Nonnull
    public Vertex absolute(@Nonnull Vertex offset, @Nonnull Quaternion rotation) {
        return rotate(rotation).add(offset);
    }
}
