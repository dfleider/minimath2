import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuExp extends JFrame {
    
    public MenuExp() {
        
        //setTitle("Menu Example");
        setSize(0, 0);
        
        // Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
        
        // Add the menubar to the frame
        setJMenuBar(menuBar);
        
        // Define and add two drop down menu to the menubar
        JMenu fileMenu = new JMenu("Archivo");
        JMenu editMenu = new JMenu("Editar");
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        
        // Create and add simple menu item to one of the drop down menu
        JMenuItem nuevaAccion = new JMenuItem("Nuevo");
        JMenuItem abrirAccion = new JMenuItem("Abrir");
        JMenuItem salirAction = new JMenuItem("Salir");
        JMenuItem cortarAction = new JMenuItem("Cortar");
        JMenuItem copiarAction = new JMenuItem("Copiar");
        JMenuItem pegarAction = new JMenuItem("Pegar");
        
        // Create and add CheckButton as a menu item to one of the drop down
        // menu
        JCheckBoxMenuItem checkAction = new JCheckBoxMenuItem("Guardar Variable");
        // Create and add Radio Buttons as simple menu items to one of the drop
        // down menu
        JRadioButtonMenuItem graficar = new JRadioButtonMenuItem(
                "Graficar");
        JRadioButtonMenuItem borrar = new JRadioButtonMenuItem(
                "Borrar");
        // Create a ButtonGroup and add both radio Button to it. Only one radio
        // button in a ButtonGroup can be selected at a time.
        ButtonGroup bg = new ButtonGroup();
        bg.add(graficar);
        bg.add(borrar);
        fileMenu.add(nuevaAccion);
        fileMenu.add(abrirAccion);
        fileMenu.add(checkAction);
        fileMenu.addSeparator();
        fileMenu.add(salirAction);
        editMenu.add(cortarAction);
        editMenu.add(copiarAction);
        editMenu.add(pegarAction);
        editMenu.addSeparator();
        editMenu.add(graficar);
        editMenu.add(borrar);
        // Add a listener to the New menu item. actionPerformed() method will
        // invoked, if user triggred this menu item
        nuevaAccion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("You have clicked on the new action");
            }
        });
    }
    }