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
public class NodoAvl<Tipo> {
    private String clave;
    private NodoAvl<Tipo> der;
    private NodoAvl<Tipo> izq;
    private Tipo info;
    int factorEquilibrio;

    public NodoAvl(String clave, Tipo info) {
        this.clave = clave;
        this.info = info;
        this.izq = null;
        this.der = null;
    }        

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


    public NodoAvl<Tipo> getDer() {
        return der;
    }

    public void setDer(NodoAvl<Tipo> der) {
        this.der = der;
    }

    public NodoAvl<Tipo> getIzq() {
        return izq;
    }

    public void setIzq(NodoAvl<Tipo> izq) {
        this.izq = izq;
    }

    public Tipo getInfo() {
        return info;
    }

    public void setInfo(Tipo info) {
        this.info = info;
    }

    public int getFactorEquilibrio() {
        return factorEquilibrio;
    }

    public void setFactorEquilibrio(int factorEquilibrio) {
        this.factorEquilibrio = factorEquilibrio;
    }
            
}
