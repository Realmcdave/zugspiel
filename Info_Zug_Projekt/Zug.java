import java.awt.Shape;

import java.awt.Rectangle;

public class Zug {
    private Leinwand leinwand;
    private float x;
    private float y;
    private int bahnnhofIndex;

    public Zug(Leinwand leinwand, int b) {
        this.leinwand = leinwand;
        this.bahnnhofIndex = b;
    }

    // setter and getter
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setBahnhofIndex(int b) {
        this.bahnnhofIndex = b;
    }

    public int getBahnhofIndex() {
        return bahnnhofIndex;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    // zeichen funktionen
    public void zeichneZug() {
        Rectangle rectangle = new Rectangle((int) x, (int) y, 40, 20);
        leinwand.zeichne(this, "blau", rectangle);
    }

    public void bewegeZug(int xnew, int ynew) throws InterruptedException {
        float deltaX = xnew - this.getX();
        float deltaY = ynew - this.getY();
        float length = (float) Math.sqrt(Math.pow(deltaY, 2) + Math.pow(deltaX, 2));
        while (true) {
            this.setX((this.getX() + (deltaX / length)));
            this.setY((this.getY() + (deltaY / length)));
            zeichneZug();
            Thread.sleep(5);
            if (Math.abs(this.getX() - xnew) < 3) {
                this.setX(xnew);
                this.setY(ynew);
                zeichneZug();
                break;
            }
        }
    }
}
