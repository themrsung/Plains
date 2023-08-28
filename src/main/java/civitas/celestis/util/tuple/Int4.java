package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * An immutable type containing four primitive {@code int}s.
 */
public class Int4 implements IntTuple {
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
     * Creates a new {@link Int4}.
     *
     * @param w The W component of this tuple
     * @param x The X component of this tuple
     * @param y The Y component of this tuple
     * @param z The Z component of this tuple
     */
    public Int4(int w, int x, int y, int z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new {@link Int4}.
     *
     * @param components An array containing the components of this tuple in XYZ order
     * @throws IllegalArgumentException When the provided array's length is not {@code 4}
     */
    public Int4(@Nonnull int[] components) {
        if (components.length != 4) {
            throw new IllegalArgumentException("The provided array's length is not 4.");
        }

        this.w = components[0];
        this.x = components[1];
        this.y = components[2];
        this.z = components[3];
    }

    /**
     * Creates a new {@link Int4}.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 4}
     */
    public Int4(@Nonnull IntTuple t) {
        if (t.size() != 4) {
            throw new IllegalArgumentException("The provided tuple's size is not 4.");
        }

        this.w = t.get(0);
        this.x = t.get(1);
        this.y = t.get(2);
        this.z = t.get(3);
    }

    //
    // Variables
    //

    /**
     * The W component of this tuple.
     */
    protected final int w;

    /**
     * The X component of this tuple.
     */
    protected final int x;

    /**
     * The Y component of this tuple.
     */
    protected final int y;

    /**
     * The Z component of this tuple.
     */
    protected final int z;

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
        return w == 0 && x == 0 && y == 0 && z == 0;
    }

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param v The value of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(int v) {
        return w == v || x == v || y == v || z == v;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Integer> i) {
        for (final Integer v : i) {
            if (v == null) return false;
            if (!contains(v)) return false;
        }

        return true;
    }

    //
    // Getters
    //

    /**
     * {@inheritDoc}
     *
     * @param i The index of the component to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public int get(int i) throws IndexOutOfBoundsException {
        return switch (i) {
            case 0 -> w;
            case 1 -> x;
            case 2 -> y;
            case 3 -> z;
            default -> throw new TupleIndexOutOfBoundsException(i);
        };
    }

    /**
     * Returns the W component of this tuple.
     *
     * @return The W component of this tuple
     */
    public int w() {
        return w;
    }

    /**
     * Returns the X component of this tuple.
     *
     * @return The X component of this tuple
     */
    public int x() {
        return x;
    }

    /**
     * Returns the Y component of this tuple.
     *
     * @return The Y component of this tuple
     */
    public int y() {
        return y;
    }

    /**
     * Returns the Z component of this tuple.
     *
     * @return The Z component of this tuple
     */
    public int z() {
        return z;
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each component of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Int4 map(@Nonnull IntUnaryOperator f) {
        return new Int4(f.applyAsInt(w), f.applyAsInt(x), f.applyAsInt(y), f.applyAsInt(z));
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each component of this tuple
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull IntFunction<? extends F> f) {
        return Tuple.of(f.apply(w), f.apply(x), f.apply(y), f.apply(z));
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
    public int[] array() {
        return new int[]{w, x, y, z};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntStream stream() {
        return IntStream.of(w, x, y, z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Integer> list() {
        return stream().boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Integer> boxed() {
        return Tuple.of(stream().boxed().toArray(Integer[]::new));
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
        if (!(obj instanceof IntTuple t)) return false;
        if (t.size() != 4) return false;
        return w == t.get(0) && x == t.get(1) && y == t.get(2) && z == t.get(3);
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
        return "[" + w + ", " + x + ", " + y + ", " + z + "]";
    }
}
