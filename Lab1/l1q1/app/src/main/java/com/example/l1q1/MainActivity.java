package com.example.l1q1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextJobTitle, editTextCompany, editTextEmail, editTextExperience;
    private TextView textViewProfile;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextJobTitle = findViewById(R.id.editTextJobTitle);
        editTextCompany = findViewById(R.id.editTextCompany);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextExperience = findViewById(R.id.editTextExperience);
        textViewProfile = findViewById(R.id.textViewProfile);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserProfile();
            }
        });
    }

    private void createUserProfile() {
        String name = editTextName.getText().toString().trim();
        String jobTitle = editTextJobTitle.getText().toString().trim();
        String company = editTextCompany.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String experience = editTextExperience.getText().toString().trim();

        if (name.isEmpty() || jobTitle.isEmpty() || company.isEmpty() || email.isEmpty() || experience.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String profile = "Professional Profile:\n\n" +
                "Name: " + name + "\n" +
                "Job Title: " + jobTitle + "\n" +
                "Company: " + company + "\n" +
                "Email: " + email + "\n" +
                "Experience: " + experience + " years";

        textViewProfile.setText(profile);
    }
}