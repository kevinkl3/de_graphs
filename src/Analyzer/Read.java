/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyzer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author wilmercarranza
 */
public class Read {

    private String cadena = "";

    public String getCadena() {
        return cadena;
    }

    public ArrayList<Edge> createCities(String address) {
        HashMap<String, Edge> ciudades = new HashMap<String, Edge>();
        ArrayList<Edge> vertices = new ArrayList<Edge>();

        try {
            FileInputStream fstream = new FileInputStream(address);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String cursor;
            String origen = "", destino = "", costo = "", latencia = "";
            int contador = 0;
            Edge temporal = new Edge(), vecino = new Edge();
            while ((cursor = br.readLine()) != null) {
                cadena += cursor + "\n";
                origen = cursor.split(",")[0];
                destino = cursor.split(",")[1];
                costo = cursor.split(",")[2];
                latencia = cursor.split(",")[3];
                if (!ciudades.containsKey(origen)) {
                    temporal = new Edge(origen, contador++);
                    if (!ciudades.containsKey(destino)) {
                        vecino = new Edge(destino, contador++);
                        temporal.addLink(vecino, String.format("%s,%s", latencia, costo));
                        ciudades.put(destino, vecino);
                    } else {
                        temporal.addLink(ciudades.get(destino), String.format("%s,%s", latencia, costo));
                    }
                    ciudades.put(origen, temporal);
                } else {
                    if (!ciudades.containsKey(destino)) {
                        vecino = new Edge(destino, contador++);
                        ciudades.get(origen).addLink(vecino, String.format("%s,%s", latencia, costo));
                        ciudades.put(destino, vecino);
                    } else {
                        ciudades.get(origen).addLink(ciudades.get(destino), String.format("%s,%s", latencia, costo));
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        for (Edge i : ciudades.values()) {
            vertices.add(i); //esto se le mandaria al constructor de grafo
        }
        return vertices;
    }
}
