package USMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author wilmercarranza
 */
public class Canvas extends java.awt.Canvas {

    ArrayList<Arista> aristas = new ArrayList<Arista>();
    ArrayList<Edge> vertices = new ArrayList<Edge>();
    private String etiqueta;
    private BufferedImage image;
    int x;
    int y;
    private String camino;

    public Canvas(String camino) {
        try {
            image = ImageIO.read(new File("USA_small.jpg"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        this.camino = camino;
        x = 0;
        y = 0;
        readCoordinates();
        setAristas();
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void paintPaths(String path, boolean mst) {
        setAristas();
        String[] subArray = path.split("\n");
        for (int j = 0; j < subArray.length - 1; j++) {
            for (int i = 0; i < aristas.size(); i++) {
                Arista a = aristas.get(i);
                if (!mst) {
                    if (a.getStart().equals(subArray[j]) && a.getEnd().equals(subArray[j + 1])) {
                        aristas.add(new Arista(vertices.get(getIndex(a.getStart())).getX() + 3, vertices.get(getIndex(a.getStart())).getY() + 3,
                                vertices.get(getIndex(a.getEnd())).getX() + 3, vertices.get(getIndex(a.getEnd())).getY() + 3,
                                3, a.getStart(), a.getEnd(), a.getCost(), a.getLatency(), true));
                        aristas.remove(i);
                        break;
                    }
                } else {
                    if (a.getStart().equals(subArray[j]) && a.getEnd().equals(subArray[j + 1])) {
                        aristas.add(new Arista(vertices.get(getIndex(a.getStart())).getX() + 3, vertices.get(getIndex(a.getStart())).getY() + 3,
                                vertices.get(getIndex(a.getEnd())).getX() + 3, vertices.get(getIndex(a.getEnd())).getY() + 3,
                                3, a.getStart(), a.getEnd(), a.getCost(), a.getLatency(), true));
                        aristas.remove(i);
                        j++;
                        break;
                    }
                }

            }
        }

    }

    public void readCoordinates() {
        FileInputStream archivo = null;
        String[] array;
        try {
            archivo = new FileInputStream("coordenadas.txt");
            Scanner e = new Scanner(archivo);
            while (e.hasNextLine()) {
                array = e.nextLine().split(",");
                vertices.add(new Edge(Integer.parseInt(array[1]), Integer.parseInt(array[2]), 15, 15, 1, array[0], false, false));
            }

        } catch (FileNotFoundException ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void setAristas() {
        aristas.clear();
        String[] array = camino.split("\n");
        for (int i = 0; i < array.length; i++) {
            String[] subArray = array[i].split(",");
            for (int j = 0; j < subArray.length; j++) {
                aristas.add(new Arista(vertices.get(getIndex(subArray[0])).getX() + 3, vertices.get(getIndex(subArray[0])).getY() + 3,
                        vertices.get(getIndex(subArray[1])).getX() + 3, vertices.get(getIndex(subArray[1])).getY() + 3,
                        3, subArray[0], subArray[1], Double.parseDouble(subArray[2]), Double.parseDouble(subArray[3]), false));
                vertices.get(getIndex(subArray[0])).setUsing(true);//pintar de verde los q se usan
                vertices.get(getIndex(subArray[0])).esArticulacion = false;
                vertices.get(getIndex(subArray[1])).setUsing(true);
                vertices.get(getIndex(subArray[1])).esArticulacion = false;
            }

        }
    }

    //necesito encontrar el indice del estado en la lista de vertices
    public int getIndex(String s) {
        for (int i = 0; i < vertices.size(); i++) {
            if (s.equalsIgnoreCase(vertices.get(i).getEtiqueta())) {
                return i;
            }
        }
        return -1;
    }

    public void paintEdges(String s) {
        String array[] = s.split("\n");
        setAristas();
        for (int i = 0; i < array.length; i++) {
            for (Edge v : vertices) {
                if (array[i].equals(v.getEtiqueta())) {
                    v.esArticulacion = true;
                    v.paint(this.getGraphics());
                }
            }

        }
    }

    public void repaint(Graphics g) {
        this.paint(g);
    }
 //METODO PRINCIPAL, EL QUE ME PINTA TODO
    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
        if (aristas != null && vertices != null) {
            for (Arista a : aristas) {
                a.paint(g);
            }
            for (Edge v : vertices) {
                v.paint(g);
            }
        }
    }
}
