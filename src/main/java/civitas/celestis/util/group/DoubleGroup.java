package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;

import java.util.Arrays;
import java.util.List;

/**
 * A group of primitive {@code double}s which can be represented in array form.
 *
 * @see Group
 * @see Listable
 */
public interface DoubleGroup extends Group<Double>, Listable<Double> {
    /**
     * Converts this group into an array, then returns the converted array.
     *
     * @return The array representation of this group
     */
    @Nonnull
    double[] array();

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default List<Double> collect() {
        return Arrays.stream(array()).boxed().toList();
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    default List<Double> list() {
        return Arrays.stream(array()).boxed().toList();
    }
}
