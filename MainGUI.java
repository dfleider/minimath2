import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.*;
import java.util.*;


public class MainGUI {

    String      appName     = "MiniMath";
    MainGUI     mainGUI;
    MenuExp     newFrame    = new MenuExp();
    JButton     enviarComando;
    JTextField  mensajeComando;
    JTextArea   commandBox;
    JTextField  usernameChooser;
    MenuExp     preFrame;
    JPanel      panelGraficos;
    JLabel 		picLabel;

    public static void main(String[] args) {
         
    	
     String exp = "(((( 2 ^ 3 ) * 10)+5 - 27)/2/2 )*3";
        
        
        if (verificadorParentesis(exp))
        {
            String[] expresion = separador(exp); 
            String[] output = infixRPN(expresion);
            
            for (String token : output) {  
                System.out.print(token + " ");  
            }  
            
            double resultadoFinal = evaluarRPN(output);
            
            System.out.println("\n" + resultadoFinal);
        }
        else
        {
            System.out.println("Expresion no tiene misma cantidad de parentesis izquierdos y derechos");
        }

    	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainGUI mainGUI = new MainGUI();
                mainGUI.preDisplay();
            }
        });
    }
   
    public static String[] infixRPN(String[] pedazos) //Basado en http://www.technical-recipes.com/2011/a-mathematical-expression-parser-in-java-and-cpp/
    {
        ArrayList<String> out = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
            
        for (String pedazo:pedazos)    //foreach sobre pedazos
        {
            if (esNumero(pedazo))    // numero
            {
                out.add(pedazo);
            }
            else if(pedazo.equals("("))    //parentesis izquierdo
            {
                stack.push(pedazo);
            }
            else if(pedazo.equals(")"))    //parentesis derecho
            {
                while(!stack.empty() && !stack.peek().equals("("))
                {
                    out.add(stack.pop());
                }
                stack.pop();
            }
            
            else if(esOperador(pedazo))// operador
            {
                while (!stack.empty() && esOperador(stack.peek()))   
                {                      
                       out.add(stack.pop());     
                }  
                stack.push(pedazo);    // Push al nuevo operador del stack  
            }
        }
        while(!stack.empty())
        {
            out.add(stack.pop());       
        }
        String[] output = new String[out.size()];  
        return out.toArray(output);  
    }
    
    public static double evaluarRPN(String[] pedazos) // Basado en http://www.technical-recipes.com/2011/a-mathematical-expression-parser-in-java-and-cpp/
    {
        Stack<String> stack = new Stack<String>();
        
        for (String pedazo : pedazos)
        {
            if (esNumero(pedazo))
            {
                stack.push(pedazo);
            } else if(esOperador(pedazo))
            {
                double primero = Double.parseDouble(stack.pop().replace(",", "."));
                double segundo = Double.parseDouble(stack.pop().replace(",", "."));
                
                double resultado = 0;
                
                
                if (pedazo.equals("+"))
                { resultado = primero + segundo; }
                else if (pedazo.equals("-"))
                { resultado = segundo - primero; }
                else if (pedazo.equals("*"))
                { resultado = primero * segundo; }
                else if (pedazo.equals("/"))
                { resultado = segundo / primero; }
                else if (pedazo.equals("^"))
                { resultado = Math.pow(segundo, primero); }
                
                stack.push(String.valueOf(resultado));
            }
        }
        return Double.parseDouble(stack.pop());
    }
    
    public static boolean esNumero(String numero) // De: http://stackoverflow.com/questions/11241690/regex-for-checking-if-a-string-is-strictly-alphanumeric
    {                                                // y: http://stackoverflow.com/questions/21704583/regex-for-documentfilter-to-match-all-decimal-number-but-also-number-with-just-a
        String pattern= "^[0-9]+[.]?[0-9]{0,}$"; // "^[0-9]*$";
        if(numero.matches(pattern)){
            return true;
        }
        return false;  
    }
    
    public static boolean esOperador(String numero)

    {
        if (numero.equals("+") || numero.equals("-") ||
                numero.equals("*") || numero.equals("/")
                || numero.equals("^"))
        {
            return true;
        } else
        {
            return false;
        }
    }

    public static String[] separador(String expresion)
    {
        expresion = expresion.replace(" ", "");
        ArrayList<String> lista = new ArrayList<String>();
        
        int i = 0;
        
        String aux = "";
        
        while (i < expresion.length())
        {
                if(esOperador(Character.toString(expresion.charAt(i)))
                        || Character.toString(expresion.charAt(i)).equals("(")
                        || Character.toString(expresion.charAt(i)).equals( ")"))
                {        
                    if (!aux.equals(""))
                    {
                        lista.add(aux);                                        //Agregar todo lo que estaba antes del operador o parentesis
                        aux = "";                                            // Reinicializar aux
                    }
                    
                    lista.add(Character.toString(expresion.charAt(i)));    //Agregar operador
                } 
                else // Si es que es un numero
                {
                    aux = aux + Character.toString(expresion.charAt(i));
                }
                i++;
        }
        
        if (!aux.equals(""))
        {
            lista.add(aux);
        }
        
        String[] output = new String[lista.size()];
        return lista.toArray(output);
    }

    public static boolean verificadorParentesis(String expresion)
    {
        int i = 0;
        int izq = 0;
        int der = 0;
        
        while (i < expresion.length())
        {
            if (Character.toString(expresion.charAt(i)).equals("("))
            {
                izq++;
            } 
            else if (Character.toString(expresion.charAt(i)).equals(")"))
            {
                der++;
            }
            i++;
        }
        
        if (izq == der)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }
    
    BufferedImage logo;
    public void preDisplay() {
        newFrame.setVisible(false);
        //newFrame.setResizable(false);
        newFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 

        preFrame = new MenuExp();
        preFrame.setSize(300, 300);
        JButton enterServer = new JButton("Ingresar a MiniMath");
        enterServer.addActionListener(new EntrarProgramaButtonListener());
        JPanel prePanel = new JPanel(new BorderLayout());

        
    	try {
    		
    		logo = ImageIO.read(new File("logo.jpg"));
    		
    	    JLabel picLogo = new JLabel(new ImageIcon(logo));

    	    prePanel.add(picLogo,BorderLayout.CENTER);

    	    System.out.println("Funco");
    
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		System.out.println("no abrio");
    	}

        preFrame.add(BorderLayout.CENTER, prePanel);
        preFrame.add(BorderLayout.SOUTH, enterServer);
       
        preFrame.setVisible(true);
        
    }
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }
    public void display() {
    	JPanel panelCentral = new JPanel(new BorderLayout());
    	panelCentral.setBackground(Color.WHITE);

        JPanel panelMensajes = new JPanel(new BorderLayout());
        panelGraficos = new JPanel(new BorderLayout());

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.GRAY);
                
        mensajeComando = new JTextField(10);
        mensajeComando.requestFocusInWindow();

        enviarComando = new JButton("Send Command");
        enviarComando.addActionListener(new EnviarComandoButtonListener());

        commandBox = new JTextArea();
        commandBox.setEditable(false);
        commandBox.setFont(new Font("Serif", Font.PLAIN, 15));
      
        commandBox.setLineWrap(true);
        commandBox.setSize(300, 500);
        commandBox.setBackground(Color.GRAY);

        panelMensajes.add(new JScrollPane(commandBox), BorderLayout.WEST);


        southPanel.add(mensajeComando, BorderLayout.EAST);
        southPanel.add(enviarComando, BorderLayout.WEST);
        panelMensajes.add(southPanel,BorderLayout.SOUTH);
        JButton botonGrafico = new JButton();

        try {
            Image img = ImageIO.read(getClass().getResource("play.png"));
            botonGrafico.setIcon(new ImageIcon(img));
          } catch (IOException ex) {
          }

        
      JButton boton1 = new JButton();
      
      try {
          Image img = ImageIO.read(getClass().getResource("archivo.png"));
          boton1.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
      
      JButton boton2 = new JButton();
     
      try {
          Image img = ImageIO.read(getClass().getResource("archivo.png"));
          boton2.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
      
      JButton boton3 = new JButton();
     
      try {
          Image img = ImageIO.read(getClass().getResource("archivo.png"));
          boton3.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
        }
     
        
        
        JPanel subPanel = new JPanel();
        subPanel.setBackground(Color.WHITE);
        subPanel.add(boton1);
        subPanel.add(boton2);
        subPanel.add(boton3);

        //Now we simply add it to your main panel.
        panelCentral.add(subPanel, BorderLayout.CENTER);


        botonGrafico.addActionListener(new EntregarGrafico());
        panelGraficos.setBackground(Color.BLACK);
        panelCentral.getPreferredSize();
//        JLabel prueba = new JLabel();
//        prueba.setBackground(Color.WHITE);
//        panelGraficos.add(prueba,BorderLayout.CENTER);
        JLabel logoppal = new JLabel(new ImageIcon(logo));

        logoppal.setBackground(Color.WHITE);
        panelCentral.add(logoppal,BorderLayout.NORTH);
        BufferedImage espacioGraficos;
        try{ 
        	espacioGraficos = ImageIO.read(new File("1.jpg"));
        	JLabel espGraf = new JLabel (new ImageIcon(espacioGraficos.getSubimage(0, 0, 600, 1)));
        	
        	panelGraficos.add(espGraf, BorderLayout.NORTH);
        
	    System.out.println("Funco");
	    
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("no abrio");
	}
        
        //newFrame.add(panelGraficos);
        newFrame.add(BorderLayout.LINE_END,panelGraficos);
        panelCentral.add(botonGrafico,BorderLayout.SOUTH);
        newFrame.add(panelCentral);

       

        
        newFrame.add(panelMensajes, BorderLayout.BEFORE_LINE_BEGINS);
        newFrame.setDefaultCloseOperation(MenuExp.EXIT_ON_CLOSE);

        newFrame.setVisible(true);
        
        
		BufferedImage imagenWn;
    	try {
    		
    		imagenWn = ImageIO.read(new File("felipe.jpg"));
    		
    	    picLabel = new JLabel(new ImageIcon(imagenWn.getSubimage(300, 100, 500, 500)));
    	    picLabel.setVisible(false);
    	    panelGraficos.add(picLabel,BorderLayout.CENTER);
    	    picLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	    System.out.println("Funco");
    
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		System.out.println("no abrio");
    	}
 
    }
    

    class EnviarComandoButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (mensajeComando.getText().length() < 1) {
                // do nothing
            } else if (mensajeComando.getText().equals(".clear")) {
                commandBox.setText("Cleared all messages\n");
                mensajeComando.setText("");
            } else {
                commandBox.append(lineaComando + mensajeComando.getText()
                        + "\n");
                mensajeComando.setText("");
            }
            mensajeComando.requestFocusInWindow();
        }
    }

    String  lineaComando;

    class EntrarProgramaButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            
        	lineaComando="> ";

            if (lineaComando.length() < 1) {
                System.out.println("IngreseComando");
            } else {
                preFrame.setVisible(false);
                display();
            }
        }

    }
    class EntregarGrafico implements ActionListener{
    	public void actionPerformed(ActionEvent event){
    		picLabel.setVisible(true);
    	}
    }
    
    
}