package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.excepciones.*;
import ar.edu.uns.cs.ed.tdas.Entry;
import ar.edu.uns.cs.ed.tdas.Position;
import ar.edu.uns.cs.ed.tdas.tdalista.ListaDoble;
import ar.edu.uns.cs.ed.tdas.tdalista.PositionList;

public class MapaHash<K,V> implements Map<K,V> {
    
    private int N;             //cantidad de buckets
    private int size;   
    private PositionList<Entry<K,V>> [] buckets;

    public MapaHash() {
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


    public V get(K key) {
        if (key == null) throw new InvalidKeyException("clave nula");
        int i = hash(key);
        PositionList<Entry<K,V>> l = buckets[i];
        V toRet = null;
        for (Entry<K,V> e: l) {
            if (e.getKey().equals(key)) {
                toRet = e.getValue();
                break; } }
        return toRet;
    }

    private int hash(K key) {
        return ((key.hashCode() & 0x7fffffff) % N);
    }

    public V put(K key, V value) {
        if (key == null) throw new InvalidKeyException("clave nula");
        int i = hash(key);
        PositionList<Entry<K,V>> bucket = buckets[i];
        for (Position<Entry<K,V>> p: bucket.positions())               {
            Entry<K,V> e = p.element();
            if (e.getKey().equals(key))    {
                V viejo = e.getValue();
                bucket.set(p, new Entrada<K,V>(key,value));
                return viejo;              }                            }
        bucket.addLast(new Entrada<K,V>(key,value));
        size++;
        return null;
    }

    public V remove(K key) {
        if (key == null) throw new InvalidKeyException("clave nula");
        int i = hash(key);
        PositionList<Entry<K,V>> bucket = buckets[i];
        for (Position<Entry<K,V>> p: bucket.positions())                   {
            Entry<K,V> e = p.element();
            V toRet = null;
            if (e.getKey().equals(key))                {
                toRet = e.getValue();
                size--;
                bucket.remove(p);
                return toRet;                          }                    }
            return null;
        }
    
    public Iterable<K> keys() {
        ListaDoble<K> lista = new ListaDoble<K>();
        for (int i = 0; i < buckets.length; i++)                       {            //recorremos cada bucket
            PositionList<Entry<K,V>> l = buckets[i];
            for (Position<Entry<K,V>> p: l.positions())         {                  //recorremos cada lista
                lista.addLast(p.element().getKey());            }      }
        return lista;
    }

    public Iterable<V> values() {
        ListaDoble<V> lista = new ListaDoble<V>();
        for (int i = 0; i < buckets.length; i++)                              {
            PositionList<Entry<K,V>> l = buckets[i];
            for (Position<Entry<K,V>> p: l.positions())              {
                lista.addLast(p.element().getValue());               }        }
        return lista;
    }

    public Iterable<Entry<K,V>> entries() {
        ListaDoble<Entry<K,V>> lista = new ListaDoble<Entry<K,V>>(); 
        for (int i = 0; i < buckets.length; i++)                              {
            PositionList<Entry<K,V>> l = buckets[i];
            for (Position<Entry<K,V>> p: l.positions())             {
                lista.addLast(p.element());                         }         }
        return lista;
    }
    }
