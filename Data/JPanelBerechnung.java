
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
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Hauptfenster
 */
public class JPanelBerechnung extends JPanel implements ActionListener, WindowListener, DocumentListener, ItemListener{
    //Deklarieren
    //JButtons
    public static JButton btnBerechnen;
    public static JButton btnNeuBerechnen;
    
    //Checkboxen
    public static JRadioButton chbKap;
    public static JRadioButton chbInd;
 
    //Eingabefelder
    private static JTextField tfSchein;
    private static JTextField tfWirk;
    private static JTextField tfBlind;
    private static JTextField tfPhi;
    private static JTextField tfPolar;
    private static JTextField tfKartesisch;
    
    //Beschriftungen
    private static JLabel lblBerechnung;
    private static JLabel lblResultat;
    private static JLabel lblSchein;
    private static JLabel lblWirk;
    private static JLabel lblBlind;
    private static JLabel lblPhi;
    private static JLabel lblBelastungsart;
    private static JLabel lblPolar;
    private static JLabel lblKartesisch;
    
    //MenuBar
    private static JMenuBar jmbMenuleiste;
    private static JMenu jmAktion;
    private static JMenu jmHilfe;
    private static JMenuItem jmiBerechnen;
    private static JMenuItem jmiBeenden;
    private static JMenuItem jmiUeberDasProgramm;
    
    //Booleans für die Eingabefelder der Schein-, Wirk- und Blindleistung, sowie Phasenverschiebungswinkel
    private static Boolean scheinOK = true;
    private static Boolean wirkOK = true;
    private static Boolean blindOK = true;
    private static Boolean phiOK = true;
    private static Boolean btnBerechnenOK = false;
    private static Boolean chbSelected = false;
    private static Boolean standart = true;
    
    //Doubles für die Berechnungen
    private static double dbSchein;
    private static double dbWirk;
    private static double dbBlind;
    private static double dbPhi;
    
    //Documents für DocumentListener
    private static Document docSchein;
    private static Document docWirk;
    private static Document docBlind;
    private static Document docPhi;
    
    //Dialogfenster "Über das Programm"
    private JOptionPane DUeberDasProgramm;
    
    //Count für enabling in Berechnungsfenster
    private static int intSchein = 0;
    private static int intWirk = 0;
    private static int intBlind = 0;
    private static int intPhi = 0;
    private static int intSel = 0;
    
    //Case Number für die passende Formel
    private static int intCase = 0;
    private static int intCaseCheck = 0;
    private static int intCaseCheckTemp = 0;
    
    //Kommastellen minimierung
    DecimalFormat f = new DecimalFormat("#0.00"); 
    
    /**
     * Konstruktor
     */
    public JPanelBerechnung(){
        //Layout
        setSize(340,350);
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);

        //JButtons
        //Berechnen
        btnBerechnen = new JButton("Berechnen");
        btnBerechnen.setBounds(30,180,280,25);
        add(btnBerechnen);
        btnBerechnen.setEnabled(false);
        btnBerechnen.setVisible(true);
        btnBerechnen.addActionListener(this);
        //Neu berechnen
        btnNeuBerechnen = new JButton("Resultat verwerfen");
        btnNeuBerechnen.setBounds(30,180,280,25);
        add(btnNeuBerechnen);
        btnNeuBerechnen.setVisible(false);
        btnNeuBerechnen.addActionListener(this);
        
        //JRadioButtones
        lblBelastungsart = new JLabel("Belastungsart:");
        lblBelastungsart.setBounds(30,150,125,25);
        lblBelastungsart.setHorizontalAlignment(JTextField.TRAILING);
        add(lblBelastungsart);
        chbKap = new JRadioButton ("kapazitiv");
        chbKap.setBackground(Color.LIGHT_GRAY);
        chbKap.setBounds(160,150,75,25);
        add(chbKap);
        chbKap.addItemListener(this);
        chbInd = new JRadioButton ("induktiv");
        chbInd.setBackground(Color.LIGHT_GRAY);
        chbInd.setBounds(243,150,75,25);
        add(chbInd);
        chbInd.addItemListener(this);
        
        //Eingabefelder mit Beschriftung
        //Rahmen Beschriftung für Berechnungsfeld
        lblBerechnung = new JLabel("Eingabe Berechnung");
        lblBerechnung.setBounds(30,2,125,25);
        add(lblBerechnung);
        //Scheinleistung
        tfSchein = new JTextField(10);
        tfSchein.setBounds(160,30,150,25);
        add(tfSchein);
        tfSchein.addActionListener(this);
        docSchein = tfSchein.getDocument();
        docSchein.addDocumentListener(this);
        lblSchein = new JLabel("Scheinleistung in VA:");
        lblSchein.setBounds(30,30,125,25);
        lblSchein.setHorizontalAlignment(JTextField.TRAILING);
        add(lblSchein);
        //Wirkleistung
        tfWirk = new JTextField(10);
        tfWirk.setBounds(160,60,150,25);
        add(tfWirk);
        tfWirk.addActionListener(this);
        docWirk = tfWirk.getDocument();
        docWirk.addDocumentListener(this);
        lblWirk = new JLabel("Wirkleistung in W:");
        lblWirk.setBounds(30,60,125,25);
        lblWirk.setHorizontalAlignment(JTextField.TRAILING);
        add(lblWirk);
        //Blindleistung
        tfBlind = new JTextField(10);
        tfBlind.setBounds(160,90,150,25);
        add(tfBlind);
        tfBlind.addActionListener(this);
        docBlind = tfBlind.getDocument();
        docBlind.addDocumentListener(this);
        lblBlind = new JLabel("Blindleistung in var:");
        lblBlind.setBounds(30,90,125,25);
        lblBlind.setHorizontalAlignment(JTextField.TRAILING);
        add(lblBlind);
        //Verschiebungswinkel
        tfPhi = new JTextField(10);
        tfPhi.setBounds(160,120,150,25);
        add(tfPhi);
        tfPhi.addActionListener(this);
        docPhi = tfPhi.getDocument();
        docPhi.addDocumentListener(this);
        lblPhi = new JLabel("Phase in Grad:");
        lblPhi.setBounds(30,120,125,25);
        lblPhi.setHorizontalAlignment(JTextField.TRAILING);
        add(lblPhi);
        
        
        //Rahmen Beschriftung für Resultatenfeld
        lblResultat = new JLabel("Resultat");
        lblResultat.setBounds(30,237,125,25);
        add(lblResultat);
        //Polare Darstellung des Ergebnis
        tfPolar = new JTextField(10);
        tfPolar.setBounds(110,265,200,25);
        tfPolar.setEditable(false);
        add(tfPolar);
        lblPolar = new JLabel("Polar:");
        lblPolar.setBounds(30,265,70,25);
        lblPolar.setHorizontalAlignment(JTextField.TRAILING);
        add(lblPolar);
        //Kartesische Darstellung des Ergebnis
        tfKartesisch = new JTextField(10);
        tfKartesisch.setBounds(110,295,200,25);
        tfKartesisch.setEditable(false);
        add(tfKartesisch);
        lblKartesisch = new JLabel("Kartesisch:");
        lblKartesisch.setBounds(30,295,70,25);
        lblKartesisch.setHorizontalAlignment(JTextField.TRAILING);
        add(lblKartesisch);
        
        //Fenster sichtbar machen
        setVisible(true);
    }

    /**
     * ActionPerformed
     */
    public void actionPerformed(ActionEvent e){
        //Aktion A berechnen
        if(e.getSource() == btnBerechnen){
            try{
                switch(intCase){
                    case 1: tfPolar.setText("case1 \uF74D");
                            dbSchein = Double.parseDouble(tfSchein.getText()); //Scheinwert einlesen
                            dbWirk = Double.parseDouble(tfWirk.getText());  //Wirkwert einlesen
                            standart = false;   //CheckBoxen deaktivieren
                            if((dbSchein > dbWirk) && (dbSchein < 1000000000) && (dbWirk < 1000000000)){
                                if(intCaseCheck == 1){
                                    dbBlind = Math.sqrt(dbSchein*dbSchein - dbWirk*dbWirk); //Induktiver Blindwert berechnen
                                }else{
                                    dbBlind = -1*Math.sqrt(dbSchein*dbSchein - dbWirk*dbWirk); //Kapazitiver Blindwert berechnen
                                }
                                tfBlind.setText(""+f.format(Math.sqrt(dbBlind*dbBlind)));  //Blindwert ausgeben
                                dbPhi = (Math.atan(dbBlind/dbWirk)*360)/(2*Math.PI);  //Phase berechnen
                                tfPhi.setText(""+f.format(dbPhi));  //Phase ausgeben
                                btnBerechnen.setVisible(false);
                                chbInd.setEnabled(false);
                                chbKap.setEnabled(false);
                                btnNeuBerechnen.setVisible(true);
                                if(dbBlind > 0){    
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W + j"+f.format(dbBlind)+" var");    //Kartesische Ausgabe
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^j"+f.format(dbPhi)+"\u00B0");  //Polare Ausgabe
                                }else{
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    dbBlind = Math.abs(dbBlind);
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W - j"+f.format(dbBlind)+" var"); //Kartesische Ausgabe
                                    dbPhi = Math.abs(dbPhi);
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^-j"+f.format(dbPhi)+"\u00B0"); //Polare Ausgabe
                                }                    
                            }else{
                                tfPolar.setText("Ungültige Eingabe");
                                tfKartesisch.setText("Ungültige Eingabe"); 
                            }
                            break;
                    case 2: tfPolar.setText("case2"); 
                            dbWirk = Double.parseDouble(tfWirk.getText());
                            dbBlind = Double.parseDouble(tfBlind.getText());
                            if((dbWirk < 1000000000) && (dbBlind < 1000000000)){
                                dbSchein = Math.sqrt(dbWirk*dbWirk + dbBlind*dbBlind); //Scheinwert berechnen
                                tfSchein.setText(""+f.format(dbSchein));  //Scheinwert ausgeben
                                dbPhi = (Math.atan(dbBlind/dbWirk)*360)/(2*Math.PI);  //Phase berechnen
                                tfPhi.setText(""+f.format(dbPhi));  //Phase ausgeben
                                if(intCaseCheckTemp==1){    
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W + j"+f.format(dbBlind)+" var");    //Kartesische Ausgabe
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^j"+f.format(dbPhi)+"\u00B0");  //Polare Ausgabe
                                }else{
                                    dbPhi = -1*dbPhi;
                                    tfPhi.setText(""+f.format(dbPhi));
                                    dbBlind = -1*dbBlind;
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    dbBlind = Math.abs(dbBlind);
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W - j"+f.format(dbBlind)+" var"); //Kartesische Ausgabe
                                    dbPhi = Math.abs(dbPhi);
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^-j"+f.format(dbPhi)+"\u00B0"); //Polare Ausgabe
                                } 
                                btnBerechnen.setVisible(false);
                                chbInd.setEnabled(false);
                                chbKap.setEnabled(false);
                                btnNeuBerechnen.setVisible(true);
                                intCaseCheckTemp = 0;
                            }else{
                                tfPolar.setText("Ungültige Eingabe");
                                tfKartesisch.setText("Ungültige Eingabe"); 
                            }
                            break;
                    case 3: tfPolar.setText("case3"); 
                            dbBlind = Double.parseDouble(tfBlind.getText());
                            dbPhi = Double.parseDouble(tfPhi.getText());
                            if((dbBlind < 1000000000) && (((dbPhi < 90 && dbPhi >= 0 ) && (dbBlind >= 0)) || ((dbPhi > -90 && dbPhi < 0 ) && (dbBlind < 0)))){
                                dbSchein = dbBlind / (Math.sin((dbPhi*2*Math.PI)/360)); //Scheinwert berechnen
                                tfSchein.setText(""+f.format(dbSchein));  //Scheinwert ausgeben
                                dbWirk = Math.sqrt(dbSchein*dbSchein-dbBlind*dbBlind);  //Wirkwert berechnen
                                tfWirk.setText(""+f.format(dbWirk));  //Wirkwert ausgeben
                                if(dbPhi > 0){    
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W + j"+f.format(dbBlind)+" var");    //Kartesische Ausgabe
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^j"+f.format(dbPhi)+"\u00B0");  //Polare Ausgabe
                                }else{
                                    dbPhi = -1*dbPhi;
                                    tfPhi.setText(""+f.format(dbPhi));
                                    dbBlind = -1*dbBlind;
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    dbBlind = Math.abs(dbBlind);
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W - j"+f.format(dbBlind)+" var"); //Kartesische Ausgabe
                                    dbPhi = Math.abs(dbPhi);
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^-j"+f.format(dbPhi)+"\u00B0"); //Polare Ausgabe
                                } 
                                btnBerechnen.setVisible(false);
                                chbInd.setEnabled(false);
                                chbKap.setEnabled(false);
                                btnNeuBerechnen.setVisible(true);
                                intCaseCheckTemp = 0;
                            }else{
                                tfPolar.setText("Ungültige Eingabe");
                                tfKartesisch.setText("Ungültige Eingabe"); 
                            }
                            break;
                    case 4: tfPolar.setText("case4"); 
                            dbSchein = Double.parseDouble(tfSchein.getText());
                            dbBlind = Double.parseDouble(tfBlind.getText());
                            if((dbSchein > dbBlind) && (dbSchein < 1000000000) && (dbBlind < 1000000000)){
                                dbWirk = Math.sqrt(dbSchein*dbSchein - dbBlind*dbBlind); //Wirkwert berechnen
                                tfWirk.setText(""+f.format(dbWirk));  //Wirkwert ausgeben
                                dbPhi = (Math.atan(dbBlind/dbWirk)*360)/(2*Math.PI);  //Phase berechnen
                                tfPhi.setText(""+f.format(dbPhi));  //Phase ausgeben
                                if(intCaseCheckTemp==1){    
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W + j"+f.format(dbBlind)+" var");    //Kartesische Ausgabe
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^j"+f.format(dbPhi)+"\u00B0");  //Polare Ausgabe
                                }else{
                                    dbPhi = -1*dbPhi;
                                    tfPhi.setText(""+f.format(dbPhi));
                                    dbBlind = -1*dbBlind;
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    dbBlind = Math.abs(dbBlind);
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W - j"+f.format(dbBlind)+" var"); //Kartesische Ausgabe
                                    dbPhi = Math.abs(dbPhi);
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^-j"+f.format(dbPhi)+"\u00B0"); //Polare Ausgabe
                                } 
                                btnBerechnen.setVisible(false);
                                chbInd.setEnabled(false);
                                chbKap.setEnabled(false);
                                btnNeuBerechnen.setVisible(true);
                                intCaseCheckTemp = 0;
                            }else{
                                tfPolar.setText("Ungültige Eingabe");
                                tfKartesisch.setText("Ungültige Eingabe"); 
                            }
                            break;
                    case 5: tfPolar.setText("case5"); 
                            dbSchein = Double.parseDouble(tfSchein.getText());
                            dbPhi = Double.parseDouble(tfPhi.getText());
                            if((dbSchein < 1000000000) && (dbPhi < 90 && dbPhi > -90)){
                                dbWirk = Math.cos((dbPhi*2*Math.PI)/360)*dbSchein; //Wirkwert berechnen
                                tfWirk.setText(""+f.format(dbWirk));  //Wirkwert ausgeben
                                if(intCaseCheck == 1){
                                    dbBlind = Math.sqrt(dbSchein*dbSchein-dbWirk*dbWirk); //Induktiver Blindwert berechnen
                                }else{
                                    dbBlind = -1*Math.sqrt(dbSchein*dbSchein-dbWirk*dbWirk); //Kapazitiver Blindwert berechnen
                                }
                                tfBlind.setText(""+f.format(Math.sqrt(dbBlind*dbBlind)));  //Blindwert ausgeben
                                btnBerechnen.setVisible(false);
                                if(intCaseCheckTemp==1){    
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W + j"+f.format(dbBlind)+" var");    //Kartesische Ausgabe
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^j"+f.format(dbPhi)+"\u00B0");  //Polare Ausgabe
                                }else{
                                    dbPhi = -1*dbPhi;
                                    tfPhi.setText(""+f.format(dbPhi));
                                    dbBlind = -1*dbBlind;
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    dbBlind = Math.abs(dbBlind);
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W - j"+f.format(dbBlind)+" var"); //Kartesische Ausgabe
                                    dbPhi = Math.abs(dbPhi);
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^-j"+f.format(dbPhi)+"\u00B0"); //Polare Ausgabe
                                } 
                                btnBerechnen.setVisible(false);
                                chbInd.setEnabled(false);
                                chbKap.setEnabled(false);
                                btnNeuBerechnen.setVisible(true);
                                intCaseCheckTemp = 0;
                            }else{
                                tfPolar.setText("Ungültige Eingabe");
                                tfKartesisch.setText("Ungültige Eingabe"); 
                            }
                            break;
                    case 6: tfPolar.setText("case6"); 
                            dbWirk = Double.parseDouble(tfWirk.getText());
                            dbPhi = Double.parseDouble(tfPhi.getText());
                            if((dbWirk < 1000000000) && (dbPhi < 90 && dbPhi > -90)){
                                dbSchein = dbWirk/Math.cos((dbPhi*2*Math.PI)/360); //Scheinwert berechnen
                                tfSchein.setText(""+f.format(dbSchein));  //Scheinwert ausgeben
                                if(intCaseCheck == 1){
                                    dbBlind = Math.sqrt(dbSchein*dbSchein-dbWirk*dbWirk); //Induktiver Blindwert berechnen
                                }else{
                                    dbBlind = -1*Math.sqrt(dbSchein*dbSchein-dbWirk*dbWirk); //Kapazitiver Blindwert berechnen
                                }
                                tfBlind.setText(""+f.format(Math.sqrt(dbBlind*dbBlind)));  //Blindwert ausgeben
                                if(intCaseCheckTemp==1){    
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W + j"+f.format(dbBlind)+" var");    //Kartesische Ausgabe
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^j"+f.format(dbPhi)+"\u00B0");  //Polare Ausgabe
                                }else{
                                    dbPhi = -1*dbPhi;
                                    tfPhi.setText(""+f.format(dbPhi));
                                    dbBlind = -1*dbBlind;
                                    JFrameHaupt.cvDreieck.setDreieck(dbSchein,dbWirk,dbBlind,dbPhi);    //Diagramm zeichnen
                                    dbBlind = Math.abs(dbBlind);
                                    tfKartesisch.setText(""+f.format(dbWirk)+" W - j"+f.format(dbBlind)+" var"); //Kartesische Ausgabe
                                    dbPhi = Math.abs(dbPhi);
                                    tfPolar.setText(""+f.format(dbSchein)+" VA*e^-j"+f.format(dbPhi)+"\u00B0"); //Polare Ausgabe
                                } 
                                btnBerechnen.setVisible(false);
                                chbInd.setEnabled(false);
                                chbKap.setEnabled(false);
                                btnNeuBerechnen.setVisible(true);
                                intCaseCheckTemp = 0;
                            }else{
                                tfPolar.setText("Ungültige Eingabe");
                                tfKartesisch.setText("Ungültige Eingabe"); 
                            }
                            break;
                    default: tfPolar.setText("denada");break;
                }
            }catch(NumberFormatException exc){
                tfPolar.setText("Ungültige Eingabe");
                tfKartesisch.setText("Ungültige Eingabe");
            }
        }
        //Aktion B neu berechnen
        if(e.getSource() == btnNeuBerechnen){
            tfSchein.setText("");
            tfWirk.setText("");
            tfBlind.setText("");
            tfPhi.setText("");
            tfPolar.setText("");
            tfKartesisch.setText("");
            btnBerechnen.setVisible(true);
            btnNeuBerechnen.setVisible(false);
            chbKap.setEnabled(true);
            chbKap.setSelected(false);
            chbInd.setEnabled(true);
            chbInd.setSelected(false);
            JFrameHaupt.cvDreieck.setDreieck(0,0,0,0);
        }
        repaint();
    }
    
    /**
    **
    **/
    public void itemStateChanged(ItemEvent e){
        intCaseCheck = 0;
        //Prüfen ob eine Box markiert ist
        if(chbInd.isSelected()){
            intCaseCheck = 1;
        }
        if(chbKap.isSelected()){
            intCaseCheck = 2;
        }
        //Mit case States Zustand der Boxen bestimmen
        switch(intCaseCheck){
            case 1: chbKap.setEnabled(false);
                    chbSelected = true;
                    break;
            case 2: chbInd.setEnabled(false);
                    chbSelected = true;
                    break;
            case 3: chbKap.setEnabled(false);
                    chbInd.setEnabled(false);
                    chbKap.setSelected(false);
                    chbInd.setSelected(false);
                    break;
            default:chbInd.setEnabled(true);
                    chbKap.setEnabled(true);
                    chbSelected = false;
        }
        //Nachfolgend wird überpruft ob der Berechnen Button freigegeben wird
        if(tfSchein.getText().length() > 0){
            intSchein = 1;
        }else{
            intSchein = 0;
        }
        if(tfWirk.getText().length() > 0){
            intWirk = 1;
        }else{
            intWirk = 0;
        }
        if(tfBlind.getText().length() > 0){
            intBlind = 1;
        }else{
            intBlind = 0;
        }
        if(tfPhi.getText().length() > 0){
            intPhi = 1;
        }else{
            intPhi = 0;
        }
        if(intSchein + intWirk + intBlind + intPhi >= 2 && chbSelected){
            btnBerechnen.setEnabled(true);
        }else{
            btnBerechnen.setEnabled(false);
        }
        repaint();
    }
    
    public void changedUpdate(DocumentEvent e){
    }
            
    public void insertUpdate(DocumentEvent e){        
        //Schein und Wirk
        if(tfSchein.getText().length() > 0 && tfWirk.getText().length() > 0){
            tfBlind.setEditable(false);
            tfPhi.setEditable(false);
            if(chbSelected){
                btnBerechnen.setEnabled(true);
            }else{
                btnBerechnen.setEnabled(false);
            }
            intCase = 1;
        }
        //Wirk und Blind
        if(tfWirk.getText().length() > 0 && tfBlind.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfSchein.setEditable(false);
            tfPhi.setEditable(false);
            if(chbSelected){
                btnBerechnen.setEnabled(true);
            }else{
                btnBerechnen.setEnabled(false);
            }
            intCase = 2;
        }
        //Blind und Phi
        if(tfBlind.getText().length() > 0 && tfPhi.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfSchein.setEditable(false);
            tfWirk.setEditable(false);
            chbKap.setEnabled(false);
            chbInd.setEnabled(false);
            intCase = 3;
        }
        //Schein und Blind
        if(tfSchein.getText().length() > 0 && tfBlind.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfWirk.setEditable(false);
            tfPhi.setEditable(false);
            if(chbSelected){
                btnBerechnen.setEnabled(true);
            }else{
                btnBerechnen.setEnabled(false);
            }
            intCase = 4;
        }
        //Schein und Phi
        if(tfSchein.getText().length() > 0 && tfPhi.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfWirk.setEditable(false);
            tfBlind.setEditable(false);
            chbKap.setEnabled(false);
            chbInd.setEnabled(false);
            intCase = 5;
        }
        //Wirk und Phi
        if(tfWirk.getText().length() > 0 && tfPhi.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfSchein.setEditable(false);
            tfBlind.setEditable(false);
            chbKap.setEnabled(false);
            chbInd.setEnabled(false);
            intCase = 6;
        }
        //Sobald ein Winkel eingegeben wird, sollen die Checkboxes deaktiviert werden
        if(tfPhi.getText().length() > 0){
            intCaseCheckTemp = intCaseCheck;
            intCaseCheck = 3;
        }
        //Default
        if(tfSchein.getText().length() > 0){
            intSchein = 1;
        }else{
            intSchein = 0;
        }
        if(tfWirk.getText().length() > 0){
            intWirk = 1;
        }else{
            intWirk = 0;
        }
        if(tfBlind.getText().length() > 0){
            intBlind = 1;
        }else{
            intBlind = 0;
        }
        if(tfPhi.getText().length() > 0){
            intPhi = 1;
        }else{
            intPhi = 0;
        }
        if(chbSelected){
            intSel = 1;
        }else{
            intSel = 0;
        }
        if(intSchein + intWirk + intBlind + intPhi < 2){
            btnBerechnen.setEnabled(false);
            tfSchein.setEditable(true);
            tfWirk.setEditable(true);
            tfBlind.setEditable(true);
            tfPhi.setEditable(true);
            if(tfPhi.getText().length() == 0){
                intCaseCheck = 0;
            }
            //Mit case States Zustand der Boxen bestimmen
            switch(intCaseCheck){
                case 1: chbKap.setEnabled(false);
                        chbSelected = true;
                        break;
                case 2: chbInd.setEnabled(false);
                        chbSelected = true;
                        break;
                case 3: chbKap.setEnabled(false);
                        chbInd.setEnabled(false);
                        chbKap.setSelected(false);
                        chbInd.setSelected(false);
                        break;
                default:chbInd.setEnabled(true);
                        chbKap.setEnabled(true);
                        chbSelected = false;
            }
            intCase = 0;
        }
    }

    public void removeUpdate(DocumentEvent e){
        //intcaseCheck Variabel temporär speichern
        intCaseCheckTemp = intCaseCheck;
        //Schein und Wirk
        if(tfSchein.getText().length() > 0 && tfWirk.getText().length() > 0){
            tfBlind.setEditable(false);
            tfPhi.setEditable(false);
            if(chbSelected){
                btnBerechnen.setEnabled(true);
            }else{
                btnBerechnen.setEnabled(false);
            }
            intCase = 1;
        }
        //Wirk und Blind
        if(tfWirk.getText().length() > 0 && tfBlind.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfSchein.setEditable(false);
            tfPhi.setEditable(false);
            if(chbSelected){
                btnBerechnen.setEnabled(true);
            }else{
                btnBerechnen.setEnabled(false);
            }
            intCase = 2;
        }
        //Blind und Phi
        if(tfBlind.getText().length() > 0 && tfPhi.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfSchein.setEditable(false);
            tfWirk.setEditable(false);
            chbKap.setEnabled(false);
            chbInd.setEnabled(false);
            intCase = 3;
        }
        //Schein und Blind
        if(tfSchein.getText().length() > 0 && tfBlind.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfWirk.setEditable(false);
            tfPhi.setEditable(false);
            if(chbSelected){
                btnBerechnen.setEnabled(true);
            }else{
                btnBerechnen.setEnabled(false);
            }
            intCase = 4;
        }
        //Schein und Phi
        if(tfSchein.getText().length() > 0 && tfPhi.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfWirk.setEditable(false);
            tfBlind.setEditable(false);
            chbKap.setEnabled(false);
            chbInd.setEnabled(false);
            intCase = 5;
        }
        //Wirk und Phi
        if(tfWirk.getText().length() > 0 && tfPhi.getText().length() > 0 ){
            btnBerechnen.setEnabled(true);
            tfSchein.setEditable(false);
            tfBlind.setEditable(false);
            chbKap.setEnabled(false);
            chbInd.setEnabled(false);
            intCase = 6;
        }
        //Sobald ein Winkel eingegeben wird, sollen die Checkboxes deaktiviert werden
        if(tfPhi.getText().length() > 0){
            intCaseCheckTemp = intCaseCheck;
            intCaseCheck = 3;
        }
        //Default
        if(tfSchein.getText().length() > 0){
            intSchein = 1;
        }else{
            intSchein = 0;
        }
        if(tfWirk.getText().length() > 0){
            intWirk = 1;
        }else{
            intWirk = 0;
        }
        if(tfBlind.getText().length() > 0){
            intBlind = 1;
        }else{
            intBlind = 0;
        }
        if(tfPhi.getText().length() > 0){
            intPhi = 1;
        }else{
            intPhi = 0;
        }
        if(chbSelected){
            intSel = 1;
        }else{
            intSel = 0;
        }
        if(intSchein + intWirk + intBlind + intPhi < 2){
            btnBerechnen.setEnabled(false);
            tfSchein.setEditable(true);
            tfWirk.setEditable(true);
            tfBlind.setEditable(true);
            tfPhi.setEditable(true);
            if(tfPhi.getText().length() == 0){
                intCaseCheck = 0;
            }
            //Mit case States Zustand der Boxen bestimmen
            switch(intCaseCheck){
                case 1: chbKap.setEnabled(false);
                        chbSelected = true;
                        break;
                case 2: chbInd.setEnabled(false);
                        chbSelected = true;
                        break;
                case 3: chbKap.setEnabled(false);
                        chbInd.setEnabled(false);
                        chbKap.setSelected(false);
                        chbInd.setSelected(false);
                        break;
                default:chbInd.setEnabled(true);
                        chbKap.setEnabled(true);
                        chbSelected = false;
            }
            intCase = 0;
        }
    }
    
    /**
     * Paint
     */
    public void paint(Graphics g){
        super.paint(g);
        
        //Berechnungsrahmnen
        g.drawLine(15,15,25,15);    //oben
        g.drawLine(150,15,325,15);  //oben
        g.drawLine(325,15,325,220); //seite
        g.drawLine(15,220,325,220); //unten
        g.drawLine(15,15,15,220);   //seite
        //Resultatenrahmen
        g.drawLine(15,250,25,250);  //oben
        g.drawLine(80,250,325,250); //oben
        g.drawLine(325,250,325,335);//seite
        g.drawLine(15,250,15,335);  //seite
        g.drawLine(15,335,325,335); //unten
        
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