package civitas.celestis.util.tuple;

import civitas.celestis.math.Numbers;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.io.ArrayReader;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A fixed-size integer-typed tuple which can only hold three components.
 * Triples use ABC notation to identify their components.
 *
 * @see Tuple
 * @see IntTuple
 */
public class Int3 implements IntTuple<Int3> {
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
     * Creates a new triple.
     *
     * @param a The first element of this triple
     * @param b The second element of this triple
     * @param c The third element of this triple
     */
    public Int3(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Creates a new triple.
     *
     * @param elements An array containing the elements of this triple in ABC order
     * @throws IllegalArgumentException When the array's length is not {@code 3}
     */
    public Int3(@Nonnull int[] elements) {
        if (elements.length != 3) {
            throw new IllegalArgumentException("The provided array's length is not 3.");
        }

        this.a = elements[0];
        this.b = elements[1];
        this.c = elements[2];
    }

    /**
     * Creates a new triple.
     *
     * @param t The tuple of which to copy elements from
     * @throws IllegalArgumentException When the tuple's size is not {@code 3}
     */
    public Int3(@Nonnull Tuple<? extends Number> t) {
        if (t.size() != 3) {
            throw new IllegalArgumentException("The provided tuple's size is not 3.");
        }

        this.a = t.get(0).intValue();
        this.b = t.get(1).intValue();
        this.c = t.get(2).intValue();
    }

    /**
     * Creates a new triple. The required format is "{@code [0, 0, 0]}".
     *
     * @param values The string representation of this triple
     * @throws NumberFormatException When the format is invalid
     */
    public Int3(@Nonnull String values) {
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

    /**
     * The third element of this tuple.
     */
    protected final int c;

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
        return 3;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return a == 0 && b == 0 && c == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public double norm() {
        return Math.sqrt(a * a + b * b + c * c);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int norm2() {
        return a * a + b * b + c * c;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int normManhattan() {
        return Math.abs(a) + Math.abs(b) + Math.abs(c);
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
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 3.");
        };
    }

    /**
     * Returns the A component (the first component) of this triple.
     *
     * @return The A component of this triple
     */
    public int a() {
        return a;
    }

    /**
     * Returns the B component (the second component) of this triple.
     *
     * @return The B component of this triple
     */
    public int b() {
        return b;
    }

    /**
     * Returns the C component (the third component) of this triple.
     *
     * @return The C component of this triple
     */
    public int c() {
        return c;
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
        return Objects.equals(a, obj) || Objects.equals(b, obj) || Objects.equals(c, obj);
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
    public Int3 add(int s) {
        return new Int3(a + s, b + s, c + s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract from this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int3 subtract(int s) {
        return new Int3(a - s, b - s, c - s);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this tuple by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int3 multiply(int s) {
        return new Int3(a * s, b * s, c * s);
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
    public Int3 divide(int s) throws ArithmeticException {
        return new Int3(a / s, b / s, c / s);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to add to this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int3 add(@Nonnull Int3 t) {
        return new Int3(a + t.a, b + t.b, c + t.c);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to subtract from this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int3 subtract(@Nonnull Int3 t) {
        return new Int3(a - t.a, b - t.b, c - t.c);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the dot product between
     * @return {@inheritDoc}
     */
    @Override
    public int dot(@Nonnull Int3 t) {
        return a * t.a + b * t.b + c * t.c;
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
    public double distance(@Nonnull Int3 t) {
        final int da = a - t.a;
        final int db = b - t.b;
        final int dc = c - t.c;

        return Math.sqrt(da * da + db * db + dc * dc);
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the squared Euclidean distance to
     * @return {@inheritDoc}
     */
    @Override
    public int distance2(@Nonnull Int3 t) {
        final int da = a - t.a;
        final int db = b - t.b;
        final int dc = c - t.c;

        return da * da + db * db + dc * dc;
    }

    /**
     * {@inheritDoc}
     *
     * @param t The tuple of which to get the Manhattan distance to
     * @return {@inheritDoc}
     */
    @Override
    public int distanceManhattan(@Nonnull Int3 t) {
        final int da = a - t.a;
        final int db = b - t.b;
        final int dc = c - t.c;

        return Math.abs(da) + Math.abs(db) + Math.abs(dc);
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
    public Int3 min(@Nonnull Int3 t) {
        return new Int3(Math.min(a, t.a), Math.min(b, t.b), Math.min(c, t.c));
    }

    /**
     * {@inheritDoc}
     *
     * @param t The boundary tuple to compare to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int3 max(@Nonnull Int3 t) {
        return new Int3(Math.max(a, t.a), Math.max(b, t.b), Math.max(c, t.c));
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
    public Int3 clamp(@Nonnull Int3 min, @Nonnull Int3 max) {
        return new Int3(
                (int) Numbers.clamp(a, min.a, max.a),
                (int) Numbers.clamp(b, min.b, max.b),
                (int) Numbers.clamp(c, min.c, max.c)
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
    public Int3 negate() {
        return new Int3(-a, -b, -c);
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
        return new Triple<>(f.apply(a), f.apply(b), f.apply(c));
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
        if (t.size() != 3) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        return new Triple<>(f.apply(a, t.get(0)), f.apply(b, t.get(1)), f.apply(c, t.get(2)));
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
        return List.of(a, b, c).iterator();
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
        return SafeArray.ofInt(a, b, c);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Integer> list() {
        return List.of(a, b, c);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Vector3 vector() {
        return new Vector3(a, b, c);
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
        return "[" + a + ", " + b + ", " + c + "]";
    }
}
