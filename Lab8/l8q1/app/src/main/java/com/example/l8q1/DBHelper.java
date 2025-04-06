package com.example.l8q1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BillingDB";
    private static final int DB_VERSION = 1;

    // Products Table
    private static final String TABLE_BILLS = "bills";
    public static final String COL_ID = "id";
    public static final String COL_PRODUCT = "product";
    public static final String COL_PRICE = "price";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_TIMESTAMP = "timestamp";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_BILLS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUCT + " TEXT, " +
                COL_PRICE + " REAL, " +
                COL_QUANTITY + " INTEGER, " +
                COL_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        onCreate(db);
    }

    public boolean addBillItem(String product, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT, product);
        values.put(COL_PRICE, price);
        values.put(COL_QUANTITY, quantity);

        long result = db.insert(TABLE_BILLS, null, values);
        return result != -1;
    }

    // Modify getAllBills() method
    public Cursor getAllBills() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT id AS _id, product, price, quantity, timestamp FROM " + TABLE_BILLS, null);
    }

    // Add these new methods to DBHelper class
    public int getTotalQuantity() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_QUANTITY + ") FROM " + TABLE_BILLS, null);
        int total = 0;
        if(cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }
        cursor.close();
        return total;
    }

    public String getMaxProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_PRODUCT + ", " + COL_PRICE +
                " FROM " + TABLE_BILLS + " ORDER BY " + COL_PRICE + " DESC LIMIT 1", null);
        String result = "N/A";
        if(cursor.moveToFirst()) {
            result = cursor.getString(0) + " ($" + cursor.getDouble(1) + ")";
        }
        cursor.close();
        return result;
    }

    public String getMinProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_PRODUCT + ", " + COL_PRICE +
                " FROM " + TABLE_BILLS + " ORDER BY " + COL_PRICE + " ASC LIMIT 1", null);
        String result = "N/A";
        if(cursor.moveToFirst()) {
            result = cursor.getString(0) + " ($" + cursor.getDouble(1) + ")";
        }
        cursor.close();
        return result;
    }
}