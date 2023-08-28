package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * An immutable type containing two primitive {@code int}s.
 */
public class Int2 implements IntTuple {
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
     * Creates a new {@link Int2}.
     *
     * @param x The X component of this tuple
     * @param y The Y component of this tuple
     */
    public Int2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new {@link Int2}.
     *
     * @param components An array containing the components of this tuple in XY order
     * @throws IllegalArgumentException When the provided array's length is not {@code 2}
     */
    public Int2(@Nonnull int[] components) {
        if (components.length != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.x = components[0];
        this.y = components[1];
    }

    /**
     * Creates a new {@link Int2}.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 2}
     */
    public Int2(@Nonnull IntTuple t) {
        if (t.size() != 2) {
            throw new IllegalArgumentException("The provided tuple's size is not 2.");
        }

        this.x = t.get(0);
        this.y = t.get(1);
    }

    //
    // Variables
    //

    /**
     * The X component of this tuple.
     */
    protected final int x;

    /**
     * The Y component of this tuple.
     */
    protected final int y;

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
        return x == 0 && y == 0;
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
        return x == v || y == v;
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
            case 0 -> x;
            case 1 -> y;
            default -> throw new TupleIndexOutOfBoundsException(i);
        };
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
    public Int2 map(@Nonnull IntUnaryOperator f) {
        return new Int2(f.applyAsInt(x), f.applyAsInt(y));
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
        return Tuple.of(f.apply(x), f.apply(y));
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
        return new int[]{x, y};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntStream stream() {
        return IntStream.of(x, y);
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
        if (t.size() != 2) return false;
        return x == t.get(0) && y == t.get(1);
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
        return "[" + x + ", " + y + "]";
    }
}
