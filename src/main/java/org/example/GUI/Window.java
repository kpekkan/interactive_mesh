package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    
    private WireFramePanel wireFramePanel;
    
    /**
     * Constructs a new frame that is initially invisible.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws HeadlessException if GraphicsEnvironment.isHeadless()
     *                           returns true.
     * @see GraphicsEnvironment#isHeadless
     * @see Component#setSize
     * @see Component#setVisible
     * @see JComponent#getDefaultLocale
     */
    public Window() {
        super("filed");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        getContentPane().add(wireFramePanel = new WireFramePanel());
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    public static void main(String[] args) {
        new Window();
    }
}
