package main.java.view.gui;


import main.java.CollectionManager;
import main.java.model.Deck;
import main.java.model.Flashcard;
import main.java.model.Subject;
import main.java.view.gui.shapes.GuiShape;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Remove shapesToDraw from Gui and have it get called from GuiElements
 * TODO: Rename GuiElelments to GuiElementsToDraw
 * TODO: Add private font variables to the text box and the text lines
 * TODO: Resize Text With Window
 * TODO: Change xScaleText to xScale for all objects
 * TODO: Add a back button
 * TODO: Add Flashcard selector Page
 * TODO: Add Subject Page
 */

public class FlashcardApplication {

    /*******************************************************************************************************************
     * Constants
     */
    private final static String HOMEPAGE = "homepage";
    private final static String SELECT_SUBJECT_PAGE = "select subject page";
    private final static String CURRENT_SUBJECT_PAGE = "current subject page";
    private final static String DELETE_SUBJECT_PAGE = "delete subject page";
    private final static String NEW_SUBJECT_PAGE = "new subject page";
    private final static String CURRENT_DECK_PAGE = "current deck page";
    private final static String NEW_DECK_PAGE = "new deck dage";
    private final static String DELETE_DECK_PAGE = "delete deck page";
    private final static String NEW_FLASHCARD_PAGE = "new card page";
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
    private CollectionManager collectionManager;
    private String currentPage = HOMEPAGE;



    /*******************************************************************************************************************
     * Constructor
     */
    public FlashcardApplication(Gui gui) {
        this.gui = gui;
        this.guiElements = new GuiElements();
        this.buttonManager = new ButtonManager();

        this.collectionManager = new CollectionManager();

        //TODO: Remove Later
        Subject s1 = new Subject("Subject 1");
        s1.addDeck(new Deck("Deck 1", 1));
        s1.addDeck(new Deck("Deck 2", 1));
        collectionManager.addSubject(s1);
        Subject s2 = new Subject("Subject B");
        collectionManager.addSubject(s2);
        Subject s3 = new Subject("Advanced Neuroscience Terms");
        s3.addDeck(new Deck("Deck 1", 1));
        s3.addDeck(new Deck("Deck 2", 1));
        s3.addDeck(new Deck("Deck 3", 1));
        s3.addDeck(new Deck("Deck 4", 1));
        s3.addDeck(new Deck("Deck 5", 1));
        s3.addDeck(new Deck("Deck 6", 1));
        s3.addDeck(new Deck("Deck 7", 1));
        collectionManager.addSubject(s3);
        Subject s4 = new Subject("Marine Biology");
        collectionManager.addSubject(s4);
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
        // TODO LATER: collectionManager.loadData();
        System.out.println("RUNTIME START EVENT >>> " + guiElements.getShapesToDraw().size());
    }

    public void windowSizeChangeEvent(JFrame jFrame) {
        List<GuiShape> shapesToDraw = updateWindow(jFrame);
        gui.setShapesToDraw(shapesToDraw);
        gui.repaint();
        System.out.println("WINDOW SIZE CHANGE EVENT >>> " + shapesToDraw.size());
    }

    public void mouseMoveEvent() {
        boolean isUpdate = buttonManager.updateButtonsWhenMouseGoesInOrOut(gui, guiElements);
        if (isUpdate) {
            gui.repaint();
        }
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
            buttonActions(result);

            // Makes sure all shapes are prepped to be redrawn
            List<GuiShape> shapesToDraw = updateWindow(jFrame);
            gui.setShapesToDraw(shapesToDraw);

            // Checks if mouse is over a newly-loaded button
            buttonManager.updateButtonsWhenMouseGoesInOrOut(gui, guiElements);

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
        else if (currentPage.equals(SELECT_SUBJECT_PAGE)) {
            updateSelectSubjectPage(jFrame, collectionManager.getSubjectList());
        }
        else if (currentPage.equals(FLASHCARD_PAGE)) {
            updateFlashcardPage(jFrame);
        }
        else if (currentPage.equals(CURRENT_SUBJECT_PAGE)) {
            updateCurrentSubjectPage(jFrame, collectionManager.getCurrentSubject().getListOfDecks());
        }
        else if (currentPage.equals(CURRENT_DECK_PAGE)) {
            updateCurrentDeckPage(jFrame);
        }

        //
        return guiElements.getShapesToDraw();
    }


    /*******************************************************************************************************************
     * Button Actions
     */
    private void buttonActions(String command) {
        if(command.equals(GuiElements.SUBJECT_PAGE_BUTTON_ID)) {
            instantiateSelectSubjectPage(collectionManager.getSubjectList());
        }
        else if(command.contains(GuiElements.SELECT_SUBJECT_BUTTON_ID)) {
            String stringIndexOfSubject = command.replace(GuiElements.SELECT_SUBJECT_BUTTON_ID, "");
            instantiateCurrentSubjectPage(stringIndexOfSubject);
        }
        else if(command.contains(GuiElements.SELECT_DECK_BUTTON_ID)) {
            String stringIndexOfDeck = command.replace(GuiElements.SELECT_DECK_BUTTON_ID, "");
            instantiateCurrentDeckPage(stringIndexOfDeck);
        }

        //instantiateFlashcardPage(jFrame);
    }



    /*******************************************************************************************************************
     * Homepage Methods
     */
    private void instantiateHomepage() {
        currentPage = HOMEPAGE;
        guiElements.instantiateStartButton();
    }

    private void updateHomepage(JFrame jFrame) {
        guiElements.updateStartButton(jFrame);
    }


    /*******************************************************************************************************************
     * Select Subject Page Methods
     */
    private void instantiateSelectSubjectPage(List<Subject> subjects) {
        currentPage = SELECT_SUBJECT_PAGE;
        clearAllGuiShapes();
        guiElements.instantiateSubjectPageHeader();
        guiElements.instantiateSubjectSelectionButtons(subjects);

        //
        List<GuiShape> shapesToDraw = guiElements.getShapesToDraw();
        gui.setShapesToDraw(shapesToDraw);
    }

    private void updateSelectSubjectPage(JFrame jFrame, List<Subject> subjects) {
        guiElements.updatePageHeader(jFrame);
        guiElements.updateSubjectSelectionButtons(jFrame, subjects);
    }


    /*******************************************************************************************************************
     * Current Subject Page Methods
     */
    private void instantiateCurrentSubjectPage(String stringIndexOfCurrentSubject) {
        try {
            int indexOfSubject = Integer.parseInt(stringIndexOfCurrentSubject);
            Subject currentSubject = collectionManager.getSubjectList().get(indexOfSubject);
            collectionManager.setCurrentSubject(currentSubject);

            currentPage = CURRENT_SUBJECT_PAGE;
            clearAllGuiShapes();

            //Add Elements to the page
            guiElements.instantiateCurrentSubjectPageHeader(currentSubject);
            guiElements.instantiateDeckSelectionButtons(currentSubject.getListOfDecks());
        } catch (NumberFormatException e) {
            System.err.println("Number format exception in FlashcardApplication --> instantiateCurrentSubjectPage method");
        }
    }

    private void updateCurrentSubjectPage(JFrame jFrame, List<Deck> decks) {
        guiElements.updatePageHeader(jFrame);
        guiElements.updateDeckSelectionButtons(jFrame, decks);
    }


    /*******************************************************************************************************************
     * Current Deck Page Methods
     */
    private void instantiateCurrentDeckPage(String stringIndexOfCurrentDeck) {
        try {
            int indexOfDeck = Integer.parseInt(stringIndexOfCurrentDeck);
            Deck currentDeck = collectionManager.getCurrentSubject().getListOfDecks().get(indexOfDeck);
            collectionManager.setCurrentDeck(currentDeck);

            currentPage = CURRENT_DECK_PAGE;
            clearAllGuiShapes();

            //Add Elements to the page
            guiElements.instantiateCurrentDeckPageHeader(currentDeck);
            //TODO: guiElements.instantiateFlashcardSelectionButtons(currentSubject.getListOfFlashcards());
        } catch (NumberFormatException e) {
            System.err.println("Number format exception in FlashcardApplication --> instantiateCurrentDeckPage method");
        }

    }

    private void updateCurrentDeckPage(JFrame jFrame) {
        guiElements.updatePageHeader(jFrame);
        // Todo
    }


    /*******************************************************************************************************************
     * Flashcard Page Pages
     */
    private void instantiateFlashcardPage() {
        clearAllGuiShapes();
        guiElements.instantiateFlashcard();

        List<GuiShape> shapesToDraw = guiElements.getShapesToDraw();
        gui.setShapesToDraw(shapesToDraw);
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
