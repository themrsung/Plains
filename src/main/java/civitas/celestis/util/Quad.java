package civitas.celestis.util;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A fixed-size tuple which can only hold four elements.
 * Quads use ABCD notation to identify their elements.
 *
 * @param <E> The type of element this tuple should hold
 * @see Tuple
 * @see QuatQuad
 */
public class Quad<E> implements Tuple<E> {
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
     * Creates a new {@link QuatQuad quaternary quad} with four elements of different types.
     *
     * @param a   The first element of the quad
     * @param b   The second element of the quad
     * @param c   The third element of the quad
     * @param d   The fourth element of the quad
     * @param <A> The type of the first element
     * @param <B> The type of the second element
     * @param <C> The type of the third element
     * @param <D> The type of the fourth element
     * @return The quaternary quad constructed from the provided elements
     */
    @Nonnull
    public static <A, B, C, D> QuatQuad<A, B, C, D> of(A a, B b, C c, D d) {
        return new QuatQuad<>(a, b, c, d);
    }

    //
    // Constructors
    //

    /**
     * Creates a new quad.
     *
     * @param a The first element of this quad
     * @param b The second element of this quad
     * @param c The third element of this quad
     * @param d The fourth element of this quad
     */
    public Quad(E a, E b, E c, E d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Creates a new quad.
     *
     * @param elements An array containing the elements of this quad in ABCD order
     * @throws IllegalArgumentException When the array's length is not {@code 4}
     */
    public Quad(@Nonnull E[] elements) {
        if (elements.length != 4) {
            throw new IllegalArgumentException("The provided array's length is not 4.");
        }

        this.a = elements[0];
        this.b = elements[1];
        this.c = elements[2];
        this.d = elements[3];
    }

    /**
     * Creates a new quad.
     *
     * @param t The tuple of which to copy elements from
     * @throws IllegalArgumentException When the tuple's size is not {@code 4}
     */
    public Quad(@Nonnull Tuple<? extends E> t) {
        if (t.size() != 4) {
            throw new IllegalArgumentException("The provided tuple's size is not 4.");
        }

        this.a = t.get(0);
        this.b = t.get(1);
        this.c = t.get(2);
        this.d = t.get(3);
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

    /**
     * The fourth element of this tuple.
     */
    protected final E d;

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
        return 4;
    }

    //
    // Retrieval
    //

    /**
     * {@inheritDoc}
     *
     * @param i The index of the element to retrieve
     * @return {@inheritDoc}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int i) throws IndexOutOfBoundsException {
        return switch (i) {
            case 0 -> a;
            case 1 -> b;
            case 2 -> c;
            case 3 -> d;
            default -> throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for size 4.");
        };
    }

    /**
     * Returns the A component (the first component) of this quad.
     *
     * @return The A component of this quad
     */
    public E a() {
        return a;
    }

    /**
     * Returns the B component (the second component) of this quad.
     *
     * @return The B component of this quad
     */
    public E b() {
        return b;
    }

    /**
     * Returns the C component (the third component) of this quad.
     *
     * @return The C component of this quad
     */
    public E c() {
        return c;
    }

    /**
     * Returns the D component (the fourth component) of this quad.
     *
     * @return The D component of this quad
     */
    public E d() {
        return d;
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
        return Objects.equals(a, obj) ||
                Objects.equals(b, obj) ||
                Objects.equals(c, obj) ||
                Objects.equals(d, obj);
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
        return new Quad<>(f.apply(a), f.apply(b), f.apply(c), f.apply(d));
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
    public <F, G> Tuple<G> merge(@Nonnull Tuple<F> t, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException {
        if (t.size() != 4) {
            throw new IllegalArgumentException("Tuple sizes must match for this operation.");
        }

        return new Quad<>(f.apply(a, t.get(0)), f.apply(b, t.get(1)), f.apply(c, t.get(2)), f.apply(d, t.get(3)));
    }

    //
    // Iteration
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return List.of(a, b, c, d).iterator();
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tuple
     */
    @Override
    public void forEach(@Nonnull Consumer<? super E> action) {
        action.accept(a);
        action.accept(b);
        action.accept(c);
        action.accept(d);
    }

    /**
     * {@inheritDoc}
     *
     * @param action The action of which to execute for each element of this tuple
     */
    @Override
    public void forEach(@Nonnull BiConsumer<Integer, ? super E> action) {
        action.accept(0, a);
        action.accept(1, b);
        action.accept(2, c);
        action.accept(3, d);
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
    public SafeArray<E> array() {
        return SafeArray.of(a, b, c, d);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public List<E> list() {
        return List.of(a, b, c, d);
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
        return array().equals(t.array());
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return "[" + a + ", " + b + ", " + c + ", " + d + "]";
    }

    //
    // Quaternary Quad
    //

    /**
     * A specialized quad used to hold four elements of different types.
     *
     * @param <A> The first element's type
     * @param <B> The second element's type
     * @param <C> The third element's type
     * @param <D> The fourth element's type
     * @see Quad
     */
    public static class QuatQuad<A, B, C, D> extends Quad<Object> {
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
         * Creates a new quaternary quad.
         *
         * @param a The first element of this quad
         * @param b The second element of this quad
         * @param c The third element of this quad
         * @param d The fourth element of this quad
         */
        protected QuatQuad(A a, B b, C c, D d) {
            super(a, b, c, d);
        }

        /**
         * Creates a new quad.
         *
         * @param q The quad of which to copy elements from
         */
        protected QuatQuad(@Nonnull QuatQuad<A, B, C, D> q) {
            super(q);
        }

        //
        // Getters
        //

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public A a() {
            return (A) super.a();
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public B b() {
            return (B) super.b();
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public C c() {
            return (C) super.c();
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public D d() {
            return (D) super.c();
        }
    }
}
