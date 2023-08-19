package civitas.celestis.util.group;

import civitas.celestis.util.Transformable;
import civitas.celestis.util.map.Mappable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * A {@code m * n} grid-like structure of objects.
 * <p>
 * Grids have a horizontal size (the number of columns)
 * and a vertical size (the number of rows). The product of these two
 * sizes yields the area of this grid.
 * </p>
 * <p>
 * Grids can be traversed and/or accessed by either providing the row
 * and column index separately, or by providing an {@link Index Index}.
 * </p>
 * <p>
 * Grids can also be converted to a {@link java.util.Map Map}
 * of {@link Index Index} and {@code E} by calling {@link #map()}.
 * </p>
 *
 * @param <E> The type of element this grid should hold
 * @see Index Index
 * @see ArrayGrid
 * @see civitas.celestis.math.matrix.Matrix Matrix
 * @see civitas.celestis.math.matrix.LongMatrix LongMatrix
 * @see civitas.celestis.math.matrix.BigMatrix BigMatrix
 * @see Group
 * @see Mappable
 */
public interface Grid<E> extends Group<E>, Mappable<Grid.Index, E>, Iterable<E> {
    //
    // Indexing
    //

    /**
     * Returns an {@link Index Index} object constructed from the provided
     * row and column indices.
     *
     * @param r The index of the row to point to
     * @param c The index of the column to point to
     * @return The {@link Index} object representing the provided position in a grid
     */
    @Nonnull
    static Index indexOf(int r, int c) {
        return new GridIndex(r, c);
    }

    /**
     * Returns an {@link Index Index} object constructed from the provided
     * tuple of integers. The tuple must be a tuple of size {@code 2},
     * and the first and second component corresponding to row and column respectively.
     *
     * @param t The tuple of which to construct ths index object with
     * @return The {@link Index} object representing the provided position in a grid
     */
    @Nonnull
    static Index indexOf(@Nonnull Tuple<Integer> t) {
        return new GridIndex(t);
    }

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
    @Override
    int size();

    /**
     * Returns the dimensions of this grid.
     *
     * @return The dimensions of this grid
     * @see Index Index
     */
    @Nonnull
    Index dimensions();

    //
    // Getters
    //

    /**
     * Returns an element of this grid at the specified position.
     *
     * @param r The index of row to get
     * @param c The index of column to get
     * @return The element at the specified position
     * @throws IndexOutOfBoundsException When the index is out of bounds
     */
    E get(int r, int c) throws IndexOutOfBoundsException;

    /**
     * Returns the {@code i}th element of this grid.
     *
     * @param i The index of element to get
     * @return The {@code i}th element of this grid
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     * @see #indexOf(int, int)
     */
    E get(@Nonnull Index i) throws IndexOutOfBoundsException;

    //
    // Setters
    //

    /**
     * Sets the element at the specified position to the provided value {@code v}.
     *
     * @param r The index of row to set
     * @param c The index of column to set
     * @param v The value to set to
     * @throws IndexOutOfBoundsException When the index is out of bounds
     */
    void set(int r, int c, E v) throws IndexOutOfBoundsException;

    /**
     * Assigns the provided value {@code v} to the position {@code i}.
     *
     * @param i The index of element to set
     * @param v The value to assign to the {@code i}th position of this grid
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     * @see #indexOf(int, int)
     */
    void set(@Nonnull Index i, E v) throws IndexOutOfBoundsException;

    //
    // Bulk Operation
    //

    /**
     * Fills this grid, assigning every value within this
     * grid to the provided value {@code v}.
     *
     * @param v The value to fill this grid with
     */
    void fill(E v);

    /**
     * Fills this grid, but only assigns the value to positions which are currently {@code null}.
     * This is the equivalent of {@link #replaceAll(E, E)}.
     *
     * @param v The value to replace {@code null} values with
     */
    void fillEmpty(E v);

    /**
     * Fills this grid, but only assigns the value if the filter function {@code f} returns
     * {@code true} for the existing value at the corresponding index.
     *
     * @param v The value to fill this grid with
     * @param f The filter function to use
     */
    void fillIf(E v, @Nonnull Predicate<? super E> f);

    /**
     * Replaces all instances of the old value to the new value.
     *
     * @param oldValue The value to replace
     * @param newValue The value to replace to
     */
    void replaceAll(E oldValue, E newValue);

    //
    // Sub-operation
    //

    /**
     * Returns a sub-grid of this grid.
     * The return value will have a row count of {@code r2 - r1},
     * and a column count of {@code c2 - c1}.
     *
     * @param r1 The starting point's row index
     * @param c1 The starting point's column index
     * @param r2 The ending point's row index
     * @param c2 The ending point's column index
     * @return The sub-grid of the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    Grid<E> subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException;

    /**
     * Returns a sub-grid of this grid.
     * The return value will have a row count of {@code i2.x() - i1.x()},
     * and a column count of {@code i2.y() - i1.y()}.
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @return The sub-grid of the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    Grid<E> subGrid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException;

    /**
     * Sets a sub-grid of this grid.
     *
     * @param r1 The starting point's row index
     * @param c1 The starting point's column index
     * @param r2 The ending point's row index
     * @param c2 The ending point's column index
     * @param g  The grid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setAll(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends E> g) throws IndexOutOfBoundsException;

    /**
     * Sets a sub-grid of this grid.
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @param g  The grid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setAll(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Grid<? extends E> g) throws IndexOutOfBoundsException;

    //
    // Transformation
    //

    /**
     * Returns a new grid where the rows and columns are inverted.
     * The values are mapped according to the function {@code f(p{x, y}) -> p{y, x}}.
     *
     * @return The transpose of this grid
     */
    @Nonnull
    Grid<E> transpose();

    /**
     * Returns a resized grid of which the components of this grid are mapped to.
     *
     * @param r The number of rows the new grid should have
     * @param c The number of columns the new grid should have
     * @return The resized grid
     */
    @Nonnull
    Grid<E> resize(int r, int c);

    /**
     * Returns a resized grid of which the components of this grid are mapped to.
     *
     * @param size The size of the new grid
     * @return The resized grid
     */
    @Nonnull
    Grid<E> resize(@Nonnull Index size);

    /**
     * Merges this grid with another grid of the same dimensions.
     * The merger function {@code f} is applied to each component, and the
     * resulting grid is returned.
     * <p>
     * For example, if a sum function {@code (x, y) -> x + y} was provided,
     * this would return the sum of the two grids of numbers.
     * </p>
     *
     * @param g The other grid to merge with
     * @param f The merger function to apply
     * @return The resulting grid
     * @throws IllegalArgumentException When the provided grid {@code g}'s dimensions do not
     *                                  match to this grid's dimensions
     */
    @Nonnull
    Grid<E> merge(@Nonnull Grid<E> g, @Nonnull BiFunction<? super E, ? super E, E> f) throws IllegalArgumentException;

    //
    // Equality
    //

    /**
     * Checks for equality between this grid and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a grid, and the dimensions,
     * composition of elements, and the order of elements all match
     */
    @Override
    boolean equals(@Nullable Object obj);

    //
    // Index
    //

    /**
     * An index which can be used to traverse through a grid. Grids will accept any instance of
     * this interface as input for an index.
     *
     * @see #indexOf(int, int)
     * @see GridIndex
     */
    interface Index {
        /**
         * Returns the row this index is pointing to.
         *
         * @return The row of this index
         */
        int row();

        /**
         * Returns the column this index is pointing to.
         *
         * @return The column of this index
         */
        int column();
    }
}

/**
 * The default instance of {@link Grid.Index Index}.
 *
 * @see Grid.Index Index
 */
class GridIndex extends Pair<Integer> implements Grid.Index {
    //
    // Constructors
    //

    /**
     * Creates a new grid index.
     *
     * @param r The index of the row
     * @param c The index of the column
     */
    GridIndex(int r, int c) {
        super(r, c);
    }

    /**
     * Creates a new grid index.
     *
     * @param t The tuple of which to copy indices from
     */
    GridIndex(@Nonnull Tuple<Integer> t) {
        super(t);
    }

    //
    // Methods
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public final int row() {
        return a;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public final int column() {
        return b;
    }

    //
    // Equality
    //

    /**
     * Checks for equality between this index the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also an index, and the row and column index match
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Grid.Index i)) return false;
        return a == i.row() && b == i.column();
    }
}