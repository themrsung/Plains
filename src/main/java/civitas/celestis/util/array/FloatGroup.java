package civitas.celestis.util.array;

import civitas.celestis.util.collection.Listable;
import civitas.celestis.util.group.Group;
import jakarta.annotation.Nonnull;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A group of primitive {@code float}s which can be represented in array form.
 *
 * @see Group
 * @see Listable
 */
public interface FloatGroup extends Group<Float>, Listable<Float> {
    /**
     * Converts this group into an array, then returns the converted array.
     *
     * @return The array representation of this group
     */
    @Nonnull
    float[] array();

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default Collection<Float> collect() {
        final float[] array = array();
        final Float[] boxed = new Float[array.length];

        for (int i = 0; i < array.length; i++) {
            boxed[i] = array[i];
        }

        return Arrays.asList(boxed);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default List<Float> list() {
        final float[] array = array();
        final Float[] boxed = new Float[array.length];

        for (int i = 0; i < array.length; i++) {
            boxed[i] = array[i];
        }

        return Arrays.asList(boxed);
    }
}
