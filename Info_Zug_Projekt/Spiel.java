import java.awt.Color;
import java.util.Scanner;

public class Spiel {
    // bildschirm
    private int breite;
    private int hoehe;
    private Leinwand leinwand;
    // netz
    private Zugnetz zugnetz;
    private int ZielBahnhofIndex;

    private int zuege;

    // Züge dan hier

    public Spiel(int breite, int hoehe, int BahnhoefeZahl) throws InterruptedException {
        this.breite = breite;
        this.hoehe = hoehe;
        leinwand = new Leinwand("Zugspiel", breite, hoehe, Color.white);
        leinwand.setzeSichtbarkeit(true);
        zugnetz = new Zugnetz(BahnhoefeZahl, breite, hoehe, leinwand);
        zuege = 0;
        // print Geschichte zur console

        // starte game loop
        gameLoop();
    }

    public Zugnetz gibZugnetz() {
        return zugnetz;
    }

    public void gameLoop() throws InterruptedException {
        ZielBahnhofIndex = zugnetz.randomZielBahnhofBestimmen();
        while (true) {
            try (Scanner scanner = new Scanner(System.in)) {
                zuege++;
                String in = scanner.nextLine();
                handleInput(in);
            }

        }
    }

    public void handleInput(String in) throws InterruptedException {
        System.out.println(zuege);
        if (in.equals("exit")) {
            System.exit(0);
        }
        if (in.equals("zufall")) {
            zugnetz.zugZuRandomBahnhofBewegen();
            return;
        }
        try {
            int index = Integer.parseInt(in);
            if (index >= 0 && index < zugnetz.getAnzahl()) {
                if (zugnetz.zugZuBahnHofBewegen(index) && index == ZielBahnhofIndex) {
                    System.out.println("Gewonnen!");
                    System.exit(0);
                }
            } else {
                System.out.println("Fehler - Bitte gib eine Zahl zwischen 0 und " + (zugnetz.getAnzahl() - 1) + " ein");
            }

        } catch (Exception e) {
            System.out.println("Fehler - Bitte gib eine Zahl ein");
        }
    }

}
