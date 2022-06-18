package com.example.a3_ismail.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.a3_ismail.R;
import com.example.a3_ismail.data.models.Question;
import com.example.a3_ismail.databinding.FragmentQuizBinding;
import com.example.a3_ismail.ui.viewmodels.QuizViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class QuizFragment extends Fragment {

    private static final String TAG = "QuizFragment";
    private FragmentQuizBinding binding;

    private QuizViewModel viewModel;
    private final int TOTAL_NUMBER_OF_QUESTIONS = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_quiz, container, false);
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.viewModel = QuizViewModel.getInstance(this.getActivity().getApplication());

        // create an observer for the Current Question
        final Observer<Question> currentQuestion = new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                showNextQuestion(question);
            }
        };

        viewModel.getCurrentQuestionContainer().observe(getViewLifecycleOwner(), currentQuestion);

        // create an observer for Game Over flag
        final Observer<Boolean> isGameOverCheck = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isOver) {
                if (isOver) {
                    setGameOver();
                }
            }
        };
        viewModel.getIsGameOverContainer().observe(getViewLifecycleOwner(), isGameOverCheck);

        // create an observer for the Current Score
        final Observer<Integer> currentScore = new Observer<Integer>() {
            @Override
            public void onChanged(Integer score) {
                setCurrentScore(score);
            }
        };
        viewModel.getCurrentScoreContainer().observe(getViewLifecycleOwner(), currentScore);

        binding.btnOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.selectOption(Integer.parseInt(binding.btnOption1.getText().toString()));
            }
        });

        binding.btnOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.selectOption(Integer.parseInt(binding.btnOption2.getText().toString()));
            }
        });

        binding.btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStartGame();
            }
        });

        // Start game
        this.viewModel.startGame();

    }

    // Makes UI elements ready for a new game
    private void setStartGame() {
        this.binding.btnOption1.setEnabled(true);
        this.binding.btnOption2.setEnabled(true);
        this.binding.btnPlayAgain.setVisibility(View.GONE);
        viewModel.startGame();
    }

    // Makes UI elements ready for "game over"
    private void setGameOver() {
        this.binding.btnOption1.setEnabled(false);
        this.binding.btnOption2.setEnabled(false);
        this.binding.btnPlayAgain.setVisibility(View.VISIBLE);

        Snackbar.make(this.binding.getRoot(), "Game Over!", Snackbar.LENGTH_LONG).show();
    }

    // Sets the UI element for the next question
    private void showNextQuestion(Question question) {
        // Shuffle options so the order of the correct and incorrect options would be random
        ArrayList<Integer> options = new ArrayList<>();
        options.add(question.getCorrectAnswer());
        options.add(question.getIncorrectAnswer());
        Collections.shuffle(options);

        this.binding.tvQuestion.setText(question.getQuestionText());
        this.binding.btnOption1.setText(String.valueOf(options.get(0)));
        this.binding.btnOption2.setText(String.valueOf(options.get(1)));
    }

    // Sets the Current Score textview
    private void setCurrentScore(int score) {
        this.binding.tvScore.setText("Score: " + score + " out of " + TOTAL_NUMBER_OF_QUESTIONS);
    }
}