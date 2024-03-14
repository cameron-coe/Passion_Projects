package main.java.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class GuiDraw extends JComponent {

    /*******************************************************************************************************************
     * Constants
     */
    private final static Color BACKGROUND_COLOR = new java.awt.Color(255, 236, 222);
    private final static String RECTANGLE = "rectangle";
    private final static String ELLIPSE = "ellipse";


    /*******************************************************************************************************************
     * Instance Variables
     */
    private Color fillColor;
    private Color outlineColor;
    private int outlineWidth = 2;
    private List<GuiShape> shapesToDraw;


    /*******************************************************************************************************************
     * Constructor TODO: Make it take a list of shapes from the gui menu
     */
    public GuiDraw() {
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
        drawBackground(g2);
        drawAllShapes(g2);
    }

    public void drawAllShapes(Graphics2D g2) {
        for (GuiShape shape : shapesToDraw) {
            g2.setStroke(new BasicStroke(shape.getOutlineWidth()));
            int width = shape.getPoint2X() - shape.getPoint1X();
            int height = shape.getPoint2Y() - shape.getPoint1Y();

            // Shape Fill
            g2.setColor(shape.getFillColor());
            if(shape.getShapeId().equals(RECTANGLE)) {
                g2.fill(new Rectangle(shape.getPoint1X(), shape.getPoint1Y(), width, height));
            }
            else if(shape.getShapeId().equals(ELLIPSE)) {
                g2.fill(new Ellipse2D.Double(shape.getPoint1X(), shape.getPoint1Y(), width, height));
            }

            // Shape Outline
            g2.setColor(shape.getOutlineColor());
            if(shape.getShapeId().equals(RECTANGLE)) {
                g2.draw(new Rectangle(shape.getPoint1X(), shape.getPoint1Y(), width, height));
            }
            else if(shape.getShapeId().equals(ELLIPSE)) {
                g2.draw(new Ellipse2D.Double(shape.getPoint1X(), shape.getPoint1Y(), width, height));
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
    public void setOutlineWidth(int outlineWidth)  { this.outlineWidth = outlineWidth; }

    /*******************************************************************************************************************
     * Add Shapes to Draw
     */

    // Draws a rectangle by defining 2 corners
    public void addRect(int point1X, int point1Y, int point2X, int point2Y) {
        GuiShape shape = new GuiShape();
        shape.setShapeId(RECTANGLE);
        shape.setFillColor(this.fillColor);
        shape.setOutlineColor(this.outlineColor);
        shape.setOutlineWidth(this.outlineWidth);

        // Default point positions
        shape.setPoint1X(point1X);
        shape.setPoint1Y(point1Y);
        shape.setPoint2X(point2X);
        shape.setPoint2Y(point2Y);

        // Makes sure shape.point1X is the left-most point
        if(point2X < point1X) {
            shape.setPoint1X(point2X);
            shape.setPoint2X(point1X);
        }

        // Makes sure shape.point1Y is the upper-most point
        if(point2Y < point1Y) {
            shape.setPoint1Y(point2Y);
            shape.setPoint2Y(point1Y);
        }

        shapesToDraw.add(shape);
    }

    // Draws a circle by defining the rectangle it fits inside
    public void addEllipse(int point1X, int point1Y, int point2X, int point2Y) {
        // Add a rectangle to the list of shapes to draw, then turns it into an oval
        addRect(point1X, point1Y, point2X, point2Y);
        shapesToDraw.get(shapesToDraw.size() - 1).setShapeId(ELLIPSE);
    }


    /*******************************************************************************************************************
     * Other Things to Draw
     */
    public void drawBackground (Graphics2D g2) {
        int width = 50000;
        int height = 50000;

        g2.setColor(BACKGROUND_COLOR);
        g2.draw(new Rectangle(0, 0, width, height));
        g2.fill(new Rectangle(0, 0, width, height));
    }


}
