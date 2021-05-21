/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import nodos.NodoB;

/**
 *
 * @author user-ubunto
 * @param <Tipo>
 */
public class ArbolB<Tipo> {
    private NodoB<Tipo> raiz;
    private int alturaTotal;
    
    

    public NodoB getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoB raiz) {
        this.raiz = raiz;
    }
    
    
}
