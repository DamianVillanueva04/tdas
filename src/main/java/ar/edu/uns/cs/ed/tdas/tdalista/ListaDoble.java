package ar.edu.uns.cs.ed.tdas.tdalista;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyListException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;

public class ListaDoble<E> implements PositionList<E> {
    private DNodo<E> header;
    private DNodo<E> trailer;
    private int size;

    public ListaDoble() {
        header = new DNodo<E>(null,null,null);
        trailer = new DNodo<E>(null,header,null);

        header.setNext(trailer);
        
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size==0);
    }

    @Override
    public Position<E> first() {
        if (isEmpty()) throw new EmptyListException("no tiene first por estar vacia");
        return header.getNext();
    }

    @Override
    public Position<E> last() {
        if (isEmpty()) throw new EmptyListException("no tiene last por estar vacia");
      return trailer.getPrev();
    }

    @Override
    public Position<E> next(Position<E> p) {
        if( isEmpty()) throw new InvalidPositionException("la lista esta vacia") ;
        DNodo<E> nodo = checkPosition(p);
        if ( p == last() ) throw new BoundaryViolationException("es el ultimo nodo") ;
        return nodo.getNext() ;
    }

    @Override
    public Position<E> prev(Position<E> p) {
       if (isEmpty()) throw new InvalidPositionException("la lista esta vacia");
       DNodo<E> nodo = checkPosition(p);
       if (p == first()) throw new BoundaryViolationException("el primer elemento no tiene anterior");
       return nodo.getPrev();
    }

    @Override
    public void addFirst(E element) {
        DNodo<E> nuevo = new DNodo<E>(element, header, header.getNext());
        header.getNext().setPrev(nuevo);
        header.setNext(nuevo);
        size++;
    }

    @Override
    public void addLast(E element) {
        DNodo<E> nuevo = new DNodo<E>(element, trailer.getPrev(), trailer);
        trailer.getPrev().setNext(nuevo);
        trailer.setPrev(nuevo);
        size++;
    }

    @Override
    public void addAfter(Position<E> p, E element) {
        if (isEmpty()) throw new InvalidPositionException("la lista esta vacia");
        DNodo<E> n = checkPosition(p);
        DNodo<E> nodo = new DNodo<E>(element, n, n.getNext());
        n.getNext().setPrev(nodo);
        n.setNext(nodo);
        size++;
    }

    @Override
    public void addBefore(Position<E> p, E element) {
        if (isEmpty()) throw new InvalidPositionException("la lista esta vacia");
        DNodo<E> n = checkPosition(p);
        DNodo<E> nodo = new DNodo<E>(element, n.getPrev(), n);
        n.getPrev().setNext(nodo);
        n.setPrev(nodo);
        size++;
    }

    @Override
    public E remove(Position<E> p) {
        if (isEmpty()) throw new InvalidPositionException("la lista esta vacia");
        DNodo<E> n = checkPosition(p);
        E elem = n.element();
        n.getPrev().setNext(n.getNext());
        n.getNext().setPrev(n.getPrev());
        size--;

        n.setElement(null);
        n.setPrev(null);
        n.setNext(null);

        return elem;
    }

    @Override
    public E set(Position<E> p, E element) {
        E elem;
        if (isEmpty()) throw new InvalidPositionException("la lista esta vacia");
        checkPosition(p);
        elem = checkPosition(p).element();
        checkPosition(p).setElement(element);
        return elem;
    }

    @Override
    public Iterator<E> iterator() {
        return new ElementIterator<E>(this);
    }

    @Override
    public Iterable<Position<E>> positions() {
       PositionList<Position<E>> p1 = new ListaDoble<Position<E>>();
       if (size != 0) {
        DNodo<E> n = header.getNext();
        while (n != trailer) {
            p1.addLast(n);
            n = n.getNext();
        }
       }
       return p1;
    }

    private DNodo<E> checkPosition(Position<E> p){
        try{
            if( p == null) throw new InvalidPositionException("la posicion es nula") ;
            if(p.element() == null) throw new InvalidPositionException("la posicion fue removida previamente") ;
            return (DNodo<E>) p ;
        }
        catch( ClassCastException c) { throw new InvalidPositionException("la posicion no pertenece a la lista") ;}
    }

    public void ejercicio2(E e1, E e2) {
        if (size == 0)       {
            addFirst(e2);
            addLast(e1);        }
        /*else if (size == 1)     este caso queda contemplado en el else
            addAfter(,e2);   
            addBefore(,e1);        */
        else                                     {
            addAfter(header.getNext(), e1);
            addBefore(trailer.getPrev(), e2);    }
    }                                                //tiempo de ejecucion constante


    //de parcial
    public PositionList<E> copiarElemPosPar() throws EmptyListException {
        if (this.isEmpty()) throw new EmptyListException("la lista está vacía");
        PositionList<E> nueva = new ListaDoble<E>();
        int cont = 0;
        for (Position<E> p: this.positions()) {
            cont++;
            if ((cont % 2) == 0) {
                nueva.addLast(p.element());

            }
    }

    public boolean contieneEnOrden(PositionList<E> l1, PositionList<E> l2) {
        if (l1.isEmpty()) return true;
        if (!l1.isEmpty() && l2.isEmpty()) return false;

        boolean resultado = true;
        Position<E> cursor = l2.first();

        for (Position<E> pos: l1.positions()) {
            while (cursor != l2.last().getNext() && !encontre) {
                boolean encontre = false;
                if (pos.element().equals(cursor.element()))
                    encontre = true;
    }
}}
}