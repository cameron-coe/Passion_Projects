package main.java.model;

public class Flashcard {

    // Instance Variables
    private String front = "";
    private String back = "";
    private int points = 1;


    // Constructor
    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;
        this.points = 1;
    }


    // Getters
    public String getFront() {
        return this.front;
    }

    public String getBack() {
        return this.back;
    }

    public int getPoints() {
        return this.points;
    }


    // Setters
    public void setFront(String front) {
        this.front = front;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
