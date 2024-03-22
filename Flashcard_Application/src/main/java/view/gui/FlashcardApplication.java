package main.java.view.gui;


import main.java.view.gui.shapes.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * TODO: Encapsulate Drawing the frame
 * TODO: Resize Text With Window
 * TODO: Add Application Pages
 * TODO: Make buttons that click and return a value
 * TODO: Add mouse click event listener
 */

public class FlashcardApplication {

    /*******************************************************************************************************************
     * Constants
     */
    private final static Color BACKGROUND_COLOR = new Color(255, 222, 191);
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
        List<GuiShapeDataObject> shapesToDraw = guiEvents.windowUpdateEvent(jFrame);
        buttonManager.updateButtons(gui, guiEvents);
        System.out.println("MOUSE MOVE EVENT >>> " + shapesToDraw.size());
    }


    /*******************************************************************************************************************
     * Runtime Method
     */
    public void run () {
        //
    }


    /*******************************************************************************************************************
     * Button Pressed Method -- Gives behaviors to all buttons
     */
    public void buttonPress(String buttonId) {

    }



    /*******************************************************************************************************************
     * Checks if mouse if over a button
     */


}
