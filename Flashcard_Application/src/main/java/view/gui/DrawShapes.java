package main.java.view.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class DrawShapes extends JComponent {

    private Color fillColor;
    private Color outlineColor;
    private List<Shape> shapesToDraw;


    /*******************************************************************************************************************
     * Constructor
     */
    public DrawShapes() {
        shapesToDraw = new ArrayList<>();
        fillColor = Color.BLUE;
        outlineColor = Color.BLACK;
    }

    /*******************************************************************************************************************
     * Draws all the shapes when DrawShape() is declared
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);
        g2.drawRect(100, 150, 60, 200);
        g2.fillRect(100, 150, 60, 200);
    }


    /*******************************************************************************************************************
     * Add Shapes to Draw
     */

    public void drawRect() {
        Rectangle rect = new Rectangle();
        rect.setFillColor(Color.WHITE);
        shapesToDraw.
    }
}
