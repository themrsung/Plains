package civitas.celestis.util;

import jakarta.annotation.Nonnull;

/**
 * A two-dimensional structure of numbers.
 *
 * @param <N> The type of number this grid should hold
 * @see Grid
 */
public interface NumericGrid<N extends Number> extends Grid<N> {
    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this grid.
     *
     * @param s The scalar to add to this grid
     * @return The resulting grid
     */
    @Nonnull
    NumericGrid<N> add(@Nonnull N s);

    /**
     * Subtracts a scalar from this grid.
     *
     * @param s The scalar to subtract from this grid
     * @return The resulting grid
     */
    @Nonnull
    NumericGrid<N> subtract(@Nonnull N s);

    /**
     * Multiplies this grid by a scalar.
     *
     * @param s The scalar to multiply this grid by
     * @return The resulting grid
     */
    @Nonnull
    NumericGrid<N> multiply(@Nonnull N s);

    /**
     * Divides this grid by a scalar.
     *
     * @param s The scalar to divide this grid by
     * @return The resulting grid
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    NumericGrid<N> divide(@Nonnull N s) throws ArithmeticException;

    /**
     * Adds another grid to this grid.
     *
     * @param g The grid of which to add to this grid
     * @return The resulting grid
     * @throws ArithmeticException When the grids' dimensions are different
     */
    @Nonnull
    NumericGrid<N> add(@Nonnull Grid<? extends Number> g) throws ArithmeticException;

    /**
     * Subtracts another grid from this grid.
     *
     * @param g The grid of which to subtract from this grid
     * @return The resulting grid
     * @throws ArithmeticException When the grids' dimensions are different
     */
    @Nonnull
    NumericGrid<N> subtract(@Nonnull Grid<? extends Number> g) throws ArithmeticException;

    /**
     * Multiplies this grid by another grid.
     *
     * @param g The grid of which to multiply this grid by
     * @return The resulting grid
     * @throws ArithmeticException When the grids' dimensions are incompatible
     */
    @Nonnull
    NumericGrid<N> multiply(@Nonnull Grid<? extends Number> g) throws ArithmeticException;
}
