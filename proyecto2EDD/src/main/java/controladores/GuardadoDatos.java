/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import estructuras.ArbolAvl;
import estructuras.BTree;
import estructuras.ListaCircDobEnl;
import estructuras.ListaDobEnl;
import estructuras.ListaEnlSim;
import estructuras.TablaHash;
import nodos.NodoAvl;
import nodos.NodoListDob;
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
public class GuardadoDatos {

    ListaDobEnl<Usuario> usuarios;
    ListaCircDobEnl<Edificio> edificios;
    ListaCircDobEnl<Curso> Cursos;
    TablaHash<Estudiante> estudiantes;
    ArbolAvl<Catedratico> catedraticos;
    BTree<Horario> horarios;
    ListaEnlSim<Asignacion> asignaciones;

    public GuardadoDatos() {
        this.usuarios = new ListaDobEnl<>();
        this.edificios = new ListaCircDobEnl<>();
        this.Cursos = new ListaCircDobEnl<>();
        this.catedraticos = new ArbolAvl<>();
        this.asignaciones = new ListaEnlSim<>();
        this.estudiantes = new TablaHash<>();
        this.horarios = new BTree<>(3);
    }

    public ListaDobEnl<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ListaDobEnl<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ListaCircDobEnl<Edificio> getEdificios() {
        return edificios;
    }

    public void setEdificios(ListaCircDobEnl<Edificio> edificios) {
        this.edificios = edificios;
    }

    public ListaCircDobEnl<Curso> getCursos() {
        return Cursos;
    }

    public void setCursos(ListaCircDobEnl<Curso> Cursos) {
        this.Cursos = Cursos;
    }

    public ArbolAvl<Catedratico> getCatedraticos() {
        return catedraticos;
    }

    public void setCatedraticos(ArbolAvl<Catedratico> catedraticos) {
        this.catedraticos = catedraticos;
    }

    public ListaEnlSim<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(ListaEnlSim<Asignacion> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public TablaHash<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(TablaHash<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public BTree<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(BTree<Horario> horarios) {
        this.horarios = horarios;
    }

    public Edificio getEdificioPorNombre(String nombreE) {
        Edificio edificioR = null;
        NodoListDob<Edificio> ultimo = this.edificios.getRaiz();
        while (ultimo != null) {
            Edificio edf = ultimo.getInfo();
            if (edf != null) {
                String nombreEdificio = String.valueOf(edf.getNombre());
                if (nombreE.equals(nombreEdificio)) {
                    return edf;
                }
            }
            ultimo = ultimo.getSig();
            if (ultimo == this.edificios.getRaiz()) {
                return null;
            }
        }
        return edificioR;
    }

    public Usuario buscarUsuarioPorCodigo(String nombre) {
        Usuario usrR = null;
        NodoListDob<Usuario> ultimo = this.usuarios.getRaiz();
        while (ultimo != null) {
            Usuario usr = ultimo.getInfo();
            if (usr != null) {
                String nombUsr = usr.getId();                
                if (nombre.equals(nombUsr)) {
                    return usr;
                }
            }
            ultimo = ultimo.getSig();
        }
        return usrR;
    }
    
    public Usuario buscarUsuarioPorNombreContrasena(String nombre, String contrasenia) {
        Usuario usrR = null;
        NodoListDob<Usuario> ultimo = this.usuarios.getRaiz();
        while (ultimo != null) {
            Usuario usr = ultimo.getInfo();
            if (usr != null) {
                String nombUsr = usr.getNombre();
                String contUsr = usr.getContrasenia();
                if (nombre.equals(nombUsr)) {
                    if (contrasenia.equals(contUsr)) {
                        return usr;
                    }
                }
            }
            ultimo = ultimo.getSig();
        }
        return usrR;
    }

    public Usuario buscarUsuarioPorNombre(String nombre) {
        Usuario usrR = null;
        NodoListDob<Usuario> ultimo = this.usuarios.getRaiz();
        while (ultimo != null) {
            Usuario usr = ultimo.getInfo();
            if (usr != null) {
                String nombUsr = usr.getNombre();
                if (nombre.equals(nombUsr)) {
                    return usr;
                }
            }
            ultimo = ultimo.getSig();
            if (ultimo == this.usuarios.getRaiz()) {
                return null;
            }
        }
        return usrR;
    }

    public Salon buscarSalonPorNombre(String nombre) {
        NodoListDob<Edificio> aux = edificios.getRaiz();
        while (aux != null) {
            Edificio edf = aux.getInfo();
            if (edf != null) {
                Salon salon = buscarSalonEnLista(edf.getSalones(), nombre);
                if (salon != null) {
                    return salon;
                }
            }
            aux = aux.getSig();
            if (aux == this.edificios.getRaiz()) {
                return null;
            }
        }
        return null;
    }

    public Curso buscarCursoPorCodigo(String codigo) {
        NodoListDob<Curso> ultimo = this.Cursos.getRaiz();
        while (ultimo != null) {
            Curso crs = ultimo.getInfo();
            if (crs != null) {
                String codCrs = crs.getCodigo();
                if (codigo.equals(codCrs)) {
                    return crs;
                }
            }
            ultimo = ultimo.getSig();
            if (ultimo == this.Cursos.getRaiz()) {
                return null;
            }
        }
        return null;
    }

    public Catedratico buscarCatedraticoPorCodigo(String nombre) {
        return preOrdenCatedraticosCodigo(this.catedraticos.getRaiz(), nombre);
    }

    private Catedratico preOrdenCatedraticosCodigo(NodoAvl<Catedratico> nodo, String nombre) {
        if (nodo == null) {
            return null;
        }
        Catedratico catedratico = nodo.getInfo();
        if (catedratico != null && catedratico.getIdentificador().equals(nombre)) {
            return catedratico;
        } else {
            catedratico = preOrdenCatedraticosCodigo(nodo.getIzq(), nombre);
            if (catedratico != null && catedratico.getIdentificador().equals(nombre)) {
                return catedratico;
            } else {
                catedratico = preOrdenCatedraticosCodigo(nodo.getDer(), nombre);
                if (catedratico != null && catedratico.getIdentificador().equals(nombre)) {
                    return catedratico;
                }
            }
        }
        return null;
    }

    private Salon buscarSalonEnLista(ListaEnlSim<Salon> salones, String nombre) {
        ListaEnlSim ultimo = salones;
        while (ultimo != null) {
            Object objErr = ultimo.getVal();
            if (objErr != null) {
                Salon salon = (Salon) ultimo.getVal();
                if (salon.getNumero().equals(nombre)) {
                    return salon;
                }
            }
            ultimo = ultimo.getSig();
        }
        return null;
    }
    
    public Estudiante buscarEstudiantePorCarnet(String carnet){
        return this.estudiantes.buscar(carnet);
    }
    
    //Insersiones
    public void insertarUsuario(Usuario usuarioIns) {
        this.usuarios.insertar(usuarioIns);
    }

    //Eliminaciones
}
