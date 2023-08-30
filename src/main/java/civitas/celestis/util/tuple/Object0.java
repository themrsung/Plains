package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import civitas.celestis.util.function.ToFloatFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A tuple with no elements.
 *
 * @param <E> The type of element to (not) hold
 */
final class Object0<E> implements Tuple<E> {
    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Package-private constructor to ensure proper usage.
     */
    Object0() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(@Nullable Object obj) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(@Nonnull Iterable<?> i) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return new Object0<>();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleTuple mapToDouble(@Nonnull ToDoubleFunction<? super E> f) {
        return DoubleTuple.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public FloatTuple mapToFloat(@Nonnull ToFloatFunction<? super E> f) {
        return FloatTuple.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongTuple mapToLong(@Nonnull ToLongFunction<? super E> f) {
        return LongTuple.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntTuple mapToInt(@Nonnull ToIntFunction<? super E> f) {
        return IntTuple.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, ? extends G> f) throws IllegalArgumentException {
        return new Object0<>();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public E next() {
                return null;
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> a) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super E> a) {}

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public E[] array() {
        return (E[]) new Object[0];
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<E> stream() {
        return Stream.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Tuple<?> t)) return false;
        return t.size() == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return "[]";
    }

    /**
     * Returns the hash code of this instance.
     */
    @Override
    public int hashCode() {
        /*
         * This guarantees that an empty ArrayTuple will have the same hash code
         * as any instance of Object0.
         */
        return Arrays.hashCode(array());
    }
}
