package main.java.view.gui;


import main.java.view.gui.shapes.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Put Shape instantiation and update parts into methods -- have all the flashcards be based on the width of the flashcard base
 * TODO: Resize Text With Window
 *
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
    private List<GuiShape> shapesToDraw;

    private Gui gui;


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


        String homepageTitleId = "homepageTitle";
        if (shapesToDrawListContainsId(homepageTitleId)) {
            // Update
            int indexOfObject = indexOfGuiShapeById(homepageTitleId);
            shapesToDraw.get(indexOfObject).setBounds(leftSpacing, 50, rightSpacing, 100);
        } else {
            // Instantiate
            String longString = "Homepage Homepage Homepage Homepage Homepage Homepage Homepage ";
            GuiShape homepageTitle = new GuiTextBox(homepageTitleId, longString, leftSpacing, 50, rightSpacing, 100);
            homepageTitle.setTextFillColor(Color.red);
            shapesToDraw.add(homepageTitle);
        }
    }


    private void addFlashcard(JFrame jFrame) {
        int cardHeight = jFrame.getHeight() - 200;
        int cardWidth = (cardHeight * 5) / 3;

        int cardStartX = (jFrame.getWidth() - cardWidth) / 2;
        int cardEndX = cardStartX + cardWidth;
        int cardStartY = 100;
        cardStartY += 20; // Moves card down
        int cardEndY = jFrame.getHeight() - 100;

        int flashcardBorderSpacing = (int) (0.02 * cardWidth);

        String flashcardBaseId = "flashcardBase";
        String borderId = "flashcardBorder";
        String questionNumberId = "flashcardQuestionNumber";
        String frontTextId = "flashcardFrontText";
        String frontTextLinesId = "flashcardFrontTextLines";
        //String backTextId = "flashcardBackText";

        if (shapesToDrawListContainsId(flashcardBaseId)) {
            // Update
            int indexOfFlashcardBase = indexOfGuiShapeById(flashcardBaseId);
            shapesToDraw.get(indexOfFlashcardBase).setBounds(cardStartX, cardStartY, cardEndX, cardEndY);

            int indexOfBorder = indexOfGuiShapeById(borderId);
            shapesToDraw.get(indexOfBorder).setBounds(cardStartX + flashcardBorderSpacing,
                    cardStartY + flashcardBorderSpacing,
                    cardEndX - flashcardBorderSpacing,
                    cardEndY - flashcardBorderSpacing);

            int indexOfQuestionNumber = indexOfGuiShapeById(questionNumberId);
            shapesToDraw.get(indexOfQuestionNumber).setBounds(cardStartX, cardStartY + ((int) (0.08 * cardWidth)), cardEndX, cardEndY);

            int indexOfTextBoxLines = indexOfGuiShapeById(frontTextLinesId);
            shapesToDraw.get(indexOfTextBoxLines).setBounds(cardStartX + (flashcardBorderSpacing * 2),
                    cardStartY + ((int) (0.2 * cardWidth)),
                    cardEndX - (flashcardBorderSpacing * 2),
                    cardEndY - (flashcardBorderSpacing * 2));

            int indexOfTextBox = indexOfGuiShapeById(frontTextId);
            shapesToDraw.get(indexOfTextBox).setBounds(cardStartX + (flashcardBorderSpacing * 2),
                    cardStartY + ((int) (0.2 * cardWidth)),
                    cardEndX - (flashcardBorderSpacing * 2),
                    cardEndY - (flashcardBorderSpacing * 2));

        } else {
            // Instantiate
            GuiShape flashcardBase = new GuiRoundedRectangle(flashcardBaseId, cardStartX, cardStartY, cardEndX, cardEndY, 25);
            flashcardBase.setFillColor(Color.WHITE);
            shapesToDraw.add(flashcardBase);

            GuiShape questionNumber = new GuiTextBox(questionNumberId, "Question ##:",
                    cardStartX + flashcardBorderSpacing,
                    cardStartY + flashcardBorderSpacing + 50,
                    cardEndX - (2 * flashcardBorderSpacing),
                    cardEndY - (2 * flashcardBorderSpacing));
            questionNumber.setTextFillColor(Color.BLACK);
            shapesToDraw.add(questionNumber);

            GuiShape frontTextLines = new GuiTextBoxLines(frontTextLinesId, 0, 0, 0, 0);
            frontTextLines.setOutlineColor(new Color(0,0, 255, 100));
            frontTextLines.setOutlineWidth(6); // TODO: set width
            shapesToDraw.add(frontTextLines);

            GuiShape frontText = new GuiTextBox(frontTextId, "The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog.", cardStartX, cardStartY + 50, cardEndX, cardEndY);
            frontText.setTextFillColor(Color.BLACK);
            shapesToDraw.add(frontText);

            GuiShape flashcardBorder = new GuiRoundedRectangle(borderId,
                    cardStartX + flashcardBorderSpacing,
                    cardStartY + flashcardBorderSpacing,
                    cardEndX - (2 * flashcardBorderSpacing),
                    cardEndY - (2 * flashcardBorderSpacing), 25);
            flashcardBorder.setOutlineColor(Color.ORANGE);
            flashcardBorder.setOutlineWidth(2);
            flashcardBorder.noFill();
            shapesToDraw.add(flashcardBorder);
        }
    }


    private boolean shapesToDrawListContainsId(String id) {
        for (GuiShape shape : this.shapesToDraw) {
            String shapeId = shape.getShapeId();
            if (shapeId != null) {
                if (shapeId.equals(id)) {
                    return true;
                }
            }
        }

        return false;
    }

    private int indexOfGuiShapeById(String id) {
        for (int i = 0; i < this.shapesToDraw.size(); i++) {
            GuiShape shape = this.shapesToDraw.get(i);
            String shapeId = shape.getShapeId();
            if (shapeId != null) {
                if (shapeId.equals(id)) {
                    return i;
                }
            }
        }

        return -1;
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

/*******************************************************************************************************************
 * Main Method
 */
    public static void main(String[] args) {
        Gui gui = new Gui();
        GuiManager guiManager = new GuiManager(gui);
        guiManager.run();
    }


    /*******************************************************************************************************************
     * Runtime Method
     */
    public void run () {
        //
    }


}
