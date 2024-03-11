package main.java;

import main.java.model.Deck;
import main.java.model.Flashcard;
import main.java.model.quizzes.MultipleChoiceQuiz;
import main.java.model.quizzes.ReviewQuiz;
import main.java.view.FlashcardMenu;

import java.util.List;

/**
 * TODO: Make sure the answers on multiple choice and free answer quizzes cannot repeat when you make new cards
 * TODO: Make Self-Reviews a separate option from Quizzes
 * TODO: Make it so multiple choice quizzes only have 5 flashcards by including a pool of wrong answers
 * TODO: Track total score over lifetime
 * TODO: Shouldn't have to save in Application anymore, the Collection Manager will do that for all methods that change the data
 * TODO: Move edit methods over to collection Manager
 *
 * After learning about Website design:
 * TODO: Make a visual representation of progress -- Dragons
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
    private CollectionManager collectionManager;
    private String currentPage = HOMEPAGE;


    // Main Method
    public static void main(String[] args) {
        FlashcardMenu menu = new FlashcardMenu();
        CollectionManager collectionManager = new CollectionManager();

        FlashcardApplication app = new FlashcardApplication(menu, collectionManager);
        app.run();
    }


    /*******************************************************************************************************************
     * Constructor
     */
    public FlashcardApplication (FlashcardMenu menu, CollectionManager collectionManager) {
        this.menu = menu;
        this.collectionManager = collectionManager;
        this.currentPage = HOMEPAGE;
    }


    /*******************************************************************************************************************
     * Runtime Method
     */
    public void run () {
        menu.showWelcomeScreen();
        collectionManager.loadData();

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
        menu.showHomepage(collectionManager.getListOfAllDecks());
        String userInput = menu.requestUserInputAndFormat();

        // Go to selected deck if we can parse user input to an int, otherwise check for other input command
        try{
            int userInputToInt = Integer.parseInt(userInput);
            if (0 < userInputToInt && userInputToInt <= collectionManager.getListOfAllDecks().size()) {
                collectionManager.setCurrentDeck(collectionManager.getListOfAllDecks().get( userInputToInt - 1 ));
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
        Deck deckToAdd = new Deck(newDeckName, 1);

        collectionManager.setCurrentDeck(deckToAdd);
        collectionManager.addDeckToListOfAllDecks( collectionManager.getCurrentDeck() );
        collectionManager.saveData();

        currentPage = DECK_PAGE;
    }

    private void currentDeckPage() {
        menu.showDeckPage(collectionManager.getCurrentDeck());

        String userInput = menu.requestUserInputAndFormat();

        if (userInput.equals("h")) {
            // Go to homepage
            currentPage = HOMEPAGE;
            collectionManager.setCurrentDeck(null);

        } else if (userInput.equals("n")) {
            // Create new flashcard
            currentPage = NEW_FLASHCARD_PAGE;

        } else if (userInput.equals("r")) {
            // Rename current deck
            currentPage = RENAME_DECK_PAGE;

        } else if (userInput.equals("d")) {
            //  Delete this deck
            currentPage = DELETE_DECK_PAGE;

        } else if (collectionManager.getCurrentDeck().getDeckSize() > 0) {
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
        collectionManager.addFlashCardToCurrentDeck(flashcardToAdd);
        currentPage = DECK_PAGE;

        collectionManager.saveData();
    }

    private void editFlashcardPage() {
        menu.showEditFlashcardPage();

        editFlashCard(collectionManager.getCurrentFlashcard());

        collectionManager.setCurrentFlashcard(null);
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

        collectionManager.saveData();
    }

    private void deleteDeckPage() {
        menu.showDeleteDeckPage( collectionManager.getCurrentDeck() );
        boolean deleteDeck = menu.requestUserInputAndFormat().equals("y");
        if (deleteDeck) {
            collectionManager.deleteCurrentDeck();
            collectionManager.saveData();
            currentPage = HOMEPAGE;
        } else {
            currentPage = DECK_PAGE;
        }
    }

    private void renameCurrentDeckPage(){
        menu.showRenameDeckPage(collectionManager.getCurrentDeck());
        String newDeckName = menu.requestUserInput();
        collectionManager.getCurrentDeck().setDeckName( newDeckName );
        currentPage = DECK_PAGE;

        collectionManager.saveData();
    }

    private void lookAtFlashcardsInDeckPage(){
        menu.showAllFlashcardsInDeckPage( collectionManager.getCurrentDeck() );
        String userInput = menu.requestUserInputAndFormat();

        // Go to selected flashcard if we can parse user input to an int, otherwise check for other input command
        try{
            int userInputToInt = Integer.parseInt(userInput);
            if (0 < userInputToInt && userInputToInt <= collectionManager.getCurrentDeck().getDeckSize()) {
                collectionManager.setCurrentFlashcard(collectionManager.getCurrentDeck().getFlashcardsInDeck().get( userInputToInt - 1 ));
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
        menu.showFlashcardPage( collectionManager.getCurrentFlashcard() );

        String userInput = menu.requestUserInputAndFormat();
        if(userInput.equals("e")) {
            // Edit Flashcard
            currentPage = EDIT_FLASHCARD_PAGE;

        } else if (userInput.equals("d")) {
            // Delete Flashcard
            Flashcard currentFlashcard = collectionManager.getCurrentFlashcard();
            collectionManager.getCurrentDeck().removeFlashcard(currentFlashcard);
            collectionManager.setCurrentFlashcard(null);
            collectionManager.saveData();
            currentPage = ALL_FLASHCARDS_IN_DECK_PAGE;

        } else if (userInput.equals("b")) {
            // Go Back to SHow All Flashcards Page
            currentPage = ALL_FLASHCARDS_IN_DECK_PAGE;
            collectionManager.setCurrentFlashcard(null);
        }
    }


    /*******************************************************************************************************************
     * Quizzes, quizzes, and more quizzes!
     */
    private void quizzesPage() {
        menu.showQuizzesPage();

        String userInput = menu.requestUserInputAndFormat();

        int quizScore = 0;
        List<Flashcard> allFlashcardsInCurrentDeck = collectionManager.getCurrentDeck().getFlashcardsInDeck();

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
                    editFlashCard(collectionManager.getCurrentDeck().getMatchingFlashcard(currentQuizCard));
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
                        editFlashCard(collectionManager.getCurrentDeck().getMatchingFlashcard(currentQuizCard));
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
