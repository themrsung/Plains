package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
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
    @Serial
    private static final long serialVersionUID = 0L;
    private static final Double0 instance = new Double0();

    private Double0() {}

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean isNaN() {
        return false;
    }

    @Override
    public boolean isFinite() {
        return false;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public boolean contains(double v) {
        return false;
    }

    @Override
    public boolean containsAll(@Nonnull Iterable<Double> i) {
        return false;
    }

    @Override
    public double get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    @Nonnull
    @Override
    public DoubleTuple map(@Nonnull DoubleUnaryOperator f) {
        return this;
    }

    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull DoubleFunction<? extends F> f) {
        return Tuple.of();
    }

    @Nonnull
    @Override
    public double[] array() {
        return new double[0];
    }

    @Nonnull
    @Override
    public DoubleStream stream() {
        return DoubleStream.empty();
    }

    @Nonnull
    @Override
    public List<Double> list() {
        return List.of();
    }

    @Nonnull
    @Override
    public Tuple<Double> boxed() {
        return Tuple.of();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof DoubleTuple t)) return false;
        return t.size() == 0;
    }

    @Nonnull
    @Override
    public String toString() {
        return "[]";
    }
}
