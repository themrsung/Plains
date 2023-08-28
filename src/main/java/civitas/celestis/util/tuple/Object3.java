package civitas.celestis.util.tuple;

import civitas.celestis.exception.TupleIndexOutOfBoundsException;
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
 * A shallowly immutable triple of objects.
 *
 * @param <E> The type of element to contain
 */
public class Object3<E> implements Tuple<E> {
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
     * Creates a new triple.
     *
     * @param a The first element of this triple
     * @param b The second element of this triple
     * @param c The third element of this triple
     */
    public Object3(E a, E b, E c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Creates a new triple.
     *
     * @param elements An array containing the elements of this triple in ABC order
     * @throws IllegalArgumentException When the provided array's length is not {@code 3}
     */
    public Object3(@Nonnull E[] elements) {
        if (elements.length != 3) {
            throw new IllegalArgumentException("The provided array's length is not 3.");
        }

        this.a = elements[0];
        this.b = elements[1];
        this.c = elements[2];
    }

    /**
     * Creates a new triple.
     *
     * @param t The tuple of which to copy elements from
     * @throws IllegalArgumentException When the provided tuple {@code t}'s size is not {@code 3}
     */
    public Object3(@Nonnull Tuple<? extends E> t) {
        if (t.size() != 3) {
            throw new IllegalArgumentException("The provided tuple's size is not 3.");
        }

        this.a = t.get(0);
        this.b = t.get(1);
        this.c = t.get(2);
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

    /**
     * The third element of this tuple.
     */
    protected final E c;

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
        return Objects.equals(a, obj) || Objects.equals(b, obj) || Objects.equals(c, obj);
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
            case 2 -> c;
            default -> throw new TupleIndexOutOfBoundsException(i);
        };
    }

    /**
     * Returns the first element of this tuple.
     *
     * @return The first element of this tuple
     */
    public E getA() {
        return a;
    }

    /**
     * Returns the second element of this tuple.
     *
     * @return The second element of this tuple
     */
    public E getB() {
        return b;
    }

    /**
     * Returns the third element of this tuple.
     *
     * @return The third element of this tuple
     */
    public E getC() {
        return c;
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
        return Tuple.of(f.apply(a), f.apply(b), f.apply(c));
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
        return DoubleTuple.of(f.applyAsDouble(a), f.applyAsDouble(b), f.applyAsDouble(c));
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
        return FloatTuple.of(f.applyAsFloat(a), f.applyAsFloat(b), f.applyAsFloat(c));
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
        return LongTuple.of(f.applyAsLong(a), f.applyAsLong(b), f.applyAsLong(c));
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
        return IntTuple.of(f.applyAsInt(a), f.applyAsInt(b), f.applyAsInt(c));
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
        if (t.size() != 3) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        return Tuple.of(f.apply(a, t.get(0)), f.apply(b, t.get(1)), f.apply(c, t.get(2)));
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
        return Stream.of(a, b, c).iterator();
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
        a.accept(this.c);
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
        a.accept(2, this.c);
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
        return (E[]) new Object[]{a, b, c};
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<E> stream() {
        return Stream.of(a, b, c);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return List.of(a, b, c);
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
        if (t.size() != 3) return false;
        return Objects.equals(a, t.get(0)) && Objects.equals(b, t.get(1)) && Objects.equals(c, t.get(2));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return "[" + a + ", " + b + ", " + c + "]";
    }
}
