package civitas.celestis.util.data;

/**
 * An object which can be packed into 16 bits as a {@code short}.
 * <p>
 * The lossiness of the compression should be within an acceptable range,
 * or be completely lossless. (the original data has fewer than 17 bits)
 * </p>
 * <p>
 * Unpacking the packed bits is not defined in this interface, and requires
 * a custom implementation, which is usually achieved by a static method
 * {@code YourClass#unpack16(short)}.
 * </p>
 */
public interface Packable16 {
    /**
     * Packs the data regarding the current state of this object into 16 bits,
     * in the format of a {@code short}.
     *
     * @return The packed bits
     */
    short pack16();
}
