package USMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.io.Serializable;

/**
 *
 * @author wilmercarranza
 */
abstract class Dibujo implements Serializable {

    Color color;
    float grosor;
    int x, y;

    public Dibujo(int x, int y, float grosor) {
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
        this.grosor = grosor;
    }

    abstract void paint(Graphics g);

    abstract boolean estaEnMi(int x, int y);

    public void setGrosor(float grosor) {
        this.grosor = grosor;
    }

    public float getGrosor() {
        return grosor;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Color getColor() {
        return color;
    }
}
