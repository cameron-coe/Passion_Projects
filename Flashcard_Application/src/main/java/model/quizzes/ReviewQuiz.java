package main.java.model.quizzes;

import main.java.model.Flashcard;

import java.util.List;

public class ReviewQuiz extends Quiz {

    // Constructor
    public ReviewQuiz(List<Flashcard> listOfFlashcards, int numberOfCardsToPutInQuizDeck) {
        super(listOfFlashcards, numberOfCardsToPutInQuizDeck);
    }
}
