package civitas.celestis.util.collection;

import civitas.celestis.math.Sign;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A {@link GroupableArrayList} which contains a subtype of {@link Number}.
 * The signs of the component numbers can be extracted by calling {@link #getSigns()}.
 *
 * @param <N> The type of number this list should hold
 * @see GroupableArrayList
 * @see GroupableList
 */
public class NumericArrayList<N extends Number> extends GroupableArrayList<N> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 2275436368524113054L;

    //
    // Static Initializers
    //

    /**
     * Creates a new numeric array list from an array of values.
     *
     * @param values The values to contain
     * @param <N>    The type of number to hold
     * @return A numeric array list constructed from the array of values
     */
    @Nonnull
    @SafeVarargs
    public static <N extends Number> NumericArrayList<N> of(@Nonnull N... values) {
        final NumericArrayList<N> result = new NumericArrayList<>(values.length);
        result.addAll(Arrays.asList(values));
        return result;
    }

    //
    // Constructors
    //

    /**
     * Creates a new numeric array list.
     *
     * @param initialCapacity The initial capacity of this list
     */
    public NumericArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Creates a new numeric array list.
     */
    public NumericArrayList() {
    }

    /**
     * Creates a new numeric array list.
     *
     * @param c The collection of which to copy elements from
     */
    public NumericArrayList(@Nonnull Collection<? extends N> c) {
        super(c);
    }

    /**
     * Creates a new numeric array list.
     *
     * @param c The collectable object of which to copy elements from
     */
    public NumericArrayList(@Nonnull Collectable<? extends N> c) {
        super(c);
    }

    //
    // Sign
    //

    /**
     * Returns a list containing the signs of the numbers of this list.
     *
     * @return A list containing this list's elements' signs
     * @see Sign
     */
    @Nonnull
    public GroupableList<Sign> getSigns() {
        return new GroupableArrayList<>(stream().map(Sign::ofNumber).toList());
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f The function to apply to each element of this object
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public NumericArrayList<N> transform(@Nonnull Function<? super N, N> f) {
        return new NumericArrayList<>(stream().map(f).toList());
    }

    /**
     * {@inheritDoc}
     *
     * @param l The other list to merge with
     * @param f The merger function to apply
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public NumericArrayList<N> merge(@Nonnull List<N> l, @Nonnull BiFunction<? super N, ? super N, N> f)
            throws IllegalArgumentException {
        return new NumericArrayList<>(super.merge(l, f));
    }
}
