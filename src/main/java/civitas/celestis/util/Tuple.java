package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * An immutable set of elements. Tuples have a fixed size, and are shallowly immutable,
 * meaning that the component variables cannot be reassigned after instantiation.
 *
 * @param <E> The type of element this tuple should hold
 * @see Pair
 * @see Triple
 * @see Quad
 * @see ArrayTuple
 * @see IntPair
 * @see IntTriple
 * @see IntQuad
 */
public interface Tuple<E> extends Iterable<E>, Serializable {
    //
    // Factory
    //

    /**
     * Given an array of elements, this creates a new tuple instance containing
     * the provided array of elements.
     *
     * @param elements The array of elements to contain in the tuple
     * @param <E>      The type of element to contain in the tuple
     * @return The constructed tuple
     */
    @Nonnull
    @SafeVarargs
    static <E> Tuple<E> of(@Nonnull E... elements) {
        return switch (elements.length) {
            case 0 -> new Empty<>();
            case 1 -> new Single<>(elements[0]);
            case 2 -> new Pair<>(elements);
            case 3 -> new Triple<>(elements);
            case 4 -> new Quad<>(elements);
            default -> new ArrayTuple<>(elements);
        };
    }

    /**
     * Creates a new tuple from a primitive array of {@code double}s.
     *
     * @param elements The array of elements to contain in the tuple
     * @return The constructed tuple
     */
    @Nonnull
    static Tuple<Double> ofDouble(@Nonnull double... elements) {
        return switch (elements.length) {
            case 0 -> new Empty<>();
            case 1 -> new Single<>(elements[0]);
            default -> of(Arrays.stream(elements).boxed().toArray(Double[]::new));
        };
    }

    /**
     * Creates a new tuple from a primitive array of {@code float}s.
     *
     * @param elements The array of elements to contain in the tuple
     * @return The constructed tuple
     */
    @Nonnull
    static Tuple<Float> ofFloat(@Nonnull float... elements) {
        return switch (elements.length) {
            case 0 -> new Empty<>();
            case 1 -> new Single<>(elements[0]);
            default -> {
                final Float[] boxed = new Float[elements.length];
                for (int i = 0; i < elements.length; i++) boxed[i] = elements[i];
                yield of(boxed);
            }
        };
    }

    /**
     * Creates a new tuple from a primitive array of {@code long}s.
     *
     * @param elements The array of elements to contain in the tuple
     * @return The constructed tuple
     */
    @Nonnull
    static Tuple<Long> ofLong(@Nonnull long... elements) {
        return switch (elements.length) {
            case 0 -> new Empty<>();
            case 1 -> new Single<>(elements[0]);
            default -> of(Arrays.stream(elements).boxed().toArray(Long[]::new));
        };
    }

    /**
     * Creates a new tuple from a primitive array of {@code int}s.
     *
     * @param elements The array of elements to contain in the tuple
     * @return The constructed tuple
     */
    @Nonnull
    static Tuple<Integer> ofInt(@Nonnull int... elements) {
        return switch (elements.length) {
            case 0 -> new Empty<>();
            case 1 -> new Single<>(elements[0]);
            case 2 -> new IntPair(elements);
            case 3 -> new IntTriple(elements);
            case 4 -> new IntQuad(elements);
            default -> of(Arrays.stream(elements).boxed().toArray(Integer[]::new));
        };
    }

    /**
     * Creates a new tuple from a collection of elements.
     *
     * @param c   The collection of which to copy elements from
     * @param <E> The type of element to copy
     * @return The constructed tuple
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    static <E> Tuple<E> copyOf(@Nonnull Collection<E> c) {
        return of((E[]) c.toArray());
    }

    //
    // Properties
    //

    /**
     * Returns the number of elements this tuple contains.
     *
     * @return The number of elements this tuple contains
     */
    int size();

    //
    // Retrieval
    //

    /**
     * Returns the {@code i}th element of this tuple. If there is no
     * element present at the {@code i}th position of this tuple,
     * this will return {@code null}.
     *
     * @param i The index of the element to retrieve
     * @return The element at the specified position if there is one,
     * {@code null} if not
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    E get(int i) throws IndexOutOfBoundsException;

    //
    // Containment
    //

    /**
     * Checks if this tuple contains the provided object {@code obj}.
     *
     * @param obj The object of which to check for containment
     * @return {@code true} if at least one of this tuple's elements are
     * equal to the provided object {@code obj}
     */
    boolean contains(@Nullable Object obj);

    /**
     * Checks if this tuple contains multiple elements, packaged in the form of an iterable object.
     *
     * @param i The iterable object of which to check for containment
     * @return {@code true} if this tuple contains every element of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<?> i);

    //
    // Sub-operation
    //

    /**
     * Given two indices {@code i1} and {@code i2}, this returns a sub-tuple of
     * this tuple from the first index to the second index. The resulting tuple will have
     * a size of {@code i2 - i1}.
     *
     * @param i1 The starting index of the sub-tuple to get
     * @param i2 The ending index of the sub-tuple to get
     * @return The sub-tuple of the specified range
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    default Tuple<E> subTuple(int i1, int i2) throws IndexOutOfBoundsException {
        final E[] subArray = (E[]) new Object[i2 - i1];

        for (int i = i1; i < i2; i++) {
            subArray[i - i1] = get(i);
        }

        return Tuple.of(subArray);
    }

    //
    // Filtration
    //

    /**
     * Tests each element of this tuple with the provided filter function {@code f},
     * collects each element which the filter function has returned {@code true} to,
     * then returns a new tuple containing only the filtered elements of this tuple.
     *
     * @param f The filter function to handle the filtration of this tuple
     * @return The resulting tuple
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    default Tuple<E> filter(@Nonnull Predicate<? super E> f) {
        return of((E[]) array().stream().filter(f).toArray());
    }

    //
    // Transformation
    //

    /**
     * Applies the provided mapper function {@code f} to each element of this tuple,
     * then returns a new tuple containing the resulting elements.
     *
     * @param f   The function of which to apply to each element of this tuple
     * @param <F> The type of element to map this tuple to
     * @return The resulting tuple
     */
    @Nonnull
    <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f);

    /**
     * Between this tuple and the provided tuple {@code t}, this applies the merger function
     * {@code f} to each corresponding pair of elements, then returns a new tuple containing the
     * resulting elements.
     *
     * @param t   The tuple of which to merge this tuple with
     * @param f   The merger function to handle the merging of the two tuples
     * @param <F> The type of element to merge this tuple with
     * @param <G> The type of element to merge the two tuples to
     * @throws IllegalArgumentException When the provided tuple's size is
     *                                  not equal to this tuple's size
     */
    @Nonnull
    <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException;

    //
    // Iteration
    //

    /**
     * Returns an iterator of every element within this tuple, it its correct order.
     *
     * @return An iterator of every element within this tuple
     */
    @Override
    Iterator<E> iterator();

    //
    // Conversion
    //

    /**
     * Returns an array representing the elements of this tuple.
     *
     * @return The array representation of this tuple
     */
    @Nonnull
    SafeArray<E> array();

    /**
     * Converts this tuple into an unmodifiable list, then returns the converted list.
     *
     * @return An unmodifiable list representing the elements of this tuple
     * @see List
     */
    @Nonnull
    List<E> list();

    //
    // Equality
    //

    /**
     * Checks for equality between this tuple and the provided object {@code obj}.
     *
     * @param obj The object to compare to
     * @return {@code true} if the provided object is also a tuple,
     * and the size, composition and order of elements are all equal
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
    @Override
    @Nonnull
    String toString();

    //
    // Specialized Tuples
    //

    /**
     * An empty tuple. This is only used as a placeholder instead of {@code null} values.
     *
     * @param <E> The type of element to (not) hold
     */
    final class Empty<E> implements Tuple<E> {
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
         * Private constructor to prevent the external usage of this class.
         */
        private Empty() {
        }

        //
        // Serialization
        //

        /**
         * {@inheritDoc}
         */
        @Override
        @Nonnull
        public String toString() {
            return "[]";
        }

        //
        // Methods
        //

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
        public E get(int i) throws IndexOutOfBoundsException {
            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 0.");
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
        @Nonnull
        @Override
        public <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f) {
            return new Empty<>();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
                throws IllegalArgumentException {
            if (t.size() != 0) throw new IllegalArgumentException("Tuple sizes must match for this operation.");
            return new Empty<>();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public Iterator<E> iterator() {
            return new Iterator<>() {
                @Override
                public boolean hasNext() {return false;}

                @Override
                public E next() {return null;}
            };
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public SafeArray<E> array() {
            return SafeArray.of(); // An empty array
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public List<E> list() {
            return List.of(); // An empty list
        }
    }

    /**
     * A tuple with a size of {@code 1}.
     *
     * @param <E> The type of element to contain
     */
    final class Single<E> implements Tuple<E> {
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
         * Creates a new single.
         *
         * @param element The element to contain
         */
        private Single(E element) {
            this.element = element;
        }

        //
        // Variables
        //

        /**
         * The only element of this tuple.
         */
        private final E element;

        //
        // Getters
        //

        /**
         * {@inheritDoc}
         */
        @Override
        public E get(int i) throws IndexOutOfBoundsException {
            if (i != 0) throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 1.");
            return element;
        }

        //
        // Serialization
        //

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public String toString() {
            return "[" + element + "]";
        }

        //
        // Methods
        //

        /**
         * @return {@code 1}
         */
        @Override
        public int size() {
            return 1;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean contains(@Nullable Object obj) {
            return Objects.equals(element, obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean containsAll(@Nonnull Iterable<?> i) {
            for (final Object o : i) {
                if (!Objects.equals(element, o)) return false;
            }

            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public <F> Tuple<F> map(@Nonnull Function<? super E, ? extends F> f) {
            return new Single<>(f.apply(element));
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
                throws IllegalArgumentException {
            if (t.size() != 1) throw new IllegalArgumentException("Tuple sizes must match for this operation.");
            return new Single<>(f.apply(element, t.get(0)));
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public Iterator<E> iterator() {
            return List.of(element).iterator();
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public SafeArray<E> array() {
            return SafeArray.of(element);
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public List<E> list() {
            return List.of(element);
        }
    }
}
