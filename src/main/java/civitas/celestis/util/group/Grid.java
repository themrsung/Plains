package civitas.celestis.util.group;

/**
 * A {@code m * n} grid-like structure of objects.
 * <p>
 * Grids have a horizontal size (the number of columns)
 * and a vertical size (the number of rows). The product of these two
 * sizes yields the area of this grid.
 * </p>
 */
public interface Grid<E> extends Group<E> {
    //
    // Dimensions
    //

    /**
     * Returns the number of rows this grid contains.
     *
     * @return The number of rows this grid contains
     */
    int rows();

    /**
     * Returns the number of columns this grid contains.
     *
     * @return The number of columns this grid contains
     */
    int columns();

    /**
     * Returns the area ({@code rows * columns}) of this grid.
     *
     * @return The area of this grid
     */
    int area();
}
