public class Zugnetz
{
    private Bahnhof[] bahnhofsliste; 
    private boolean[][] adjazenzmatrix;
    private int maxAnzahl; 
    private int anzahl;
    
    public Zugnetz(int b)
    {
        maxAnzahl = b;
        anzahl = 0;
        bahnhofsliste = new Bahnhof [b];       
        adjazenzmatrix = new boolean [b] [b];
    }

    public void knotenEinfuegen (Bahnhof b) {
        if (anzahl < maxAnzahl) {
            bahnhofsliste[anzahl] = b;
            anzahl = anzahl + 1;
        }
        else {
            System.out.println("Fehler - Die maximale Anzahl an Bahnhoefen ist schon erreicht");
}
}

    public int knotenindexSuchen (Bahnhof b) {
            int index = -1;
            int i = 0;
        while (index < 0 && 1 < bahnhofsliste.length) {
            if (bahnhofsliste[i].equals(b) ) {
                index = 1;
            }
            else {
                i = i+1;
            }
            if (index < 0) {
                System.out.println ("Bahnhof in der Liste nicht gefunden!");
            }
        return index;
}
}

    public void VerbindungEinfuegen (int i, int j) {
        if (i > anzahl || j > anzahl) {
            System.out.println(" Falscher Index. So viele Bahnhoefe gibt es nicht!") ;
        }
        else {
            adjazenzmatrix [i][j] = true;
}
}

    public void VerbindungEntfernen (int i, int j) {
        if (!adjazenzmatrix [i][j] || i >= anzahl || j >= anzahl) {
            System.out.println("Diese Verbindung existiert nicht!");
        }
        else {
            adjazenzmatrix [i][i] = false;
}
}

    public void adjazenzmatrixAusgeben () {
        System.out.print ("");
        for (int i = 0; i <anzahl; i++){
        System.out.print (i+" ");
    }
        System.out.println("");
        for (int i = 0; i < anzahl; i++){
            System.out.print (i+" ") ;
            for (int j = 0; j < anzahl; j++) {
                if (adjazenzmatrix [i][j]) {
                  System.out.print("X ");  
                }
                else{       
                    System.out.print ("- ") ;
                }
            }   
        System.out.println ();
    }
}

    public Bahnhof [] knotenlisteGeben ( ) {
        return bahnhofsliste;
}

    public void bahnhofslisteAusgeben () {
        for (int i = 0; i < anzahl; i++) {
            System.out.print ("__ Knoten mit den Daten ") ;
            bahnhofsliste[i].inhaltGeben().ausgeben();
            System.out.print ("--");
}
}
}


