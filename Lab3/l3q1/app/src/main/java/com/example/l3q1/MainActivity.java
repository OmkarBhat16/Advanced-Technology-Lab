package com.example.l3q1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etFeedback;
    private TextInputLayout nameInputLayout, emailInputLayout, feedbackInputLayout;
    private RatingBar ratingBar;
    private CheckBox cbFollowUp;

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etFeedback = findViewById(R.id.etFeedback);
        nameInputLayout = findViewById(R.id.nameInputLayout);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        feedbackInputLayout = findViewById(R.id.feedbackInputLayout);
        ratingBar = findViewById(R.id.ratingBar);
        cbFollowUp = findViewById(R.id.cbFollowUp);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        // Reset errors
        nameInputLayout.setError(null);
        emailInputLayout.setError(null);
        feedbackInputLayout.setError(null);

        // Get input values
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String feedback = etFeedback.getText().toString().trim();
        float rating = ratingBar.getRating();
        boolean wantsFollowUp = cbFollowUp.isChecked();

        // Validate inputs
        boolean isValid = true;

        if (name.isEmpty()) {
            nameInputLayout.setError("Please enter your name");
            isValid = false;
        }

        if (email.isEmpty()) {
            emailInputLayout.setError("Please enter your email");
            isValid = false;
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            emailInputLayout.setError("Invalid email address");
            isValid = false;
        }

        if (feedback.isEmpty()) {
            feedbackInputLayout.setError("Please provide feedback");
            isValid = false;
        }

        if (rating < 1) {
            Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (isValid) {
            // Here you would typically send data to server
            String message = "Thank you for your feedback!\n";
            message += "Rating: " + rating + " stars\n";
            message += "Follow-up requested: " + (wantsFollowUp ? "Yes" : "No");

            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            // Optional: Clear form after submission
            clearForm();
        }
    }

    private void clearForm() {
        etName.setText("");
        etEmail.setText("");
        etFeedback.setText("");
        ratingBar.setRating(0);
        cbFollowUp.setChecked(false);
    }
}