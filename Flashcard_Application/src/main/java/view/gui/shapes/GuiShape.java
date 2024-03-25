package main.java.view.gui.shapes;

import java.awt.*;

public abstract class GuiShape {

    /*******************************************************************************************************************
     *Constants
     */
    private final static Color DEFAULT_FILL = Color.BLUE;
    private final static Color DEFAULT_OUTLINE = Color.BLACK;


    /*******************************************************************************************************************
     * Instance Variables
     */
    private String shapeId;
    private int point1X;
    private int point1Y;
    private int point2X;
    private int point2Y;
    private int point3X;
    private int point3Y;
    private  Color fillColor = DEFAULT_FILL;
    private  Color outlineColor = DEFAULT_OUTLINE;
    private int outlineWidth = 2;

    private int arc;
    private String text;
    private double textScaleX;
    private String imageFileName;
    private int imageWidthInPixels;
    private int imageHeightInPixels;


    /*******************************************************************************************************************
     * Constructor
     */
    public GuiShape() {

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

    public void setOutlineWidth(int outlineWidth) {
        this.outlineWidth = outlineWidth;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    public void setBounds(int point1X, int point1Y, int point2X, int point2Y) {
        // Default values
        this.setPoint1X(point1X);
        this.setPoint1Y(point1Y);
        this.setPoint2X(point2X);
        this.setPoint2Y(point2Y);

        //Makes sure the new Shape's point1X < point2X
        if (point1X > point2X) {
            this.setPoint1X(point2X);
            this.setPoint2X(point1X);
        }

        //Makes sure the new Shape's point1Y < point2Y
        if (point1Y > point2Y) {
            this.setPoint1Y(point2Y);
            this.setPoint2Y(point1Y);
        }
    }

    public void setPoint1X(int newXValue) {
        this.point1X = newXValue;
    }

    public void setPoint1Y(int point1Y) {
        this.point1Y = point1Y;
    }

    public void setPoint2X(int point2X) {
        this.point2X = point2X;
    }

    public void setPoint2Y(int point2Y) {
        this.point2Y = point2Y;
    }

    public void setPoint3X(int point3X) {
        this.point3X = point3X;
    }

    public void setPoint3Y(int point3Y) {
        this.point3Y = point3Y;
    }

    public void setArc(int arc) {
        this.arc = arc;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextScaleX(double textScaleX) {
        this.textScaleX = textScaleX;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setImageWidthInPixels(int imageWidthInPixels) {
        this.imageWidthInPixels = imageWidthInPixels;
    }

    public void setImageHeightInPixels(int getImageHeightInPixels) {
        this.imageHeightInPixels = getImageHeightInPixels;
    }

    /*******************************************************************************************************************
     * Getters
     */
    public String getShapeId() {
        return shapeId;
    }

    public int getPoint1X() {
        return point1X;
    }

    public int getPoint1Y() {
        return point1Y;
    }

    public int getPoint2X() {
        return point2X;
    }

    public int getPoint2Y() {
        return point2Y;
    }

    public int getPoint3X() {
        return point3X;
    }

    public int getPoint3Y() {
        return point3Y;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public int getOutlineWidth() {
        return outlineWidth;
    }

    public int getArc() {
        return arc;
    }

    public String getText() {
        return text;
    }

    public double getTextScaleX() {
        return textScaleX;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public int getImageWidthInPixels() {
        return imageWidthInPixels;
    }

    public int getImageHeightInPixels() {
        return imageHeightInPixels;
    }


    /*******************************************************************************************************************
     * Other Methods
     */
    public void noFill() {
        setFillColor(new Color(0, 0, 0, 0));
    }

    public void noOutline() {
        setOutlineColor(new Color(0, 0, 0, 0));
    }




}
