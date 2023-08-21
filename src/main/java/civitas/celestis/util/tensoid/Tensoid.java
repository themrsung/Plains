package civitas.celestis.util.tensoid;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.function.QuadConsumer;
import civitas.celestis.util.function.QuadFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.function.*;

/**
 * A three-dimensional structure of objects. Tensoids can be traversed
 * by either providing three separate IJK coordinates, or by providing an
 * {@link Index Index} object to use as the index.
 *
 * @param <E> The type of element this tensoid should hold
 * @see ArrayTensoid
 * @see SyncTensoid
 * @see DynamicTensoid
 * @see HashTensoid
 */
public interface Tensoid<E> extends Iterable<E>, Serializable {
    //
    // Static Methods
    //

    /**
     * Returns a new {@link Index index} with the specified coordinates.
     *
     * @param i The I coordinate of the index
     * @param j The J coordinate of the index
     * @param k The K coordinate of the index
     * @return The index object corresponding to the specified position
     */
    @Nonnull
    static Index newIndex(int i, int j, int k) {
        // Try to find pre-built index
        final int key = TensoidIndex.calculateKey(i, j, k);
        if (TensoidIndex.indices.containsKey(key)) return TensoidIndex.indices.get(key);

        // No index found; Create new index
        final TensoidIndex index = new TensoidIndex(i, j, k);
        TensoidIndex.indices.put(key, index);
        return index;
    }

    //
    // Factory
    //

    /**
     * Given a three-dimensional primitive array of elements, this returns a tensoid
     * constructed from those elements. This automatically determines the implementation
     * to use to create the new instance. In order to manually designate the instance,
     * use the static initializers in the implementations themselves.
     *
     * @param elements The 3D array of elements to construct the tensoid from
     * @param <E>      The type of element to contain
     * @return The constructed tensoid
     * @see ArrayTensoid#of(Object[][][])
     * @see DynamicTensoid#of(Object[][][])
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    static <E> Tensoid<E> of(@Nonnull E[][][] elements) {
        // Determine the size of the data
        final int width = elements.length;
        final int height = width > 0 ? elements[0].length : 0;
        final int depth = height > 0 ? elements[0][0].length : 0;

        final int size = width * height * depth;

        // Check if data is large enough to be worth examining
        if (size > 1000) {
            // If there are more null values than this threshold, a hash tensoid will be returned
            final int nullThreshold = (size) / 3;

            // Convert the elements into a 1D array for stream operations
            final E[] array = (E[]) new Object[size];

            int counter = 0;
            for (final E[][] element : elements) {
                for (int j = 0; j < height; j++) {
                    for (int k = 0; k < depth; k++) {
                        array[counter++] = element[j][k];
                    }
                }
            }

            // Return hash tensoid if appropriate
            if (Arrays.stream(array).filter(Objects::isNull).count() > nullThreshold) {
                return HashTensoid.of(elements);
            }
        }

        // Array tensoid is more suitable
        return ArrayTensoid.of(elements);
    }

    /**
     * Given a three-dimensional primitive array of elements, this returns a thread-safe
     * tensoid constructed from those elements.
     *
     * @param elements The 3D array of elements to construct the tensoid from
     * @param <E>      The type of element to contain
     * @return The constructed tensoid
     */
    @Nonnull
    static <E> Tensoid<E> syncOf(@Nonnull E[][][] elements) {
        return SyncTensoid.of(elements);
    }

    //
    // Properties
    //

    /**
     * Returns the size of this tensoid. (the number of slots this tensoid has)
     * This is equivalent to the geometric volume of this tensoid if it were a cuboid.
     *
     * @return The size of this tensoid
     */
    int size();

    /**
     * Returns the width of this tensoid. (the number of slots along the I axis)
     *
     * @return The width of this tensoid
     */
    int width();

    /**
     * Returns the height of this tensoid. (the number of slots along the J axis)
     *
     * @return The height of this tensoid
     */
    int height();

    /**
     * Returns the depth of this tensoid. (the number of slots along the K axis)
     *
     * @return The depth of this tensoid
     */
    int depth();

    /**
     * Returns the dimensions of this tensoid as an {@link Index Index} object.
     *
     * @return The dimensions of this tensoid, packaged as an index object
     */
    @Nonnull
    Index dimensions();

    //
    // Containment
    //

    /**
     * Checks if this tensoid contains the provided object {@code obj}.
     *
     * @param obj The object to check for containment
     * @return {@code true} if at least one element of this tensoid is equal to
     * the provided object {@code obj}
     */
    boolean contains(@Nullable Object obj);

    /**
     * Checks if this tensoid contains every element within the iterable object {@code i}.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this tensoid contains every element of the iterable object {@code i}
     */
    boolean containsAll(@Nonnull Iterable<?> i);

    //
    // Accessors
    //

    /**
     * Returns the element currently present at the specified slot.
     *
     * @param i The I coordinate of the element to get
     * @param j The J coordinate of the element to get
     * @param k The K coordinate of the element to get
     * @return The value at the specified slot
     * @throws IndexOutOfBoundsException When the index is out of bounds
     */
    E get(int i, int j, int k) throws IndexOutOfBoundsException;

    /**
     * Returns the {@code i}th element of this tensoid.
     *
     * @param i The index of the slot to get
     * @return The value at the specified slot
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    E get(@Nonnull Index i) throws IndexOutOfBoundsException;

    /**
     * Sets the value at the specified position.
     *
     * @param i The I coordinate of the slot to set
     * @param j The J coordinate of the slot to set
     * @param k The K coordinate of the slot to set
     * @param v The value to assign to the specified slot
     * @throws IndexOutOfBoundsException When the index is out of bounds
     */
    void set(int i, int j, int k, E v) throws IndexOutOfBoundsException;

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
     * Applies the provided function {@code f} to every slot of this tensoid.
     * The return value of the function will be assigned to the corresponding slot.
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    void apply(@Nonnull Function<? super E, E> f);

    /**
     * Applies the provided function {@code f} to every slot of this tensoid.
     * The index of the slot is given as the first parameter, and the original value
     * is given as the second parameter. The return value of the function
     * is then assigned to the corresponding slot.
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    void apply(@Nonnull BiFunction<Index, ? super E, E> f);

    /**
     * Applies the provided function {@code f} to every slot of this tensoid.
     * The IJK coordinates of the slot are provided as parameter {@code 1-3}, and the
     * original value is given as the last parameter. The return value of the function
     * is then assigned to the corresponding slot.
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    void apply(@Nonnull QuadFunction<Integer, Integer, Integer, ? super E, E> f);

    /**
     * Fills this tensoid with the provided value {@code v}.
     *
     * @param v The value to fill this tensoid with
     */
    void fill(E v);

    /**
     * Fills this tensoid, but only assigns empty slots (slots which are {@code null})
     * to the provided element {@code v}.
     *
     * @param v The value to fill empty slots of this tensoid with
     */
    void fillEmpty(E v);

    /**
     * Fills this tensoid, but only if the filter function {@code f} returns {@code true}
     * for the corresponding slot's original value.
     *
     * @param v The value to fill this tensoid selectively with
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
     * Returns a sub-tensoid of this tensoid, constructed from the provided range.
     *
     * @param i1 The I coordinate of the starting point
     * @param j1 The J coordinate of the starting point
     * @param k1 The K coordinate of the starting point
     * @param i2 The I coordinate of the ending point
     * @param j2 The J coordinate of the ending point
     * @param k2 The K coordinate of the ending point
     * @return The sub-tensoid of this tensoid at the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    Tensoid<E> subTensoid(int i1, int j1, int k1, int i2, int j2, int k2) throws IndexOutOfBoundsException;

    /**
     * Returns a sub-tensoid of this tensoid, constructed from the provided range.
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @return The sub-tensoid of this tensoid of the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    Tensoid<E> subTensoid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException;

    /**
     * Sets a sub-tensoid of this tensoid by providing the values in the form of another tensoid.
     *
     * @param i1 The I coordinate of the starting point
     * @param j1 The J coordinate of the starting point
     * @param k1 The K coordinate of the starting point
     * @param i2 The I coordinate of the ending point
     * @param j2 The J coordinate of the ending point
     * @param k2 The K coordinate of the ending point
     * @param t  The tensoid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setRange(int i1, int j1, int k1, int i2, int j2, int k2, @Nonnull Tensoid<? extends E> t)
            throws IndexOutOfBoundsException;

    /**
     * Sets a sub-tensoid of this tensoid by providing the values in the form of another tensoid.
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @param t  The tensoid containing the values to assign
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    void setRange(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Tensoid<? extends E> t)
            throws IndexOutOfBoundsException;

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to each element of this tensoid,
     * then returns a new tensoid containing the resulting elements.
     *
     * @param f   The function of which to apply to each element of this tensoid
     * @param <F> The type of element to map this tensoid to
     * @return The resulting tensoid
     */
    @Nonnull
    <F> Tensoid<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Between this tensoid and the provided tensoid {@code t}, this applies the merger function {@code f}
     * to each corresponding pair of elements, then returns a new tensoid containing the resulting elements.
     *
     * @param t   The tensoid of which to merge this tensoid with
     * @param f   The merger function to handle the merging of the two tensoids
     * @param <F> The type of element to merge this tensoid with
     * @param <G> The type of element to merge the two tensoids to
     * @return The resulting tensoid
     * @throws IllegalArgumentException When the two tensoids' dimensions are different
     */
    @Nonnull
    <F, G> Tensoid<G> merge(@Nonnull Tensoid<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException;

    //
    // Resizing
    //

    /**
     * Returns a resized tensoid of the provided dimensions, where the values of this tensoid
     * are mapped to. If the requested size is larger than this tensoid, the oversized slots
     * will not be populated with values, thus leaving them to be {@code null}.
     *
     * @param width  The resulting tensoid's width
     * @param height The resulting tensoid's height
     * @param depth  The resulting tensoid's depth
     * @return The resized tensoid
     */
    @Nonnull
    Tensoid<E> resize(int width, int height, int depth);

    /**
     * Returns a resized tensoid of the provided dimensions, where the values of this tensoid
     * are mapped to. If the requested size is larger than this tensoid, the oversized slots
     * will not be populated with values, thus leaving them to be {@code null}.
     *
     * @param size The size the resized tensoid should have
     * @return The resized tensoid
     */
    @Nonnull
    Tensoid<E> resize(@Nonnull Index size);

    //
    // Iteration
    //

    /**
     * Returns an iterator of every element within this tensoid, in a consistent order.
     *
     * @return An iterator of every element within this tensoid
     */
    @Nonnull
    @Override
    Iterator<E> iterator();

    /**
     * Executes the provided action for each element of this tensoid. The current value
     * is provided as the input parameter.
     *
     * @param action The action of which to execute for each element of this tensoid
     */
    @Override
    void forEach(@Nonnull Consumer<? super E> action);

    /**
     * Executes the provided action for each element of this tensoid. The index
     * of the corresponding slot is given as the first parameter, and the current value
     * is given as the second parameter.
     *
     * @param action The action of which to execute for each element of this tensoid
     */
    void forEach(@Nonnull BiConsumer<Index, ? super E> action);

    /**
     * Executes the provided action for each element of this tensoid. The IJK
     * indices are given as the first, second, and third parameter respectively,
     * and the current value if given as the fourth parameter.
     *
     * @param action The action of which to execute for each element of this tensoid
     */
    void forEach(@Nonnull QuadConsumer<Integer, Integer, Integer, ? super E> action);

    //
    // Conversion
    //

    /**
     * Returns a one-dimensional type-safe array containing every element of this tensoid.
     * Changes in the return value will not be reflected to this tensoid.
     *
     * @return The array representation of this tensoid
     */
    @Nonnull
    SafeArray<E> array();

    /**
     * Returns a tuple containing every element of this tensoid.
     *
     * @return The tuple representation of this tensoid
     * @see Tuple
     */
    @Nonnull
    Tuple<E> tuple();

    /**
     * Returns a collection containing every element of this tensoid.
     *
     * @return The collection representation of this tensoid
     * @see Collection
     */
    @Nonnull
    Collection<E> collect();

    /**
     * Returns a map containing every element of this grid, mapped
     * by their corresponding {@link Index index}.
     *
     * @return The map representation of this tensoid
     * @see Map
     */
    @Nonnull
    Map<Index, E> map();

    //
    // Equality
    //

    /**
     * Checks for equality between this tensoid and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a tensoid, and the dimensions, composition,
     * and order of elements are all equal
     */
    @Override
    boolean equals(@Nullable Object obj);

    //
    // Serialization
    //

    /**
     * Serializes this tensoid into a string.
     *
     * @return The string representation of this tensoid
     */
    @Nonnull
    @Override
    String toString();

    //
    // Indexing
    //

    /**
     * The index used to traverse a tensoid. Indices start at {@code 0} and increment
     * until the limit {@code n} has been reached, where {@code n + 1} is the depth of the
     * corresponding axis.
     */
    interface Index {
        /**
         * Returns the I coordinate of this index.
         *
         * @return The I coordinate of this index
         */
        int i();

        /**
         * Returns the J coordinate of this index.
         *
         * @return The J coordinate of this index
         */
        int j();

        /**
         * Returns the K coordinate of this index.
         *
         * @return The K coordinate of this index
         */
        int k();

        /**
         * Checks for equality between this index and the provided object {@code obj}.
         *
         * @param obj The object to compare to
         * @return {@code true} if the other object is also an index, and the
         * IJK coordinates are equal
         */
        @Override
        boolean equals(@Nullable Object obj);
    }

    //
    // Internal
    //

    /**
     * The default implementation of {@link Index Index}.
     */
    final class TensoidIndex implements Index {

        /**
         * The map of indices to reuse.
         */
        private static final Map<Integer, Index> indices = new HashMap<>();

        static {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        indices.put(calculateKey(i, j, k), new TensoidIndex(i, j, k));
                    }
                }
            }
        }

        /*
         * Indexing
         */

        private static int calculateKey(int i, int j, int k) {
            return i + j * J_PRIME_FACTOR + k * K_PRIME_FACTOR;
        }

        private static final int J_PRIME_FACTOR = 2;
        private static final int K_PRIME_FACTOR = 3;

        /**
         * Private constructor to ensure this class is used internally.
         *
         * @param i The I coordinate of this index
         * @param j The J coordinate of this index
         * @param k The K coordinate of this index
         */
        private TensoidIndex(int i, int j, int k) {
            this.i = i;
            this.j = j;
            this.k = k;
        }

        /**
         * The I coordinate of this index.
         */
        private final int i;

        /**
         * The J coordinate of this index.
         */
        private final int j;

        /**
         * The K coordinate of this index.
         */
        private final int k;

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        public int i() {
            return i;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        public int j() {
            return j;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        public int k() {
            return k;
        }

        /**
         * {@inheritDoc}
         *
         * @param obj The object to compare to
         * @return {@inheritDoc}
         */
        @Override
        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof Index index)) return false;
            return i == index.i() && j == index.j() && k == index.k();
        }

        /**
         * Serializes this index into a string.
         *
         * @return The string representation of this index
         */
        @Nonnull
        @Override
        public String toString() {
            return "[" + i + ", " + j + ", " + k + "]";
        }
    }
}
