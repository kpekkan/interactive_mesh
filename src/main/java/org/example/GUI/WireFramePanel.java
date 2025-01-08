package org.example.GUI;

import org.example.WireFrame2D;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class WireFramePanel extends JPanel {
    private final WireFrame2D wireFrame;
    private WireFramePanelModes mode;
    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public WireFramePanel() {
        setPreferredSize(new Dimension(500, 500));
        wireFrame = new WireFrame2D();
        mode = WireFramePanelModes.VIEW_MODE;
    }
    
    public WireFramePanel(File vertexFile, File edgeFile) throws FileNotFoundException {
        this();
        
        WireFrame2D.newWireFrame2DFromFile(wireFrame, vertexFile, edgeFile);
    }
    
    public void setMode(WireFramePanelModes mode) {
        this.mode = mode;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
    }
}

