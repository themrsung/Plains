package civitas.celestis.util.data;

/**
 * An object which can be packed into 64 bits as a {@code long}.
 * <p>
 * The lossiness of the compression should be within an acceptable range,
 * or be completely lossless. (the original data has fewer than 65 bits)
 * </p>
 * <p>
 * Unpacking the packed bits is not defined in this interface, and requires
 * a custom implementation, which is usually achieved by a static method
 * {@code YourClass#unpack64(long)}.
 * </p>
 */
public interface Packable64 {
    /**
     * Packs the data regarding the current state of this object into 64 bits,
     * in the format of a {@code long}.
     *
     * @return The packed bits
     */
    long pack64();
}
