package com.example.l2q2;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etInput;
    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.etInput);
        tvOutput = findViewById(R.id.tvOutput);
        Button btnEncrypt = findViewById(R.id.btnEncrypt);

        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = etInput.getText().toString().trim();

                if (inputText.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Please enter some text!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String encryptedText = caesarCipherEncrypt(inputText, 3);
                tvOutput.setText(encryptedText);
            }
        });
    }

    private String caesarCipherEncrypt(String input, int shift) {
        StringBuilder result = new StringBuilder();

        for (char character : input.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                char encryptedChar = (char) ((character - base + shift) % 26 + base);
                result.append(encryptedChar);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
}