package org.example.GUI;

import org.example.WireFrame2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WireFramePanel extends JPanel implements MouseMotionListener, MouseListener {
    public final int INITIAL_WIDTH = 500;
    public final int INITIAL_HEIGHT = 500;
    private final WireFrame2D wireFrame;
    private ArrayList<Point2D> vertexesInCameraCoordinates;
    private WireFramePanelModes mode;
    private final CameraLocation camera;
    private Point2D lastPoint;
    
    /**
     * the index of the vertex or edge currently being clicked
     */
    private int objectBeingClicked;
    
    
    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public WireFramePanel() {
        setPreferredSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
        wireFrame = new WireFrame2D();
        vertexesInCameraCoordinates = new ArrayList<>();
        wireFrame.addVertex(new Point2D.Double(250, 250));
        wireFrame.addVertex(new Point2D.Double(200, 250));
        mode = WireFramePanelModes.VIEW_MODE;
        camera = new CameraLocation();
        objectBeingClicked = -1;
        
        addMouseMotionListener(this);
        addMouseListener(this);
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
        
        vertexesInCameraCoordinates.clear();
        ArrayList<Point2D> vertexes = (ArrayList<Point2D>) wireFrame.getVertexes();
        vertexesInCameraCoordinates.ensureCapacity(vertexes.size());
        
        for (int i = 0; i < vertexes.size(); i++) {
            Point2D cameraCord = camera.world2CameraCord(vertexes.get(i), this);
            vertexesInCameraCoordinates.add(cameraCord);
            g2D.draw(new Ellipse2D.Double(cameraCord.getX(), cameraCord.getY(), 10, 10));
        }
        
        //write mode in the top left corner
        g2D.drawString(mode.toString() + ": " + switch (mode) {
            case VIEW_MODE -> camera.toString();
            case EDIT_VERTEX_MODE -> vertexes.get(objectBeingClicked).toString();
            case EDIT_EDGE_MODE -> "";
        }, 5, 10);
    }
    
    
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    
    
    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Point2D currentPoint = e.getPoint();
        for (int i = 0; i < vertexesInCameraCoordinates.size(); i++)
            if (currentPoint.distance(vertexesInCameraCoordinates.get(i)) <= 10) {
                mode = WireFramePanelModes.EDIT_VERTEX_MODE;
                objectBeingClicked = i;
                repaint();
                return;
            }
        
            
        mode = WireFramePanelModes.VIEW_MODE;
    }
    
    
    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    
    /**
     * Invoked when the mouse enters a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    
    
    /**
     * Invoked when the mouse exits a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    
    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  {@code MOUSE_DRAGGED} events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * {@code MOUSE_DRAGGED} events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D currentPoint = e.getPoint();
        switch (mode) {
            case VIEW_MODE -> {
                camera.topLeft.setLocation(
                        camera.topLeft.getX() + lastPoint.getX() - currentPoint.getX(),
                        camera.topLeft.getY() + lastPoint.getY() - currentPoint.getY()
                );
                repaint();
            }
            case EDIT_VERTEX_MODE -> {
                wireFrame.getVertexe(objectBeingClicked).setLocation(
                        camera.camera2WorldCord(currentPoint, this)
                );
                repaint();
            }
        }
        
        
        lastPoint = e.getPoint();
    }
    
    
    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        lastPoint = e.getPoint();
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
    
    
    public Point2D getCenter() {
        return new Point2D.Double(topLeft.getX() + getWidth() / 2, topLeft.getY() + getHeight() / 2);
    }
    
    
    public Point2D world2CameraCord(Point2D worldCord, JPanel panel) {
        double magnifyingConstantX = panel.getWidth() / this.getWidth();
        double magnifyingConstantY = panel.getHeight() / this.getHeight();
        
        return new Point2D.Double((worldCord.getX() - topLeft.getX()) * magnifyingConstantX, (worldCord.getY() - topLeft.getY()) * magnifyingConstantY);
    }
    
    public Point2D camera2WorldCord(Point2D cameraCord, JPanel panel) {
        double magnifyingConstantX = panel.getWidth() / this.getWidth();
        double magnifyingConstantY = panel.getHeight() / this.getHeight();
        
        return new Point2D.Double(
                cameraCord.getX() / magnifyingConstantX + topLeft.getX(),
                cameraCord.getY() / magnifyingConstantY + topLeft.getY()
        );
    }
    
    
    @Override
    public String toString() {
        return "CameraLocation{" +
                "x=" + topLeft.getX() +
                ", y=" + topLeft.getY() +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}

