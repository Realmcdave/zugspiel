public class Bahnhof extends Zugnetz
{
    private Datenelement inhalt;
    private boolean markierung; 
    
    public Bahnhof (Datenelement d){
        inhalt = d;
    }
    
    public Datenelement inhaltGeben () {
        return inhalt;
    }
    
    public void markierungSetzen (boolean m) {
        markierung = m;
    }
    
    public boolean markierungGeben () {
        return markierung;
    }
    
    public abstract class Datenelement{
        public abstract void ausgeben ();
    }
        
    
}
