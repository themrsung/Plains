package civitas.celestis.graphics;

import civitas.celestis.util.grid.Grid;
import civitas.celestis.util.grid.LongGrid;
import jakarta.annotation.Nonnull;

/**
 * Represents a two-dimensional array of color values. Images are used as a
 * container for holding rendered information within the Plains API.
 * <p>
 * Images can be traversed by either providing separate X and Y coordinates,
 * or by providing a {@link Point point} which represents the coordinate.
 * </p>
 */
public class Image {
    //
    // Constructors
    //

    /**
     * Creates a new image.
     * @param width The width of this image in pixels
     * @param height The height of this image in pixels
     */
    public Image(int width, int height) {
        this.pixels = new LongGrid(height, width);
    }

    //
    // Variables
    //

    /**
     * The internal grid of pixels. Note that the X and Y coordinate are inverted.
     * Rows represent the Y coordinate, and columns represent the X coordinate.
     */
    @Nonnull
    protected final Grid<Long> pixels;

    //
    // Getters
    //

    /**
     * Returns the color at the specified pixel.
     * @param x The X coordinate of the pixel
     * @param y The Y coordinate of the pixel
     * @return The color at the specified pixel
     */
    @Nonnull
    public Color getColor(int x, int y) {
        return new PackedColor(pixels.get(y, x));
    }

    /**
     * Returns the color at the specified pixel.
     * @param p The pixel's coordinates
     * @return The color at the specified pixel
     */
    @Nonnull
    public Color getColor(@Nonnull Point p) {
        return new PackedColor(pixels.get(p.y, p.x));
    }

    //
    // Setters
    //
}
