package civitas.celestis.util.collection;

import civitas.celestis.util.group.Groupable;

import java.util.Collection;

/**
 * A superinterface for collections which can be grouped.
 *
 * @param <E> The type of element this collection should hold
 * @see Collection
 * @see Groupable
 */
public interface GroupableCollection<E> extends Collection<E>, Groupable<E> {
}
