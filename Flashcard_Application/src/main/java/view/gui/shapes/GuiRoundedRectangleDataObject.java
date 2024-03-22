package main.java.view.gui.shapes;

public class GuiRoundedRectangleDataObject extends GuiShapeDataObject {

    /*******************************************************************************************************************
     * Constructor
     */
    public GuiRoundedRectangleDataObject(String id, int point1X, int point1Y, int point2X, int point2Y, int arc) {
        this.setShapeId(id);
        this.setBounds(point1X, point1Y, point2X, point2Y);
        this.setArc(arc);
    }
}
