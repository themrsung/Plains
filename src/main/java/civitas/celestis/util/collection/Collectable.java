package civitas.celestis.util.collection;

import jakarta.annotation.Nonnull;

import java.util.Collection;

/**
 * An interface for objects which are not a collection,
 * but can be easily converted to a collection.
 *
 * @param <E> The type of element this object is holding
 * @see Collection
 */
public interface Collectable<E> {
    /**
     * Converts this object into a collection, then returns the converted collection.
     *
     * @return The collective representation of this object
     */
    @Nonnull
    Collection<E> collect();
}
