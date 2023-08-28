package civitas.celestis.util.grid;

import civitas.celestis.util.function.TriConsumer;
import jakarta.annotation.Nonnull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The base class for all grids, including primitive specialized grids.
 * @param <E> The type of element this grid should hold
 * @see Grid
 */
public interface BaseGrid<E> extends Iterable<E>, Serializable {
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
