package main.java;

import main.java.model.Deck;
import main.java.model.Flashcard;

import java.util.ArrayList;
import java.util.List;

public class AllDecksManager {

    // Instance variables
    private List<Deck> listOfAllDecks;
    private Deck currentDeck;
    private Flashcard currentFlashcard;


    // Constructor
    public AllDecksManager() {
        listOfAllDecks = new ArrayList<>();
    }


    // Getters
    public List<Deck> getListOfAllDecks() {
        return this.listOfAllDecks;
    }

    public Deck getCurrentDeck() {
        return this.currentDeck;
    }

    public Flashcard getCurrentFlashcard() {
        return this.currentFlashcard;
    }


    // Setters
    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }


    public void setCurrentFlashcard(Flashcard currentFlashcard) {
        this.currentFlashcard = currentFlashcard;
    }


    // Methods
    public void addDeckToListOfAllDecks(Deck deckToAdd) {
        this.listOfAllDecks.add(deckToAdd);
    }

    public void addFlashCardToCurrentDeck(Flashcard flashcardToAdd) {
        this.currentDeck.addFlashcard(flashcardToAdd);
    }

    public void deleteCurrentDeck() {
        this.listOfAllDecks.remove( this.currentDeck );
    }

    public void saveData() {
        SaveAndLoad saveAndLoadObject = new SaveAndLoad();
        saveAndLoadObject.overwriteSaveData(this.listOfAllDecks);
    }

    public void loadData() {
        SaveAndLoad saveAndLoadObject = new SaveAndLoad();
        this.listOfAllDecks = saveAndLoadObject.loadSaveData();
    }

}
