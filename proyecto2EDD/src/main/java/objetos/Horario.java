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
public class Horario {
    
    private String codigo;
    private String codigoSalonStr;
    private String codigoCursoStr;
    private String codigoEdificioStr;
    private String codigoNumIdCatedStr;
    private String periodo;
    //Referencia a Objetos 
    private Salon salon;
    private Curso curso;
    private Edificio edificio;
    private Catedratico catedratico;    
    private int cantidadAsignados;

    public Horario() {
        this.cantidadAsignados = 0;
    }
    
    public void insertarAsignado(){
        this.cantidadAsignados++;
    }
    
    public void eliminarAsignado(){
        this.cantidadAsignados--;
    }

    public int getCantidadAsignados() {
        return cantidadAsignados;
    }

    public void setCantidadAsignados(int cantidadAsignados) {
        this.cantidadAsignados = cantidadAsignados;
    }                
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoSalonStr() {
        return codigoSalonStr;
    }

    public void setCodigoSalonStr(String codigoSalonStr) {
        this.codigoSalonStr = codigoSalonStr;
    }

    public String getCodigoCursoStr() {
        return codigoCursoStr;
    }

    public void setCodigoCursoStr(String codigoCursoStr) {
        this.codigoCursoStr = codigoCursoStr;
    }

    public String getCodigoEdificioStr() {
        return codigoEdificioStr;
    }

    public void setCodigoEdificioStr(String codigoEdificioStr) {
        this.codigoEdificioStr = codigoEdificioStr;
    }

    public String getCodigoNumIdCatedStr() {
        return codigoNumIdCatedStr;
    }

    public void setCodigoNumIdCatedStr(String codigoNumIdCatedStr) {
        this.codigoNumIdCatedStr = codigoNumIdCatedStr;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    public Catedratico getCatedratico() {
        return catedratico;
    }

    public void setCatedratico(Catedratico catedratico) {
        this.catedratico = catedratico;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    
    
    
}
