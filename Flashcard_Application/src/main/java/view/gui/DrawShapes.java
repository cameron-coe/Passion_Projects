package main.java.view.gui;

import main.java.view.gui.shapes.GuiRectangle;
import main.java.view.gui.shapes.GuiShape;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class DrawShapes extends JComponent {

    private Color fillColor;
    private Color outlineColor;
    private List<GuiShape> shapesToDraw;


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
        g2.setStroke(new BasicStroke(2));
        /*g2.setColor(Color.blue);
        g2.fillRect(100, 150, 60, 200);
        g2.setColor(Color.black);
        g2.drawRect(100, 150, 60, 200);*/


        for (GuiShape shape : shapesToDraw) {
            g2.setColor(shape.getFillColor());
            if(shape instanceof GuiRectangle) {
                g2.fillRect(shape.getXPos(), shape.getYPos(), shape.getWidth(), shape.getHeight());
                //g2.fill(new Rectangle(shape.getXPos(), shape.getYPos(), shape.getWidth(), shape.getHeight()));
            }

            g2.setColor(shape.getOutlineColor());
            if(shape instanceof GuiRectangle) {
                g2.drawRect(shape.getXPos(), shape.getYPos(), shape.getWidth(), shape.getHeight());
                //g2.draw(new Rectangle(shape.getXPos(), shape.getYPos(), shape.getWidth(), shape.getHeight()));
            }
        }
    }


    /*******************************************************************************************************************
     * Setters
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    /*******************************************************************************************************************
     * Add Shapes to Draw
     */
    public void drawRect(int xPos, int yPos, int width, int height) {
        GuiShape shape = new GuiRectangle(xPos, yPos, width, height);
        shape.setFillColor(this.fillColor);
        shape.setOutlineColor(this.outlineColor);
        shapesToDraw.add(shape);
    }
}
