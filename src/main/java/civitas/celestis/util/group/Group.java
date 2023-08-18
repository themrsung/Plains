package civitas.celestis.util.group;

import civitas.celestis.util.collection.Collectable;

/**
 * A group of objects.
 * <p>
 * Groups represent a structured collection of objects.
 * Groups may or may not have a consistent order, which is similar to
 * Java collections. Thus, all groups can be converted into a collection.
 * </p>
 *
 * @param <E> The type of element this group holds
 * @see Collectable
 * @see Groupable
 */
public interface Group<E> extends Collectable<E> {
}
