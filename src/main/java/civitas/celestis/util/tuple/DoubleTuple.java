package civitas.celestis.util.tuple;

import civitas.celestis.util.array.DoubleArray;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

/**
 * A specialized tuple which holds the primitive type {@code double}. Double tuples
 * can be converted to their boxed form by calling {@link #boxed()}.
 *
 * @see Tuple
 * @see Double2
 * @see Double3
 * @see Double4
 * @see DoubleArrayTuple
 */
public interface DoubleTuple extends BaseTuple<Double> {
    //
    // Factory
    //

    /**
     * Creates a new double tuple from the provided array of components.
     *
     * @param components The components of which to construct the tuple from
     * @return A tuple constructed from the provided components
     */
    @Nonnull
    static DoubleTuple of(@Nonnull double... components) {
        return switch (components.length) {
            case 0 -> Double0.getInstance();
            case 2 -> new Double2(components);
            case 3 -> new Double3(components);
            case 4 -> new Double4(components);
            default -> new DoubleArrayTuple(components);
        };
    }

    /**
     * Returns an empty double tuple.
     *
     * @return An empty double tuple
     */
    @Nonnull
    static DoubleTuple empty() {
        return Double0.getInstance();
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

    /**
     * Returns whether this tuple contains {@link Double#NaN}.
     *
     * @return {@code true} if at least one component of this tuple is not a number
     * @see Double#isNaN(double)
     */
    boolean isNaN();

    /**
     * Returns whether this tuple is finite.
     *
     * @return {@code true} if every component of this tuple is finite
     * @see Double#isFinite(double)
     */
    boolean isFinite();

    /**
     * Returns whether this tuple is infinite.
     *
     * @return {@code true} if at least one component of this tuple is infinite
     * @see Double#isInfinite(double)
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
    boolean contains(double v);

    /**
     * Checks if this tuple contains multiple values.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this tuple contains every component of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<Double> i);

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
    double get(int i) throws IndexOutOfBoundsException;

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
    DoubleTuple map(@Nonnull DoubleUnaryOperator f);

    /**
     * Applies the provided mapper function {@code f} to each component of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f   The function of which to apply to each component of this tuple
     * @param <F> The type of component to map this tuple to
     * @return The resulting tuple
     */
    @Nonnull
    <F> Tuple<F> mapToObj(@Nonnull DoubleFunction<? extends F> f);

    //
    // Conversion
    //

    /**
     * Returns an array containing the components of this tuple in their proper order.
     *
     * @return The array representation of this tuple
     */
    @Nonnull
    double[] array();

    /**
     * Returns a double array containing the components of this tuple in their proper order.
     *
     * @return The array representation of this tuple
     * @see DoubleArray
     */
    @Nonnull
    default DoubleArray doubleArray() {
        return DoubleArray.from(stream());
    }

    /**
     * Returns a stream whose source is the elements of this tuple.
     *
     * @return A stream whose source is the elements of this tuple
     * @see DoubleStream
     */
    @Nonnull
    DoubleStream stream();

    /**
     * Returns an unmodifiable list containing the components of this tuple in their proper order.
     *
     * @return The list representation of this tuple
     * @see List
     */
    @Nonnull
    @Override
    List<Double> list();

    /**
     * Returns a tuple containing the components of this tuple in their boxed form.
     *
     * @return The boxed object representation of this tuple
     * @see Tuple
     */
    @Nonnull
    Tuple<Double> boxed();

    //
    // Equality
    //

    /**
     * Checks for equality between this tuple and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also a double tuple, and the number of components,
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

