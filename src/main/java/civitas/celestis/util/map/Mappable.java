package civitas.celestis.util.map;

import jakarta.annotation.Nonnull;

import java.util.Map;

/**
 * An object which is not a map, but can easily be converted into a map.
 *
 * @param <K> The key object
 * @param <V> The value object
 */
public interface Mappable<K, V> {
    /**
     * Converts this object into a map, then returns the converted map.
     *
     * @return The map representation of this object
     */
    @Nonnull
    Map<K, V> map();
}
