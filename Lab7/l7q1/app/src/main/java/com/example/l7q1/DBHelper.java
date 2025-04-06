package com.example.l7q1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";

    public static final String COL_ROLL = "roll_no";
    public static final String COL_NAME = "name";
    public static final String COL_MARKS = "marks";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ROLL + " TEXT PRIMARY KEY, "
                + COL_NAME + " TEXT, "
                + COL_MARKS + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addStudent(String roll, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ROLL, roll);
        values.put(COL_NAME, name);
        values.put(COL_MARKS, marks);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean updateStudent(String roll, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_MARKS, marks);

        int result = db.update(TABLE_NAME, values, COL_ROLL + "=?", new String[]{roll});
        return result > 0;
    }

    public boolean deleteStudent(String roll) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ROLL + "=?", new String[]{roll});
        return result > 0;
    }
    // Modify getAllStudents() method
    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT roll_no AS _id, name, marks FROM " + TABLE_NAME, null);
    }
}
