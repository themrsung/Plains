package civitas.celestis.util.array;

import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A type-safe array whose elements' types are bound to the raw primitive {@code int}.
 * Elements are stored in their primitive form, reducing the overhead of boxed {@link Integer}s.
 * Integer-typed arrays are designed with speed as their first priority, and are not thread-safe.
 *
 * @see SafeArray
 */
public class IntArray implements SafeArray<Integer> {
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
     * Creates a new int-typed array from the provided array of elements,
     * then returns the newly created array instance.
     *
     * @param elements The elements this array should contain
     * @return The new array instance created from the provided elements
     */
    @Nonnull
    public static IntArray of(@Nonnull int... elements) {
        return new IntArray(elements);
    }

    //
    // Constructors
    //

    /**
     * Creates a new int array.
     *
     * @param length The length of this array
     */
    public IntArray(int length) {
        this.elements = new int[length];
    }

    /**
     * Creates a new int array.
     *
     * @param a The array of which to copy elements from
     */
    public IntArray(@Nonnull SafeArray<? extends Number> a) {
        this.elements = a.stream().mapToInt(Number::intValue).toArray();
    }

    /**
     * Creates a new int array.
     *
     * @param elements The elements this array should contain
     */
    private IntArray(@Nonnull int... elements) {
        this.elements = Arrays.stream(elements).toArray();
    }

    /**
     * Creates a new int array by directly assigning the internal array.
     * This is a dangerous constructor, and thus is hidden as protected.
     *
     * @param elements The array to assign as the elements of this array
     * @param ignored  Ignored
     */
    protected IntArray(@Nonnull int[] elements, boolean ignored) {
        this.elements = elements;
    }

    //
    // Variables
    //

    /**
     * The internal array of elements.
     */
    protected final int[] elements;

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
        for (final int element : elements) {
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
    public Integer get(int i) throws IndexOutOfBoundsException {
        return elements[i];
    }

    /**
     * As primitive int-typed arrays cannot contain {@code null} values, this method
     * has the same effect as calling {@link #get(int)}.
     *
     * @param i       The index of the element to get
     * @param ignored Ignored
     * @return The {@code i}th element of this array
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Integer getOrDefault(int i, Integer ignored) throws IndexOutOfBoundsException {
        return elements[i];
    }

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to set
     * @param v The value to set to
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void set(int i, Integer v) throws IndexOutOfBoundsException {
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
    public void fill(Integer v) {
        Arrays.fill(elements, v);
    }

    /**
     * As primitive int-typed arrays cannot contain {@code null} values,
     * this method has the same effect as calling {@link #fill(Integer)}.
     *
     * @param v The value to fill this array with
     */
    @Override
    public void fillEmpty(Integer v) {
        Arrays.fill(elements, v);
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
    public void fillRange(int i1, int i2, Integer v) throws IndexOutOfBoundsException {
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
    public void apply(@Nonnull Function<? super Integer, Integer> f) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = f.apply(elements[i]);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param f The function of which to apply to each element of this array
     */
    @Override
    public void apply(@Nonnull BiFunction<Integer, ? super Integer, Integer> f) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = f.apply(i, elements[i]);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceAll(Integer oldValue, Integer newValue) {
        apply(v -> Objects.equals(v, oldValue) ? newValue : v);
    }

    /**
     * {@inheritDoc}
     *
     * @param oldValue The old value to replace
     * @param newValue The new value to replace to
     */
    @Override
    public void replaceFirst(Integer oldValue, Integer newValue) {
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
    public void replaceLast(Integer oldValue, Integer newValue) {
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
    public SafeArray<Integer> subArray(int i1, int i2) throws IndexOutOfBoundsException {
        final IntArray result = new IntArray(i2 - i1);
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
    public void setRange(int i1, int i2, @Nonnull SafeArray<? extends Integer> a) throws IndexOutOfBoundsException {
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
    public SafeArray<Integer> resize(int size) {
        final IntArray result = new IntArray(size);
        System.arraycopy(elements, 0, result.elements, 0, Math.min(elements.length, size));
        return result;
    }

    //
    // Sorting
    //

    /**
     * Sorts this array by the elements' values ascending. (lower values come first)
     */
    @Override
    public void sort() {
        Arrays.sort(elements);
    }

    /**
     * {@inheritDoc}
     *
     * @param f The comparator function to sort this array with
     */
    @Override
    public void sort(@Nonnull Comparator<? super Integer> f) {
        final int[] sortedArray = Arrays.stream(elements)
                .boxed()
                .sorted(f)
                .mapToInt(Integer::intValue)
                .toArray();

        // Update the elements array with the sorted values
        System.arraycopy(sortedArray, 0, elements, 0, sortedArray.length);
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
            final int temp = elements[i];

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
    public SafeArray<Integer> filter(@Nonnull Predicate<? super Integer> f) {
        return new IntArray(Arrays.stream(elements).filter(f::test).toArray(), false);
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
    public <F> SafeArray<F> map(@Nonnull Function<? super Integer, ? extends F> f) {
        return new FastArray<>((F[]) Arrays.stream(elements).boxed().map(f).toArray(), false);
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
    public <F> Collection<F> mapToCollection(@Nonnull Function<? super Integer, ? extends F> f) {
        return Arrays.stream(elements).boxed().map(f).collect(Collectors.toList());
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
    public <F> List<F> mapToList(@Nonnull Function<? super Integer, ? extends F> f) {
        return Arrays.stream(elements).boxed().map(f).collect(Collectors.toList());
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
    public <F> Set<F> mapToSet(@Nonnull Function<? super Integer, ? extends F> f) {
        return Arrays.stream(elements).boxed().map(f).collect(Collectors.toSet());
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
    public <F, G> SafeArray<G> merge(@Nonnull SafeArray<F> a, @Nonnull BiFunction<? super Integer, ? super F, G> f)
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


    /**
     * Explicitly casts each element of this array to the provided class {@code c}, then returns
     * the resulting array. The provided class {@code c} must be a superclass of this array in order
     * for this operation to succeed.
     * <p>
     * Unlike {@link FastArray}, a shallow copy is performed in the process, and changes in the
     * new array will not be reflected to this array.
     * </p>
     *
     * @param c   The class of which to cast the elements of this array to
     * @param <F> {@inheritDoc}
     * @return {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     */
    @Nonnull
    @Override
    public <F> SafeArray<F> cast(@Nonnull Class<F> c) throws ClassCastException {
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
    public Integer[] array() {
        return Arrays.stream(elements).boxed().toArray(Integer[]::new);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Stream<Integer> stream() {
        return Arrays.stream(elements).boxed();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Collection<Integer> collect() {
        return Arrays.stream(elements).boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Set<Integer> set() {
        return Arrays.stream(elements).boxed().collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<Integer> list() {
        return Arrays.stream(elements).boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public ArrayList<Integer> arrayList() {
        return new ArrayList<>(Arrays.stream(elements).boxed().toList());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Tuple<Integer> tuple() {
        return Tuple.ofInt(elements);
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
    public Iterator<Integer> iterator() {
        return Arrays.stream(elements).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this array
     */
    @Override
    public void forEach(@Nonnull Consumer<? super Integer> action) {
        for (final int element : elements) {
            action.accept(element);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this array
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Integer, ? super Integer> action) {
        for (int i = 0; i < elements.length; i++) {
            action.accept(i, elements[i]);
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
    public SafeArray<Integer> copy() {
        return new IntArray(elements);
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
        if (elements.length != a.length()) return false;

        final Object[] array = a.array();
        for (int i = 0; i < elements.length; i++) {
            if (!Objects.equals(elements[i], array[i])) return false;
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
        return Arrays.toString(elements);
    }
}
