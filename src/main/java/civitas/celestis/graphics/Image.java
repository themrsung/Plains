package civitas.celestis.graphics;

import jakarta.annotation.Nonnull;

/**
 * A two-dimensional image. Images are used to store rendered information within
 * the Plains graphics API.
 * @see PackedImage
 */
public interface Image {
    //
    // Properties
    //

    /**
     * Returns the dimensions of this image, packaged as a point.
     * The X coordinate represents the width of this image, and the
     * Y coordinate represents the height of this image.
     * @return The dimensions of this image
     */
    @Nonnull
    Point getDimensions();

    /**
     * Returns the width of this image.
     * @return The width of this image
     */
    int getWidth();

    /**
     * Returns the height of this image.
     * @return The height of this image
     */
    int getHeight();

    //
    // RGBA
    //

    /**
     * Returns the 64-bit RGBA value at the specified point.
     * @param x The X coordinate of the point to get
     * @param y The Y coordinate of the point to get
     * @return The 64-bit RGBA value at the specified point
     * @throws IndexOutOfBoundsException When the point is out of bounds
     */
    long getRGBA(int x, int y) throws IndexOutOfBoundsException;

    /**
     * Returns the 64-bit RGBA value at the specified point.
     * @param p The point to get the RGBA value of
     * @return The 64-bit RGBA value at the specified point
     * @throws IndexOutOfBoundsException When the point is out of bounds
     */
    long getRGBA(@Nonnull Point p) throws IndexOutOfBoundsException;

    /**
     * Sets the 64-bit RGBA value at of the specified point.
     * @param x The X coordinate of the point to set
     * @param y The Y coordinate of ths point to set
     * @param rgba64 The 64-bit RGBA value to set to
     * @throws IndexOutOfBoundsException When the point is out of bounds
     */
    void setRGBA(int x, int y, long rgba64) throws IndexOutOfBoundsException;

    /**
     * Sets the 64-bit RGBA value at the specified point.
     * @param p The point to set the RGBA value of
     * @param rgba64 The 64-bit RGBA value to set to
     * @throws IndexOutOfBoundsException When the point is out of bounds
     */
    void setRGBA(@Nonnull Point p, long rgba64) throws IndexOutOfBoundsException;

    //
    // Colors
    //

    /**
     * Returns the color of the specified point.
     * @param x The X coordinate of the point to get
     * @param y The Y coordinate of the point to get
     * @return The color of the specified point
     * @throws IndexOutOfBoundsException When the point is out of bounds
     */
    @Nonnull
    Color getColor(int x, int y) throws IndexOutOfBoundsException;

    /**
     * Returns the color of the specified point.
     * @param p The point to get the color of
     * @return The color of the specified point
     * @throws IndexOutOfBoundsException When the point is out of bounds
     */
    @Nonnull
    Color getColor(@Nonnull Point p) throws IndexOutOfBoundsException;

    /**
     * Sets the color of the specified point.
     * @param x The X coordinate of the point to set
     * @param y The Y coordinate of the point to set
     * @param c The color to set to
     * @throws IndexOutOfBoundsException When the point is out of bounds
     */
    void setColor(int x, int y, @Nonnull Color c) throws IndexOutOfBoundsException;

    /**
     * Sets the color of the specified point.
     * @param p The point to set the color of
     * @param c The color to set to
     * @throws IndexOutOfBoundsException When the point is out of bounds
     */
    void setColor(@Nonnull Point p, @Nonnull Color c) throws IndexOutOfBoundsException;

    //
    // Manipulation
    //

    /**
     * Creates a new {@link Renderer renderer}, then returns the newly created instance.
     * @return A {@link Renderer} object which can be used to manipulate the image
     */
    @Nonnull
    Renderer createRenderer();

}
