/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package USMap;

/**
 *
 * @author wilmercarranza
 */
public class Destiny {

    private String nombre;
    private double costo;
    private int number;

    public Destiny(String nombre, double costo, int indice) {
        this.nombre = nombre;
        this.costo = costo;
        this.number = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCosto() {
        return costo;
    }

    public int getNumber() {
        return number;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void setIndice(int indice) {
        this.number = indice;
    }
}
