package civitas.celestis.util;

import civitas.celestis.math.Vector;
import jakarta.annotation.Nonnull;

/**
 * A two-dimensional structure of {@link Vector}s.
 *
 * @param <V> The type of vector this grid should hold
 * @see Grid
 */
public interface VectorGrid<V extends Vector<V>> extends Grid<V> {
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
    VectorGrid<V> add(double s);

    /**
     * Subtracts a scalar from this grid.
     *
     * @param s The scalar to subtract from this grid
     * @return The resulting grid
     */
    @Nonnull
    VectorGrid<V> subtract(double s);

    /**
     * Multiplies this grid by a scalar.
     *
     * @param s The scalar to multiply this grid by
     * @return The resulting grid
     */
    @Nonnull
    VectorGrid<V> multiply(double s);

    /**
     * Divides this grid by a scalar.
     *
     * @param s The scalar to divide this grid by
     * @return The resulting grid
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    @Nonnull
    VectorGrid<V> divide(double s) throws ArithmeticException;

    /**
     * Adds a vector to this grid.
     *
     * @param v The vector to add to this grid
     * @return The resulting grid
     */
    @Nonnull
    VectorGrid<V> add(@Nonnull V v);

    /**
     * Subtracts a vector from this grid.
     *
     * @param v The vector to subtract from this grid
     * @return The resulting grid
     */
    @Nonnull
    VectorGrid<V> subtract(@Nonnull V v);

    /**
     * Adds another grid to this grid.
     *
     * @param g The grid of which to add to this grid
     * @return The resulting grid
     * @throws ArithmeticException When the grids' dimensions are different
     */
    @Nonnull
    VectorGrid<V> add(@Nonnull Grid<? extends V> g) throws ArithmeticException;

    /**
     * Subtracts another grid from this grid.
     *
     * @param g The grid of which to subtract from this grid
     * @return The resulting grid
     * @throws ArithmeticException When the grids' dimensions are different
     */
    @Nonnull
    VectorGrid<V> subtract(@Nonnull Grid<? extends V> g) throws ArithmeticException;
}
