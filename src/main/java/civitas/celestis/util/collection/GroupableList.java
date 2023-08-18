package civitas.celestis.util.collection;

import java.util.List;

/**
 * A superinterface for lists which can be grouped.
 *
 * @param <E> The type of element this list should hold
 * @see List
 * @see GroupableCollection
 */
public interface GroupableList<E> extends List<E>, GroupableCollection<E> {
}
