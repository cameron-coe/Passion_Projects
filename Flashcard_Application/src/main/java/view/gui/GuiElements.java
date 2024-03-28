package main.java.view.gui;

import main.java.model.Deck;
import main.java.model.Subject;
import main.java.view.gui.shapes.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiElements {

    /*******************************************************************************************************************
     * Constants
     */
    final String FLASHCARD_BASE_ID = "flashcardBase";
    final String FLASHCARD_BORDER_ID = "flashcardBorder";
    final String FLASHCARD_HEADER_ID = "flashcardQuestionNumber";
    final String FLASHCARD_FRONT_TEXT_ID = "flashcardFrontText";
    final String FLASHCARD_FRONT_TEXT_LINES_ID = "flashcardFrontTextLines";
    // final String FLASHCARD_BACK_TEXT_ID = "flashcardBackText";

    final String PAGE_HEADER_ID = "pageHeader";

    static final String SUBJECT_PAGE_BUTTON_ID = "subjectPageButton";
    static final String SELECT_SUBJECT_BUTTON_ID = "selectSubjectButton";
    private static final String SELECT_SUBJECT_BUTTON_TEXT_ID = "selectSubjectButtonText";

    static final String SELECT_DECK_BUTTON_ID = "selectDeckButton";
    private static final String SELECT_DECK_BUTTON_TEXT_ID = "selectDeckButtonText";



    /*******************************************************************************************************************
     * Instance Variables
     */
    private List<GuiShape> shapesToDraw;


    /*******************************************************************************************************************
     * Constructor
     */
    public GuiElements() {
        this.shapesToDraw = new ArrayList<>();
    }


    /*******************************************************************************************************************
     * Getter
     */
    public List<GuiShape> getShapesToDraw() {
        return shapesToDraw;
    }


    /*******************************************************************************************************************
     * Setter
     */
    public void setShapesToDraw(List<GuiShape> shapesToDraw) {
        this.shapesToDraw = shapesToDraw;
    }


    /*******************************************************************************************************************
     * Update Groups
     */
    public void updateFlashcard(JFrame jFrame) {
        int indexOfFlashcardBase = updateFlashcardBase(FLASHCARD_BASE_ID, jFrame);
        GuiShape flashcardBase = shapesToDraw.get(indexOfFlashcardBase);

        updateFlashcardHeader(FLASHCARD_HEADER_ID, flashcardBase);
        updateFlashcardBorder(FLASHCARD_BORDER_ID, flashcardBase);
        updateFlashcardTextLines(FLASHCARD_FRONT_TEXT_LINES_ID, flashcardBase);
        updateFlashcardFrontText(FLASHCARD_FRONT_TEXT_ID, flashcardBase);
    }


    /*******************************************************************************************************************
     * Instantiate Groups
     */
    public void instantiateFlashcard() {
        // Instantiate
        instantiateFlashcardBase(FLASHCARD_BASE_ID);
        instantiateFlashcardHeader(FLASHCARD_HEADER_ID);
        instantiateFlashcardTextLines(FLASHCARD_FRONT_TEXT_LINES_ID);
        instantiateFlashcardBorder(FLASHCARD_BORDER_ID);
        instantiateFrontCardText(FLASHCARD_FRONT_TEXT_ID);
    }




    /*******************************************************************************************************************
     * Instantiate Headers
     */
    public void instantiateSubjectPageHeader() {
        GuiShape subjectPageHeader = new GuiTextBox(PAGE_HEADER_ID, "Select a Subject", 0, 0, 0, 0);
        subjectPageHeader.setFillColor(Color.BLACK);
        shapesToDraw.add(subjectPageHeader);
    }

    public void instantiateCurrentSubjectPageHeader(Subject subject) {
        GuiShape subjectPageHeader = new GuiTextBox(PAGE_HEADER_ID, subject.getName(), 0, 0, 0, 0);
        subjectPageHeader.setFillColor(Color.BLACK);
        shapesToDraw.add(subjectPageHeader);
    }

    public void instantiateCurrentDeckPageHeader(Deck deck) {
        String headerText = "All Flashcards in " +  deck.getDeckName();
        GuiShape subjectPageHeader = new GuiTextBox(PAGE_HEADER_ID, headerText, 0, 0, 0, 0);
        subjectPageHeader.setFillColor(Color.BLACK);
        shapesToDraw.add(subjectPageHeader);
    }

    /*******************************************************************************************************************
     * Update Headers
     */
    public void updatePageHeader(JFrame jFrame) {
        int indexOfPageHeader = indexOfGuiShapeById(PAGE_HEADER_ID);
        shapesToDraw.get(indexOfPageHeader).setBounds(0, 50, jFrame.getWidth(), 150);
    }


    /*******************************************************************************************************************
     * Instantiate Subject Selection Button Elements
     */
    public void instantiateSubjectSelectionButtons(List<Subject> subjects) {
        for(int i = 0; i < subjects.size(); i++) {
            String uniqueButtonId = SELECT_SUBJECT_BUTTON_ID + i;
            String uniqueButtonTextId = SELECT_SUBJECT_BUTTON_TEXT_ID + i;
            instantiateSubjectButton(uniqueButtonId);
            instantiateSubjectButtonText(uniqueButtonTextId, subjects.get(i));
        }
    }

    private void instantiateSubjectButton(String id) {
        GuiShape button = new GuiButton(id, 0, 0, 0, 0, 25);
        button.setFillColor(Color.magenta);
        ((GuiButton)button).setHoverColor(Color.YELLOW);
        ((GuiButton)button).setPressedColor(Color.GREEN);
        shapesToDraw.add(button);
    }

    private void instantiateSubjectButtonText(String id, Subject subject) {
        GuiShape text = new GuiTextBox(id, subject.getName(), 0, 0, 0, 0);
        text.setFillColor(Color.BLACK);
        shapesToDraw.add(text);
    }


    /*******************************************************************************************************************
     * Update Subject Selection Buttons
     */
    public void updateSubjectSelectionButtons(JFrame jFrame, List<Subject> subjects) {
        for(int i = 0; i < subjects.size(); i++) {
            updateSubjectButton(jFrame, i);
            updateSubjectButtonText(i);
        }
    }

    private void updateSubjectButton(JFrame jFrame, int index) {
        String uniqueId = SELECT_SUBJECT_BUTTON_ID + index;
        formatSelectionButtons(jFrame, index, uniqueId);
    }

    private void updateSubjectButtonText(int index) {
        String baseButtonId = SELECT_SUBJECT_BUTTON_ID + index;
        String uniqueButtonTextId = SELECT_SUBJECT_BUTTON_TEXT_ID + index;

        formatSelectionButtons(baseButtonId, uniqueButtonTextId);
    }


    /*******************************************************************************************************************
     * Instantiate Deck Selection Button Elements
     */
    public void instantiateDeckSelectionButtons(List<Deck> decks) {
        for(int i = 0; i < decks.size(); i++) {
            String uniqueButtonId = SELECT_DECK_BUTTON_ID + i;
            String uniqueButtonTextId = SELECT_DECK_BUTTON_TEXT_ID + i;
            instantiateDeckButton(uniqueButtonId);
            instantiateDeckButtonText(uniqueButtonTextId, decks.get(i));
        }
    }

    private void instantiateDeckButton(String id) {
        GuiShape button = new GuiButton(id, 0, 0, 0, 0, 25);
        button.setFillColor(Color.BLUE);
        ((GuiButton)button).setHoverColor(Color.YELLOW);
        ((GuiButton)button).setPressedColor(Color.GREEN);
        shapesToDraw.add(button);
    }

    private void instantiateDeckButtonText(String id, Deck deck) {
        GuiShape text = new GuiTextBox(id, deck.getDeckName(), 0, 0, 0, 0);
        text.setFillColor(Color.BLACK);
        shapesToDraw.add(text);
    }

    /*******************************************************************************************************************
     * Update Deck Selection Buttons
     */
    public void updateDeckSelectionButtons(JFrame jFrame, List<Deck> decks) {
        for(int i = 0; i < decks.size(); i++) {
            updateDeckButton(jFrame, i);
            updateDeckButtonText(i);
        }
    }

    private void updateDeckButton(JFrame jFrame, int index) {
        String uniqueId = SELECT_DECK_BUTTON_ID + index;
        formatSelectionButtons(jFrame, index, uniqueId);
    }

    private void updateDeckButtonText(int index) {
        String baseButtonId = SELECT_DECK_BUTTON_ID + index;
        String uniqueButtonTextId = SELECT_DECK_BUTTON_TEXT_ID + index;

        formatSelectionButtons(baseButtonId, uniqueButtonTextId);
    }


    /*******************************************************************************************************************
     * Format Selection Buttons
     */
    private void formatSelectionButtons(JFrame jFrame, int index, String id) {
        double buttonWidth = jFrame.getWidth() / 4;

        int startX = (int) ((jFrame.getWidth()/2) - (buttonWidth/2) + (((index%3)-1) * jFrame.getWidth() / 3));
        int endX = (int) (startX + buttonWidth);
        int startY = 200 + ((index/3) * (jFrame.getHeight() / 4));
        int endY = startY + (jFrame.getHeight() / 8);

        // Update
        int indexOfButton = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfButton).setBounds(startX, startY, endX, endY);
    }

    private void formatSelectionButtons(String baseButtonId, String textId) {
        int indexOfBaseButton = indexOfGuiShapeById(baseButtonId);
        GuiShape baseButton = shapesToDraw.get(indexOfBaseButton);

        int startX = baseButton.getPoint1X();
        int endX = baseButton.getPoint2X();
        int startY = baseButton.getPoint1Y();
        int endY = baseButton.getPoint2Y();

        // Update
        int indexOfButtonText = indexOfGuiShapeById(textId);
        shapesToDraw.get(indexOfButtonText).setBounds(startX, startY, endX, endY);
    }

    /*******************************************************************************************************************
     * Instantiate Flashcard Elements
     */
    private void instantiateFlashcardBase(String id) {
        GuiShape flashcardBase = new GuiRoundedRectangle(id, 0, 0, 0, 0, 25);
        flashcardBase.setFillColor(Color.WHITE);
        flashcardBase.noOutline();
        shapesToDraw.add(flashcardBase);
    }

    public void instantiateFlashcardHeader(String id) {
        GuiShape cardHeader = new GuiTextBox(id, "For 3 Points:", 0, 0, 0, 0);
        cardHeader.setFillColor(Color.BLACK);
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
        frontText.setFillColor(Color.BLACK);
        shapesToDraw.add(frontText);
    }

    public void instantiateStartButton() {
        GuiShape button = new GuiButton(SUBJECT_PAGE_BUTTON_ID, 0, 0, 0, 0, 25);
        button.setFillColor(Color.cyan);
        ((GuiButton)button).setHoverColor(Color.YELLOW);
        ((GuiButton)button).setPressedColor(Color.GREEN);
        shapesToDraw.add(button);
    }


    /*******************************************************************************************************************
     * Update Flashcard Elements
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

        int startX = (int) (flashcardBase.getPoint1X() + (marginWidth * 2));
        int startY = (int) (flashcardBase.getPoint1Y() + (marginWidth * 8));
        int endX = (int) (flashcardBase.getPoint2X() - (marginWidth * 2));
        int endY = (int) (flashcardBase.getPoint2Y() - (marginWidth * 1));

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
        // Inherits properties of The Text Lines for simplicity
        updateFlashcardTextLines(id, flashcardBase);
        int indexOfText = indexOfGuiShapeById(id);
        shapesToDraw.get(indexOfText).setText("The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog. The quick brown fox jumped over the lazy dog.");
    }

    public int updateStartButton(JFrame jFrame) {
        // Update
        int startX = (int) (0.2 * jFrame.getWidth());
        int endX = jFrame.getWidth() - startX;
        int startY = jFrame.getHeight() - 150;
        int endY = startY + 75;

        int indexOfObject = indexOfGuiShapeById(SUBJECT_PAGE_BUTTON_ID);
        shapesToDraw.get(indexOfObject).setBounds(startX, startY, endX, endY);

        return indexOfObject;
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

}
