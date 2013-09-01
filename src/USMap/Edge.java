package USMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 *
 * @author wilmercarranza
 */
public class Edge extends Dibujo {

    private String etiqueta;
    private int indice;
    private ArrayList<String> adyacentes;
    private ArrayList<Destiny> destinos;
    private boolean using;
    boolean esArticulacion;
    float baseFrame;
    float alturaFrame;

    public Edge(float x, float y, float baseFrame, float alturaFrame, float grosor, String etiqueta, boolean using, boolean esArticulacion) {
        super((int) x, (int) y, grosor);
        this.baseFrame = baseFrame;
        this.alturaFrame = alturaFrame;
        this.etiqueta = etiqueta;
        this.adyacentes = new ArrayList<String>();
        indice = -1;
        this.using = using;
        this.esArticulacion = false;
    }

    public Edge(String etiqueta, int indice) {
        super(0, 0, 0);
        this.etiqueta = etiqueta;
        adyacentes = new ArrayList<String>();
        this.indice = indice;
        destinos = new ArrayList<Destiny>();
    }

    public boolean isUsing() {
        return using;
    }

    public void setUsing(boolean using) {
        this.using = using;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public int getIndice() {
        return indice;
    }

    public void agregarAdyacente(String s) {
        adyacentes.add(s);
    }

    public void agregarDestino(Destiny d) {
        destinos.add(d);
    }

    public ArrayList<Destiny> getDestinos() {
        return destinos;
    }

    @Override
    public void paint(Graphics g) {
        Shape circ = new Ellipse2D.Float(x, y, baseFrame, alturaFrame);
        Graphics2D ga = (Graphics2D) g;
        ga.draw(circ);
        ga.setStroke(new BasicStroke(grosor));
        if (using) {
            ga.setPaint(Color.GREEN);
        } else {
            ga.setPaint(Color.RED);
        }

        if (esArticulacion) {
            ga.setPaint(Color.BLUE);
        }

        ga.fill(circ);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, (int) baseFrame, (int) alturaFrame);
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void setBaseFrame(float baseFrame) {
        this.baseFrame = baseFrame;
    }

    public void setAlturaFrame(float alturaFrame) {
        this.alturaFrame = alturaFrame;
    }

    public float getBaseFrame() {
        return baseFrame;
    }

    public float getAlturaFrame() {
        return alturaFrame;
    }

    public boolean estaEnMi(int x, int y) {
        if (x > this.x && x < this.x + baseFrame && y > this.y && y < this.y + alturaFrame) {
            return true;
        } else {
            return false;
        }
    }
}
