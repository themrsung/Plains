package civitas.celestis.util;

import civitas.celestis.math.Scalars;
import civitas.celestis.math.Vector2;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A fixed-size integer-typed tuple which can only hold two components.
 * Pairs use AB notation to identify their components.
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
     * @param a The first element of this pair
     * @param b The second element of this pair
     */
    public Int2(int a, int b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Creates a new pair.
     *
     * @param elements An array containing the elements of this pair in AB order
     * @throws IllegalArgumentException When the array's length is not {@code 2}
     */
    public Int2(@Nonnull int[] elements) {
        if (elements.length != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.a = elements[0];
        this.b = elements[1];
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

        this.a = t.get(0).intValue();
        this.b = t.get(1).intValue();
    }

    /**
     * Creates a new pair. The required format is "{@code [0, 0]}".
     *
     * @param values The string representation of this pair
     * @throws NumberFormatException When the format is invalid
     */
    public Int2(@Nonnull String values) {
        this(ArrayReader.readIntArray(values));
    }

    //
    // Variables
    //

    /**
     * The first element of this tuple.
     */
    protected final int a;

    /**
     * The second element of this tuple.
     */
    protected final int b;

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
        return a == 0 && b == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm() {
        return Math.sqrt(a * a + b * b);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int norm2() {
        return a * a + b * b;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int normManhattan() {
        return Math.abs(a) + Math.abs(b);
    }

    //
    // Retrieval
    //

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to retrieve
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Integer get(int i) throws IndexOutOfBoundsException {
        return switch (i) {
            case 0 -> a;
            case 1 -> b;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 2.");
        };
    }

    /**
     * Returns the A component (the first component) of this pair.
     *
     * @return The A component of this pair
     */
    public int a() {
        return a;
    }

    /**
     * Returns the B component (the second component) of this pair.
     *
     * @return The B component of this pair
     */
    public int b() {
        return b;
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
        return Objects.equals(a, obj) || Objects.equals(b, obj);
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
        return new Int2(a + s, b + s);
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
        return new Int2(a - s, b - s);
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
        return new Int2(a * s, b * s);
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
        return new Int2(a / s, b / s);
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
        return new Int2(a + t.a, b + t.b);
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
        return new Int2(a - t.a, b - t.b);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public int dot(@Nonnull Int2 t) {
        return a * t.a + b * t.b;
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
        final int da = a - t.a;
        final int db = b - t.b;

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
        final int da = a - t.a;
        final int db = b - t.b;

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
        final int da = a - t.a;
        final int db = b - t.b;

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
        return new Int2(Math.min(a, t.a), Math.min(b, t.b));
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
        return new Int2(Math.max(a, t.a), Math.max(b, t.b));
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
                (int) Scalars.clamp(a, min.a, max.a),
                (int) Scalars.clamp(b, min.b, max.b)
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
        return new Int2(-a, -b);
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
        return new Pair<>(f.apply(a), f.apply(b));
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

        return new Pair<>(f.apply(a, t.get(0)), f.apply(b, t.get(1)));
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
        return List.of(a, b).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tuple
     */
    @Override
    public void forEach(@Nonnull Consumer<? super Integer> action) {
        action.accept(a);
        action.accept(b);
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tuple
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Integer, ? super Integer> action) {
        action.accept(0, a);
        action.accept(1, b);
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
    public SafeArray<Integer> array() {
        return SafeArray.ofInt(a, b);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Integer> list() {
        return List.of(a, b);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector2 vector() {
        return new Vector2(a, b);
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
        return "[" + a + ", " + b + "]";
    }
}
