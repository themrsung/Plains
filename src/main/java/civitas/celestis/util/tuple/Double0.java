package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

/**
 * A tuple with zero elements.
 */
final class Double0 implements DoubleTuple {
    /**
     * Returns the instance of this tuple.
     *
     * @return The empty tuple instance
     */
    @Nonnull
    public static Double0 getInstance() {
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
    private static final Double0 instance = new Double0();

    /**
     * Private constructor to enforce the Singleton design pattern.
     */
    private Double0() {}

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
    public boolean isNaN() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(double v) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Double> i) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleTuple map(@Nonnull DoubleUnaryOperator f) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull DoubleFunction<? extends F> f) {
        return Tuple.of();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public double[] array() {
        return new double[0];
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleStream stream() {
        return DoubleStream.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Double> list() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Double> boxed() {
        return Tuple.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof DoubleTuple t)) return false;
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
         * This guarantees that an empty DoubleArrayTuple will have the same hash code
         * as any instance of Object0.
         */
        return Arrays.hashCode(array());
    }
}
