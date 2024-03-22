package main.java.view.gui;


import main.java.view.gui.shapes.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * TODO: Resize Text With Window
 * TODO: Add Pages
 * TODO: Make buttons
 * TODO: Add mouse over event listener
 * TODO: Add mouse click event listener
 */

public class GuiManager {

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


    /*******************************************************************************************************************
     * Constructor
     */
    public GuiManager(Gui gui) {
        this.gui = gui;
        this.guiEvents = new GuiEvents();
    }


    /*******************************************************************************************************************
     * Main Method
     */
    public static void main(String[] args) {
        Gui gui = new Gui();
        GuiManager guiManager = new GuiManager(gui);
        guiManager.run();
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

}
