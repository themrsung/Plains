package civitas.celestis.util.compression;

import jakarta.annotation.Nonnull;

/**
 * An object which can be packed into an arbitrary object {@link P}.
 * <p>
 * Unpacking the packed data is not defined in this interface, and requires
 * a custom implementation, which is usually achieved by a static method
 * {@code YourClass#unpack(P)}.
 * </p>
 * <p>
 * Using {@link String} as the packaged data is recommended, although it is not enforced.
 * </p>
 *
 * @param <P> The packaged data of this object
 */
public interface Packable<P> {
    /**
     * Packs the data regarding the current state of this object into another object.
     *
     * @return The packed data
     */
    @Nonnull
    P pack();
}
