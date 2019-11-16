
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.URL;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import java.text.*;
import javax.print.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.List.*;
import java.io.File;
import java.io.*;
import java.awt.Graphics;

/**
 * Hauptfenster
 */
public class JFrameHaupt extends JFrame implements ActionListener, WindowListener{
    //Deklarieren
    //MenuBar
    private static JMenuBar jmbMenuleiste;
    private static JMenu jmAktion;
    private static JMenu jmHilfe;
    private static JMenuItem jmiBerechnen;
    private static JMenuItem jmiBeenden;
    private static JMenuItem jmiUeberDasProgramm;
    
    //Dialogfenster "Über das Programm"
    private JOptionPane DUeberDasProgramm;
    
    //Panel für Berechnungen
    private JPanelBerechnung jpBerechnung;
    
    //Canvas für Dreiecks-Darstellung
    public static CanvasDreieck cvDreieck;
    
    /**
     * Konstruktor
     */
    public JFrameHaupt(){
        //Layout
        setTitle("Imaginär");
        setSize(740,410);
        setLayout(null);
        setLocation(100,100);
        setResizable(true);
        
        //Berechnungs Panel
        jpBerechnung = new JPanelBerechnung();
        jpBerechnung.setLocation(0,0);
        add(jpBerechnung);
        
        //Dreiecks Canvas
        cvDreieck = new CanvasDreieck();
        cvDreieck.setLocation(340,0);
        add(cvDreieck);

        //Menuleiste
        jmbMenuleiste = new JMenuBar();
        //Aktion
        jmAktion = new JMenu("Aktion");
            jmiBerechnen = new JMenuItem("Berechnen");
            jmiBerechnen.setEnabled(false);
            jmiBerechnen.addActionListener(this);
            jmAktion.add(jmiBerechnen);
            jmiBeenden = new JMenuItem("Beenden");
            jmiBeenden.setEnabled(true);
            jmiBeenden.addActionListener(this);
            jmAktion.add(jmiBeenden);
        //jmbMenuleiste.setBackground(new Color(242,242,208));
        jmbMenuleiste.add(jmAktion);
        //Hilfe
        jmHilfe = new JMenu("Hilfe");
            jmiUeberDasProgramm = new JMenuItem("Über das Programm");
            jmiUeberDasProgramm.addActionListener(this);
            jmHilfe.add(jmiUeberDasProgramm);
        jmbMenuleiste.add(jmHilfe);
        //Menuleiste hinzufügen
        setJMenuBar(jmbMenuleiste);
        
        //Fenster sichtbar machen
        setVisible(true);
        addWindowListener(this);
    }

    /**
     * ActionPerformed
     */
    public void actionPerformed(ActionEvent e){
        //Aktion A Informationen zum Programm
        if(e.getSource() == jmiUeberDasProgramm){
            DUeberDasProgramm = new JOptionPane();
            DUeberDasProgramm.showMessageDialog(this, String.format("Autor: Nico Korn%nVersion: 0.5 vom 14.04.2014%nProgrammiert mit BlueJ%nGewrappt mit JSmooth%n%nDieses Programm stellt ein Leistungsdreieck grafisch dar%nund gibt die entsprechende komplexxe Gleichung aus."), "Über das Programm", JOptionPane.PLAIN_MESSAGE);
        }
        repaint();
    }
    
    /**
     * WindowListener Methoden
     */
    public void windowClosing(WindowEvent event){
        System.exit(0);
    }
    public void windowIconified(WindowEvent event){ 
    }
    public void windowOpened(WindowEvent event){
    }
    public void windowClosed(WindowEvent event){
    }
    public void windowActivated(WindowEvent event){
    }
    public void windowDeiconified(WindowEvent event){
    }
    public void windowDeactivated(WindowEvent event){
    }
}