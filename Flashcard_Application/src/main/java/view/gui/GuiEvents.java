package main.java.view.gui;

import main.java.view.gui.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiEvents {

    /*******************************************************************************************************************
     * Instance Variables
     */
    private List<GuiShapeDataObject> shapesToDraw;

    String flashcardBaseId = "flashcardBase";
    String borderId = "flashcardBorder";
    String cardHeaderId = "flashcardQuestionNumber";
    String frontTextId = "flashcardFrontText";
    String frontTextLinesId = "flashcardFrontTextLines";
    //String backTextId = "flashcardBackText";

    String testButtonId = "testButton";
    String testButton2Id = "testButton2";


    /*******************************************************************************************************************
     * Constructor
     */
    public GuiEvents() {
        this.shapesToDraw = new ArrayList<>();
    }


    /*******************************************************************************************************************
     * Getter
     */
    public List<GuiShapeDataObject> getShapesToDraw() {
        return shapesToDraw;
    }

    /*******************************************************************************************************************
     * Event Methods
     */
    public List<GuiShapeDataObject> runtimeStartEvent(JFrame jFrame) {
        instantiateFlashcard();
        updateFlashcard(jFrame);

        // TODO - Remove test buttons
        instantiateButton(testButtonId);
        instantiateButton2(testButton2Id);
        updateButton(testButtonId, jFrame);
        updateButton2(testButton2Id, jFrame);

        return this.shapesToDraw;
    }

    public List<GuiShapeDataObject> windowUpdateEvent(JFrame jFrame) {
        updateFlashcard(jFrame);
        return this.shapesToDraw;
    }


    /*******************************************************************************************************************
     * Instantiate Pages
     */
    private void instantiateFlashcardPage() {

    }


    /*******************************************************************************************************************
     * Update Pages
     */




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
            GuiShapeDataObject homepageTitle = new GuiTextBoxDataObject(homepageTitleId, longString, leftSpacing, 50, rightSpacing, 100);
            homepageTitle.setTextFillColor(Color.red);
            shapesToDraw.add(homepageTitle);
        }
    }


    /*******************************************************************************************************************
     * Update Groups
     */
    private void updateFlashcard(JFrame jFrame) {
        int indexOfFlashcardBase = updateFlashcardBase(flashcardBaseId, jFrame);
        GuiShapeDataObject flashcardBase = shapesToDraw.get(indexOfFlashcardBase);

        updateFlashcardHeader(cardHeaderId, flashcardBase);
        updateFlashcardBorder(borderId, flashcardBase);
        updateFlashcardTextLines(frontTextLinesId, flashcardBase);
        updateFlashcardFrontText(frontTextId, flashcardBase);
    }


    /*******************************************************************************************************************
     * Instantiate Groups
     */
    private void instantiateFlashcard() {
        // Instantiate
        instantiateFlashcardBase(flashcardBaseId);
        instantiateFlashcardHeader(cardHeaderId);
        instantiateFlashcardTextLines(frontTextLinesId);
        instantiateFlashcardBorder(borderId);
        instantiateFrontCardText(frontTextId);
    }




    /*******************************************************************************************************************
     * Instantiate GUI Shapes
     */
    private void instantiateFlashcardBase(String id) {
        GuiShapeDataObject flashcardBase = new GuiRoundedRectangleDataObject(id, 0, 0, 0, 0, 25);
        flashcardBase.setFillColor(Color.WHITE);
        flashcardBase.noOutline();
        shapesToDraw.add(flashcardBase);
    }

    public void instantiateFlashcardHeader(String id) {
        GuiShapeDataObject cardHeader = new GuiTextBoxDataObject(id, "For 3 Points:", 0, 0, 0, 0);
        cardHeader.setTextFillColor(Color.BLACK);
        shapesToDraw.add(cardHeader);
    }

    private void instantiateFlashcardBorder(String id) {
        GuiShapeDataObject flashcardBase = new GuiRoundedRectangleDataObject(id, 0, 0, 0, 0, 0);
        flashcardBase.setOutlineColor(Color.ORANGE);
        flashcardBase.noFill();
        shapesToDraw.add(flashcardBase);
    }

    private void instantiateFlashcardTextLines(String id) {
        GuiShapeDataObject frontTextLines = new GuiTextBoxLinesDataObject(id, 0, 0, 0, 0);
        frontTextLines.setOutlineColor(new Color(0,0, 255, 100));
        frontTextLines.setOutlineWidth(1);
        shapesToDraw.add(frontTextLines);
    }

    private void instantiateFrontCardText(String id) {
        GuiShapeDataObject frontText = new GuiTextBoxDataObject(id, "", 0, 0, 0, 0);
        frontText.setTextFillColor(Color.BLACK);
        shapesToDraw.add(frontText);
    }

    private void instantiateButton(String id) {
        GuiShapeDataObject button = new GuiButtonDataObject(id, 0, 0, 0, 0, 25);
        button.setFillColor(Color.cyan);
        shapesToDraw.add(button);
    }

    private void instantiateButton2(String id) {
        GuiShapeDataObject button = new GuiButtonDataObject(id, 0, 0, 0, 0, 25);
        button.setFillColor(Color.cyan);
        shapesToDraw.add(button);
    }


    /*******************************************************************************************************************
     * Update GUI Shapes
     */
    public int updateFlashcardBase(String id, JFrame jFrame) {
        int cardHeight = jFrame.getHeight() - 200;
        int cardWidth = (cardHeight * 5) / 3;

        int cardStartX = (jFrame.getWidth() - cardWidth) / 2;
        int cardEndX = cardStartX + cardWidth;
        int cardStartY = 100;
        cardStartY += 20; // Moves card down
        int cardEndY = jFrame.getHeight() - 100;

        // Update
        int indexOfFlashcardBase = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfFlashcardBase).setBounds(cardStartX, cardStartY, cardEndX, cardEndY);

        return indexOfFlashcardBase;
    }

    public void updateFlashcardHeader(String id, GuiShapeDataObject flashcardBase) {
        double flashcardBaseWidth = flashcardBase.getPoint2X() - flashcardBase.getPoint1X();
        double marginWidth = flashcardBaseWidth * 0.02;

        int startX = (int) (flashcardBase.getPoint1X() + marginWidth);
        int startY = (int) (flashcardBase.getPoint1Y() + (marginWidth * 3));
        int endX = (int) (flashcardBase.getPoint2X() - marginWidth);
        int endY = (int) (flashcardBase.getPoint2Y() - marginWidth);

        int indexOfHeader = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfHeader).setBounds(startX, startY, endX, endY);
    }

    public void updateFlashcardTextLines(String id, GuiShapeDataObject flashcardBase) {
        double flashcardBaseWidth = flashcardBase.getPoint2X() - flashcardBase.getPoint1X();
        double marginWidth = flashcardBaseWidth * 0.02;

        int startX = (int) (flashcardBase.getPoint1X() + (marginWidth * 2));
        int startY = (int) (flashcardBase.getPoint1Y() + (marginWidth * 8));
        int endX = (int) (flashcardBase.getPoint2X() - (marginWidth * 2));
        int endY = (int) (flashcardBase.getPoint2Y() - (marginWidth * 1));

        int indexOfTextLines = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfTextLines).setBounds(startX, startY, endX, endY);
    }

    public void updateFlashcardBorder(String id, GuiShapeDataObject flashcardBase) {
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

    public void updateFlashcardFrontText(String id, GuiShapeDataObject flashcardBase) {
        // Inherits properties of The Text Lines for simplicity
        updateFlashcardTextLines(id, flashcardBase);
        int indexOfText = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfText).setText("The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog.");
    }

    public int updateButton(String id, JFrame jFrame) {
        // Update
        int indexOfObject = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfObject).setBounds(50, 50, 100, 100);

        return indexOfObject;
    }

    public int updateButton2(String id, JFrame jFrame) {
        // Update
        int indexOfObject = indexOfGuiShapeById(id);
        GuiButtonDataObject button = (GuiButtonDataObject) shapesToDraw.get(indexOfObject);
        button.setBounds(150, 50, 200, 100);
        button.setPressedColor(Color.BLACK);


        return indexOfObject;
    }





    /*******************************************************************************************************************
     * Getting Values from the ShapesToDraw List
     */
    private boolean shapesToDrawListContainsId(String id) {
        for (GuiShapeDataObject shape : this.shapesToDraw) {
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
            GuiShapeDataObject shape = this.shapesToDraw.get(i);
            String shapeId = shape.getShapeId();
            if (shapeId != null) {
                if (shapeId.equals(id)) {
                    return i;
                }
            }
        }
        return -1;
    }

}
