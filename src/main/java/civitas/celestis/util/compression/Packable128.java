package civitas.celestis.util.compression;

import jakarta.annotation.Nonnull;

import java.util.UUID;

/**
 * An object which can be packed into 128 bits as a {@link UUID}.
 * <p>
 * The lossiness of the compression should be within an acceptable range,
 * or be completely lossless. (the original data has fewer than 129 bits)
 * </p>
 * <p>
 * Unpacking the packed bits is not defined in this interface, and requires
 * a custom implementation, which is usually achieved by a static method
 * {@code YourClass#unpack128(UUID)}.
 * </p>
 */
public interface Packable128 {
    /**
     * Packs the data regarding the current state of this object into 128 bits,
     * in the format of a {@link UUID}.
     *
     * @return The packed bits
     */
    @Nonnull
    UUID pack128();
}
