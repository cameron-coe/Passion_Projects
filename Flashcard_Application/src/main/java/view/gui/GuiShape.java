package main.java.view.gui;

import java.awt.*;

public class GuiShape {

    /*******************************************************************************************************************
     *Constants
     */
    private final static Color DEFAULT_FILL = Color.BLUE;
    private final static Color DEFAULT_OUTLINE = Color.BLACK;
    public final static int RECTANGLE = 1;
    public final static int ELLIPSE = 2;
    public final static int ROUNDED_RECTANGLE = 3;
    public final static int LINE = 4;
    public final static int TEXT_BOX = 5;
    public final static int TEXT_BOX_LINES = 6;


    /*******************************************************************************************************************
     * Instance Variables
     */

    private int shapeId;
    private int point1X;
    private int point1Y;
    private int point2X;
    private int point2Y;
    private int point3X;
    private int point3Y;
    private  Color fillColor = DEFAULT_FILL;
    private  Color outlineColor = DEFAULT_OUTLINE;
    private  Color textFillColor = DEFAULT_OUTLINE;
    private int outlineWidth = 2;

    private int arc;
    private String text;
    private double textScaleX;


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

    public void setTextFillColor(Color textFillColor) {
        this.textFillColor = textFillColor;
    }

    public void setOutlineWidth(int outlineWidth) {
        this.outlineWidth = outlineWidth;
    }

    public void setShapeId(int shapeId) {
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

    /*******************************************************************************************************************
     * Getters
     */
    public int getShapeId() {
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

    public Color getTextFillColor() {
        return textFillColor;
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


    /*******************************************************************************************************************
     * Make Shapes
     */
    static GuiShape makeRectangle(int point1X, int point1Y, int point2X, int point2Y) {
        GuiShape newShape = new GuiShape();
        newShape.shapeId = RECTANGLE;

        newShape.setBounds(point1X, point1Y, point2X, point2Y);

        return newShape;
    }

    static GuiShape makeEllipse(int point1X, int point1Y, int point2X, int point2Y) {
        GuiShape newShape = GuiShape.makeRectangle(point1X, point1Y, point2X, point2Y);
        newShape.setShapeId(GuiShape.ELLIPSE);

        return newShape;
    }

    static GuiShape makeRoundedRectangle(int point1X, int point1Y, int point2X, int point2Y, int arc) {
        GuiShape newShape = GuiShape.makeRectangle(point1X, point1Y, point2X, point2Y);
        newShape.setShapeId(GuiShape.ROUNDED_RECTANGLE);
        newShape.setArc(arc);

        return newShape;
    }

    static GuiShape makeLine(int point1X, int point1Y, int point2X, int point2Y) {
        GuiShape newShape = new GuiShape();
        newShape.setShapeId(GuiShape.LINE);

        newShape.setPoint1X(point1X);
        newShape.setPoint1Y(point1Y);
        newShape.setPoint2X(point2X);
        newShape.setPoint2Y(point2Y);

        return newShape;
    }

    static GuiShape makeTextBox(String text, int point1X, int point1Y, int point2X, int point2Y) {
        GuiShape newShape = GuiShape.makeRectangle(point1X, point1Y, point2X, point2Y);
        newShape.setShapeId(GuiShape.TEXT_BOX);
        newShape.setText(text);
        newShape.setTextScaleX(1);

        return newShape;
    }

    static GuiShape makeTextBoxLines(int point1X, int point1Y, int point2X, int point2Y) {
        GuiShape newShape = GuiShape.makeRectangle(point1X, point1Y, point2X, point2Y);
        newShape.setShapeId(GuiShape.TEXT_BOX_LINES);

        return newShape;
    }

}
