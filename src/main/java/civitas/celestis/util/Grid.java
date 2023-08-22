package civitas.celestis.util;

import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.*;

/**
 * A two-dimensional structure of objects.
 * Grids can be traversed by either providing two separate indices
 * for the row and column, or by providing am {@link Index Index} object.
 *
 * @param <E> The type of element this grid should hold
 * @see ArrayGrid
 * @see SyncGrid
 */
public interface Grid<E> extends Iterable<E>, Serializable {
    //
    // Static Methods
    //

    /**
     * Returns a new {@link Index index} with the specified row and column indices.
     *
     * @param row    The index of the row
     * @param column The index of the column
     * @return The index object corresponding to the specified position
     */
    @Nonnull
    static Index newIndex(int row, int column) {
        return new SimpleIndex(row, column);
    }

    //
    // Factory
    //

    /**
     * Given a two-dimensional primitive array of elements, this returns a grid
     * constructed from those elements.
     *
     * @param elements The 2D array of elements to construct the grid from
     * @param <E>      The type of element to contain
     * @return The constructed grid
     */
    @Nonnull
    static <E> Grid<E> of(@Nonnull E[][] elements) {
        return ArrayGrid.of(elements);
    }

    /**
     * Given a two-dimensional primitive array of elements, this returns a thread-safe grid
     * constructed from those elements.
     *
     * @param elements The 2D array of elements to construct the grid from
     * @param <E>      The type of element to contain
     * @return The constructed grid
     */
    @Nonnull
    static <E> Grid<E> syncOf(@Nonnull E[][] elements) {
        return SyncGrid.of(elements);
    }

    //
    // Properties
    //

    /**
     * Returns the size of this grid. (the number of slots this grid has)
     *
     * @return The size of this grid
     */
    int size();

    /**
     * Returns the row count of this grid. (the height)
     *
     * @return The number of rows this grid has
     */
    int rows();

    /**
     * Returns the column count of this grid. (the width)
     *
     * @return The number of columns this grid has
     */
    int columns();

    /**
     * Returns the dimensions of this grid as an {@link Index Index} object.
     *
     * @return The dimensions of this grid, packaged as an index object
     */
    @Nonnull
    Index dimensions();

    //
    // Containment
    //

    /**
     * Checks if this grid contains the provided object {@code obj}.
     *
     * @param obj The object to check for containment
     * @return {@code true} if at least one element of this grid is equal to
     * the provided object {@code obj}
     */
    boolean contains(@Nullable Object obj);

    /**
     * Checks if this grid contains every element within the iterable object {@code i}.
     *
     * @param t The iterable object of which to check for containment
     * @return {@code true} if this grid contains every element of the iterable object {@code i}
     */
    boolean containsAll(@Nonnull Iterable<?> t);

    //
    // Accessors
    //

    /**
     * Returns the element currently present at the specified slot.
     *
     * @param r The index of the row to get
     * @param c The index of the column to get
     * @return The value at the specified slot
     * @throws IndexOutOfBoundsException When the index is out of bounds
     */
    E get(int r, int c) throws IndexOutOfBoundsException;

    /**
     * Returns the {@code i}th element of this grid.
     *
     * @param i The index of the slot to get
     * @return The value at the specified slot
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    E get(@Nonnull Index i) throws IndexOutOfBoundsException;

    /**
     * Sets the value at the specified position.
     *
     * @param r The index of the row to set
     * @param c The index of the column to set
     * @param v The value to assign to the specified slot
     * @throws IndexOutOfBoundsException When the index is out of bounds
     */
    void set(int r, int c, E v) throws IndexOutOfBoundsException;

    /**
     * Sets the value at the specified position.
     *
     * @param i The index of the slot to set
     * @param v The value to assign to the specified slot
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    void set(@Nonnull Index i, E v) throws IndexOutOfBoundsException;

    //
    // Bulk Operation
    //

    /**
     * Applies the provided function {@code f} to every slot of this grid.
     * The return value of the function will be assigned to the corresponding slot.
     *
     * @param f The function of which to apply to each slot of this grid
     */
    void apply(@Nonnull Function<? super E, E> f);

    /**
     * Applies the provided function {@code f} to every slot of this grid.
     * The index of the slot is given as the first parameter, and the original value
     * is given as the second parameter. The return value of the function
     * is then assigned to the corresponding slot.
     *
     * @param f The function of which to apply to each slot of this grid
     */
    void apply(@Nonnull BiFunction<Index, E, ? extends E> f);

    /**
     * Applies the provided function {@code f} to every slot of this grid.
     * The row and column indices of the slot are given as the first and second parameter
     * respectively, and the original value is given as the third parameter.
     * The return value of the function is then assigned to the corresponding slot.
     *
     * @param f The function of which to apply to each slot of this grid
     */
    void apply(@Nonnull TriFunction<Integer, Integer, E, ? extends E> f);

    /**
     * Fills this grid with the provided element {@code v}.
     *
     * @param v The value to fill this grid with
     */
    void fill(E v);

    /**
     * Fills this grid, but only assigns empty slots (slots which are {@code null})
     * to the provided element {@code v}.
     *
     * @param v The value to fill empty slots of this grid with
     */
    void fillEmpty(E v);

    /**
     * Fills this grid, but only if the filter function {@code f} returns {@code true}
     * for the corresponding slot's original value.
     *
     * @param v The value to fill this grid selectively with
     * @param f The filter function of which to test each original element with
     */
    void fillIf(E v, @Nonnull Predicate<? super E> f);

    /**
     * Replaces all instances of the old value to the new value.
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    void replaceAll(E oldValue, E newValue);

    //
    // Sub Operation
    //

    /**
     * Returns a sub-grid of this grid, constructed from the provided range.
     *
     * @param r1 The starting point's row index
     * @param c1 The starting point's column index
     * @param r2 The ending point's row index
     * @param c2 The ending point's column index
     * @return The sub-grid of this grid of the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    Grid<E> subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException;

    /**
     * Returns a sub-grid of this grid, constructed from the provided range.
     *
     * @param i1 The starting index of the sub-grid
     * @param i2 The ending index of the sub-grid
     * @return The sub-grid of this grid of the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    Grid<E> subGrid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException;

    /**
     * Sets a sub-grid of this grid by providing the values in the form of another grid.
     *
     * @param r1 The starting point's row index
     * @param c1 The starting point's column index
     * @param r2 The ending point's row index
     * @param c2 The ending point's column index
     * @param g  The grid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setRange(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends E> g)
            throws IndexOutOfBoundsException;

    /**
     * Sets a sub-grid of this grid by providing the values in the form of another grid.
     *
     * @param i1 The starting index at which to start assigning values
     * @param i2 The ending index at which to stop assigning values
     * @param g  The grid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setRange(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Grid<? extends E> g)
            throws IndexOutOfBoundsException;

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to each element of this grid,
     * then returns a new grid containing the resulting elements.
     *
     * @param f   The function of which to apply to each element of this grid
     * @param <F> The type of element to map this grid to
     * @return The resulting grid
     */
    @Nonnull
    <F> Grid<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Between this grid and the provided grid {@code g}, this applies the merger function {@code f}
     * to each corresponding pair of elements, then returns a new grid containing the resulting elements.
     *
     * @param g   The grid of which to merge this grid with
     * @param f   The merger function to handle the merging of the two grids
     * @param <F> The type of element to merge this grid with
     * @param <G> The type of element to merge the two grids to
     * @return The resulting grid
     * @throws IllegalArgumentException When the two grids' dimensions are different
     */
    @Nonnull
    <F, G> Grid<G> merge(@Nonnull Grid<F> g, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException;

    //
    // Transposition
    //

    /**
     * Transposes this grid, (swaps the rows and columns) then returns the resulting grid.
     *
     * @return The transpose of this grid
     */
    @Nonnull
    Grid<E> transpose();

    //
    // Resizing
    //

    /**
     * Returns a resized grid of {@code r} rows and {@code c} columns, where the values of
     * this grid are mapped to. If the requested size is larger than this grid, the oversized
     * slots will not be populated with values, thus leaving them to be {@code null}.
     *
     * @param r The number of rows the resized grid should have
     * @param c The number of columns the resized grid should have
     * @return The resized grid
     */
    @Nonnull
    Grid<E> resize(int r, int c);

    /**
     * Returns a resized grid of the provided size, where the values of this grid are mapped to.
     * If the requested size is larger than this grid, the oversized slots will not be populated with
     * values, thus leaving them to be {@code null}.
     *
     * @param size The size the resized grid should have
     * @return The resized grid
     */
    @Nonnull
    Grid<E> resize(@Nonnull Index size);

    //
    // Iteration
    //

    /**
     * Returns an iterator of every element within this grid, in a consistent order.
     *
     * @return An iterator of every element within this grid
     */
    @Override
    @Nonnull
    Iterator<E> iterator();

    /**
     * Executes the provided action for each element of this grid. The current
     * value is provided as the input parameter.
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    void forEach(@Nonnull Consumer<? super E> action);

    /**
     * Executes the provided action for each element of this grid. The index
     * of the corresponding slot is given as the first parameter, and the current value
     * is given as the second parameter.
     *
     * @param action The action of which to execute for each element of this grid
     */
    void forEach(@Nonnull BiConsumer<Index, ? super E> action);

    /**
     * Executes the provided action for each element of this grid. The row and
     * column indices are given as the first and second parameter respectively,
     * and the current value if given as the third parameter.
     *
     * @param action The action of which to execute for each element of this grid
     */
    void forEach(@Nonnull TriConsumer<Integer, Integer, ? super E> action);

    //
    // Conversion
    //

    /**
     * Returns a one-dimensional type-safe array containing every element of this grid.
     * Changes in the return value will not be reflected to this grid.
     *
     * @return The array representation of this grid
     */
    @Nonnull
    SafeArray<E> array();

    /**
     * Returns a tuple containing every element of this grid.
     *
     * @return The tuple representation of this grid
     * @see Tuple
     */
    @Nonnull
    Tuple<E> tuple();

    /**
     * Returns a collection containing every element of this grid.
     *
     * @return The collection representation of this grid
     * @see Collection
     */
    @Nonnull
    Collection<E> collect();

    /**
     * Returns a map containing every element of this grid, mapped
     * by their corresponding {@link Index index}.
     *
     * @return The map representation of this grid
     * @see Map
     */
    @Nonnull
    Map<Index, E> map();

    //
    // Equality
    //

    /**
     * Checks for equality between this grid and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a grid, and the dimensions, composition,
     * and order of elements are all equal
     */
    @Override
    boolean equals(@Nullable Object obj);

    //
    // Serialization
    //

    /**
     * Serializes this grid into a string.
     *
     * @return The string representation of this grid
     */
    @Nonnull
    @Override
    String toString();

    //
    // Indexing
    //

    /**
     * The index of a grid. Indices start at {@code 0} and increment from
     * top-to-bottom and left-to-right.
     * <p>
     * 0, 1, 2, 3<br>
     * 4, 5, 6, 7<br>
     * 8, 9, 10, 11<br>
     * <b>In this grid, the value {@code 6} is currently in row {@code 1} and
     * column {@code 2}.</b>
     * </p>
     * <p>
     * When representing the dimensions of a grid, the row index corresponds to
     * the number of rows (the height), and the column index corresponds to the
     * number of columns (the width).
     * </p>
     */
    interface Index {
        /**
         * Returns the row of this index.
         *
         * @return The row of this index
         */
        int row();

        /**
         * Returns the column of this index.
         *
         * @return The column of this index
         */
        int column();

        /**
         * Checks for equality between this index and the provided object {@code obj}.
         *
         * @param obj The object to compare to
         * @return {@code true} if the other object is also an index,
         * and the row and column indices are equal
         */
        @Override
        boolean equals(@Nullable Object obj);
    }

    //
    // Internal
    //

    /**
     * The default implementation of a grid's {@link Index index}.
     */
    final class SimpleIndex implements Index {
        /**
         * Private constructor to ensure this class is used internally.
         *
         * @param row    The row of this index
         * @param column The column of this index
         */
        private SimpleIndex(int row, int column) {
            this.row = row;
            this.column = column;
        }

        /**
         * The row of this index.
         */
        private final int row;

        /**
         * The column of this index.
         */
        private final int column;

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        public int row() {
            return row;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        public int column() {
            return column;
        }

        /**
         * {@inheritDoc}
         *
         * @param obj The object to compare to
         * @return {@inheritDoc}
         */
        @Override
        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof Index i)) return false;
            return row == i.row() && column == i.column();
        }

        /**
         * Serializes this index into a string.
         *
         * @return The string representation of this index
         */
        @Nonnull
        @Override
        public String toString() {
            return "[" + row + ", " + column + "]";
        }
    }
}
