package org.example;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class WireFrame2D implements WireFrame<Point2D> {
    private final ArrayList<Point2D> vertexTable;
    private final ArrayList<Wire> edgeTable;
    
    
    public WireFrame2D() {
        vertexTable = new ArrayList<>();
        edgeTable = new ArrayList<>();
    }


    public static void newWireFrame2DFromFile(WireFrame2D wireFrame2D, File vertexFile, File edgeFile) throws IOException {

        // Read vertices from exported file
        try (BufferedReader vertexReader = new BufferedReader(new FileReader(vertexFile))) {
            String line;
            while ((line = vertexReader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("[") || line.startsWith("];")) continue;
                String[] points = line.split(";\\s*");
                for (String point : points) {
                    point = point.trim();
                    if (!point.isEmpty()) {
                        String[] coords = point.split("\\s+");
                        if (coords.length == 2) {
                            double x = Double.parseDouble(coords[0]);
                            double y = Double.parseDouble(coords[1]);
                            wireFrame2D.addVertex(new Point2D.Double(x, y));
                        }
                    }
                }
            }
        }

        // Read edges from exported file
        try (BufferedReader edgeReader = new BufferedReader(new FileReader(edgeFile))) {
            String line;
            while ((line = edgeReader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("[") || line.startsWith("];")) continue;
                String[] edges = line.split(";\\s*");
                for (String edge : edges) {
                    edge = edge.trim();
                    if (!edge.isEmpty()) {
                        String[] nodes = edge.split("\\s+");
                        if (nodes.length == 2) {
                            int vertexIndex1 = Integer.parseInt(nodes[0]);
                            int vertexIndex2 = Integer.parseInt(nodes[1]);
                            wireFrame2D.addEdge(vertexIndex1, vertexIndex2);
                        }
                    }
                }
            }
        }
    }



    /**
     * gives all the Vertexes of this {@link WireFrame}s vertex table as an array of vertexes.
     *
     * @return all the vertexes in this {@link WireFrame}s vertex table
     */
    @Override
    public ArrayList<Point2D> getVertexes() {
        return vertexTable;
    }
    
    
    /**
     * Returns the vertex at the specified vertex index in this {@link WireFrame}s vertex table.
     *
     * @param vertexIndex index of the vertex to return
     * @return the vertex at the specified vertex index in this {@link WireFrame}s vertex table
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Point2D getVertexe(int vertexIndex) {
        return vertexTable.get(vertexIndex);
    }
    
    
    /**
     * gives all the edges of this {@link WireFrame}s edge table as an array of edges.
     *
     * @return all the edges in this {@link WireFrame}s edge table
     */
    @Override
    public Collection<Wire> getEdges() {
        return edgeTable;
    }
    
    
    /**
     * Returns the edge at the specified edge index in this {@link WireFrame}s edge table.
     *
     * @param edgeIndex index of the edge to return
     * @return the edge at the specified edge index in this {@link WireFrame}s edge table
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Wire getEdge(int edgeIndex) {
        return edgeTable.get(edgeIndex);
    }
    
    
    /**
     * Appends the specified vertex to the end of this {@link WireFrame}s vertex table.
     *
     * @param vertex vertex to be appended to this {@link WireFrame}s vertex table
     */
    @Override
    public void addVertex(Point2D vertex) {
        vertexTable.add(vertex);
    }
    
    
    /**
     * Appends the specified edge to the end of this {@link WireFrame}s edge table.
     *
     * @param vertex1Index the first vertex which is part of the edge,
     *                     which will be appended to this {@link WireFrame}s edge table
     * @param vertex2Index the second vertex which is part of the edge,
     *                     which will be appended to this {@link WireFrame}s edge table
     */
    @Override
    public void addEdge(int vertex1Index, int vertex2Index) {
        edgeTable.add(new Wire(vertex1Index, vertex2Index));
    }
    @Override
    public void addEdge(Wire.WireType wireType, int vertex1Index, int vertex2Index) {
        edgeTable.add(new Wire(wireType, vertex1Index, vertex2Index));
    }
    
    /**
     * Removes the vertex at the specified position in this {@link WireFrame}s vertex table.
     * Shifts any subsequent vertexes to the left (subtracts one from their
     * indices).
     *
     * @param vertexIndex the vertex index of the vertex to be removed
     * @return true if the operation was successful
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public boolean removeVertex(int vertexIndex) {
        vertexTable.remove(vertexIndex);
        for (int i = 0; i < edgeTable.size(); i++) {
            Wire edge = edgeTable.get(i);
            if (edge.getEdge1() == vertexIndex || edge.getEdge2() == vertexIndex) {
                removeEdge(i--);
            } else {
                if (edge.getEdge1() > vertexIndex) {
                    edge.setEdge1(edge.getEdge1() - 1);
                }
                if (edge.getEdge2() > vertexIndex) {
                    edge.setEdge2(edge.getEdge2() - 1);
                }
            }
        }
        return true;
    }
    
    
    /**
     * Removes the edge at the specified position in this {@link WireFrame}s edge table.
     * Shifts any subsequent edge to the left (subtracts one from their
     * indices).
     *
     * @param edgeIndex the edge index of the edge to be removed
     * @return the edge that was removed from the {@link WireFrame}s edge table
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Wire removeEdge(int edgeIndex) {
        return edgeTable.remove(edgeIndex);
    }
    
    
    /**
     * exports file
     *
     * @return true on a successful export
     */
    public boolean export() throws IOException {
        BufferedWriter text1 = new BufferedWriter(new FileWriter("noktalar.txt"));
        text1.write("[\n");
        for (int i = 0; i < vertexTable.size(); i++) {
            double Xcoordinate = vertexTable.get(i).getX();
            double Ycoordinate = vertexTable.get(i).getY();
            text1.write(Double.toString(Xcoordinate));
            text1.write(" ");
            text1.write(Double.toString(Ycoordinate));
            text1.write("; ");
            if (i % 8 == 7)
                text1.write("\n");
        }
        text1.write("\n];");
        text1.close();

        BufferedWriter text2 = new BufferedWriter(new FileWriter("kenar.txt"));
        text2.write("[\n");
        for (int i = 0; i < edgeTable.size(); i++) {
            int Node1 = edgeTable.get(i).getEdge1();
            int Node2 = edgeTable.get(i).getEdge2();
            text2.write(Integer.toString(Node1));
            text2.write(" ");
            text2.write(Integer.toString(Node2));
            text2.write("; ");
            if (i % 8 == 7)
                text2.write("\n");
        }
        text2.write("\n];");
        text2.close();
        return true;
    }
}
