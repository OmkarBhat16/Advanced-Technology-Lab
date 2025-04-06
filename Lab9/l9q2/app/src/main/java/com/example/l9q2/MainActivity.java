package com.example.l9q2;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.l9q2.FlightDbHelper;

public class MainActivity extends AppCompatActivity {
    private FlightDbHelper dbHelper;
    private SimpleCursorAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new FlightDbHelper(this);

        listView = findViewById(R.id.listFlights);
        Button btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> searchFlights());
        setupListView();
    }

    private void searchFlights() {
        EditText etFrom = findViewById(R.id.etFrom);
        EditText etTo = findViewById(R.id.etTo);

        String from = etFrom.getText().toString();
        String to = etTo.getText().toString();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.rawQuery(
                     "SELECT * FROM " + FlightDbHelper.TABLE_FLIGHTS +
                             " WHERE " + FlightDbHelper.COL_FROM + "=? AND " +
                             FlightDbHelper.COL_TO + "=? AND " +
                             FlightDbHelper.COL_SEATS + ">0",
                     new String[]{from, to})) {

            adapter.swapCursor(cursor);
        }
    }

    @SuppressLint("Range")
    private void setupListView() {
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.item_flight,
                null,
                new String[]{
                        FlightDbHelper.COL_FLIGHT_NO,
                        FlightDbHelper.COL_TIME,
                        FlightDbHelper.COL_PRICE,
                        FlightDbHelper.COL_SEATS
                },
                new int[]{R.id.tvFlight, R.id.tvTime, R.id.tvPrice, R.id.tvSeats},
                0
        );

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.tvSeats) {
                    int seats = cursor.getInt(cursor.getColumnIndex(FlightDbHelper.COL_SEATS));
                    ((TextView) view).setText("Available Seats: " + seats);
                    return true;
                }
                if (view.getId() == R.id.tvPrice) {
                    double price = cursor.getDouble(cursor.getColumnIndex(FlightDbHelper.COL_PRICE));
                    ((TextView) view).setText(String.format("Price: $%.2f", price));
                    return true;
                }
                return false;
            }
        });

        listView.setAdapter(adapter);
    }

    @SuppressLint("Range")
    private void showBookingDialog(Cursor cursor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_booking, null);

        TextView tvFlight = dialogView.findViewById(R.id.tvFlight);
        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etSeats = dialogView.findViewById(R.id.etSeats);

        int flightId = cursor.getInt(cursor.getColumnIndex(FlightDbHelper.COL_ID));
        String flightNo = cursor.getString(cursor.getColumnIndex(FlightDbHelper.COL_FLIGHT_NO));
        int availableSeats = cursor.getInt(cursor.getColumnIndex(FlightDbHelper.COL_SEATS));

        tvFlight.setText("Flight: " + flightNo);

        builder.setView(dialogView)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    try {
                        String name = etName.getText().toString();
                        int seats = Integer.parseInt(etSeats.getText().toString());

                        if (seats <= availableSeats && !name.isEmpty()) {
                            bookFlight(flightId, seats);
                            searchFlights(); // Refresh list
                            Toast.makeText(MainActivity.this,
                                    "Booking confirmed for " + name,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "Invalid input or not enough seats",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this,
                                "Please enter valid number of seats",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void bookFlight(int flightId, int seats) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + FlightDbHelper.TABLE_FLIGHTS +
                    " SET " + FlightDbHelper.COL_SEATS + " = " + FlightDbHelper.COL_SEATS + " - " + seats +
                    " WHERE " + FlightDbHelper.COL_ID + " = " + flightId);
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        if (adapter != null && adapter.getCursor() != null) {
            adapter.getCursor().close();
        }
        super.onDestroy();
    }
}