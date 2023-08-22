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
 * Quads use ABCD notation to identify their components.
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
     * @param a The first element of this quad
     * @param b The second element of this quad
     * @param c The third element of this quad
     * @param d The fourth element of this quad
     */
    public Int4(int a, int b, int c, int d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Creates a new quad.
     *
     * @param elements An array containing the elements of this quad in ABCD order
     * @throws IllegalArgumentException When the array's length is not {@code 4}
     */
    public Int4(@Nonnull int[] elements) {
        if (elements.length != 4) {
            throw new IllegalArgumentException("The provided array's length is not 4.");
        }

        this.a = elements[0];
        this.b = elements[1];
        this.c = elements[2];
        this.d = elements[3];
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

        this.a = t.get(0).intValue();
        this.b = t.get(1).intValue();
        this.c = t.get(2).intValue();
        this.d = t.get(3).intValue();
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

    /**
     * The third element of this tuple.
     */
    protected final int c;

    /**
     * The fourth element of this tuple.
     */
    protected final int d;

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
        return a == 0 && b == 0 && c == 0 && d == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm() {
        return Math.sqrt(a * a + b * b + c * c + d * d);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int norm2() {
        return a * a + b * b + c * c + d * d;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int normManhattan() {
        return Math.abs(a) + Math.abs(b) + Math.abs(c) + Math.abs(d);
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
            case 2 -> c;
            case 3 -> d;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 4.");
        };
    }

    /**
     * Returns the A component (the first component) of this quad.
     *
     * @return The A component of this quad
     */
    public int a() {
        return a;
    }

    /**
     * Returns the B component (the second component) of this quad.
     *
     * @return The B component of this quad
     */
    public int b() {
        return b;
    }

    /**
     * Returns the C component (the third component) of this quad.
     *
     * @return The C component of this quad
     */
    public int c() {
        return c;
    }

    /**
     * Returns the D component (the fourth component) of this quad.
     *
     * @return The D component of this quad
     */
    public int d() {
        return d;
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
        return Objects.equals(a, obj) ||
                Objects.equals(b, obj) ||
                Objects.equals(c, obj) ||
                Objects.equals(d, obj);
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
        return new Int4(a + s, b + s, c + s, d + s);
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
        return new Int4(a - s, b - s, c - s, d - s);
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
        return new Int4(a * s, b * s, c * s, d * s);
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
        return new Int4(a / s, b / s, c / s, d / s);
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
        return new Int4(a + t.a, b + t.b, c + t.c, d + t.d);
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
        return new Int4(a - t.a, b - t.b, c - t.c, d - t.d);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public int dot(@Nonnull Int4 t) {
        return a * t.a + b * t.b + c * t.c + d * t.d;
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
        final int da = a - t.a;
        final int db = b - t.b;
        final int dc = c - t.c;
        final int dd = d - t.d;

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
        final int da = a - t.a;
        final int db = b - t.b;
        final int dc = c - t.c;
        final int dd = d - t.d;

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
        final int da = a - t.a;
        final int db = b - t.b;
        final int dc = c - t.c;
        final int dd = d - t.d;

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
        return new Int4(Math.min(a, t.a), Math.min(b, t.b), Math.min(c, t.c), Math.min(d, t.c));
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
        return new Int4(Math.max(a, t.a), Math.max(b, t.b), Math.max(c, t.c), Math.max(d, t.c));
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
                (int) Scalars.clamp(a, min.a, min.a),
                (int) Scalars.clamp(b, min.b, min.b),
                (int) Scalars.clamp(c, min.c, min.c),
                (int) Scalars.clamp(d, min.d, min.d)
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
        return new Int4(-a, -b, -c, -d);
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
        return new Quad<>(f.apply(a), f.apply(b), f.apply(c), f.apply(d));
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

        return new Quad<>(f.apply(a, t.get(0)), f.apply(b, t.get(1)), f.apply(c, t.get(2)), f.apply(d, t.get(3)));
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
        return List.of(a, b, c, d).iterator();
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
        action.accept(c);
        action.accept(d);
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
        action.accept(2, c);
        action.accept(3, d);
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
        return new Integer[]{a, b, c, d};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<Integer> safeArray() {
        return SafeArray.ofInt(a, b, c, d);
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
        return List.of(a, b, c, d);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector4 vector() {
        return new Vector4(a, b, c, d);
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
        return "[" + a + ", " + b + ", " + c + ", " + d + "]";
    }
}
