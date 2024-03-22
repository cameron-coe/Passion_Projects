package main.java.view.gui.shapes;

public class GuiEllipseDataObject extends GuiShapeDataObject {

    /*******************************************************************************************************************
     * Constructor
     */
    public GuiEllipseDataObject(String id, int point1X, int point1Y, int point2X, int point2Y) {
        this.setShapeId(id);
        this.setBounds(point1X, point1Y, point2X, point2Y);
    }
}
