package com.example.l8q12;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AppointmentActivity extends AppCompatActivity {
    Spinner doctorSpinner;
    Button pickDateTime, book;
    TextView selectedDateTime;
    DBHelper db;
    String userEmail;
    String datetime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        doctorSpinner = findViewById(R.id.doctorSpinner);
        pickDateTime = findViewById(R.id.pickDateTime);
        book = findViewById(R.id.book);
        selectedDateTime = findViewById(R.id.selectedDateTime);
        db = new DBHelper(this);
        userEmail = getIntent().getStringExtra("userEmail");

        List<String> doctors = db.getAllDoctorNames();
        if (doctors.isEmpty()) {
            doctors.add("No doctors available");
            book.setEnabled(false);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorSpinner.setAdapter(adapter);


        pickDateTime.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, day) -> {
                TimePickerDialog timePicker = new TimePickerDialog(this, (view1, hour, minute) -> {
                    datetime = year + "-" + (month + 1) + "-" + day + " " + hour + ":" + minute;
                    selectedDateTime.setText(datetime);
                }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
                timePicker.show();
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
        });

        book.setOnClickListener(v -> {
            String doctorName = doctorSpinner.getSelectedItem().toString();
            if (db.bookAppointment(userEmail, doctorName, datetime)) {
                Toast.makeText(this, "Appointment booked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Slot already taken", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
