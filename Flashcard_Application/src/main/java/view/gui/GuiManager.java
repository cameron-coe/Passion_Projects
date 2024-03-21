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
        String cardHeaderId = "flashcardQuestionNumber";
        String frontTextId = "flashcardFrontText";
        String frontTextLinesId = "flashcardFrontTextLines";
        //String backTextId = "flashcardBackText";

        if (shapesToDrawListContainsId(flashcardBaseId)) {
            // Update
            int indexOfFlashcardBase = indexOfGuiShapeById(flashcardBaseId);
            shapesToDraw.get(indexOfFlashcardBase).setBounds(cardStartX, cardStartY, cardEndX, cardEndY);

            GuiShape flashcardBase = shapesToDraw.get(indexOfFlashcardBase);

            updateFlashcardHeader(cardHeaderId, flashcardBase);
            updateFlashcardBorder(borderId, flashcardBase);
            updateFlashcardTextLines(frontTextLinesId, flashcardBase);
            updateFlashcardFrontText(frontTextId, flashcardBase);

        } else {
            // Instantiate
            instantiateFlashcardBase(flashcardBaseId, cardStartX, cardStartY, cardEndX, cardEndY);
            instantiateFlashcardHeader(cardHeaderId);
            instantiateFlashcardTextLines(frontTextLinesId);
            instantiateFlashcardBorder(borderId);
            instantiateFrontCardText(frontTextId);
            /**********/



        }
    }

    private void instantiateFlashcardBase(String id, int cardStartX, int cardStartY, int cardEndX, int cardEndY) {
        GuiShape flashcardBase = new GuiRoundedRectangle(id, cardStartX, cardStartY, cardEndX, cardEndY, 25);
        flashcardBase.setFillColor(Color.WHITE);
        flashcardBase.noOutline();
        shapesToDraw.add(flashcardBase);
    }

    public void instantiateFlashcardHeader(String id) {
        GuiShape cardHeader = new GuiTextBox(id, "For 3 Points:", 0, 0, 0, 0);
        cardHeader.setTextFillColor(Color.BLACK);
        shapesToDraw.add(cardHeader);
    }

    private void instantiateFlashcardBorder(String id) {
        GuiShape flashcardBase = new GuiRoundedRectangle(id, 0, 0, 0, 0, 0);
        flashcardBase.setOutlineColor(Color.ORANGE);
        flashcardBase.noFill();
        shapesToDraw.add(flashcardBase);
    }

    private void instantiateFlashcardTextLines(String id) {
        GuiShape frontTextLines = new GuiTextBoxLines(id, 0, 0, 0, 0);
        frontTextLines.setOutlineColor(new Color(0,0, 255, 100));
        frontTextLines.setOutlineWidth(1);
        shapesToDraw.add(frontTextLines);
    }

    private void instantiateFrontCardText(String id) {
        GuiShape frontText = new GuiTextBox(id, "", 0, 0, 0, 0);
        frontText.setTextFillColor(Color.BLACK);
        shapesToDraw.add(frontText);
    }


    public void updateFlashcardHeader(String id, GuiShape flashcardBase) {
        double flashcardBaseWidth = flashcardBase.getPoint2X() - flashcardBase.getPoint1X();
        double marginWidth = flashcardBaseWidth * 0.02;

        int startX = (int) (flashcardBase.getPoint1X() + marginWidth);
        int startY = (int) (flashcardBase.getPoint1Y() + (marginWidth * 3));
        int endX = (int) (flashcardBase.getPoint2X() - marginWidth);
        int endY = (int) (flashcardBase.getPoint2Y() - marginWidth);

        int indexOfHeader = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfHeader).setBounds(startX, startY, endX, endY);
    }

    public void updateFlashcardTextLines(String id, GuiShape flashcardBase) {
        double flashcardBaseWidth = flashcardBase.getPoint2X() - flashcardBase.getPoint1X();
        double marginWidth = flashcardBaseWidth * 0.02;

        int startX = (int) (flashcardBase.getPoint1X() + marginWidth);
        int startY = (int) (flashcardBase.getPoint1Y() + (marginWidth * 8));
        int endX = (int) (flashcardBase.getPoint2X() - marginWidth);
        int endY = (int) (flashcardBase.getPoint2Y() - (marginWidth * 2));

        int indexOfTextLines = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfTextLines).setBounds(startX, startY, endX, endY);
    }

    public void updateFlashcardBorder(String id, GuiShape flashcardBase) {
        double flashcardBaseWidth = flashcardBase.getPoint2X() - flashcardBase.getPoint1X();
        double marginWidth = flashcardBaseWidth * 0.02;
        int arc = (int) (flashcardBase.getArc() * 0.9);

        int startX = (int) (flashcardBase.getPoint1X() + marginWidth);
        int startY = (int) (flashcardBase.getPoint1Y() + marginWidth);
        int endX = (int) (flashcardBase.getPoint2X() - marginWidth);
        int endY = (int) (flashcardBase.getPoint2Y() - marginWidth);

        int indexOfBorder = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfBorder).setBounds(startX, startY, endX, endY);
        shapesToDraw.get(indexOfBorder).setArc(arc);
    }

    public void updateFlashcardFrontText(String id, GuiShape flashcardBase) {
        updateFlashcardTextLines(id, flashcardBase);
        int indexOfText = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfText).setText("The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog.");
    }


    /*******************************************************************************************************************
     * Getting Values from the ShapesToDraw List
     */
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
