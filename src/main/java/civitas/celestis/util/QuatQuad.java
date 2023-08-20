package civitas.celestis.util;

import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * A specialized quad used to hold four elements of different types.
 *
 * @param <A> The first element's type
 * @param <B> The second element's type
 * @param <C> The third element's type
 * @param <D> The fourth element's type
 * @see Quad
 */
public class QuatQuad<A, B, C, D> extends Quad<Object> {
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
    public QuatQuad(A a, B b, C c, D d) {
        super(a, b, c, d);
    }

    /**
     * Creates a new quad.
     *
     * @param q The quad of which to copy elements from
     */
    public QuatQuad(@Nonnull QuatQuad<A, B, C, D> q) {
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
