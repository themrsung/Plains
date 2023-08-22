package civitas.celestis.util.grid;

import java.io.Serializable;

/**
 * A two-dimensional structure of objects. Grids can be traversed
 * by either providing two separate indices, or by providing an index
 * @param <E> The type of element this grid should hold
 */
public interface Grid<E> extends Iterable<E>, Serializable {
}
