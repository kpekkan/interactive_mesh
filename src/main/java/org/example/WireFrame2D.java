package org.example;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.function.Consumer;

public class WireFrame2D implements WireFrame<Point2D> {
    private ArrayList<Point2D> vertexTable;
    private ArrayList<int[]> edgeTable;
    
    public WireFrame2D() {
        vertexTable = new ArrayList<>();
        edgeTable = new ArrayList<>();
    }
    
    public static WireFrame2D newWireFrame2DFromFile(File vertexFile, File edgeFile) throws FileNotFoundException {
        WireFrame2D wireFrame2D = new WireFrame2D();
        BufferedReader vertexReader = new BufferedReader(new FileReader(vertexFile));
        BufferedReader edgeReader = new BufferedReader(new FileReader(edgeFile));
        
        vertexReader.lines().forEachOrdered(string -> {
            String regex = "[\\s]";
            String[] myArray = string.split(regex);
            double x = Double.parseDouble(myArray[0]);
            double y = Double.parseDouble(myArray[1]);
            wireFrame2D.addVertex(new Point2D.Double(x, y));
        });
        
        edgeReader.lines().forEachOrdered(string -> {
            String regex = "[\\s]";
            String[] myArray = string.split(regex);
            int x = Integer.parseInt(myArray[0]);
            int y = Integer.parseInt(myArray[1]);
            wireFrame2D.addEdge(x, y);
        });
        
        return wireFrame2D;
    }
    
    /**
     * gives all the Vertexes of this {@link WireFrame}s vertex table as an array of vertexes.
     *
     * @return all the vertexes in this {@link WireFrame}s vertex table
     */
    @Override
    public Point2D[] getVertexes() {
        return (Point2D[]) vertexTable.toArray();
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
    public int[][] getEdges() {
        return (int[][]) edgeTable.toArray();
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
     * @return the vertex that was removed from the {@link WireFrame}s vertex table
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Point2D removeVertex(int vertexIndex) {
        return vertexTable.remove(vertexIndex);
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
}
