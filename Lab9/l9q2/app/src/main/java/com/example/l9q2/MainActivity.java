package com.example.l9q2;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    Spinner flightSpinner;
    Button bookButton;
    TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        flightSpinner = findViewById(R.id.flightSpinner);
        bookButton = findViewById(R.id.bookButton);
        statusText = findViewById(R.id.statusText);

        // Sample flight options
        String[] flights = {"Select Flight", "Flight A - NYC to LA", "Flight B - Delhi to Mumbai", "Flight C - London to Paris"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, flights);
        flightSpinner.setAdapter(adapter);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String flight = flightSpinner.getSelectedItem().toString();

                if (name.isEmpty() || flight.equals("Select Flight")) {
                    statusText.setText("Please enter your name and select a flight.");
                } else {
                    statusText.setText("Ticket booked for " + name + " on " + flight);
                }
            }
        });
    }
}