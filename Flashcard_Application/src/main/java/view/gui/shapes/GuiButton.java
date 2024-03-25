package main.java.view.gui.shapes;

import java.awt.*;

public class GuiButton extends GuiShape {

    /*******************************************************************************************************************
     * Instance variables
     */
    private Color hoverColor = Color.RED;
    private Color pressedColor = Color.YELLOW;
    private boolean isMouseOver = false;
    private boolean isMousePressed = false;


    /*******************************************************************************************************************
     * Constructor
     */
    public GuiButton(String id, int point1X, int point1Y, int point2X, int point2Y, int arc) {
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

    public Color getPressedColor() {
        return pressedColor;
    }

    public boolean isMousePressed() {
        return isMousePressed;
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

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    public void setMousePressed(boolean mousePressed) {
        isMousePressed = mousePressed;
    }
}
