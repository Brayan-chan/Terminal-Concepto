import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public final class Consola extends JFrame implements KeyListener{
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private JLabel labelTitulo = new JLabel("Consola BCA");
    private JTextField texto = new JTextField();
    private JLabel label2 = new JLabel("Ingresa el comando: ");
    private JTextArea areaTerminal = new JTextArea("");
    private boolean metalera =  false;
    private Process p;
    private String textoTerminal = "";
    public Consola(){
        initComponents();
        setIconImage(getIconImage());
        setTitle("BCA-CMD");
        setLayout(new GridLayout(2,1));
        setBounds(200,50,600,350);
        //setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public JTextField getTexto() {
        return texto;
        
    }
    public void setTexto(JTextField texto) {
        this.texto = texto;
        
    }
    public void initComponents(){
        panel.setLayout(null);
        panel.setBackground(Color.black);
        label.setIcon(new ImageIcon(getClass().getResource("terminal.jpg")));
        label.setBackground(Color.black);
        label.setBounds(0, 0, 100, 90);
        label2.setBackground(Color.black);
        label2.setForeground(Color.green);
        label2.setFont(new Font("consolas",1,12));
        label2.setBounds(0, 50, 150, 100);
        labelTitulo.setBounds(200,10,300,20);
        labelTitulo.setBackground(Color.black);
        labelTitulo.setForeground(Color.red);
        labelTitulo.setFont(new Font("consolas",1,20));
        getTexto().setBounds(150,90,400,20);
        getTexto().setForeground(Color.green);
        getTexto().setBackground(Color.black);
        getTexto().setSelectionColor(Color.red);
        getTexto().setFont(new Font("consolas",1,12));
        areaTerminal.setBounds(0,150,300,300);
        areaTerminal.setBackground(Color.black);
        areaTerminal.setForeground(Color.green);
        areaTerminal.setSelectionColor(Color.red);
        areaTerminal.setFont(new Font("consolas",1,10));
        areaTerminal.setEnabled(false);
        panel.add(labelTitulo);
        panel.add(label);
        panel.add(label2);
        panel.add(getTexto());

        add(panel,BorderLayout.NORTH);
        add(new JScrollPane(areaTerminal),BorderLayout.SOUTH);
        getTexto().addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()== KeyEvent.VK_ENTER){
                    if(getTexto().getText().equals(""))
                        metalera = false;
                    else
                        metalera = true;
                    if(metalera){
                        String cadena = getTexto().getText();
                        mensageSalida(cadena);
                    }
                    getTexto().setText("");
                }
            }
        });
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }

    public void mensageSalida(final String cadena){
        try {
            if(cadena.equals("cls") ||cadena.equals("clear"))
                textoTerminal = "";
            try {
                p = Runtime.getRuntime().exec(cadena);
            } catch (IOException iOException) {
                p = Runtime.getRuntime().exec("cmd /c " + cadena);
            }
            InputStream salida = p.getInputStream(); 
            BufferedReader leer;
            leer = new BufferedReader(new InputStreamReader(salida));
            String lectura = leer.readLine();             
            while (lectura != null) { 
                System.out.println (lectura);
                textoTerminal += lectura + "\n";
                 areaTerminal.setText(textoTerminal);
                lectura = leer.readLine(); 
            } 
        }  
        catch (Exception e) { 
            areaTerminal.setText(e + ""); 
        }
    }

    
    
}