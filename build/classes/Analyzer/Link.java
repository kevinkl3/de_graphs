/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyzer;

/**
 *
 * @author wilmercarranza
 */
public class Link {

    private Edge destino;
    private String info;

    public Link(Edge destino, String info) {
        this.destino = destino;
        this.info = info;
    }

    public Link(Edge destino) {
        this.destino = destino;
        info = "None";
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Edge getDestino() {
        return destino;
    }

    public void setDestino(Edge otro) {
        this.destino = otro;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return destino + ", " + getInfo();
    }
}
