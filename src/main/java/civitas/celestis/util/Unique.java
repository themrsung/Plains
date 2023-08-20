package civitas.celestis.util;

import jakarta.annotation.Nonnull;

/**
 * A unique object which can be identified by a unique identifier of type {@code K}.
 * Unique identifiers should be unique within the lifecycle of this object and its usage context.
 *
 * @param <K> The type of key this object uses
 */
public interface Unique<K> {
    /**
     * Returns the unique identifier of this object. This identifier is guaranteed
     * to be unique within the lifecycle and context of this object.
     *
     * @return The unique identifier of this object
     */
    @Nonnull
    K getUniqueId();
}
