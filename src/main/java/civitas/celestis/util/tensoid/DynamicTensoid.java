package civitas.celestis.util.tensoid;

import civitas.celestis.math.Numbers;
import jakarta.annotation.Nonnull;

/**
 * A tensoid whose size can be dynamically adjusted without requiring re-instantiation.
 *
 * @param <E> The type of element this tensoid should hold
 * @see Tensoid
 * @see HashTensoid
 */
public interface DynamicTensoid<E> extends Tensoid<E> {
    //
    // Factory
    //

    /**
     * Creates a new dynamic tensoid from a 3D array of entries.
     *
     * @param elements The elements the tensoid should contain
     * @param <E>      The type of element to contain
     * @return The constructed dynamic tensoid
     */
    @Nonnull
    static <E> DynamicTensoid<E> of(@Nonnull E[][][] elements) {
        return HashTensoid.of(elements);
    }

    //
    // Properties
    //

    /**
     * Checks if the specified index exists within this tensoid.
     *
     * @param i The I coordinate of the slot to check
     * @param j The J coordinate of the slot to check
     * @param k The K coordinate of the slot to check
     * @return {@code true} if the slot exists (either has a value, or is {@code null})
     */
    boolean exists(int i, int j, int k);

    /**
     * Checks if the specified index exists within this tensoid.
     *
     * @param i The index of the slot to check
     * @return {@code true} if the slot exists (either has a value, or is {@code null})
     */
    boolean exists(@Nonnull Index i);

    //
    // Accessors
    //

    /**
     * {@inheritDoc}
     * Since dynamic tensoids do not have a fixed size, this operation cannot fail.
     *
     * @param i The I coordinate of the slot to set
     * @param j The J coordinate of the slot to set
     * @param k The K coordinate of the slot to set
     * @param v The value to assign to the specified slot
     */
    @Override
    void set(int i, int j, int k, E v);

    /**
     * {@inheritDoc}
     * Since dynamic tensoids do not have a fixed size, this operation cannot fail.
     *
     * @param i The index of the slot to set
     * @param v The value to assign to the specified slot
     */
    @Override
    void set(@Nonnull Index i, E v);

    /**
     * Removes an element from this tensoid, and reduces this tensoid's size if necessary.
     *
     * @param i The I coordinate of the element to remove
     * @param j The J coordinate of the element to remove
     * @param k The K coordinate of the element to remove
     * @return {@code true} if this tensoid's state was changed as a result of this operation
     * (if an element or and empty slot was found at the specified position)
     */
    boolean remove(int i, int j, int k);

    /**
     * Removes an element from this tensoid, and reduces this tensoid's size if necessary.
     *
     * @param i The index of the element to remove
     * @return {@code true} if this tensoid's state was changed as a result of this operation
     * (if an element or and empty slot was found at the specified position)
     */
    boolean remove(@Nonnull Index i);

    //
    // Resizing
    //

    /**
     * Cleans this tensoid by removing every {@code null} entry. This differs from {@link #trim()}
     * in only that it does not reduce the dimensions of this tensoid even if possible.
     *
     * @see #trim()
     */
    void clean();

    /**
     * Trims this tensoid by first removing every {@code null} value from this tensoid,
     * then reduces its size to be as small as possible. This differs from {@link #clean()}
     * in that it reduces the size of this tensoid if possible.
     *
     * @see #clean()
     */
    void trim();

    /**
     * Forcefully sets the size of this tensoid, without regard to the integrity of the data.
     *
     * @param w The new width of this tensoid
     * @param h The new height of this tensoid
     * @param d The new depth of this tensoid
     */
    void setSize(int w, int h, int d);

    /**
     * Forcefully sets the size of this tensoid, without regard to the integrity of the data.
     *
     * @param size The new size of this tensoid
     */
    void setSize(@Nonnull Index size);

    //
    // Utility
    //

    /**
     * Returns whether the provided value {@code val} is within the range of {@code [min, max]}.
     *
     * @param val The value of which to validate
     * @param min The minimum allowed value
     * @param max The maximum allowed value
     * @return {@code true} if the value is within the range of {@code [min, max]}
     */
    static boolean isInRange(@Nonnull Index val, @Nonnull Index min, @Nonnull Index max) {
        return Numbers.isInRange(val.i(), min.i(), max.i()) &&
                Numbers.isInRange(val.j(), min.j(), max.j()) &&
                Numbers.isInRange(val.k(), min.k(), max.k());
    }

    /**
     * Returns the minimum index between the two indices.
     *
     * @param i1 The first index to compare
     * @param i2 The second index to compare
     * @return The minimum index of the two indices
     */
    @Nonnull
    static Index min(@Nonnull Index i1, @Nonnull Index i2) {
        return Tensoid.newIndex(Math.min(i1.i(), i2.i()), Math.min(i1.j(), i2.j()), Math.min(i1.k(), i2.k()));
    }

    /**
     * Returns the maximum index between the two indices.
     *
     * @param i1 The first index to compare
     * @param i2 The second index to compare
     * @return The maximum index of the two indices
     */
    @Nonnull
    static Index max(@Nonnull Index i1, @Nonnull Index i2) {
        return Tensoid.newIndex(Math.max(i1.i(), i2.i()), Math.max(i1.j(), i2.j()), Math.max(i1.k(), i2.k()));
    }
}
