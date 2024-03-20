package main.java.view.gui;


import main.java.model.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Gui{

    /*******************************************************************************************************************
     * Constants
     */
    private final static Color BACKGROUND_COLOR = new Color(255, 222, 191);
    private final static String HOMEPAGE = "homepage";


    /*******************************************************************************************************************
     * Instance Variables
     */
    private List<GuiShape> shapesToDraw;

    private DrawGraphics drawGraphics;


    /*******************************************************************************************************************
     * Gui Elements
     */
    private GuiShape flashcard;
    private GuiShape homepageTitle;


    /*******************************************************************************************************************
     * Constructor
     */
    public Gui(DrawGraphics drawGraphics) {
        shapesToDraw = new ArrayList<>();
        this.drawGraphics = drawGraphics;


    }





    /*******************************************************************************************************************
     * Menu Displays
     */

    // Called whenever there is an update request from GUI
    public void updateFrame(JFrame jFrame) {
        addHomepageTitle(jFrame);
        addFlashcard(jFrame);
        //shapesToDraw.add(pageTitle);
        drawGraphics.setShapesToDraw(this.shapesToDraw);
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
            homepageTitle = GuiShape.makeTextBox(longString, leftSpacing, 50, rightSpacing, 100);
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
            flashcard = GuiShape.makeRoundedRectangle(cardStartX, cardStartY, cardEndX, cardEndY, 25);
            flashcard.setFillColor(Color.WHITE);

            shapesToDraw.add(flashcard);
        }
    }


    /**
    private void addFlashcard(JFrame jFrame) {

        if (shapesToDraw.contains(flashcard)) {
            // Update
            int indexOfObject = shapesToDraw.indexOf(flashcard);
            shapesToDraw.get(indexOfObject).setBounds(drawGraphics.getMouseX(), drawGraphics.getMouseY(), drawGraphics.getMouseX(), drawGraphics.getMouseY());
            flashcard.setImageWidthInPixels(jFrame.getWidth());
        } else {
            // Instantiate
            flashcard = GuiShape.makeImage("AppIcon.png", drawGraphics.getMouseX(), drawGraphics.getMouseY());

            shapesToDraw.add(flashcard);
        }
    }
*/

}
