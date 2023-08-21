package civitas.celestis.util.collection;

import civitas.celestis.util.array.SafeArray;
import civitas.celestis.util.tuple.Tuple;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * An extended {@link List} with advanced featured.
 *
 * @param <E> The type of element this list should contain
 * @see List
 * @see Collection
 */
public interface RichList<E> extends List<E>, RichCollection<E> {
    //
    // Factory
    //

    /**
     * Creates a new rich list whose elements are populated from that of the provided array's
     * elements, then returns the new rich list instance.
     *
     * @param elements The elements to contain in the list
     * @param <E>      The type of element to contain
     * @return A new rich list containing the provided elements
     */
    @Nonnull
    @SafeVarargs
    static <E> RichList<E> of(@Nonnull E... elements) {
        return RichArrayList.of(elements);
    }

    /**
     * Creates a new rich list whose elements are copied from that of the provided collection {@code c}'s
     * elements, then returns the new rich list instance.
     *
     * @param c   The collection of which to copy elements from
     * @param <E> The type of element to contain
     * @return A new rich list containing the provided collection {@code c}'s elements
     */
    @Nonnull
    static <E> RichList<E> copyOf(@Nonnull Collection<? extends E> c) {
        return new RichArrayList<>(c);
    }

    /**
     * Creates a new rich list whose elements are copied from that of the provided array {@code a}'s
     * elements, then returns the new rich list instance.
     * @param a The array of which to copy elements from
     * @return A new rich list containing the provided array {@code a}'s elements
     * @param <E> The type of element to contain
     */
    @Nonnull
    static <E> RichList<E> copyOf(@Nonnull SafeArray<? extends E> a) {
        return RichArrayList.of(a.array());
    }

    /**
     * Creates a new rich list whose elements are copied from that of the provided tuple {@code t}'s
     * elements, then returns the new rich list instance.
     * @param t The tuple of which to copy elements from
     * @return A new rich list containing the provided tuple {@code t}'s elements
     * @param <E> The type of element to contain
     */
    @Nonnull
    static <E> RichList<E> copyOf(@Nonnull Tuple<? extends E> t) {
        return new RichArrayList<>(t.list());
    }

    //
    // Sub Operation
    //

    /**
     * Sets a sub-list of this list, populating the elements between the range of {@code [i1, i2)}
     * to the elements of the provided sub-list {@code l}.
     *
     * @param i1 The index at which to start copying elements
     * @param i2 The index at which to stop copying elements
     * @param l  The sub-list of which to copy elements from
     * @throws IndexOutOfBoundsException When the range is invalid, or the indices are out of bounds
     */
    void setRange(int i1, int i2, @Nonnull List<? extends E> l) throws IndexOutOfBoundsException;

    //
    // Shuffling
    //

    /**
     * Shuffles this list, randomizing its components' order.
     */
    default void shuffle() {
        Collections.shuffle(this);
    }

    //
    // Transformation
    //

    /**
     * Between this list and the provided list {@code l}, this applies the merger function {@code f}
     * to each corresponding pair of elements, then returns a new list whose values are populated from
     * that of the resulting values.
     *
     * @param l   The list of which to merge this list with
     * @param f   The merger function to handle the merging of the two lists
     * @param <F> The type of element to merge this list with
     * @param <G> The type of element to merge the two lists to
     * @return The resulting list
     * @throws IllegalArgumentException When the provided list {@code l}'s size is not equal to that
     *                                  of this list's size
     */
    @Nonnull
    <F, G> RichList<G> merge(@Nonnull List<F> l, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException;

    //
    // Iteration
    //

    /**
     * Executes the provided action for each element of this list. The index of the element is
     * provided as the first parameter, and the current value is provided as the second parameter.
     *
     * @param action The action of which to execute for each element of this list
     */
    void forEach(@Nonnull BiConsumer<Integer, ? super E> action);
}
