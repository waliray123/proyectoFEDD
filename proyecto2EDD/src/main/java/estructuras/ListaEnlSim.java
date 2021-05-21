/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

/**
 *
 * @author user-ubunto
 * @param <Tipo>
 */
public class ListaEnlSim<Tipo> {

    private ListaEnlSim sig;
    private Tipo valor;

    public void add(ListaEnlSim raiz, Tipo valorAgr) {
        if (raiz == null) {
//            JOptionPane.showMessageDialog(null, "la lista esta vacia \n no se puede insertar al final");
        } else {
            ListaEnlSim nuevo = new ListaEnlSim();
            nuevo.valor = valorAgr;
            nuevo.sig = null;
            ListaEnlSim ultimo = new ListaEnlSim();
            ultimo = raiz;
            while (ultimo.sig != null) {
                ultimo = ultimo.sig;
            }
            ultimo.sig = nuevo;
        }
    }
    
    public Tipo getVal(){
        return this.valor;
    }
    
    public ListaEnlSim getSig(){
        return sig;
    }
    
    public boolean isEmpty(){        
        ListaEnlSim aux = this;
        while (aux != null) {            
            Object objErr = aux.getVal();
            if (objErr != null) {
                return false;                
            }            
            aux = aux.getSig();
        }
        return true;
    }
    
    public void remove(int index){
        int i = 0;
        ListaEnlSim ant = this;
        ListaEnlSim aux = this;
        while (aux != null) {            
            Object objErr = aux.getVal();
            if (objErr != null) {
                if (i == index) {                    
                    ant.sig = aux.sig;
                    aux.sig = null;
                    return;
                }
            }    
            i++;
            ant = aux;
            aux = aux.getSig();
        }        
    }
    
    public int size(){
        int i = 0;
        ListaEnlSim aux = this;
        while (aux != null) {            
            Object objErr = aux.getVal();
            if (objErr != null) {
                i++;              
            }            
            aux = aux.getSig();
        }
        return i;
    }
    
}
