package ar.edu.uns.cs.ed.tdas.tdamapeo;

import ar.edu.uns.cs.ed.tdas.Entry;

public class Entrada<K,V> implements Entry<K,V> {

    private K clave;
    private V valor;

    public Entrada(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getKey() {
        return clave;
    }

    public V getValue() {
        return valor;
    }

    public void setKey(K clave) {
        this.clave = clave;
    }

    public void setValue(V valor) {
        this.valor = valor;
    }

    public String toString() {
        return "(" + getKey() + "," + getValue() + ")";
    }
}