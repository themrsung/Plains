package civitas.celestis.util.group;

import civitas.celestis.util.Transformable;
import civitas.celestis.util.collection.Collectable;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.util.List;

/**
 * A type-safe group of objects.
 * <p>
 * Groups represent a structured collection of objects.
 * Groups may or may not have a consistent order, which is similar to
 * Java collections. Thus, all groups can be converted into a collection.
 * </p>
 *
 * @param <E> The type of element this group holds
 * @see Collectable
 * @see Groupable
 * @see Tuple
 * @see ArrayGroup
 */
public interface Group<E> extends Transformable<E>, Collectable<E>, Serializable {
    //
    // Factory
    //

    /**
     * Creates a new immutable group of the provided values.
     *
     * @param values The values to group
     * @param <E>    The type of element to group
     * @return An immutable group composed of the provided values
     */
    @Nonnull
    @SafeVarargs
    static <E> Group<E> of(@Nonnull E... values) {
        return Tuple.of(values);
    }

    /**
     * Creates a shallowly immutable copy of the provided group {@code g}.
     *
     * @param g   The group of which to create an immutable copy of
     * @param <E> The type of element the group is holding
     * @return A shallowly immutable copy of the provided group {@code g}
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    static <E> Group<E> copyOf(@Nonnull Group<E> g) {
        return Tuple.of((E[]) List.copyOf(g.collect()).toArray());
    }

    //
    // Properties
    //

    /**
     * Returns the size of this group. (the number of elements this group contains)
     *
     * @return The number of elements this group contains
     */
    int size();

    //
    // Containment
    //

    /**
     * Checks if this group contains the provided object {@code obj}.
     * If the provided element is {@code null}, this will return {@code true}
     * if this group contains at least one element which is {@code null}.
     *
     * @param obj The object to check for containment
     * @return {@code true} if at least one element of this group is equal to
     * the provided object {@code obj}
     */
    boolean contains(@Nullable Object obj);

    /**
     * Checks if this group contains multiple elements.
     * The iterable object provided will be iterated to check for containment
     * of all elements the iterable object provides.
     *
     * @param i The iterable object containing the elements to check for containment
     * @return {@code true} if this group contains every element of the iterable object
     */
    boolean containsAll(@Nonnull Iterable<?> i);

    //
    // Equality
    //

    /**
     * Checks for equality between this group and the provided object {@code obj}.
     * <p>
     * Unless explicitly stated otherwise in an overridden comment, this will check is the other object
     * is also a group, and the composition of the two groups are equal.
     * </p>
     * <p>
     * This requires that the two groups use the same method of indexing. (e.g. Tuple vs. Grid;
     * the former uses one-dimensional indexing whereas the second uses two-dimensional indexing.
     * Thus, a tuple and a grid cannot be equal)
     * </p>
     *
     * @param obj The object to compare to
     * @return {@code true} if the object is considered equal by the conventions mentioned above
     */
    boolean equals(@Nullable Object obj);

    //
    // Serialization
    //

    /**
     * Serializes this group into a string.
     *
     * @return The string representation of this group
     */
    @Override
    @Nonnull
    String toString();
}
