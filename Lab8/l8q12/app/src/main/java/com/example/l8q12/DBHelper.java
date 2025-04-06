package com.example.l8q12;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "ClinicDB";
    private static final int DB_VERSION = 1;

    // Users Table
    public static final String TABLE_USERS = "users";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_ROLE = "role";

    // Appointments Table
    public static final String TABLE_APPOINTMENTS = "appointments";
    public static final String COL_DATETIME = "datetime";
    public static final String COL_DOCTOR_ID = "doctor_id";
    public static final String COL_PATIENT_ID = "patient_id";
    public static final String COL_STATUS = "status";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String createUsers = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_EMAIL + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT, " +
                COL_ROLE + " TEXT)";
        db.execSQL(createUsers);

        // Create appointments table
        String createAppointments = "CREATE TABLE " + TABLE_APPOINTMENTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DOCTOR_ID + " INTEGER, " +
                COL_PATIENT_ID + " INTEGER, " +
                COL_DATETIME + " TEXT, " +
                COL_STATUS + " TEXT DEFAULT 'Pending')";
        db.execSQL(createAppointments);

        // Insert sample doctor
        ContentValues values = new ContentValues();
        values.put(COL_NAME, "Dr. Smith");
        values.put(COL_EMAIL, "doctor@clinic.com");
        values.put(COL_PASSWORD, "doctor123");
        values.put(COL_ROLE, "doctor");
        db.insert(TABLE_USERS, null, values);
    }

    // Add user registration method
    public boolean registerUser(String name, String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);
        values.put(COL_ROLE, role);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Check user credentials
    public Cursor checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COL_EMAIL + "=? AND " + COL_PASSWORD + "=?",
                new String[]{email, password});
    }

    // Book appointment
    public boolean bookAppointment(int doctorId, int patientId, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_DOCTOR_ID, doctorId);
        values.put(COL_PATIENT_ID, patientId);
        values.put(COL_DATETIME, datetime);
        long result = db.insert(TABLE_APPOINTMENTS, null, values);
        return result != -1;
    }

    // Get available doctors
    public Cursor getDoctors() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS +
                " WHERE " + COL_ROLE + "='doctor'", null);
    }

    // Check appointment availability
    public boolean isSlotAvailable(int doctorId, String datetime) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_APPOINTMENTS +
                        " WHERE " + COL_DOCTOR_ID + "=? AND " + COL_DATETIME + "=?",
                new String[]{String.valueOf(doctorId), datetime});
        boolean available = cursor.getCount() == 0;
        cursor.close();
        return available;
    }
}