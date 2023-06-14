
public class Bahnhof1 extends Bahnhof
{
  
    private String name;
    private String position;

    public Bahnhof1 (String n, String p) {
        name = n;
        position = p;
    }

    public String nameGeben(){
        return name;
    }
    
    public String positionGeben () {
        return postion;
    }
        
    public void ausgeben () {
        System.out.print (name+" "+position);
}
}
