package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

/**
 * An immutable type containing two primitive {@code long}s.
 */
public class Long2 implements LongTuple {
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
     * Creates a new {@link Long2}.
     *
     * @param x The X component of this tuple
     * @param y The Y component of this tuple
     */
    public Long2(long x, long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new {@link Long2}.
     *
     * @param components An array containing the components of this tuple in XY order
     * @throws IllegalArgumentException When the provided array's length is not {@code 2}
     */
    public Long2(@Nonnull long[] components) {
        if (components.length != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.x = components[0];
        this.y = components[1];
    }

    /**
     * Creates a new {@link Long2}.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 2}
     */
    public Long2(@Nonnull LongTuple t) {
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
    protected final long x;

    /**
     * The Y component of this tuple.
     */
    protected final long y;

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
    public boolean contains(long v) {
        return x == v || y == v;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Long> i) {
        for (final Long v : i) {
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
    public long get(int i) throws IndexOutOfBoundsException {
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
    public long x() {
        return x;
    }

    /**
     * Returns the Y component of this tuple.
     *
     * @return The Y component of this tuple
     */
    public long y() {
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
    public Long2 map(@Nonnull LongUnaryOperator f) {
        return new Long2(f.applyAsLong(x), f.applyAsLong(y));
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
    public <F> Tuple<F> mapToObj(@Nonnull LongFunction<? extends F> f) {
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
    public long[] array() {
        return new long[]{x, y};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongStream stream() {
        return LongStream.of(x, y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Long> list() {
        return stream().boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Long> boxed() {
        return Tuple.of(stream().boxed().toArray(Long[]::new));
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
        if (!(obj instanceof LongTuple t)) return false;
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
