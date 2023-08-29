package civitas.celestis.util.array;

import civitas.celestis.util.function.ToFloatFunction;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A type-safe array which stores atomic references as opposed to the values themselves.
 * Atomic references are thread-safe as long as the underlying elements are not concurrently
 * modifiable, or have other thread-unsafe properties.
 *
 * @param <E> The type of element to reference
 * @see SafeArray
 */
public class AtomicArray<E> implements SafeArray<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Static Initializers
    //

    /**
     * Creates a new atomic array from the provided array of values.
     *
     * @param values The values of which to reference
     * @param <E>    The type of element to reference
     * @return A new atomic array referencing the provided values
     */
    @Nonnull
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <E> AtomicArray<E> of(@Nonnull E... values) {
        final AtomicReference<E>[] referenced = (AtomicReference<E>[]) Arrays.stream(values)
                .map(AtomicReference::new)
                .toArray(AtomicReference[]::new);

        return new AtomicArray<>(referenced);
    }

    //
    // Constructors
    //

    /**
     * Creates a new atomic array.
     *
     * @param length The length of which to initialize this array to
     */
    @SuppressWarnings("unchecked")
    public AtomicArray(int length) {
        this.references = (AtomicReference<E>[]) Array.newInstance(AtomicReference.class, length);

        // Initialize reference objects
        for (int i = 0; i < references.length; i++) {
            references[i] = new AtomicReference<>();
        }
    }

    /**
     * Creates a new atomic array.
     *
     * @param a The array of which to copy values from
     */
    public AtomicArray(@Nonnull SafeArray<? extends E> a) {
        this(a.length());
        setRange(0, length(), a);
    }

    /**
     * Creates a new atomic array. This is a dangerous constructor,
     * and should only be used internally.
     *
     * @param references The array of references to directly assign
     */
    private AtomicArray(@Nonnull AtomicReference<E>[] references) {
        this.references = references;
    }

    //
    // Variables
    //

    /**
     * The internal array of references.
     */
    @Nonnull
    private final AtomicReference<E>[] references;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public int length() {
        return references.length;
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
        for (final AtomicReference<E> reference : references) {
            if (Objects.equals(reference.get(), obj)) return true;
        }

        return false;
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
    // Accessors
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
        return references[i].get();
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to get
     * @param e The fallback value to default to when the value is {@code null}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E getOrDefault(int i, E e) throws IndexOutOfBoundsException {
        final E value = references[i].get();
        return value != null ? value : e;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to set
     * @param e The element of which to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(int i, E e) throws IndexOutOfBoundsException {
        references[i].set(e);
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to update
     * @param f The update function of which to apply to the element
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void update(int i, @Nonnull UnaryOperator<E> f) throws IndexOutOfBoundsException {
        references[i].getAndUpdate(f);
    }


    //
    // Bulk Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill this array with
     */
    @Override
    public void fill(E v) {
        for (final AtomicReference<E> reference : references) {
            reference.set(v);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param v The value to fill empty slots of this array with
     */
    @Override
    public void fillEmpty(E v) {
        replaceAll(null, v);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The starting index at which to start assigning values from
     * @param e The ending index at which to stop assigning values at
     * @param v The value of which to assign to every slot within the specified range
     */
    @Override
    public void fillRange(int s, int e, E v) {
        for (int i = s; i < e; i++) {
            references[i].set(v);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull UnaryOperator<E> f) {
        for (final AtomicReference<E> reference : references) {
            reference.getAndUpdate(f);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void update(@Nonnull BiFunction<? super Integer, ? super E, E> f) {
        for (int i = 0; i < references.length; i++) {
            final int index = i;
            references[i].getAndUpdate(v -> f.apply(index, v));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceAll(E oldValue, E newValue) {
        for (final AtomicReference<E> reference : references) {
            if (!Objects.equals(reference.get(), oldValue)) continue;
            reference.set(newValue);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceFirst(E oldValue, E newValue) {
        for (final AtomicReference<E> reference : references) {
            if (!Objects.equals(reference.get(), oldValue)) continue;
            reference.set(newValue);
            return;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceLast(E oldValue, E newValue) {
        for (int i = (references.length - 1); i >= 0; i--) {
            if (!Objects.equals(references[i].get(), oldValue)) continue;
            references[i].set(newValue);
            return;
        }
    }

    //
    // Sub Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param s The starting index at which to start creating the sub-array (inclusive)
     * @param e The ending index at which to stop creating the sub-array (exclusive)
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<E> subArray(int s, int e) throws IndexOutOfBoundsException {
        return new AtomicSubArray<>(references, s, e);
    }

    /**
     * {@inheritDoc}
     *
     * @param s The starting index at which to start copying values from (inclusive)
     * @param e The ending index at which to stop copying values from (exclusive)
     * @param a The sub-array containing the values to assign to this array
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void setRange(int s, int e, @Nonnull SafeArray<? extends E> a) throws IndexOutOfBoundsException {
        for (int i = s; i < e; i++) {
            references[i].set(a.get(i - s));
        }
    }

    //
    // Resizing
    //

    /**
     * {@inheritDoc}
     *
     * @param size The size to resize this array to
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public SafeArray<E> resize(int size) {
        final SyncArray<E> result = new SyncArray<>(size);
        final int minLength = Math.min(references.length, size);

        for (int i = 0; i < minLength; i++) {
            result.values[i] = references[i].get();
        }

        return result;
    }

    //
    // Ordering
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffle() {
        final int n = references.length;
        final Random random = new Random();

        for (int i = n - 1; i > 0; i--) {
            final int j = random.nextInt(i + 1);

            // Swap elements at i and j
            final E temp = references[i].get();

            references[i].set(references[j].get());
            references[j].set(temp);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sort() throws UnsupportedOperationException {
        try {
            Arrays.sort(references, (ref1, ref2) -> {
                final Comparable<E> value1 = (Comparable<E>) ref1.get();
                final E value2 = ref2.get();

                if (value1 == null && value2 == null) {
                    return 0;
                } else if (value1 == null) {
                    return -1;
                } else if (value2 == null) {
                    return 1;
                }

                return value1.compareTo(value2);
            });
        } catch (final ClassCastException e) {
            throw new UnsupportedOperationException("Non-comparable objects cannot be naturally sorted.", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param c The comparator function of which to sort this array with
     */
    @Override
    public void sort(@Nonnull Comparator<? super E> c) {
        Arrays.sort(references, (ref1, ref2) -> {
            final E value1 = ref1.get();
            final E value2 = ref2.get();

            if (value1 == null && value2 == null) {
                return 0;
            } else if (value1 == null) {
                return -1;
            } else if (value2 == null) {
                return 1;
            }

            return c.compare(value1, value2);
        });
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public <F> SafeArray<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return SyncArray.of((F[]) stream().map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public DoubleArray mapToDouble(@Nonnull ToDoubleFunction<? super E> f) {
        return new DoubleFastArray(stream().mapToDouble(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public FloatArray mapToFloat(@Nonnull ToFloatFunction<? super E> f) {
        return new FloatFastArray(stream().map(f::applyAsFloat).toArray(Float[]::new));
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public LongArray mapToLong(@Nonnull ToLongFunction<? super E> f) {
        return new LongFastArray(stream().mapToLong(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public IntArray mapToInt(@Nonnull ToIntFunction<? super E> f) {
        return new IntFastArray(stream().mapToInt(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param a   The array of which to merge this array with
     * @param f   The merger function to handle the merging of the two arrays
     * @param <F> {@inheritDoc}
     * @param <G> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F, G> SafeArray<G> merge(@Nonnull SafeArray<F> a, @Nonnull BiFunction<? super E, ? super F, ? extends G> f)
            throws IllegalArgumentException {
        if (references.length != a.length()) {
            throw new IllegalArgumentException("Array lengths must match for this operation.");
        }

        final SyncArray<G> result = new SyncArray<>(references.length);

        for (int i = 0; i < references.length; i++) {
            result.values[i] = f.apply(references[i].get(), a.get(i));
        }

        return result;
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
        return stream().iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> a) {
        for (final AtomicReference<E> reference : references) {
            a.accept(reference.get());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param a The action to be performed for each element
     */
    @Override
    public void forEach(@Nonnull BiConsumer<? super Integer, ? super E> a) {
        for (int i = 0; i < references.length; i++) {
            a.accept(i, references[i].get());
        }
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
        return (E[]) Arrays.stream(references).map(AtomicReference::get).toArray();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<E> stream() {
        return Arrays.stream(references).map(AtomicReference::get);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return stream().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<E> tuple() {
        return Tuple.of(array());
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
        if (!(obj instanceof SafeArray<?> a)) return false;
        if (references.length != a.length()) return false;
        for (int i = 0; i < references.length; i++) {
            if (!Objects.equals(references[i].get(), a.get(i))) return false;
        }

        return true;
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
        return Arrays.toString(array());
    }

    /**
     * Returns the hash code of this instance.
     *
     * @return The hash code of this instance
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(array());
    }
}
