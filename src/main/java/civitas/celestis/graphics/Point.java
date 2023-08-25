package civitas.celestis.graphics;

import jakarta.annotation.Nonnull;

/**
 * Represents a point within a two-dimensional plane in a graphical context.
 * Points can either be absolute or relative, depending on the context they are used in.
 * <p>
 * The X coordinate represents width, and increments from left-to-right.
 * The Y coordinate represents height, and increments from top-to-bottom.
 * </p>
 */
public class Point {
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
}
