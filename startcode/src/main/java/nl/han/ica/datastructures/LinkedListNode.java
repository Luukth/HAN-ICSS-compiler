package nl.han.ica.datastructures;

public class LinkedListNode<T> {
    private T value;
    private LinkedListNode<T> next;

    public LinkedListNode(T value) {
        this.value = value;
    }

    public void setNext(LinkedListNode<T> next) {
        this.next = next;
    }

    public LinkedListNode<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
