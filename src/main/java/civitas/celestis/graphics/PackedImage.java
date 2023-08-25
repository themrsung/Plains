package civitas.celestis.graphics;

import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * An image which stores colors in the packed 64-bit RGBA form.
 * Packed images are memory-efficient compared to other implementations, but
 * harder to manipulate due to their packed nature.
 * @see Image
 */
public class PackedImage implements Image {
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
     * Creates a new image.
     * @param width The width of this image
     * @param height The height of this image
     */
    public PackedImage(int width, int height) {
        this.width = width;
        this.height = height;
        this.values = new long[height][width];
    }

    //
    // Variables
    //

    /**
     * The internal two-dimensional array of color values.
     */
    @Nonnull
    private final long[][] values;

    /**
     * The width of this image.
     */
    protected final int width;

    /**
     * The height of this image.
     */
    protected final int height;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Point getDimensions() {
        return new Point(width, height);
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public int getHeight() {
        return height;
    }

    //
    // RGBA
    //

    /**
     * {@inheritDoc}
     * @param x The X coordinate of the point to get
     * @param y The Y coordinate of the point to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public long getRGBA(int x, int y) throws IndexOutOfBoundsException {
        return values[y][x];
    }

    /**
     * {@inheritDoc}
     * @param p The point to get the RGBA value of
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public long getRGBA(@Nonnull Point p) throws IndexOutOfBoundsException {
        return values[p.y][p.x];
    }

    /**
     * {@inheritDoc}
     * @param x The X coordinate of the point to set
     * @param y The Y coordinate of ths point to set
     * @param rgba64 The 64-bit RGBA value to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void setRGBA(int x, int y, long rgba64) throws IndexOutOfBoundsException {
        values[y][x] = rgba64;
    }

    /**
     * {@inheritDoc}
     * @param p The point to set the RGBA value of
     * @param rgba64 The 64-bit RGBA value to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void setRGBA(@Nonnull Point p, long rgba64) throws IndexOutOfBoundsException {
        values[p.y][p.x] = rgba64;
    }

    //
    // Colors
    //

    /**
     * {@inheritDoc}
     * @param x The X coordinate of the point to get
     * @param y The Y coordinate of the point to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Color getColor(int x, int y) throws IndexOutOfBoundsException {
        return Color.of(values[y][x]);
    }

    /**
     * {@inheritDoc}
     * @param p The point to get the color of
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Color getColor(@Nonnull Point p) throws IndexOutOfBoundsException {
        return Color.of(values[p.y][p.x]);
    }

    /**
     * {@inheritDoc}
     * @param x The X coordinate of the point to set
     * @param y The Y coordinate of the point to set
     * @param c The color to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void setColor(int x, int y, @Nonnull Color c) throws IndexOutOfBoundsException {
        values[y][x] = c.rgba64();
    }

    /**
     * {@inheritDoc}
     * @param p The point to set the color of
     * @param c The color to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void setColor(@Nonnull Point p, @Nonnull Color c) throws IndexOutOfBoundsException {
        values[p.y][p.x] = c.rgba64();
    }
}
