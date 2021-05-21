/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import nodos.NodoListDob;

/**
 *
 * @author user-ubunto
 * @param <Tipo>
 */
public class ListaDobEnl<Tipo> {

    private NodoListDob<Tipo> raiz;

    public ListaDobEnl() {
        this.raiz = null;
    }

    public void insertar(Tipo info) {
        if (raiz == null) {
            raiz = new NodoListDob<>(info);
        } else {
            NodoListDob<Tipo> nuevo = new NodoListDob<>(info);
            NodoListDob<Tipo> ultimo = raiz;
            while (ultimo.getSig() != null) {
                ultimo = ultimo.getSig();
            }
            ultimo.setSig(nuevo);
            nuevo.setAnt(ultimo);
        }
    }

    public void remove(int index) {
        int i = 0;
        NodoListDob aux = this.raiz;
        while (aux != null) {
            Object objErr = aux.getInfo();
            if (objErr != null) {
                if (i == index) {
                    if (index == 0) {
                        NodoListDob aux2 = this.raiz;
                        this.raiz = this.raiz.getSig();
                        aux2.setAnt(null);
                        aux2.setSig(null);
                    } else {
                        NodoListDob ant = aux.getAnt();
                        ant.setSig(aux.getSig());
                        if (aux.getSig() != null) {
                            aux.getSig().setAnt(ant);
                        }
                        aux.setAnt(null);
                        aux.setSig(null);
                    }
                    return;
                }
            }
            i++;
            aux = aux.getSig();
        }
    }
    
    public boolean remove(Tipo valorE){        
        NodoListDob<Tipo> ultimo = raiz;
        int cont = 0;
        while (ultimo != null) {
            Tipo infoE = ultimo.getInfo();
            if (infoE != null) {                
                if (infoE == valorE) {
                    //return usr;
                    remove(cont);
                    return true;                    
                }
            }
            cont++;
            ultimo = ultimo.getSig();
        }        
        return false;
    }

    public NodoListDob<Tipo> getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoListDob<Tipo> raiz) {
        this.raiz = raiz;
    }

}
