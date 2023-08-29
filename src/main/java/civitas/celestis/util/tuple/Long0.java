package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
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

    @Serial
    private static final long serialVersionUID = 0L;
    private static final Long0 instance = new Long0();

    private Long0() {}

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean contains(long v) {
        return false;
    }

    @Override
    public boolean containsAll(@Nonnull Iterable<Long> i) {
        return false;
    }

    @Override
    public long get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    @Nonnull
    @Override
    public LongTuple map(@Nonnull LongUnaryOperator f) {
        return this;
    }

    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull LongFunction<? extends F> f) {
        return Tuple.of();
    }

    @Nonnull
    @Override
    public long[] array() {
        return new long[0];
    }

    @Nonnull
    @Override
    public LongStream stream() {
        return LongStream.empty();
    }

    @Nonnull
    @Override
    public List<Long> list() {
        return List.of();
    }

    @Nonnull
    @Override
    public Tuple<Long> boxed() {
        return Tuple.of();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof LongTuple t)) return false;
        return t.size() == 0;
    }

    @Nonnull
    @Override
    public String toString() {
        return "[]";
    }
}
