package civitas.celestis.util.group;

import civitas.celestis.util.collection.Listable;
import jakarta.annotation.Nonnull;

/**
 * A shallowly immutable group of objects.
 * Tuples have a defined order between its elements, and thus are convertible to lists.
 *
 * @param <E> The type of element this tuple holds
 * @see Group
 * @see Listable
 */
public interface Tuple<E> extends Group<E>, Listable<E>, Iterable<E> {
    //
    // Factory
    //

    /**
     * Creates a tuple containing the provided values.
     *
     * @param values The values to contain
     * @param <E>    The type of element to contain
     * @return A tuple containing the provided values
     */
    @Nonnull
    @SafeVarargs
    static <E> Tuple<E> of(@Nonnull E... values) {
        return switch (values.length) {
            case 0 -> new Empty<>();
            case 1 -> new Single<>(values[0]);
            case 2 -> new Pair<>(values[0], values[1]);
            case 3 -> new Triple<>(values[0], values[1], values[2]);
            case 4 -> new Quad<>(values[0], values[1], values[2], values[3]);
            default -> new ArrayTuple<>(values);
        };
    }

    //
    // Getters
    //

    /**
     * Returns the {@code i}th element of this tuple.
     *
     * @param i The index of the element to get
     * @return The {@code i}th element of this tuple
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    @Nonnull
    E get(int i) throws IndexOutOfBoundsException;

    /**
     * Converts this tuple into an array of elements.
     *
     * @return The array representation of this tuple
     */
    @Nonnull
    E[] array();

    //
    // Sub-operation
    //

    /**
     * Returns a sub-tuple of this tuple between the range of {@code i1-i2}.
     *
     * @param i1 The starting index of the sub-tuple
     * @param i2 The ending index of the sub-tuple
     * @return The sub-tuple of this tuple
     * @throws IndexOutOfBoundsException When the range is out of bounds
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    default Tuple<E> subTuple(int i1, int i2) throws IndexOutOfBoundsException {
        final E[] original = array();
        final E[] result;

        try {
            result = (E[]) new Object[i2 - i1];
        } catch (final Throwable ignored) {
            return new Empty<>(); // Fallback to empty tuple
        }

        System.arraycopy(original, i1, result, 0, i2 - i1);

        return of(result);
    }
}
