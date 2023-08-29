package civitas.celestis.util.tuple;

import civitas.celestis.exception.tuple.TupleIndexOutOfBoundsException;
import civitas.celestis.util.function.ToFloatFunction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
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
    @Serial
    private static final long serialVersionUID = 0L;

    Object0() {}

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(@Nullable Object obj) {
        return false;
    }

    @Override
    public boolean containsAll(@Nonnull Iterable<?> i) {
        return false;
    }

    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        throw new TupleIndexOutOfBoundsException(i);
    }

    @Nonnull
    @Override
    public <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return new Object0<>();
    }

    @Nonnull
    @Override
    public DoubleTuple mapToDouble(@Nonnull ToDoubleFunction<? super E> f) {
        return DoubleTuple.empty();
    }

    @Nonnull
    @Override
    public FloatTuple mapToFloat(@Nonnull ToFloatFunction<? super E> f) {
        return FloatTuple.empty();
    }

    @Nonnull
    @Override
    public LongTuple mapToLong(@Nonnull ToLongFunction<? super E> f) {
        return LongTuple.empty();
    }

    @Nonnull
    @Override
    public IntTuple mapToInt(@Nonnull ToIntFunction<? super E> f) {
        return IntTuple.empty();
    }

    @Nonnull
    @Override
    public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, ? extends G> f) throws IllegalArgumentException {
        return new Object0<>();
    }

    @Nonnull
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
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

    @Override
    public void forEach(@Nonnull Consumer<? super E> a) {}

    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super E> a) {}

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public E[] array() {
        return (E[]) new Object[0];
    }

    @Nonnull
    @Override
    public Stream<E> stream() {
        return Stream.empty();
    }

    @Nonnull
    @Override
    public List<E> list() {
        return List.of();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Tuple<?> t)) return false;
        return t.size() == 0;
    }

    @Nonnull
    @Override
    public String toString() {
        return "[]";
    }
}
