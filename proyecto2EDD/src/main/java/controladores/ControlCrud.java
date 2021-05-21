/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import estructuras.ArbolAvl;
import estructuras.BTree;
import javax.swing.JOptionPane;
import nodos.Llave;
import nodos.NodoAvl;
import nodos.NodoB;
import nodos.NodoHash;
import objetos.Asignacion;
import objetos.Catedratico;
import objetos.Curso;
import objetos.Edificio;
import objetos.Estudiante;
import objetos.Horario;
import objetos.Salon;
import objetos.Usuario;

/**
 *
 * @author user-ubunto
 */
public class ControlCrud {

    private GuardadoDatos guardadoDatos;

    public ControlCrud(GuardadoDatos guardadoDatos) {
        this.guardadoDatos = guardadoDatos;
    }

    //Insersiones
    public void insertarUsuario(String codigo, String nombre, String contrasenia, String tipo) {
        Usuario usuario = this.guardadoDatos.buscarUsuarioPorNombre(nombre);
        if (usuario == null) {
            Usuario usuarioIns = new Usuario();
            usuarioIns.setId(codigo);
            usuarioIns.setNombre(nombre);
            usuarioIns.setContrasenia(contrasenia);
            usuarioIns.setTipo(tipo);
            if (tipo.equals("Estudiante")) {
                Estudiante estudianteIns = this.guardadoDatos.getEstudiantes().buscar(usuarioIns.getId());
                if (estudianteIns == null) {
                    //Error el estudiante no existe
                    JOptionPane.showMessageDialog(null, "Estudiante no existe", "Ingreso incorrecto", JOptionPane.ERROR_MESSAGE);
                } else {
                    //Insertar en la lista e insertar el estudiante
                    usuarioIns.setEstudiante(estudianteIns);
                    this.guardadoDatos.insertarUsuario(usuarioIns);
                    JOptionPane.showMessageDialog(null, "Usuario ingresado correctamente", "Ingreso correcto", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                this.guardadoDatos.insertarUsuario(usuarioIns);
                JOptionPane.showMessageDialog(null, "Usuario ingresado correctamente", "Ingreso correcto", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El usuario ya existe", "Error de Ingreso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertarEstudiante(String carnet, String nombre, String direccion) {
        Estudiante estudiante = this.guardadoDatos.buscarEstudiantePorCarnet(carnet);
        if (estudiante == null) {
            Estudiante estudianteIns = new Estudiante();
            estudianteIns.setCarnet(carnet);
            estudianteIns.setNombre(nombre);
            estudianteIns.setDireccion(direccion);
            this.guardadoDatos.getEstudiantes().insertar(estudianteIns, estudianteIns.getCarnet());
            JOptionPane.showMessageDialog(null, "Estudiante insertado correctamente", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "El estudiante ya existe", "Error de Ingreso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertarCatedratico(String identificador, String nombre, String direccion) {
        Catedratico cat = this.guardadoDatos.buscarCatedraticoPorCodigo(identificador);
        if (cat == null) {
            Catedratico catedraticoIns = new Catedratico();
            catedraticoIns.setIdentificador(identificador);
            catedraticoIns.setNombre(nombre);
            catedraticoIns.setDireccion(direccion);
            try {
                this.guardadoDatos.getCatedraticos().insertar(catedraticoIns.getNombre(), catedraticoIns);
//            this.guardadoDatos.getCatedraticos().insertar(catedraticoIns.getIdentificador(), catedraticoIns); 
                JOptionPane.showMessageDialog(null, "Catedratico Ingresado correctamente", "Ingreso", JOptionPane.INFORMATION_MESSAGE);           
            } catch (NullPointerException e) {
                System.out.println(e);
                e.printStackTrace();
            }            
        } else {
            JOptionPane.showMessageDialog(null, "Catedratico no ingresado, ya existe un catedratico con ese codigo", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void insertarHorario(String codigo, String codigoSalon, String codigoCurso, String codigoEdificio, String codigoNumId, String periodo) {
        Horario horarioIns = new Horario();
        horarioIns.setCodigo(codigo);
        horarioIns.setCodigoSalonStr(codigoSalon);
        horarioIns.setCodigoCursoStr(codigoCurso);
        horarioIns.setCodigoEdificioStr(codigoEdificio);
        horarioIns.setCodigoNumIdCatedStr(codigoNumId);
        horarioIns.setPeriodo(periodo);
        revisarHorario(horarioIns);
    }

    private void revisarHorario(Horario horarioIns) {
        boolean insertar = true;
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
            this.guardadoDatos.getHorarios().Insert(llave);
            JOptionPane.showMessageDialog(null, "Horario insertado correctamente", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Horario no insertado", "Ingreso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertarAsignacion(String codigoHorario, String codigoCarnet, String zona, String exFinal) {
        boolean insertar = true;
        Asignacion asignacionIns = new Asignacion();
        Horario horario = this.guardadoDatos.horarios.buscar(codigoHorario);
        Estudiante estudiante = this.guardadoDatos.getEstudiantes().buscar(codigoCarnet);
        if (estudiante == null) {
            insertar = false;
            JOptionPane.showMessageDialog(null, "No se puede asignar, estudiante no encontrado", "Ingreso", JOptionPane.ERROR_MESSAGE);
        }
        if (horario == null) {
            insertar = false;
            JOptionPane.showMessageDialog(null, "No se puede asignar, horario no encontrado", "Ingreso", JOptionPane.ERROR_MESSAGE);
        }
        if (insertar) {
            Salon salon = horario.getSalon();
            int cantidadEstudiantesAsignados = horario.getCantidadAsignados();
            int capacidad = Integer.parseInt(salon.getCapacidad());
            if (capacidad > cantidadEstudiantesAsignados) {
                asignacionIns.setCarnetStr(codigoCarnet);
                asignacionIns.setCodHorarioStr(codigoHorario);
                asignacionIns.setZona(zona);
                asignacionIns.setExFinal(exFinal);
                asignacionIns.setEstudiante(estudiante);
                asignacionIns.setHorario(horario);
                this.guardadoDatos.getAsignaciones().add(this.guardadoDatos.getAsignaciones(), asignacionIns);
                horario.insertarAsignado();
                JOptionPane.showMessageDialog(null, "Asignacion completa", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                //error capacidad exedida
            }
        }
    }

    public void insertarEdificio(String nombre) {
        Edificio edificio = this.guardadoDatos.getEdificioPorNombre(nombre);
        if (edificio == null) {
            Edificio edificioIns = new Edificio();
            edificioIns.setNombre(nombre);
            this.guardadoDatos.getEdificios().insertar(edificioIns);
            JOptionPane.showMessageDialog(null, "Edificio insertado correctamente", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Edificio no insertado, ya existe un edificio con ese nombre", "Ingreso", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertarCurso(String codigo, String nombre, String semestre, String creditos) {
        Curso cursoB = this.guardadoDatos.buscarCursoPorCodigo(codigo);
        if (cursoB == null) {
            Curso cursoIns = new Curso();
            cursoIns.setCodigo(codigo);
            cursoIns.setNombre(nombre);
            cursoIns.setSemestre(semestre);
            cursoIns.setCreditos(creditos);
            this.guardadoDatos.getCursos().insertar(cursoIns);
            JOptionPane.showMessageDialog(null, "Curso insertadocorrectamente", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Curso no insertado, ya existe un curso con ese codigo", "Ingreso", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Eliminaciones
    public void eliminarUsuario(Usuario usuarioE) {
        boolean seElimino = this.guardadoDatos.usuarios.remove(usuarioE);
        if (seElimino) {
            JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente", "Eliminacion", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Usuario no eliminado", "Error de Eliminacion", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarEstudiante(Estudiante estudiante) {
        NodoHash nodo = this.guardadoDatos.getEstudiantes().buscarNodo(estudiante.getCarnet());
        nodo.setEstado('b');
        JOptionPane.showMessageDialog(null, "Estudiante eliminado correctamente", "Eliminacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public void eliminarCatedratico(Catedratico catedratico) {
        ArbolAvl catedraticos = new ArbolAvl();
        preOrden(this.guardadoDatos.getCatedraticos().getRaiz(), catedraticos, catedratico);
        this.guardadoDatos.setCatedraticos(catedraticos);
    }

    public void eliminarHorario(Horario horarioElim) {
        BTree nuevoArbol = new BTree(3);
        Show(this.guardadoDatos.getHorarios().getRoot(), horarioElim, nuevoArbol);
        this.guardadoDatos.setHorarios(nuevoArbol);
    }

    public void eliminarEdificio(Edificio edificioElim) {
        //TODO

    }

    public void eliminarCurso(Curso curso) {
        //TODO
    }

    // Display
    private void Show(NodoB<Horario> x, Horario horarioElim, BTree arbolN) {
        assert (x == null);
        for (int i = 0; i < x.n; i++) {
            if (horarioElim == x.key[i].getValor()) {

            } else {
                arbolN.Insert(x.key[i]);
            }
        }
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                Show(x.child[i], horarioElim, arbolN);
            }
        }
    }

    private void preOrden(NodoAvl<Catedratico> nodo, ArbolAvl arbol, Catedratico cat) {
        if (nodo == null) {
            return;
        }
        if (nodo.getInfo().getIdentificador().equals(cat.getIdentificador())) {

        } else {
            arbol.insertar(nodo.getInfo().getIdentificador(), nodo.getInfo());
        }
        preOrden(nodo.getIzq(), arbol, cat);
        preOrden(nodo.getDer(), arbol, cat);
    }

    //Modificaciones
    public void modificarUsuario(String nombre, String contrasenia, Usuario usuarioMod) {
        usuarioMod.setNombre(nombre);
        usuarioMod.setContrasenia(contrasenia);
        JOptionPane.showMessageDialog(null, "Usuario modificado correctamente", "Modificacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public void modificarEstudiante(String nombre, String direccion, Estudiante estudianteMod) {
        estudianteMod.setNombre(nombre);
        estudianteMod.setDireccion(direccion);
        JOptionPane.showMessageDialog(null, "Estudiante modificado correctamente", "Modificacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public void modificarCatedratico(String nombre, String direccion, Catedratico catedratico) {
        catedratico.setNombre(nombre);
        catedratico.setDireccion(direccion);
        JOptionPane.showMessageDialog(null, "Catedratico modificado correctamente", "Modificacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public void modificarHorario(String codigo, String codigoSalon, String codigoCurso, String codigoEdificio, String codigoNumId, String periodo, Horario horarioIns) {
        boolean insertar = true;
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
            JOptionPane.showMessageDialog(null, "Horario modificado correctamente", "Modificacion", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Horario no modificado", "Modificacion", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modificarEdificio(String nombre, Edificio edMod) {
        edMod.setNombre(nombre);
        JOptionPane.showMessageDialog(null, "Edificio modificado correctamente", "Modificacion", JOptionPane.INFORMATION_MESSAGE);
    }

    public void modificarCurso(String nombre, String semestre, String creditos, Curso curso) {
        curso.setNombre(nombre);
        curso.setSemestre(semestre);
        curso.setCreditos(creditos);
        JOptionPane.showMessageDialog(null, "Curso modificado correctamente", "Modificacion", JOptionPane.INFORMATION_MESSAGE);
    }
}
