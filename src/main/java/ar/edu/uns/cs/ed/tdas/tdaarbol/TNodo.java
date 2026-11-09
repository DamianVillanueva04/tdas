package ar.edu.uns.cs.ed.tdas.tdaarbol;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoble;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class TNodo<E> implements Position<E> {
    
    private TNodo<E> padre;
    private E elem;
    private PositionList<TNodo<E>> hijos;

    public TNodo(TNodo<E> p, E elemento) {
        padre = p;
        elem = elemento;
        hijos = new ListaDoble<TNodo<E>>();
    }

    public E element() {
        return elem;
    }

    public TNodo<E> getPadre() {
        return padre;
    }

    public PositionList<TNodo<E>> getHijos() {
        return hijos;
    }

    public void setElement(E elemento) {
        elem = elemento;
    }

    public void setPadre(TNodo<E> p) {
        padre = p;
    }

}