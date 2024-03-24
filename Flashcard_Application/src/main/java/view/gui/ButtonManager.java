package main.java.view.gui;

import main.java.view.gui.shapes.GuiButtonDataObject;
import main.java.view.gui.shapes.GuiShapeDataObject;

public class ButtonManager {

    /*******************************************************************************************************************
     * Instance Variables
     */
    private boolean isMouseOverButtonState = false;
    private GuiButtonDataObject hoveredOverButton = null;
    private GuiButtonDataObject pressedDownButton = null;


    /*******************************************************************************************************************
     * Constructor
     */
    public ButtonManager () {

    }


    /*******************************************************************************************************************
     * Update Buttons
     * Highlights buttons when the mouse hovers over them
     * Makes the active button the button being hovered over
     * returns true if there is an update
     */
    public boolean updateButtonsWhenMouseGoesInOrOut(Gui gui, GuiEvents guiEvents) {
        boolean isUpdate = false;

        // Button Activation
        hoveredOverButton = buttonTheMouseIsOver(gui, guiEvents);
        boolean isMouseOverAButton = hoveredOverButton != null;


        if (isMouseOverAButton && !isMouseOverButtonState) {
            // Repaints and updates when mouse enters the button
            isMouseOverButtonState = true;
            hoveredOverButton.setMouseOver(true);
            isUpdate = true;
        }
        else if (!isMouseOverAButton && isMouseOverButtonState) {
            // Repaints and updates when the mouse leaves the button
            isMouseOverButtonState = false;
            if (pressedDownButton != null) {
                pressedDownButton.setMousePressed(false);
                pressedDownButton.setMouseOver(false);
                pressedDownButton = null;
            }
            isUpdate = true;
        }

        return isUpdate;
    }


    /*******************************************************************************************************************
     * Sets pressedDownButton when the mouse presses down on the button
     * returns true if pressed down on a button
     */
    public boolean mouseDownOnButton(GuiEvents guiEvents) {
        if (hoveredOverButton != null) {
            pressedDownButton = hoveredOverButton;
            pressedDownButton.setMousePressed(true);
            return true;
        }
        return false;
    }


    /*******************************************************************************************************************
     * When the mouse completes a click on the button
     */
    public String mouseReleasedOnButton(Gui gui) {
        String result = null;
        if (hoveredOverButton != null && pressedDownButton != null) {
            if (hoveredOverButton.getShapeId().equals(pressedDownButton.getShapeId())) {
                result = pressedDownButton.getShapeId();
                pressedDownButton.setMousePressed(false);
                pressedDownButton = null;
            }
        }

        return result;
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
