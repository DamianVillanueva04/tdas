package ar.edu.uns.cs.ed.tdas.tdacola;

import java.util.Stack;

public class ColaConPila<E> implements Queue<E> {
    
    private Stack<E> p1;   
    private Stack<E> p2;

    public ColaConPila() {
        p1 = new Stack<>();  /* podría tmb poner Stack<E>() */
        p2 = new Stack<>();
    }

    public int size() {
        return (p1.size() + p2.size());
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public E front() {
        E aux;
        if (p1.size() > 0)
            aux = p1[0];
        else aux = p2.peek();
        return aux;
    }

    front
    enqueue
    dequeue

}