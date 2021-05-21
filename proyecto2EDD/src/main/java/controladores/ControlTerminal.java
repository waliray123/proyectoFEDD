/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import javax.swing.JOptionPane;

/**
 *
 * @author user-ubunto
 */
public class ControlTerminal {

    private String fileInputPath ;
    private String fileOutputPath;

    public ControlTerminal(String fileInputPath, String fileOutputPath) {
        this.fileInputPath = fileInputPath;
        this.fileOutputPath = fileOutputPath;
//        generarImagen();
    }

    public void generarImagen(){
        String cmd = "dot -Tpng "+ this.fileInputPath + " -o " + this.fileOutputPath;
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec( cmd );
            JOptionPane.showMessageDialog(null,"Imagen creada en : "+ this.fileOutputPath);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
        
    
    
}
