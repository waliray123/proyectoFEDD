/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodos;

/**
 *
 * @author user-ubunto
 * @param <Tipo>
 */
public class NodoListDob<Tipo> {
    private NodoListDob<Tipo> ant;
    private NodoListDob<Tipo> sig;
    private Tipo info;

    public NodoListDob(Tipo info) {
        this.ant = null;
        this.sig = null;
        this.info = info;
    }        

    public NodoListDob<Tipo> getAnt() {
        return ant;
    }

    public void setAnt(NodoListDob<Tipo> ant) {
        this.ant = ant;
    }

    public NodoListDob<Tipo> getSig() {
        return sig;
    }

    public void setSig(NodoListDob<Tipo> sig) {
        this.sig = sig;
    }

    public Tipo getInfo() {
        return info;
    }

    public void setInfo(Tipo info) {
        this.info = info;
    }
    
    
}
