import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Zugnetz {
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
        this.zug = new Zug(leinwand, 0);
        // zugnetz
        maxAnzahl = b;
        anzahl = 0;
        knotenliste = new Knoten[b];
        adjazenzmatrix = new boolean[b][b];
        randomBahnhofGeneration(b);
        // zeichne alle Knoten
        zeichneKnotenliste();
        zeichenAdjezenzenMatrix();
        // zeichne Zug
        zug.setX(knotenliste[0].inhaltGeben().xGeben());
        zug.setY(knotenliste[0].inhaltGeben().yGeben());
        zug.zeichneZug();
    }

    // Graph schmarn
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

    public void VerbindungEinfuegen(int i, int j) {
        if (i > anzahl || j > anzahl) {
            System.out.println(" Falscher Index. So viele Bahnhoefe gibt es nicht!");
        } else {
            adjazenzmatrix[i][j] = true;
            adjazenzmatrix[j][i] = true;
        }
    }

    public boolean gibVerbindung(int x, int y) {
        return (adjazenzmatrix[x][y] || adjazenzmatrix[y][x]);
    }

    // zug bewegen funktionen
    public boolean zugZuBahnHofBewegen(int b) throws InterruptedException {
        if (b > anzahl) {
            System.out.println("Dummer Hund - So viele Bahnhoefe gibt es nicht!");
            return false;
        }
        if (!adjazenzmatrix[zug.getBahnhofIndex()][b]) {
            System.out.print(
                    "Dummer Hund keine Verbindung zwischen " + zug.getBahnhofIndex() + " und " + b + " vorhanden");
            return false;
        }
        System.out.println("Zug bewegt sich von " + zug.getBahnhofIndex() + " zu " + b);
        Bahnhof bahnhof = (Bahnhof) knotenliste[b].inhaltGeben();
        zug.bewegeZug(bahnhof.xGeben(), bahnhof.yGeben());
        zug.setBahnhofIndex(b);
        return true;
    }

    public void zugZuRandomBahnhofBewegen() throws InterruptedException {
        // GET RANDOM BAHNHOF VON DER ADJAZENZEN MATRIX
        boolean[] verbindungen = adjazenzmatrix[zug.getBahnhofIndex()];
        ArrayList<Integer> verfuegbareBahnhofe = new ArrayList<Integer>();
        for (int i = 0; i < verbindungen.length; i++) {
            if (verbindungen[i]) {
                verfuegbareBahnhofe.add(i);
            }
        }
        int randomBahnhof = verfuegbareBahnhofe.get((int) (Math.random() * verfuegbareBahnhofe.size()));
        zugZuBahnHofBewegen(randomBahnhof);
    }

    public int randomZielBahnhofBestimmen() {
        int randomNumber = 0;
        while (randomNumber == 0) {
            randomNumber = (int) (Math.random() * (anzahl - 1));
        }

        Bahnhof b = (Bahnhof) knotenliste[randomNumber].inhaltGeben();
        Rectangle r = new Rectangle(b.xGeben(), b.yGeben(), 30, 30);
        leinwand.zeichne(b, "rot", r);
        return randomNumber;
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
            Rectangle r = new Rectangle(b.xGeben(), b.yGeben(), 30, 30);
            // index into r schreiben
            leinwand.zeichne(b, "schwarz", r);

        }

    }

    public void zeichenAdjezenzenMatrix() {
        for (int i = 0; i < anzahl; i++) {
            for (int j = 0; j < anzahl; j++) {
                if (adjazenzmatrix[i][j]) {
                    Bahnhof b1 = (Bahnhof) knotenliste[i].inhaltGeben();
                    Bahnhof b2 = (Bahnhof) knotenliste[j].inhaltGeben();
                    int finalx1 = b1.xGeben() + 15;
                    int finaly1 = b1.yGeben() + 15;
                    int finalx2 = b2.xGeben() + 15;
                    int finaly2 = b2.yGeben() + 15;
                    int Laenge = (int) Math.sqrt(Math.pow(finalx1 - finalx2, 2)
                            + Math.pow(finaly1 - finaly2, 2));
                    Rectangle verbindung = new Rectangle(finalx1, finaly1, Laenge, 1);
                    // rotate rectangle so that it is between the two stations

                    AffineTransform transform = new AffineTransform();
                    float angle = (float) (Math.atan2(finaly2 - finaly1, finalx2 - finalx1));
                    transform.rotate(angle, finalx1, finaly1);
                    Shape transformed = transform.createTransformedShape(verbindung);
                    leinwand.zeichne(verbindung, "blau", transformed);

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
            if (i != 0 && bahnhofInNaehe(x, y)) {
                System.out.println("Bahnhof in der Nähe. Erstelle neuen Bahnhof");
                i--;
                continue;
            }
            // name des bahnhoefes
            String name = "Bahnhof " + i;
            // erstelle neuen bahnhoef
            Knoten knoten = new Knoten(new Bahnhof(x, y, name));
            // füge bahnhoef in die knotenliste ein
            knotenEinfuegen(knoten);
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
            String name = "" + i;
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

    // gewtter for alle atribute
    public int getAnzahl() {
        return anzahl;
    }

    public Zug getZug() {
        return zug;
    }
}
