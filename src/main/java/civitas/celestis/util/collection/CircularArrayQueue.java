package civitas.celestis.util.collection;

import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * A circular queue implemented by an array list.
 *
 * @param <E> The type of element this queue should hold
 */
public class CircularArrayQueue<E> extends RichArrayList<E> implements CircularQueue<E> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 5963575498640084907L;

    //
    // Constructors
    //

    /**
     * Creates a new circular queue.
     *
     * @param initialCapacity The initial capacity of this queue
     */
    public CircularArrayQueue(int initialCapacity) {
        super(initialCapacity);
        this.i = 0;
    }

    /**
     * Creates a new circular queue.
     */
    public CircularArrayQueue() {
        this.i = 0;
    }

    /**
     * Creates a new circular queue.
     *
     * @param c The collection of which to copy entries from
     */
    public CircularArrayQueue(@Nonnull Collection<? extends E> c) {
        super(c);
        this.i = 0;
    }

    //
    // Variables
    //

    /**
     * The current iterator of this queue.
     */
    private volatile int i;

    //
    // Iterator
    //

    /**
     * Increments the current iterator, then returns the current iterator.
     * This is the equivalent of {@code i++}.
     *
     * @return The current iterator of this queue
     */
    protected synchronized final int i() {
        final int current = i;
        i = (i + 1) % size();
        return current;
    }

    /**
     * Increments the current iterator by {@code n}, then returns the current iterator.
     * This is the equivalent of {@code i = i + n}.
     *
     * @param n The number of iterations to increment
     * @return The current iterator of this queue
     */
    protected synchronized final int i(int n) {
        final int current = i;
        i = (i + n) % size();
        return current;
    }

    //
    // Methods
    //

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized int size() {
        return super.size();
    }

    /**
     * {@inheritDoc}
     *
     * @param e element whose presence in this collection is to be ensured
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean add(E e) {
        i();
        return super.add(e);
    }

    /**
     * {@inheritDoc}
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    @Override
    public synchronized void add(int index, E element) {
        i();
        super.add(index, element);
    }

    /**
     * {@inheritDoc}
     *
     * @param c collection containing elements to be added to this collection
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean addAll(Collection<? extends E> c) {
        i(c.size());
        return super.addAll(c);
    }

    /**
     * {@inheritDoc}
     *
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c     collection containing elements to be added to this list
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        i(c.size());
        return super.addAll(index, c);
    }

    /**
     * {@inheritDoc}
     *
     * @param o element to be removed from this collection, if present
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean remove(Object o) {
        final boolean result = super.remove(o);
        if (result) i--;
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param index the index of the element to be removed
     * @return {@inheritDoc}
     */
    @Override
    public synchronized E remove(int index) {
        final E result = super.remove(index);
        i--;
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param fromIndex index of first element to be removed
     * @param toIndex   index after last element to be removed
     */
    @Override
    protected synchronized void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        i -= (toIndex - fromIndex);
    }

    /**
     * {@inheritDoc}
     *
     * @param c collection containing elements to be removed from this collection
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        final boolean result = super.removeAll(c);
        if (result) i = 0;
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param filter a predicate which returns {@code true} for elements to be
     *               removed
     * @return {@inheritDoc}
     */
    @Override
    public synchronized boolean removeIf(Predicate<? super E> filter) {
        final int currentSize = size();
        final boolean result = super.removeIf(filter);
        final int resultingSize = size();

        i -= (resultingSize - currentSize);

        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public synchronized E next() {
        return get(i());
    }

    /**
     * {@inheritDoc}
     *
     * @param e the element to add
     * @return {@inheritDoc}
     */
    @Override
    public boolean offer(E e) {
        return add(e);
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public E remove() {
        return remove(i());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public E poll() {
        return remove(i());
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public E element() {
        final E element = get(i());
        if (element == null) throw new NoSuchElementException("This queue is empty.");
        return element;
    }

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    @Override
    public E peek() {
        return get(i());
    }
}
