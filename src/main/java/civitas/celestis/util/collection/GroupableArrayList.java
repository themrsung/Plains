package civitas.celestis.util.collection;

import civitas.celestis.util.group.Group;
import civitas.celestis.util.group.SafeArray;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An {@link ArrayList} which can be converted into a {@link Group}.
 *
 * @param <E> The type of element this list should hold
 * @see ArrayList
 * @see GroupableList
 */
public class GroupableArrayList<E> extends ArrayList<E> implements GroupableList<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 928085825613956822L;

    //
    // Constructors
    //

    /**
     * Creates a new groupable array list.
     *
     * @param initialCapacity The initial capacity of this list
     */
    public GroupableArrayList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Creates a new groupable array list.
     */
    public GroupableArrayList() {
    }

    /**
     * Creates a new groupable array list.
     *
     * @param c The collection of which to copy elements from
     */
    public GroupableArrayList(@Nonnull Collection<? extends E> c) {
        super(c);
    }

    //
    // Type Conversion
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public Group<E> group() {
        return SafeArray.of((E[]) toArray(Object[]::new));
    }
}
