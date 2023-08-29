package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * A tuple with zero elements.
 */
final class Int0 implements IntTuple {
    /**
     * Returns the instance of this tuple.
     *
     * @return The empty tuple instance
     */
    @Nonnull
    public static Int0 getInstance() {
        return instance;
    }

    @Serial
    private static final long serialVersionUID = 0L;
    private static final Int0 instance = new Int0();

    private Int0() {}

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean contains(int v) {
        return false;
    }

    @Override
    public boolean containsAll(@Nonnull Iterable<Integer> i) {
        return false;
    }

    @Override
    public int get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    @Nonnull
    @Override
    public IntTuple map(@Nonnull IntUnaryOperator f) {
        return this;
    }

    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull IntFunction<? extends F> f) {
        return Tuple.of();
    }

    @Nonnull
    @Override
    public int[] array() {
        return new int[0];
    }

    @Nonnull
    @Override
    public IntStream stream() {
        return IntStream.empty();
    }

    @Nonnull
    @Override
    public List<Integer> list() {
        return List.of();
    }

    @Nonnull
    @Override
    public Tuple<Integer> boxed() {
        return Tuple.of();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof IntTuple t)) return false;
        return t.size() == 0;
    }

    @Nonnull
    @Override
    public String toString() {
        return "[]";
    }
}
