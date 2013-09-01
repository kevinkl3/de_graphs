/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Analyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 *
 * @author wilmercarranza
 */
public class Graph {

    int globalCounter;
    public double matrixL[][];
    public double matrixC[][];
    private double paths[][];
    private ArrayList<Edge> edges; //donde van a estar todos los nodos, con sus respectivos enlaces.
    private ArrayList<Edge> edgesOnly;
    ArrayList<Edge> articulationPoints;
    private double totalCost = 0;
    private double kruskalCost = 0;
    private int length = 0;

    {
        edges = new ArrayList<Edge>();
        edgesOnly = new ArrayList<Edge>();
        articulationPoints = new ArrayList<Edge>();
    }

//Las cuidades deber venir unicas, aunque se repitan como cuidad de origen/destino
    public Graph(ArrayList<Edge> cities) { //este constructor me crea mis matrices de adyacencias de costo/latencia.
        length = cities.size();
        edges = cities;
        globalCounter = 1;
        for (Edge i : cities) {
            edgesOnly.add(new Edge(i.getName(), i.getNumber()));
        }
        paths = new double[length][length];
        matrixL = new double[length][length];
        matrixC = new double[length][length];
        for (Edge e : cities) {
            if (!cities.isEmpty()) {
                for (Edge edge : e.getVerticesInArray()) {
                    matrixL[e.getNumber()][edge.getNumber()] = e.getLatency(edge);
                }

            }
        }
        for (Edge e : cities) {
            if (!cities.isEmpty()) {
                for (Edge edge : e.getVerticesInArray()) {
                    matrixC[e.getNumber()][edge.getNumber()] = e.getCost(edge);
                }

            }
        }
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public void floyd() {
        double[][] copyLatencies = new double[length][length];
        for (int i = 0; i < length; i++) {//Si en algun espacio hay 0 es porque no hay ruta asi que se le asigna infinito
            for (int j = 0; j < length; j++) {
                if (matrixL[i][j] == 0) {
                    matrixL[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
        for (int i = 0; i < length; i++) {//Makes a copy a the latencies matrix to modify it through floyd
            for (int j = 0; j < length; j++) {
                copyLatencies[i][j] = this.matrixL[i][j];
                paths[i][j] = 0;
            }
        }
        for (int i = 0; i < length; i++) {//Diagonales de la matriz en copia en 0 ??
            copyLatencies[i][i] = 0;
        }
        //Floyd Algorithm
        for (int k = 0; k < length; k++) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    if (copyLatencies[i][k] + copyLatencies[k][j] < copyLatencies[i][j]) {
                        copyLatencies[i][j] = copyLatencies[i][k] + copyLatencies[k][j];
                        this.paths[i][j] = k;
                    }
                }
            }
        }
    }

    public String getAllPaths() {
        String report = "";
        for (int i = 0; i < length; i++) {//Printing paths from 1 node all of the others
            for (int j = 0; j < length; j++) {
                report += ("\n<<From " + getEdge(i).getName() + " to " + getEdge(j).getName() + " path>>: \n");
                report += this.path(i, j);
            }
        }
        return report;
    }

    public String path(int orig, int dest) {//Returns the optimized path from node origin to node destiny
        String path = "";
        double k = paths[orig][dest];
        if (k == 0) {
            return "";
        }
        path += path(orig, (int) k);
        path += (getEdge((int) k).getName() + "\n");
        path += path((int) k, dest);
        return path;
    }

    public String getOPath(String from, String to) {
        int orig = -1;
        int dest = -1;
        for (Edge e : edges) {
            if (e.getName().equalsIgnoreCase(from)) {
                orig = e.getNumber();
            }
            if (e.getName().equalsIgnoreCase(to)) {
                dest = e.getNumber();
            }
        }
        this.floyd();
        return this.path(orig, dest);
    }

    public Edge getEdge(int id) throws NoSuchElementException {
        for (Edge i : edges) {
            if (i.getNumber() == id) {
                return i;
            }
        }
        throw new NoSuchElementException("No such vertex.");
    }

    public Edge getEdge(String id) throws NoSuchElementException {
        for (Edge i : edges) {
            if (i.getName().equalsIgnoreCase(id)) {
                return i;
            }
        }
        throw new NoSuchElementException("No such vertex.");
    }

    public Edge getEdgeOnly(int id) {
        for (Edge i : edgesOnly) {
            if (i.getNumber() == id) {
                return i;
            }
        }
        return null;
    }

    public ArrayList<Edge> kruskal() {
        totalCost = 0;
        kruskalCost = 0;
        double minCost = Double.POSITIVE_INFINITY;
        Edge origen = null;
        Edge destino = null;
        ArrayList<Edge> arbol = new ArrayList<Edge>();
        ArrayList<Double> aristas = new ArrayList<Double>();
        double[][] copy = new double[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if ((matrixC[i][j] != 0)) {
                    aristas.add(matrixC[i][j]);
                    totalCost += matrixC[i][j];
                    copy[i][j] = matrixC[i][j];
                }
            }
        }
        //Kruskal algorithm
        while (!aristas.isEmpty()) {
            minCost = Collections.min(aristas);
            aristas.remove(minCost);
            outerloop:
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    if (copy[i][j] == minCost) {
                        copy[j][i] = -1;
                        copy[i][j] = -1; //asi ya no vuelve a pasar por estos valores.
                        origen = getEdgeOnly(i);
                        destino = getEdgeOnly(j);
                        if (!arbol.contains(origen) && !arbol.contains(destino)) {
                            origen.addLink(destino, String.valueOf(matrixL[i][j]) + "," + String.valueOf(minCost));
                            destino.addLink(origen, String.valueOf(matrixL[i][j]) + "," + String.valueOf(minCost));
                            arbol.add(origen);
                            arbol.add(destino);
                            restoreVisits(arbol);
                            kruskalCost += minCost;
                            if (hayCiclo(origen, arbol, null)) {
                                origen.removeLinkFrom(destino);
                                destino.removeLinkFrom(origen);
                                arbol.remove(origen);
                                arbol.remove(destino);
                                kruskalCost -= minCost;
                            }
                            break outerloop;
                        } else {
                            if (arbol.contains(origen) && !arbol.contains(destino)) {
                                arbol.get(arbol.lastIndexOf(origen)).addLink(destino, String.valueOf(matrixL[i][j]) + "," + String.valueOf(minCost));
                                destino.addLink(arbol.get(arbol.lastIndexOf(origen)), String.valueOf(matrixL[i][j]) + "," + String.valueOf(minCost));
                                arbol.add(destino);
                                restoreVisits(arbol);
                                kruskalCost += minCost;
                                if (hayCiclo(destino, arbol, null)) {
                                    arbol.get(arbol.lastIndexOf(origen)).removeLinkFrom(destino);
                                    destino.removeLinkFrom(arbol.get(arbol.lastIndexOf(origen)));
                                    arbol.remove(destino);
                                    kruskalCost -= minCost;
                                }
                                break outerloop;
                            } else {
                                if (!arbol.contains(origen) && arbol.contains(destino)) {
                                    arbol.get(arbol.lastIndexOf(destino)).addLink(origen, String.valueOf(matrixL[i][j]) + "," + String.valueOf(minCost));
                                    origen.addLink(arbol.get(arbol.lastIndexOf(destino)), String.valueOf(matrixL[i][j]) + "," + String.valueOf(minCost));
                                    arbol.add(origen);
                                    restoreVisits(arbol);
                                    kruskalCost += minCost;
                                    if (hayCiclo(origen, arbol, null)) {
                                        arbol.get(arbol.lastIndexOf(destino)).removeLinkFrom(origen);
                                        origen.removeLinkFrom(arbol.get(arbol.lastIndexOf(destino)));
                                        arbol.remove(origen);
                                        kruskalCost -= minCost;
                                    }
                                    break outerloop;
                                } else {
                                    arbol.get(arbol.lastIndexOf(origen)).addLink(arbol.get(arbol.lastIndexOf(destino)), String.valueOf(matrixL[i][j]) + "," + String.valueOf(minCost));
                                    arbol.get(arbol.lastIndexOf(destino)).addLink(arbol.get(arbol.lastIndexOf(origen)), String.valueOf(matrixL[i][j]) + "," + String.valueOf(minCost));
                                    restoreVisits(arbol);
                                    kruskalCost += minCost;
                                    if (hayCiclo(origen, arbol, null)) {
                                        arbol.get(arbol.lastIndexOf(origen)).removeLinkFrom(arbol.get(arbol.lastIndexOf(destino)));
                                        arbol.get(arbol.lastIndexOf(destino)).removeLinkFrom(arbol.get(arbol.lastIndexOf(origen)));
                                        kruskalCost -= minCost;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        edgesOnly.clear();
        for (Edge i : edges) {
            edgesOnly.add(new Edge(i.getName(), i.getNumber()));
        } //hago esto para que se 'reset' los enlaces de cada nodo en esta lista. si no lo hago y en algun momento llamo kruskal de nuevo, no va a funcionar... ver mejoras despues.....
        return arbol;
    }

    public void restoreVisits(ArrayList<Edge> array) {
        for (Edge e : array) {
            e.setVisited(false);
        }
    }

    public boolean hayCiclo(Edge start, ArrayList<Edge> nodos, Edge last) {
        nodos.get(nodos.lastIndexOf(start)).setVisited(true);
        for (Edge i : start.getVerticesInArray()) {
            if (!i.equals(last)) { // si el nodo tiene una conexion con el nodo anterior, se lo salta.
                if (i.isVisited()) {
                    // si no es igual al nodo anterior, pregunta si ese nodo ya habia sido visitado
                    return true; // si ya fue visitado, return true, hay ciclo
                } else { // si no habia sido visitado, entonces evaluamos con este vecino a ver si tiene enlaces con nodos ya visitados.
                    if (!hayCiclo(i, nodos, start)) {
                        continue;
                    } else {
                        return true;
                    }
                }
            }
        }
        return false; //si al verificar todos sus vecinos ninguna forma ciclos, es obvio que no los hay, return false, no hay ciclos.
    }

    public String minimalSpanningTreeToString() {
        ArrayList<Edge> result = new ArrayList<Edge>();
        for (Edge i : kruskal()) {
            result.add(i);
        }
        restoreVisits(result);
        return runGraph(result.get(0), result, null);
        /*
        String toreturn = "";
        for (Edge i : kruskal()) {
        toreturn += String.format("\nNodo:%s\nVecinos:%s\n", i, i.showConnections());
        }
        return toreturn;*/
    }

    public String runGraph(Edge start, ArrayList<Edge> nodos, Edge last) {
        String acum = "";
        acum += "\n" + start.getName() + ", adjacent vertices: " + start.showConnections();
        nodos.get(nodos.lastIndexOf(start)).setVisited(true);
        for (Edge i : start.getVerticesInArray()) {
            if (!i.isVisited()) {
                acum += runGraph(i, nodos, start);
            }
        }
        return acum;
    }

    public void articulationPoints() {
        for (Edge i : edges) { //por cada nodo de este grafo, que verifique si su numero el profundidad, es menor o igual al bajo de uno de sus hijos
            //de ser asi es un punto de articulacion...
            innerloop:
            for (Edge e : i.hijos) { //busco en cada uno de los hijos del nodo a ver si se cumple la condicion anterior.
                if (bajo(e) >= i.searchNumber && i.searchNumber != 1) {
                    this.articulationPoints.add(i);
                    break innerloop;
                }
            }
        }
    }

    public void fillTree(Edge start, ArrayList<Edge> nodos, Edge anterior) {
        //metodo que me hace el recorrido en profundidad, y me crea "un arbol".
        nodos.get(nodos.lastIndexOf(start)).setVisited(true);
        nodos.get(nodos.lastIndexOf(start)).searchNumber = globalCounter++;
        for (Edge e : start.getVerticesInArray()) {
            if (!e.equals(anterior)) {
                if (!e.isVisited()) { // si este hijo no ha sido visitado, lo tomo.
                    start.hijos.add(e);
                    fillTree(e, nodos, start); //hago la llamada recursiva de mi hijo, poniendome de anterior a mi.
                } else { // si ya fue visitado en alguna instancia pasada, entonces hay una arista de retroceso.
                    if (!e.retroceso.contains(start)) { //aqui pregunto si el hijo visitado anteriormente, ya me habia agregado como de retroceso, si no es asi lo agrego yo como de retroceso, pero los dos no.
                        start.retroceso.add(e);
                    }

                }
            }
        }
    }

    public int bajo(Edge i) { // calcula el bajo de un vertice.
        if (i.searchNumber == 1) {
            i.low = 1;
            return 1;
        }
        if (!i.hijos.isEmpty()) {
            int[] arr = new int[i.hijos.size() + 1];
            for (int j = 0; j < i.hijos.size(); j++) {
                arr[j] = bajo(i.hijos.get(j));
            }
            arr[arr.length - 1] = i.searchNumber;
            i.low = getMin(arr);
        } else {
            //si no tiene hijos es porque el nodo es una hoja, y su bajo se calculo con el minimo de su busqueda en profundidad y el de sus aristas de retro.
            int[] arr = new int[i.retroceso.size() + 1];
            for (int j = 0; j < i.retroceso.size(); j++) {
                arr[j] = i.retroceso.get(j).searchNumber;
            }
            arr[arr.length - 1] = i.searchNumber;
            i.low = getMin(arr);
        }
        return i.low;
    }

    public int getMin(int[] values) {
        int least = values[0];
        for (Integer x : values) {
            if ((int) x < least) {
                least = x;
            }
        }
        return least;
    }

    public double getTotalCost() {
        return this.totalCost;
    }

    public double getMinimalCost() {
        return this.kruskalCost;
    }
}
