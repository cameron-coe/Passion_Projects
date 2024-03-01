package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    // Instance Variables
    private String deckName = "";
    private List<Flashcard> listOfFlashcardsInDeck;


    // Constructor
    public Deck(String deckName) {
        this.deckName = deckName;
        this.listOfFlashcardsInDeck = new ArrayList<>();
    }


    // Getters
    public String getDeckName() {
        return this.deckName;
    }

    public int getDeckSize() {
        return this.listOfFlashcardsInDeck.size();
    }

    public List<Flashcard> getFlashcardsInDeck() {
        return this.listOfFlashcardsInDeck;
    }

    public Flashcard getMatchingFlashcard(Flashcard flashcardToFind) {
        for(Flashcard flashcard : this.listOfFlashcardsInDeck) {
            if(flashcard.getFront().equals(flashcardToFind.getFront())) {
                if(flashcard.getBack().equals(flashcardToFind.getBack())) {
                    return flashcard;
                }
            }
        }

        return null;
    }


    // Setters
    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }


    // Methods
    public void addFlashcard(Flashcard flashcard) {
        this.listOfFlashcardsInDeck.add(flashcard);

    }

    public void removeFlashcard(Flashcard flashcardToRemove) {
        this.listOfFlashcardsInDeck.remove(flashcardToRemove);
    }


}
