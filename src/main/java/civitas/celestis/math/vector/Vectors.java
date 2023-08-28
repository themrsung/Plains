package civitas.celestis.math.vector;

import civitas.celestis.exception.IllegalInstanceException;

/**
 * A static utility class which contains methods related to {@link Vector vectors}.
 *
 * @see Vector
 * @see Vector2
 * @see Vector3
 * @see Vector4
 * @see ArrayVector
 */
public final class Vectors {
    //
    // Randomization
    //


    //
    // Miscellaneous
    //

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalInstanceException Always
     */
    private Vectors() throws IllegalInstanceException {
        throw new IllegalInstanceException(this);
    }
}
