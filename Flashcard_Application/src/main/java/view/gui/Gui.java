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
    private GuiDraw guiDraw;

    /*******************************************************************************************************************
     * Constructor
     */
    public Gui () {
        jFrame = new JFrame();
        jPanel = new JPanel();
        guiDraw = new GuiDraw();

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
        jFrame.setPreferredSize(new Dimension(800, 450));

    }

    public void renderAll () {
        //drawRect();
        jFrame.getContentPane().add(guiDraw);
    }


}
