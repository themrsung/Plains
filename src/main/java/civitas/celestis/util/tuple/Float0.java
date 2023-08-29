package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import civitas.celestis.util.function.FloatFunction;
import civitas.celestis.util.function.FloatUnaryOperator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.stream.Stream;

/**
 * A tuple with zero elements.
 */
final class Float0 implements FloatTuple {
    /**
     * Returns the instance of this tuple.
     *
     * @return The empty tuple instance
     */
    @Nonnull
    public static Float0 getInstance() {
        return instance;
    }

    @Serial
    private static final long serialVersionUID = 0L;
    private static final Float0 instance = new Float0();

    private Float0() {}

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
    public boolean contains(float v) {
        return false;
    }

    @Override
    public boolean containsAll(@Nonnull Iterable<Float> i) {
        return false;
    }

    @Override
    public float get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    @Nonnull
    @Override
    public FloatTuple map(@Nonnull FloatUnaryOperator f) {
        return this;
    }

    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull FloatFunction<? extends F> f) {
        return Tuple.of();
    }

    @Nonnull
    @Override
    public float[] array() {
        return new float[0];
    }

    @Nonnull
    @Override
    public Stream<Float> stream() {
        return Stream.empty();
    }

    @Nonnull
    @Override
    public List<Float> list() {
        return List.of();
    }

    @Nonnull
    @Override
    public Tuple<Float> boxed() {
        return Tuple.of();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof FloatTuple t)) return false;
        return t.size() == 0;
    }

    @Nonnull
    @Override
    public String toString() {
        return "[]";
    }
}
