package civitas.celestis.util.grid;

import civitas.celestis.exception.IllegalInstanceException;
import jakarta.annotation.Nonnull;

import java.util.Arrays;
import java.util.Objects;

/**
 * A utility class related to {@link Grid}s.
 *
 * @see Grid
 */
public final class Grids {
    //
    // Hashing
    //

    /**
     * Hashes a grid.
     *
     * @param g The grid of which to hash
     * @return The hash code of the grid
     */
    public static int hash(@Nonnull Grid<?> g) {
        return Objects.hash(Arrays.hashCode(g.array()), g.rows(), g.columns());
    }

    /**
     * Hashes a grid.
     *
     * @param g The grid of which to hash
     * @return The hash code of the grid
     */
    public static int hash(@Nonnull DoubleGrid g) {
        return Objects.hash(Arrays.hashCode(g.array()), g.rows(), g.columns());
    }

    /**
     * Hashes a grid.
     *
     * @param g The grid of which to hash
     * @return The hash code of the grid
     */
    public static int hash(@Nonnull FloatGrid g) {
        return Objects.hash(Arrays.hashCode(g.array()), g.rows(), g.columns());
    }

    /**
     * Hashes a grid.
     *
     * @param g The grid of which to hash
     * @return The hash code of the grid
     */
    public static int hash(@Nonnull LongGrid g) {
        return Objects.hash(Arrays.hashCode(g.array()), g.rows(), g.columns());
    }

    /**
     * Hashes a grid.
     *
     * @param g The grid of which to hash
     * @return The hash code of the grid
     */
    public static int hash(@Nonnull IntGrid g) {
        return Objects.hash(Arrays.hashCode(g.array()), g.rows(), g.columns());
    }

    //
    // Miscellaneous
    //

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalInstanceException Always
     */
    private Grids() throws IllegalInstanceException {
        throw new IllegalInstanceException(this);
    }
}
