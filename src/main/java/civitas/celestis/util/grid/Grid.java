package civitas.celestis.util.grid;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.function.TriConsumer;
import civitas.celestis.util.function.TriFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A two-dimensional structure of elements. Grids can be traversed by providing
 * two indices: one for the row, and one for the column.
 * <p>
 * Static grids which use two-dimensional arrays as their underlying data structure
 * are fixed-size, meaning the dimensions cannot be changes without re-instantiation.
 * </p>
 * <p>
 * On the other hand, {@link DynamicGrid dynamic grids} use a more flexible underlying
 * data structure, allowing the resizing of the grid without re-instantiation. Note
 * that dynamic grids have more overhead in terms of memory consumption, and primitive
 * types are not supported. (the boxed object types such as {@link Double}s are used)
 * </p>
 *
 * @param <E> The type of element this grid should hold
 * @see ArrayGrid
 * @see DynamicGrid
 * @see SyncGrid
 * @see AtomicGrid
 * @see DoubleGrid
 * @see LongGrid
 * @see IntGrid
 */
public interface Grid<E> extends Iterable<E>, Serializable {
    //
    // Factory
    //

    /**
     * Creates a new grid from a two-dimensional array of values.
     *
     * @param values The values of which to contain in the grid
     * @param <E>    The type of element to contain
     * @return The constructed grid instance
     */
    @Nonnull
    static <E> Grid<E> of(@Nonnull E[][] values) {
        return ArrayGrid.of(values);
    }

    /**
     * Creates a new grid from a two-dimensional array of values.
     *
     * @param values The values of which to contain in the grid
     * @return The constructed grid instance
     * @see DoubleGrid
     */
    @Nonnull
    static Grid<Double> ofDouble(@Nonnull double[][] values) {
        return DoubleGrid.of(values);
    }

    /**
     * Creates a new grid from a two-dimensional array of values.
     *
     * @param values The values of which to contain in the grid
     * @return The constructed grid instance
     * @see LongGrid
     */
    @Nonnull
    static Grid<Long> ofLong(@Nonnull long[][] values) {
        return LongGrid.of(values);
    }

    /**
     * Creates a new grid from a two-dimensional array of values.
     *
     * @param values The values of which to contain in the grid
     * @return The constructed grid instance
     * @see IntGrid
     */
    @Nonnull
    static Grid<Integer> ofInt(@Nonnull int[][] values) {
        return IntGrid.of(values);
    }

    /**
     * Creates a new dynamic grid from a two-dimensional array of values.
     *
     * @param values The values of which to contain in the grid
     * @param <E>    The type of element to contain
     * @return The constructed dynamic grid instance
     * @see DynamicGrid
     */
    @Nonnull
    static <E> Grid<E> dynamicOf(@Nonnull E[][] values) {
        return HashGrid.of(values);
    }

    /**
     * Creates a new thread-safe grid from a two-dimensional array of values.
     *
     * @param values The values of which to contain in the grid
     * @param <E>    The type of element to contain
     * @return The constructed thread-safe grid instance
     * @see SyncGrid
     */
    @Nonnull
    static <E> Grid<E> syncOf(@Nonnull E[][] values) {
        return SyncGrid.of(values);
    }

    /**
     * Creates a new atomic grid form a two-dimensional array of values.
     *
     * @param values The values of which to contain in the grid
     * @param <E>    The type of element to contain
     * @return The constructed atomic grid instance
     * @see AtomicGrid
     */
    @Nonnull
    static <E> Grid<E> atomicOf(@Nonnull E[][] values) {
        return AtomicGrid.of(values);
    }

    /**
     * Creates a copy of an existing grid.
     *
     * @param g   The grid of which to copy elements from
     * @param <E> The type of element to contain
     * @return A shallow copy of the provided grid {@code g}
     */
    @Nonnull
    static <E> Grid<E> copyOf(@Nonnull Grid<? extends E> g) {
        return new ArrayGrid<>(g);
    }

    /**
     * Creates a dynamic copy of an existing grid.
     *
     * @param g   The grid of which to copy elements from
     * @param <E> The type of element to contain
     * @return A dynamic shallow copy of the provided grid {@code g}
     * @see DynamicGrid
     */
    @Nonnull
    static <E> Grid<E> dynamicCopyOf(@Nonnull Grid<? extends E> g) {
        return new HashGrid<>(g);
    }

    /**
     * Creates a thread-safe copy of an existing grid.
     *
     * @param g   The grid of which to copy elements from
     * @param <E> The type of element to contain
     * @return A thread-safe shallow copy of the provided grid {@code g}
     * @see SyncGrid
     */
    @Nonnull
    static <E> Grid<E> syncCopyOf(@Nonnull Grid<? extends E> g) {
        return new SyncGrid<>(g);
    }

    /**
     * Creates an atomic copy of an existing grid.
     *
     * @param g   The grid of which to copy elements from
     * @param <E> The type of element to contain
     * @return An atomic shallow copy of the provided grid {@code g}
     * @see AtomicGrid
     */
    @Nonnull
    static <E> Grid<E> atomicCopyOf(@Nonnull Grid<? extends E> g) {
        return new AtomicGrid<>(g);
    }

    /**
     * Creates a {@code double}-typed copy of an existing grid.
     *
     * @param g The grid of which to copy elements from
     * @return A {@code double}-typed shallow copy of the provided grid {@code g}
     * @see DoubleGrid
     */
    @Nonnull
    static Grid<Double> doubleCopyOf(@Nonnull Grid<? extends Number> g) {
        return new DoubleGrid(g);
    }

    /**
     * Creates a {@code long}-typed copy of an existing grid.
     *
     * @param g The grid of which to copy elements from
     * @return A {@code long}-typed shallow copy of the provided grid {@code g}
     * @see LongGrid
     */
    @Nonnull
    static Grid<Long> longCopyOf(@Nonnull Grid<? extends Number> g) {
        return new LongGrid(g);
    }

    /**
     * Creates an {@code int}-typed copy of an existing grid.
     *
     * @param g The grid of which to copy elements from
     * @return An {@code int}-typed shallow copy of the provided grid {@code g}
     * @see IntGrid
     */
    @Nonnull
    static Grid<Integer> intCopyOf(@Nonnull Grid<? extends Number> g) {
        return new IntGrid(g);
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
    // Containment
    //

    /**
     * Checks if this grid contains the provided object {@code obj}.
     *
     * @param obj The object of which to check for containment
     * @return {@code true} if there is at least one element within this grid is
     * equal to that of the provided object {@code obj}
     */
    boolean contains(@Nullable Object obj);

    /**
     * Checks if this grid contains multiple objects.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this grid contains every element of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<?> i);

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
    E get(int r, int c) throws IndexOutOfBoundsException;

    /**
     * Returns the element at the specified index, but returns the fallback value
     * if the existing element is {@code null}.
     *
     * @param r        The index of the row to get
     * @param c        The index of the column to get
     * @param fallback The fallback value of which to return if the element is {@code null}
     * @return The element at the specified index, or the fallback value if the existing
     * element is equal to {@code null}
     * @throws IndexOutOfBoundsException When the indices are out of bounds
     */
    E getOrDefault(int r, int c, E fallback) throws IndexOutOfBoundsException;

    /**
     * Sets the value of the specified index.
     *
     * @param r The index of the row to set
     * @param c The index of the column to set
     * @param v The value of which to assign to the specified index
     * @throws IndexOutOfBoundsException When the indices are out of bounds
     */
    void set(int r, int c, E v) throws IndexOutOfBoundsException;

    //
    // Bulk Operation
    //

    /**
     * Fills this grid, populating every index with the provided value {@code v}.
     *
     * @param v The value of which to fill this grid with
     */
    void fill(E v);

    /**
     * Fills this grid, but only does so if the original element currently occupying the
     * corresponding slot is {@code null}.
     *
     * @param v The values of which to selectively fill this grid with
     */
    void fillEmpty(E v);

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
    void fillRange(int r1, int c1, int r2, int c2, E v) throws IndexOutOfBoundsException;

    /**
     * Applies the provided function {@code f} to every element of this grid, then assigns
     * the return value of the function to the corresponding index of this grid.
     *
     * @param f The function of which to apply to each element of this grid
     */
    void apply(@Nonnull Function<? super E, E> f);

    /**
     * Applies the provided function {@code f} to every element of this grid, then assigns
     * the return value of the function to the corresponding index of this grid. The row
     * and column indices are provided as the first and second parameter of the function
     * respectively, and the original element is provided as the third parameter of ths function.
     *
     * @param f The function of which to apply to each element of this grid
     */
    void apply(@Nonnull TriFunction<Integer, Integer, ? super E, E> f);

    /**
     * Replaces all instances of the old value with the new value within this grid.
     *
     * @param oldValue The old value of which to replace
     * @param newValue The new value of which to replace to
     */
    void replaceAll(E oldValue, E newValue);


    //
    // Sub Operation
    //

    /**
     * Returns a sub-grid of this grid, whose values are populated from that of this grid's
     * elements within the specified range.
     *
     * @param r1 The starting position's row index
     * @param c1 The starting position's column index
     * @param r2 The ending position's row index
     * @param c2 The ending position's column index
     * @return The sub-grid of this grid corresponding to the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    Grid<E> subGrid(int r1, int c1, int r2, int c2) throws IndexOutOfBoundsException;

    /**
     * Sets a sub-grid of this grid, copying values from the provided sub-grid {@code g}.
     *
     * @param r1 The starting position's row index
     * @param c1 The starting position's column index
     * @param r2 The ending position's row index
     * @param c2 The ending position's column index
     * @param g  The sub-grid of this grid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setRange(int r1, int c1, int r2, int c2, @Nonnull Grid<? extends E> g) throws IndexOutOfBoundsException;

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
    Grid<E> resize(int rows, int columns);

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
    Grid<E> transpose();

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to every element of this grid, then returns
     * a new grid whose values are populated from that of ths function's return values.
     *
     * @param f   The function of which to apply to each element of this grid
     * @param <F> The type of element to map this grid to
     * @return The resulting grid
     */
    @Nonnull
    <F> Grid<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Between this grid and the provided grid {@code g}, this applies the merger function {@code f}
     * for each corresponding pair of elements, then returns a new grid whose elements are populated
     * from that of the return values of the merger function {@code f}.
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
     * Returns an array containing every element within this grid. The length of this array is not
     * guaranteed to be equal to the size of this grid, and the order is not guaranteed to be consistent.
     *
     * @return The array representation of this grid
     */
    @Nonnull
    E[] array();

    /**
     * Returns a type-safe array containing every element within this grid. The length of this array is not
     * guaranteed to be equal to the size of this grid, and the order is not guaranteed to be consistent.
     *
     * @return The type-safe array representation of this grid
     * @see SafeArray
     */
    @Nonnull
    SafeArray<E> safeArray();

    /**
     * Returns a stream whose source is the elements of this grid.
     *
     * @return A stream whose source is the elements of this grid
     * @see Stream
     */
    @Nonnull
    Stream<E> stream();

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
     * @param action The action of which to execute for each element of this grid
     */
    @Override
    void forEach(@Nonnull Consumer<? super E> action);

    /**
     * Executes the provided action for each element of this grid. The row and column indices are
     * provided as the first and second parameter of the function respectively, and the current
     * element is provided as the third parameter of the function. The order of execution is not
     * guaranteed to be consistent.
     *
     * @param action The action of which to execute for each element of this grid
     */
    void forEach(@Nonnull TriConsumer<Integer, Integer, ? super E> action);

    //
    // Copying
    //

    /**
     * Returns an identical copy of this grid, whose dimensions, order of elements, and composition
     * are all equal to that of this grid. In other words, this performs a shallow copy of this grid,
     * then returns the copied grid.
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
     * @return {@code true} if the other object is also a grid, and the dimensions, order of elements,
     * and composition are all equal to this grid
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
