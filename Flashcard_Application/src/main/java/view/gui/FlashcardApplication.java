package main.java.view.gui;


import main.java.view.gui.shapes.GuiShapeDataObject;

import javax.swing.*;
import java.util.List;

/**
 * TODO: Resize Text With Window
 * TODO: Add Application Pages
 * TODO: Make buttons that click and return a value
 * TODO: Add mouse click event listener
 */

public class FlashcardApplication {

    /*******************************************************************************************************************
     * Constants
     */
    private final static String HOMEPAGE = "homepage";


    /*******************************************************************************************************************
     * Instance Variables
     */
    private Gui gui;
    private GuiEvents guiEvents;
    private ButtonManager buttonManager;



    /*******************************************************************************************************************
     * Constructor
     */
    public FlashcardApplication(Gui gui) {
        this.gui = gui;
        this.guiEvents = new GuiEvents();
        this.buttonManager = new ButtonManager();
    }


    /*******************************************************************************************************************
     * Main Method
     */
    public static void main(String[] args) {
        Gui gui = new Gui();
        FlashcardApplication flashcardApplication = new FlashcardApplication(gui);
        flashcardApplication.run();
    }


    /*******************************************************************************************************************
     * Runtime Method
     */
    public void run () {
        //
    }


    /*******************************************************************************************************************
     * Subscribes Event Actions to Listeners
     */
    public void runtimeStartEvent(JFrame jFrame) {
        List<GuiShapeDataObject> shapesToDraw = guiEvents.runtimeStartEvent(jFrame);
        gui.setShapesToDraw(shapesToDraw);
        gui.repaint();
        System.out.println("RUNTIME START EVENT >>> " + shapesToDraw.size());
    }

    public void windowSizeChangeEvent(JFrame jFrame) {
        List<GuiShapeDataObject> shapesToDraw = guiEvents.windowUpdateEvent(jFrame);
        gui.setShapesToDraw(shapesToDraw);
        gui.repaint();
        System.out.println("WINDOW SIZE CHANGE EVENT >>> " + shapesToDraw.size());
    }

    public void mouseMoveEvent(JFrame jFrame) {
        boolean isUpdate = buttonManager.updateButtonsWhenMouseGoesInOrOut(gui, guiEvents);
        if (isUpdate) {
            gui.repaint();
        }

        List<GuiShapeDataObject> shapesToDraw = guiEvents.windowUpdateEvent(jFrame);
        System.out.println("MOUSE MOVE EVENT >>> " + shapesToDraw.size());
    }

    public void mousePressedEvent(JFrame jFrame) {
        boolean isAButtonPressed = buttonManager.mouseDownOnButton(guiEvents);
        if (isAButtonPressed) {
            gui.repaint();
        }

        List<GuiShapeDataObject> shapesToDraw = guiEvents.windowUpdateEvent(jFrame);
        System.out.println("MOUSE PRESSED EVENT >>> " + shapesToDraw.size());
    }

    public void mouseReleasedEvent(JFrame jFrame) {
        String result = buttonManager.mouseReleasedOnButton(gui);

        if (result != null) {
            gui.repaint();
        }

        // Makes sure all shapes are prepped to be redrawn
        List<GuiShapeDataObject> shapesToDraw = guiEvents.windowUpdateEvent(jFrame);
        gui.setShapesToDraw(shapesToDraw);

        System.out.println("MOUSE RELEASED EVENT >>> " + result);
    }



}
