package org.example;

import java.awt.*;

public class Wire {
    private WireType wireType;
    private int edge1;
    private int edge2;


    public enum WireType {
        NORMAL(Color.BLUE),
        MUSCLE(Color.RED);
        public final Color color;

        WireType(Color color) {
            this.color = color;
        }
    }

    public WireType getWireType() {
        return wireType;
    }

    public void setWireType(WireType wireType) {
        this.wireType = wireType;
    }

    public Wire(int edge1, int edge2) {
        this.edge1 = edge1;
        this.edge2 = edge2;
        wireType = WireType.NORMAL;
    }

    public Wire(WireType wireType, int edge1, int edge2) {
        this(edge1, edge2);
        this.wireType = wireType;
    }

    public int getEdge1() {
        return edge1;
    }

    public void setEdge1(int edge1) {
        this.edge1 = edge1;
    }

    public int getEdge2() {
        return edge2;
    }

    public void setEdge2(int edge2) {
        this.edge2 = edge2;
    }
}
