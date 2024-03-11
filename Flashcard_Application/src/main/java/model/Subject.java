package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    // Instance Variables
    private String name;
    private List<Deck> listOfDecks;


    // Constructor
    public Subject(String name) {
        this.name = name;
        listOfDecks = new ArrayList<>();
    }


    // Getter
    public String getName() {
        return this.name;
    }

    public List<Deck> getListOfDecks() {
        return this.listOfDecks;
    }

    public int getNumberOfDecksInSubject() {
        return this.listOfDecks.size();
    }


    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setListOfDecks(List<Deck> listOfDecks) {
        this.listOfDecks = listOfDecks;
    }

    // Methods
    public void addDeck(Deck deck) {
        this.listOfDecks.add(deck);
    }

    public void removeDeck(Deck deckToRemove) {
        this.listOfDecks.remove(deckToRemove);
    }
}
