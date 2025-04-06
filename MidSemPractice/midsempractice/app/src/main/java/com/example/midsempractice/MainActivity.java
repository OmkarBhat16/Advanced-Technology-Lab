package com.example.midsempractice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button nextScreen = findViewById(R.id.next);
        nextScreen.setOnClickListener(v-> moveToNextScreen());

        Button alertBtn = findViewById(R.id.button);
        alertBtn.setOnClickListener(v->showAlertDialog());
    }

    private void moveToNextScreen(){
        Intent intent = new Intent(this,ScreenTwo.class);
        intent.putExtra("Random number",123);
        startActivity(intent);
    }

    private void showAlertDialog(){
        new AlertDialog.Builder(this)
                .setTitle("SKibidi")
                .setNegativeButton("hello", null)
                .setPositiveButton("Yellow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveToNextScreen();
                    }
                }).show();
    }
}