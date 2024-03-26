package main.java;

import main.java.model.Deck;
import main.java.model.Flashcard;
import main.java.model.GameDataDto;
import main.java.model.Subject;

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
    private final static String BREAK_BETWEEN_DECK_DATA = "⛶";
    private final static String BREAK_BETWEEN_DECKS = "⛝";
    private final static String BREAK_BETWEEN_SUBJECT_NAME_AND_DECKS = "⬒";
    private final static String BREAK_BETWEEN_SUBJECTS = "⟏";
    private final static String BREAK_BETWEEN_GAME_DATA = "⚿";

    private final static int DEFAULT_QUIZ_TYPE = 1; // Multiple Choice


    // Constructor
    public SaveAndLoad () {

    }


    // Methods
    public GameDataDto loadSaveData() {
        GameDataDto gameData = new GameDataDto();

        try (Scanner dataInput = new Scanner(SAVE_FILE)) {
            while(dataInput.hasNextLine()) {
                String currentLine = dataInput.nextLine();
                gameData = textToGameDataObject(currentLine); // Turns text into the data
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found when loading save file");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Array out of bounds when reading save file");
        }

        return gameData;
    }


    public void overwriteSaveData(GameDataDto gameData) {
        try (PrintWriter dataOutput = new PrintWriter(SAVE_FILE)){
            dataOutput.print(""); // Clears the text file
            String saveData = gameDataObjectToText(gameData);
            dataOutput.println(saveData); // Writing the save Data
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /*******************************************************************************************************************
     * Turn Text Into Objects
     */
    private GameDataDto textToGameDataObject (String text) {
        String[] dividedTextData = text.split(BREAK_BETWEEN_GAME_DATA);

        int totalScore = 0;
        try {
            totalScore = Integer.parseInt(dividedTextData[0]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }

        List<Subject> subjectList = textToSubjectList(dividedTextData[1]);

        GameDataDto gameData = new GameDataDto();
        gameData.setTotalScore(totalScore);
        gameData.setSubjectList(subjectList);
        return gameData;
    }

    private List<Subject> textToSubjectList(String text) {
        List<Subject> subjectList = new ArrayList<>();
        String[] dividedTextData = text.split(BREAK_BETWEEN_SUBJECTS);

        for (String textData : dividedTextData) {
            Subject newSubject = textToSubject(textData);
            subjectList.add(newSubject);
        }

        return subjectList;
    }

    private Subject textToSubject(String text) {
        String[] dividedTextData = text.split(BREAK_BETWEEN_SUBJECT_NAME_AND_DECKS);

        String subjectName = dividedTextData[0];
        Subject newSubject = new Subject(subjectName);

        List<Deck> deckList = textToDeckList(dividedTextData[1]);
        newSubject.setListOfDecks(deckList);

        return newSubject;
    }

    private List<Deck> textToDeckList(String text) {
        List<Deck> deckList = new ArrayList<>();
        String[] dividedTextData = text.split(BREAK_BETWEEN_DECKS);

        for(String textData : dividedTextData){
            Deck deck = textToDeck(textData);
            deckList.add(deck);
        }

        return deckList;
    }

    private Deck textToDeck(String text) {
        String[] deckComponents = text.split(BREAK_BETWEEN_DECK_DATA);
        String deckName = deckComponents[0];
        int quizType = DEFAULT_QUIZ_TYPE;
        try {
            quizType = Integer.parseInt(deckComponents[1]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }

        List<Flashcard> flashcardList = textToFlashcardList(deckComponents[2]);

        Deck newDeck = new Deck(deckName, quizType);
        newDeck.setListOfFlashcardsInDeck(flashcardList);
        return newDeck;
    }

    private List<Flashcard> textToFlashcardList(String text) {
        List<Flashcard> flashcardList = new ArrayList<>();
        String[] dividedTextData = text.split(BREAK_BETWEEN_FLASHCARDS);

        for(String textData : dividedTextData){
            Flashcard flashcard = textToFlashcard(textData);
            flashcardList.add(flashcard);
        }

        return flashcardList;
    }

    private Flashcard textToFlashcard(String text){
        String[] frontAndBack = text.split(BREAK_BETWEEN_CARD_FRONT_AND_BACK);
        return new Flashcard(frontAndBack[0], frontAndBack[1]);
    }


    /*******************************************************************************************************************
     * Turn Objects Into Text
     */
    private String gameDataObjectToText (GameDataDto gameData) {
        String output = "";
        output += gameData.getTotalScore();
        output += BREAK_BETWEEN_GAME_DATA;
        output += subjectListToText(gameData.getSubjectList());

        return output;
    }

    private String subjectListToText(List<Subject> subjectList) {
        String output = "";
        for (Subject subject : subjectList) {
            output += subjectToText(subject);
            output += BREAK_BETWEEN_SUBJECTS;
        }
        return output;
    }

    private String subjectToText(Subject subject) {
        String output = "";
        output += subject.getName();
        output += BREAK_BETWEEN_SUBJECT_NAME_AND_DECKS;
        output += deckListToText( subject.getListOfDecks() );

        return output;
    }

    private String deckListToText (List<Deck> deckList) {
        String output = "";
        for (Deck deck : deckList) {
            output += deckToText(deck);
            output += BREAK_BETWEEN_DECKS;
        }
        return output;
    }

    private String deckToText(Deck deck) {
        String output = "";
        output += deck.getDeckName();
        output += BREAK_BETWEEN_DECK_DATA;
        output += deck.getQuizType();
        output += BREAK_BETWEEN_DECK_DATA;
        output += flashcardListToText(deck.getFlashcardsInDeck());

        return output;
    }

    private String flashcardListToText(List<Flashcard> flashcardList) {
        String output = "";
        for (Flashcard flashcard : flashcardList) {
            output += flashcardToText(flashcard);
            output += BREAK_BETWEEN_FLASHCARDS;
        }
        return output;
    }

    private String flashcardToText(Flashcard flashcard){
        String output = flashcard.getFront();
        output += BREAK_BETWEEN_CARD_FRONT_AND_BACK;
        output += flashcard.getBack();
        return output;
    }
}
