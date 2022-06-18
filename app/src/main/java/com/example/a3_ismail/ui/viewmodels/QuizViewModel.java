package com.example.a3_ismail.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.a3_ismail.data.db.HistoryDatabase;
import com.example.a3_ismail.data.db.ResultDao;
import com.example.a3_ismail.data.models.Question;
import com.example.a3_ismail.data.models.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class QuizViewModel extends AndroidViewModel {

    private static QuizViewModel instance;
    private ArrayList<Question> questionArrayList = new ArrayList<>();
    private final int TOTAL_NUMBER_OF_QUESTIONS = 3;
    private  int currentQuestionIndex;
    private  int currentScore = 0;
    private ResultDao dao;

    private MutableLiveData<Question> currentQuestionContainer = new MutableLiveData<>();
    private MutableLiveData<Boolean> isGameOverContainer = new MutableLiveData<>();
    private MutableLiveData<Integer> currentScoreContainer = new MutableLiveData<>();

    public QuizViewModel(@NonNull Application application) {
        super(application);
        this.dao = HistoryDatabase.getDatabase(application).resultDao();
    }

    public static QuizViewModel getInstance(Application application) {
        if (instance == null) {
            instance = new QuizViewModel(application);
        }

        return instance;
    }

    // Reloads Question ArrayList and Current Score. Then, starts the game with the first question
    public void startGame() {
        questionArrayList.removeAll(questionArrayList);

        for (int i = 0; i < TOTAL_NUMBER_OF_QUESTIONS; i++) {
            questionArrayList.add(new Question());
        }

        currentScore = 0;
        currentScoreContainer.setValue(0);
        setCurrentQuestion(0);
    }

    // Set the Current Score and increase the Current Question number when an option is selected
    public void selectOption(int answer) {
        if (questionArrayList.get(currentQuestionIndex).getCorrectAnswer() == answer) {
            // correct answer
            currentScore++;
            currentScoreContainer.setValue(currentScore);
        }
        else {
            // wrong answer -> Do nothing.
        }
        setCurrentQuestion(currentQuestionIndex + 1);
    }

    // Send the Current Question to the UI
    private void setCurrentQuestion(int index) {
        if (index >= TOTAL_NUMBER_OF_QUESTIONS) {
            setGameOver();
        }
        else {
            currentQuestionIndex = index;
            currentQuestionContainer.setValue(this.questionArrayList.get(index));
            isGameOverContainer.setValue(false);
        }

    }

    // Set the game as Over. Save the Current Date and Score to Room DB
    private void setGameOver() {
        isGameOverContainer.setValue(true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        dao.insert(new Result(formatter.format(now), currentScore));
    }

    public MutableLiveData<Question> getCurrentQuestionContainer() {
        return currentQuestionContainer;
    }

    public MutableLiveData<Boolean> getIsGameOverContainer() {
        return isGameOverContainer;
    }

    public MutableLiveData<Integer> getCurrentScoreContainer() {
        return currentScoreContainer;
    }
}
