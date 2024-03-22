package main.java.view.gui.shapes;

public class GuiRectangleDataObject extends GuiShapeDataObject {

    /*******************************************************************************************************************
     * Constructor
     */
    public GuiRectangleDataObject(String id, int point1X, int point1Y, int point2X, int point2Y) {
        this.setShapeId(id);
        this.setBounds(point1X, point1Y, point2X, point2Y);
    }



}
