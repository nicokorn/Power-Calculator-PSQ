
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
public class CanvasDreieck extends Canvas implements ActionListener, WindowListener{
    //Deklarieren
    private static JLabel lblGrafik;
    private int intSchein = 0;
    private int intWirk = 0;
    private int intBlind = 0;
    private int intPhi = 0;
    
    /**
     * Konstruktor
     */
    public CanvasDreieck(){
        //Layout
        setSize(400,350);
        setBackground(Color.LIGHT_GRAY);
        //Fenster sichtbar machen
        setVisible(true);
    }
    
    /**
     * Metohde zum übergeben von Zeinungsangaben für das Dreieck
    **/
    public void setDreieck(double schein, double wirk, double blind, double phi){
        int intSkal = 0;
        // Werte in int verwandeln
        intSchein = (int)(Math.round(schein));
        intWirk = (int)(Math.round(wirk));
        intBlind = (int)(Math.round(blind));
        intPhi = (int)(Math.round(phi));


        //Skalierungsfaktor über Wirkwert bestimmen
        if(intWirk > 280){               
            //Skalierungsfaktor bestimmen
            intSkal = (intWirk / 280)+1;
                
            //Skalieren
            intSchein /=  intSkal;
            intWirk /= intSkal;
            intBlind /= intSkal;
            intSkal = 0;
        }
        if(intWirk < 140 && intWirk > 0){
            //Skalierungsfaktor bestimmen
            intSkal = (280/intWirk)+1;
            
            //Skalieren
            intSchein *= intSkal;
            intWirk *= intSkal;
            intBlind *= intSkal;
            intSkal = 0;
        }
 
        //Skalierungsfaktor über Blindwert bestimmen
        if((intBlind > 100) || (intBlind < -100)){               
            //Skalierungsfaktor bestimmen
            intSkal = Math.abs((intBlind / 100))+1;
            
            //Skalieren
            intSchein /=  intSkal;
            intWirk /= intSkal;
            intBlind /= intSkal;
            intSkal = 0;
        }
        if((intBlind < 50 && intBlind > 0) || (intBlind > -50 && intBlind < 0)){
            //Skalierungsfaktor bestimmen
            intSkal = Math.abs((100/intBlind))+1;
            
            //Skalieren
            intSchein *= intSkal;
            intWirk *= intSkal;
            intBlind *= intSkal;
            intSkal = 0;
        }
        
        //anpassen des Blindwerts aufgrund canvas Gegebenheiten
        intBlind *= -1;
        
        repaint();
    }
    
    public void malePfeil(Graphics b, int vonX, int vonY, int bisX, int bisY) { 
        b.drawLine(vonX, vonY, bisX, bisY); 

        double l = 10.0; // Pfeilspitzenlänge 

        double a = Math.PI/4 - Math.atan2((bisY - vonY),(bisX - vonX)); 
        double c = Math.cos(a)*l; 
        double s = Math.sin(a)*l; 
        b.drawLine(bisX, bisY,(int)(bisX-s), (int)(bisY-c)); 
        b.drawLine(bisX, bisY,(int)(bisX-c), (int)(bisY+s)); 
    } 
    
    /**
     * ActionPerformed
     */
    public void actionPerformed(ActionEvent e){
        repaint();
    }
    
    /**
     * Paint
     */
    public void paint (Graphics g) {
        Graphics2D g2;
        g2 = (Graphics2D) g;
        g2.drawString ("Gaussche Ebene", 30, 20);
         
        //Rahmen
        g2.drawLine(15,15,25,15);    //oben
        g2.drawLine(130,15,370,15);  //oben
        g2.drawLine(370,15,370,335); //seite
        g2.drawLine(15,335,370,335); //unten
        g2.drawLine(15,15,15,335);   //seite
        
        //Skala
        g2.setColor(Color.black);
        malePfeil(g2,45,290,45,45); //y-Achse
        malePfeil(g2,45,175,340,175); //x-Achse
        
        //Dreieck
        g2.setColor(Color.blue);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(45,175+intBlind,45+intWirk,175);    //Schein
        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(45,175,45+intWirk,175);    //Wirk
        g2.setColor(Color.green);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(45,175,45,175+intBlind);    //Blind
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