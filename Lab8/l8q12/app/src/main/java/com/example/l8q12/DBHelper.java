package com.example.l8q12;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "clinic.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT)");
        db.execSQL("CREATE TABLE doctors(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE appointments(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, doctor_id INTEGER, datetime TEXT)");

        db.execSQL("INSERT INTO users(email, password) VALUES('user@test.com','123456')");
        db.execSQL("INSERT INTO doctors(name) VALUES('Dr. Smith'), ('Dr. Adams')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS doctors");
        db.execSQL("DROP TABLE IF EXISTS appointments");
        onCreate(db);
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
        return c.getCount() > 0;
    }

    public List<String> getAllDoctorNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM doctors", null);
        List<String> names = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {
                names.add(c.getString(0));
            } while (c.moveToNext());
            c.close();
        }
        return names;
    }


    public boolean bookAppointment(String email, String doctorName, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor userCursor = db.rawQuery("SELECT id FROM users WHERE email=?", new String[]{email});
        Cursor doctorCursor = db.rawQuery("SELECT id FROM doctors WHERE name=?", new String[]{doctorName});
        if (userCursor.moveToFirst() && doctorCursor.moveToFirst()) {
            int userId = userCursor.getInt(0);
            int doctorId = doctorCursor.getInt(0);

            Cursor check = db.rawQuery("SELECT * FROM appointments WHERE doctor_id=? AND datetime=?", new String[]{String.valueOf(doctorId), datetime});
            if (check.getCount() == 0) {
                db.execSQL("INSERT INTO appointments(user_id, doctor_id, datetime) VALUES(?,?,?)",
                        new Object[]{userId, doctorId, datetime});
                return true;
            }
        }
        return false;
    }
}
