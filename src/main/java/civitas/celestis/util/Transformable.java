package civitas.celestis.util;

import jakarta.annotation.Nonnull;

import java.util.function.Function;

/**
 * A type-safe transformable object composed of sub-objects.
 * Transformation can be performed by either preserving the type ({@link #transform(Function)})
 * or by changing the type ({@link #map(Function)}).
 *
 * @param <E> The type of element this object is holding
 */
public interface Transformable<E> {
    /**
     * Applies the provided function {@code f} to all elements of this object,
     * then returns the transformed object.
     * If this object has a specified order, the order will remain intact.
     *
     * @param f The function to apply to each element of this object
     * @return The transformed object
     */
    @Nonnull
    Transformable<E> transform(@Nonnull Function<? super E, E> f);

    /**
     * Applies the provided mapper function {@code f} to all elements of this object,
     * then returns the mapped object.
     *
     * @param f   The function to apply to each element of this object
     * @param <F> The type of which to map this object to
     * @return The mapped object
     */
    @Nonnull
    <F> Transformable<F> map(@Nonnull Function<? super E, F> f);
}
