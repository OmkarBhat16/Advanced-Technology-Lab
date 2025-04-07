package com.example.l8q12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        db = new DBHelper(this);

        try {
        login.setOnClickListener(v -> {
            if (db.checkUser(email.getText().toString(), password.getText().toString())) {
                Intent i = new Intent(MainActivity.this, AppointmentActivity.class);
                i.putExtra("userEmail", email.getText().toString());
                startActivity(i);
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });} catch (Exception e) {
            e.printStackTrace();  // Or use Log.e("DB", "Error: " + e.getMessage());
        }
    }
}