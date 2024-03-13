package main.java.view.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Gui {

    /*******************************************************************************************************************
     * Instance Variables
     */
    private JFrame jFrame;
    private JPanel jPanel;
    private DrawShapes drawShapes;

    /*******************************************************************************************************************
     * Constructor
     */
    public Gui () {
        jFrame = new JFrame();
        jPanel = new JPanel();
        drawShapes = new DrawShapes();

        Border border = BorderFactory.createEmptyBorder(30, 30, 10, 30);
        jPanel.setBorder(border);
        jPanel.setLayout(new GridLayout(0, 1));

        jFrame.add(jPanel, BorderLayout.CENTER);

        // Ends the program when the gui window closes
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);

        windowSettings();
        jFrame.pack();
        jFrame.setVisible(true);

    }

    private void windowSettings() {
        jFrame.setTitle("Flashcard App");
        //TODO: Set icon image
        drawBackground();
        jFrame.setPreferredSize(new Dimension(800, 450));

    }

    private void drawBackground () {
        Color color = new java.awt.Color(255, 236, 222);
        jPanel.setBackground(color);
        jFrame.getContentPane().add(jPanel);
        drawRect();
    }

    private void drawRect () {
        jFrame.getContentPane().add(drawShapes);
    }



}
