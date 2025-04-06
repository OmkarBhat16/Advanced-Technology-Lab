package com.example.l9q1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SurveyDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "survey.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SurveyContract.SurveyEntry.TABLE_NAME + " (" +
                    SurveyContract.SurveyEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SurveyContract.SurveyEntry.COLUMN_SOURCE + " TEXT," +
                    SurveyContract.SurveyEntry.COLUMN_RATING + " REAL," +
                    SurveyContract.SurveyEntry.COLUMN_COMMENTS + " TEXT," +
                    SurveyContract.SurveyEntry.COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

    public SurveyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SurveyContract.SurveyEntry.TABLE_NAME);
        onCreate(db);
    }
}