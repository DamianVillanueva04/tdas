package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import ar.edu.uns.cs.ed.tdas.Position;

public class BTNodo<E> implements Position<E> {
    private E elem;
    private BTNodo<E> parent;
    private BTNodo<E> hijoI;
    private BTNodo<E> hijoD;

    public BTNodo(BTNodo<E> father, BTNodo<E> left, BTNodo<E> right, E elemento) {
        elem = elemento;
        parent = father;
        hijoI = left;
        hijoD = right;
    }

    public E element() {
        return elem;
    }

    public BTNodo<E> getParent() {
        return parent;
    }

    public BTNodo<E> getLeft() {
        return hijoI;
    }

    public BTNodo<E> getRight() {
        return hijoD;
    }

    //setters

    public void setElement(E elemento) {
        elem = elemento;
    }

    public void setParent(BTNodo<E> p) {
        parent = p;
    }

    public void setLeft(BTNodo<E> l) {
        hijoI = l;
    }

    public void setRight(BTNodo<E> r) {
        hijoD = r;
    }
}
