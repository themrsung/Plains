package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import civitas.celestis.util.function.ToFloatFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A shallowly immutable pair of objects.
 *
 * @param <E> The type of element to contain
 */
public class Object2<E> implements Tuple<E> {
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
     * Creates a new pair.
     *
     * @param a The first element of this pair
     * @param b The second element of this pair
     */
    public Object2(E a, E b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Creates a new pair.
     *
     * @param elements An array containing the elements of this pair in AB order
     * @throws IllegalArgumentException When the provided array's length is not {@code 2}
     */
    public Object2(@Nonnull E[] elements) {
        if (elements.length != 2) {
            throw new IllegalArgumentException("The provided array's length is not 2.");
        }

        this.a = elements[0];
        this.b = elements[1];
    }

    /**
     * Creates a new pair.
     *
     * @param t The tuple of which to copy elements from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 2}
     */
    public Object2(@Nonnull Tuple<? extends E> t) {
        if (t.size() != 2) {
            throw new IllegalArgumentException("The provided tuple's size is not 2.");
        }

        this.a = t.get(0);
        this.b = t.get(1);
    }

    //
    // Variables
    //

    /**
     * The first element of this tuple.
     */
    protected final E a;

    /**
     * The second element of this tuple.
     */
    protected final E b;

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

    //
    // Containment
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        return Objects.equals(a, obj) || Objects.equals(b, obj);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object of which to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<?> i) {
        for (final Object o : i) {
            if (!contains(o)) return false;
        }

        return true;
    }

    //
    // Getters
    //

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to get
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        return switch (i) {
            case 0 -> a;
            case 1 -> b;
            default -> throw new TupleIndexOutOfBoundsException(i);
        };
    }

    /**
     * Returns the first element of this tuple.
     *
     * @return The first element of this tuple
     */
    public E a() {
        return a;
    }

    /**
     * Returns the second element of this tuple.
     *
     * @return The second element of this tuple
     */
    public E b() {
        return b;
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this tuple
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return Tuple.of(f.apply(a), f.apply(b));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleTuple mapToDouble(@Nonnull ToDoubleFunction<? super E> f) {
        return DoubleTuple.of(f.applyAsDouble(a), f.applyAsDouble(b));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public FloatTuple mapToFloat(@Nonnull ToFloatFunction<? super E> f) {
        return FloatTuple.of(f.applyAsFloat(a), f.applyAsFloat(b));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongTuple mapToLong(@Nonnull ToLongFunction<? super E> f) {
        return LongTuple.of(f.applyAsLong(a), f.applyAsLong(b));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this tuple
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntTuple mapToInt(@Nonnull ToIntFunction<? super E> f) {
        return IntTuple.of(f.applyAsInt(a), f.applyAsInt(b));
    }

    /**
     * {@inheritDoc}
     *
     * @param t   The tuple of which to merge this tuple with
     * @param f   The merger function to handle the merging of the two tuples
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, ? extends G> f)
            throws IllegalArgumentException {
        if (t.size() != 2) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        return Tuple.of(f.apply(a, t.get(0)), f.apply(b, t.get(1)));
    }

    //
    // Iteration
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return Stream.of(a, b).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> a) {
        a.accept(this.a);
        a.accept(this.b);
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super E> a) {
        a.accept(0, this.a);
        a.accept(1, this.b);
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
    @SuppressWarnings("unchecked")
    public E[] array() {
        return (E[]) new Object[]{a, b};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<E> stream() {
        return Stream.of(a, b);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return List.of(a, b);
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
        if (!(obj instanceof Tuple<?> t)) return false;
        if (t.size() != 2) return false;
        return Objects.equals(a, t.get(0)) && Objects.equals(b, t.get(1));
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
        return "[" + a + ", " + b + "]";
    }

    /**
     * Returns the hash code of this instance.
     *
     * @return The hash code of this instance
     */
    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
