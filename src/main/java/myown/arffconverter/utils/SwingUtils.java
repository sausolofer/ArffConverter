/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myown.arffconverter.utils;

import java.awt.Frame;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author solorio
 */
public class SwingUtils {
    
    public static File getFile(String... extensions) {
        // TODO add your handling code here:
        File file = null;
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("").getAbsoluteFile());
        fc.addChoosableFileFilter(new JFileFilter(extensions));
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        }
        return file;
    }
    
    public static File saveFile(String... extensions) {
        File file = null;
        Frame parentFrame = new Frame();
        parentFrame.setIconImage(new ImageIcon("/images/MEDYP2_32x32.png").getImage());
        JFileChooser fc = new JFileChooser();
        
        fc.setDialogTitle("Guardar archivo como");
        fc.setCurrentDirectory(new File("output").getAbsoluteFile());
        fc.addChoosableFileFilter(new JFileFilter(extensions));
        int userSelection = fc.showSaveDialog(parentFrame);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            System.out.println("Save as file: " + file.getAbsolutePath());
        }
        return file;
        
    }
    
    private static class JFileFilter extends FileFilter {
        
        String[] extensions;
        
        public JFileFilter(String... extensions) {
            this.extensions = extensions;
        }
        
        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }
            String s = f.getName();
            int i = s.lastIndexOf('.');
            
            if (i > 0 && i < s.length() - 1) {
                for (String item : extensions) {
                    if (s.substring(i + 1).toLowerCase().equals(item)) {
                        return true;
                    }
                }
            }
            
            return false;
        }
        
        @Override
        public String getDescription() {
            return StringUtils.join(extensions, ",");
        }
    }
}
