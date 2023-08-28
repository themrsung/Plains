package civitas.celestis.math.vector;

import civitas.celestis.exception.IllegalInstanceException;
import civitas.celestis.math.Scalars;
import jakarta.annotation.Nonnull;

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

    /**
     * Returns a random unit vector. (a vector with a Euclidean norm of {@code 1})
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector2 random2() {
        return new Vector2(Scalars.random(-1, 1), Scalars.random(-1, 1)).normalizeOrDefault(Vector2.POSITIVE_X);
    }

    /**
     * Returns a random unit vector. (a vector with a Euclidean norm of {@code 1})
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector3 random3() {
        return new Vector3(Scalars.random(-1, 1), Scalars.random(-1, 1), Scalars.random(-1, 1))
                .normalizeOrDefault(Vector3.POSITIVE_X);
    }

    /**
     * Returns a random unit vector. (a vector with a Euclidean norm of {@code 1})
     *
     * @return A random unit vector
     */
    @Nonnull
    public static Vector4 random4() {
        return new Vector4(
                Scalars.random(-1, 1),
                Scalars.random(-1, 1),
                Scalars.random(-1, 1),
                Scalars.random(-1, 1)
        ).normalizeOrDefault(Vector4.POSITIVE_X);
    }

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
