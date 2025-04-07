package com.example.l9q2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "airline.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE passengers(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, flight TEXT)");
        db.execSQL("CREATE TABLE flights(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("INSERT INTO flights(name) VALUES('Flight A'), ('Flight B'), ('Flight C')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS passengers");
        db.execSQL("DROP TABLE IF EXISTS flights");
        onCreate(db);
    }

    public List<String> getAllFlights() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM flights", null);
        List<String> list = new ArrayList<>();
        while (c.moveToNext()) list.add(c.getString(0));
        c.close();
        return list;
    }

    public boolean bookTicket(String name, String flight) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor check = db.rawQuery("SELECT * FROM passengers WHERE name=? AND flight=?", new String[]{name, flight});
        if (check.getCount() == 0) {
            db.execSQL("INSERT INTO passengers(name, flight) VALUES(?, ?)", new Object[]{name, flight});
            return true;
        }
        return false;
    }
}
