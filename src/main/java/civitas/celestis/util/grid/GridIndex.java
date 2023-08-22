package civitas.celestis.util.grid;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * The default implementation of a grid's {@link Grid.Index index}.
 *
 * @param row    The row of this index.
 * @param column The column of this index.
 */
public record GridIndex(int row, int column) implements Grid.Index {
    /**
     * {@inheritDoc}
     *
     * @param obj The object to compare to
     * @return {@inheritDoc}
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Grid.Index i)) return false;
        return row == i.row() && column == i.column();
    }

    /**
     * Serializes this index into a string.
     *
     * @return The string representation of this index
     */
    @Nonnull
    @Override
    public String toString() {
        return "[" + row + ", " + column + "]";
    }
}
