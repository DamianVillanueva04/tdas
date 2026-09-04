import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public static void Invertir(Persona[] A) {
    if (A == null || A.length == 0)    //si el arreglo esta vacio no hace nada
        return;
    else {
        Stack<Persona> pila = new Stack<>();   //creamos la pila
        for (int i=0; i < A.length; i++) {
            pila.push(A[i]);
                                          }
        for (int i=0; i < A.length; i++) {
            A[i] = pila.peek();                //podria haber usado pop directo,
            pila.pop();                    //el pop de java devuelve el objeto y
        }                                  // despues lo elimina
                                           
}                                         
}

public Queue<Integer> devolverImpares(Queue<Integer> cola) {  

    Queue<Integer> colaImpares = new LinkedList<>(); //en linkedlist podria poner integer pero no hace falta
    while (!cola.isEmpty()) {
        int actual = cola.poll();
        if ((actual % 2) != 0)
            colaImpares.add(actual);
}
    return colaImpares;
}

public Stack<Integer> intercalar(Stack<Integer> p1, Stack<Integer> p2) {
    int masGrande = 0;

    Stack<Integer> p3 = new Stack<>();

    if (p1.size() >= p2.size()) masGrande = p1.size();
    else masGrande = p2.size();

    for(int i=0; i < masGrande; i++) {
        if (!p1.isEmpty())
            p3.push(p1.pop());
        if (!p2.isEmpty())
            p3.push(p2.pop());        }
        
    return p3;
                                              }

public Integer mayorValorDe(Queue<Integer> q) {
    if (!q.isEmpty()) {
        int numMasGrande = q.peek();
        int cant = q.size();
        Queue<Integer> colaAux = new LinkedList<>();

        for (int i=0; i < cant; i++) {
            if (numMasGrande < q.peek())
                numMasGrande = q.peek();
            colaAux.add(q.poll()); }
        for (int i=0; i < cant; i++) {
            q.add(colaAux.poll()); }
        return numMasGrande;
    }
    return null;
}
