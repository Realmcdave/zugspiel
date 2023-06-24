import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Leinwand {

    private static Leinwand leinwandSingleton;

    public static Leinwand gibLeinwand() {
        if (leinwandSingleton == null) {
            leinwandSingleton = new Leinwand("Leinwand", 400, 600, Color.white);
        }
        leinwandSingleton.setzeSichtbarkeit(true);
        return leinwandSingleton;
    }

    private JFrame fenster;
    private Zeichenflaeche zeichenflaeche;
    private Graphics2D graphic;
    private Color hintergrundfarbe;
    private Image leinwandImage;
    private List figuren;
    private Map figurZuShape;

    public Leinwand(String titel, int breite, int hoehe, Color grundfarbe) {
        fenster = new JFrame();
        zeichenflaeche = new Zeichenflaeche();
        fenster.setContentPane(zeichenflaeche);
        fenster.setTitle(titel);
        zeichenflaeche.setPreferredSize(new Dimension(breite, hoehe));
        hintergrundfarbe = grundfarbe;
        fenster.pack();
        figuren = new ArrayList();
        figurZuShape = new HashMap();
    }

    public void setzeSichtbarkeit(boolean sichtbar) {
        if (graphic == null) {
            Dimension size = zeichenflaeche.getSize();
            leinwandImage = zeichenflaeche.createImage(size.width, size.height);
            graphic = (Graphics2D) leinwandImage.getGraphics();
            graphic.setColor(hintergrundfarbe);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        fenster.setVisible(sichtbar);
    }

    public void zeichne(Object figur, String farbe, Shape shape) {
        figuren.remove(figur);
        figuren.add(figur);
        figurZuShape.put(figur, new ShapeMitFarbe(shape, farbe));
        erneutZeichnen();
    }

    public void entferne(Object figur) {
        figuren.remove(figur);
        figurZuShape.remove(figur);
        erneutZeichnen();
    }

    public void setzeZeichenfarbe(String farbname) {
        if (farbname.equals("rot"))
            graphic.setColor(Color.red);
        else if (farbname.equals("schwarz"))
            graphic.setColor(Color.black);
        else if (farbname.equals("blau"))
            graphic.setColor(Color.blue);
        else if (farbname.equals("gelb"))
            graphic.setColor(Color.yellow);
        else if (farbname.equals("gruen"))
            graphic.setColor(Color.green);
        else if (farbname.equals("lila"))
            graphic.setColor(Color.magenta);
        else if (farbname.equals("weiss"))
            graphic.setColor(Color.white);
        else
            graphic.setColor(Color.black);
    }

    public void warte(int millisekunden) {
        try {
            Thread.sleep(millisekunden);
        } catch (Exception e) {

        }
    }

    private void erneutZeichnen() {
        loeschen();
        for (Iterator i = figuren.iterator(); i.hasNext();) {
            ((ShapeMitFarbe) figurZuShape.get(i.next())).draw(graphic);
        }
        zeichenflaeche.repaint();
    }

    private void loeschen() {
        Color original = graphic.getColor();
        graphic.setColor(hintergrundfarbe);
        Dimension size = zeichenflaeche.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    private class Zeichenflaeche extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(leinwandImage, 0, 0, null);
        }
    }

    private class ShapeMitFarbe {
        private Shape shape;
        private String farbe;

        public ShapeMitFarbe(Shape shape, String farbe) {
            this.shape = shape;
            this.farbe = farbe;
        }

        public void draw(Graphics2D graphic) {
            setzeZeichenfarbe(farbe);
            graphic.fill(shape);
        }
    }
}
