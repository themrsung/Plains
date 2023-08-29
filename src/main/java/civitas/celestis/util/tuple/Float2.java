package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import civitas.celestis.util.function.FloatFunction;
import civitas.celestis.util.function.FloatUnaryOperator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * An immutable type containing two primitive {@code float}s.
 */
public class Float2 implements FloatTuple {
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
     * Creates a new {@link Float2}.
     *
     * @param x The X component of this tuple
     * @param y The Y component of this tuple
     */
    public Float2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new {@link Float2}.
     *
     * @param components An array containing the components of this tuple in XY order
     * @throws IllegalArgumentException When the provided array's length is not {@code 2}
     */
    public Float2(@Nonnull float[] components) {
        if (components.length != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.x = components[0];
        this.y = components[1];
    }

    /**
     * Creates a new {@link Float2}.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 2}
     */
    public Float2(@Nonnull FloatTuple t) {
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
    protected final float x;

    /**
     * The Y component of this tuple.
     */
    protected final float y;

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

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        return Float.isNaN(x + y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Float.isFinite(x + y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Float.isInfinite(x + y);
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
    public boolean contains(float v) {
        return x == v || y == v;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Float> i) {
        for (final Float v : i) {
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
    public float get(int i) throws IndexOutOfBoundsException {
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
    public float x() {
        return x;
    }

    /**
     * Returns the Y component of this tuple.
     *
     * @return The Y component of this tuple
     */
    public float y() {
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
    public Float2 map(@Nonnull FloatUnaryOperator f) {
        return new Float2(f.applyAsFloat(x), f.applyAsFloat(y));
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
    public <F> Tuple<F> mapToObj(@Nonnull FloatFunction<? extends F> f) {
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
    public float[] array() {
        return new float[]{x, y};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<Float> stream() {
        return Stream.of(x, y);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Float> list() {
        return stream().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Float> boxed() {
        return Tuple.of(stream().toArray(Float[]::new));
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
        if (!(obj instanceof FloatTuple t)) return false;
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

    /**
     * Returns the hash code of this instance.
     *
     * @return The hash code of this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
