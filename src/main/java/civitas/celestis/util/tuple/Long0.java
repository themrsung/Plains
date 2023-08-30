package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

/**
 * A tuple with zero elements.
 */
final class Long0 implements LongTuple {
    /**
     * Returns the instance of this tuple.
     *
     * @return The empty tuple instance
     */
    @Nonnull
    public static Long0 getInstance() {
        return instance;
    }

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * The instance of this class.
     */
    private static final Long0 instance = new Long0();

    /**
     * Private constructor to enforce the Singleton design pattern.
     */
    private Long0() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(long v) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Long> i) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongTuple map(@Nonnull LongUnaryOperator f) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull LongFunction<? extends F> f) {
        return Tuple.of();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public long[] array() {
        return new long[0];
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongStream stream() {
        return LongStream.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Long> list() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Long> boxed() {
        return Tuple.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof LongTuple t)) return false;
        return t.size() == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return "[]";
    }

    /**
     * Returns the hash code of this instance.
     */
    @Override
    public int hashCode() {
        /*
         * This guarantees that an empty LongArrayTuple will have the same hash code
         * as any instance of Object0.
         */
        return Arrays.hashCode(array());
    }
}
