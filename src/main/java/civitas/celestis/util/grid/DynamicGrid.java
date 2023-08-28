package civitas.celestis.util.grid;

import jakarta.annotation.Nonnull;

/**
 * A grid whose size can be changed without requiring re-instantiation.
 * Dynamic grids use composite data structures as opposed to primitive arrays,
 * and thus have more overhead in terms of memory consumption.
 *
 * @param <E> The type of element this grid should hold
 * @see Grid
 */
public interface DynamicGrid<E> extends Grid<E> {
    //
    // Factory
    //

    /**
     * Creates a new dynamic grid from a two-dimensional array of values.
     *
     * @param values The values of which to contain in the grid
     * @param <E>    The type of element to contain
     * @return The constructed grid instance
     */
    @Nonnull
    static <E> DynamicGrid<E> of(@Nonnull E[][] values) {
        return HashGrid.of(values);
    }

    //
    // Removal
    //

    /**
     * Clears all entries within this grid, removing them from memory. This differs from
     * filling the grid with {@code null} in that it reduces the footprint of this grid.
     * This is the equivalent of setting this grid's size to {@code {0, 0}}, then resizing
     * it back to its original size.
     */
    void clear();

    /**
     * Removes the element from the specified position. This differs from setting the
     * value to {@code null} in that it physically removes the entry corresponding to
     * the specified index, as opposed to simply setting the value to {@code null}.
     *
     * @param r The index of the row to remove
     * @param c The index of the column to remove
     * @return {@code true} if there was a value (including {@code null}) at the specified position,
     * and thus the state of this grid has changed as a result of this operation
     */
    boolean remove(int r, int c);

    //
    // Resizing
    //

    /**
     * Cleans this grid, removing all instance of {@code null}.
     * This does not trim the grid to its minimum possible size.
     *
     * @see #trim()
     * @see #cleanAndTrim()
     */
    void clean();

    /**
     * Trims this grid, reducing its size to the minimum possible size
     * required to hold all elements. This does not clean {@code null} values.
     *
     * @see #clean()
     * @see #cleanAndTrim()
     */
    void trim();

    /**
     * Cleans this grid, then trims it to its minimum possible size. In other words,
     * this performs {@link #clean()}, then {@link #trim() trims} the grid.
     *
     * @see #clean()
     * @see #trim()
     */
    void cleanAndTrim();

    /**
     * Forcefully sets the size of this grid, without regard to the data's integrity.
     * If this operation results in the reduction of this grid's size, all elements which are
     * now outside the bounds of this grid will be removed.
     *
     * @param rows    The number rows to resize to
     * @param columns The number of columns to resize to
     */
    void setSize(int rows, int columns);
}
