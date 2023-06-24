import java.awt.Color;

public class Spiel {
    // bildschirm
    private int breite;
    private int hoehe;
    private Leinwand leinwand;
    // netz
    private Zugnetz zugnetz;
    // Züge dan hier

    public Spiel(int breite, int hoehe, int BahnhoefeZahl) {
        this.breite = breite;
        this.hoehe = hoehe;
        leinwand = new Leinwand("Zugspiel", breite, hoehe, Color.white);
        leinwand.setzeSichtbarkeit(true);
        zugnetz = new Zugnetz(BahnhoefeZahl, breite, hoehe, leinwand);
    }

    public Zugnetz gibZugnetz() {
        return zugnetz;
    }
}
