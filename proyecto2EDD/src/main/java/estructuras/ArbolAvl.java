/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import nodos.NodoAvl;

/**
 *
 * @author user-ubunto
 */
public class ArbolAvl<Tipo> {
    NodoAvl<Tipo> raiz;
    public int size;

    public ArbolAvl() {
        this.raiz = null;
        size = 0;
    }
    
    public NodoAvl<Tipo> buscar(String id){
        return buscarNodo(id,raiz);
    }
    
    private NodoAvl<Tipo> buscarNodo(String id, NodoAvl<Tipo> raiz){        
        if (raiz == null) {
            return null;
        }if (id.compareTo(raiz.getClave()) == 0) {
            return raiz;
        }
        if (id.compareTo(raiz.getClave()) >= 1) {
            return buscarNodo(id,raiz.getDer());
        }
        if (id.compareTo(raiz.getClave()) <= -1) {
            return buscarNodo(id,raiz.getIzq());
        }
        return null;
    }
    
    public NodoAvl<Tipo> insertarAvl(NodoAvl<Tipo> nuevo,NodoAvl<Tipo> subAr){
        NodoAvl<Tipo> nuevoPadre = subAr;
//        if (nuevo.getClave().compareTo(subAr.getClave()) < 0) {
//        }else if(nuevo.getClave().compareTo(subAr.getClave()) > 0){        }
//            Integer.parseInt(nuevo.getClave())<Integer.parseInt(subAr.getClave());
        if (nuevo.getClave().compareTo(subAr.getClave()) < 0) {
            //Llenar de lado izquierdo
            if (subAr.getIzq() == null) {
                subAr.setIzq(nuevo);
            }else{
                subAr.setIzq(insertarAvl(nuevo,subAr.getIzq()));
                
                if ((obtenerFe(subAr.getIzq()) - obtenerFe(subAr.getDer())) == 2) {
                    if (nuevo.getClave().compareTo(subAr.getIzq().getClave()) == -1) {
                        nuevoPadre = rotacionIzquierda(subAr);
                    }else{
                        nuevoPadre = rotacionDerecha(subAr);
                    }
                }
            }
        }else if(nuevo.getClave().compareTo(subAr.getClave()) > 0){
            //Llenar a la derecha
            if (subAr.getDer() == null) {
                subAr.setDer(nuevo);
            }else{
                subAr.setDer(insertarAvl(nuevo,subAr.getDer()));
                
                if ((obtenerFe(subAr.getDer()) - obtenerFe(subAr.getIzq())) == 2) {
                    if (nuevo.getClave().compareTo(subAr.getDer().getClave()) == -1) {
                        nuevoPadre = rotacionIzquierda(subAr);
                    }else{
                        nuevoPadre = rotacionDerecha(subAr);
                    }
                }
            }
        }else{
            System.out.println("nodo Duplicado");
        }
        if (subAr.getIzq() == null && subAr.getDer() != null) {
            subAr.setFactorEquilibrio(subAr.getDer().getFactorEquilibrio() + 1);
        }else if(subAr.getDer() == null && subAr.getIzq() != null){
            subAr.setFactorEquilibrio(subAr.getIzq().getFactorEquilibrio() + 1);
        }else{
            subAr.setFactorEquilibrio(Math.max(obtenerFe(subAr.getIzq()),obtenerFe(subAr.getDer())) + 1);
        }
        
        return nuevoPadre;
        
    }        
    
    public NodoAvl<Tipo> rotacionIzquierda(NodoAvl<Tipo> c){
        NodoAvl<Tipo> aux = c.getIzq();
        c.setIzq(aux.getDer());
        aux.setDer(c);
        c.setFactorEquilibrio(Math.max(obtenerFe(c.getIzq()), obtenerFe(c.getDer())) + 1);
        aux.setFactorEquilibrio(Math.max(obtenerFe(aux.getIzq()), obtenerFe(aux.getDer())) + 1);
        return aux;
    }
    
    public NodoAvl<Tipo> rotacionDerecha(NodoAvl<Tipo> c){
        NodoAvl<Tipo> aux = c.getDer();
        c.setDer(aux.getIzq());
        aux.setIzq(c);
        c.setFactorEquilibrio(Math.max(obtenerFe(c.getIzq()), obtenerFe(c.getDer())) + 1);
        aux.setFactorEquilibrio(Math.max(obtenerFe(aux.getIzq()), obtenerFe(aux.getDer())) + 1);
        return aux;
    }
    
    public void insertar(String id, Tipo info){
        NodoAvl<Tipo> nuevo = new NodoAvl(id,info);
        if (raiz == null) {
            raiz = nuevo;
        }else{
            raiz = insertarAvl(nuevo,raiz);
//            System.out.println("id insertado");
        }
    }
    
    public void insertar(NodoAvl<Tipo> nuevo){        
        if (raiz == null) {
            raiz = nuevo;
        }else{
            raiz = insertarAvl(nuevo,raiz);
//            System.out.println("id insertado");
        }
    }        
    
    private int obtenerFe(NodoAvl<Tipo> nodo){
        if (nodo == null) {
            return -1;
        }
        return nodo.getFactorEquilibrio();
    }
    
    public void inOrden(){
        inOrden(this.raiz);
    }
    
    public void postOrden(){
        postOrden(this.raiz);
    }
    
    public void preOrden(){
        preOrden(this.raiz);
    }
    
    private void inOrden(NodoAvl<Tipo> nodo){
        if (nodo == null) {
            return;
        }        
        inOrden(nodo.getIzq());
        System.out.println("Clave: " + nodo.getClave());
        inOrden(nodo.getDer());   
    }
    
    private void preOrden(NodoAvl<Tipo> nodo){
        if (nodo == null) {
            return;
        }
        System.out.println("Clave: " + nodo.getClave());
        preOrden(nodo.getIzq());
        preOrden(nodo.getDer());        
    }
    
    private void postOrden(NodoAvl<Tipo> nodo){
        if (nodo == null) {
            return;
        }
        postOrden(nodo.getIzq());
        postOrden(nodo.getDer());
        System.out.println("Clave: " + nodo.getClave());
    }

    public NodoAvl<Tipo> getRaiz() {
        return raiz;
    }
    
    
}
