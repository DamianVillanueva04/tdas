package ar.edu.uns.cs.ed.tdas.tdacola;

import ar.edu.uns.cs.ed.tdas.excepciones.EmptyQueueException;


public class ColaConArreglo<E> implements Queue<E> {
    private int cant;
    private E[] arreglo;

    public ColaConArreglo(int cap) {
        arreglo = (E[]) new Object[cap];
        cant = 0;
    }

    public ColaConArreglo() {
        arreglo = (E[]) new Object[100];
        cant = 0;
    }

    public int size() {
        return cant;
    }

    public boolean isEmpty() {
        return (cant == 0);
    }

    public E front() {
        if (cant == 0)
            throw new EmptyQueueException("La cola está vacía");
        return arreglo[0];
    }

    public void enqueue(E elem) {
        if (arreglo.length == cant)             {
            E[] nuevo = (E[]) new Object[arreglo.length * 2];
            arreglo = nuevo;                    }
        arreglo[cant] = elem;
        cant++;
    }

    public E dequeue() {
        if (cant == 0)
            throw new EmptyQueueException("No se puede sacar a una cola vacía");
        E aux = front();
        for (int i=0; i < cant-1; i++)
            arreglo[i] = arreglo[i+1];
        cant--;
        return aux;
    }
}