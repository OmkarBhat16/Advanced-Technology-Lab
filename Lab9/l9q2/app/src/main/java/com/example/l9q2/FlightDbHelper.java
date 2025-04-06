package com.example.l9q2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FlightDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "flights.db";
    private static final int DB_VERSION = 1;

    // Flights Table
    public static final String TABLE_FLIGHTS = "flights";
    public static final String COL_ID = "_id";
    public static final String COL_FLIGHT_NO = "flight_no";
    public static final String COL_FROM = "departure";
    public static final String COL_TO = "destination";
    public static final String COL_TIME = "time";
    public static final String COL_PRICE = "price";
    public static final String COL_SEATS = "seats";

    public FlightDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_FLIGHTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FLIGHT_NO + " TEXT, " +
                COL_FROM + " TEXT, " +
                COL_TO + " TEXT, " +
                COL_TIME + " TEXT, " +
                COL_PRICE + " REAL, " +
                COL_SEATS + " INTEGER)";
        db.execSQL(createTable);

        // Insert sample flights
        insertSampleFlights(db);
    }

    private void insertSampleFlights(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COL_FLIGHT_NO, "AA101");
        values.put(COL_FROM, "New York");
        values.put(COL_TO, "London");
        values.put(COL_TIME, "08:00 AM");
        values.put(COL_PRICE, 450.00);
        values.put(COL_SEATS, 50);
        db.insert(TABLE_FLIGHTS, null, values);

        values.put(COL_FLIGHT_NO, "BA202");
        values.put(COL_FROM, "London");
        values.put(COL_TO, "Paris");
        values.put(COL_TIME, "10:30 AM");
        values.put(COL_PRICE, 150.00);
        values.put(COL_SEATS, 30);
        db.insert(TABLE_FLIGHTS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db);
    }
}