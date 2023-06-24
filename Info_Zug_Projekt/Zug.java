import java.awt.Shape;

import javafx.scene.shape.Rectangle;

public class Zug {
    private Leinwand leinwand;
    private float x;
    private float y;

    public Zug(Leinwand leinwand) {
        this.leinwand = leinwand;
    }

    // setter and getter
    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
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
        leinwand.zeichne(this, "blau", (Shape) rectangle);
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
