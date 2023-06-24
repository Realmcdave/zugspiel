import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Zugnetz  {
    private Knoten[] knotenliste;
    private boolean[][] adjazenzmatrix;
    private int maxAnzahl;
    private int anzahl;

    private int bildschirmbreite;
    private int bildschirmhoehe;
    private Leinwand leinwand;
    private Zug zug;

    public Zugnetz(int b, int bildschirmbreite, int bildschirmhoehe, Leinwand leinwand) throws InterruptedException {
        // bildschirm
        this.bildschirmbreite = bildschirmbreite;
        this.bildschirmhoehe = bildschirmhoehe;
        this.leinwand = leinwand;
        this.zug = new Zug();
        // zugnetz
        maxAnzahl = b;
        anzahl = 0;
        knotenliste = new Knoten[b];
        adjazenzmatrix = new boolean[b][b];
        KreisBahnhoefeGenereien(b);
        // zeichne alle Knoten
        zeichneKnotenliste();
        zeichenAdjezenzenMatrix();
    }

    public void knotenEinfuegen(Knoten b) {
        if (anzahl < maxAnzahl) {
            knotenliste[anzahl] = b;
            anzahl++;
            System.out.println("Bahnhof " + b.inhaltGeben().nameGeben() + " wurde eingefuegt. Jetzt gibt es " + anzahl
                    + " Bahnhoefe.");
        } else {
            System.out.println("Fehler - Die maximale Anzahl an Bahnhoefen ist schon erreicht");
        }
    }

    public int knotenindexSuchen(Knoten b) {
        int index = -1;
        int i = 0;
        while (index < 0 && 1 < knotenliste.length) {
            if (knotenliste[i].equals(b)) {
                index = 1;
            } else {
                i = i + 1;
            }
            if (index < 0) {
                System.out.println("Bahnhof in der Liste nicht gefunden!");
            }
            return index;
        }
        return index;
    }

    public void VerbindungEinfuegen(int i, int j) {
        System.out.println("Verbindung zwischen " + i + " und " + j + " wird eingefuegt");
        if (i > anzahl || j > anzahl) {
            System.out.println(" Falscher Index. So viele Bahnhoefe gibt es nicht!");
        } else {
            adjazenzmatrix[i][j] = true;
            adjazenzmatrix[j][i] = true;
        }
    }

    public void VerbindungEntfernen(int i, int j) {
        if (!adjazenzmatrix[i][j] || i >= anzahl || j >= anzahl) {
            System.out.println("Diese Verbindung existiert nicht!");
        } else {
            adjazenzmatrix[i][i] = false;
        }
    }

    public boolean gibVerbindung(int x, int y) {
        return (adjazenzmatrix[x][y] || adjazenzmatrix[y][x]);
    }

    public void adjazenzmatrixAusgeben() {
        System.out.print("");
        for (int i = 0; i < anzahl; i++) {
            System.out.print(i + " ");
        }
        System.out.println("");
        for (int i = 0; i < anzahl; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < anzahl; j++) {
                if (adjazenzmatrix[i][j]) {
                    System.out.print("X ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public Knoten[] knotenlisteGeben() {
        return knotenliste;
    }

    public void knotenlisteAusgeben() {
        String ausgabe = "[";
        for (int i = 0; i < anzahl; i++) {
            ausgabe += knotenliste[i].inhaltGeben().nameGeben() + " , ";
        }
        ausgabe += "]";
        System.out.println(ausgabe);
    }

    // checken ob bahnhof in nähe
    public boolean bahnhofInNaehe(int x, int y) {
        int distanz = 100;
        for (int i = 0; i < anzahl; i++) {
            Bahnhof b = (Bahnhof) knotenliste[i].inhaltGeben();
            if (b.xGeben() - distanz < x && x < b.xGeben() + distanz && b.yGeben() - distanz < y
                    && y < b.yGeben() + distanz) {
                return true;
            }
        }
        return false;
    }

    // zeichen funktionen
    public void zeichneKnotenliste() {
        for (int i = 0; i < anzahl; i++) {
            Bahnhof b = (Bahnhof) knotenliste[i].inhaltGeben();
            System.out.println(b.xGeben() + " " + b.yGeben() + " " + b.nameGeben() + " wird gezeichnet");
            leinwand.zeichne(b, "schwarz", new Rectangle(b.xGeben(), b.yGeben(), 30, 30));
        }

    }
    public void zeichneZug(){
        Rectangle rectangle = new Rectangle((int)zug.getX(), (int)zug.getY(), 40, 20);
        leinwand.zeichne(zug, "blau", rectangle);
    }
    public void bewegeZug(int xnew, int ynew) throws InterruptedException {
        float deltaX = xnew - zug.getX();
        float deltaY = ynew - zug.getY();
        float length = (float) Math.sqrt(Math.pow(deltaY, 2) + Math.pow(deltaX, 2));
        while(true) {
            zug.setX((zug.getX() + (deltaX/length)));
            zug.setY((zug.getY() + (deltaY/length)));
            zeichneZug();
            Thread.sleep(5);
            if(Math.abs(zug.getX() - xnew) < 3) {
            zug.setX(xnew);
            zug.setY(ynew);
            zeichneZug();
            break;
            }
        }
    }
    public void zeichenAdjezenzenMatrix() throws InterruptedException {
        for (int i = 0; i < anzahl; i++) {
            for (int j = 0; j < anzahl; j++) {
                if (adjazenzmatrix[i][j]) {
                    Bahnhof b1 = (Bahnhof) knotenliste[i].inhaltGeben();
                    Bahnhof b2 = (Bahnhof) knotenliste[j].inhaltGeben();
                    int Laenge = (int) Math.sqrt(Math.pow(b1.xGeben() - b2.xGeben(), 2)
                            + Math.pow(b1.yGeben() - b2.yGeben(), 2));
                    Rectangle verbindung = new Rectangle(b1.xGeben(), b1.yGeben(), Laenge, 1);
                    // rotate rectangle so that it is between the two stations

                    AffineTransform transform = new AffineTransform();
                    float angle = (float) (Math.atan2(b2.yGeben() - b1.yGeben(), b2.xGeben() - b1.xGeben()));
                    transform.rotate(angle, b1.xGeben(), b1.yGeben());
                    Shape transformed = transform.createTransformedShape(verbindung);

                    System.out.println("Verbindung zwischen " + b1.nameGeben() + " und " + b2.nameGeben()
                            + " wird gezeichnet. Sie liegt bei: " + b1.xGeben() + ", " + b1.yGeben() + " und "
                            + b2.xGeben() + ", " + b2.yGeben() + ". ");
                    leinwand.zeichne(verbindung, "blau", transformed);
                    zug.setX(b1.xGeben());
                    zug.setY(b1.yGeben());

                }
            }
        }
    }
    // graph generieren methodem
    private void randomBahnhofGeneration(int b) {
        for (int i = 0; i < b; i++) {
            // x und y werte über den bildschirm verteilen bahnhoefe sodass sie nicht näher
            // als 50 pixel aneinander sind
            int x = (int) (Math.random() * (bildschirmbreite));
            int y = (int) (Math.random() * (bildschirmhoehe));
            System.out.println("Bahnhof " + i + " an Position " + x + " x " + y + " y");
            if (bahnhofInNaehe(x, y)) {
                System.out.println("Bahnhof in der Nähe. Erstelle neuen Bahnhof");
                i--;
                continue;
            }
            // name des bahnhoefes
            String name = "Bahnhof " + i;
            // erstelle neuen bahnhoef
            Knoten knoten = new Knoten(new Bahnhof(x, y, name));
            // füge bahnhoef in die knotenliste ein

        }
        System.out.println("--------------------");
        System.out.println("Generiere Verbindungen");
        // generiere 1 - 2 verbindungen pro Knoten
        for (int i = 0; i < b; i++) {
            int verbindungen = (int) (Math.random() * 2) + 1;
            for (int j = 0; j < verbindungen; j++) {
                int ziel = (int) (Math.random() * b);
                if (ziel != i && !gibVerbindung(i, ziel)) {
                    VerbindungEinfuegen(i, ziel);
                } else {
                    j--;
                }
            }
        }
    }

    // generiere Graph mit Kreisstruktur
    private void KreisBahnhoefeGenereien(int b) {
        int radius = bildschirmhoehe / 2 - 100;
        int winkel = 360 / b; // winkel zwischen den bahnhoefen
        // generiere alle bahnhoefe
        for (int i = 0; i < b; i++) {
            int x = (int) (Math.cos(Math.toRadians(winkel * i)) * radius + bildschirmbreite / 2);
            int y = (int) (Math.sin(Math.toRadians(winkel * i)) * radius + bildschirmhoehe / 2);
            String name = "Bahnhof " + i;
            Knoten knoten = new Knoten(new Bahnhof(x, y, name));
            knotenEinfuegen(knoten);
        }
        // verbindung immer zum nächsten bahnhof
        for (int i = 0; i < b; i++) {
            if (i == b - 1) {
                VerbindungEinfuegen(i, 0);
            } else {
                VerbindungEinfuegen(i, i + 1);
            }
        }
    }

    // Generiere Graph mit einer Sinvollen Struktur
   private void BahnhoefeGenerieren() {
        Knoten k1 = new Knoten(new Bahnhof(100, 100, "Bahnhof 1"));
        Knoten k2 = new Knoten(new Bahnhof(200, 100, "Bahnhof 2"));
        Knoten k3 = new Knoten(new Bahnhof(300, 200, "Bahnhof 3"));
        Knoten k4 = new Knoten(new Bahnhof(400, 400, "Bahnhof 4"));
        Knoten k5 = new Knoten(new Bahnhof(400, 700, "Bahnhof 5"));

        knotenEinfuegen(k1);
        knotenEinfuegen(k2);
        knotenEinfuegen(k3);
        knotenEinfuegen(k4);
        knotenEinfuegen(k5);

        // verbinfungen einfügen
        VerbindungEinfuegen(0, 1);
        VerbindungEinfuegen(1, 2);
        VerbindungEinfuegen(2, 3);
        VerbindungEinfuegen(3, 4);
        VerbindungEinfuegen(0, 4);
        VerbindungEinfuegen(0, 5);
    }

}
