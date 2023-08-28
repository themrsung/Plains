package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * An immutable type containing three primitive {@code float}s.
 */
public class Float3 implements FloatTuple {
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
     * Creates a new {@link Float3}.
     *
     * @param x The X component of this tuple
     * @param y The Y component of this tuple
     * @param z The Z component of this tuple
     */
    public Float3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new {@link Float3}.
     *
     * @param components An array containing the components of this tuple in XYZ order
     * @throws IllegalArgumentException When the provided array's length is not {@code 3}
     */
    public Float3(@Nonnull float[] components) {
        if (components.length != 3) {
            throw new IllegalArgumentException("The provided array's length is not 3.");
        }

        this.x = components[0];
        this.y = components[1];
        this.z = components[2];
    }

    /**
     * Creates a new {@link Float3}.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 3}
     */
    public Float3(@Nonnull FloatTuple t) {
        if (t.size() != 3) {
            throw new IllegalArgumentException("The provided tuple's size is not 3.");
        }

        this.x = t.get(0);
        this.y = t.get(1);
        this.z = t.get(2);
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

    /**
     * The Z component of this tuple.
     */
    protected final float z;

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
        return 3;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return x == 0 && y == 0 && z == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        return Float.isNaN(x + y + z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Float.isFinite(x + y + z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Float.isInfinite(x + y + z);
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
        return x == v || y == v || z == v;
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
            case 2 -> z;
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

    /**
     * Returns the Z component of this tuple.
     *
     * @return The Z component of this tuple
     */
    public float z() {
        return z;
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
    public Float3 map(@Nonnull UnaryOperator<Float> f) {
        return new Float3(f.apply(x), f.apply(y), f.apply(z));
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
    public <F> Tuple<F> mapToObj(@Nonnull Function<? super Float, ? extends F> f) {
        return Tuple.of(f.apply(x), f.apply(y), f.apply(z));
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
        return new float[]{x, y, z};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<Float> stream() {
        return Stream.of(x, y, z);
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
        if (t.size() != 3) return false;
        return x == t.get(0) && y == t.get(1) && z == t.get(2);
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
        return "[" + x + ", " + y + ", " + z + "]";
    }
}
