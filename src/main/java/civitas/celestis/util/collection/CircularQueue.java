package civitas.celestis.util.collection;

import java.util.Queue;

/**
 * A circular queue.
 *
 * @param <E> The type of element this queue should hold
 */
public interface CircularQueue<E> extends Queue<E>, RichCollection<E> {
    /**
     * Returns the next element in this queue, without removing it.
     * If this queue is empty, this will return {@code null}.
     *
     * @return The next element in this queue if present, {@code null} if not
     */
    E next();
}
