package main.java;

import main.java.model.Deck;
import main.java.model.Flashcard;
import main.java.model.GameDataDto;
import main.java.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class CollectionManager {

    // Instance variables
    private SaveAndLoad saveAndLoadObject;
    private List<Subject> subjectList;
    private int totalScore;
    private Subject currentSubject;
    private Deck currentDeck;
    private Flashcard currentFlashcard;


    // Constructor
    public CollectionManager() {
        this.subjectList = new ArrayList<>();
        this.saveAndLoadObject = new SaveAndLoad();

        //this.saveAndLoadObject.loadSaveData();
        loadData();
    }


    // Getters
    public Subject getCurrentSubject() { return this.currentSubject; }

    public Deck getCurrentDeck() {
        return this.currentDeck;
    }

    public Flashcard getCurrentFlashcard() {
        return this.currentFlashcard;
    }

    public List<Subject> getSubjectList() {
        return this.subjectList;
    }
    public int getTotalScore() {
        return this.totalScore;
    }


    // Setters
    public void setCurrentSubject(Subject currentSubject) {
        this.currentSubject = currentSubject;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    public void setCurrentFlashcard(Flashcard currentFlashcard) {
        this.currentFlashcard = currentFlashcard;
    }


    // Methods
    public void addSubject(Subject subjectToAdd) {
        this.subjectList.add(subjectToAdd);
        saveData();
    }

    public void addFlashCardToCurrentDeck(Flashcard flashcardToAdd) {
        this.currentDeck.addFlashcard(flashcardToAdd);
        saveData();
    }

    public void deleteCurrentDeck() {
        this.currentSubject.removeDeck(this.currentDeck);
        saveData();
    }
    public void deleteCurrentSubject() {
        subjectList.remove(this.currentSubject);
        saveData();
    }


    // Save and Load Data
    public void saveData() {
        GameDataDto gameData = new GameDataDto();
        gameData.setTotalScore(this.totalScore);
        gameData.setSubjectList(this.subjectList);
        saveAndLoadObject.overwriteSaveData(gameData);
    }

    public void loadData() {
        GameDataDto gameData = saveAndLoadObject.loadSaveData();
        this.totalScore = gameData.getTotalScore();
        this.subjectList = gameData.getSubjectList();
    }

}
