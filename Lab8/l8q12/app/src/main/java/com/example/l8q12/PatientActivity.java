package com.example.l8q12;

import android.os.Bundle;

public class PatientActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private int patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        dbHelper = new DBHelper(this);
        ListView listView = findViewById(R.id.listDoctors);

        // Get patient ID from shared preferences or intent
        // Implement your own user session management

        Cursor cursor = dbHelper.getDoctors();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{DBHelper.COL_NAME},
                new int[]{android.R.id.text1},
                0
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Cursor c = (Cursor) parent.getItemAtPosition(position);
            int doctorId = c.getInt(c.getColumnIndex(DBHelper.COL_ID));
            showDateTimePicker(doctorId);
        });
    }

    private void showDateTimePicker(int doctorId) {
        // Implement date and time picker dialog
        // After selection, check availability and book
        String selectedDateTime = "2023-08-15 10:00"; // Example format

        if(dbHelper.isSlotAvailable(doctorId, selectedDateTime)) {
            boolean booked = dbHelper.bookAppointment(doctorId, patientId, selectedDateTime);
            if(booked) {
                Toast.makeText(this, "Appointment booked!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Slot not available", Toast.LENGTH_SHORT).show();
        }
    }
}