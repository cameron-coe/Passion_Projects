package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class GameDataDto {
    private int totalScore;
    private List<Subject> subjectList;

    public GameDataDto() {
        totalScore = 0;
        subjectList = new ArrayList<>();
    }

    public int getTotalScore() {
        return totalScore;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }


    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }
}
