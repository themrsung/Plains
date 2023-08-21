package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A lightweight type-safe array suitable for single-threaded applications.
 * Thread safety is not guaranteed by this implementation.
 *
 * @param <E> The type of element this array should hold
 * @see SafeArray
 * @see SyncArray
 */
public class FastArray<E> implements SafeArray<E>, Iterable<E>, Serializable {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Factory
    //

    /**
     * Creates a new type-safe array from the provided array of elements,
     * then returns the newly created array instance.
     *
     * @param elements The elements this array should contain
     * @param <E>      The type of element the array should hold
     * @return The new array instance created from the provided elements
     */
    @Nonnull
    @SafeVarargs
    public static <E> FastArray<E> of(@Nonnull E... elements) {
        return new FastArray<>(elements);
    }

    //
    // Constructors
    //

    /**
     * Creates a new type-safe array.
     *
     * @param length The length of this array
     */
    @SuppressWarnings("unchecked")
    public FastArray(int length) {
        this.elements = (E[]) new Object[length];
    }

    /**
     * Creates a new type-safe array.
     *
     * @param a The array of which to copy elements from
     */
    public FastArray(@Nonnull SafeArray<? extends E> a) {
        this.elements = a.array();
    }

    /**
     * Creates a new type-safe array.
     *
     * @param elements The elements this array should contain
     */
    @SuppressWarnings("unchecked")
    private FastArray(@Nonnull E... elements) {
        this.elements = (E[]) Arrays.stream(elements).toArray();
    }

    /**
     * Creates a new type-safe array by directly assigning the internal array.
     * This is a dangerous constructor, and thus is hidden as protected.
     *
     * @param elements The array to assign as the elements of this array
     * @param ignored  Ignored
     */
    protected FastArray(@Nonnull E[] elements, boolean ignored) {
        this.elements = elements;
    }

    //
    // Variables
    //

    /**
     * The internal array of elements.
     */
    @Nonnull
    protected final E[] elements;

    //
    // Properties
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public final int length() {
        return elements.length;
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
        for (final E element : elements) {
            if (Objects.equals(element, obj)) return true;
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
        return elements[i];
    }

    /**
     * {@inheritDoc}
     *
     * @param i        The index of the element to get
     * @param fallback The fallback value to return instead of {@code null}
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Nonnull
    @Override
    public E getOrDefault(int i, @Nonnull E fallback) throws IndexOutOfBoundsException {
        final E element = elements[i];
        return element != null ? element : fallback;
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to set
     * @param v The value to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(int i, E v) throws IndexOutOfBoundsException {
        elements[i] = v;
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
        Arrays.fill(elements, v);
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
     * @param i1 The starting index at which to start assigning values from
     * @param i2 The ending index at which to stop assigning values at
     * @param v  The value of which to assign to every slot within the specified range
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void fillRange(int i1, int i2, E v) throws IndexOutOfBoundsException {
        for (int i = i1; i < i2; i++) {
            elements[i] = v;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void apply(@Nonnull UnaryOperator<E> f) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = f.apply(elements[i]);
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
        apply(v -> Objects.equals(v, oldValue) ? newValue : v);
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceFirst(E oldValue, E newValue) {
        for (int i = 0; i < elements.length; i++) {
            if (Objects.equals(elements[i], oldValue)) {
                elements[i] = newValue;
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
    public void replaceLast(E oldValue, E newValue) {
        for (int i = (elements.length - 1); i >= 0; i--) {
            if (Objects.equals(elements[i], oldValue)) {
                elements[i] = newValue;
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
    public SafeArray<E> subArray(int i1, int i2) throws IndexOutOfBoundsException {
        final FastArray<E> result = new FastArray<>(i2 - i1);
        System.arraycopy(elements, i1, result.elements, 0, i2 - i1);
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
    public void setRange(int i1, int i2, @Nonnull SafeArray<? extends E> a) throws IndexOutOfBoundsException {
        for (int i = i1; i < i2; i++) {
            elements[i] = a.get(i - i1);
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
        final FastArray<E> result = new FastArray<>(size);
        System.arraycopy(elements, 0, result.elements, 0, Math.min(elements.length, size));
        return result;
    }

    //
    // Sorting
    //

    /**
     * {@inheritDoc}
     */
    @Override
    public void sort() {
        try {
            Arrays.sort(elements);
        } catch (final ClassCastException e) {
            throw new UnsupportedOperationException("Cannot perform sort operations on non-comparable objects.", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The comparator function to sort this array with
     */
    @Override
    public void sort(@Nonnull Comparator<? super E> f) {
        Arrays.sort(elements, f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffle() {
        final int n = elements.length;
        final Random random = new Random();

        for (int i = n - 1; i > 0; i--) {
            final int j = random.nextInt(i + 1);

            // Swap elements at i and j
            final E temp = elements[i];

            elements[i] = elements[j];
            elements[j] = temp;
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
    public SafeArray<E> filter(@Nonnull Predicate<? super E> f) {
        return new FastArray<>((E[]) Arrays.stream(elements).filter(f).toArray(), false);
    }

    //
    // Mapping
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
    public <F> SafeArray<F> map(@Nonnull Function<? super E, F> f) {
        return new FastArray<>((F[]) Arrays.stream(elements).map(f).toArray(), false);
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
    public <F> Collection<F> mapToCollection(@Nonnull Function<? super E, F> f) {
        return Arrays.stream(elements).map(f).collect(Collectors.toList());
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
    public <F> List<F> mapToList(@Nonnull Function<? super E, F> f) {
        return Arrays.stream(elements).map(f).toList();
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
    public <F> Set<F> mapToSet(@Nonnull Function<? super E, F> f) {
        return Arrays.stream(elements).map(f).collect(Collectors.toSet());
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
    public <F, G> SafeArray<G> merge(@Nonnull SafeArray<F> a, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {

        if (elements.length != a.length()) {
            throw new IllegalArgumentException("Array lengths must match for this operation.");
        }

        final FastArray<G> result = new FastArray<>(elements.length);

        for (int i = 0; i < elements.length; i++) {
            result.elements[i] = f.apply(elements[i], a.get(i));
        }

        return result;
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
        return (E[]) Arrays.stream(elements).toArray();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<E> stream() {
        return Arrays.stream(elements);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<E> collect() {
        return List.copyOf(Arrays.asList(elements));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Set<E> set() {
        return Set.copyOf(Arrays.asList(elements));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return List.copyOf(Arrays.asList(elements));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayList<E> arrayList() {
        return new ArrayList<>(Arrays.asList(elements));
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<E> tuple() {
        return Tuple.of(elements);
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
        return Arrays.stream(elements).iterator();
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
    public SafeArray<E> copy() {
        return new FastArray<>(array(), false);
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
        return Arrays.equals(elements, a.array());
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
        return Arrays.toString(elements);
    }
}
