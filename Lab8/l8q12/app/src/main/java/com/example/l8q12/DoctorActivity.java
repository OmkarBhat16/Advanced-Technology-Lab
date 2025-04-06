package com.example.l8q12;

public class DoctorActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        dbHelper = new DBHelper(this);
        ListView listView = findViewById(R.id.listAppointments);

        // Get doctor ID from shared preferences or intent
        // Implement your own user session management

        Cursor cursor = dbHelper.getDoctorAppointments(doctorId);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[]{DBHelper.COL_DATETIME, DBHelper.COL_STATUS},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );
        listView.setAdapter(adapter);
    }
}