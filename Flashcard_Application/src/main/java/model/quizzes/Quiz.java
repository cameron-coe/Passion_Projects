package main.java.model.quizzes;

import main.java.model.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Quiz {

    // Instance Variables
    private List<Flashcard> quizCards;
    private Flashcard currentCardInQuiz = null;


    // Constructor
    public Quiz (List<Flashcard> listOfFlashcards, int numberOfCardsToPutInQuizDeck) {
        this.quizCards = new ArrayList<>();
        List<Flashcard> listOfFlashcardsClone = new ArrayList<>(listOfFlashcards);

        // Gets a number of random flashcards to put in the deck
        for (int i = 0; i < numberOfCardsToPutInQuizDeck; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, listOfFlashcardsClone.size());
            Flashcard flashcardToAddToQuiz = listOfFlashcardsClone.remove(randomIndex);
            flashcardToAddToQuiz.setPoints(1);

            this.quizCards.add(flashcardToAddToQuiz);
        }
    }


    // Getters
    public int getNumberOfCardsInQuiz() {
        return this.quizCards.size();
    }

    public List<Flashcard> getQuizCards() {
        return this.quizCards;
    }

    // Methods
    public Flashcard pullCardOutOfQuizCardsList() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, this.quizCards.size());
        Flashcard flashcardPulled = this.quizCards.get(randomIndex);
        this.currentCardInQuiz = flashcardPulled;
        this.quizCards.remove(randomIndex);

        return this.currentCardInQuiz;
    }

    public void putPulledCardBackIntoQuizCardsList() {
        this.currentCardInQuiz.setPoints( currentCardInQuiz.getPoints() + 1 );
        this.quizCards.add(this.currentCardInQuiz);
        this.currentCardInQuiz = null;
    }



}
