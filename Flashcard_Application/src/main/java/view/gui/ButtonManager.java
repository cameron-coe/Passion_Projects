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
     */
    public void updateButtonsWhenMouseGoesInOrOut(Gui gui, GuiEvents guiEvents) {
        // Button Activation
        hoveredOverButton = buttonTheMouseIsOver(gui, guiEvents);
        boolean isMouseOverAButton = hoveredOverButton != null;

        // Repaints and updates when mouse enters the button
        if (isMouseOverAButton && !isMouseOverButtonState) {
            isMouseOverButtonState = true;
            hoveredOverButton.setMouseOver(true);
            gui.repaint();
        }

        // Repaints and updates when the mouse leaves the button
        else if (!isMouseOverAButton && isMouseOverButtonState) {
            isMouseOverButtonState = false;
            gui.repaint();
        }
    }


    /*******************************************************************************************************************
     * When the mouse presses down on the button
     */
    public void mouseDownOnButton(Gui gui, GuiEvents guiEvents) {
        if (hoveredOverButton != null) {
            pressedDownButton = hoveredOverButton;

            // TODO: Check if hovered over button has the same id as the pressed down button

            isMouseOverButtonState = false;  // Resets the state so the next method will update
            updateButtonsWhenMouseGoesInOrOut(gui, guiEvents);
        }
        //int mouseDownX = gui.getMouseDownX();
        //int mouseDownY = gui.getMouseDownY();
    }


    /*******************************************************************************************************************
     * When the mouse completes a click on the button
     */
    public void mouseClickOnButton() {

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
