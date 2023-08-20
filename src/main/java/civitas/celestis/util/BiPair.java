package civitas.celestis.util;

import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * A specialized pair used to hold two elements of different types.
 *
 * @param <A> The first element's type
 * @param <B> The second element's type
 * @see Pair
 */
public class BiPair<A, B> extends Pair<Object> {
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
     * Creates a new binary pair.
     *
     * @param a The first element of this pair
     * @param b The second element of this pair
     */
    public BiPair(A a, B b) {
        super(a, b);
    }

    /**
     * Creates a new pair.
     *
     * @param p The pair of which to copy elements from
     */
    public BiPair(@Nonnull BiPair<A, B> p) {
        super(p);
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
}
