package main.java;

import main.java.model.Deck;
import main.java.model.Flashcard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SaveAndLoad {

    // Constants
    private final static String FILE_PATH = "SavedDeckData.txt";
    private final static File SAVE_FILE = new File(FILE_PATH);
    private final static String BREAK_BETWEEN_FLASHCARDS = "◧";
    private final static String BREAK_BETWEEN_CARD_FRONT_AND_BACK = "◩";
    private final static String BREAK_BETWEEN_DECK_NAME_AND_FLASHCARDS = "▩";


    // TODO: Implement Later:
    private final static String BREAK_BETWEEN_DECKS = "";



    // Constructor
    public SaveAndLoad () {

    }


    // Methods
    public List<Deck> loadSaveData() {
        List listOfDecksLoaded = new ArrayList<>();

        try (Scanner dataInput = new Scanner(SAVE_FILE)) {
            while(dataInput.hasNextLine()) {
                String currentLine = dataInput.nextLine();

                if (currentLine.length() > 0) {  // Don't load empty lines
                    if (currentLine.charAt(0) != BREAK_BETWEEN_DECK_NAME_AND_FLASHCARDS.charAt(0)) {  // Don't load data without a deck name
                        String[] splitToGetDeckName = currentLine.split(BREAK_BETWEEN_DECK_NAME_AND_FLASHCARDS);
                        Deck currentDeck = new Deck(splitToGetDeckName[0]);

                        if (splitToGetDeckName.length > 1) {
                            String[] splitToGetFlashcards = splitToGetDeckName[1].split(BREAK_BETWEEN_FLASHCARDS);
                            for (String flashcard : splitToGetFlashcards) {
                                String[] cardFrontAndBack = flashcard.split(BREAK_BETWEEN_CARD_FRONT_AND_BACK);
                                Flashcard currentFlashcard = new Flashcard(cardFrontAndBack[0], cardFrontAndBack[1]);
                                currentDeck.addFlashcard(currentFlashcard);
                            }
                        }
                        listOfDecksLoaded.add(currentDeck);
                    }
                }

            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array out of bounds.");
        }

        return listOfDecksLoaded;

    }


    public void overwriteSaveData(List<Deck> listOfDecksToSave) {
        try (PrintWriter dataOutput = new PrintWriter(SAVE_FILE)){
            dataOutput.print(""); // Clears the text file

            for (Deck currentDeck : listOfDecksToSave) {
                if (!currentDeck.getDeckName().equals("")) {  // Don't save decks without a name
                    String deckDataAsAString = "";
                    deckDataAsAString += currentDeck.getDeckName();
                    deckDataAsAString += BREAK_BETWEEN_DECK_NAME_AND_FLASHCARDS;

                    for (Flashcard currentFlashcard : currentDeck.getFlashcardsInDeck()) {
                        deckDataAsAString += currentFlashcard.getFront();
                        deckDataAsAString += BREAK_BETWEEN_CARD_FRONT_AND_BACK;
                        deckDataAsAString += currentFlashcard.getBack();
                        deckDataAsAString += BREAK_BETWEEN_FLASHCARDS;
                    }

                    dataOutput.println(deckDataAsAString);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
