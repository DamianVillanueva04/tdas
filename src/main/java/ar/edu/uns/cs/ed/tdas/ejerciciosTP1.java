 public interface Conjunto<E> {
    public int size();
    public int capacity();
    public boolean isEmpty();
    public E get(int i);
    public void put(E elem);
    public boolean pertenece(E elem);
    public Conjunto<E> interseccion(Conjunto<E> c);
}



public class ConjuntoArreglo<E> implements Conjunto<E> {
    protected E[] arreglo;
    protected int cant;

    public ConjuntoArreglo(int cap) {
        arreglo = (E[]) new Object[cap];
        cant = 0;
    }

    public int size() {
        return cant;
    }

    public int capacity() {
        return arreglo.length;
    }

    public boolean isEmpty() {
        return cant == 0;
    }

    public E get(int i){
        return arreglo[i];
    }

    public void put(E elem) {
        arreglo[cant] = elem;
        cant++;
    }

    public boolean pertenece(E elem) {
        boolean existe = false;
        for (int i = 0; (i < cant) && (!existe); i++) {
            if (arreglo[i].equals(elem)) {
                existe = true;
            }
        }
        return existe;  }

        public Conjunto<E> interseccion(Conjunto<E> c) {
            int menorConj = 0;         //para recorrer lo menos posible
            if (this.size() >= c.size())
                menorConj = c.size();
            else menorConj = this.size();
            Conjunto<E> resultado = new ConjuntoArreglo<E>(menorConj);   //se crea un nuevo conjunto
            for(int i = 0; i < this.size(); i++) {                       // se recorre el conjunto que recibe el msj
                if (c.pertenece(this.get(i))) {
                    resultado.put(get(i));
                }
            }
            return resultado;
        }
    }



