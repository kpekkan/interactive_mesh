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

public class WireFramePanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener {
    public final int VERTEX_RADIUS = 10;
    public final int INITIAL_WIDTH = 500;
    public final int INITIAL_HEIGHT = 500;
    private final WireFrame2D wireFrame;
    private final ArrayList<Point2D> vertexesInCameraCoordinates;
    private final CameraLocation camera;
    private WireFramePanelModes mode;
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
        mode = WireFramePanelModes.VIEW_MODE;
        camera = new CameraLocation();
        objectBeingClicked = -1;
        
        addMouseMotionListener(this);
        addMouseListener(this);
        
        setFocusable(true);
        addKeyListener(this);
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
        
        int halfVertexRadius = VERTEX_RADIUS >> 1;
        
        vertexesInCameraCoordinates.clear();
        ArrayList<Point2D> vertexes = (ArrayList<Point2D>) wireFrame.getVertexes();
        vertexesInCameraCoordinates.ensureCapacity(vertexes.size());
        
        // Draw Vertexes
        for (int i = 0; i < vertexes.size(); i++) {
            Point2D cameraCord = camera.world2CameraCord(vertexes.get(i), this);
            vertexesInCameraCoordinates.add(cameraCord);
            g2D.draw(new Ellipse2D.Double(cameraCord.getX() - halfVertexRadius, cameraCord.getY() - halfVertexRadius, VERTEX_RADIUS, VERTEX_RADIUS));
        }
        
        ArrayList<int[]> edges = (ArrayList<int[]>) wireFrame.getEdges();
        // Draw Edges
        for (int i = 0; i < edges.size(); i++) {
            int[] edge = edges.get(i);
            Point2D point1 = vertexesInCameraCoordinates.get(edge[0]);
            Point2D point2 = vertexesInCameraCoordinates.get(edge[1]);
            g2D.drawLine((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY());
        }
        
        //write mode in the top left corner
        switch (mode) {
            case VIEW_MODE -> {
                g2D.drawString(mode + ": " + camera.toString(), 5, 10);
            }
            case EDIT_VERTEX_MODE -> {
                g2D.drawString(mode + ": " + vertexes.get(objectBeingClicked).toString(), 5, 10);
            }
            case EDIT_EDGE_MODE -> {
                Point2D point = vertexesInCameraCoordinates.get(objectBeingClicked);
                Point2D mouse = getMousePosition();
                g2D.drawString(mode + ": " + point.toString(), 5, 10);
                g2D.drawLine((int) point.getX(), (int) point.getY(), (int) mouse.getX(), (int) mouse.getY());
            }
        }
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
        if ((objectBeingClicked = findVertexInCameraMode(currentPoint)) != -1) {
            mode = WireFramePanelModes.EDIT_VERTEX_MODE;
            repaint();
            return;
        }
        
        mode = WireFramePanelModes.VIEW_MODE;
        repaint();
    }
    
    
    /**
     * finds the index of the vertex within 10 pixels of the point
     *
     * @param point the point you want to find a vertex at
     * @return the vertexes index inside the vertex table
     */
    private int findVertexInCameraMode(Point2D point) {
        for (int i = 0; i < vertexesInCameraCoordinates.size(); i++)
            if (point.distance(vertexesInCameraCoordinates.get(i)) <= 10) {
                return i;
            }
        return -1;
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
                camera.topLeft.setLocation(camera.topLeft.getX() + lastPoint.getX() - currentPoint.getX(), camera.topLeft.getY() + lastPoint.getY() - currentPoint.getY());
                repaint();
            }
            case EDIT_VERTEX_MODE -> {
                wireFrame.getVertexe(objectBeingClicked).setLocation(camera.camera2WorldCord(currentPoint, this));
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
        if (mode.isEdgeMode())
            repaint();
    }
    
    
    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    
    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }
    
    
    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'v' -> {
                mode = WireFramePanelModes.VIEW_MODE;
                if ((objectBeingClicked = findVertexInCameraMode(e.getComponent().getMousePosition())) == -1)
                    wireFrame.addVertex(camera.camera2WorldCord(e.getComponent().getMousePosition(), this));
                else {
                    wireFrame.removeVertex(objectBeingClicked);
                }
                repaint();
            }
            case 'e' -> {
                if (mode.isEdgeMode()) {
                    int currentPoint = findVertexInCameraMode(e.getComponent().getMousePosition());
                    if (currentPoint != -1 && currentPoint != objectBeingClicked)
                        wireFrame.addEdge(objectBeingClicked, currentPoint);
                    
                    mode = WireFramePanelModes.VIEW_MODE;
                    objectBeingClicked = -1;
                } else {
                    objectBeingClicked = findVertexInCameraMode(e.getComponent().getMousePosition());
                    if (objectBeingClicked != -1)
                        mode = WireFramePanelModes.EDIT_EDGE_MODE;
                }
                repaint();
            }
        }
    }
    
    
    public WireFrame2D getWireFrame() {
        return wireFrame;
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
        
        return new Point2D.Double(cameraCord.getX() / magnifyingConstantX + topLeft.getX(), cameraCord.getY() / magnifyingConstantY + topLeft.getY());
    }
    
    public void increaseCameraWithBy(int deltaX) {
        
    }
    
    public void increaseCameraHeightBy(int deltaY) {
        
    }
    
    @Override
    public String toString() {
        return "CameraLocation{" + "x=" + topLeft.getX() + ", y=" + topLeft.getY() + ", width=" + width + ", height=" + height + '}';
    }
}

