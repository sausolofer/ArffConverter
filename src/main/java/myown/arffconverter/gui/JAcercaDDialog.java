/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myown.arffconverter.gui;

/**
 *
 * @author ssf
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//PRIMERO CREAMOS UNA CLASE QUE HERE DE LA CLASE JDialog
public class JAcercaDDialog extends JDialog {

    //AHORA CREAMOS LOS COMPONENTES QUE NECESITAMOS
    JLabel programaLinea1 = new JLabel("Data Converter", JLabel.CENTER);
    JLabel programaLinea2 = new JLabel("Version 1.0", JLabel.CENTER);
    JLabel autor = new JLabel("Developer: Saúl Solorio Fdez.", JLabel.CENTER);
    JLabel derechos = new JLabel("2016", JLabel.CENTER);

    JButton aceptar = new JButton("OK");

    //AHORA HACEMOS LOS PANELES QUE NECESITAMOS PARA ACOMODAR NUESTROS COMPONENTES
    JPanel principal = new JPanel(new BorderLayout(WIDTH, WIDTH));

    //JPanel info = new JPanel(new GridLayout(11, 1));
    JPanel info = new JPanel(new GridLayout(8, 1,WIDTH, -35));
//    ImageIcon image = new ImageIcon(getClass().getResource("/images/MEDYP2_48x48.png"));
//    JLabel logo = new JLabel(image);
    //ImageIcon pplcImage = new ImageIcon(getClass().getResource("/images/PPLCNew3.png"));
    //JLabel pPLClogo = new JLabel(pplcImage);
    JPanel boton = new JPanel(new FlowLayout());

    //CONSTRUCTOR DE LA CLASE
    public JAcercaDDialog() {
        super(new Frame(), "About...", true);
       
        //AGREGAMOS AL PANEL info, LAS TRES ETIQUETAS QUE CREAMOS
        info.add(new JLabel(""));
        //info.add(pPLClogo);
        info.add(programaLinea1);
        info.add(programaLinea2);
        info.add(new JLabel(""));
        info.add(autor);
        info.add(derechos);
               

        info.add(new JLabel(""));
        //logo.setPreferredSize(new Dimension(50, 50));
        //info.add(logo);
        info.add(new JLabel(""));
        //AGREGAMOS AL PANEL boton, EL BOTON QUE CREAMOS
        boton.add(aceptar);

        //AHORA AGREGAMOS AL PANEL principal, LOS PANELES info, boton
        //QUE A SU VEZ CONTIENEN A TODOS LOS COMPONENTES
        principal.add("Center", info);
        principal.add("South", boton);

        //AGREGAMOS EL PANEL PRINCIPAL AL CUADRO DE DIALOGO
        getContentPane().add(principal);

        //ACOMODAMOS EL TAMA¥O DEL DIALOGO DE ACUERDO AL NUMERO DE COMPONENTES QUE TIENE
        setSize(300, 200);
		//pack();

        //INDICAMOS QUE NO PUEDAN CAMBIAR EL TAMA¥O DEL DIALOGO CON EL MOUSE
        setResizable(false);

        //CENTRAMOS EL DIALOGO EN LA PANTALLA
        Dimension pantalla, cuadro;
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        cuadro = this.getSize();

        this.setLocation(((pantalla.width - cuadro.width) / 2), (pantalla.height - cuadro.height) / 2);

        //LE AGREGAMOS EL EVENTO AL BOTON
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dispose();
            }
        });

    }//FIN DEL CONSTRUCTOR DE LA CLASE JAcercaDDialog

}//FIN DE LA CLASE JAcercaDDialog

