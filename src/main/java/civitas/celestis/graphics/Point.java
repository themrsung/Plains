package civitas.celestis.graphics;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a point on a 2D space, particularly in a graphical context.
 * Points can also represent the dimensions of an object, or the area of a shape.
 * <p>
 * The X coordinate represents width, and increments from left-to-right.
 * The Y coordinate represents height, and increments from top-to-bottom.
 * </p>
 */
public class Point implements Serializable {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * The point {@code [0, 0]}. Represents origin.
     */
    public static final Point ZERO = new Point(0, 0);

    /**
     * The dimensions for 720p.
     */
    public static final Point DIMENSIONS_720 = new Point(1280, 720);

    /**
     * The maximum coordinate of a 720p screen.
     */
    public static final Point MAX_VALUE_720 = DIMENSIONS_720.subtract(1);

    /**
     * The dimensions for 1080p.
     */
    public static final Point DIMENSIONS_FHD = new Point(1920, 1080);

    /**
     * The maximum coordinate of a 1080p screen.
     */
    public static final Point MAX_VALUE_FHD = DIMENSIONS_FHD.subtract(1);

    /**
     * The dimensions for 1440p.
     */
    public static final Point DIMENSIONS_QHD = new Point(2560, 1440);

    /**
     * The maximum coordinate of a 1440p screen.
     */
    public static final Point MAX_VALUE_QHD = DIMENSIONS_QHD.subtract(1);

    /**
     * The dimensions for 2160p.
     */
    public static final Point DIMENSIONS_UHD = new Point(3840, 2160);

    /**
     * The maximum coordinate of a 2160p screen.
     */
    public static final Point MAX_VALUE_UHD = DIMENSIONS_UHD.subtract(1);

    //
    // Static Initializers
    //

    /**
     * Returns the current screen size.
     * @return The current screen size
     */
    @Nonnull
    public static Point getScreenSize() {
        final Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        return new Point(dimensions.width, dimensions.height);
    }

    //
    // Constructors
    //

    /**
     * Creates a new point.
     * @param x The X coordinate of this point
     * @param y The Y coordinate of this point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new point.
     * @param p The point of which to copy coordinates from
     */
    public Point(@Nonnull Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    //
    // Variables
    //

    /**
     * The X coordinate of this point.
     */
    protected final int x;

    /**
     * The Y coordinate of this point.
     */
    protected final int y;

    //
    // Getters
    //

    /**
     * Returns the X coordinate of this point.
     * @return The X coordinate of this point
     */
    public int x() {
        return x;
    }

    /**
     * Returns the Y coordinate of this point.
     * @return The Y coordinate of this point
     */
    public int y() {
        return y;
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this point, then returns the resulting point.
     * @param s The scalar of which to add to this point
     * @return The resulting point
     */
    @Nonnull
    public Point add(int s) {
        return new Point(x + s, y + s);
    }

    /**
     * Subtracts a scalar from this point, then returns the resulting point.
     * @param s The scalar of which to subtract from this point
     * @return The resulting point
     */
    @Nonnull
    public Point subtract(int s) {
        return new Point(x - s, y - s);
    }

    /**
     * Multiplies this point by a scalar, then returns the resulting point.
     * @param s The scalar of which to multiply this point by
     * @return The resulting point
     */
    @Nonnull
    public Point multiply(int s) {
        return new Point(x * s, y * s);
    }

    /**
     * Divides this point by a scalar, then returns the resulting point.
     * @param s The scalar of which to divide this point by
     * @return The resulting point
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    public Point divide(int s) throws ArithmeticException {
        return new Point(x / s, y / s);
    }

    /**
     * Adds another point to this point, then returns the resulting point.
     * @param p The point of which to add to this point
     * @return The resulting point
     */
    @Nonnull
    public Point add(@Nonnull Point p) {
        return new Point(x + p.x, y + p.y);
    }

    /**
     * Subtracts another point from this point, then returns the resulting point.
     * @param p The point of which to subtract from this point
     * @return The resulting point
     */
    @Nonnull
    public Point subtract(@Nonnull Point p) {
        return new Point(x - p.x, y - p.y);
    }

    //
    // Rotation
    //

    /**
     * Rotates this point counter-clockwise by the provided angle {@code t}.
     * Angle is denoted in radians.
     *
     * @param t The angle of rotation to apply in radians
     * @return The resulting point
     * @see Math#toRadians(double)
     */
    @Nonnull
    public Point rotate(double t) {
        final double c = Math.cos(-t);
        final double s = Math.sin(-t);

        return new Point(
                (int) ((double) x * c - (double) y * s),
                (int) ((double) x * s - (double) y * c)
        );
    }

    //
    // Clamping
    //

    /**
     * Between this point and the provided boundary points {@code min} and {@code max}, this returns
     * a new point whose coordinates are clamped to the value between the two points.
     * @param min The minimum boundary point to compare to
     * @param max The maximum boundary point to compare to
     * @return The clamped point whose components respect the provided boundaries
     */
    @Nonnull
    public Point clamp(@Nonnull Point min, @Nonnull Point max) {
        return new Point(Math.min(Math.max(x, min.x), max.x), Math.min(Math.max(y, min.y), max.y));
    }

    //
    // Conversion
    //

    /**
     * Returns an array containing the coordinates of this point in XY order.
     * @return An array containing the coordinates of this point
     */
    @Nonnull
    public int[] array() {
        return new int[] {x, y};
    }

    /**
     * Returns a type-safe array containing the coordinates of this point in XY order.
     * @return A type-safe array containing the coordinates of this point
     * @see SafeArray
     */
    @Nonnull
    public SafeArray<Integer> safeArray() {
        return SafeArray.ofInt(x, y);
    }

    /**
     * Returns an unmodifiable list containing the coordinates of this point in XY order.
     * @return The list representation of this point
     * @see List
     */
    @Nonnull
    public List<Integer> list() {
        return List.of(x, y);
    }

    /**
     * Returns a tuple containing the coordinates of this point in XY order.
     * @return The tuple representation of this point
     * @see Tuple
     */
    @Nonnull
    public Tuple<Integer> tuple() {
        return Tuple.ofInt(x, y);
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this point and the provided object {@code obj}.
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a point, and the X and Y coordinates are equal
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Point p)) return false;
        return x == p.x && y == p.y;
    }

    //
    // Serialization
    //

    /**
     * Serializes this point into a string.
     * @return The string representation of this point
     */
    @Nonnull
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
