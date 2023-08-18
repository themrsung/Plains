package civitas.celestis.util.array;

import civitas.celestis.util.collection.Listable;
import civitas.celestis.util.group.Group;
import jakarta.annotation.Nonnull;

import java.util.Arrays;
import java.util.List;

/**
 * A group of primitive {@code int}s which can be represented in array form.
 *
 * @see Group
 * @see Listable
 */
public interface IntGroup extends Group<Integer>, Listable<Integer> {
    /**
     * Converts this group into an array, then returns the converted array.
     *
     * @return The array representation of this group
     */
    @Nonnull
    int[] array();

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default List<Integer> collect() {
        return Arrays.stream(array()).boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default List<Integer> list() {
        return Arrays.stream(array()).boxed().toList();
    }
}
