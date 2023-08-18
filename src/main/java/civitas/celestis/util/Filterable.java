package civitas.celestis.util;

import jakarta.annotation.Nonnull;

import java.util.function.Predicate;

/**
 * A type-safe filterable object composed of sub-objects.
 * Filters can be applied by providing a {@link Predicate} function.
 *
 * @param <E> The type of element this object is holding
 */
public interface Filterable<E> {
    /**
     * Applies the provided filter function {@code f} to all elements of this object,
     * then returns the filtered object.
     * If this object has a specified order, the order will remain intact.
     *
     * @param f The filter function to apply to sub-objects of this object
     * @return The filtered object
     */
    @Nonnull
    Filterable<E> filter(@Nonnull Predicate<? super E> f);
}
