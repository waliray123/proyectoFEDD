/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodos;

/**
 *
 * @author user-ubunto
 */
public class NodoHash<Tipo> {

    //Y este nodo hash es el que contiene los valores y el estado en la tabla
    //Ponete el nombre es el carnet pero ya que hueva cambiarlo
    //Y el estado es 'a' si esta disponible o 'b' si esta cerrado 
    private char estado;
    private String nombre;
    private Tipo valor;

//    public NodoHash() {
//        
//    }

    /**
     * Constructor NodoHash
     * @param nombre
     * @param valor 
     */
    public NodoHash(String nombre, Tipo valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getValor() {
        return valor;
    }

    public void setValor(Tipo valor) {
        this.valor = valor;
    }

}
