package analizadores;
import java_cup.runtime.*; 
import analizadores.sym.*;
import objetos.ErrorCom;
import estructuras.ListaEnlSim;

%%

//Configuracion JFLEX
%public
%class LexerSAC
%standalone
%line
%column
%cup

//Expresiones Regulares
//Palabras Reservadas
usuario = "Usuario"
edificio = "Edificio"
salon = "Salon"
curso = "Curso"
estudiante = "Estudiante"
catedratico = "Catedratico"
horario = "Horario"
asignar = "Asignar"
tipo = ("super"|"colaborador"|"estudiante")

//Expresiones
num = [0-9]+
pal = [a-zA-ZÀ-ÿ0-9:\-\u00f1\u00d1]+
pal2 = "\""({pal}|[ ])+"\""
blancos = [ \r\t\b\f\n]+



//Codigo Incrustado
%{          
    ListaEnlSim<ErrorCom> erroresCom;

    private void error(String lexeme) {
        erroresCom.add(erroresCom,new ErrorCom("Lexico","Simbolo no existe en el lenguaje",String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }    

    private void errorPrueba(String lexeme,String desc) {
        erroresCom.add(erroresCom,new ErrorCom("PRUEBA",desc,String.valueOf(yyline+1),String.valueOf(yycolumn+1),lexeme));
    }

    public ListaEnlSim<ErrorCom> getErroresCom() {
        return erroresCom;
    }
    

%}

%init{
    erroresCom = new ListaEnlSim<>();
%init}

%%


//Reglas Lexicas
<YYINITIAL> {
    "," {return new Symbol(sym.COMA,yyline+1,yycolumn+1, yytext());}    
    ";" {return new Symbol(sym.PUNTCOMA,yyline+1,yycolumn+1, yytext());}
    "(" {return new Symbol(sym.PARI,yyline+1,yycolumn+1, yytext());} 
    ")" {return new Symbol(sym.PARD,yyline+1,yycolumn+1, yytext());}
    //"\"" {return new Symbol(sym.COMDOB,yyline+1,yycolumn+1, yytext());}
    {usuario} {return new Symbol(sym.USUARIO,yyline+1,yycolumn+1, yytext());}
    {edificio} {return new Symbol(sym.EDIFICIO,yyline+1,yycolumn+1, yytext());}
    {salon} {return new Symbol(sym.SALON,yyline+1,yycolumn+1, yytext());}
    {curso} {return new Symbol(sym.CURSO,yyline+1,yycolumn+1, yytext());}
    {estudiante} {return new Symbol(sym.ESTUDIANTE,yyline+1,yycolumn+1, yytext());}
    {catedratico} {return new Symbol(sym.CATEDRATICO,yyline+1,yycolumn+1, yytext());}
    {horario} {return new Symbol(sym.HORARIO,yyline+1,yycolumn+1, yytext());}
    {asignar} {return new Symbol(sym.ASIGNAR,yyline+1,yycolumn+1, yytext());}    
    {num} {return new Symbol(sym.NUM,yyline+1,yycolumn+1, yytext());}  
    {tipo} {return new Symbol(sym.TIPO,yyline+1,yycolumn+1, yytext());}      
    {pal2} {return new Symbol(sym.PAL,yyline+1,yycolumn+1, yytext().replace("\"", ""));}
    {pal} {return new Symbol(sym.PAL,yyline+1,yycolumn+1, yytext());}    
    {blancos} {}
}

/* Error por cualquier otro simbolo*/
[^]
		{ error(yytext()); new Symbol(sym.error,yyline,yycolumn, yytext());}
