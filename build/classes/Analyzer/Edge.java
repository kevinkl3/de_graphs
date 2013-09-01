/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyzer;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author wilmercarranza
 */
public class Edge {

    private String ciudad;
    int low, searchNumber;
    private int number;
    private ArrayList<Link> links;
    public ArrayList<Edge> hijos;
    public ArrayList<Edge> retroceso;
    private boolean visited = false;
    Graphics graphics;

    {
        links = new ArrayList<Link>();
        hijos = new ArrayList<Edge>();
        retroceso = new ArrayList<Edge>();
    }

    public Edge() {
        ciudad = "None";
        searchNumber = -1;
        low = 0;
        number = -1;
    }

    public Edge(String ciudad, int vertexNum) {
        low = 0;
        searchNumber = -1;
        this.ciudad = ciudad;
        this.number = vertexNum;
    }

    public Edge(String ciudad) {

        this.ciudad = ciudad;
    }

    public void setNumber(int v) {
        this.number = v;
    }

    public int getNumber() {
        return this.number;
    }

    @Override
    public String toString() {
        return "<\"" + ciudad + "\", " + number + ">,";
    }

    public String getName() {
        return ciudad;
    }

    public String showConnections() {
        String acum = "";
        for (Link x : this.links) {
            acum += "\n" + x.getDestino().getName();
        }
        return acum + "\n";
    }

    public ArrayList<Edge> getVerticesInArray() { //me devuelve un arraylist con los nodos conectados a este nodo.
        ArrayList<Edge> arr = new ArrayList<Edge>();
        for (Link i : this.links) {
            arr.add(i.getDestino());
        }
        return arr;
    }

    public ArrayList<Link> getLinks() {
        return this.links;
    }

    public void setLatency(Edge destino, String info) {
        if (isLinkedWith(destino)) {
            for (int i = 0; i < links.size(); i++) {
                if (links.get(i).getDestino().equals(destino)) {
                    String temp = links.get(i).getInfo();
                    temp = temp.replace(temp.split(",")[0], info);
                    links.get(i).setInfo(temp);
                }
            }
        }
    }

    public double getLatency(Edge destino) {
        if (isLinkedWith(destino)) {
            for (int i = 0; i < links.size(); i++) {
                if (links.get(i).getDestino().equals(destino)) {
                    return Double.parseDouble(links.get(i).getInfo().split(",")[0]);
                }
            }
        }
        return -.1;
    }

    public void setCost(Edge destino, String info) {
        if (isLinkedWith(destino)) {
            for (int i = 0; i < links.size(); i++) {
                if (links.get(i).getDestino().equals(destino)) {
                    String temp = links.get(i).getInfo();
                    temp = temp.replace(temp.split(",")[1], info);
                    links.get(i).setInfo(temp);
                }
            }
        }
    }

    public double getCost(Edge destino) {
        if (isLinkedWith(destino)) {
            for (int i = 0; i < links.size(); i++) {
                if (links.get(i).getDestino().equals(destino)) {
                    return Double.parseDouble(links.get(i).getInfo().split(",")[1]);
                }
            }
        }
        return -.1;
    }

    public void addLink(Edge otro, String info) {
        if (links.isEmpty()) {
            links.add(new Link(otro, info));
        } else {
            if (!isLinkedWith(otro)) {
                links.add(new Link(otro, info));
            }
        }
    }

    public boolean isLinkedWith(Edge otro) {
        for (Link i : links) {
            if (i.getDestino().equals(otro)) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean bool) {
        this.visited = bool;
    }

    public void removeLinkFrom(Edge destino) {
        for (Link i : this.links) {
            if (i.getDestino().equals(destino)) {
                links.remove(i);
                break;
            }
        }
    }
}