package com.example.l7q1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etRollNo, etName, etMarks;
    private Button btnAdd, btnUpdate, btnDelete, btnViewAll;
    private ListView listView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        etRollNo = findViewById(R.id.etRollNo);
        etName = findViewById(R.id.etName);
        etMarks = findViewById(R.id.etMarks);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnViewAll = findViewById(R.id.btnViewAll);
        listView = findViewById(R.id.listView);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roll = etRollNo.getText().toString();
                String name = etName.getText().toString();
                String marks = etMarks.getText().toString();

                if(roll.isEmpty() || name.isEmpty() || marks.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean added = dbHelper.addStudent(roll, name, marks);
                if(added) {
                    Toast.makeText(MainActivity.this, "Student added", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(MainActivity.this, "Addition failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roll = etRollNo.getText().toString();
                String name = etName.getText().toString();
                String marks = etMarks.getText().toString();

                if(roll.isEmpty() || name.isEmpty() || marks.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean updated = dbHelper.updateStudent(roll, name, marks);
                if(updated) {
                    Toast.makeText(MainActivity.this, "Student updated", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(MainActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roll = etRollNo.getText().toString();

                if(roll.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter roll number", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean deleted = dbHelper.deleteStudent(roll);
                if(deleted) {
                    Toast.makeText(MainActivity.this, "Student deleted", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(MainActivity.this, "Deletion failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getAllStudents();

                if(cursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No records found", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Use correct column names including _id alias
                String[] fromColumns = {"_id", DBHelper.COL_NAME, DBHelper.COL_MARKS};
                int[] toViews = {R.id.tvRoll, R.id.tvName, R.id.tvMarks};

                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        MainActivity.this,
                        R.layout.list_item,
                        cursor,
                        fromColumns,
                        toViews,
                        0
                );

                listView.setAdapter(adapter);
            }
        });
    }

    private void clearFields() {
        etRollNo.setText("");
        etName.setText("");
        etMarks.setText("");
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}