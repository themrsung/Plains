package civitas.celestis.util.collection;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.List;

/**
 * An interface for objects which are not a list,
 * but can be easily converted to a list.
 *
 * @param <E> The type of element this object is holding
 * @see List
 */
public interface Listable<E> extends Collectable<E> {
    /**
     * Converts this object into a list, then returns the converted list.
     *
     * @return The list representation of this object
     */
    @Nonnull
    List<E> list();

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default Collection<E> collect() {
        return list();
    }
}
