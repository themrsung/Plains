package civitas.celestis.util.tensoid;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.function.QuadConsumer;
import civitas.celestis.util.function.QuadFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;

/**
 * A three-dimensional tensoid implemented using a {@link HashMap}.
 * This implementation is suitable for datasets which are expected to be
 * sparsely populated, and in applications which require dynamic resizing.
 * <p>
 * Note that hash tensoids are noticeably slower than array-based implementations
 * when it comes to access and modification speeds due to their additional overhead.
 * Hash tensoids should only be used to hold a very large tensoid
 * which is not intended to be entirely populated with values, but
 * partially left uninitialized as {@code null}.
 * </p>
 *
 * @param <E> The type of element this tensoid should hold
 * @see Tensoid
 * @see DynamicTensoid
 */
public class HashTensoid<E> implements DynamicTensoid<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * The index {@code 0, 0, 0}.
     */
    private static final Index ZERO = Tensoid.newIndex(0, 0, 0);

    //
    // Static Initializers
    //

    /**
     * Creates a new hash tensoid from a 3D array of entries.
     *
     * @param entries The entries the tensoid should contain
     * @param <E>     The type of element to contain
     * @return The constructed hash tensoid
     */
    @Nonnull
    public static <E> HashTensoid<E> of(@Nonnull E[][][] entries) {
        final int width = entries.length;
        final int height = width > 0 ? entries[0].length : 0;
        final int depth = height > 0 ? entries[0][0].length : 0;

        final HashTensoid<E> result = new HashTensoid<>(width, height, depth);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    final E value = entries[i][j][k];
                    if (value == null) continue;
                    result.values.put(Tensoid.newIndex(i, j, k), value);
                }
            }
        }

        return result;
    }

    /**
     * Creates a new hash tensoid from a map of index-value pairs.
     *
     * @param entries The entries the tensoid should contain
     * @param <E>     The type of element to contain
     * @return The constructed hash tensoid
     */
    @Nonnull
    static <E> HashTensoid<E> of(@Nonnull Map<Index, ? extends E> entries) {
        final HashTensoid<E> result = new HashTensoid<>();
        entries.forEach(result::set);
        return result;
    }

    //
    // Constructors
    //

    /**
     * Creates a new empty hash tensoid.
     */
    public HashTensoid() {
        this(0, 0, 0);
    }

    /**
     * Creates a new hash tensoid with an initial size.
     *
     * @param w The initial width (range of I) of this tensoid
     * @param h The initial height (range of J) of this tensoid
     * @param d The initial depth (range of K) of this tensoid
     */
    public HashTensoid(int w, int h, int d) {
        this.values = new HashMap<>();
        this.dimensions = Tensoid.newIndex(w, h, d);
    }

    /**
     * Creates a new hash tensoid with an initial size.
     *
     * @param initialSize The initial size of this tensoid
     */
    public HashTensoid(@Nonnull Index initialSize) {
        this.values = new HashMap<>();

        /*
         * The index object must be copied as third-party implementations may not be immutable.
         */

        this.dimensions = Tensoid.newIndex(initialSize.i(), initialSize.j(), initialSize.k());
    }

    /**
     * Creates a new hash tensoid.
     *
     * @param t The tensoid of which to copy values from
     */
    public HashTensoid(@Nonnull Tensoid<? extends E> t) {
        this.values = new HashMap<>(t.map());
        this.dimensions = Tensoid.newIndex(0, 0, 0);
        adjustDimensions();
    }

    //
    // Internal
    //

    /**
     * Determines the dimensions of a given map, then returns the dimensions.
     *
     * @param map The map of which to determine the size of
     * @return The dimensions of the map
     */
    @Nonnull
    protected static Index getDimensionsOfMap(@Nonnull Map<Index, ?> map) {
        Index size = Tensoid.newIndex(0, 0, 0);
        for (final Index index : map.keySet()) {
            size = DynamicTensoid.max(size, index);
        }
        return size;
    }

    /**
     * Adjusts this tensoid's dimensions to accurately represent the dataset.
     */
    protected synchronized void adjustDimensions() {
        dimensions = DynamicTensoid.max(dimensions, getDimensionsOfMap(values));
    }

    //
    // Variables
    //

    /**
     * The internal hash map of elements.
     */
    @Nonnull
    protected final HashMap<Index, E> values;

    /**
     * The last-known dimensions of this tensoid.
     * <p>
     * As this variable is constantly reassigned to adjust for addition and removal,
     * this must maintain at least some level of thread safety.
     * </p>
     */
    private volatile Index dimensions;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized int size() {
        return dimensions.i() * dimensions.j() * dimensions.k();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized int width() {
        return dimensions.i();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized int height() {
        return dimensions.j();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized int depth() {
        return dimensions.k();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Index dimensions() {
        return dimensions;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the slot to check
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean exists(@Nonnull Index i) {
        return DynamicTensoid.isInRange(i, ZERO, dimensions);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The I coordinate of the slot to check
     * @param j The J coordinate of the slot to check
     * @param k The K coordinate of the slot to check
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean exists(int i, int j, int k) {
        return DynamicTensoid.isInRange(Tensoid.newIndex(i, j, k), ZERO, dimensions);
    }

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        return values.containsValue(obj);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<?> i) {
        for (final Object o : i) {
            if (!contains(o)) return false;
        }

        return true;
    }

    //
    // Accessors
    //

    /**
     * {@inheritDoc}
     *
     * @param i The I coordinate of the element to get
     * @param j The J coordinate of the element to get
     * @param k The K coordinate of the element to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized E get(int i, int j, int k) throws IndexOutOfBoundsException {
        final Index index = Tensoid.newIndex(i, j, k);

        if (!DynamicTensoid.isInRange(index, ZERO, dimensions)) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for dimensions " + dimensions + ".");
        }

        return values.get(index);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the slot to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized E get(@Nonnull Index i) throws IndexOutOfBoundsException {

        if (!DynamicTensoid.isInRange(i, ZERO, dimensions)) {
            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for dimensions " + dimensions + ".");
        }

        return values.get(i);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The I coordinate of the slot to set
     * @param j The J coordinate of the slot to set
     * @param k The K coordinate of the slot to set
     * @param v The value to assign to the specified slot
     */
    @Override
    public synchronized void set(int i, int j, int k, E v) {
        values.put(Tensoid.newIndex(i, j, k), v);
        adjustDimensions();
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the slot to set
     * @param v The value to assign to the specified slot
     */
    @Override
    public synchronized void set(@Nonnull Index i, E v) {
        values.put(i, v);
        adjustDimensions();
    }

    /**
     * {@inheritDoc}
     *
     * @param i The I coordinate of the element to remove
     * @param j The J coordinate of the element to remove
     * @param k The K coordinate of the element to remove
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean remove(int i, int j, int k) {
        return remove(Tensoid.newIndex(i, j, k));
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to remove
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean remove(@Nonnull Index i) {
        final boolean exists = values.containsKey(i);
        values.remove(i);
        return exists;
    }

    //
    // Bulk Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    @Override
    public synchronized void apply(@Nonnull Function<? super E, E> f) {
        final HashMap<Index, E> temp = new HashMap<>(values);
        temp.forEach((i, v) -> values.put(i, f.apply(v)));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    @Override
    public synchronized void apply(@Nonnull BiFunction<Index, ? super E, E> f) {
        final HashMap<Index, E> temp = new HashMap<>(values);
        temp.forEach((i, v) -> values.put(i, f.apply(i, v)));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    @Override
    public synchronized void apply(@Nonnull QuadFunction<Integer, Integer, Integer, ? super E, E> f) {
        final HashMap<Index, E> temp = new HashMap<>(values);
        temp.forEach((i, v) -> values.put(i, f.apply(i.i(), i.j(), i.k(), v)));
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this tensoid with
     */
    @Override
    public synchronized void fill(E v) {
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                for (int k = 0; k < depth(); k++) {
                    values.put(Tensoid.newIndex(i, j, k), v);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill empty slots of this tensoid with
     */
    @Override
    public synchronized void fillEmpty(E v) {
        replaceAll(null, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this tensoid selectively with
     * @param f The filter function of which to test each original element with
     */
    @Override
    public synchronized void fillIf(E v, @Nonnull Predicate<? super E> f) {
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                for (int k = 0; k < depth(); k++) {
                    final Index index = Tensoid.newIndex(i, j, k);
                    final E old = values.get(index);

                    if (!f.test(old)) continue;

                    values.put(index, v);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceAll(E oldValue, E newValue) {
        values.replaceAll((i, v) -> Objects.equals(v, oldValue) ? newValue : v);
    }

    //
    // Sub-Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param i1 The I coordinate of the starting point
     * @param j1 The J coordinate of the starting point
     * @param k1 The K coordinate of the starting point
     * @param i2 The I coordinate of the ending point
     * @param j2 The J coordinate of the ending point
     * @param k2 The K coordinate of the ending point
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Tensoid<E> subTensoid(int i1, int j1, int k1, int i2, int j2, int k2) throws IndexOutOfBoundsException {
        return subTensoid(Tensoid.newIndex(i1, j1, k1), Tensoid.newIndex(i2, j2, k2));
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Tensoid<E> subTensoid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException {
        if (!DynamicTensoid.isInRange(i1, ZERO, dimensions) || !DynamicTensoid.isInRange(i2, ZERO, dimensions)) {
            throw new IndexOutOfBoundsException("Range [" + i1 + ", " + i2 + "] is out of bounds for dimensions " + dimensions + ".");
        }

        final HashTensoid<E> result = new HashTensoid<>();

        values.forEach((i, v) -> {
            if (!DynamicTensoid.isInRange(i, i1, i2)) return;
            result.values.put(i, v);
        });

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The I coordinate of the starting point
     * @param j1 The J coordinate of the starting point
     * @param k1 The K coordinate of the starting point
     * @param i2 The I coordinate of the ending point
     * @param j2 The J coordinate of the ending point
     * @param k2 The K coordinate of the ending point
     * @param t  The tensoid containing the values to assign
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void setRange(int i1, int j1, int k1, int i2, int j2, int k2, @Nonnull Tensoid<? extends E> t)
            throws IndexOutOfBoundsException {
        for (int i = i1; i < i2; i++) {
            for (int j = j1; j < j2; j++) {
                for (int k = k1; k < k2; k++) {
                    set(i, j, k, t.get(i - i1, j - j2, k - k2));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The starting point's index
     * @param i2 The ending point's index
     * @param t  The tensoid containing the values to assign
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void setRange(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Tensoid<? extends E> t)
            throws IndexOutOfBoundsException {
        setRange(i1.i(), i1.j(), i1.k(), i2.i(), i2.j(), i2.k(), t);
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this tensoid
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tensoid<F> map(@Nonnull Function<? super E, ? extends F> f) {
        final HashTensoid<F> result = new HashTensoid<>();
        values.forEach((i, v) -> result.values.put(i, f.apply(v)));
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param t   The tensoid of which to merge this tensoid with
     * @param f   The merger function to handle the merging of the two tensoids
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F, G> Tensoid<G> merge(@Nonnull Tensoid<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        if (!dimensions.equals(t.dimensions())) {
            throw new IllegalArgumentException("Tensoid dimensions must match for this operation.");
        }

        final HashTensoid<G> result = new HashTensoid<>();

        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                for (int k = 0; k < depth(); k++) {
                    final Index index = Tensoid.newIndex(i, j, k);
                    result.values.put(index, f.apply(values.get(index), t.get(index)));
                }
            }
        }

        return result;
    }

    //
    // Resizing
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void clean() {
        final HashMap<Index, E> temp = new HashMap<>(values);
        temp.forEach((i, v) -> {
            if (v != null) return;
            values.remove(i);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void trim() {
        clean();
        dimensions = getDimensionsOfMap(values);
    }

    /**
     * {@inheritDoc}
     *
     * @param w The new width of this tensoid
     * @param h The new height of this tensoid
     * @param d The new depth of this tensoid
     */
    @Override
    public synchronized void setSize(int w, int h, int d) {
        dimensions = Tensoid.newIndex(w, h, d);
    }

    /**
     * {@inheritDoc}
     *
     * @param size The new size of this tensoid
     */
    @Override
    public synchronized void setSize(@Nonnull Index size) {
        dimensions = Tensoid.newIndex(size.i(), size.j(), size.k());
    }

    /**
     * {@inheritDoc}
     *
     * @param width  The resulting tensoid's width
     * @param height The resulting tensoid's height
     * @param depth  The resulting tensoid's depth
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tensoid<E> resize(int width, int height, int depth) {
        final HashTensoid<E> result = new HashTensoid<>(width, height, depth);

        values.forEach((i, v) -> {
            if (!result.exists(i)) return;
            result.values.put(i, v);
        });

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param size The size the resized tensoid should have
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tensoid<E> resize(@Nonnull Index size) {
        final HashTensoid<E> result = new HashTensoid<>(size);

        values.forEach((i, v) -> {
            if (!result.exists(i)) return;
            result.values.put(i, v);
        });

        return result;
    }

    //
    // Iteration
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return values.values().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> action) {
        values.forEach((i, v) -> action.accept(v));
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tensoid
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Index, ? super E> action) {
        values.forEach(action);
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tensoid
     */
    @Override
    public void forEach(@Nonnull QuadConsumer<Integer, Integer, Integer, ? super E> action) {
        values.forEach((i, v) -> action.accept(i.i(), i.j(), i.k(), v));
    }

    //
    // Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<E> array() {
        return SafeArray.syncCopyOf(values.values());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<E> tuple() {
        return Tuple.copyOf(values.values());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return List.copyOf(values.values());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Map<Index, E> map() {
        return Map.copyOf(values);
    }

    //
    // Equality
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object to compare to
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Tensoid<?> t)) return false;
        if (!dimensions.equals(t.dimensions())) return false;

        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                for (int k = 0; k < depth(); k++) {
                    if (!Objects.equals(get(i, j, k), t.get(i, j, k))) return false;
                }
            }
        }

        return true;
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return values.toString();
    }
}
