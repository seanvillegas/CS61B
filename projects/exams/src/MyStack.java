public interface MyStack<E> {
    void push(E element);
    E pop();
    boolean isEmpty();
    int size();

    private void insertAtBottom(E item) {

    }

    default void flip() {

    }
}
