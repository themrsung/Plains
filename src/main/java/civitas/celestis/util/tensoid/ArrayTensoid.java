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
 * A three-dimensional tensoid implemented using a 3D array.
 * This implementation is suitable for datasets which are expected to be
 * densely populated, and in applications which require fast access speed.
 *
 * @param <E> The type of element this tensoid should hold
 * @see Tensoid
 */
public class ArrayTensoid<E> implements Tensoid<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Static Initializers
    //

    /**
     * Creates a new tensoid from a three-dimensional primitive array.
     *
     * @param elements The 3D array of elements to map into a tensoid
     * @param <E>      The type of element to contain
     * @return An {@link ArrayTensoid} constructed from the provided elements
     */
    @Nonnull
    static <E> ArrayTensoid<E> of(@Nonnull E[][][] elements) {
        final int width = elements.length;
        final int height = width > 0 ? elements[0].length : 0;
        final int depth = height > 0 ? elements[0][0].length : 0;

        final ArrayTensoid<E> tensoid = new ArrayTensoid<>(width, height, depth);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.arraycopy(elements[i][j], 0, tensoid.values[i][j], 0, depth);
            }
        }

        return tensoid;
    }

    //
    // Constructors
    //

    /**
     * Creates a new array tensoid.
     *
     * @param w The width (range of I) of this tensoid
     * @param h The height (range of J) of this tensoid
     * @param d The depth (range of K) of this tensoid
     */
    @SuppressWarnings("unchecked")
    public ArrayTensoid(int w, int h, int d) {
        this.width = w;
        this.height = h;
        this.depth = d;
        this.values = (E[][][]) new Object[w][h][d];
    }

    /**
     * Creates a new array tensoid.
     *
     * @param size The size to initialize this tensoid to
     */
    @SuppressWarnings("unchecked")
    public ArrayTensoid(@Nonnull Index size) {
        this.width = size.i();
        this.height = size.j();
        this.depth = size.k();
        this.values = (E[][][]) new Object[width][height][depth];
    }

    /**
     * Creates a new array tensoid.
     *
     * @param t The tensoid of which to copy values from
     */
    @SuppressWarnings("unchecked")
    public ArrayTensoid(@Nonnull Tensoid<? extends E> t) {
        this.width = t.width();
        this.height = t.height();
        this.depth = t.depth();
        this.values = (E[][][]) new Object[width][height][depth];
        setRange(0, 0, 0, width, height, depth, t);
    }

    //
    // Variables
    //

    /**
     * The internal 3D array of elements.
     */
    @Nonnull
    protected final E[][][] values;

    /**
     * The width (range of I) of this tensoid.
     */
    protected final int width;

    /**
     * The height (range of J) of this tensoid.
     */
    protected final int height;

    /**
     * The depth (range of K) of this tensoid.
     */
    protected final int depth;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int size() {
        return width * height * depth;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int width() {
        return width;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int height() {
        return height;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int depth() {
        return depth;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Index dimensions() {
        return Tensoid.newIndex(width, height, depth);
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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    if (!Objects.equals(values[i][j][k], obj)) return true;
                }
            }
        }

        return false;
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
    public E get(int i, int j, int k) throws IndexOutOfBoundsException {
        return values[i][j][k];
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the slot to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(@Nonnull Index i) throws IndexOutOfBoundsException {
        return values[i.i()][i.j()][i.k()];
    }

    /**
     * {@inheritDoc}
     *
     * @param i The I coordinate of the slot to set
     * @param j The J coordinate of the slot to set
     * @param k The K coordinate of the slot to set
     * @param v The value to assign to the specified slot
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(int i, int j, int k, E v) throws IndexOutOfBoundsException {
        values[i][j][k] = v;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the slot to set
     * @param v The value to assign to the specified slot
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(@Nonnull Index i, E v) throws IndexOutOfBoundsException {
        values[i.i()][i.j()][i.k()] = v;
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
    public void apply(@Nonnull Function<? super E, E> f) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    values[i][j][k] = f.apply(values[i][j][k]);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    @Override
    public void apply(@Nonnull BiFunction<Index, ? super E, E> f) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    values[i][j][k] = f.apply(Tensoid.newIndex(i, j, k), values[i][j][k]);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each slot of this tensoid
     */
    @Override
    public void apply(@Nonnull QuadFunction<Integer, Integer, Integer, ? super E, E> f) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    values[i][j][k] = f.apply(i, j, k, values[i][j][k]);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this tensoid with
     */
    @Override
    public void fill(E v) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Arrays.fill(values[i][j], v);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill empty slots of this tensoid with
     */
    @Override
    public void fillEmpty(E v) {
        replaceAll(null, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this tensoid selectively with
     * @param f The filter function of which to test each original element with
     */
    @Override
    public void fillIf(E v, @Nonnull Predicate<? super E> f) {
        apply(old -> f.test(old) ? v : old);
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceAll(E oldValue, E newValue) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    if (Objects.equals(values[i][j][k], oldValue)) values[i][j][k] = newValue;
                }
            }
        }
    }

    //
    // Sub Operation
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
    public Tensoid<E> subTensoid(int i1, int j1, int k1, int i2, int j2, int k2) throws IndexOutOfBoundsException {
        final ArrayTensoid<E> result = new ArrayTensoid<>(i2 - i1, j2 - j1, k2 - k1);

        for (int i = i1; i < i2; i++) {
            for (int j = j1; j < j2; j++) {
                System.arraycopy(values[i][j], k1, result.values[i - i1][j - j1], 0, k2 - k1);
            }
        }

        return result;
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
    public Tensoid<E> subTensoid(@Nonnull Index i1, @Nonnull Index i2) throws IndexOutOfBoundsException {
        return subTensoid(i1.i(), i1.j(), i1.k(), i2.i(), i2.j(), i2.k());
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
    public void setRange(int i1, int j1, int k1, int i2, int j2, int k2, @Nonnull Tensoid<? extends E> t)
            throws IndexOutOfBoundsException {
        for (int i = i1; i < i2; i++) {
            for (int j = j1; j < j2; j++) {
                for (int k = k1; k < k2; k++) {
                    values[i][j][k] = t.get(i - i1, j - j1, k - k1);
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
    public void setRange(@Nonnull Index i1, @Nonnull Index i2, @Nonnull Tensoid<? extends E> t)
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
        final ArrayTensoid<F> result = new ArrayTensoid<>(width, height, depth);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    result.values[i][j][k] = f.apply(values[i][j][k]);
                }
            }
        }

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
        if (!dimensions().equals(t.dimensions())) {
            throw new IllegalArgumentException("Tensoid dimensions must match for this operation.");
        }

        final ArrayTensoid<G> result = new ArrayTensoid<>(width, height, depth);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    result.values[i][j][k] = f.apply(values[i][j][k], t.get(i, j, k));
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
     *
     * @param width  The resulting tensoid's width
     * @param height The resulting tensoid's height
     * @param depth  The resulting tensoid's depth
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tensoid<E> resize(int width, int height, int depth) {
        final ArrayTensoid<E> result = new ArrayTensoid<>(width, height, depth);

        final int w = Math.min(this.width, width);
        final int h = Math.min(this.height, height);
        final int d = Math.min(this.depth, depth);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                System.arraycopy(values[i][j], 0, result.values[i][j], 0, d);
            }
        }

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
        return resize(size.i(), size.j(), size.k());
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
        return array().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tensoid
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> action) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    action.accept(values[i][j][k]);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tensoid
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Index, ? super E> action) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    action.accept(Tensoid.newIndex(i, j, k), values[i][j][k]);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tensoid
     */
    @Override
    public void forEach(@Nonnull QuadConsumer<Integer, Integer, Integer, ? super E> action) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    action.accept(i, j, k, values[i][j][k]);
                }
            }
        }
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
    @SuppressWarnings("unchecked")
    public SafeArray<E> array() {
        final E[] array = (E[]) new Object[size()];

        int counter = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    array[counter++] = values[i][j][k];
                }
            }
        }

        return SafeArray.of(array);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<E> tuple() {
        return array().tuple();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return array().collect();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Map<Index, E> map() {
        final Map<Index, E> map = new HashMap<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    map.put(Tensoid.newIndex(i, j, k), values[i][j][k]);
                }
            }
        }

        return map;
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
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Tensoid<?> t)) return false;
        if (!dimensions().equals(t.dimensions())) return false;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < depth; k++) {
                    if (!Objects.equals(values[i][j][k], t.get(i, j, k))) return false;
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
        return map().toString();
    }
}
