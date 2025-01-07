package org.example;

/**
 * represents a <a href="https://en.wikipedia.org/wiki/Wire-frame_model">wireframe model</a>.
 *
 * @param <E> the type of the vertex in this wireframe
 */
public interface WireFrame<E> {
    /**
     * gives all the Vertexes of this {@link WireFrame}s vertex table as an array of vertexes.
     *
     * @return all the vertexes in this {@link WireFrame}s vertex table
     */
    E[] getVertexes();
    /**
     * Returns the vertex at the specified vertex index in this {@link WireFrame}s vertex table.
     *
     * @param vertexIndex index of the vertex to return
     * @return the vertex at the specified vertex index in this {@link WireFrame}s vertex table
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    default E getVertexe(int vertexIndex) {
        return getVertexes()[vertexIndex];
    }
    /**
     * gives all the edges of this {@link WireFrame}s edge table as an array of edges.
     *
     * @return all the edges in this {@link WireFrame}s edge table
     */
    int[][] getEdges();
    /**
     * Returns the edge at the specified edge index in this {@link WireFrame}s edge table.
     *
     * @param edgeIndex index of the edge to return
     * @return the edge at the specified edge index in this {@link WireFrame}s edge table
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    default int[] getEdge(int edgeIndex) {
        return getEdges()[edgeIndex];
    }
    /**
     * Appends the specified vertex to the end of this {@link WireFrame}s vertex table.
     *
     * @param vertex vertex to be appended to this {@link WireFrame}s vertex table
     */
    void addVertex(E vertex);
    
    /**
     * Appends the specified edge to the end of this {@link WireFrame}s edge table.
     *
     * @param vertex1Index the first vertex which is part of the edge,
     *                     which will be appended to this {@link WireFrame}s edge table
     * @param vertex2Index the second vertex which is part of the edge,
     *                     which will be appended to this {@link WireFrame}s edge table
     */
    void addEdge(int vertex1Index, int vertex2Index);
    /**
     * Removes the vertex at the specified position in this {@link WireFrame}s vertex table.
     * Shifts any subsequent vertexes to the left (subtracts one from their
     * indices).
     *
     * @param vertexIndex the vertex index of the vertex to be removed
     * @return the vertex that was removed from the {@link WireFrame}s vertex table
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    E removeVertex(int vertexIndex);
    /**
     * Removes the edge at the specified position in this {@link WireFrame}s edge table.
     * Shifts any subsequent edge to the left (subtracts one from their
     * indices).
     *
     * @param edgeIndex the edge index of the edge to be removed
     * @return the edge that was removed from the {@link WireFrame}s edge table
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    int[] removeEdge(int edgeIndex);
}
