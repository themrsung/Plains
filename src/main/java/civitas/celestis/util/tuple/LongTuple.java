package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.List;
import java.util.function.LongFunction;
import java.util.function.LongUnaryOperator;
import java.util.stream.LongStream;

/**
 * A specialized tuple which holds the primitive type {@code long}. Long tuples
 * can be converted to their boxed form by calling {@link #boxed()}.
 *
 * @see Tuple
 * @see Long2
 * @see Long3
 * @see Long4
 * @see LongArrayTuple
 */
public interface LongTuple extends BaseTuple<Long> {
    //
    // Factory
    //

    /**
     * Creates a new long tuple from the provided array of components.
     *
     * @param components The components of which to construct the tuple from
     * @return A tuple constructed from the provided components
     */
    @Nonnull
    static LongTuple of(@Nonnull long... components) {
        return switch (components.length) {
            case 2 -> new Long2(components);
            case 3 -> new Long3(components);
            case 4 -> new Long4(components);
            default -> new LongArrayTuple(components);
        };
    }

    //
    // Properties
    //

    /**
     * Returns the size of this tuple. (the number of components it has)
     *
     * @return The number of components this tuple has
     */
    @Override
    int size();

    /**
     * Returns whether this tuple represents zero. (whether every component is zero)
     *
     * @return {@code true} if every component of this tuple is zero
     */
    boolean isZero();

    //
    // Containment
    //

    /**
     * Checks if this tuple contains the provided value {@code v}.
     *
     * @param v The value of which to check for containment
     * @return {@code true} if at least one component of this tuple is equal to the provided value {@code v}
     */
    boolean contains(long v);

    /**
     * Checks if this tuple contains multiple values.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this tuple contains every component of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<Long> i);

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
    long get(int i) throws IndexOutOfBoundsException;

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
    LongTuple map(@Nonnull LongUnaryOperator f);

    /**
     * Applies the provided mapper function {@code f} to each component of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f   The function of which to apply to each component of this tuple
     * @param <F> The type of component to map this tuple to
     * @return The resulting tuple
     */
    @Nonnull
    <F> Tuple<F> mapToObj(@Nonnull LongFunction<? extends F> f);

    //
    // Conversion
    //

    /**
     * Returns an array containing the components of this tuple in their proper order.
     *
     * @return The array representation of this tuple
     */
    @Nonnull
    long[] array();

    /**
     * Returns a stream whose source is the elements of this tuple.
     *
     * @return A stream whose source is the elements of this tuple
     */
    @Nonnull
    LongStream stream();

    /**
     * Returns an unmodifiable list containing the components of this tuple in their proper order.
     *
     * @return The list representation of this tuple
     */
    @Nonnull
    @Override
    List<Long> list();

    /**
     * Returns a tuple containing the components of this tuple in their boxed form.
     *
     * @return The boxed object representation of this tuple
     */
    @Nonnull
    Tuple<Long> boxed();

    //
    // Equality
    //

    /**
     * Checks for equality between this tuple and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a long tuple, and the number of components,
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
final class EmptyLongTuple implements LongTuple {
    /**
     * Returns the instance of this tuple.
     *
     * @return The empty tuple instance
     */
    @Nonnull
    public static EmptyLongTuple getInstance() {
        return instance;
    }

    @Serial
    private static final long serialVersionUID = 0L;
    private static final EmptyLongTuple instance = new EmptyLongTuple();

    private EmptyLongTuple() {}

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean contains(long v) {
        return false;
    }

    @Override
    public boolean containsAll(@Nonnull Iterable<Long> i) {
        return false;
    }

    @Override
    public long get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    @Nonnull
    @Override
    public LongTuple map(@Nonnull LongUnaryOperator f) {
        return this;
    }

    @Nonnull
    @Override
    public <F> Tuple<F> mapToObj(@Nonnull LongFunction<? extends F> f) {
        return Tuple.of();
    }

    @Nonnull
    @Override
    public long[] array() {
        return new long[0];
    }

    @Nonnull
    @Override
    public LongStream stream() {
        return LongStream.empty();
    }

    @Nonnull
    @Override
    public List<Long> list() {
        return List.of();
    }

    @Nonnull
    @Override
    public Tuple<Long> boxed() {
        return Tuple.of();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof LongTuple t)) return false;
        return t.size() == 0;
    }

    @Nonnull
    @Override
    public String toString() {
        return "[]";
    }
}
