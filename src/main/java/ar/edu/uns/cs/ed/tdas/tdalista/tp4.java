package ar.edu.uns.cs.ed.tdas.tdalista;   // o el paquete que uses

import java.util.Iterator;
import ar.edu.uns.cs.ed.tdas.Position;

public class TP4 {

/* public static<E> void ejercicio2(E e1, E e2) {
        if (size == 0)       {
            addFirst(e2);
            addLast(e1);        }
        else if (size == 1)     este caso queda contemplado en el else
            addAfter(,e2);   
            addBefore(,e1);        
        else                                     {
            addAfter(header.getNext(), e1);
            addBefore(trailer.getPrev(), e2);    }
    }                                                tiempo de ejecucion constante  

                     esta hecho con header, tail, etc. así que lo dejé en ListaDoble */

    public static<E> boolean estaEnLista(E e, PositionList<E> l) {
        boolean encontre = false;
        Iterator<E> it = l.iterator();
        while (it.hasNext() && !encontre) {
            E elem = it.next();
            if (e.equals(elem))
                encontre = true;          }
        return encontre;
    }

    public static<E> int cantAparicionesdeE(PositionList<E> l, E e) {
        int cant = 0;
        Iterator<E> it = l.iterator();
        while (it.hasNext())              {
            E elem = it.next();
            if (e.equals(elem))
                cant++;                   }
        return cant;
    }

    public static<E> boolean alMenosN(PositionList<E> l, E x, int n) {
        boolean resultado = (n <= 0);   //si n = 0 ya da true;
        int cont = 0;
        Iterator<E> it = l.iterator();
        while (it.hasNext() && !resultado)                 {
            E elem = it.next();
            if (elem == x)                 {
                cont++;
                if (cont == n)
                    resultado = true;           }           }
        return resultado;
    }

    public static <E> PositionList<E> duplicar(PositionList<E> l) {
        PositionList<E> nueva = new ListaDoble<E>();
        for (E elem : l)                 {
            nueva.addLast(elem);
            nueva.addLast(elem);         }
        return nueva;
    }

    public static Iterable<Character> eliminarComunes(PositionList<Character> l1, PositionList<Character> l2) {
        PositionList<Character> eliminados = new ListaDoble<Character>();
        for (Position<Character> p : l2.positions())          {
            if (estaEnLista(p.element(), l1))
                eliminados.addLast(l2.remove(p));             }
        return eliminados;
    }

    public <E> PositionList<E> intercalar(PositionList<E> l1, PositionList<E> l2) {
        PositionList<E> Lnueva = new ListaDoble<E>();
        Iterator<E> it1 = l1.iterator();
        Iterator<E> it2 = l2.iterator();

        while (it1.hasNext() && it2.hasNext())   {
            Lnueva.addLast(it1.next());
            Lnueva.addLast(it2.next());        }
        
        while (it1.hasNext())
            Lnueva.addLast(it1.next());

        while (it2.hasNext())
            Lnueva.addLast(it2.next());

        return Lnueva;
    }

    public static PositionList<Integer> intercalarOrdenada(PositionList<Integer> l1, PositionList<Integer> l2) {
        PositionList<Integer> Lnueva = new ListaDoble<Integer>();
        Iterator<Integer> it1 = l1.iterator();
        Iterator<Integer> it2 = l2.iterator();
        int a = 0; int b = 0;
        boolean hayA = false; boolean hayB = false;

        if (it1.hasNext()) {
            hayA = true;
            a = it1.next();        }

        if (it2.hasNext()) {
            hayB = true;
            b = it2.next();        }

        while (hayA || hayB)                                                           {
            int menor;
            if (hayA && (!hayB || (a < b)))           {
                menor = a;
                if (it1.hasNext()) 
                    a = it1.next();
                else 
                    hayA = false;                      }

            else                              {
                menor = b;
                if (it2.hasNext())
                    b = it2.next();
                else 
                    hayB = false;             }

            if (Lnueva.isEmpty() || !Lnueva.last().element().equals(menor))
                Lnueva.addLast(menor);                                                  }

        return Lnueva;     }

    public <E> void eliminar(PositionList<E> l1, PositionList<E> l2) {
        for (Position<E> p: l1.positions())        {
            if (estaEnLista(p.element(), l2))
                l1.remove(p);                      }
        
        if (!l2.isEmpty())                                 {
            Position<E> pos = l2.last();
            boolean termine = false;
            while (!termine)                     {
                l1.addLast(pos.element());
                if (pos == l2.first())
                    termine = true;
                else 
                    pos = l2.prev(pos);               }        }         }

}