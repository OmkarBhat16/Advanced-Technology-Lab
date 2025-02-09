package com.example.l2q3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ConstraintLayout mainLayout;
    private Spinner colorSpinner;

    // Color options and their corresponding hex values
    private final String[] colors = {"Red", "Green", "Blue", "Yellow", "White", "Black"};
    private final String[] colorCodes = {
            "#FF0000", "#00FF00", "#0000FF",
            "#FFFF00", "#FFFFFF", "#000000"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);
        colorSpinner = findViewById(R.id.colorSpinner);

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                colors
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
        colorSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Change background color based on selection
        String selectedColor = colorCodes[position];
        mainLayout.setBackgroundColor(Color.parseColor(selectedColor));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Optional: Handle no selection
    }
}