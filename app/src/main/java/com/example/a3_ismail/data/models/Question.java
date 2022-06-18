package com.example.a3_ismail.data.models;

import java.util.Random;

public class Question {
    String questionText;
    int correctAnswer;

    public Question() {
        Random random = new Random();
        int firstNumber = random.nextInt((10 - 1) + 1);
        int secondNumber = random.nextInt((10 - 1) + 1);
        this.questionText = firstNumber + " + " + secondNumber;
        this.correctAnswer = firstNumber + secondNumber;
    }

    public int getIncorrectAnswer() {
        return correctAnswer - 1;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
