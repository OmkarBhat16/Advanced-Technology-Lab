package com.example.l9q1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private SurveyDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new SurveyDbHelper(this);

        Button submitButton = findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(v -> submitSurvey());
    }

    private void submitSurvey() {
        // Get survey responses
        String source = "";
        RadioGroup sourceGroup = findViewById(R.id.radioGroup);
        int selectedId = sourceGroup.getCheckedRadioButtonId();

        if(selectedId == R.id.radioFriend) source = "Friend";
        else if(selectedId == R.id.radioOnline) source = "Online";
        else if(selectedId == R.id.radioOther) source = "Other";

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        float rating = ratingBar.getRating();

        EditText comments = findViewById(R.id.etComments);
        String commentsText = comments.getText().toString();

        // Save to database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SurveyContract.SurveyEntry.COLUMN_SOURCE, source);
        values.put(SurveyContract.SurveyEntry.COLUMN_RATING, rating);
        values.put(SurveyContract.SurveyEntry.COLUMN_COMMENTS, commentsText);

        long newRowId = db.insert(SurveyContract.SurveyEntry.TABLE_NAME, null, values);

        if(newRowId != -1) {
            Toast.makeText(this, "Survey submitted!", Toast.LENGTH_SHORT).show();
            clearForm();
        } else {
            Toast.makeText(this, "Submission failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearForm() {
        RadioGroup sourceGroup = findViewById(R.id.radioGroup);
        sourceGroup.clearCheck();

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(0);

        EditText comments = findViewById(R.id.etComments);
        comments.setText("");
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}