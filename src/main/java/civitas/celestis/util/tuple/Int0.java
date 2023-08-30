package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
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

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * The instance of this class.
     */
    private static final Int0 instance = new Int0();

    /**
     * Private constructor to enforce the Singleton design pattern.
     */
    private Int0() {}

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
    public boolean contains(int v) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Integer> i) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntTuple map(@Nonnull IntUnaryOperator f) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull IntFunction<? extends F> f) {
        return Tuple.of();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public int[] array() {
        return new int[0];
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntStream stream() {
        return IntStream.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Integer> list() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Integer> boxed() {
        return Tuple.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof IntTuple t)) return false;
        return t.size() == 0;
    }

    /**
     * Returns the hash code of this instance.
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
         * This guarantees that an empty IntArrayTuple will have the same hash code
         * as any instance of Object0.
         */
        return Arrays.hashCode(array());
    }
}
