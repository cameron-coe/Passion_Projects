package main.java.view.gui;


import main.java.view.gui.shapes.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiManager {

    /*******************************************************************************************************************
     * Constants
     */
    private final static Color BACKGROUND_COLOR = new Color(255, 222, 191);
    private final static String HOMEPAGE = "homepage";


    /*******************************************************************************************************************
     * Instance Variables
     */
    private List<GuiShape> shapesToDraw;

    private Gui gui;


    /*******************************************************************************************************************
     * GuiManager Elements
     */
    private GuiShape flashcard;
    private GuiShape homepageTitle;


    /*******************************************************************************************************************
     * Constructor
     */
    public GuiManager(Gui gui) {
        shapesToDraw = new ArrayList<>();
        this.gui = gui;


    }





    /*******************************************************************************************************************
     * Menu Displays
     */

    // Called whenever there is an update request from GUI
    public void updateFrame(JFrame jFrame) {
        addHomepageTitle(jFrame);
        addFlashcard(jFrame);
        //shapesToDraw.add(pageTitle);
        gui.setShapesToDraw(this.shapesToDraw);
    }

    private void addHomepageTitle(JFrame jFrame) {
        int leftSpacing = 10;
        int rightSpacing = jFrame.getWidth() - leftSpacing - 7;


        if (shapesToDraw.contains(homepageTitle)) {
            // Update
            int indexOfObject = shapesToDraw.indexOf(homepageTitle);
            shapesToDraw.get(indexOfObject).setBounds(leftSpacing, 50, rightSpacing, 100);
        } else {
            // Instantiate
            String longString = "Homepage Homepage Homepage Homepage Homepage Homepage Homepage ";
            homepageTitle = new GuiTextBox(longString, leftSpacing, 50, rightSpacing, 100);
            homepageTitle.setTextFillColor(Color.red);
            shapesToDraw.add(homepageTitle);
        }
    }


    private void addFlashcard(JFrame jFrame) {
        int cardHeight = jFrame.getHeight() - 200;
        int cardWidth = (cardHeight * 5) / 3;

        int cardStartX = (jFrame.getWidth() - cardWidth) / 2;
        int cardEndX = cardStartX + cardWidth;
        int cardStartY = (jFrame.getHeight() - cardHeight) / 2;
        cardStartY += 20; // Moves card down
        int cardEndY = cardStartY + cardHeight;

        if (shapesToDraw.contains(flashcard)) {
            // Update
            int indexOfObject = shapesToDraw.indexOf(flashcard);
            shapesToDraw.get(indexOfObject).setBounds(cardStartX, cardStartY, cardEndX, cardEndY);
            flashcard.setImageWidthInPixels(jFrame.getWidth()/2);
        } else {
            // Instantiate
            flashcard = new GuiRoundedRectangle(cardStartX, cardStartY, cardEndX, cardEndY, 25);
            flashcard.setFillColor(Color.WHITE);

            shapesToDraw.add(flashcard);
        }
    }


    /**
    private void addFlashcard(JFrame jFrame) {

        if (shapesToDraw.contains(flashcard)) {
            // Update
            int indexOfObject = shapesToDraw.indexOf(flashcard);
            shapesToDraw.get(indexOfObject).setBounds(gui.getMouseX(), gui.getMouseY(), gui.getMouseX(), gui.getMouseY());
            flashcard.setImageWidthInPixels(jFrame.getWidth());
        } else {
            // Instantiate
            flashcard = GuiShape.makeImage("AppIcon.png", gui.getMouseX(), gui.getMouseY());

            shapesToDraw.add(flashcard);
        }
    }
*/

}
