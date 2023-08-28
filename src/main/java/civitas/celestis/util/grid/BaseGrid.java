package civitas.celestis.util.grid;

import civitas.celestis.util.function.TriConsumer;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * The base class for all grids, including primitive specialized grids.
 *
 * @param <E> The type of element this grid should hold
 * @see Grid
 * @see DoubleGrid
 * @see FloatGrid
 * @see LongGrid
 * @see IntGrid
 */
public interface BaseGrid<E> extends Iterable<E>, Serializable {
    //
    // Static Methods
    //

    /**
     * Checks for equality between two instances of {@link BaseGrid grids}. This will return
     * {@code true} if the dimensions, order of elements, and the element's values are all equal.
     * In other words, this returns {@code true} if all corresponding element pairs are equal.
     *
     * @param g1  The first grid to compare
     * @param g2  The second grid to compare
     * @param <T> The element of the first grid (used for copying the grid)
     * @return {@code true} if the grids are considered equal according to the criteria mentioned above
     */
    static <T> boolean equals(@Nullable BaseGrid<T> g1, @Nullable BaseGrid<?> g2) {
        if (g1 == null) return g2 == null;
        if (g2 == null) return false;

        if (g1.rows() != g2.rows() || g1.columns() != g2.columns()) return false;

        final ArrayGrid<T> ag1 = new ArrayGrid<>(g1.rows(), g1.columns());
        g1.forEach(ag1::set);

        final AtomicBoolean notEquals = new AtomicBoolean(false);

        g2.forEach((r, c, v) -> {
            if (notEquals.get()) return;
            if (!Objects.equals(ag1.get(r, c), v)) notEquals.set(true);
        });

        return !notEquals.get();
    }

    //
    // Properties
    //

    /**
     * Returns the number of rows (the height) of this grid.
     *
     * @return The number of rows this grid has
     */
    int rows();

    /**
     * Returns the number of columns (the width) of this grid.
     *
     * @return The number of columns this grid has
     */
    int columns();

    /**
     * Returns the size (the geometric area) of this grid. In other words, this
     * return the maximum capacity of this grid.
     *
     * @return The number of element this gird can potentially hold
     */
    int size();

    //
    // Conversion
    //

    /**
     * Returns a collection containing every element within this grid. The size of the collection is
     * not guaranteed to be equal to the size of this grid, and the order is not guaranteed to be consistent,
     * if there even is one.
     *
     * @return The collection representation of this grid
     * @see Collection
     */
    @Nonnull
    Collection<E> collect();

    /**
     * Returns a set containing every element of this grid. Due to the nature of sets, every duplicate
     * element will be counted as one element. This property can be useful for certain applications.
     *
     * @return The set representation of this grid
     * @see Set
     */
    @Nonnull
    Set<E> set();

    //
    // Iteration
    //

    /**
     * Returns an iterator of every element of this grid.
     *
     * @return An iterator of every element of this grid
     */
    @Nonnull
    @Override
    Iterator<E> iterator();

    /**
     * Executes the provided action for each element of this grid. The order of execution is not
     * guaranteed to be consistent.
     *
     * @param a The action to be performed for each element
     */
    @Override
    void forEach(@Nonnull Consumer<? super E> a);

    /**
     * Executes the provided action for each element of this grid. The row and column indices are
     * provided as the first and second parameter of the function respectively, and the current
     * element is provided as the third parameter of the function. The order of execution is not
     * guaranteed to be consistent.
     *
     * @param a The action to be performed for each element
     */
    void forEach(@Nonnull TriConsumer<Integer, Integer, ? super E> a);
}
