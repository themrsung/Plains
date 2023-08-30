package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import civitas.celestis.util.function.FloatFunction;
import civitas.celestis.util.function.FloatUnaryOperator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
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

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * The instance of this class.
     */
    private static final Float0 instance = new Float0();

    /**
     * Private constructor to enforce the Singleton design pattern.
     */
    private Float0() {}

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
    public boolean contains(float v) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Float> i) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public FloatTuple map(@Nonnull FloatUnaryOperator f) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull FloatFunction<? extends F> f) {
        return Tuple.of();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public float[] array() {
        return new float[0];
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<Float> stream() {
        return Stream.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Float> list() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Float> boxed() {
        return Tuple.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof FloatTuple t)) return false;
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
         * This guarantees that an empty FloatArrayTuple will have the same hash code
         * as any instance of Object0.
         */
        return Arrays.hashCode(array());
    }
}
