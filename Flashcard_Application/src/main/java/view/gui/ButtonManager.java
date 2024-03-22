package main.java.view.gui;

import main.java.view.gui.shapes.GuiButtonDataObject;
import main.java.view.gui.shapes.GuiShapeDataObject;

public class ButtonManager {

    /*******************************************************************************************************************
     * Instance Variables
     */
    private boolean isMouseOverButtonState = false;


    /*******************************************************************************************************************
     * Constructor
     */
    public ButtonManager () {

    }


    /*******************************************************************************************************************
     * Update Buttons
     */
    public void updateButtons (Gui gui, GuiEvents guiEvents) {
        // Button Activation
        GuiButtonDataObject activeButton = buttonTheMouseIsOver(gui, guiEvents);
        boolean isMouseOverAButton = activeButton != null;

        // Repaints and updates when mouse enters the button
        if (isMouseOverAButton && !isMouseOverButtonState) {
            isMouseOverButtonState = true;
            activeButton.setMouseOver(true);
            gui.repaint();
        }

        // Repaints and updates when the mouse leaves the button
        else if (!isMouseOverAButton && isMouseOverButtonState) {
            isMouseOverButtonState = false;
            gui.repaint();
        }
    }


    /*******************************************************************************************************************
     * Check if the mouse is over a button
     * return the button it's over as a GuiButtonDataObject
     */
    private GuiButtonDataObject buttonTheMouseIsOver(Gui gui, GuiEvents guiEvents) {
        for (GuiShapeDataObject shape : guiEvents.getShapesToDraw()) {
            if (shape instanceof GuiButtonDataObject) {
                int mouseX = gui.getMouseX();
                int mouseY = gui.getMouseY();

                // Checks if mouse is inside button
                if (mouseX > shape.getPoint1X() && mouseX < shape.getPoint2X() && mouseY > shape.getPoint1Y() && mouseY < shape.getPoint2Y()) {
                    return (GuiButtonDataObject) shape;
                } else {
                    ((GuiButtonDataObject) shape).setMouseOver(false);
                }
            }
        }
        return null;
    }
}
