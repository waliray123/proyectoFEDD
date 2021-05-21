/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import estructuras.ListaDobEnl;
import estructuras.ListaEnlSim;
import nodos.NodoB;
import nodos.NodoListDob;
import objetos.Asignacion;
import objetos.Curso;
import objetos.Estudiante;
import objetos.EstudianteNota;
import objetos.Horario;
import objetos.Salon;

/**
 *
 * @author user-ubunto
 */
public class ControlReportes {

    private GuardadoDatos guardadoDatos;

    public ControlReportes(GuardadoDatos guardadoDatos) {
        this.guardadoDatos = guardadoDatos;
    }

    public ListaDobEnl<Curso> mostrarCursosAsignados(Estudiante estudiante) {
        ListaDobEnl<Curso> cursos = new ListaDobEnl<>();
        ListaEnlSim<Asignacion> asignaciones = this.guardadoDatos.getAsignaciones();
        ListaEnlSim ultimo = asignaciones;
        while (ultimo != null) {
            Object objErr = ultimo.getVal();
            if (objErr != null) {
                Asignacion asignacion = (Asignacion) objErr;
                if (asignacion.getEstudiante() == estudiante) {
                    Horario horario = asignacion.getHorario();
                    Curso curso = horario.getCurso();
                    cursos.insertar(curso);
                }
            }
            ultimo = ultimo.getSig();
        }
        return cursos;
    }

    public ListaDobEnl<Estudiante> mostrarEstudiantesAsignadosACurso(Curso curso) {
        ListaDobEnl<Estudiante> estudiantes = new ListaDobEnl<>();
        ListaDobEnl<Horario> horariosConCurso = new ListaDobEnl<>();
        buscarHorariosConCurso(horariosConCurso, curso);
        NodoListDob<Horario> ultimo = horariosConCurso.getRaiz();
        while (ultimo != null) {
            Horario horario = ultimo.getInfo();
            if (horario != null) {
                ListaEnlSim<Asignacion> ultimo2 = this.guardadoDatos.getAsignaciones();
                while (ultimo2 != null) {
                    Asignacion asignacion = ultimo2.getVal();
                    if (asignacion != null) {                        
                        if (asignacion.getHorario() == horario) {                            
                            estudiantes.insertar(asignacion.getEstudiante());
                        }
                    }
                    ultimo2 = ultimo2.getSig();
                }

            }
            ultimo = ultimo.getSig();
        }
        return estudiantes;
    }

    public void buscarHorariosConCurso(ListaDobEnl<Horario> horariosConCurso, Curso curso) {
        buscarHorarioConCurso(this.guardadoDatos.getHorarios().getRoot(), horariosConCurso, curso);
    }
    
    private void buscarHorarioConCurso(NodoB<Horario> x, ListaDobEnl<Horario> horariosConCurso, Curso curso) {
        assert (x == null);
        for (int i = 0; i < x.n; i++) {
            if (x.key[i].getValor().getCurso() == curso) {
                horariosConCurso.insertar(x.key[i].getValor());
            }
        }
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                buscarHorarioConCurso(x.child[i], horariosConCurso, curso);
            }
        }
    }
    
    public ListaDobEnl<Curso> buscarCursosQueSeDanEnSalon(Salon salon){
        ListaDobEnl<Curso> cursosEnSalon = new ListaDobEnl<>();
        buscarCursosEnSalon(cursosEnSalon,salon);
        return cursosEnSalon;
    }
    
    public void buscarCursosEnSalon(ListaDobEnl<Curso> cursosEnSalon, Salon salon) {
        buscarCursosEnSalon(this.guardadoDatos.getHorarios().getRoot(), cursosEnSalon, salon);
    }
    
    private void buscarCursosEnSalon(NodoB<Horario> x, ListaDobEnl<Curso> cursosEnSalon, Salon salon) {
        assert (x == null);
        for (int i = 0; i < x.n; i++) {
            if (x.key[i].getValor().getSalon() == salon) {
                cursosEnSalon.insertar(x.key[i].getValor().getCurso());
            }
        }
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                buscarCursosEnSalon(x.child[i], cursosEnSalon, salon);
            }
        }
    }
    
    public ListaDobEnl<EstudianteNota> mostrarCantEstudiantesAprobados(String numSemestre){
        ListaDobEnl<EstudianteNota> estudiantes = new ListaDobEnl<>();
        ListaDobEnl<Horario> horariosPorSemestre = new ListaDobEnl<>();        
        buscarHorariosPorSemestre(horariosPorSemestre,numSemestre);
        NodoListDob<Horario> ultimo = horariosPorSemestre.getRaiz();
        while (ultimo != null) {
            Horario horario = ultimo.getInfo();
            if (horario != null) {
                ListaEnlSim<Asignacion> ultimo2 = this.guardadoDatos.getAsignaciones();
                while (ultimo2 != null) {
                    Asignacion asignacion = ultimo2.getVal();
                    if (asignacion != null) {                        
                        if (asignacion.getHorario() == horario) {     
                            Double zona = Double.parseDouble(asignacion.getZona());
                            Double exFinal = Double.parseDouble(asignacion.getExFinal());
                            Double nota = zona+exFinal;                            
                            String estado = "aprobado"; 
                            if (nota < 61) {
                                estado = "reprobado";
                            }
                            EstudianteNota estTemp = new EstudianteNota(asignacion.getEstudiante().getCarnet(),
                                asignacion.getEstudiante().getCarnet(),String.valueOf(nota),estado);
                            estudiantes.insertar(estTemp);
                        }
                    }
                    ultimo2 = ultimo2.getSig();
                }

            }
            ultimo = ultimo.getSig();
        }
        return estudiantes;
    }
    
    public void buscarHorariosPorSemestre(ListaDobEnl<Horario> horariosPorSemestre, String semestre) {
        buscarHorarioPorSemestre(this.guardadoDatos.getHorarios().getRoot(), horariosPorSemestre, semestre);
    }
    
    private void buscarHorarioPorSemestre(NodoB<Horario> x, ListaDobEnl<Horario> horariosPorSemestre, String semestre) {
        assert (x == null);
        for (int i = 0; i < x.n; i++) {
            if (x.key[i].getValor().getCurso().getSemestre().equals(semestre)) {
                horariosPorSemestre.insertar(x.key[i].getValor());
            }
        }
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                buscarHorarioPorSemestre(x.child[i], horariosPorSemestre, semestre);
            }
        }
    }
    
}
