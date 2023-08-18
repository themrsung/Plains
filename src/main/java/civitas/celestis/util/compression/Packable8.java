package civitas.celestis.util.compression;

/**
 * An object which can be packed into 8 bits as a {@code byte}.
 * <p>
 * The lossiness of the compression should be within an acceptable range,
 * or be completely lossless. (the original data has fewer than 9 bits)
 * </p>
 * <p>
 * Unpacking the packed bits is not defined in this interface, and requires
 * a custom implementation, which is usually achieved by a static method
 * {@code YourClass#unpack16(byte)}.
 * </p>
 */
public interface Packable8 {
    /**
     * Packs the data regarding the current state of this object into 8 bits,
     * in the format of a {@code short}.
     *
     * @return The packed bits
     */
    byte pack8();
}
