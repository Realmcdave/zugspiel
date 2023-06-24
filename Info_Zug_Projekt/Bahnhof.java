public class Bahnhof {
    private int x;
    private int y;
    private String name;

    // konstrucktor
    public Bahnhof(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    // alle setter und getter

    public void xSetzen(int x) {
        this.x = x;
    }

    public void ySetzen(int y) {
        this.y = y;
    }

    public void nameSetzen(String name) {
        this.name = name;
    }

    public int xGeben() {
        return x;
    }

    public int yGeben() {
        return y;
    }

    public String nameGeben() {
        return name;
    }

}
