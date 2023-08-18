package civitas.celestis.util.map;

import civitas.celestis.util.group.Group;
import jakarta.annotation.Nonnull;

import java.util.HashMap;
import java.util.Map;

/**
 * A transient intermediary utility class used to convert groups
 * into {@link Map}s with a key of {@link Integer}.
 *
 * @param <E> The type of element to map
 */
public final class GroupMapper<E> implements Mappable<Integer, E> {
    //
    // Static Methods
    //

    /**
     * Maps a group of objects into a map.
     * The keys correspond to their index within the group.
     *
     * @param g   The group to map
     * @param <E> The type of element to map
     * @return The mapped group
     */
    @Nonnull
    public static <E> Map<Integer, E> map(@Nonnull Group<E> g) {
        return new GroupMapper<>(g).map();
    }

    //
    // Constructors
    //

    /**
     * Creates a new group mapper.
     *
     * @param g The group of which to map
     */
    private GroupMapper(@Nonnull Group<E> g) {
        this.group = g;
    }

    //
    // Variables
    //

    /**
     * The group to map.
     */
    private final Group<E> group;

    //
    // Methods
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Map<Integer, E> map() {
        final Map<Integer, E> map = new HashMap<>();

        int i = 0;
        for (final E e : group.collect()) {
            map.put(i++, e);
        }

        return map;
    }
}
