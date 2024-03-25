package main.java.view.gui.shapes;

public class GuiTextBox extends GuiShape {

    /*******************************************************************************************************************
     * Constructor
     */
    public GuiTextBox(String id, String text, int point1X, int point1Y, int point2X, int point2Y) {
        this.setShapeId(id);
        this.setBounds(point1X, point1Y, point2X, point2Y);
        this.setText(text);
        this.setTextScaleX(1);
    }
}
