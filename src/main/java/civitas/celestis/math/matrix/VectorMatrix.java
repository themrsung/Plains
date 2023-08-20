package civitas.celestis.math.matrix;

import civitas.celestis.math.vector.Vector;
import civitas.celestis.util.grid.ArrayGrid;
import civitas.celestis.util.grid.Grid;
import civitas.celestis.util.grid.VectorGrid;
import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * A matrix of vectors.
 *
 * @param <V> The type of vector this matrix should hold
 */
public class VectorMatrix<V extends Vector<V>> extends ArrayGrid<V> implements VectorGrid<V> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = -339851313101714394L;

    //
    // Constructors
    //

    /**
     * Creates a new matrix.
     *
     * @param rows    The number of rows to initialize
     * @param columns The number of columns to initialize
     */
    public VectorMatrix(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Creates a new matrix.
     *
     * @param size The size of which to initialize this matrix to
     */
    public VectorMatrix(@Nonnull Index size) {
        super(size);
    }

    /**
     * Creates a new matrix.
     *
     * @param g The grid of which to copy elements from
     */
    public VectorMatrix(@Nonnull Grid<? extends V> g) {
        super(g);
    }

    //
    // Arithmetic
    //

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to add to this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public VectorGrid<V> add(double s) {
        final VectorMatrix<V> result = new VectorMatrix<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c].add(s);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to subtract from this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public VectorGrid<V> subtract(double s) {
        final VectorMatrix<V> result = new VectorMatrix<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c].subtract(s);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to multiply this grid by
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public VectorGrid<V> multiply(double s) {
        final VectorMatrix<V> result = new VectorMatrix<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c].multiply(s);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param s The scalar to divide this grid by
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public VectorGrid<V> divide(double s) throws ArithmeticException {
        final VectorMatrix<V> result = new VectorMatrix<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c].divide(s);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to add to this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public VectorGrid<V> add(@Nonnull V v) {
        final VectorMatrix<V> result = new VectorMatrix<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c].add(v);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param v The vector to subtract from this grid
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public VectorGrid<V> subtract(@Nonnull V v) {
        final VectorMatrix<V> result = new VectorMatrix<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c].subtract(v);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param g The grid of which to add to this grid
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public VectorGrid<V> add(@Nonnull Grid<? extends V> g) throws ArithmeticException {
        if (!dimensions().equals(g.dimensions())) {
            throw new IllegalArgumentException("Grid dimensions must match for this operation.");
        }

        final VectorMatrix<V> result = new VectorMatrix<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c].add(g.get(r, c));
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param g The grid of which to subtract from this grid
     * @return {@inheritDoc}
     * @throws ArithmeticException {@inheritDoc}
     */
    @Nonnull
    @Override
    public VectorGrid<V> subtract(@Nonnull Grid<? extends V> g) throws ArithmeticException {
        if (!dimensions().equals(g.dimensions())) {
            throw new IllegalArgumentException("Grid dimensions must match for this operation.");
        }

        final VectorMatrix<V> result = new VectorMatrix<>(rows, columns);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result.values[r][c] = values[r][c].subtract(g.get(r, c));
            }
        }

        return result;
    }
}
