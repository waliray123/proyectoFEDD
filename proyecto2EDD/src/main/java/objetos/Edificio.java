/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import estructuras.ListaEnlSim;

/**
 *
 * @author user-ubunto
 */
public class Edificio {
    private String nombre;
    private ListaEnlSim<Salon> salones;

    public Edificio() {
        this.salones = new ListaEnlSim<>();
    }
            
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaEnlSim<Salon> getSalones() {
        return salones;
    }

    public void setSalones(ListaEnlSim<Salon> salones) {
        this.salones = salones;
    }
    
    public void insertarSalon(Salon salonIns){
        salones.add(salones, salonIns);
    }
        
}
