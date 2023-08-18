package civitas.celestis.util.group;

import jakarta.annotation.Nonnull;

/**
 * An object which is not a group, but can easily be converted into a group.
 *
 * @param <E> The type of element this object is holding
 * @see Group
 */
public interface Groupable<E> {
    /**
     * Converts this object into a group, then returns the converted group.
     *
     * @return The group representation of this object
     */
    @Nonnull
    Group<E> group();
}
