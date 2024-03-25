package main.java.view.gui.shapes;

public class GuiImage extends GuiShape {

    /*******************************************************************************************************************
     * Constructor
     */
    public GuiImage(String id, String imageFileName, int point1X, int point1Y) {
        this.setShapeId(id);
        this.setBounds(point1X, point1Y, point1X, point1Y);
        this.setImageFileName(imageFileName);
        this.setImageHeightInPixels(0);  // 0 means use default pixel size
        this.setImageWidthInPixels(0);  // 0 means use default pixel size
    }
}
