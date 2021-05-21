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
public class Asignacion {
    private String carnetStr;
    private String codHorarioStr;
    private String zona;
    private String exFinal;
    /*Referencias a objetos*/
    private Estudiante estudiante;
    private Horario horario;

    public String getCarnetStr() {
        return carnetStr;
    }

    public void setCarnetStr(String carnetStr) {
        this.carnetStr = carnetStr;
    }

    public String getCodHorarioStr() {
        return codHorarioStr;
    }

    public void setCodHorarioStr(String codHorarioStr) {
        this.codHorarioStr = codHorarioStr;
    }


    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getExFinal() {
        return exFinal;
    }

    public void setExFinal(String exFinal) {
        this.exFinal = exFinal;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    
    
    
}
