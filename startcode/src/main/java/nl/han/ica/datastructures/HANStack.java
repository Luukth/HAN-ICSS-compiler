package nl.han.ica.datastructures;

public class HANStack<T> implements IHANStack<T> {

    IHANLinkedList<T> list;

    public HANStack() {
        list = new HANLinkedList<>();
    }

    @Override
    public void push(T value) {
        list.addFirst(value);
    }

    @Override
    public T pop() {
        T tmp = list.getFirst();
        list.delete(0);
        return tmp;
    }

    @Override
    public T peek() {
        return list.getFirst();
    }

    @Override
    public boolean isEmpty() {
        return list.getSize() == 0;
    }
}
