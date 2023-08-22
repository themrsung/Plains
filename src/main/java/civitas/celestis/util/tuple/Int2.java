package civitas.celestis.util.tuple;

import civitas.celestis.math.Scalars;
import civitas.celestis.math.vector.Vector2;
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
 * A fixed-size integer-typed tuple which can only hold two components.
 * Integer pairs use IJ notation to identify their components.
 *
 * @see Tuple
 * @see IntTuple
 */
public class Int2 implements IntTuple<Int2> {
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
     * Creates a new pair.
     *
     * @param i The first element of this pair
     * @param j The second element of this pair
     */
    public Int2(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /**
     * Creates a new pair.
     *
     * @param elements An array containing the elements of this pair in IJ order
     * @throws IllegalArgumentException When the array's length is not {@code 2}
     */
    public Int2(@Nonnull int[] elements) {
        if (elements.length != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.i = elements[0];
        this.j = elements[1];
    }

    /**
     * Creates a new pair.
     *
     * @param t The tuple of which to copy elements from
     * @throws IllegalArgumentException When the tuple's size is not {@code 2}
     */
    public Int2(@Nonnull Tuple<? extends Number> t) {
        if (t.size() != 2) {
            throw new IllegalArgumentException("The provided tuple's size is not 2.");
        }

        this.i = t.get(0).intValue();
        this.j = t.get(1).intValue();
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
        return 2;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return i == 0 && j == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm() {
        return Math.sqrt(i * i + j * j);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int norm2() {
        return i * i + j * j;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int normManhattan() {
        return Math.abs(i) + Math.abs(j);
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
            default -> throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for size 2.");
        };
    }

    /**
     * Returns the I component (the first component) of this pair.
     *
     * @return The I component of this pair
     */
    public int i() {
        return i;
    }

    /**
     * Returns the J component (the second component) of this pair.
     *
     * @return The J component of this pair
     */
    public int j() {
        return j;
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
        return Objects.equals(i, obj) || Objects.equals(j, obj);
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
    public Int2 add(int s) {
        return new Int2(i + s, j + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract from this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int2 subtract(int s) {
        return new Int2(i - s, j - s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this tuple by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int2 multiply(int s) {
        return new Int2(i * s, j * s);
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
    public Int2 divide(int s) throws ArithmeticException {
        return new Int2(i / s, j / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to add to this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int2 add(@Nonnull Int2 t) {
        return new Int2(i + t.i, j + t.j);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to subtract from this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int2 subtract(@Nonnull Int2 t) {
        return new Int2(i - t.i, j - t.j);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public int dot(@Nonnull Int2 t) {
        return i * t.i + j * t.j;
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
    public double distance(@Nonnull Int2 t) {
        final int da = i - t.i;
        final int db = j - t.j;

        return Math.sqrt(da * da + db * db);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the squared Euclidean distance to
     * @return {@inheritDoc}
     */
    @Override
    public int distance2(@Nonnull Int2 t) {
        final int da = i - t.i;
        final int db = j - t.j;

        return da * da + db * db;
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the Manhattan distance to
     * @return {@inheritDoc}
     */
    @Override
    public int distanceManhattan(@Nonnull Int2 t) {
        final int da = i - t.i;
        final int db = j - t.j;

        return Math.abs(da) + Math.abs(db);
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
    public Int2 min(@Nonnull Int2 t) {
        return new Int2(Math.min(i, t.i), Math.min(j, t.j));
    }

    /**
     * {@inheritDoc}
     *
     * @param t The boundary tuple to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int2 max(@Nonnull Int2 t) {
        return new Int2(Math.max(i, t.i), Math.max(j, t.j));
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
    public Int2 clamp(@Nonnull Int2 min, @Nonnull Int2 max) {
        return new Int2(
                (int) Scalars.clamp(i, min.i, max.i),
                (int) Scalars.clamp(j, min.j, max.j)
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
    public Int2 negate() {
        return new Int2(-i, -j);
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
        return new Pair<>(f.apply(i), f.apply(j));
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
        if (t.size() != 2) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        return new Pair<>(f.apply(i, t.get(0)), f.apply(j, t.get(1)));
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
        return List.of(i, j).iterator();
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
        return new Integer[]{i, j};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<Integer> safeArray() {
        return SafeArray.ofInt(i, j);
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
        return List.of(i, j);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 vector() {
        return new Vector2(i, j);
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
        return "[" + i + ", " + j + "]";
    }
}
