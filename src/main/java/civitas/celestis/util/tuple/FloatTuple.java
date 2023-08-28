package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import civitas.celestis.util.function.FloatFunction;
import civitas.celestis.util.function.FloatUnaryOperator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

/**
 * A specialized tuple which holds the primitive type {@code float}. Float tuples
 * * can be converted to their boxed form by calling {@link #boxed()}.
 *
 * @see Tuple
 * @see Float2
 * @see Float3
 * @see Float4
 * @see FloatArrayTuple
 */
public interface FloatTuple extends Serializable {
    //
    // Factory
    //

    /**
     * Creates a new float tuple from the provided array of components.
     *
     * @param components The components of which to construct the tuple from
     * @return A tuple constructed from the provided components
     */
    @Nonnull
    static FloatTuple of(@Nonnull float... components) {
        return switch (components.length) {
            case 0 -> EmptyFloatTuple.getInstance();
            case 2 -> new Float2(components);
            case 3 -> new Float3(components);
            case 4 -> new Float4(components);
            default -> new FloatArrayTuple(components);
        };
    }

    /**
     * Creates a new float tuple from the provided array of components.
     *
     * @param components The components of which to construct the tuple from
     * @return A tuple constructed from the provided components
     */
    @Nonnull
    static FloatTuple of(@Nonnull Float[] components) {
        return switch (components.length) {
            case 0 -> EmptyFloatTuple.getInstance();
            case 2 -> new Float2(components[0], components[1]);
            case 3 -> new Float3(components[0], components[1], components[2]);
            case 4 -> new Float4(components[0], components[1], components[2], components[3]);
            default -> {
                final float[] unboxed = new float[components.length];
                for (int i = 0; i < components.length; i++) unboxed[i] = components[i];
                yield new FloatArrayTuple(unboxed);
            }
        };
    }

    /**
     * Returns an empty float tuple.
     *
     * @return An empty float tuple
     */
    @Nonnull
    static FloatTuple empty() {
        return EmptyFloatTuple.getInstance();
    }

    //
    // Properties
    //

    /**
     * Returns the size of this tuple. (the number of components it has)
     *
     * @return The number of components this tuple has
     */
    int size();

    /**
     * Returns whether this tuple represents zero. (whether every component is zero)
     *
     * @return {@code true} if every component of this tuple is zero
     */
    boolean isZero();

    /**
     * Returns whether this tuple contains {@link Float#NaN}.
     *
     * @return {@code true} if at least one component of this tuple is not a number
     * @see Float#isNaN(float)
     */
    boolean isNaN();

    /**
     * Returns whether this tuple is finite.
     *
     * @return {@code true} if every component of this tuple is finite
     * @see Float#isFinite(float)
     */
    boolean isFinite();

    /**
     * Returns whether this tuple is infinite.
     *
     * @return {@code true} if at least one component of this tuple is infinite
     * @see Float#isInfinite(float)
     */
    boolean isInfinite();

    //
    // Containment
    //

    /**
     * Checks if this tuple contains the provided value {@code v}.
     *
     * @param v The value of which to check for containment
     * @return {@code true} if at least one component of this tuple is equal
     * to the provided value {@code v}
     */
    boolean contains(float v);

    /**
     * Checks if this tuple contains multiple values.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this tuple contains every component of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<Float> i);

    //
    // Getters
    //

    /**
     * Returns the {@code i}th component of this tuple.
     *
     * @param i The index of the component to get
     * @return The {@code i}th component of this tuple
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    float get(int i) throws IndexOutOfBoundsException;

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to each component of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each component of this tuple
     * @return The resulting tuple
     */
    @Nonnull
    FloatTuple map(@Nonnull FloatUnaryOperator f);

    /**
     * Applies the provided mapper function {@code f} to each component of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f   The function of which to apply to each component of this tuple
     * @param <F> The type of component to map this tuple to
     * @return The resulting tuple
     */
    @Nonnull
    <F> Tuple<F> mapToObj(@Nonnull FloatFunction<? extends F> f);

    //
    // Conversion
    //

    /**
     * Returns an array containing the components of this tuple in their proper order.
     *
     * @return The array representation of this tuple
     */
    @Nonnull
    float[] array();

    /**
     * Returns a stream whose source is the elements of this tuple.
     *
     * @return A stream whose source is the elements of this tuple
     */
    @Nonnull
    Stream<Float> stream();

    /**
     * Returns an unmodifiable list containing the components of this tuple in their proper order.
     *
     * @return The list representation of this tuple
     */
    @Nonnull
    List<Float> list();

    /**
     * Returns a tuple containing the components of this tuple in their boxed form.
     *
     * @return The boxed object representation of this tuple
     */
    @Nonnull
    Tuple<Float> boxed();

    //
    // Equality
    //

    /**
     * Checks for equality between this tuple and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a float tuple, and the number of components,
     * the order of the components, and the components' values are all equal
     */
    @Override
    boolean equals(@Nullable Object obj);

    //
    // Serialization
    //

    /**
     * Serializes this tuple into a string.
     *
     * @return The string representation of this tuple
     */
    @Nonnull
    @Override
    String toString();
}

/**
 * A tuple with zero elements.
 */
final class EmptyFloatTuple implements FloatTuple {
    /**
     * Returns the instance of this tuple.
     *
     * @return The empty tuple instance
     */
    @Nonnull
    public static EmptyFloatTuple getInstance() {
        return instance;
    }

    @Serial
    private static final long serialVersionUID = 0L;
    private static final EmptyFloatTuple instance = new EmptyFloatTuple();

    private EmptyFloatTuple() {}

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
