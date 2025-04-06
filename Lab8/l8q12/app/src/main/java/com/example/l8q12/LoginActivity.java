package com.example.l8q12;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DBHelper(this);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(v -> loginUser());
        findViewById(R.id.btnRegister).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void loginUser() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        Cursor cursor = dbHelper.checkUser(email, password);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            String role = cursor.getString(cursor.getColumnIndex(DBHelper.COL_ROLE));
            Intent intent = role.equals("doctor") ?
                    new Intent(this, DoctorActivity.class) :
                    new Intent(this, PatientActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}