package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A group of objects which can be represented in array form.
 *
 * @param <E> The type of element this group holds
 * @see Group
 * @see Listable
 */
public interface ArrayGroup<E> extends Group<E>, Listable<E> {
    //
    // Type Conversion
    //

    /**
     * Converts this group into an array, then returns the converted array.
     *
     * @return The array representation of this group
     */
    @Nonnull
    E[] array();

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default Collection<E> collect() {
        return Arrays.asList(array());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default List<E> list() {
        return Arrays.asList(array());
    }
}
