/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import nodos.NodoHash;

/**
 *
 * @author user-ubunto
 * @param <Tipo>
 */
public class TablaHash<Tipo> {

    private final float factorUtil = 55.0f;
    private float porcentajeUtil;
    private int tamano;
    private int ocupados;
    private NodoHash<Tipo>[] vectorH;

    public TablaHash() {
        this.ocupados = 0;
        this.tamano = 37;
        this.vectorH = new NodoHash[this.tamano];
        this.porcentajeUtil = calcularPorcentajeUtil();
    }

    public void insertar(Tipo valor, String llaveStr) {
        int llave = Integer.valueOf(llaveStr);
        boolean colision = false;
        boolean insertado = false;
        if (this.porcentajeUtil <= this.factorUtil) {
            for (int i = 0; i < this.tamano; i++) {
                int posicion;
                if (colision == true) {
                    posicion = funcionDobleDis(llave, i);
                } else {
                    posicion = funcionHash(llave);
                }

                if (posicion > tamano) {
                    posicion -= tamano;
                }

                if (vectorH[posicion] == null || vectorH[posicion].getEstado() == 'b') {
                    NodoHash<Tipo> nodoIns = new NodoHash(llaveStr, valor);
                    nodoIns.setEstado('a');
                    vectorH[posicion] = nodoIns;
                    ocupados += 1;
                    this.porcentajeUtil = calcularPorcentajeUtil();
                    insertado = true;
                    break;
                } else {
                    if (vectorH[posicion].getNombre().equals(llaveStr)) {
                        //La variable ya existe
                        System.out.println("La variable " + llaveStr + " ya existe");
                        break;
                    } else {
                        //Hay una colision
                        colision = true;
                    }
                }
            }

            //if insertado = true se inserto sino no se inserto        
        } else {
            //Rehashing            
            reHashing();
            insertar(valor, llaveStr);
        }
    }

    private void reHashing() {
        NodoHash<Tipo>[] tmp = vectorH;
        int tamanoTmp = tamano;
        this.tamano = tamanoTmp + 6;
        this.vectorH = new NodoHash[this.tamano];
        this.ocupados = 0;
        this.porcentajeUtil = calcularPorcentajeUtil();
        for (int i = 0; i < tamanoTmp; i++) {
            if (tmp[i] != null && tmp[i].getEstado() == 'a') {
                insertar(tmp[i].getValor(), tmp[i].getNombre());
            }
        }
    }

    public Tipo buscar(String llaveBusStr) {
        int llaveBus = Integer.valueOf(llaveBusStr);        
        boolean colision = false;
        int pos = 0;
        for (int i = 0; i < this.tamano; i++) {
            if (colision == true) {
                pos = funcionDobleDis(llaveBus, i);
            } else {
                pos = funcionHash(llaveBus);
            }
            
            if (vectorH[pos] != null) {
                if ( vectorH[pos].getEstado() == 'a' && vectorH[pos].getNombre().equals(llaveBusStr)) {
                    return vectorH[pos].getValor();
                }else{
                    colision = true;
                }
            }else{
                 return null;
            }
        }
        return null;
    }
    
    public NodoHash buscarNodo(String llaveBusStr) {
        int llaveBus = Integer.valueOf(llaveBusStr);        
        boolean colision = false;
        int pos = 0;
        for (int i = 0; i < this.tamano; i++) {
            if (colision == true) {
                pos = funcionDobleDis(llaveBus, i);
            } else {
                pos = funcionHash(llaveBus);
            }
            
            if (vectorH[pos] != null) {
                if ( vectorH[pos].getEstado() == 'a' && vectorH[pos].getNombre().equals(llaveBusStr)) {
                    return vectorH[pos];
                }else{
                    colision = true;
                }
            }else{
                 return null;
            }
        }
        return null;
    }
        
    public int funcionHash(int llave) {
        return llave % this.tamano;
    }
    
    public int funcionDobleDis(int llave, int i) {
        return ((llave % 7) + 1) * i;
    }

    public int calcularPorcentajeUtil() {
        return (this.ocupados * 100) / this.tamano;
    }

    public NodoHash<Tipo>[] getVectorH() {
        return vectorH;
    }

    public void setVectorH(NodoHash<Tipo>[] vectorH) {
        this.vectorH = vectorH;
    }        

}
