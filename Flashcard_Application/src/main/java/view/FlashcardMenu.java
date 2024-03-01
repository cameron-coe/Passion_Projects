package main.java.view;

import main.java.model.Deck;
import main.java.model.Flashcard;

import java.util.List;
import java.util.Scanner;

public class FlashcardMenu {

    // Constants
    private final static char BLOCK = '■';
    private final static Scanner userInput = new Scanner(System.in);



    public void drawHeading(String message) {
        int messageLength = message.length();
        String blockLine = "";

        for (int i = 0; i < messageLength; i++) {
            blockLine += BLOCK;
        }

        System.out.println(blockLine);
        System.out.println(message);
        System.out.println(blockLine);
    }


    // User Input Methods
    public String requestUserInput() {
        System.out.print(">>> ");

        String input = userInput.nextLine();  // Read the user input
        System.out.println();

        return input;
    }

    public String requestUserInputAndFormat() {
        return requestUserInput().trim().toLowerCase();
    }

    public void askUserToRetryInput(String message) {
        System.out.println(message);
    }


    public void showWelcomeScreen() {
        drawHeading("Welcome to the Flashcard App!");
    }


    public void showHomepage(List<Deck> listOfAllDecksFromAllDecksManager) {
        drawHeading("Homepage");

        if (listOfAllDecksFromAllDecksManager.size() == 0) {
            System.out.println("You don't have any decks. Type \"N\" to create a new deck.");
        } else {
            System.out.println("Type an integer to select a deck, or \"N\" to create a new deck.");

            for (int i = 0; i < listOfAllDecksFromAllDecksManager.size(); i++) {
                System.out.println("" + (i + 1) + ".) -- " + listOfAllDecksFromAllDecksManager.get(i).getDeckName());
            }
        }
    }


    public void showNewDeckPage() {
        drawHeading("Make a New Deck!");
        System.out.println("What will you name this deck?");
    }


    public void showDeckPage(Deck currentDeck) {
        if (currentDeck.getDeckSize() == 1) {
            drawHeading(currentDeck.getDeckName() + ": " + currentDeck.getDeckSize() + " Card");
        } else {
            drawHeading(currentDeck.getDeckName() + ": " + currentDeck.getDeckSize() + " Cards");
        }

        if (currentDeck.getDeckSize() == 0) {
            System.out.println("You don't have any flashcards in this deck yet.");
            System.out.println("Type a command from the options below:");
            System.out.println("\"N\" -- Create a New Flashcard");
            System.out.println("\"R\" -- Rename this Deck");
            System.out.println("\"H\" -- Go to Homepage");
            System.out.println("\"D\" -- Delete this Deck");
        } else {
            System.out.println("Type a command from the options below:");
            System.out.println("\"Q\" -- Take a Quiz");
            System.out.println("\"N\" -- Create a New Flashcard");
            System.out.println("\"L\" -- Look through Flashcards");
            System.out.println("\"H\" -- Go to Homepage");
            System.out.println("\"R\" -- Rename this Deck");
            System.out.println("\"D\" -- Delete this Deck");
        }
    }


    public void showNewFlashcardPage() {
        drawHeading("New Flashcard!");
    }

    public void askUserToInputFlashcardFront() {
        System.out.println("Write the definition / question on the front of the card:");
    }

    public void askUserToInputFlashcardBack() {
        System.out.println("Write the keyword / answer on the back of the card:");
    }

    public void askUserToUpdateFlashcardFront(Flashcard flashcard) {
        System.out.println("Old Flashcard Front: " + flashcard.getFront());
        System.out.println("Write the new definition / question on the front of the card:");
    }

    public void askUserToUpdateFlashcardBack(Flashcard flashcard) {
        System.out.println("Old Flashcard Back: " + flashcard.getBack());
        System.out.println("Write the new keyword / answer on the back of the card:");
    }


    public void showDeleteDeckPage(Deck deckToDelete) {
        drawHeading("Are you sure you want to delete \"" + deckToDelete.getDeckName() + "\" and all flashcards inside it?");
        System.out.println("Type Y or N");
    }


    public void showRenameDeckPage(Deck deckToRename) {
        drawHeading("Rename \"" + deckToRename.getDeckName() + "\" to what?");
    }


    public void showAllFlashcardsInDeckPage(Deck deckToLookThrough) {
        drawHeading("All Flashcards In \"" + deckToLookThrough.getDeckName() + "\"");

        List<Flashcard> listOfFlashcardsInDeck = deckToLookThrough.getFlashcardsInDeck();
        for (int i = 0; i < listOfFlashcardsInDeck.size(); i++) {
            String lineToPrint = "" + (i + 1) + ".) -- ";
            lineToPrint += listOfFlashcardsInDeck.get(i).getFront();
            lineToPrint += " || ";
            lineToPrint += listOfFlashcardsInDeck.get(i).getBack();

            System.out.println(lineToPrint);
        }

        System.out.println();
        System.out.println("Type an integer to select a flashcard, or \"B\" to go back.");
    }


    public void showFlashcardPage(Flashcard currentFlashcard) {
        drawHeading("Selected Flashcard:");
        System.out.println("Front: " + currentFlashcard.getFront());
        System.out.println("Back: " + currentFlashcard.getBack());
        System.out.println("Press \"E\" to edit, \"D\" to delete, or \"B\" to go back.");
    }


    public void showEditFlashcardPage() {
        drawHeading("Editing Selected Flashcard:");
    }


    public void showQuizzesPage() {
        drawHeading("Let's Take a Quiz!");
        System.out.println("Select which quiz you'd like to take:");

        System.out.println("A.) -- Lightning Multiple Choice (Up to 5 Questions)");
        System.out.println("B.) -- Lightning Review (Up to 5 Questions)");
        System.out.println("C.) -- Mid-Sized Multiple Choice (Up to 15 Questions)");
        System.out.println("D.) -- Mid-Sized Review (Up to 15 Questions)");
        System.out.println("E.) -- Full Deck Multiple Choice");
        System.out.println("F.) -- Full Deck Review");
    }

    public void introToQuestion (Flashcard currentQuizCard, int totalScore) {
        System.out.println("Your Score: " + totalScore);
        if (currentQuizCard.getPoints() == 1) {
            System.out.println("This Card is worth 1 point.");
        } else {
            System.out.println("This Card is worth " + currentQuizCard.getPoints() + " points.");
        }
        System.out.println("■ Q: " + currentQuizCard.getFront());
    }

    public void showReviewQuestion() {
        System.out.println("Press ENTER to see the answer.");
    }

    public void showReviewAnswer(Flashcard currentQuizCard, int totalScore) {
        System.out.println("■ A: " + currentQuizCard.getBack());
        System.out.println("Press ENTER if you got it correct, \"R\" to review this card (this will make it worth more points later), or \"E\" to edit this card.");
    }

    public void showMultipleChoiceQuestion(String[] answerOptions) {
        System.out.println("    A.) " + answerOptions[0]);
        System.out.println("    B.) " + answerOptions[1]);
        System.out.println("    C.) " + answerOptions[2]);
        System.out.println("    D.) " + answerOptions[3]);
        System.out.println("Choose your answer and press enter. Or type \"E\" to edit this card.");
    }

    public void correctMessage() {
        System.out.println(" ☆☆ CORRECT! ☆☆");
    }

    public void cardRevisionMessage() {
        System.out.println(" ☆ You Earned 1 Point For Improving That Flashcard! ☆");
    }

    public void incorrectMessage(Flashcard flashcard) {
        System.out.println("■ Close, the correct answer was: " + flashcard.getBack());
    }

    public void endQuizMessage (int totalScore) {
        if (totalScore == 1) {
            System.out.println("Awesome! You ended with 1 point!");
        } else {
            System.out.println("Awesome! You ended with " + totalScore + " points!");
        }
    }

}

