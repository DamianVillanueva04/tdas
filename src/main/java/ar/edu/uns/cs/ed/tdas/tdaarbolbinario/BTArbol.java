package ar.edu.uns.cs.ed.tdas.tdaarbolbinario;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.BoundaryViolationException;
import ar.edu.uns.cs.ed.tdas.excepciones.EmptyTreeException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidOperationException;
import ar.edu.uns.cs.ed.tdas.excepciones.InvalidPositionException;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.DiccionarioHash;
import ar.edu.uns.cs.ed.tdas.tdadiccionario.Dictionary;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoble;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class BTArbol<E> implements BinaryTree<E> {

    private BTNodo<E> raiz;
    private int size;

    public BTArbol() {
        raiz = null;
        size = 0;
    }

    public Position<E> left(Position<E> v) {
        BTNodo<E> nodo = checkPosition(v);
        if (nodo.getLeft() != null)
            return nodo.getLeft();
        else throw new BoundaryViolationException("el nodo no tiene hijo izquierdo");
    }

    public Position<E> right(Position<E> v) {
        BTNodo<E> nodo = checkPosition(v);
        if (nodo.getRight() != null) 
            return nodo.getRight();
        else throw new BoundaryViolationException("el nodo no tiene hijo derecho");
    }

    private BTNodo<E> checkPosition(Position<E> p) {
        try {
            if (p == null) throw new InvalidPositionException("posicion nula");
            if (p.element() == null) throw new InvalidPositionException("la posicion no tiene nodo");
            return (BTNodo<E>) p;
        } 
        catch (ClassCastException c) {throw new InvalidPositionException("la posicion no pertenece al arbol");}
    }

    public boolean hasLeft(Position<E> v) {
        boolean respuesta;
        BTNodo<E> nodo = checkPosition(v);
        if (nodo.getLeft() != null)
            respuesta = true;
        else respuesta = false;
        return respuesta;
    }

    public boolean hasRight(Position<E> v) {
        BTNodo<E> nodo = checkPosition(v);
        boolean respuesta;
        if (nodo.getRight() != null)
            respuesta = true;
        else respuesta = false;
        return respuesta;
    }

    public Position<E> addLeft(Position<E> v, E r) {
        if (this.isEmpty())
            throw new InvalidPositionException("el arbol esta vacio");
        BTNodo<E> nodo = checkPosition(v);
        if (this.hasLeft(v))
            throw new InvalidOperationException("el nodo ya tiene hijo izquierdo");
        else {
            BTNodo<E> hijo = new BTNodo<E>(nodo, null, null, r);
            nodo.setLeft(hijo);
            size++;
            return hijo;
        }
    }

    public Position<E> addRight(Position<E> v, E r) {
        if (this.isEmpty()) throw new InvalidPositionException("el arbol está vacío");
        BTNodo<E> nodo = checkPosition(v);
        if (this.hasRight(nodo))
            throw new BoundaryViolationException("el nodo ya tiene hijo derecho");
        else {
            BTNodo<E> hijo = new BTNodo<E>(nodo,null,null,r);
            nodo.setRight(hijo);
            size++;
            return hijo;
        }
    }

    public void attach(Position<E> v, BinaryTree<E> T1, BinaryTree<E> T2) {
        if ((T1 == this) || (T2 == this)) throw new InvalidOperationException("no se puede insertar el mismo arbol");
        if (this.isEmpty()) throw new InvalidPositionException("el árbol está vacío");
        BTNodo<E> nodo = checkPosition(v);
        if (!isExternal(nodo)) throw new InvalidPositionException("el nodo no es hoja");
        else {
            BTArbol<E> t1 = (BTArbol<E>) T1;
            BTArbol<E> t2 = (BTArbol<E>) T2;
            if ((t1 != null) && (!t1.isEmpty())) {
                t1.raiz.setParent(nodo);
                nodo.setLeft(t1.raiz);
                t1.raiz = null;
                size = size + t1.size;
                t1.size = 0; }

            if ((t2 != null) && (!t2.isEmpty())) {
                t2.raiz.setParent(nodo);
                nodo.setRight(t2.raiz);
                t2.raiz = null;
                size = size + t2.size;
                t2.size = 0; }
        }
    }

    // metodos de Tree<E>

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public Iterator<E> iterator() {
        PositionList<E> lista = new ListaDoble<E>();
        for (Position<E> p: positions())
            lista.addLast(p.element());
        return lista.iterator();
    }

    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> lista = new ListaDoble<Position<E>>();
        if (size != 0)
            preOrden(raiz, lista);
        return lista;
    }

    private void preOrden(BTNodo<E> r, PositionList<Position<E>> lista) {
        if (r != null) {
            lista.addLast(r);
            preOrden(r.getLeft(), lista);
            preOrden(r.getRight(), lista); 
        }
    }

    public E replace(Position<E> v, E e) {
        BTNodo<E> nodo = checkPosition(v);
        E devolver = nodo.element();
        nodo.setElement(e);
        return devolver;
    }

    public Position<E> root() {
        if (isEmpty()) throw new EmptyTreeException("el arbol está vacío");
        return raiz;
    }

    public Position<E> parent(Position<E> v) {
        BTNodo<E> nodo = checkPosition(v);
        if (nodo == raiz) throw new BoundaryViolationException("la raiz no tiene padre");
        return nodo.getParent();
    }

    public Iterable<Position<E>> children(Position<E> v) {
        BTNodo<E> nodo = checkPosition(v);
        PositionList<Position<E>> lista = new ListaDoble<Position<E>>();
        lista.addLast(nodo.getLeft());             
        lista.addLast(nodo.getRight());
        return lista;
    }

    public boolean isInternal(Position<E> v) {
        BTNodo<E> nodo = checkPosition(v);
        boolean respuesta = false;
        if (nodo.getLeft() != null)
            respuesta = true;
        return respuesta;
    }




    // implementaciones de los ejercicios

    /*Agregue un método a la clase árbol binario programada en el inciso anterior tal que recorra el árbol en 
    pre-orden y retorne un diccionario donde sus entradas tengan como clave al rótulo del padre y como valor a 
    los rótulos de cada uno de sus hijos. Los rótulos ubicados en hojas del árbol no deben pertenecer 
    al diccionario, de esta forma en el diccionario no pueden existir valores nulos. */
    public Dictionary<E,E> diccionario() {
        Dictionary<E,E> d = new DiccionarioHash<E,E>();
        //recorrer en preorden y añadir al diccionario con clave del rotulo del padre y valor de su propio rotulo
        //si la posicion es interno, añadir lo de arriba

        for (Position<E> p: this.positions()) {
            BTNodo<E> n = checkPosition(p);
            if (n.getLeft() != null)
                d.insert(n.element(), n.getLeft().element());
            if (n.getRight() != null)
                d.insert(n.element(), n.getRight().element());
        }
        return d;
    }

    /*Ejercicio 3: Un árbol binario de expresión es un árbol binario donde cada uno de 
    sus nodos internos están rotulados con operadores y sus hojas con operandos (tal como
    el que se muestra en la figura). Dado un árbol binario A que representa una expresión 
    aritmética, escriba un método recursivo (es decir, no puede usar los iteradores) que 
    retorne un iterable de caracteres con la notación infija de la expresión que el 
    árbol representa */

    private Iterable<Character> arbolExpresion(BTArbol<Character>) {

    }
}
