/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadores;

import controladores.ControlTerminal;
import controladores.GuardadoDatos;
import estructuras.ListaCircDobEnl;
import estructuras.ListaDobEnl;
import estructuras.ListaEnlSim;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import nodos.NodoAvl;
import nodos.NodoB;
import nodos.NodoHash;
import nodos.NodoListDob;
import objetos.Asignacion;
import objetos.Catedratico;
import objetos.Edificio;
import objetos.Estudiante;
import objetos.Horario;
import objetos.Salon;
import objetos.Usuario;

/**
 *
 * @author user-ubunto
 */
public class GenStrGraphviz {

    private GuardadoDatos guardadoDatos;
    private String pathGuardado;
    private String strC;
    private String strH;
    private String strR;

    public GenStrGraphviz(GuardadoDatos guardadoDatos, String pathGuardado) {
        this.guardadoDatos = guardadoDatos;
        this.pathGuardado = pathGuardado;
    }

    private String genStrUsuarios(String strU) {
        ListaDobEnl<Usuario> usuarios = this.guardadoDatos.getUsuarios();
        NodoListDob<Usuario> ultimo = usuarios.getRaiz();
        while (ultimo != null) {
            Usuario usr = ultimo.getInfo();
            if (usr != null) {
                String codigo = usr.getId();
                String nombUsr = usr.getNombre();
                String passUsr = usr.getContrasenia();
                String tipoUsr = usr.getTipo();

                strU += "nodeUsr" + codigo + "[label = \" Codigo:" + codigo + "\\n Nombre: " + nombUsr
                        + "\\n Contrasena: " + passUsr + "\\n Tipo: " + tipoUsr + "\"];\n";

                NodoListDob<Usuario> usrAnt = ultimo.getAnt();
//                Usuario usrSig = ultimo.getAnt().getInfo();
                if (usrAnt != null) {
                    Usuario usrAnt2 = usrAnt.getInfo();
                    String codigo2 = usrAnt2.getId();
                    strU += "nodeUsr" + codigo2 + " -> " + "nodeUsr" + codigo + ";\n";
                    strU += "nodeUsr" + codigo + " -> " + "nodeUsr" + codigo2 + ";\n";
                }

            }
            ultimo = ultimo.getSig();
        }
        return strU;
    }

    private String genStrEstudiantes(String strE) {
        strE += "ESTU [label=<\n";
        strE += "<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">";
        NodoHash<Estudiante>[] vectorH = this.guardadoDatos.getEstudiantes().getVectorH();
        boolean insertar = false;
        Estudiante estIns = null;
        String codigo = "";
        for (int i = 0; i < vectorH.length; i++) {
            insertar = false;
            codigo = "";
            NodoHash<Estudiante> nodoHash = vectorH[i];
            if (nodoHash != null) {
                if (nodoHash.getEstado() == 'a') {
                    insertar = true;
                }
            }
            if (insertar) {
                estIns = nodoHash.getValor();
                codigo = estIns.getCarnet();
            }
            strE += "<tr><td port=\"" + i + "\">" + codigo + "</td></tr>";
        }
        strE += "</table>>];\n";
        return strE;
    }

    private String genStrCatedraticos() {
        NodoAvl<Catedratico> raiz = this.guardadoDatos.getCatedraticos().getRaiz();
        inOrdenCatedraticos(raiz, null, null);
        return strC;
    }

    private void inOrdenCatedraticos(NodoAvl<Catedratico> nodo, NodoAvl<Catedratico> nodoPadre, String lado) {
        if (nodo == null) {
            return;
        }
        strC += "nodeC" + nodo.getInfo().getIdentificador() + "[label=\"<f0> |<f1> Id: " + nodo.getInfo().getIdentificador()
                + "\\n Nombre:" + nodo.getInfo().getNombre() + "|<f2>\"];\n";
        if (nodoPadre != null) {
            strC += "nodeC" + nodoPadre.getInfo().getIdentificador() + ": " + lado + " -> nodeC" + nodo.getInfo().getIdentificador() + ":f1;\n";
        }
        inOrdenCatedraticos(nodo.getIzq(), nodo, "f0");
        inOrdenCatedraticos(nodo.getDer(), nodo, "f2");
    }

    private String genStrEdificiosSalones(String strEd) {
        ListaEnlSim<String> nombresEdificios = new ListaEnlSim();
        ListaCircDobEnl<Edificio> edificios = this.guardadoDatos.getEdificios();
        NodoListDob<Edificio> ultimo = edificios.getRaiz();
        while (ultimo != null) {
            Edificio edf = ultimo.getInfo();
            if (edf != null) {
                String codigo = edf.getNombre();

                strEd += "nodeEd" + codigo + "[label = \" Codigo:" + codigo + "\"];\n";
                nombresEdificios.add(nombresEdificios, "nodeEd" + codigo);

                if (edf.getSalones().isEmpty() == false) {
                    strEd = genStrSalones(strEd, "nodeEd" + codigo, edf.getSalones());
                }

                NodoListDob<Edificio> edfAnt = ultimo.getAnt();
                if (edfAnt != null) {
                    Edificio edfAnt2 = edfAnt.getInfo();
                    String codigo2 = edfAnt2.getNombre();
                    strEd += "nodeEd" + codigo2 + " -> " + "nodeEd" + codigo + ";\n";
                    strEd += "nodeEd" + codigo + " -> " + "nodeEd" + codigo2 + ";\n";
                }
            }
            ultimo = ultimo.getSig();
            if (ultimo == edificios.getRaiz()) {
                break;
            }
        }
        strEd = mismoRango(strEd, nombresEdificios);
        return strEd;
    }

    private String genStrSalones(String strEd, String nombreEd, ListaEnlSim<Salon> salones) {
        ListaEnlSim<Salon> temp = salones;
        while (temp != null) {
            Salon salon = temp.getVal();
            if (salon != null) {
                String codigo = salon.getNumero();
                String capacidad = salon.getCapacidad();
                strEd += "nodeSal" + codigo + "[label = \" Codigo:" + codigo
                        + "\\n Capacidad: " + capacidad + "\"];\n";
                strEd += nombreEd + " -> " + "nodeSal" + codigo + ";\n";
                nombreEd = "nodeSal" + codigo;
            }
            temp = temp.getSig();
        }
        return strEd;
    }

    private String mismoRango(String strEd, ListaEnlSim<String> nombresEdificios) {
        ListaEnlSim<String> temp = nombresEdificios;
        strEd += "{rank = same \n";
        while (temp != null) {
            String nombre = temp.getVal();
            if (nombre != null) {
                strEd += nombre + " ";
            }
            temp = temp.getSig();
        }
        strEd += "\n}\n";
        return strEd;
    }

    private void genStrHorarios(NodoB<Horario> x, int name) {
        assert (x == null);
        strH += "nodeHo" + name + "[label=\"";
        for (int i = 0; i < x.n; i++) {
            if (i != 0) {
                strH += "|";
            }
//            strH += "<f" + i + "> " + x.key[i].llave + " " + x.key[i].getValor().getCodigo();
            strH += "<f" + i + "> " + x.key[i].llave + " \\n Salon: " + x.key[i].getValor().getCodigoSalonStr()
                    + " \\n Curso: " + x.key[i].getValor().getCodigoCursoStr()
                    + " \\n Edificio: " + x.key[i].getValor().getCodigoEdificioStr()
                    + " \\n Catedratico: " + x.key[i].getValor().getCodigoNumIdCatedStr()
                    + " \\n Periodo: " + x.key[i].getValor().getPeriodo()
                    + " \\n Cant Asig: " + x.key[i].getValor().getCantidadAsignados();
        }
        strH += " \"];\n";
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                genStrHorarios(x.child[i], name + i + 1);
//                System.out.print("nodeU"+name+": f0 "+" -> " +"nodeU"+(name+i+1)+": f0;\n" );
                strH += "nodeHo" + name + " -> " + "nodeHo" + (name + i + 1) + ";\n";
            }
        }
    }

    private void genStrTodoHorarios(NodoB<Horario> x, int name) {
        assert (x == null);
        strH += "nodeHo" + name + "[label=\"";
        for (int i = 0; i < x.n; i++) {
            if (i != 0) {
                strH += "|";
            }
//            strH += "<f" + i + "> " + x.key[i].llave + " " + x.key[i].getValor().getCodigo();
            strH += "<f" + i + "> " + x.key[i].llave + " \\n Salon: " + x.key[i].getValor().getCodigoSalonStr()
                    + " \\n Curso: " + x.key[i].getValor().getCodigoCursoStr()
                    + " \\n Edificio: " + x.key[i].getValor().getCodigoEdificioStr()
                    + " \\n Catedratico: " + x.key[i].getValor().getCodigoNumIdCatedStr()
                    + " \\n Periodo: " + x.key[i].getValor().getPeriodo()
                    + " \\n Cant Asig: " + x.key[i].getValor().getCantidadAsignados();
        }
        strH += " \"];\n";
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                genStrTodoHorarios(x.child[i], name + i + 1);
//                System.out.print("nodeU"+name+": f0 "+" -> " +"nodeU"+(name+i+1)+": f0;\n" );
                strH += "nodeHo" + name + " -> " + "nodeHo" + (name + i + 1) + ";\n";
            }
        }
    }

    private void genRelacionHorarios(NodoB<Horario> x, int name) {
        assert (x == null);
        for (int i = 0; i < x.n; i++) {
            Horario horarioIns = x.key[i].getValor();
            strR += "nodeHo" + name + ": " + "<f" + i + "> " + " -> " + "nodeSal" + horarioIns.getCodigoSalonStr() + ";\n";
            strR += "nodeHo" + name + ": " + "<f" + i + "> " + " -> " + "nodeEd" + horarioIns.getCodigoEdificioStr() + ";\n";
            strR += "nodeHo" + name + ": " + "<f" + i + "> " + " -> " + "nodeC" + horarioIns.getCodigoNumIdCatedStr() + ";\n";
            ListaEnlSim<Asignacion> asignaciones = this.guardadoDatos.getAsignaciones();
            ListaEnlSim ultimo = asignaciones;
            while (ultimo != null) {
                Object objErr = ultimo.getVal();
                if (objErr != null) {
                    Asignacion asig = (Asignacion) ultimo.getVal();
                    if (asig.getHorario() == horarioIns) {
                        String codigoU = asig.getCarnetStr();
                        String codigoH = asig.getCodHorarioStr();
                        int nota = Integer.parseInt(asig.getZona()) + Integer.parseInt(asig.getExFinal());
                        strR += "nodeAsi" + codigoU + codigoH + "->" + "nodeHo" + name + ": " + "<f" + i + "> "+";\n";                   
                    }
                }
                ultimo = ultimo.getSig();
            }
        }
        if (!x.leaf) {
            for (int i = 0; i < x.n + 1; i++) {
                genRelacionHorarios(x.child[i], name + i + 1);
//                System.out.print("nodeU"+name+": f0 "+" -> " +"nodeU"+(name+i+1)+": f0;\n" );
//                strH += "nodeHo" + name + " -> " + "nodeHo" + (name + i + 1) + ";\n";
            }
        }
    }

    private String genStrAsignaciones(String strA) {
        ListaEnlSim<Asignacion> asignaciones = this.guardadoDatos.getAsignaciones();
        ListaEnlSim<Asignacion> temp = asignaciones;
        int cont = 0;
        String asAnt = "";
        while (temp != null) {
            Asignacion asig = temp.getVal();
            if (asig != null) {
                String codigoU = asig.getCarnetStr();
                String codigoH = asig.getCodHorarioStr();
                int nota = Integer.parseInt(asig.getZona()) + Integer.parseInt(asig.getExFinal());
                strA += "nodeAsi" + codigoU + codigoH + "[label = \" Codigo Estudiante:" + codigoU
                        + "\\n Codigo Horario: " + codigoH
                        + "\\n Zona: " + asig.getZona()
                        + "\\n Examen Final: " + asig.getExFinal()
                        + "\\n Nota Final: " + nota + "\"];\n";
                if (cont > 1) {
                    strA += asAnt + " -> " + "nodeAsi" + codigoU + codigoH + ";\n";
                }
                asAnt = "nodeAsi" + codigoU + codigoH;
            }
            cont++;
            temp = temp.getSig();
        }
        return strA;
    }

    private String genStrSoloUsuarios() {
        String strU = "digraph U {\n";
        strU += "node [shape=box]\n";
        strU = genStrUsuarios(strU);
        strU += "}";

        return strU;
    }

    private String genStrSoloEstudiantes() {
        String strE = "digraph E {\n";
        strE += "node [shape=plain]\n";
        strE += "rankdir=LR;\n";
        strE = genStrEstudiantes(strE);
        strE += "}";
        return strE;
    }

    private String genStrSoloCatedraticos() {
        strC = "digraph E {\n";
        strC += "node [shape=record];\n";
        strC = genStrCatedraticos();
        strC += "}\n";
        return strC;
    }

    private String genStrSoloEdificiosYSalones() {
        String strEd = "digraph EdS {\n";
        strEd += "node [shape=box]\n";
        strEd = genStrEdificiosSalones(strEd);
        strEd += "}";
        return strEd;
    }

    public void genStrSoloHorarios() {
        strH = "digraph G {";
        strH += "node [shape=record];";
        genStrHorarios(this.guardadoDatos.getHorarios().getRoot(), 1);
        strH += "}";
    }

    private String genStrSoloAsignaciones() {
        String strAs = "digraph Asg {\n";
        strAs += "node [shape=box]\n";
        strAs = genStrAsignaciones(strAs);
        strAs += "}";
        return strAs;
    }

    private String genStrTodo() {
        String strT = "digraph E {\n";
//        strT += "rankdir = LR;\n";
        strT += "subgraph cluster_1 {\n";
        strT += "node [shape=box]\n";
        strT = genStrUsuarios(strT);
        strT += "};\n";
        strT += "subgraph cluster_2 {\n";
        strT += "node [shape=plain]\n";
        strT += "rankdir=LR;\n";
        strT = genStrEstudiantes(strT);
        strT += "};\n";
        strT += "subgraph cluster_4 {\n";
        strT += "node [shape=record];\n";
        this.strC = "";
        strT += genStrCatedraticos();
        strT += "};\n";
        strT += "subgraph cluster_5 {";
        strT += "node [shape=box]\n";
        strT = genStrEdificiosSalones(strT);
        strT += "};\n";
        strT += "subgraph cluster_6 {";
        strT += "node [shape=record];";
        this.strH = "";
        genStrTodoHorarios(this.guardadoDatos.getHorarios().getRoot(), 1);
        strT += strH;
        strT += "};\n";
        this.strR = "";
        genRelacionHorarios(this.guardadoDatos.getHorarios().getRoot(), 1);
        strT += strR;
        strT += "subgraph cluster_3 {";
        strT += "node [shape=box]\n";
        strT = genStrAsignaciones(strT);
        strT += "};\n";
        strT += "}\n";

        return strT;
    }

    //Generadores de PNG
    /**
     * Genera la imagen de la memoria de los usuarios
     */
    public void genPNGSoloUsuarios() {
        String entrada = genStrSoloUsuarios();
        generarPNG("usuarios", entrada);
    }

    public void genPNGSoloEstudiantes() {
        String entrada = genStrSoloEstudiantes();
        generarPNG("estudiantes", entrada);
    }

    public void genPNGSoloCatedraticos() {
        String entrada = genStrSoloCatedraticos();
        generarPNG("catedraticos", entrada);
    }

    public void genPNGSoloEdificiosSalones() {
        String entrada = genStrSoloEdificiosYSalones();
        generarPNG("edificios", entrada);
    }

    public void genPNGSoloHorarios() {
        genStrSoloHorarios();
        generarPNG("horarios", this.strH);
    }

    public void genPNGSoloAsignaciones() {
        String entrada = genStrSoloAsignaciones();
        generarPNG("asignaciones", entrada);
    }

    public void genPNGTodo() {
        String entrada = genStrTodo();
        generarPNG("todo", entrada);
    }

    /**
     * Generador de PNG guardado con un nombre y generado por un string con
     * lenguaje dot
     *
     * @param nombre
     * @param entrada
     */
    private void generarPNG(String nombre, String entrada) {
        try {
            String ruta = this.pathGuardado + "/" + nombre + ".dot";
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(entrada);
            bw.close();
//            //Generar Imagen creada por .dot
            ControlTerminal controlTer = new ControlTerminal(ruta, this.pathGuardado + "/" + nombre + ".png");
            controlTer.generarImagen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
