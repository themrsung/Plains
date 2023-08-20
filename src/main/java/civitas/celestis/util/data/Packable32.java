package civitas.celestis.util.data;

/**
 * An object which can be packed into 32 bits as a {@code int}.
 * <p>
 * The lossiness of the compression should be within an acceptable range,
 * or be completely lossless. (the original data has fewer than 33 bits)
 * </p>
 * <p>
 * Unpacking the packed bits is not defined in this interface, and requires
 * a custom implementation, which is usually achieved by a static method
 * {@code YourClass#unpack32(int)}.
 * </p>
 */
public interface Packable32 {
    /**
     * Packs the data regarding the current state of this object into 64 bits,
     * in the format of a {@code int}.
     *
     * @return The packed bits
     */
    int pack32();
}
