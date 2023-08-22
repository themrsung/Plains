package civitas.celestis.util.tuple;

import civitas.celestis.math.Scalars;
import civitas.celestis.math.vector.Vector4;
import civitas.celestis.util.array.SafeArray;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A fixed-size integer-typed tuple which can only hold four components.
 * Integer quads use IJKL notation to identify their components.
 *
 * @see Tuple
 * @see IntTuple
 */
public class Int4 implements IntTuple<Int4> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    /**
     * Creates a new quad.
     *
     * @param i The first element of this quad
     * @param j The second element of this quad
     * @param k The third element of this quad
     * @param l The fourth element of this quad
     */
    public Int4(int i, int j, int k, int l) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
    }

    /**
     * Creates a new quad.
     *
     * @param elements An array containing the elements of this quad in IJKL order
     * @throws IllegalArgumentException When the array's length is not {@code 4}
     */
    public Int4(@Nonnull int[] elements) {
        if (elements.length != 4) {
            throw new IllegalArgumentException("The provided array's length is not 4.");
        }

        this.i = elements[0];
        this.j = elements[1];
        this.k = elements[2];
        this.l = elements[3];
    }

    /**
     * Creates a new quad.
     *
     * @param t The tuple of which to copy elements from
     * @throws IllegalArgumentException When the tuple's size is not {@code 4}
     */
    public Int4(@Nonnull Tuple<? extends Number> t) {
        if (t.size() != 4) {
            throw new IllegalArgumentException("The provided tuple's size is not 4.");
        }

        this.i = t.get(0).intValue();
        this.j = t.get(1).intValue();
        this.k = t.get(2).intValue();
        this.l = t.get(3).intValue();
    }

    //
    // Variables
    //

    /**
     * The first element of this tuple.
     */
    protected final int i;

    /**
     * The second element of this tuple.
     */
    protected final int j;

    /**
     * The third element of this tuple.
     */
    protected final int k;

    /**
     * The fourth element of this tuple.
     */
    protected final int l;

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
        return 4;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return i == 0 && j == 0 && k == 0 && l == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm() {
        return Math.sqrt(i * i + j * j + k * k + l * l);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int norm2() {
        return i * i + j * j + k * k + l * l;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int normManhattan() {
        return Math.abs(i) + Math.abs(j) + Math.abs(k) + Math.abs(l);
    }

    //
    // Retrieval
    //

    /**
     * {@inheritDoc}
     *
     * @param index The index of the element to retrieve
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Integer get(int index) throws IndexOutOfBoundsException {
        return switch (index) {
            case 0 -> i;
            case 1 -> j;
            case 2 -> k;
            case 3 -> l;
            default -> throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size 4.");
        };
    }

    /**
     * Returns the I component (the first component) of this quad.
     *
     * @return The I component of this quad
     */
    public int i() {
        return i;
    }

    /**
     * Returns the J component (the second component) of this quad.
     *
     * @return The J component of this quad
     */
    public int j() {
        return j;
    }

    /**
     * Returns the K component (the third component) of this quad.
     *
     * @return The K component of this quad
     */
    public int k() {
        return k;
    }

    /**
     * Returns the L component (the fourth component) of this quad.
     *
     * @return The L component of this quad
     */
    public int l() {
        return l;
    }

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        return Objects.equals(i, obj) ||
                Objects.equals(j, obj) ||
                Objects.equals(k, obj) ||
                Objects.equals(l, obj);
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
    // Arithmetic
    //

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to add to this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 add(int s) {
        return new Int4(i + s, j + s, k + s, l + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract from this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 subtract(int s) {
        return new Int4(i - s, j - s, k - s, l - s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this tuple by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 multiply(int s) {
        return new Int4(i * s, j * s, k * s, l * s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to divide this tuple by
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 divide(int s) throws ArithmeticException {
        return new Int4(i / s, j / s, k / s, l / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to add to this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 add(@Nonnull Int4 t) {
        return new Int4(i + t.i, j + t.j, k + t.k, l + t.l);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to subtract from this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 subtract(@Nonnull Int4 t) {
        return new Int4(i - t.i, j - t.j, k - t.k, l - t.l);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public int dot(@Nonnull Int4 t) {
        return i * t.i + j * t.j + k * t.k + l * t.l;
    }


    //
    // Distance
    //

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the Euclidean distance to
     * @return {@inheritDoc}
     */
    @Override
    public double distance(@Nonnull Int4 t) {
        final int da = i - t.i;
        final int db = j - t.j;
        final int dc = k - t.k;
        final int dd = l - t.l;

        return Math.sqrt(da * da + db * db + dc * dc + dd * dd);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the squared Euclidean distance to
     * @return {@inheritDoc}
     */
    @Override
    public int distance2(@Nonnull Int4 t) {
        final int da = i - t.i;
        final int db = j - t.j;
        final int dc = k - t.k;
        final int dd = l - t.l;

        return da * da + db * db + dc * dc + dd * dd;
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the Manhattan distance to
     * @return {@inheritDoc}
     */
    @Override
    public int distanceManhattan(@Nonnull Int4 t) {
        final int da = i - t.i;
        final int db = j - t.j;
        final int dc = k - t.k;
        final int dd = l - t.l;

        return Math.abs(da) + Math.abs(db) + Math.abs(dc) + Math.abs(dd);
    }

    //
    // Clamping
    //

    /**
     * {@inheritDoc}
     *
     * @param t The boundary tuple to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 min(@Nonnull Int4 t) {
        return new Int4(Math.min(i, t.i), Math.min(j, t.j), Math.min(k, t.k), Math.min(l, t.k));
    }

    /**
     * {@inheritDoc}
     *
     * @param t The boundary tuple to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 max(@Nonnull Int4 t) {
        return new Int4(Math.max(i, t.i), Math.max(j, t.j), Math.max(k, t.k), Math.max(l, t.k));
    }

    /**
     * {@inheritDoc}
     *
     * @param min The minimum boundary tuple to compare to
     * @param max The maximum boundary tuple to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 clamp(@Nonnull Int4 min, @Nonnull Int4 max) {
        return new Int4(
                (int) Scalars.clamp(i, min.i, min.i),
                (int) Scalars.clamp(j, min.j, min.j),
                (int) Scalars.clamp(k, min.k, min.k),
                (int) Scalars.clamp(l, min.l, min.l)
        );
    }


    //
    // Negation
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 negate() {
        return new Int4(-i, -j, -k, -l);
    }


    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this tuple
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> map(@Nonnull Function<? super Integer, ? extends F> f) {
        return new Quad<>(f.apply(i), f.apply(j), f.apply(k), f.apply(l));
    }


    /**
     * {@inheritDoc}
     *
     * @param t   The tuple of which to merge this tuple with
     * @param f   The merger function to handle the merging of the two tuples
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super Integer, ? super F, G> f)
            throws IllegalArgumentException {
        if (t.size() != 4) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        return new Quad<>(f.apply(i, t.get(0)), f.apply(j, t.get(1)), f.apply(k, t.get(2)), f.apply(l, t.get(3)));
    }

    //
    // Iteration
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Iterator<Integer> iterator() {
        return List.of(i, j, k, l).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tuple
     */
    @Override
    public void forEach(@Nonnull Consumer<? super Integer> action) {
        action.accept(i);
        action.accept(j);
        action.accept(k);
        action.accept(l);
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tuple
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Integer, ? super Integer> action) {
        action.accept(0, i);
        action.accept(1, j);
        action.accept(2, k);
        action.accept(3, l);
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
    public Integer[] array() {
        return new Integer[]{i, j, k, l};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<Integer> safeArray() {
        return SafeArray.ofInt(i, j, k, l);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<Integer> stream() {
        return Arrays.stream(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Integer> list() {
        return List.of(i, j, k, l);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 vector() {
        return new Vector4(i, j, k, l);
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
        if (!(obj instanceof Tuple<?> t)) return false;
        return array().equals(t.array());
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return "[" + i + ", " + j + ", " + k + ", " + l + "]";
    }
}
