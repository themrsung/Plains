package civitas.celestis.util.collection;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.function.BiFunction;

/**
 * A list with advanced features.
 *
 * @param <E> The type of element this list should hold
 */
public interface RichList<E> extends RichCollection<E> {
    //
    // Transformation
    //

    /**
     * Between this collection and the provided collection {@code c}, this applies the merger
     * function {@code f} to each corresponding pair of elements, then returns a new collection
     * containing the resulting elements. This operation does not preserve the type bounds
     * of this collection.
     *
     * @param l   The list of which to merge this collection with
     * @param f   The merger function to handle the merging of the two collections
     * @param <F> The type of element to merge this collection with
     * @param <G> The type of element to merge the two collections to (does not require that it
     *            is a subtype of {@code E} or the other collection's generic component type)
     * @return The resulting collection
     * @throws IllegalArgumentException When the two collections' sizes are different
     */
    @Nonnull
    <F, G> RichCollection<G> merge(@Nonnull List<F> l, @Nonnull BiFunction<? super E, ? super F, G> f)
            throws IllegalArgumentException;
}
