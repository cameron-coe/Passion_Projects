package main.java.view.gui;


import main.java.view.gui.shapes.GuiShape;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Add font private variables to the text box and the text lines
 * TODO: Resize Text With Window
 * TODO: Add Select Subject Buttons
 * TODO: Change xScaleText to xScale for all objects
 * TODO: Add Subject Selector Page
 * TODO: Add Deck Selector Page
 */

public class FlashcardApplication {

    /*******************************************************************************************************************
     * Constants
     */
    private final static String HOMEPAGE = "homepage";
    private final static String SUBJECT_PAGE = "subject page";
    private final static String NEW_SUBJECT_PAGE = "new subject page";
    private final static String DECK_PAGE = "deck page";
    private final static String NEW_DECK_PAGE = "new deck dage";
    private final static String NEW_FLASHCARD_PAGE = "new card page";
    private final static String DELETE_DECK_PAGE = "delete deck page";
    private final static String ALL_FLASHCARDS_IN_DECK_PAGE = "look through all flashcards in deck page";
    private final static String FLASHCARD_PAGE = "flashcard page";
    private final static String RENAME_DECK_PAGE = "rename deck page";
    private final static String EDIT_FLASHCARD_PAGE = "edit flashcard page";
    private final static String QUIZZES_PAGE = "quizzes page";

    private final static int  SHORT_QUIZ = 5;
    private final static int  MID_SIZED_QUIZ = 15;


    /*******************************************************************************************************************
     * Instance Variables
     */
    private Gui gui;
    private GuiElements guiElements;
    private ButtonManager buttonManager;
    private String currentPage = HOMEPAGE;



    /*******************************************************************************************************************
     * Constructor
     */
    public FlashcardApplication(Gui gui) {
        this.gui = gui;
        this.guiElements = new GuiElements();
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
        instantiateHomepage();
        gui.repaint();
        System.out.println("RUNTIME START EVENT >>> " + guiElements.getShapesToDraw().size());
    }

    public void windowSizeChangeEvent(JFrame jFrame) {
        List<GuiShape> shapesToDraw = updateWindow(jFrame);
        gui.setShapesToDraw(shapesToDraw);
        gui.repaint();
        System.out.println("WINDOW SIZE CHANGE EVENT >>> " + shapesToDraw.size());
    }

    public void mouseMoveEvent(JFrame jFrame) {
        boolean isUpdate = buttonManager.updateButtonsWhenMouseGoesInOrOut(gui, guiElements);
        if (isUpdate) {
            gui.repaint();
        }

        List<GuiShape> shapesToDraw = updateWindow(jFrame);
        System.out.println("MOUSE MOVE EVENT >>> " + shapesToDraw.size());
    }

    public void mousePressedEvent(JFrame jFrame) {
        boolean isAButtonPressed = buttonManager.mouseDownOnButton(guiElements);
        if (isAButtonPressed) {
            gui.repaint();
        }

        List<GuiShape> shapesToDraw = updateWindow(jFrame);
        System.out.println("MOUSE PRESSED EVENT >>> " + shapesToDraw.size());
    }

    public void mouseReleasedEvent(JFrame jFrame) {
        String result = buttonManager.mouseReleasedOnButton(gui);

        if (result != null) {
            buttonActions(result, jFrame);

            // Makes sure all shapes are prepped to be redrawn
            List<GuiShape> shapesToDraw = updateWindow(jFrame);
            gui.setShapesToDraw(shapesToDraw);

            gui.repaint();
        }

        System.out.println("MOUSE RELEASED EVENT >>> " + result);
    }


    /*******************************************************************************************************************
     * Update Method
     */
    public List<GuiShape> updateWindow(JFrame jFrame) {
        if(currentPage.equals(HOMEPAGE)) {
            updateHomepage(jFrame);
        }
        else if (currentPage.equals(SUBJECT_PAGE)) {
            updateSubjectPage(jFrame);
        }
        else if (currentPage.equals(FLASHCARD_PAGE)) {
            updateFlashcardPage(jFrame);
        }

        //
        return guiElements.getShapesToDraw();
    }


    /*******************************************************************************************************************
     * Button Actions
     */
    private void buttonActions(String command, JFrame jFrame) {
        if(command.equals(GuiElements.SUBJECT_PAGE_BUTTON_ID)) {
            instantiateSubjectPage();
        }

        //instantiateFlashcardPage(jFrame);
    }



    /*******************************************************************************************************************
     * Instantiate Pages
     */
    private void instantiateHomepage() {
        currentPage = HOMEPAGE;
        guiElements.instantiateStartButton();
    }

    private void instantiateSubjectPage() {
        currentPage = SUBJECT_PAGE;
        clearAllGuiShapes();
        guiElements.instantiateSubjectPageHeader();

        //
        List<GuiShape> shapesToDraw = guiElements.getShapesToDraw();
        gui.setShapesToDraw(shapesToDraw);
    }

    private void instantiateFlashcardPage() {
        clearAllGuiShapes();
        guiElements.instantiateFlashcard();

        List<GuiShape> shapesToDraw = guiElements.getShapesToDraw();
        gui.setShapesToDraw(shapesToDraw);
    }


    /*******************************************************************************************************************
     * Update Pages
     */
    private void updateHomepage(JFrame jFrame) {
        guiElements.updateStartButton(jFrame);
    }

    private void updateSubjectPage(JFrame jFrame) {
        guiElements.updatePageHeader(jFrame);
    }

    private void updateFlashcardPage(JFrame jFrame) {
        guiElements.updateFlashcard(jFrame);
    }


    /*******************************************************************************************************************
     * Clears the GUI's shapes to draw
     */
    private void clearAllGuiShapes() {
        guiElements.setShapesToDraw(new ArrayList<>());
    }






}
