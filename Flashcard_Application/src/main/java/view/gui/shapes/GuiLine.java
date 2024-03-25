package main.java.view.gui.shapes;

public class GuiLine extends GuiShape {

    /*******************************************************************************************************************
     * Constructor
     */
    public GuiLine(String id, int point1X, int point1Y, int point2X, int point2Y) {
        this.setShapeId(id);
        this.setPoint1X(point1X);
        this.setPoint1Y(point1Y);
        this.setPoint2X(point2X);
        this.setPoint2Y(point2Y);
    }
}
