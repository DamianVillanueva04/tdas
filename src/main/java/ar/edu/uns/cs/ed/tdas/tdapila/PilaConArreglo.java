public class PilaConArreglo<E> implements Stack<E>{

    private int cant;
    private E[] arreglo;

    public PilaConArreglo(int cap) {
        arreglo = (E[]) new Object[cap];
        cant = 0;
    }

    public int size() {
        return cant;
    }

    public boolean isEmpty() {
        return (cant == 0);
    }

    public E top() {
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
            Throws new EmptyStackException("No se puede sacar elementos de una pila vacia");
        


       /*  private E aux = (E) new Object();
        aux = arreglo[cant-1];
        arreglo[cant-1] = null;
        return aux; */
    }
    
}