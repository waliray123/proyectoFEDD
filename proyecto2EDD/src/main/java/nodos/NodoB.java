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
public class NodoB<Tipo> {
    
    
    public int n;
    public Llave<Tipo> key[];
    public NodoB<Tipo> child[];
    public boolean leaf = true;
    public Tipo valor;

    public NodoB(int T) {
        this.key = new Llave[2 * T - 1];
        this.child = new NodoB[2 * T];
    }

    public int Find(int k) {
        for (int i = 0; i < this.n; i++) {
            if (this.key[i].llave == k) {
                return i;
            }
        }
        return -1;
    }

    public Tipo getValor() {
        return valor;
    }

    public void setValor(Tipo valor) {
        this.valor = valor;
    }
    
    

}
