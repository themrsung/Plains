package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;

import java.util.Arrays;
import java.util.List;

/**
 * A group of primitive {@code long}s which can be represented in array form.
 *
 * @see Group
 * @see Listable
 */
public interface LongGroup extends Group<Long>, Listable<Long> {
    /**
     * Converts this group into an array, then returns the converted array.
     *
     * @return The array representation of this group
     */
    @Nonnull
    long[] array();

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default List<Long> collect() {
        return Arrays.stream(array()).boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default List<Long> list() {
        return Arrays.stream(array()).boxed().toList();
    }
}
