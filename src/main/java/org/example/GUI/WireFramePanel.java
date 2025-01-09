package org.example.GUI;

import org.example.WireFrame2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;

public class WireFramePanel extends JPanel {
    public final int INITIAL_WIDTH = 500;
    public final int INITIAL_HEIGHT = 500;
    private final WireFrame2D wireFrame;
    private WireFramePanelModes mode;
    private final CameraLocation camera;
    
    
    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public WireFramePanel() {
        setPreferredSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
        wireFrame = new WireFrame2D();
        wireFrame.addVertex(new Point2D.Double(250, 250));
        mode = WireFramePanelModes.VIEW_MODE;
        camera = new CameraLocation();
        
        addMouseMotionListener(new MouseMotionListener() {
            private Point2D lastPoint;
            @Override
            public void mouseDragged(MouseEvent e) {
                Point2D currentPoint = e.getPoint();
                
                if (mode.isViewMode()) {
                    camera.topLeft.setLocation(
                            camera.topLeft.getX() + lastPoint.getX() - currentPoint.getX(),
                            camera.topLeft.getY() + lastPoint.getY() - currentPoint.getY()
                    );
                    repaint();
                }
                
                lastPoint = e.getPoint();
            }
            
            
            @Override
            public void mouseMoved(MouseEvent e) {
                lastPoint = e.getPoint();
            }
        });
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
        Graphics2D g2D = (Graphics2D) g;
        for (Point2D point: wireFrame.getVertexes()) {
            point = camera.convert2CameraCord(point, this);
            g2D.fillOval((int) point.getX(), (int) point.getY(), 10, 10);
        }
    }
}

class CameraLocation extends Dimension {
    Point2D topLeft;
    
    
    /**
     * Creates an instance of {@code Dimension} with a width
     * of zero and a height of zero.
     */
    public CameraLocation() {
        super(300, 300);
        topLeft = new Point(0, 0);
    }
    
    
    public Point2D convert2CameraCord(Point2D worldCord, JPanel panel) {
        double magnifyingConstantX = panel.getWidth() / this.getWidth();
        double magnifyingConstantY = panel.getHeight() / this.getHeight();
        
        return new Point2D.Double((worldCord.getX() - topLeft.getX()) * magnifyingConstantX, (worldCord.getY() - topLeft.getY()) * magnifyingConstantY);
    }
}

