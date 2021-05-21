#! /bin/bash 
echo "INICIANDO COMPILACION JFLEX "
java -jar libe/jflex-full-1.8.2.jar LexerSAC.jflex
echo "INICIANDO COMPILACION JAVA CUP"
java -jar libe/java-cup-11b.jar -parser ParserSAC -symbols sym ParserSAC.cup