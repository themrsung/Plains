package civitas.celestis.util.array;

import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An array which stores atomic references instead of the elements themselves.
 * Since the reference objects are not externally accessible, atomic arrays can
 * be considered thread-safe as long as the underlying elements themselves
 * do not have thread-unsafe properties such as being modifiable concurrently.
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
     * Creates a new atomic array containing the provided elements.
     *
     * @param elements The elements to contain in the array
     * @param <E>      The type of element to reference
     * @return A new atomic array instance containing the provided elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> AtomicArray<E> of(@Nonnull E... elements) {
        return new AtomicArray<>(elements);
    }

    //
    // Constructors
    //

    /**
     * Creates a new atomic array.
     *
     * @param length The length of this array
     */
    @SuppressWarnings("unchecked")
    public AtomicArray(int length) {
        this.references = (AtomicReference<E>[]) Array.newInstance(AtomicReference.class, length);

        // Populate the array with new atomic reference objects
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
        a.forEach((i, v) -> references[i].set(v));
    }

    /**
     * Creates a new atomic array.
     *
     * @param elements The elements this array should contain
     */
    @SafeVarargs
    protected AtomicArray(@Nonnull E... elements) {
        this(elements.length);
        for (int i = 0; i < elements.length; i++) {
            references[i].set(elements[i]);
        }
    }

    /**
     * Direct assignment constructor. Do not make this publicly available.
     *
     * @param references The array to directly assign
     * @param ignored    Ignored
     */
    protected AtomicArray(@Nonnull AtomicReference<E>[] references, boolean ignored) {
        this.references = references;
    }

    //
    // Variables
    //

    /**
     * The internal array of references.
     */
    protected final AtomicReference<E>[] references;

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
    public synchronized boolean contains(@Nullable Object obj) {
        for (final AtomicReference<E> reference : references) {
            if (Objects.equals(reference.get(), obj)) return true;
        }

        return false;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The iterable object containing the values to check for containment
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean containsAll(@Nonnull Iterable<?> i) {
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
    public synchronized E get(int i) throws IndexOutOfBoundsException {
        return references[i].get();
    }

    /**
     * {@inheritDoc}
     *
     * @param i        The index of the element to get
     * @param fallback The fallback value to return instead of {@code null}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized E getOrDefault(int i, E fallback) throws IndexOutOfBoundsException {
        final E value = references[i].get();
        return value != null ? value : fallback;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to set
     * @param v The value to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void set(int i, E v) throws IndexOutOfBoundsException {
        references[i].set(v);
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
    public synchronized void fill(E v) {
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
    public synchronized void fillEmpty(E v) {
        for (final AtomicReference<E> reference : references) {
            reference.getAndUpdate(e -> e != null ? v : e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The starting index at which to start assigning values from
     * @param i2 The ending index at which to stop assigning values at
     * @param v  The value of which to assign to every slot within the specified range
     * @throws IndexOutOfBoundsException
     */
    @Override
    public synchronized void fillRange(int i1, int i2, E v) throws IndexOutOfBoundsException {
        for (int i = i1; i < i2; i++) {
            references[i].set(v);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public synchronized void apply(@Nonnull Function<? super E, E> f) {
        for (final AtomicReference<E> reference : references) {
            reference.getAndUpdate(f::apply);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public synchronized void apply(@Nonnull BiFunction<Integer, ? super E, E> f) {
        for (int i = 0; i < references.length; i++) {
            final int j = i;
            references[i].getAndUpdate(e -> f.apply(j, e));
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public synchronized void replaceAll(E oldValue, E newValue) {
        for (final AtomicReference<E> reference : references) {
            reference.getAndUpdate(e -> Objects.equals(e, oldValue) ? newValue : oldValue);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public synchronized void replaceFirst(E oldValue, E newValue) {
        for (AtomicReference<E> reference : references) {
            if (Objects.equals(reference.get(), oldValue)) {
                reference.set(newValue);
                return;
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public synchronized void replaceLast(E oldValue, E newValue) {
        for (int i = (references.length - 1); i >= 0; i--) {
            if (Objects.equals(references[i].get(), oldValue)) {
                references[i].set(newValue);
                return;
            }
        }
    }

    //
    // Sub Operation
    //

    /**
     * {@inheritDoc}
     *
     * @param i1 The starting index at which to start copying elements from
     * @param i2 The ending index at which to stop copying elements at
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized SafeArray<E> subArray(int i1, int i2) throws IndexOutOfBoundsException {
        final FastArray<E> result = new FastArray<>(i2 - i1);
        for (int i = i1; i < i2; i++) {
            result.elements[i - i1] = references[i].get();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param i1 The starting index at which to start assigning elements from
     * @param i2 The ending index at which to stop assigning elements at
     * @param a  The sub-array of which to copy elements from
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public synchronized void setRange(int i1, int i2, @Nonnull SafeArray<? extends E> a)
            throws IndexOutOfBoundsException {
        for (int i = i1; i < i2; i++) {
            references[i].set(a.get(i));
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
    public synchronized SafeArray<E> resize(int size) {
        final FastArray<E> result = new FastArray<>(size);
        final int min = Math.min(references.length, size);

        for (int i = 0; i < min; i++) {
            result.elements[i] = references[i].get();
        }

        return result;
    }

    //
    // Sorting
    //

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized void sort() {
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
    }

    /**
     * {@inheritDoc}
     *
     * @param f The comparator function to sort this array with
     */
    @Override
    public synchronized void sort(@Nonnull Comparator<? super E> f) {
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

            return f.compare(value1, value2);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void shuffle() {
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

    //
    // Filtration
    //

    /**
     * {@inheritDoc}
     *
     * @param f The filter function to handle the filtration of this array
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized SafeArray<E> filter(@Nonnull Predicate<? super E> f) {
        return FastArray.of((E[]) Arrays.stream(references).map(AtomicReference::get).toArray());
    }

    //
    // Transformation
    //

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized <F> SafeArray<F> map(@Nonnull Function<? super E, ? extends F> f) {
        return FastArray.of((F[]) Arrays.stream(references).map(AtomicReference::get).map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized <F> Tuple<F> mapToTuple(@Nonnull Function<? super E, ? extends F> f) {
        return Tuple.of((F[]) Arrays.stream(references).map(AtomicReference::get).map(f).toArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> Collection<F> mapToCollection(@Nonnull Function<? super E, ? extends F> f) {
        return Arrays.stream(references).map(AtomicReference::get).map(f).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> List<F> mapToList(@Nonnull Function<? super E, ? extends F> f) {
        return Arrays.stream(references).map(AtomicReference::get).map(f).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     *
     * @param f   The mapper function of which to apply to each element of this array
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> Set<F> mapToSet(@Nonnull Function<? super E, ? extends F> f) {
        return Arrays.stream(references).map(AtomicReference::get).map(f).collect(Collectors.toSet());
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
    public synchronized <F, G> SafeArray<G> merge(@Nonnull SafeArray<F> a, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        if (references.length != a.length()) {
            throw new IllegalArgumentException("Array lengths must match for this operation.");
        }

        final FastArray<G> result = new FastArray<>(references.length);

        for (int i = 0; i < references.length; i++) {
            result.elements[i] = f.apply(references[i].get(), a.get(i));
        }

        return result;
    }

    /**
     * Explicitly casts each element of this array to the provided class c, then returns the resulting array.
     * The provided class c must be a superclass of this array in order for this operation to succeed.
     *
     * @param c   The class of which to cast the elements of this array to
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized <F> SafeArray<F> cast(@Nonnull Class<F> c) throws ClassCastException {
        return map(c::cast);
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
    public synchronized E[] array() {
        return (E[]) Arrays.stream(references).map(AtomicReference::get).toArray();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Stream<E> stream() {
        return Arrays.stream(references).map(AtomicReference::get);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Collection<E> collect() {
        return Arrays.stream(references).map(AtomicReference::get).toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized Set<E> set() {
        return Arrays.stream(references).map(AtomicReference::get).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized List<E> list() {
        return Arrays.stream(references).map(AtomicReference::get).toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public synchronized ArrayList<E> arrayList() {
        return new ArrayList<>(Arrays.stream(references).map(AtomicReference::get).toList());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized Tuple<E> tuple() {
        return Tuple.of((E[]) Arrays.stream(references).map(AtomicReference::get).toArray());
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
    public synchronized Iterator<E> iterator() {
        return Arrays.stream(references).map(AtomicReference::get).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this array
     */
    @Override
    public synchronized void forEach(@Nonnull Consumer<? super E> action) {
        for (final AtomicReference<E> reference : references) {
            action.accept(reference.get());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this array
     */
    @Override
    public synchronized void forEach(@Nonnull BiConsumer<Integer, ? super E> action) {
        for (int i = 0; i < references.length; i++) {
            action.accept(i, references[i].get());
        }
    }

    //
    // Copying
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public synchronized SafeArray<E> copy() {
        // Dereferences the array and creates a non-atomic copy
        return SafeArray.of((E[]) Arrays.stream(references).map(AtomicReference::get).toArray());
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
    public synchronized boolean equals(@Nullable Object obj) {
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
    public synchronized String toString() {
        return Arrays.toString(Arrays.stream(references).map(AtomicReference::get).toArray());
    }
}
