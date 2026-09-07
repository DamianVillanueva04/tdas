package ar.edu.uns.cs.ed.tdas.tdacola;

import ar.edu.uns.cs.ed.tdas.tdapila.PilaConArreglo;

public class ColaConPila implements Queue<E> {
    private int cant;
    private PilaConArreglo pila;    

    public ColaConPila(int cap) {
        pila = (E[]) new Object[cap];
        
    }
}
