/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import GUI.JFErrores;
import analizadores.LexerSAC;
import analizadores.ParserSAC;
import estructuras.ArbolAvl;
import estructuras.BTree;
import estructuras.ListaCircDobEnl;
import estructuras.ListaDobEnl;
import estructuras.ListaEnlSim;
import estructuras.TablaHash;
import java.io.StringReader;
import javax.swing.JOptionPane;
import nodos.Llave;
import objetos.Asignacion;
import objetos.Catedratico;
import objetos.Curso;
import objetos.Edificio;
import objetos.ErrorCom;
import objetos.Estudiante;
import objetos.Horario;
import objetos.Salon;
import objetos.Usuario;

/**
 *
 * @author user-ubunto
 */
public class ControlCargarDatos {

    private String entrada;
    private ListaEnlSim<ErrorCom> erroresComp;
    private GuardadoDatos guardadoDatos;

    public ControlCargarDatos(GuardadoDatos guardadoDatos) {
        this.guardadoDatos = guardadoDatos;
    }

    public void cargarDatos(String entrada) {
        this.entrada = entrada;
        StringReader reader = new StringReader(entrada);
        LexerSAC lexico = new LexerSAC(reader);
        ParserSAC parser = new ParserSAC(lexico);
        try {
            parser.parse();
            this.erroresComp = parser.getErroresCom();
            if (this.erroresComp.isEmpty() == true) {
                //TODO: Insertar Estudiantes en hash
                ListaEnlSim<Estudiante> estudiantes = parser.getEstudiantes();
                insertarEstudiantesEnHash(estudiantes);

                //Insertar edificios con salones
                guardadoDatos.setEdificios(parser.getEdificios());
                insertarSalones(parser.getSalones());

                //Insertar Cursos
                guardadoDatos.setCursos(parser.getCursos());

                //Insertar Catedraticos                
                insertarCatedraticos(parser.getCatedraticos());

                //Insertar Usuarios
                insertarUsuarios(parser.getUsuarios());

                //Insertar en horario las referencias e insertar horario en arbolB
                insertarHorarios(parser.getHorarios());

                //Insertar referencias de asignaciones   
                insertarAsignaciones(parser.getAsignaciones());
            }
        } catch (Exception ex) {
            System.out.println("Error irrecuperable");
            System.out.println("Causa: " + ex.getCause());
            System.out.println("Causa2: " + ex.toString());
        }
        if (erroresComp.isEmpty()) {
//            No hay errores se pueden cargar los datos                
            System.out.println("No hay errores");

        } else {
            //Hay errores mostrar errores en un JF
            JFErrores jfErrores = new JFErrores(erroresComp);
            jfErrores.setVisible(true);
        }
    }

    private void insertarSalones(ListaEnlSim<Salon> salones) {
        ListaEnlSim ultimo = salones;
        while (ultimo != null) {
            Object objErr = ultimo.getVal();
            if (objErr != null) {
                Salon salonIns = (Salon) ultimo.getVal();
                String nombreE = salonIns.getNombre();
                Edificio edificio = guardadoDatos.getEdificioPorNombre(nombreE);
                if (edificio != null) {
                    edificio.insertarSalon(salonIns);
                } else {
                    //ERROR El edificio no existe
                }
            }
            ultimo = ultimo.getSig();
        }
    }

    private void insertarCatedraticos(ListaEnlSim<Catedratico> catedraticos) {
        ArbolAvl<Catedratico> arbolCatedraticos = this.guardadoDatos.getCatedraticos();
        ListaEnlSim ultimo = catedraticos;
        while (ultimo != null) {
            Object objErr = ultimo.getVal();
            if (objErr != null) {
                try {
                    Catedratico catedraticoIns = (Catedratico) ultimo.getVal();
                    arbolCatedraticos.insertar(catedraticoIns.getNombre(), catedraticoIns);
//                    arbolCatedraticos.insertar(catedraticoIns.getIdentificador(), catedraticoIns);
                } catch (NullPointerException e) {

                }
            }
            ultimo = ultimo.getSig();
        }
        this.guardadoDatos.setCatedraticos(arbolCatedraticos);
    }

    private void insertarEstudiantesEnHash(ListaEnlSim<Estudiante> estudiantes) {
        TablaHash<Estudiante> estudiantesHash = this.guardadoDatos.getEstudiantes();
        ListaEnlSim ultimo = estudiantes;
        while (ultimo != null) {
            Object objErr = ultimo.getVal();
            if (objErr != null) {
                Estudiante estudianteIns = (Estudiante) ultimo.getVal();
                estudiantesHash.insertar(estudianteIns, estudianteIns.getCarnet());
            }
            ultimo = ultimo.getSig();
        }
    }

    private void insertarUsuarios(ListaEnlSim<Usuario> usuariosIns) {
        ListaDobEnl<Usuario> usuarios = this.guardadoDatos.getUsuarios();
        ListaEnlSim ultimo = usuariosIns;
        while (ultimo != null) {
            Object objErr = ultimo.getVal();
            if (objErr != null) {
                Usuario usuarioIns = (Usuario) ultimo.getVal();
                String tipoUsuario = usuarioIns.getTipo();
                if (tipoUsuario.equals("estudiante")) {
                    //Traer estudiante en hash
                    Estudiante estudianteIns = this.guardadoDatos.getEstudiantes().buscar(usuarioIns.getId());
                    if (estudianteIns == null) {
                        //Error el estudiante no existe
                    } else {
                        //Insertar en la lista e insertar el estudiante
                        usuarioIns.setEstudiante(estudianteIns);
                        usuarios.insertar(usuarioIns);
                    }
                } else {
                    //Insertar en la lista
                    usuarios.insertar(usuarioIns);
                }
            }
            ultimo = ultimo.getSig();
        }
    }

    private void insertarHorarios(ListaEnlSim<Horario> horariosIns) {
        BTree<Horario> horarios = this.guardadoDatos.getHorarios();
        ListaEnlSim ultimo = horariosIns;
        while (ultimo != null) {
            boolean insertar = true;
            Object objErr = ultimo.getVal();
            if (objErr != null) {
                Horario horarioIns = (Horario) ultimo.getVal();
                Salon salonIns = this.guardadoDatos.buscarSalonPorNombre(horarioIns.getCodigoSalonStr());
                if (salonIns == null) {
                    //Error no se pudo insertar: salon no encontrado
                    insertar = false;
                }
                Curso curso = this.guardadoDatos.buscarCursoPorCodigo(horarioIns.getCodigoCursoStr());
                if (curso == null) {
                    insertar = false;
                }

                Edificio edificio = this.guardadoDatos.getEdificioPorNombre(horarioIns.getCodigoEdificioStr());
                if (edificio == null) {
                    insertar = false;
                }

                Catedratico catedratico = this.guardadoDatos.buscarCatedraticoPorCodigo(horarioIns.getCodigoNumIdCatedStr());
                if (catedratico == null) {
                    insertar = false;
                }

                if (insertar) {
                    horarioIns.setCatedratico(catedratico);
                    horarioIns.setEdificio(edificio);
                    horarioIns.setCurso(curso);
                    horarioIns.setSalon(salonIns);
                    Llave<Horario> llave = new Llave<>(Integer.parseInt(horarioIns.getCodigo()), horarioIns);
                    horarios.Insert(llave);
                }
            }
            ultimo = ultimo.getSig();
        }
    }

    private void insertarAsignaciones(ListaEnlSim<Asignacion> asignaciones) {
        ListaEnlSim<Asignacion> asignacionesG = this.guardadoDatos.getAsignaciones();
        ListaEnlSim ultimo = asignaciones;
        while (ultimo != null) {
            boolean insertar = true;
            Object objErr = ultimo.getVal();
            if (objErr != null) {
                Asignacion asignacionIns = (Asignacion) ultimo.getVal();
                Horario horario = this.guardadoDatos.horarios.buscar(asignacionIns.getCodHorarioStr());
                if (horario == null) {
                    insertar = false;
                }
                Estudiante estudiante = this.guardadoDatos.getEstudiantes().buscar(asignacionIns.getCarnetStr());
                if (estudiante == null) {
                    insertar = false;
                }
                if (insertar) {
                    Salon salon = horario.getSalon();
                    int cantidadEstudiantesAsignados = horario.getCantidadAsignados();
                    int capacidad = Integer.parseInt(salon.getCapacidad());
                    if (capacidad > cantidadEstudiantesAsignados) {
                        asignacionIns.setEstudiante(estudiante);
                        asignacionIns.setHorario(horario);
                        asignacionesG.add(asignacionesG, asignacionIns);
                        horario.insertarAsignado();
                    } else {
                        //error capacidad exedida
                    }
                }
            }
            ultimo = ultimo.getSig();
        }
    }
}
