package com.example.midsempractice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScreenTwo extends AppCompatActivity {

    private Button backScreen;
    private TextView  label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_screen_two);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backScreen = findViewById(R.id.back);
        backScreen.setOnClickListener(v->moveToPrevScreen());

        label = findViewById(R.id.textView);
        int msg = getIntent().getIntExtra("Random number",12);
        label.setText(String.valueOf(msg));

    }

    private void moveToPrevScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}