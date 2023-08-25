package civitas.celestis.graphics;

import jakarta.annotation.Nonnull;

import java.util.Arrays;

/**
 * A transient object used to manipulate images.
 */
public class Renderer {
    //
    // Constructors
    //

    /**
     * Creates a new renderer.
     * @param values The 2D array of 64-bit RGBA color values to manipulate
     * @param width The width of the image to manipulate
     * @param height The height of the image to manipulate
     */
    public Renderer(@Nonnull long[][] values, int width, int height) {
        this.values = values;
        this.width = width;
        this.height = height;
    }

    //
    // Variables
    //

    /**
     * The array of 64-bit RGBA color values to manipulate.
     */
    @Nonnull
    protected final long[][] values;

    /**
     * The width of the image this renderer is manipulating.
     */
    protected final int width;

    /**
     * The height of ths image this renderer is manipulating.
     */
    protected final int height;

    //
    // Filling
    //

    /**
     * Fills the entire image with the provided color.
     * @param rgba64 The 64-bit RGBA color value to fill the section with
     * @return A reference to this renderer ({@code this})
     */
    @Nonnull
    public Renderer fill(long rgba64) {
        for (final long[] row : values) {
            Arrays.fill(row, rgba64);
        }

        return this;
    }

    /**
     * Fills a sub-portion of the image with the provided color.
     * @param x1 The starting point's X coordinate
     * @param y1 The starting point's Y coordinate
     * @param x2 The ending point's X coordinate
     * @param y2 The ending point's Y coordinate
     * @param rgba64 The 64-bit RGBA color value to fill the section with
     * @return A reference to this renderer ({@code this})
     */
    @Nonnull
    public Renderer fill(int x1, int y1, int x2, int y2, long rgba64) {
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2; y++) {
                values[y][x] = rgba64;
            }
        }

        return this;
    }

    //
    // Lines
    //

    /**
     * Renders a line to the image.
     * @param x1 The starting point's X coordinate
     * @param y1 The starting point's Y coordinate
     * @param x2 The ending point's X coordinate
     * @param y2 The ending point's Y coordinate
     * @param rgba64 The 64-bit RGBA color value of the line to draw
     * @return A reference to this renderer ({@code this})
     */
    @Nonnull
    public Renderer renderLine(int x1, int y1, int x2, int y2, long rgba64) {
        final int dx = x2 - x1;
        final int dy = y2 - y1;

        // Check for zero
        if (dx == 0 && dy == 0) {
            // No rendering required
            return this;
        }

        // A temporary variable
        int temp;

        // Rearrange points if necessary
        if (dx < 0) {
            temp = x2;
            x2 = x1;
            x1 = temp;
        }

        if (dy < 0) {
             temp = y2;
             y2 = y1;
             y1 = temp;
        }

        // Determine slope
        double slope = (double) dy / (double) dx;

        // Swap X and Y if necessary
        if (!Double.isFinite(slope) || Math.abs(dy) > Math.abs(dx)) {
            temp = y1;
            y1 = x1;
            x1 = temp;

            temp = y2;
            y2 = x2;
            x2 = temp;

            slope = (double) dx / (double) dy;
        }

        // Check for straight diagonals
        if (Math.abs(dx) == Math.abs(dy)) {
            int y = y1;
            for (int x = x1; x <= x2; x++) {
                values[y++][x] = rgba64;
            }

            return this;
        }

        // Draw line
        int y = y1;
        double y_increment = 0;
        for (int x = x1; x <= x2; x++) {
            // Handle Y coordinate
            if (Math.abs(y_increment) > Math.abs(slope)) {
                y = (int) ((double) y + y_increment);
                y_increment = 0;
            } else {
                y_increment += slope;
            }

            values[y][x] = rgba64;
        }

        return this;
    }
}
