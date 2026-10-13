package ar.edu.uns.cs.ed.tdas.tdamapeo;

import java.security.InvalidKeyException;

public class MapaHash<K,V> implements Map<K,V> {
    
    private int N;
    private int n;
    private PositionList<Entrada<K,V>> [] A;

    private static final double fc = 0.7;

    public MapaHash<K,V>() {
        N = nextPrimo(N);
        n = 0;
        A = new PositionList<> [N];
        for (int i = 0; i < A)
    }

    public int size() {
        return A.length;
    }

    public boolean isEmpty() {
        return (size() == 0);
    }

    public V get(K key) {
    if (key == null) throw new InvalidKeyException("clave nula");
    int i = hash(key);
    PositionList<Entrada<K,V>> l = A[i];
    V toRet = null;
    for (Entrada<K,V> e: l)                             {
        if (e.getKey().equals(key))         {
            toRet = e.getValue();        //o es l.getValue()
            break;                          }             } 
    return toRet;
    }

    private int hash(K key) {
        return mathAbs(key.hashCode() % N);
    }


}
