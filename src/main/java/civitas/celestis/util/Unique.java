package civitas.celestis.util;

import jakarta.annotation.Nonnull;

/**
 * A unique object which can be identified by an arbitrary key {@code K}.
 *
 * @param <K> The key used to uniquely identify an instance of this object
 */
public interface Unique<K> {
    /**
     * Returns the unique identifier of this object.
     *
     * @return The unique identifier of this object
     */
    @Nonnull
    K getUniqueId();
}
