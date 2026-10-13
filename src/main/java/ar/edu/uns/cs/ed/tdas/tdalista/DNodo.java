package ar.edu.uns.cs.ed.tdas.tdalista;
import ar.edu.uns.cs.ed.tdas.Position;


public class DNodo<E> implements Position<E> {
    private DNodo<E> sig;
    private DNodo<E> ant;
    private E elem;

public DNodo(E elemento, DNodo<E> prev, DNodo<E> next) {
    sig = next;
    ant = prev;
    elem = elemento;
}

public E element() {
    return elem;
}

//getter

public DNodo<E> getPrev() {
    return ant;
} 

public DNodo<E> getNext() {
    return sig;
}

//setters

public void setElement(E elemento) {
    elem = elemento;
}

public void setPrev(DNodo<E> prev) {
    ant = prev;
}

public void setNext(DNodo<E> next) {
    sig = next;
}
}