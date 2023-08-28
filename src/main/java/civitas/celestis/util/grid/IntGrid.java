package civitas.celestis.util.grid;

import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * A specialized grid which uses the primitive type {@code int}.
 *
 * @see Grid
 */
public interface IntGrid extends BaseGrid<Integer> {
    //
    // Factory
    //

    /**
     * Creates a new int grid from the provided two-dimensional array of values.
     *
     * @param values The values of which to contain in the grid
     * @return A new grid containing the provided values
     */
    @Nonnull
    static IntGrid of(@Nonnull int[][] values) {
        return IntArrayGrid.of(values);
    }

    //
    // Properties
    //

    /**
     * Returns the number of rows (the height) of this grid.
     *
     * @return The number of rows this grid has
     */
    @Override
    int rows();

    /**
     * Returns the number of columns (the width) of this grid.
     *
     * @return The number of columns this grid has
     */
    @Override
    int columns();

    /**
     * Returns the size (the geometric area) of this grid. In other words, this
     * return the maximum capacity of this grid.
     *
     * @return The number of element this gird can potentially hold
     */
    @Override
    int size();

    //
    // Containment
    //

    /**
     * Checks if this grid contains the provided value {@code v}.
     *
     * @param v The value of which to check for containment
     * @return {@code true} if there is at least one element within this grid which is
     * equal to that of the provided value {@code v}
     */
    boolean contains(int v);

    /**
     * Checks if this grid contains multiple values.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this grid contains every value of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<Integer> i);

    //
    // Accessors
    //

    /**
     * Returns the element at the specified index.
     *
     * @param r The index of the row to get
     * @param c The index of the column to get
     * @return The element at the specified index
     * @throws IndexOutOfBoundsException When the indices are out of bounds
     */
    int get(int r, int c) throws IndexOutOfBoundsException;

    /**
     * Sets the value of the specified index.
     *
     * @param r The index of the row to set
     * @param c The index of the column to set
     * @param v The value of which to assign to the specified index
     * @throws IndexOutOfBoundsException When the indices are out of bounds
     */
    void set(int r, int c, int v) throws IndexOutOfBoundsException;

    //
    // Bulk Operation
    //

    /**
     * Fills this grid, populating every index with the provided value {@code v}.
     *
     * @param v The value of which to fill this grid with
     */
    void fill(int v);

    /**
     * Fills this grid, but only does so within the specified range.
     *
     * @param r1 The starting position's row index
     * @param c1 The starting position's column index
     * @param r2 The ending position's row index
     * @param c2 The ending position's column index
     * @param v  The value of which to fill the specified range with
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void fillRange(int r1, int c1, int r2, int c2, int v) throws IndexOutOfBoundsException;

    /**
     * Applies the provided update function {@code f} to every element of this grid, assigning
     * the return value of the function to the corresponding index of this grid.
     *
     * @param f The function of which to apply to each element of this grid
     */
    void update(@Nonnull IntUnaryOperator f);

    /**
     * Applies the provided update function {@code f} to every element of this grid, assigning
     * the return value of the function to the corresponding index of this grid. The row
     * and column indices are provided as the first and second parameter of the function
     * respectively, and the original element is provided as the third parameter of ths function.
     *
     * @param f The function of which to apply to each element of this grid
     */
    void update(@Nonnull TriFunction<? super Integer, ? super Integer, ? super Integer, Integer> f);

    /**
     * Replaces all instances of the old value with the new value within this grid.
     *
     * @param oldValue The old value of which to replace
     * @param newValue The new value of which to replace to
     */
    void replaceAll(int oldValue, int newValue);

    //
    // Sub Operation
    //

    /**
     * Returns a sub-grid of this grid, whose values are populated from that of this grid's
     * elements within the specified range. This is not a direct reference to this grid,
     * and changes in the sub-grid will not be reflected to the original grid.
     *
     * @param r1 The starting position's row index
     * @param c1 The starting position's column index
     * @param r2 The ending position's row index
     * @param c2 The ending position's column index
     * @return The sub-grid of this grid corresponding to the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    IntGrid subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException;

    /**
     * Sets a sub-grid of this grid, copying values from the provided sub-grid {@code g}.
     * This is not a direct reference to this grid, and changes in the sub-grid will not
     * be reflected to the original grid.
     *
     * @param r1 The starting position's row index
     * @param c1 The starting position's column index
     * @param r2 The ending position's row index
     * @param c2 The ending position's column index
     * @param g  The sub-grid of this grid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setRange(int r1, int c1, int r2, int c2, @Nonnull IntGrid g) throws IndexOutOfBoundsException;

    //
    // Resizing
    //

    /**
     * Returns a resized grid, whose elements are populated from that of this grid's elements.
     * If the requested size is larger than that of this grid's size, the oversized portion will
     * not be populated, leaving it uninitialized as {@code null}.
     *
     * @param rows    The number of rows the resized grid should have
     * @param columns The number of columns the resized grid should have
     * @return A new grid with the specified dimensions
     */
    @Nonnull
    IntGrid resize(int rows, int columns);

    //
    // Transposition
    //

    /**
     * Returns the transpose of this grid. The transpose is a grid whose rows and columns are
     * inverted from that os this grid's rows and columns. The elements are mapped according to
     * the function {@code f({r, c}) -> {c, r}}, where {@code r} represents the row index, and
     * {@code c} represents the column index. In simple terms, the rows and columns are inverted.
     *
     * @return The transpose of this grid
     */
    @Nonnull
    IntGrid transpose();

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to every element of this grid, then returns
     * a new grid whose values are populated from that of the provided function's return values.
     *
     * @param f The function of which to apply to each element of this grid
     * @return The resulting grid
     */
    @Nonnull
    IntGrid map(@Nonnull IntUnaryOperator f);

    /**
     * Applies the provided mapper function {@code f} to every element of this grid, then returns
     * a new grid whose values are populated from that of the provided function's return values.
     *
     * @param f   The function of which to apply to each element of this grid
     * @param <F> The type of element to map this grid to
     * @return The resulting grid
     */
    @Nonnull
    <F> Grid<F> mapToObj(@Nonnull IntFunction<? extends F> f);

    /**
     * Between this grid and the provided grid {@code g}, this applies the merger function {@code f}
     * for each corresponding pair of elements, then returns a new grid whose elements are populated
     * from that of the return values of the merger function {@code f}.
     *
     * @param g The grid of which to merge this grid with
     * @param f The merger function to handle the merging of the two grids
     * @return The resulting grid
     * @throws IllegalArgumentException When the provided grid {@code g}'s dimensions are different
     *                                  from that of this grid's dimensions
     */
    @Nonnull
    IntGrid merge(@Nonnull IntGrid g, @Nonnull IntBinaryOperator f) throws IllegalArgumentException;

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
    Iterator<Integer> iterator();

    /**
     * Executes the provided action for each element of this grid. The order of execution is not
     * guaranteed to be consistent.
     *
     * @param a The action to be performed for each element
     */
    @Override
    void forEach(@Nonnull Consumer<? super Integer> a);

    /**
     * Executes the provided action for each element of this grid. The row and column indices are
     * provided as the first and second parameter of the function respectively, and the current
     * element is provided as the third parameter of the function. The order of execution is not
     * guaranteed to be consistent.
     *
     * @param a The action to be performed for each element
     */
    @Override
    void forEach(@Nonnull TriConsumer<Integer, Integer, ? super Integer> a);

    //
    // Conversion
    //

    /**
     * Returns an array containing every element within this grid. The length of the array is not
     * guaranteed to be equal to the size of this grid, and the order is not guaranteed to be consistent.
     *
     * @return The array representation of this grid
     */
    @Nonnull
    int[] array();

    /**
     * Returns a stream whose source is the elements of this grid.
     *
     * @return A stream whose source is the elements of this grid
     * @see IntStream
     */
    @Nonnull
    IntStream stream();

    /**
     * Returns a collection containing every element within this grid. The size of the collection is
     * not guaranteed to be equal to the size of this grid, and the order is not guaranteed to be consistent,
     * if there even is one.
     *
     * @return The collection representation of this grid
     * @see Collection
     */
    @Nonnull
    @Override
    Collection<Integer> collect();

    /**
     * Returns a set containing every element of this grid. Due to the nature of sets, every duplicate
     * element will be counted as one element. This property can be useful for certain applications.
     *
     * @return The set representation of this grid
     * @see Set
     */
    @Nonnull
    @Override
    Set<Integer> set();

    /**
     * Returns a new grid whose values are populated from that of this grid's values, encapsulated
     * inside a {@link Integer} object.
     *
     * @return The boxed grid representation of this grid
     * @see Grid
     */
    @Nonnull
    Grid<Integer> boxed();

    //
    // Equality
    //

    /**
     * Checks for equality between this grid and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the object is also a int grid, and the dimensions, order of elements,
     * and the elements' values are all equal
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
}
