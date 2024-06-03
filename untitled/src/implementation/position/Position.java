package implementation.position;

public interface Position<E> {
    E getElement() throws IllegalStateException;
}
