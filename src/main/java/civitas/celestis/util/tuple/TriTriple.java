package civitas.celestis.util.tuple;

import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * A specialized triple used to hold three elements of different types.
 *
 * @param <A> The first element's type
 * @param <B> The second element's type
 * @param <C> The third element's type
 * @see Triple
 */
public class TriTriple<A, B, C> extends Triple<Object> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 8632716994301230512L;

    //
    // Constructors
    //

    /**
     * Creates a new ternary triple.
     *
     * @param a The first element of this triple
     * @param b The second element of this triple
     * @param c The third element of this triple
     */
    public TriTriple(A a, B b, C c) {
        super(a, b, c);
    }

    /**
     * Creates a new ternary triple.
     *
     * @param t The triple of which to copy elements from
     */
    public TriTriple(@Nonnull TriTriple<A, B, C> t) {
        super(t);
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
}
