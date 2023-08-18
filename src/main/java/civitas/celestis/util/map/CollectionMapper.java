package civitas.celestis.util.map;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A transient intermediary utility class used to convert collections
 * into {@link Map}s with a key of {@link Integer}.
 *
 * @param <E> The type of element to map
 */
public final class CollectionMapper<E> implements Mappable<Integer, E> {
    //
    // Static Methods
    //

    /**
     * Maps a collection of objects into a map.
     * The keys correspond to their index within the collection.
     *
     * @param c   The collection to map
     * @param <E> The type of element to map
     * @return The mapped collection
     */
    @Nonnull
    public static <E> Map<Integer, E> map(@Nonnull Collection<E> c) {
        return new CollectionMapper<>(c).map();
    }

    //
    // Constructors
    //

    /**
     * Creates a new collection mapper.
     *
     * @param c The collection of which to map
     */
    private CollectionMapper(@Nonnull Collection<E> c) {
        this.collection = c;
    }

    //
    // Variables
    //

    /**
     * The collection to map.
     */
    private final Collection<E> collection;

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
        for (final E e : collection) {
            map.put(i++, e);
        }

        return map;
    }
}
