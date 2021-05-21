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
public class Usuario {
    private String id;
    private String nombre;
    private String contrasenia;
    private String tipo;
    private Estudiante estudiante;

//    public Usuario(String nombre, String contrasenia, String tipo) {
//        this.nombre = nombre;
//        this.contrasenia = contrasenia;
//        this.tipo = tipo;
//    }

    public Usuario() {
        
    }   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }        

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    
    
    
}
