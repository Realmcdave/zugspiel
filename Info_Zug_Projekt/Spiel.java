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
        System.out.println("Richards Reisen:");
        System.out.println(
                "Richards Startpunkt ist der Punkt 0 und der rot markierte Punk ist der Ort an dem seine Freunde auf ihn warten.");
        System.out.println(
                "Die Nachbarstädte von Richard sind auch nummeriert, aber er weiß nicht welcher Ort welche Nummer besitzt und muss so durch Ausprobieren und dem Merken der Nummern seinen Weg finden.");
        System.out.println(
                "Du kannst Richard helfen in dem du eine Nummer in das Eingabefeld gibst. Wenn eine der verbundenen Nachbarstädte diese Nummer trägt fährt Richard in diese Stadt. Wenn nicht, dann musst du es mit einer anderen Nummer oder mit einer zufälligen Nummer (Eingabe: Zufall) versuchen.");
        System.out.println("Wenn eine der verbundenen Nachbarstädte diese Nummer trägt fährt Richard in diese Stadt.");
        System.out.println(
                "Wenn nicht, dann musst du es mit einer anderen Nummer oder mit einer zufälligen Nummer (Eingabe: Zufall) versuchen.");
        System.out.println("Euer Ziel ist wie bereits gesagt, dass Richard schnellstmöglich seine Freunde findet.");
        System.out.println("Let's go");
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
            if (zugnetz.getZug().getBahnhofIndex() == ZielBahnhofIndex) {
                handleGewinn();
            }
            return;
        }
        try {
            int index = Integer.parseInt(in);
            if (index >= 0 && index < zugnetz.getAnzahl()) {
                if (zugnetz.zugZuBahnHofBewegen(index) && index == ZielBahnhofIndex) {
                    handleGewinn();
                }
            } else {
                System.out.println("Fehler - Bitte gib eine Zahl zwischen 0 und " + (zugnetz.getAnzahl() - 1) + " ein");
            }

        } catch (Exception e) {
            System.out.println("Fehler - Bitte gib eine Zahl ein");
        }
    }

    public void handleGewinn() {
        System.out.println("Gewonnen! Du hast " + zuege + " gebraucht!");
        System.exit(0);
    }

}
