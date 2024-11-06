package com.example.dicegame;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int gameScore = 0;
    private int rollScore = 0;
    private int countRoll = 0;
    private int[] arr;
    private int[] textViewIDs;
    private List<TextView> textViews = new ArrayList<>();
    private Button button;
    private Button reset;
    private TextView countRollTextView;
    private TextView gameScoreTextView;
    private TextView rollScoreTextView;
    private int min = 1;
    private int max = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        reset = findViewById(R.id.reset);
        countRollTextView = findViewById(R.id.count);
        gameScoreTextView = findViewById(R.id.gameScore);
        rollScoreTextView = findViewById(R.id.rollScore);

        arr = new int[5];
        textViewIDs = new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4, R.id.textView5};

        for (int resID : textViewIDs) {
            TextView textView = findViewById(resID);
            textViews.add(textView);
        }

        button.setOnClickListener(v -> rollDice());
        reset.setOnClickListener(v -> resetGame());
    }

    private void rollDice() {
        for (int i = 0; i < textViews.size(); i++) {
            arr[i] = (int) (Math.random() * (max - min + 1)) + min;
        }

        rollScore = calculateScore(arr);

        updateTextView(arr);
        updateRollScore(rollScore);
        updateGameScore(rollScore);
        updateRollCount();
    }

    private int calculateScore(int[] diceResult) {
        int score = 0;
        int[] occurrences = new int[max + 1];

        for (int value : diceResult) {
            occurrences[value]++;
        }

        for (int i = min; i <= max; i++) {
            if (occurrences[i] >= 2) {
                score += i * occurrences[i];
            }
        }

        return score;
    }

    private void updateTextView(int[] diceResult) {
        for (int i = 0; i < diceResult.length; i++) {
            TextView textView = textViews.get(i);
            textView.setText(String.valueOf(diceResult[i]));
        }
    }

    private void updateRollScore(int newScore) {
        rollScoreTextView.setText("Wynik tego losowania: " + newScore);
    }

    private void updateGameScore(int newScore) {
        gameScore += newScore;
        gameScoreTextView.setText("Wynik gry: " + gameScore);
    }

    private void updateRollCount() {
        countRoll++;
        countRollTextView.setText("Liczba rzutow: " + countRoll);
    }

    private void resetGame() {
        rollScore = 0;
        gameScore = 0;
        countRoll = -1;
        updateRollScore(rollScore);
        updateGameScore(gameScore);
        updateRollCount();

        for (TextView textView : textViews) {
            textView.setText("?");
        }
    }
}
