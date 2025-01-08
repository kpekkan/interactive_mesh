package org.example.GUI;

public enum WireFramePanelModes {
    VIEW_MODE((short) 0),
    EDIT_VERTEX_MODE((short) 1),
    EDIT_EDGE_MODE((short) 2);
    
    private final short number;
    
    WireFramePanelModes(short i) {
        this.number = i;
    }
    public boolean isViewMode() {
        return getMode() == 0;
    }
    
    public boolean isVertexMode() {
        return getMode() == 1;
    }
    
    public boolean isEdgeMode() {
        return getMode() == 2;
    }
    
    public short getMode() {
        return number;
    }
}
