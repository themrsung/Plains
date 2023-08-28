package civitas.celestis.util.tuple;

import civitas.celestis.util.array.IntArray;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

/**
 * A specialized tuple which holds the primitive type {@code int}. Integer tuples
 * * can be converted to their boxed form by calling {@link #boxed()}.
 *
 * @see Tuple
 * @see Int2
 * @see Int3
 * @see Int4
 * @see IntArrayTuple
 */
public interface IntTuple extends BaseTuple<Integer> {
    //
    // Factory
    //

    /**
     * Creates a new integer tuple from the provided array of components.
     *
     * @param components The components of which to construct the tuple from
     * @return A tuple constructed from the provided components
     */
    @Nonnull
    static IntTuple of(@Nonnull int... components) {
        return switch (components.length) {
            case 0 -> Int0.getInstance();
            case 1 -> new Int1(components);
            case 2 -> new Int2(components);
            case 3 -> new Int3(components);
            case 4 -> new Int4(components);
            default -> new IntArrayTuple(components);
        };
    }

    /**
     * Returns an empty integer tuple.
     *
     * @return An empty integer tuple
     */
    @Nonnull
    static IntTuple empty() {
        return Int0.getInstance();
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
    boolean contains(int v);

    /**
     * Checks if this tuple contains multiple values.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this tuple contains every component of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<Integer> i);

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
    int get(int i) throws IndexOutOfBoundsException;

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to each component of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f   The function of which to apply to each component of this tuple
     * @param <F> The type of component to map this tuple to
     * @return The resulting tuple
     */
    @Nonnull
    <F> Tuple<F> mapToObj(@Nonnull IntFunction<? extends F> f);

    /**
     * Applies the provided mapper function {@code f} to each component of this tuple,
     * then returns a new tuple containing the return values of the function {@code f}.
     *
     * @param f The function of which to apply to each component of this tuple
     * @return The resulting tuple
     */
    @Nonnull
    IntTuple map(@Nonnull IntUnaryOperator f);

    //
    // Conversion
    //

    /**
     * Returns an array containing the components of this tuple in their proper order.
     *
     * @return The array representation of this tuple
     */
    @Nonnull
    int[] array();

    /**
     * Returns an integer array containing the components of this tuple in their proper order.
     *
     * @return The array representation of this tuple
     * @see IntArray
     */
    @Nonnull
    default IntArray intArray() {
        return IntArray.from(stream());
    }

    /**
     * Returns a stream whose source is the elements of this tuple.
     *
     * @return A stream whose source is the elements of this tuple
     * @see IntStream
     */
    @Nonnull
    IntStream stream();

    /**
     * Returns an unmodifiable list containing the components of this tuple in their proper order.
     *
     * @return The list representation of this tuple
     * @see List
     */
    @Nonnull
    @Override
    List<Integer> list();

    /**
     * Returns a tuple containing the components of this tuple in their boxed form.
     *
     * @return The boxed object representation of this tuple
     * @see Tuple
     */
    @Nonnull
    Tuple<Integer> boxed();

    //
    // Equality
    //

    /**
     * Checks for equality between this tuple and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the other object is also an integer tuple, and the number of components,
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


