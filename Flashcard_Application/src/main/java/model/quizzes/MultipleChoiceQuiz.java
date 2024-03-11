package main.java.model.quizzes;

import main.java.model.Flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MultipleChoiceQuiz extends Quiz {

    //Instance Variables
    private String[] answerOptions;
    private int indexOfCorrectAnswer;


    // Constructor
    public MultipleChoiceQuiz(List<Flashcard> listOfFlashcards, int numberOfCardsToPutInQuizDeck) {
        super(listOfFlashcards, numberOfCardsToPutInQuizDeck);
    }


    // Getters
    public String[] getAnswerOptions() {
        return this.answerOptions;
    }

    public boolean checkAnswer(String answer) {
        if (answer.equalsIgnoreCase("a") && indexOfCorrectAnswer == 0) {
            return true;
        }
        if (answer.equalsIgnoreCase("b") && indexOfCorrectAnswer == 1) {
            return true;
        }
        if (answer.equalsIgnoreCase("c") && indexOfCorrectAnswer == 2) {
            return true;
        }
        if (answer.equalsIgnoreCase("d") && indexOfCorrectAnswer == 3) {
            return true;
        }
        return false;
    }


    // Methods
    @Override
    public Flashcard pullCardOutOfQuizCardsList() {
        Flashcard pulledFlashcard = super.pullCardOutOfQuizCardsList();
        this.answerOptions = new String[]{"", "", "", ""};

        // sets the correct answer
        this.indexOfCorrectAnswer = ThreadLocalRandom.current().nextInt(0, this.answerOptions.length);
        this.answerOptions[this.indexOfCorrectAnswer] = pulledFlashcard.getBack();

        // Fill the other answer options with wrong answers
        List<Flashcard> answerPool = new ArrayList<>(this.getQuizCards());
        for (int i=0; i < this.answerOptions.length; i++) {
            if (this.answerOptions[i].equals("")) {
                if (answerPool.size() > 0) {
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, answerPool.size());
                    this.answerOptions[i] = answerPool.get(randomIndex).getBack();
                    answerPool.remove(randomIndex);
                }
            }
        }

        return pulledFlashcard;
    }
}
