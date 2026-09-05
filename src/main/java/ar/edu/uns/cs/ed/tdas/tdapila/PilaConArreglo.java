package ar.edu.uns.cs.ed.tdas.tdapila;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyStackException;

public class PilaConArreglo<E> implements Stack<E>{

    private int cant;
    private E[] arreglo;

    public PilaConArreglo(int cap) {
        arreglo = (E[]) new Object[cap];
        cant = 0;
    }

    public PilaConArreglo() {
        arreglo = (E[]) new Object[100];
        cant = 0;
    } 

    public int size() {
        return cant;
    }

    public boolean isEmpty() {
        return (cant == 0);
    }

    public E top() {
        if (isEmpty())
            throw new EmptyStackException("La pila está vacía");
        return arreglo[cant-1];
    }


    public void push(E element) {
        if (cant != arreglo.length) {
            arreglo[cant] = element;
            cant++;                 }
        else        {       //cant = cap (length)
            E[] nuevoArreglo = (E[]) new Object[2*arreglo.length];
                for (int i=0; i < arreglo.length; i++) 
                    nuevoArreglo[i] = arreglo[i];
                arreglo = nuevoArreglo;
            arreglo[cant] = element;
            cant++; }
    }

    public E pop() {
        if (isEmpty())
            throw new EmptyStackException("No se puede sacar elementos de una pila vacia");
        E aux = arreglo[cant-1];
        arreglo[cant-1] = null;
        cant--;
        return aux;                 
    }
}