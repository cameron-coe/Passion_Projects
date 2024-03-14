package main.java.view.gui;

import java.awt.*;

public class GuiShape {
    private String shapeId;
    private int point1X;
    private int point1Y;
    private int point2X;
    private int point2Y;
    private int point3X;
    private int point3Y;
    private  Color fillColor;
    private  Color outlineColor;
    private int outlineWidth;


    /*******************************************************************************************************************
     * Constructor
     */
    public GuiShape() {

    }


    /*******************************************************************************************************************
     * Getters and Setters
     */

    public int getPoint1X() {
        return point1X;
    }

    public void setPoint1X(int point1X) {
        this.point1X = point1X;
    }

    public int getPoint1Y() {
        return point1Y;
    }

    public void setPoint1Y(int point1Y) {
        this.point1Y = point1Y;
    }

    public int getPoint2X() {
        return point2X;
    }

    public void setPoint2X(int point2X) {
        this.point2X = point2X;
    }

    public int getPoint2Y() {
        return point2Y;
    }

    public void setPoint2Y(int point2Y) {
        this.point2Y = point2Y;
    }

    public int getPoint3X() {
        return point3X;
    }

    public void setPoint3X(int point3X) {
        this.point3X = point3X;
    }

    public int getPoint3Y() {
        return point3Y;
    }

    public void setPoint3Y(int point3Y) {
        this.point3Y = point3Y;
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

    public int getOutlineWidth() {
        return outlineWidth;
    }

    public void setOutlineWidth(int outlineWidth) {
        this.outlineWidth = outlineWidth;
    }

    public String getShapeId() {
        return shapeId;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }
}
