package main.java.view.gui.shapes;

import java.awt.*;

public abstract class GuiShape {
    private int xPos = 0;
    private int yPos = 0;
    private int width = 100;
    private int height = 100;
    private  Color fillColor = Color.BLACK;
    private  Color outlineColor = Color.BLUE;


    /*******************************************************************************************************************
     * Constructor
     */
    public GuiShape() {

    }


    /*******************************************************************************************************************
     * Getters and Setters
     */
    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }
}
