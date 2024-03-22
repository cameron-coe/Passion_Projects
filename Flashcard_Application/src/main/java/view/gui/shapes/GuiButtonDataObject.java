package main.java.view.gui.shapes;

import java.awt.*;

public class GuiButtonDataObject extends GuiShapeDataObject {

    /*******************************************************************************************************************
     * Instance variables
     */
    private Color hoverColor = Color.RED;
    private boolean isMouseOver = false;


    /*******************************************************************************************************************
     * Constructor
     */
    public GuiButtonDataObject(String id, int point1X, int point1Y, int point2X, int point2Y, int arc) {
        this.setShapeId(id);
        this.setBounds(point1X, point1Y, point2X, point2Y);
        this.setArc(arc);
    }


    /*******************************************************************************************************************
     * Getters
     */
    public Color getHoverColor() {
        return hoverColor;
    }

    public boolean isMouseOver() {
        return isMouseOver;
    }


    /*******************************************************************************************************************
     * Setters
     */
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setMouseOver(boolean mouseOver) {
        isMouseOver = mouseOver;
    }
}
