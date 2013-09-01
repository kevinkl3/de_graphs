package USMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author wilmercarranza
 */
public class Arista extends Dibujo {

    private int x2;
    private int y2;
    private String inicio;
    private String fin;
    private double cost;
    private double latency;
    private boolean using;

    public String getEnd() {
        return fin;
    }

    public double getLatency() {
        return latency;
    }

    public void setLatency(double latencia) {
        this.latency = latencia;
    }

    public String getStart() {
        return inicio;
    }

    public Arista(int x1, int y1, int x2, int y2, float grosor, String inicio, String fin, double costo, double latencia, boolean using) {
        super(x1, y1, grosor);
        this.latency = latencia;
        this.x2 = x2;
        this.y2 = y2;
        this.inicio = inicio;
        this.fin = fin;
        this.cost = costo;
        this.using = using;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double costo) {
        this.cost = costo;
    }

    @Override
    public void paint(Graphics pincel) {
        Graphics2D g2 = (Graphics2D) pincel;
        g2.setFont(new Font("Ubuntu", Font.PLAIN, 13));
        g2.setStroke(new BasicStroke(2));
        if (using) {
            pincel.setColor(Color.WHITE);
        } else {
        pincel.setColor(Color.BLACK);
        }
        pincel.drawLine(x, y, x2, y2);
        if (x2 >= x && y2 >= y) {
            pincel.drawString("$ " + String.valueOf(cost) + "  " + "Lat: " + latency, x + (x2 - x) / 2, y + (y2 - y) / 2);
        } else if (x2 >= x && y2 < y) {
            pincel.drawString("$ " + String.valueOf(cost) + "  " + "Lat: " + latency, x + (x2 - x) / 2, y2 + (y - y2) / 2);
        } else if (x2 < x && y2 >= y) {
            pincel.drawString("$ " + String.valueOf(cost) + "  " + "Lat: " + latency, x2 + (x - x2) / 2, y + (y2 - y) / 2);
        } else if (x2 < x && y2 < y) {
            pincel.drawString("$ " + String.valueOf(cost) + "  " + "Lat: " + latency, x2 + (x - x2) / 2, y2 + (y - y2) / 2);
        }
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public int getX2() {
        return x2;
    }

    @Override
    public boolean estaEnMi(int px, int py) {
        return false;
    }

    public void setUsing(boolean g) {
        this.using = g;
    }
}