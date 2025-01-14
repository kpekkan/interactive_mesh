package org.example;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class WireFrame2D implements WireFrame<Point2D> {
    private final ArrayList<Point2D> vertexTable;
    private final ArrayList<int[]> edgeTable;
    
    
    public WireFrame2D() {
        vertexTable = new ArrayList<>();
        edgeTable = new ArrayList<>();
    }
    
    
    public static void newWireFrame2DFromFile(WireFrame2D wireFrame2D, File vertexFile, File edgeFile) throws FileNotFoundException {
        new BufferedReader(new FileReader(vertexFile)).lines().forEachOrdered(string -> {
            String regex = "\\s";
            String[] myArray = string.split(regex);
            double x = Double.parseDouble(myArray[0]);
            double y = Double.parseDouble(myArray[1]);
            wireFrame2D.addVertex(new Point2D.Double(x, y));
        });
        
        new BufferedReader(new FileReader(edgeFile)).lines().forEachOrdered(string -> {
            String regex = "\\s";
            String[] myArray = string.split(regex);
            int x = Integer.parseInt(myArray[0]);
            int y = Integer.parseInt(myArray[1]);
            wireFrame2D.addEdge(x, y);
        });
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
    public Collection<int[]> getEdges() {
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
    public int[] getEdge(int edgeIndex) {
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
        edgeTable.add(new int[]{vertex1Index, vertex2Index});
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
            int[] edge = edgeTable.get(i);
            if (edge[0] == vertexIndex || edge[1] == vertexIndex) {
                removeEdge(i--);
            } else {
                if (edge[0] > vertexIndex) {
                    edge[0]--;
                }
                if (edge[1] > vertexIndex) {
                    edge[1]--;
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
    public int[] removeEdge(int edgeIndex) {
        return edgeTable.remove(edgeIndex);
    }
    
    
    /**
     * exports file
     *
     * @return true on a successful export
     */
    public boolean export() throws IOException {
        return true;
    }
}
