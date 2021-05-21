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
public class Llave<Tipo> {
    public int llave;
    private Tipo valor;

    public Llave(int llave, Tipo valor) {
        this.llave = llave;
        this.valor = valor;
    }        

    public Tipo getValor() {
        return valor;
    }

    public void setValor(Tipo valor) {
        this.valor = valor;
    }
    
    
}
