package main.java;

import main.java.model.Deck;
import main.java.model.Flashcard;
import main.java.quizzes.MultipleChoiceQuiz;
import main.java.quizzes.ReviewQuiz;
import main.java.view.FlashcardMenu;

import java.util.List;

/**
 * TODO: Add Subjects to hold multiple decks
 * TODO: Set a quiz type for each deck - multiple choice, true/false, & free answer
 * TODO: Make sure the answers on multiple choice nad free answer quizzes cannot repeat
 * TODO: Add quiz type to each save
 * TODO: Revise saving to save everything on one line -- separate decks w/ a diamond or something
 * TODO: Make a 'stringToDeck' Method in save
 * TODO: Make Self-Reviews a separate option from Quizzes
 * TODO: Make it so multiple choice quizzes ask the same 5 questions and have a pool of wrong answers
 * TODO: Track total score over lifetime
 *
 * After learning about Website design:
 * TODO: Make a visual representation of progress
 */

public class FlashcardApplication {
    // Constants
    private final static String HOMEPAGE = "homepage";
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



    // Instance Variables
    private FlashcardMenu menu;
    private AllDecksManager allDecksManager;
    private String currentPage = HOMEPAGE;


    // Main Method
    public static void main(String[] args) {
        FlashcardMenu menu = new FlashcardMenu();
        AllDecksManager allDecksManager = new AllDecksManager();

        FlashcardApplication app = new FlashcardApplication(menu, allDecksManager);
        app.run();
    }


    /*******************************************************************************************************************
     * Constructor
     */
    public FlashcardApplication (FlashcardMenu menu, AllDecksManager allDecksManager) {
        this.menu = menu;
        this.allDecksManager = allDecksManager;
        this.currentPage = HOMEPAGE;
    }


    /*******************************************************************************************************************
     * Runtime Method
     */
    public void run () {
        menu.showWelcomeScreen();
        allDecksManager.loadData();

        boolean isRunning = true;
        while (isRunning) {
            if (currentPage.equals(HOMEPAGE)) {
                homepage();
            }
            else if (currentPage.equals(NEW_DECK_PAGE)) {
                makeANewDeckPage();
            }
            else if (currentPage.equals(DECK_PAGE)) {
                currentDeckPage();
            }
            else if (currentPage.equals(NEW_FLASHCARD_PAGE)) {
                newFlashcardPage();
            }
            else if (currentPage.equals(DELETE_DECK_PAGE)) {
                deleteDeckPage();
            }
            else if (currentPage.equals(RENAME_DECK_PAGE)) {
                renameCurrentDeckPage();
            }
            else if (currentPage.equals(ALL_FLASHCARDS_IN_DECK_PAGE)) {
                lookAtFlashcardsInDeckPage();
            }
            else if (currentPage.equals(FLASHCARD_PAGE)) {
                currentFlashcardPage();
            }
            else if (currentPage.equals(EDIT_FLASHCARD_PAGE)) {
                editFlashcardPage();
            }
            else if (currentPage.equals(QUIZZES_PAGE)) {
                quizzesPage();
            }
            else {
                // If the current page gets an invalid value, go back to the home page
                currentPage = HOMEPAGE;
            }
        }
    }


    /*******************************************************************************************************************
     * Methods for different pages
     */
    private void homepage() {
        menu.showHomepage(allDecksManager.getListOfAllDecks());
        String userInput = menu.requestUserInputAndFormat();

        // Go to selected deck if we can parse user input to an int, otherwise check for other input command
        try{
            int userInputToInt = Integer.parseInt(userInput);
            if (0 < userInputToInt && userInputToInt <= allDecksManager.getListOfAllDecks().size()) {
                allDecksManager.setCurrentDeck(allDecksManager.getListOfAllDecks().get( userInputToInt - 1 ));
                this.currentPage = DECK_PAGE;
            } else {
                menu.askUserToRetryInput("Sorry, the given number is out of bounds.");
            }

        } catch (NumberFormatException e) {
            if (userInput.equals("n")) {
                currentPage = NEW_DECK_PAGE;
            } else {
                menu.askUserToRetryInput("Sorry, the input could not be read. Please try again.");
            }
        }
    }

    private void makeANewDeckPage() {
        menu.showNewDeckPage();

        String newDeckName = menu.requestUserInput();
        Deck deckToAdd = new Deck(newDeckName);

        allDecksManager.setCurrentDeck(deckToAdd);
        allDecksManager.addDeckToListOfAllDecks( allDecksManager.getCurrentDeck() );
        allDecksManager.saveData();

        currentPage = DECK_PAGE;
    }

    private void currentDeckPage() {
        menu.showDeckPage(allDecksManager.getCurrentDeck());

        String userInput = menu.requestUserInputAndFormat();

        if (userInput.equals("h")) {
            // Go to homepage
            currentPage = HOMEPAGE;
            allDecksManager.setCurrentDeck(null);

        } else if (userInput.equals("n")) {
            // Create new flashcard
            currentPage = NEW_FLASHCARD_PAGE;

        } else if (userInput.equals("r")) {
            // Rename current deck
            currentPage = RENAME_DECK_PAGE;

        } else if (userInput.equals("d")) {
            //  Delete this deck
            currentPage = DELETE_DECK_PAGE;

        } else if (allDecksManager.getCurrentDeck().getDeckSize() > 0) {
            if (userInput.equals("q")) {
                // Take a quiz
                currentPage = QUIZZES_PAGE;

            } else if (userInput.equals("l")) {
                currentPage = ALL_FLASHCARDS_IN_DECK_PAGE;

            } else {
                menu.askUserToRetryInput("Sorry, the input could not be read. Please try again.");
            }
        } else {
            menu.askUserToRetryInput("Sorry, the input could not be read. Please try again.");
        }
    }

    private void newFlashcardPage() {
        this.menu.showNewFlashcardPage();

        menu.askUserToInputFlashcardFront();
        String flashcardFront = menu.requestUserInput();

        menu.askUserToInputFlashcardBack();
        String flashcardBack = menu.requestUserInput();

        Flashcard flashcardToAdd = new Flashcard(flashcardFront, flashcardBack);
        allDecksManager.addFlashCardToCurrentDeck(flashcardToAdd);
        currentPage = DECK_PAGE;

        allDecksManager.saveData();
    }

    private void editFlashcardPage() {
        menu.showEditFlashcardPage();

        editFlashCard(allDecksManager.getCurrentFlashcard());

        allDecksManager.setCurrentFlashcard(null);
        currentPage = ALL_FLASHCARDS_IN_DECK_PAGE;
    }

    private void editFlashCard(Flashcard flashcardToUpdate) {
        menu.askUserToUpdateFlashcardFront(flashcardToUpdate);
        String newFlashcardFront = menu.requestUserInput();
        if(!newFlashcardFront.trim().equals("")) {
            flashcardToUpdate.setFront(newFlashcardFront);
        }

        menu.askUserToUpdateFlashcardBack(flashcardToUpdate);
        String newFlashcardBack = menu.requestUserInput();
        if(!newFlashcardBack.trim().equals("")) {
            flashcardToUpdate.setBack(newFlashcardBack);
        }

        allDecksManager.saveData();
    }

    private void deleteDeckPage() {
        menu.showDeleteDeckPage( allDecksManager.getCurrentDeck() );
        boolean deleteDeck = menu.requestUserInputAndFormat().equals("y");
        if (deleteDeck) {
            allDecksManager.deleteCurrentDeck();
            allDecksManager.saveData();
            currentPage = HOMEPAGE;
        } else {
            currentPage = DECK_PAGE;
        }
    }

    private void renameCurrentDeckPage(){
        menu.showRenameDeckPage(allDecksManager.getCurrentDeck());
        String newDeckName = menu.requestUserInput();
        allDecksManager.getCurrentDeck().setDeckName( newDeckName );
        currentPage = DECK_PAGE;

        allDecksManager.saveData();
    }

    private void lookAtFlashcardsInDeckPage(){
        menu.showAllFlashcardsInDeckPage( allDecksManager.getCurrentDeck() );
        String userInput = menu.requestUserInputAndFormat();

        // Go to selected flashcard if we can parse user input to an int, otherwise check for other input command
        try{
            int userInputToInt = Integer.parseInt(userInput);
            if (0 < userInputToInt && userInputToInt <= allDecksManager.getCurrentDeck().getDeckSize()) {
                allDecksManager.setCurrentFlashcard(allDecksManager.getCurrentDeck().getFlashcardsInDeck().get( userInputToInt - 1 ));
                currentPage = FLASHCARD_PAGE;
            } else {
                menu.askUserToRetryInput("Sorry, the given number is out of bounds.");
            }

        } catch (NumberFormatException e) {
            if (userInput.equals("b")) {
                currentPage = DECK_PAGE;
            } else {
                menu.askUserToRetryInput("Sorry, the input could not be read. Please try again.");
            }
        }
    }

    private void currentFlashcardPage() {
        menu.showFlashcardPage( allDecksManager.getCurrentFlashcard() );

        String userInput = menu.requestUserInputAndFormat();
        if(userInput.equals("e")) {
            // Edit Flashcard
            currentPage = EDIT_FLASHCARD_PAGE;

        } else if (userInput.equals("d")) {
            // Delete Flashcard
            Flashcard currentFlashcard = allDecksManager.getCurrentFlashcard();
            allDecksManager.getCurrentDeck().removeFlashcard(currentFlashcard);
            allDecksManager.setCurrentFlashcard(null);
            allDecksManager.saveData();
            currentPage = ALL_FLASHCARDS_IN_DECK_PAGE;

        } else if (userInput.equals("b")) {
            // Go Back to SHow All Flashcards Page
            currentPage = ALL_FLASHCARDS_IN_DECK_PAGE;
            allDecksManager.setCurrentFlashcard(null);
        }
    }


    /*******************************************************************************************************************
     * Quizzes, quizzes, and more quizzes!
     */
    private void quizzesPage() {
        menu.showQuizzesPage();

        String userInput = menu.requestUserInputAndFormat();

        int quizScore = 0;
        List<Flashcard> allFlashcardsInCurrentDeck = allDecksManager.getCurrentDeck().getFlashcardsInDeck();

        // Review Quiz
        if (userInput.equals("b") || userInput.equals("d") || userInput.equals("f")) {
            ReviewQuiz reviewQuiz;
            if (userInput.equals("b")) {
                reviewQuiz = new ReviewQuiz(allFlashcardsInCurrentDeck, SHORT_QUIZ);
            } else if (userInput.equals("d")) {
                reviewQuiz = new ReviewQuiz(allFlashcardsInCurrentDeck, MID_SIZED_QUIZ);
            } else {
                reviewQuiz = new ReviewQuiz(allFlashcardsInCurrentDeck, allFlashcardsInCurrentDeck.size());
            }

            // Loop the quiz
            while (reviewQuiz.getNumberOfCardsInQuiz() > 0) {
                Flashcard currentQuizCard = reviewQuiz.pullCardOutOfQuizCardsList();
                menu.introToQuestion(currentQuizCard, quizScore);
                menu.showReviewQuestion();
                menu.requestUserInput();
                menu.showReviewAnswer(currentQuizCard, quizScore);
                userInput = menu.requestUserInputAndFormat();
                if (userInput.equals("r")) {
                    // Put the card back in the deck for review
                    reviewQuiz.putPulledCardBackIntoQuizCardsList();
                }
                else if (userInput.equals("e")) {
                    //Edit the flashcard and put it back in the deck
                    editFlashCard(allDecksManager.getCurrentDeck().getMatchingFlashcard(currentQuizCard));
                    reviewQuiz.putPulledCardBackIntoQuizCardsList();
                    quizScore += 1; // You get a point for editing a card
                    menu.cardRevisionMessage();
                }
                else {
                    // getting the right answer
                    quizScore += currentQuizCard.getPoints();
                    menu.correctMessage();
                }
            }
        }

        // Multiple Choice Quiz
        else if (userInput.equals("a") || userInput.equals("c") || userInput.equals("e")) {
            if (allFlashcardsInCurrentDeck.size() >= 4) {
                MultipleChoiceQuiz multipleChoiceQuiz;
                if (userInput.equals("a")) {
                    multipleChoiceQuiz = new MultipleChoiceQuiz(allFlashcardsInCurrentDeck, SHORT_QUIZ + 3);
                } else if (userInput.equals("c")) {
                    multipleChoiceQuiz = new MultipleChoiceQuiz(allFlashcardsInCurrentDeck, MID_SIZED_QUIZ + 3);
                } else {
                    multipleChoiceQuiz = new MultipleChoiceQuiz(allFlashcardsInCurrentDeck, allFlashcardsInCurrentDeck.size());
                }

                // Loop the quiz
                while (multipleChoiceQuiz.getNumberOfCardsInQuiz() >= 4) {
                    Flashcard currentQuizCard = multipleChoiceQuiz.pullCardOutOfQuizCardsList();
                    menu.introToQuestion(currentQuizCard, quizScore);
                    menu.showMultipleChoiceQuestion(multipleChoiceQuiz.getAnswerOptions());
                    String userAnswer = menu.requestUserInputAndFormat();
                    if (multipleChoiceQuiz.checkAnswer(userAnswer)) {
                        // Getting the right answer
                        quizScore += currentQuizCard.getPoints();
                        menu.correctMessage();
                    }
                    else if (userAnswer.equals("e")) {
                        //Edit the flashcard and put it back in the deck
                        editFlashCard(allDecksManager.getCurrentDeck().getMatchingFlashcard(currentQuizCard));
                        multipleChoiceQuiz.putPulledCardBackIntoQuizCardsList();
                        quizScore += 1; // You get a point for editing a card
                        menu.cardRevisionMessage();
                    }
                    else {
                        // Getting the wrong answer
                        multipleChoiceQuiz.putPulledCardBackIntoQuizCardsList();
                        menu.incorrectMessage(currentQuizCard);
                    }
                }
            } else {
                menu.askUserToRetryInput("Sorry, you need at least 4 cards in this deck to do a multiple choice quiz.");
            }
        }

        else {
            menu.askUserToRetryInput("Sorry, the input could not be read. Please try again.");
        }

        menu.endQuizMessage(quizScore);
        currentPage = DECK_PAGE;
    }

}
