package ar.edu.uns.cs.ed.tdas.tdadiccionario;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoble;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;
import ar.edu.uns.cs.ed.tdas.tdamapeo.Entrada;
import ar.edu.uns.cs.ed.tdas.excepciones.*;
import ar.edu.uns.cs.ed.tdas.tdaarbolbinario.BinaryTree;

public class DiccionarioHash<K,V> implements Dictionary<K,V> {

    private int size;
    private int N;
    private PositionList<Entry<K,V>> [] buckets;

    public DiccionarioHash() {
        N = 13;
        buckets = (PositionList<Entry<K,V>>[]) new PositionList[N];
        for (int i = 0; i < N; i++) 
            buckets[i] = new ListaDoble<Entry<K,V>>();
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    private int hash(K key) {
        return ((key.hashCode() & 0x7fffffff) % N);
    }

    public Entry<K,V> find(K key) {
        if (key == null) throw new InvalidKeyException("clave nula");
        Entry<K,V> toRet = null;
        int i = hash(key);
        PositionList<Entry<K,V>> l = buckets[i];
        for (Position<Entry<K,V>> p: l.positions())                         {
            if (p.element().getKey().equals(key))       {
                toRet = p.element();
                break;                                  }                   }
        return toRet;
    }   

    public Iterable<Entry<K,V>> findAll(K key) {
        if (key == null) throw new InvalidKeyException("clave nula");
        int i = hash(key);
        PositionList<Entry<K,V>> l = buckets[i];
        PositionList<Entry<K,V>> lista = new ListaDoble<Entry<K,V>>();
        for (Position<Entry<K,V>> p: l.positions()) {
            if (p.element().getKey().equals(key))
                lista.addLast(p.element());
        }
        return lista;
    }

    public Entry<K,V> insert(K key, V value) {
        if (key == null) throw new InvalidKeyException("clave nula");
        int i = hash(key);
        PositionList<Entry<K,V>> l = buckets[i];
        Entry<K,V> entrada = new Entrada<K,V>(key,value);
        l.addLast(entrada);
        size++;
        return entrada;
    }

    public Entry<K,V> remove(Entry<K,V> e) {
        if (e == null) throw new InvalidEntryException("entrada nula");
        if (e.getKey() == null) throw new InvalidKeyException("clave nula");
        int i = hash(e.getKey());
        Entry<K,V> entrada = null;
        for (Position<Entry<K,V>> p: buckets[i].positions()) {
            if (p.element() == e)             {
                entrada = buckets[i].remove(p); 
                size--; 
                return entrada;               }               }
        throw new InvalidEntryException("entrada no existente");                    
    }

    public Iterable<Entry<K,V>> entries() {
        PositionList<Entry<K,V>> lista = new ListaDoble<Entry<K,V>>();
        for (int i = 0; i < buckets.length; i++) {
            for (Position<Entry<K,V>> p: buckets[i].positions())
                lista.addLast(p.element());
        }
        return lista;
    }

    public Iterable<Entry<K,V>> eliminarTodas(K c,V v) {
        if (c == null) throw new InvalidKeyException("clave nula");
        PositionList<Entry<K,V>> listaEliminados = new ListaDoble<Entry<K,V>>();
        boolean termine = false;
        PositionList<Entry<K,V>> bucket = buckets[hash(c)];
        if (!bucket.isEmpty())                                              {
            Position<Entry<K,V>> p = bucket.first();
            Position<Entry<K,V>> ultimo = bucket.last();

            while (!termine)                              {
                Position<Entry<K,V>> sig = null;
                if (p == ultimo)
                    termine = true;
                else sig = bucket.next(p);

                if (p.element().getKey().equals(c) && p.element().getValue().equals(v))  {
                    listaEliminados.addLast(bucket.remove(p));
                    size--;                                                              }
                p = sig;                                   }                 }
        return listaEliminados;
    }

    /* creo mapa
    si es hoja
        agrego al mapa
        elimino p
        return mapa
    creo lista
    postOrden(arbol, p, lista)
    recorro la lista (solo habrian hojas)
        si (mapa.get(element()) != null)
            mapa.put(element(), mapa.get(element()+1))
        else agrego a mapa con (element(),1)
        elimino la hoja


    postOrden:
    si tiene left 
        postOrden(arbol, left, lista)
    si tiene right
        postOrden(arbol, right, lista)
    si (posicion es hoja)
        agrego a la lista
    return void
*/
}

