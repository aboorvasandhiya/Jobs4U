package com.example;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz extends  AppCompatActivity implements View.OnClickListener {
    // setting up things
    private Button falseButton;
    private Button trueButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private ImageView Image;
    private TextView questionTextView;
    private int correct = 0;
    private ImageButton home;
    // to keep current question track
    private int currentQuestionIndex = 0;

    private Questions[] questionBank = new Questions[] {
            // array of objects of class Question
            // providing questions from string
            // resource and the correct ans
            new Questions(R.string.a, true),
            new Questions(R.string.b, true),
            new Questions(R.string.c, false),
            new Questions(R.string.d, false),
            new Questions(R.string.e, false),

    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // setting up the buttons
        // associated with id
        falseButton = findViewById(R.id.false_button);
        trueButton = findViewById(R.id.true_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        Image = findViewById(R.id.myimage);
        falseButton.setOnClickListener(this);
        home = findViewById(R.id.quizhome);
        home.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        questionTextView
                = findViewById(R.id.answer_text_view);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Quiz.this, Home.class));

            }
        });
    }




    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v)
    {
        // checking which button is
        // clicked by user
        // in this case user choose false
        switch (v.getId()) {
            case R.id.false_button:
                checkAnswer(false);
                break;

            case R.id.true_button:
                checkAnswer(true);
                break;

            case R.id.next_button:
                // go to next question
                // limiting question bank range
                if (currentQuestionIndex < 6) {
                    currentQuestionIndex
                            = currentQuestionIndex + 1;
                    // we are safe now!
                    // last question reached
                    // making buttons
                    // invisible
                    if (currentQuestionIndex == 5) {
                        questionTextView.setText(getString(
                                R.string.correct, correct));
                        nextButton.setVisibility(
                                View.INVISIBLE);
                        prevButton.setVisibility(
                                View.INVISIBLE);
                        trueButton.setVisibility(
                                View.INVISIBLE);
                        falseButton.setVisibility(
                                View.INVISIBLE);
                        if (correct > 3)

                            questionTextView.setText(
                                    "CORRECTNESS IS " + correct
                                            + " "
                                            + "OUT OF 5");
                        // showing correctness
                    }
                    else {
                        updateQuestion();
                    }
                }

                break;
            case R.id.prev_button:
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex
                            = (currentQuestionIndex - 1)
                            % questionBank.length;
                    updateQuestion();
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateQuestion()
    {


        questionTextView.setText(
                questionBank[currentQuestionIndex]
                        .getAnswerResId());
        // setting the textview with new question
        switch (currentQuestionIndex) {
            case 1:
                // setting up image for each
                // question
                Image.setImageResource(R.drawable.q2);
                break;
            case 2:
                Image.setImageResource(R.drawable.q3);
                break;
            case 3:
                Image.setImageResource(R.drawable.q4);
                break;
            case 4:
                Image.setImageResource(R.drawable.q5);
                break;
            case 7:
                Image.setImageResource(R.drawable.homequiz);
                break;
        }
    }
    private void checkAnswer(boolean userChooseCorrect)
    {
        boolean answerIsTrue
                = questionBank[currentQuestionIndex]
                .isAnswerTrue();
        // getting correct ans of current question
        int toastMessageId;
        // if ans matches with the
        // button clicked

        if (userChooseCorrect == answerIsTrue) {
            toastMessageId = R.string.correct_answer;
            correct++;
        }
        else {
            // showing toast
            // message correct
            toastMessageId = R.string.wrong_answer;
        }

        Toast
                .makeText(Quiz.this, toastMessageId,
                        Toast.LENGTH_SHORT)
                .show();

    }



}