package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.stream.DoubleStream;

/**
 * An immutable type containing four primitive {@code double}s.
 */
public class Double4 implements DoubleTuple {
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
     * Creates a new {@link Double4}.
     *
     * @param w The W component of this tuple
     * @param x The X component of this tuple
     * @param y The Y component of this tuple
     * @param z The Z component of this tuple
     */
    public Double4(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a new {@link Double4}.
     *
     * @param components An array containing the components of this tuple in XYZ order
     * @throws IllegalArgumentException When the provided array's length is not {@code 4}
     */
    public Double4(@Nonnull double[] components) {
        if (components.length != 4) {
            throw new IllegalArgumentException("The provided array's length is not 4.");
        }

        this.w = components[0];
        this.x = components[1];
        this.y = components[2];
        this.z = components[3];
    }

    /**
     * Creates a new {@link Double4}.
     *
     * @param t The tuple of which to copy component values from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 4}
     */
    public Double4(@Nonnull DoubleTuple t) {
        if (t.size() != 4) {
            throw new IllegalArgumentException("The provided tuple's size is not 4.");
        }

        this.w = t.get(0);
        this.x = t.get(1);
        this.y = t.get(2);
        this.z = t.get(3);
    }

    //
    // Variables
    //

    /**
     * The W component of this tuple.
     */
    protected final double w;

    /**
     * The X component of this tuple.
     */
    protected final double x;

    /**
     * The Y component of this tuple.
     */
    protected final double y;

    /**
     * The Z component of this tuple.
     */
    protected final double z;

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
        return 4;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isZero() {
        return w == 0 && x == 0 && y == 0 && z == 0;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isNaN() {
        return Double.isNaN(w + x + y + z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isFinite() {
        return Double.isFinite(w + x + y + z);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return Double.isInfinite(w + x + y + z);
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
    public boolean contains(double v) {
        return w == v || x == v || y == v || z == v;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<Double> i) {
        for (final Double v : i) {
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
    public double get(int i) throws IndexOutOfBoundsException {
        return switch (i) {
            case 0 -> w;
            case 1 -> x;
            case 2 -> y;
            case 3 -> z;
            default -> throw new TupleIndexOutOfBoundsException(i);
        };
    }

    /**
     * Returns the W component of this tuple.
     * @return The W component of this tuple
     */
    public double w() {
        return w;
    }

    /**
     * Returns the X component of this tuple.
     *
     * @return The X component of this tuple
     */
    public double x() {
        return x;
    }

    /**
     * Returns the Y component of this tuple.
     *
     * @return The Y component of this tuple
     */
    public double y() {
        return y;
    }

    /**
     * Returns the Z component of this tuple.
     * @return The Z component of this tuple
     */
    public double z() {
        return z;
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each component of this tuple
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> map(@Nonnull Function<? super Double, ? extends F> f) {
        return Tuple.of(f.apply(w), f.apply(x), f.apply(y), f.apply(z));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each component of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Double4 mapToDouble(@Nonnull DoubleUnaryOperator f) {
        return new Double4(f.applyAsDouble(w), f.applyAsDouble(x), f.applyAsDouble(y), f.applyAsDouble(z));
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
    public double[] array() {
        return new double[]{w, x, y, z};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleStream stream() {
        return Arrays.stream(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Double> list() {
        return stream().boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Double> boxed() {
        return Tuple.of(stream().boxed().toArray(Double[]::new));
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
        if (!(obj instanceof DoubleTuple t)) return false;
        if (t.size() != 4) return false;
        return w == t.get(0) && x == t.get(1) && y == t.get(2) && z == t.get(3);
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
        return "[" + w + ", " + x + ", " + y + ", " + z + "]";
    }
}
