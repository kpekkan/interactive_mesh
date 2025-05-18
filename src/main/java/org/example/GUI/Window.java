package org.example.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Window extends JFrame {
    
    private final WireFramePanel wireFramePanel;
    
    
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
        
        addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window has been closed.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                // calls export function before closing
                try {
                    wireFramePanel.getWireFrame().export();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                super.windowClosed(e);
            }
        });
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

