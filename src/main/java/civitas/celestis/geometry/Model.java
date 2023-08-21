package civitas.celestis.geometry;

import civitas.celestis.util.Tuple;
import jakarta.annotation.Nonnull;

import java.io.Serializable;

/**
 * Represents a graphical model of an object in 3D space. Models are shallowly
 * immutable, and thus faces cannot be added or removed after instantiation.
 *
 * @see Face
 */
public interface Model extends Serializable {
    //
    // Faces
    //

    /**
     * Returns a tuple containing every face of this model.
     *
     * @return A tuple of all faces of this model
     */
    @Nonnull
    Tuple<? extends Face> getFaces();

    /**
     * Returns the {@code i}th face of this model.
     *
     * @param i The index of the face to get
     * @return The {@code i}th face of this model
     * @throws IndexOutOfBoundsException When the index {@code i} is out of bounds
     */
    @Nonnull
    Face getFace(int i) throws IndexOutOfBoundsException;

    /**
     * Returns the face count of this model.
     *
     * @return The number of faces this model has
     */
    int getFaceCount();
}
