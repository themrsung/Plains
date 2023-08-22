package civitas.celestis.util.grid;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A two-dimensional structure of objects. Grids can be traversed
 * by either providing two separate indices, or by providing an {@link Index index}.
 *
 * @param <E> The type of element this grid should hold
 * @see Index Index
 */
public interface Grid<E> extends Iterable<E>, Serializable {
    //
    // Properties
    //

    /**
     * Returns the size of this grid. The size is defined as the geometric area (rows * columns)
     * of this grid. In other words, this is the maximum capacity of this grid.
     *
     * @return The number of elements this grid can contain
     */
    int size();

    /**
     * Returns the number of rows (the height) of this grid. This is equivalent to the traversable
     * range of this grid along the I axis plus one.
     *
     * @return The number of rows this grid has
     */
    int rows();

    /**
     * Returns the number of columns (the width) of this grid. This is equivalent to the traversable
     * range of this grid along the J axis plus one.
     *
     * @return The number of columns this grid has
     */
    int columns();

    /**
     * Returns the dimensions of this grid in the form of an {@link Index index}.
     *
     * @return An {@link Index index} which represents the dimensions of this grid
     */
    @Nonnull
    Index dimensions();

    //
    // Containment
    //

    /**
     * Checks if this grid contains the provided object {@code obj}.
     *
     * @param obj The object of which to check for containment
     * @return {@code true} if at least one element of this grid is equal
     * to the provided object {@code obj}
     */
    boolean contains(@Nullable Object obj);

    /**
     * Checks if this grid contains multiple objects.
     *
     * @param iterable The iterable object containing the values to check for containment
     * @return {@code true} if this grid contains every element of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<?> iterable);

    //
    // Accessors
    //

    /**
     * Returns the element at the specified index.
     *
     * @param i The I index (the row index) of the element to get
     * @param j The J index (the column index) of the element to get
     * @return The element at the specified index
     * @throws IndexOutOfBoundsException When the index is out of bounds
     */
    E get(int i, int j) throws IndexOutOfBoundsException;

    /**
     * Returns the {@code i}th element of this grid.
     *
     * @param i The index of the element to get
     * @return The {@code i}th element of this grid
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    E get(@Nonnull Index i) throws IndexOutOfBoundsException;

    /**
     * Sets the value of the specified index.
     *
     * @param i The I index (the row index) of the position to assign the value to
     * @param j The J index (the column index) of ths position to assign the value to
     * @param v The value of which to assign to the specified position
     * @throws IndexOutOfBoundsException When the index is out of bounds
     */
    void set(int i, int j, E v) throws IndexOutOfBoundsException;

    /**
     * Sets {@code i}th value of this grid.
     *
     * @param i The index of which to assign the value to
     * @param v The value of which to assign to the specified index
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    void set(@Nonnull Index i, E v) throws IndexOutOfBoundsException;

    //
    // Bulk Operation
    //

    /**
     * Fills this grid, assigning every index of this grid to the provided value {@code v}.
     *
     * @param v The value of which to fill this grid with
     */
    void fill(E v);

    /**
     * Fills this grid, but only does so if the original element at that index is {@code null}.
     *
     * @param v The value of which to conditionally fill this grid with
     */
    void fillEmpty(E v);

    /**
     * Fills this grid, but only within the specified range.
     *
     * @param i1 The starting point's I index
     * @param j1 The starting point's J index
     * @param i2 The ending point's I index
     * @param j2 The ending point's J index
     * @param v  The value of which to fill the specified range with
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void fillRange(int i1, int j1, int i2, int j2, E v) throws IndexOutOfBoundsException;

    /**
     * Fills this grid, but only within the specified range.
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @param v  The value of which to fill the specified range with
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void fillRange(@Nonnull Index i1, @Nonnull Index i2, E v) throws IndexOutOfBoundsException;

    /**
     * Applies the provided function {@code f} to each element of this grid. The return
     * value of the function is then assigned to the corresponding position.
     *
     * @param f The function of which to apply to this grid's elements
     */
    void apply(@Nonnull Function<? super E, E> f);

    /**
     * Applies the provided function {@code f} to each element of this grid. The index of
     * the element is provided as the first element, and the original value is provided as
     * the second element. The return value is then assigned to the corresponding position.
     *
     * @param f The function of which to apply to this grid's elements
     */
    void apply(@Nonnull BiFunction<Index, ? super E, E> f);

    /**
     * Applies the provided function {@code f} to each element of this grid. The IJ
     * indices are provided as the first and second element respectively, and the original
     * value is provided as the third element. The return value is then assigned to the
     * corresponding position.
     *
     * @param f The function of which to apply to this grid's elements
     */
    void apply(@Nonnull TriFunction<Integer, Integer, ? super E, E> f);

    /**
     * Replaces all occurrences of the old value to the new value.
     *
     * @param oldValue The old value of which to replace
     * @param newValue The new value of which to replace to
     */
    void replaceAll(E oldValue, E newValue);

    //
    // Sub Operation
    //

    /**
     * Returns a sub-grid of this grid, whose values are populated from that of the specified range.
     *
     * @param i1 The starting point's I index
     * @param j1 The starting point's J index
     * @param i2 The ending point's I index
     * @param j2 The ending point's J index
     * @return The sub-grid at the specified range
     * @throws IndexOutOfBoundsException When the range is either invalid or out of bounds
     */
    @Nonnull
    Grid<E> subGrid(int i1, int j1, int i2, int j2) throws IndexOutOfBoundsException;

    /**
     * Returns a sub-grid of this grid, whose values are populated from that of the specified range.
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @return The sub-grid at the specified range
     * @throws IndexOutOfBoundsException When the range is either invalid or out of bounds
     */
    @Nonnull
    Grid<E> subGrid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException;

    /**
     * Sets a sub-grid of this grid, populating the elements between the specified range with
     * the values provided by the sub-grid {@code g}.
     *
     * @param i1 The starting point's I index
     * @param j1 The starting point's J index
     * @param i2 The ending point's I index
     * @param j2 The ending point's J index
     * @param g  The sub-grid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setRange(int i1, int j1, int i2, int j2, @Nonnull Grid<? extends E> g)
            throws IndexOutOfBoundsException;

    /**
     * Sets a sub-grid of this grid, populating the elements between the specified range with
     * the values provided by the sub-grid {@code g}.
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @param g  The sub-grid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setRange(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Grid<? extends E> g)
            throws IndexOutOfBoundsException;

    //
    // Resizing
    //

    /**
     * Returns a resized grid whose dimensions are {@code i * j}, and whose values are
     * mapped from that of this grid's elements. If the requested size is larger than that
     * of this grid's size, the oversized part will not be populated, leaving it uninitialized
     * as {@code null}.
     *
     * @param i The width (range of I) of the resized grid
     * @param j The height (range of J) of ths resized grid
     * @return A resized grid whose values are mapped from that of this grid's values
     */
    @Nonnull
    Grid<E> resize(int i, int j);

    /**
     * Returns a resized grid whose dimensions are of the specified size, and whose values
     * are mapped from that of this grid's elements. If the requested size is larger than that
     * of this grid's size, the oversized part will not be populated, leaving it uninitialized
     * as {@code null}.
     *
     * @param size The size of the resized grid
     * @return A resized grid whose values are mapped from that of this grid's values
     */
    @Nonnull
    Grid<E> resize(@Nonnull Index size);

    //
    // Filtration
    //

    /**
     * Tests every element of this grid with provided filter function {@code f}, collects only
     * the values which the filter function returns {@code true} to, then returns a new grid
     * with the same dimensions as this grid, but only containing the filtered values.
     *
     * @param f The filter function of which to test each element of this grid with
     * @return The filtered grid
     */
    @Nonnull
    Grid<E> filter(@Nonnull Predicate<? super E> f);

    //
    // Mapping
    //

    /**
     * Applies the provided mapper function {@code f} to each element of this grid, then returns
     * a new grid containing the resulting values.
     *
     * @param f   The mapper function of which to apply to each element of this grid
     * @param <F> The type of element to map this grid to
     * @return The resulting grid
     */
    @Nonnull
    <F> Grid<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Between this grid and the provided grid {@code f}, this applies the provided merger
     * function {@code f} to each corresponding pair of elements, then returns a new grid whose
     * values are populated from that of the resulting values.
     *
     * @param g   The grid of which to merge this grid with
     * @param f   The merger function to handle the merging of the two grids
     * @param <F> The type of element to merge this grid with
     * @param <G> The type of element to merge the two grids to
     * @return The resulting grid
     * @throws IllegalArgumentException When the provided grid {@code g}'s dimensions are different
     *                                  from that of this grid's dimensions
     */
    @Nonnull
    <F, G> Grid<G> merge(@Nonnull Grid<F> g, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException;

    //
    // Conversion
    //

    /**
     * Converts the elements in this grid into a one-dimensional array,
     * then returns the resulting array.
     *
     * @return The array representation of this grid
     */
    @Nonnull
    E[] array();

    /**
     * Converts the elements in this grid into a type-safe one-dimensional array,
     * then returns the resulting array.
     *
     * @return The type-safe array representation of this grid
     */
    @Nonnull
    SafeArray<E> safeArray();

    /**
     * Returns a stream object whose source is the elements of this grid.
     *
     * @return A stream object derived from this grid's elements
     */
    @Nonnull
    Stream<E> stream();

    /**
     * Converts this grid into a map of {@link Index index}-value pairs,
     * then returns the resulting map.
     *
     * @return The map representation of this grid
     */
    @Nonnull
    Map<? extends Index, E> map();

    /**
     * Returns a collection containing every element of this grid.
     *
     * @return The collection representation of this grid
     */
    @Nonnull
    Collection<E> collect();

    /**
     * Returns a tuple containing every element of this grid.
     *
     * @return The tuple representation of this grid
     */
    @Nonnull
    Tuple<E> tuple();

    //
    // Iteration
    //

    /**
     * Returns an iterator of this grid's elements.
     * The order of iteration is not guaranteed to be consistent.
     *
     * @return An iterator of this grid's elements
     */
    @Nonnull
    @Override
    Iterator<E> iterator();

    /**
     * Executes the provided action for each element of this grid.
     *
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    void forEach(@Nonnull Consumer<? super E> action);

    /**
     * Executes the provided action for each element of this grid. The index of the
     * element is provided as the first parameter, and the current value is provided
     * as the second parameter.
     *
     * @param action The action of which to execute for each element of this grid
     */
    void forEach(@Nonnull BiConsumer<Index, ? super E> action);

    /**
     * Executes the provided action for each element of this grid. The IJ indices
     * are provided as the first and second parameter respectively, and the current
     * value is provided as the third parameter.
     *
     * @param action The action of which to execute for each element of this grid
     */
    void forEach(@Nonnull TriConsumer<Integer, Integer, ? super E> action);

    //
    // Copying
    //

    /**
     * Returns a new grid whose dimensions, order of elements, and composition are all
     * equal to this grid. In other words, this operation performs a shallow copy of this grid.
     *
     * @return A shallow copy of this grid
     */
    @Nonnull
    Grid<E> copy();

    //
    // Equality
    //

    /**
     * Checks for equality between this grid and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a grid, and the dimensions, order of
     * elements, and composition are all equal
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
     * Creates a new {@link Index index} from the provided IJ indices.
     *
     * @param i The I component (the row index) of the index
     * @param j The J component (the column index) of the index
     * @return The constructed index object
     */
    @Nonnull
    static Index index(int i, int j) {
        return new GridIndex(i, j);
    }

    /**
     * An index object which can be used to traverse a grid. The I component
     * represents the row index, and the J component represents the column index.
     */
    interface Index {
        //
        // Components
        //

        /**
         * Returns the I component (the row index) of this index.
         *
         * @return The I component of this index
         */
        int i();

        /**
         * Returns the J component (the column index) of this index.
         *
         * @return The J component of this index
         */
        int j();

        //
        // Equality
        //

        /**
         * Checks for equality between this index and the provided object {@code obj}.
         *
         * @param obj The object to compare to
         * @return {@code true} if the other object is also an index, and the IJ components are equal
         */
        @Override
        boolean equals(@Nullable Object obj);

        //
        // Serialization
        //

        /**
         * Serializes this index into a string. The standard format is "{@code [i, j]}".
         *
         * @return The string representation of this index
         * @see GridIndex#toString() Standard Formatting
         */
        @Nonnull
        @Override
        String toString();
    }
}
