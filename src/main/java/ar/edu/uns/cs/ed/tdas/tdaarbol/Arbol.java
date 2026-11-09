package ar.edu.uns.cs.ed.tdas.tdaarbol;

import ar.edu.uns.cs.ed.tdas.tdaarbol.*;
import ar.edu.uns.cs.ed.tdas.tdaarbolbinario.BTNodo;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoble;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Map;
import ar.edu.uns.cs.ed.tdas.tdamapeo.MapaHash;

import java.util.Iterator;

import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.excepciones.*;

public class Arbol<E> implements Tree<E> {
    
    private TNodo<E> raiz; 
    private int size;

    public Arbol() {
        raiz = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {//8=========D
        return (size == 0);
    }

    public Iterator<E> iterator() {
        PositionList<E> lista = new ListaDoble<E>();
        for (Position<E> p: positions())
            lista.addLast(p.element());
        return lista.iterator();
    }

    public Iterable<Position<E>> positions() {
        PositionList<Position<E>> p1 = new ListaDoble<Position<E>>();
        if (size != 0)
            preOrden(raiz, p1);
        return p1;
    }

    private void preOrden(TNodo<E> r, PositionList<Position<E>> lista) {
        lista.addLast(r);
        for (TNodo<E> p: r.getHijos())
            preOrden(p, lista);
    }

    public E replace(Position<E> v, E e) {
        TNodo<E> nodo = checkPosition(v);
        E viejo = nodo.element();
        nodo.setElement(e);
        return viejo;
    }
    
    private TNodo<E> checkPosition(Position<E> v) {
        try {
            if (v == null) throw new InvalidPositionException("posicion es nula");
            if (v.element() == null) throw new InvalidPositionException("la posicion no tiene Nodo");
            return (TNodo<E>) v; 
        }
        catch (ClassCastException c) { throw new InvalidPositionException("la posicion no pertenece al arbol");}
    }

    public Position<E> root() {
        if (size == 0) throw new EmptyTreeException("el arbol esta vacio");
        return raiz;
    }

    public Position<E> parent(Position<E> v) {
        TNodo<E> nodo = checkPosition(v);
        if (nodo == raiz) throw new BoundaryViolationException("la raiz no tiene padre");
        return nodo.getPadre();
    }

    public Iterable<Position<E> > children(Position<E> v) {
        TNodo<E> nodo = checkPosition(v);
        PositionList<Position<E>> hijos = new ListaDoble<Position<E>>();
        for (Position<E> p: nodo.getHijos())
            hijos.addLast(p);
        return hijos;
    }

    public boolean isInternal(Position<E> v) {        //si tiene hijos
        TNodo<E> nodo = checkPosition(v);
        return (!nodo.getHijos().isEmpty());
    }

    public boolean isExternal(Position<E> v) {
        TNodo<E> nodo = checkPosition(v);
        return (nodo.getHijos().isEmpty());
    }

    public boolean isRoot(Position<E> v) {
        TNodo<E> nodo = checkPosition(v);
        return (nodo == raiz);
    }

    public void createRoot(E e) {
        if (raiz != null) throw new InvalidOperationException("el arbol ya tiene raiz");
        raiz = new TNodo<E>(null, e);
        size++;                           //este size++ está bien?
    }

    public Position<E> addFirstChild(Position<E> p, E e) {
        if (this.isEmpty()) throw new InvalidPositionException("arbol vacío");
        TNodo<E> padre = checkPosition(p);
        TNodo<E> hijo = new TNodo<E>(padre,e);
        padre.getHijos().addFirst(hijo);
        size++;
        return hijo;                        
        }

    public Position<E> addLastChild(Position<E> p, E e) {
        if (this.isEmpty()) throw new InvalidPositionException("arbol vacío");
        TNodo<E> padre = checkPosition(p);
        TNodo<E> hijo = new TNodo<E>(padre,e);
        padre.getHijos().addLast(hijo);
        size++;
        return hijo;
    }

    private Position<TNodo<E>> buscarEnHijos(TNodo<E> padre, TNodo<E> hijoABuscar) {
        checkPosition(padre); checkPosition(hijoABuscar); 
        for (Position<TNodo<E>> pos: padre.getHijos().positions())
            if (pos.element() == hijoABuscar)
                return pos;
        return null;
    }
    
    public Position<E> addBefore(Position<E> p, Position<E> rb, E e) {
        if (this.isEmpty()) throw new InvalidPositionException("arbol vacío");
        TNodo<E> padre = checkPosition(p);
        TNodo<E> hermanoDer = checkPosition(rb);
        TNodo<E> hijo = new TNodo<E>(padre,e);
        Position<TNodo<E>> pos = buscarEnHijos(padre,hermanoDer);
        if (pos == null) throw new InvalidPositionException("rb no es hijo de p");
        padre.getHijos().addBefore(pos,hijo);
        size++;
        return hijo;
    }

    public Position<E> addAfter(Position<E> p, Position<E> lb, E e) {
        if (this.isEmpty()) throw new InvalidPositionException("arbol vacío");
        TNodo<E> padre = checkPosition(p);
        TNodo<E> hermanoIzq = checkPosition(lb);
        TNodo<E> hijo = new TNodo<E>(padre,e);
        Position<TNodo<E>> pos = buscarEnHijos(padre,hermanoIzq);
        if (pos == null) throw new InvalidPositionException("lb no es hijo de p");   //creo que falta tirar excepcion en otras situacions
        padre.getHijos().addAfter(pos, hijo);
        size++;
        return hijo;
    }

    public void removeExternalNode(Position<E> p) {

        TNodo<E> nodo = checkPosition(p);

        if ((this.isEmpty()) || (this.isInternal(nodo)))
            throw new InvalidPositionException("el arbol está vacio o el nodo es interno");

        if (size == 1)       {    //caso 2: en el arbol solo hay raiz y hay que eliminarla
            raiz = null;
            size--;          }

        else {                   //caso 4: hay que remover un nodo externo

            TNodo<E> padre = nodo.getPadre();
            PositionList<TNodo<E>> hPadre = padre.getHijos();
            Position<TNodo<E>> posDeN;
            Position<TNodo<E>> cursor = hPadre.first();

            while ((cursor != null) && (cursor.element() != nodo))                  {
                if (cursor == hPadre.last())
                    cursor = null;
                else
                    cursor = hPadre.next(cursor);                                   }
            if (cursor != null)
                posDeN = cursor;
            else throw new InvalidPositionException("la estructura no corresponde a un árbol valido");

            hPadre.remove(posDeN);                
            size--;
        }
    }

    public void removeInternalNode(Position<E> p) {
        TNodo<E> nodo = checkPosition(p);
        if ((this.isEmpty()) || (!this.isInternal(nodo)))
            throw new InvalidPositionException("el arbol esta vacio o el nodo es externo");

        if ((nodo == raiz) && (nodo.getHijos().size() != 1))                     //caso 3
            throw new InvalidPositionException("para eliminar una raiz, esta debe tener un solo hijo maximo");
        
        if (nodo == raiz)      {                        //caso 1
            PositionList<TNodo<E>> listaHijos = nodo.getHijos();
            TNodo<E> hijo = listaHijos.first().element();
            hijo.setPadre(null);
            raiz = hijo;
            size--;            }

        else {                                   //caso 4 con nodo interno
            TNodo<E> padre = nodo.getPadre();
            PositionList<TNodo<E>> hPadre = padre.getHijos();
            PositionList<TNodo<E>> hNodo = nodo.getHijos();
            Position<TNodo<E>> cursor = hPadre.first();
            Position<TNodo<E>> posDeN;
            while ((cursor != null) && (cursor.element() != nodo))       {
                if (cursor == hPadre.last())
                    cursor = null;
                else cursor = hPadre.next(cursor);                            }

            if (cursor == null)
                throw new InvalidPositionException("la estructura no corresponde a un árbol valido");
            else                                               {
                posDeN = cursor;
                while (!hNodo.isEmpty())                                          { 
                    Position<TNodo<E>> primero = hNodo.first();                   
                    primero.element().setPadre(padre);                           
                    hPadre.addBefore(posDeN,hNodo.remove(primero));               }

                hPadre.remove(posDeN);
                size--;
                                                              }
        }
    }

    public void removeNode(Position<E> p) {
        checkPosition(p);
        if (this.isExternal(p))
            this.removeExternalNode(p);
        else if (this.isInternal(p))
            this.removeInternalNode(p);
    }

    public void eliminarUltimoHijo(Position<E> p) {
        TNodo<E> nodo = checkPosition(p);
        if (nodo == raiz) throw new InvalidOperationException("una raiz no se puede considerar ultimo hijo");
        TNodo<E> padre = nodo.getPadre();
        PositionList<TNodo<E>> hPadre = padre.getHijos();
        TNodo<E> ultimo = hPadre.last().element();
        if (ultimo == nodo)   {
            PositionList<TNodo<E>> hijosNodo = nodo.getHijos();
            if (!hijosNodo.isEmpty()) {
                Position<TNodo<E>> posUlt = hPadre.last();
                for (TNodo<E> cursor: hijosNodo) {
                    hPadre.addBefore(posUlt,cursor);
                    cursor.setPadre(padre);
                }
            }
            hPadre.remove(hPadre.last());
            nodo.setPadre(null);
            size--;       }
    }

    /*Este método deberá retornar un mapeo con cada uno de los
    caracteres del árbol y la cantidad de veces que aparece cada carácter en el árbol. Resuelva este problema utilizando
    un recorrido en preorden.
    */
    public Map<Character, Integer> cantidadRepeticiones(Tree<Character> t) {
        MapaHash<Character, Integer> mapa = new MapaHash<Character, Integer>();
        if (!t.isEmpty())                
            preOrdenMapa(t,mapa,t.root());
        return mapa;                   
    }


     private void preOrdenMapa(Tree<Character> t, MapaHash<Character,Integer> mapa, Position<Character> pos) {
        Character c = pos.element();

        Integer i = mapa.get(c);
        if (i == null)
            mapa.put(c,1);
        else
            mapa.put(c,i+1);

        for (Position<Character> p: t.children(pos))
            preOrdenMapa(t,mapa,p);
     }

     /*Dado un árbol a de Strings y un String s, programe un método tal que retorne un 
     Iterable con las posiciones del árbol en las que aparece el String s. Para resolver 
     este problema implemente un recorrido en postorden. */

     public Iterable<Position<String>> aparicionesDeS(Tree<String> a, String s) {
        PositionList<Position<String>> lista = new ListaDoble<Position<String>>();
        if (!a.isEmpty())
            postOrden(a,a.root(),s,lista);
        return lista;
     }

     private void postOrden(Tree<String> t, Position<String> p, String s, PositionList<Position<String>> lista) {
        //visito hijos
        for (Position<String> h: t.children(p))
            postOrden(t,h,s,lista);

        if (p.element().equals(s))
            lista.addLast(p);
    }


    private int eliminarElemento(Tree<E> a, E elem) {
        int cantEliminados = 0;
        if (!a.isEmpty()) {
            PositionList<Position<E>> lista = new ListaDoble<Position<E>>();
            postOrdenEliminar(a,elem,a.root(),lista);

            //recorremos la lista de los nodos a eliminar
            for (Position<E> p: lista) {
                a.removeNode(p);
                cantEliminados++;  }
        }
        return cantEliminados;
    }

    private void postOrdenEliminar(Tree<E> t, E e, Position<E> pos, PositionList<Position<E>> lista) {
        for (Position<E> h: t.children(pos))
            postOrdenEliminar(t,e,h,lista);

        if (pos.element().equals(e))
            lista.addLast(pos);
    }


    private boolean perteneceN(Tree<Integer> a, Integer n) {
        boolean encontre = false;
        Iterator<Integer> it = a.iterator();
        while ((!encontre) && (it.hasNext())) {
            if (it.next() == n)
                encontre = true; 
            }
        return encontre;
    }


    /* Suponiendo que cuenta con una clase Arbol<E> que implementa la
    interfaz Tree<E> vista en clase. Esta clase utiliza la
    implementación de lista de hijos y enlace al padre. Agregue un
    método a esta clase cuya signatura sea public int
    sizeSubarbol(Position<E> p) throws InvalidPositionException. Este
    método deberá retornar el tamaño del subárbol con raíz p del
    árbol receptor del mensaje. Si utiliza otros métodos del TDA
    Arbol deberá implementarlos. */

    public int sizeSubarbol(Position<E> p) {
        TNodo<E> nodo = checkPosition(p);
        int contador = 0;
        if (nodo == raiz) {
            contador = size;
            return contador; }
        PositionList<Position<E>> lista = new ListaDoble<Position<E>>();
        postOrden(nodo,lista);
        for (Position<E> pos: lista) {
            contador++; }
        return contador;
    }

    private void postOrden(Position<E> p, PositionList<Position<E>> l) {
        for (Position<E> pos: children(p))
            postOrden(pos,l);
        l.addLast(p);
    } 

    
    /* Ejercicio: resuelva nuevamente este ejercicio pero desde fuera
    de la ED. 

    private int sizeSubarbol(Tree<E> t, Position<E> p) {
        TNodo<E> nodo = checkPosition(p);
        int cont = 1;
        for (Position<E> pos: t.children(p))
            cont = (cont + sizeSubarbol(t,pos));
        return cont;
    }

    private TNodo<E> checkPosition(Position<E> p) {
        try {
            if (p == null) throw new InvalidPositionException("la posicion esta vacia");
            if (p.element() == null) throw new InvalidPositionException("el nodo esta vacio");
            return (TNodo<E>) p;
        } 
        catch (ClassCastException c) { throw new InvalidPositionException("la posicion no pertenece al arbol");
                                    }
    } */


    

    
    /* Suponiendo que cuenta con una clase Arbol<E> que implementa la
    interfaz Tree<E> vista en clase. Esta clase utiliza la
    implementación de lista de hijos y enlace al padre. Agregue un
    método a esta clase cuya signatura sea public Map<Position<E>,
    Integer> mapSizeSubarboles( ) throws InvalidPositionException.
    Este método deberá retornar un mapeo entre posiciones del árbol
    y el tamaño del subárbol que tiene a esa posición como raíz. Si
    utiliza otros métodos del TDA Arbol deberá implementarlos.  */

    public Map<Position<E>,Integer> mapSizeSubarboles() {
        Map<Position<E>,Integer> mapa = new MapaHash<Position<E>,Integer>();
        calcular(raiz,mapa);
        return mapa;
    }

    private Integer calcular(Position<E> p, Map<Position<E>,Integer> m) {
        int resultado = 1;
        for (Position<E> pos: children(p))
            resultado = calcular(pos,m)+1;
        m.put(p,resultado);
        return resultado;
    }


    /* Suponiendo que cuenta con una clase Arbol<E> que implementa la
    interfaz Tree<E> vista en clase. Esta clase utiliza la
    implementación de lista de hijos y enlace al padre. Agregue un
    método a esta clase cuya signatura sea public int
    podarSubarbol(Position<E> p) throws InvalidPositionException.
    Este método deberá eliminar el subarbol con raíz p del árbol
    receptor del mensaje. Retorna el tamaño del subárbol eliminado.
    Si utiliza otros métodos del TDA Arbol deberá implementarlos. */

    public int podarSubarbol(Position<E> p) {
        TNodo<E> nodoRaiz = checkPosition(p);
        int resultado = postOrdenExtra(nodoRaiz);
        return resultado;
    }

    private int postOrdenExtra(Position<E> p) {
        int contador = 1;
        for (Position<E> hijo: children(p))
            contador += postOrdenExtra(hijo);
        removeNode(p);
        return contador;
    }

    public boolean eliminarUltimo(Position<E> p) throws InvalidPositionException {
    TNodo<E> n = checkPosition(p);
    TNodo<E> padre = n.getPadre();

    if (padre == null)                          // es la raíz: no es hijo de nadie
        return false;

    PositionList<TNodo<E>> hijosPadre = padre.getHijos();
    Position<TNodo<E>> ultimo = hijosPadre.last();
    if (ultimo.element() != n)                  // no es el último hijo
        return false;

    hijosPadre.remove(ultimo);                  // saco a n de la lista de su padre

    for (TNodo<E> h : n.getHijos()) {           // sus hijos ocupan su lugar (el final), en orden
        h.setPadre(padre);
        hijosPadre.addLast(h);
    }
    n.setElemento(null);                        // invalido el nodo eliminado
    n.setPadre(null);
    n.setHijos(null);
    size--;
    return true;
}
}