package civitas.celestis.math;

import civitas.celestis.math.vector.Quaternion;
import civitas.celestis.math.vector.Vector2;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.math.vector.Vector4;
import civitas.celestis.util.io.ArrayReader;
import jakarta.annotation.Nonnull;

/**
 * Contains utility methods and constants related to vectors.
 */
public final class Vectors {
    //
    // IO
    //

    /**
     * Deserializes a string into a two-dimensional vector.
     *
     * @param input The input string to parse
     * @return The parsed vector
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static Vector2 parseVector2(@Nonnull String input)
            throws NumberFormatException {
        return new Vector2(ArrayReader.readDoubleArray(input));
    }

    /**
     * Deserializes a string into a three-dimensional vector.
     *
     * @param input The input string to parse
     * @return The parsed vector
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static Vector3 parseVector3(@Nonnull String input)
            throws NumberFormatException {
        return new Vector3(ArrayReader.readDoubleArray(input));
    }

    /**
     * Deserializes a string into a four-dimensional vector.
     *
     * @param input The input string to parse
     * @return The parsed vector
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static Vector4 parseVector4(@Nonnull String input)
            throws NumberFormatException {
        return new Vector4(ArrayReader.readDoubleArray(input));
    }

    /**
     * Deserializes a string into a quaternion.
     *
     * @param input The input string to parse
     * @return The parsed vector
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static Quaternion parseQuaternion(@Nonnull String input)
            throws NumberFormatException {
        return new Quaternion(ArrayReader.readDoubleArray(input));
    }
}
