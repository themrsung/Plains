package civitas.celestis.util.grid;

import civitas.celestis.util.tuple.Int2;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;

/**
 * An index used to traverse a {@link Grid}.
 *
 * @see Grid
 * @see Grid.Index Index
 */
public class GridIndex extends Int2 implements Grid.Index {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    /**
     * Creates a new grid index.
     *
     * @param i The I component of this index
     * @param j The J component of this index
     */
    public GridIndex(int i, int j) {
        super(i, j);
    }

    /**
     * Creates a new grid index.
     *
     * @param elements An array containing the components of this index in IJ order
     */
    public GridIndex(@Nonnull int[] elements) {
        super(elements);
    }

    /**
     * Creates a new grid index.
     *
     * @param t The numeric tuple of which to copy component values from
     */
    public GridIndex(@Nonnull Tuple<? extends Number> t) {
        super(t);
    }

    //
    // Equality
    //

    /**
     * {@inheritDoc}
     *
     * @param obj The object to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Grid.Index index)) return false;
        return i == index.i() && j == index.j();
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String toString() {
        return "[" + i + ", " + j + "]";
    }
}
