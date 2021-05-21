/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author user-ubunto
 */
public class EstudianteNota {
    private String carnet;
    private String nombre;
    private String nota;
    private String estado;

    public EstudianteNota(String carnet, String nombre, String nota, String estado) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.nota = nota;
        this.estado = estado;
    }

    public EstudianteNota() {
    }        

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
}
