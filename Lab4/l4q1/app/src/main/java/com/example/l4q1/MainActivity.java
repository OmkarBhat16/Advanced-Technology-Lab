package com.example.l4q1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup question1, question2;
    private final int[] correctAnswers = {R.id.q1a2, R.id.q2a3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question1 = findViewById(R.id.question1);
        question2 = findViewById(R.id.question2);
        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(v -> showConfirmationDialog());
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit the quiz?")
                .setPositiveButton("Yes", (dialog, which) -> calculateScore())
                .setNegativeButton("No", null)
                .show();
    }

    private void calculateScore() {
        int score = 0;

        if (question1.getCheckedRadioButtonId() == correctAnswers[0]) score++;
        if (question2.getCheckedRadioButtonId() == correctAnswers[1]) score++;

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }
}