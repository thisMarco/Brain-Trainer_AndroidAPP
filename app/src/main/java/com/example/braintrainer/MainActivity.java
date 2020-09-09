package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;

    int iCorrectAnswerLocation;
    int score = 0;
    int nOfQuestions = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.btnAnswer0);
        btn1 = findViewById(R.id.btnAnswer1);
        btn2 = findViewById(R.id.btnAnswer2);
        btn3 = findViewById(R.id.btnAnswer3);

        ActivateAnswerButtons(false);

    }

    public void Start(View v) {
        TextView score = findViewById(R.id.txtScore);
        score.setText("0/0");
        newQuestion();
        Button start = findViewById(R.id.btnStart);
        start.setVisibility(View.INVISIBLE);

        new CountDownTimer(30 * 1000 + 100, 1 * 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                TextView timeLeft = findViewById(R.id.txtSeconds);
                timeLeft.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {
                StopAndReset();
            }
        }.start();

        ActivateAnswerButtons(true);

    }

    public void SendAnswer(View v) {
        String btnID = v.getTag().toString();
        String correctAnswer = Integer.toString(iCorrectAnswerLocation);
        TextView checkResult = findViewById(R.id.txtCheckAnswer);

        if (btnID.equals(correctAnswer)) {
            checkResult.setText("Correct!");
            checkResult.setBackgroundColor(Color.GREEN);
            score++;
        }
        else {
            checkResult.setText("Wrong!");
            checkResult.setBackgroundColor(Color.RED);
        }

        nOfQuestions++;

        TextView txtScore = findViewById(R.id.txtScore);
        txtScore.setText(Integer.toString(score) + "/" + Integer.toString(nOfQuestions));

        newQuestion();
    }

    public void newQuestion() {

        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        TextView problem = findViewById(R.id.txtProblem);
        problem.setText(Integer.toString(a) + " + " + Integer.toString(b));

        iCorrectAnswerLocation = random.nextInt(4);

        ArrayList<Integer> answers = new ArrayList<Integer>();

        answers.clear();

        for (int i = 0; i<4; i++) {
            if (i == iCorrectAnswerLocation)
                answers.add(a+b);
            else {
                int wrongAnswer = random.nextInt(41);

                while( wrongAnswer == a+b){
                    wrongAnswer = random.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        btn0.setText(Integer.toString(answers.get(0)));
        btn1.setText(Integer.toString(answers.get(1)));
        btn2.setText(Integer.toString(answers.get(2)));
        btn3.setText(Integer.toString(answers.get(3)));
    }

    public void ActivateAnswerButtons(Boolean active) {

        btn0.setEnabled(active);
        btn1.setEnabled(active);
        btn2.setEnabled(active);
        btn3.setEnabled(active);

        if (!active) {
            btn0.setText("");
            btn1.setText("");
            btn2.setText("");
            btn3.setText("");
        }
    }

    public void StopAndReset() {
        TextView checkResult = findViewById(R.id.txtCheckAnswer);
        checkResult.setText("Done!");

        TextView problem = findViewById(R.id.txtProblem);
        problem.setText("");

        TextView checkAnswer = findViewById(R.id.txtCheckAnswer);
        checkAnswer.setBackgroundColor(Color.TRANSPARENT);

        ActivateAnswerButtons(false);

        Button btnGo = findViewById(R.id.btnStart);
        btnGo.setVisibility(View.VISIBLE);

        score = 0;
        nOfQuestions = 0;
    }
}