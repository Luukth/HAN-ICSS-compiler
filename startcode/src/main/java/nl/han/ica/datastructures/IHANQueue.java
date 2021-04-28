package nl.han.ica.datastructures;

public interface IHANQueue<T> {

    /**
     * Clears list. Size equals 0 afterwards
     */
    void clear();

    /**
     * Checks whether queue is empty or not
     * @return true when empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Adds value T to the back of the queue
     * @param value value to add
     */
    void enqueue(T value);

    /**
     * Dequeues value at the front of the queue
     * @return value T at the front of the Queue
     */
    T dequeue();

    /**
     * Returns value at the front of the queue without removing
     * @return value at the front without removing
     */
    T peek();

    /**
     * Size of queue
     * @return the number of items in queue
     */
    int getSize();
}
