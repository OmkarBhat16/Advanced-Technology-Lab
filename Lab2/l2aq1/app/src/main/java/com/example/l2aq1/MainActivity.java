package com.example.l2aq1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView tvDisplay;
    private String currentNumber = "";
    private String operation = "";
    private double firstNumber = 0;
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDisplay = findViewById(R.id.tvDisplay);
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "C":
                clearCalculator();
                break;
            case "DEL":
                deleteLastDigit();
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                setOperation(buttonText);
                break;
            case "=":
                calculateResult();
                break;
            default:
                appendNumber(buttonText);
                break;
        }
    }

    private void appendNumber(String number) {
        if (isNewOperation) {
            currentNumber = "";
            isNewOperation = false;
        }
        currentNumber += number;
        tvDisplay.setText(currentNumber);
    }

    private void setOperation(String op) {
        firstNumber = Double.parseDouble(currentNumber);
        operation = op;
        isNewOperation = true;
    }

    private void calculateResult() {
        if (operation.isEmpty()) return;

        double secondNumber = Double.parseDouble(currentNumber);
        double result = 0;

        switch (operation) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    tvDisplay.setText("Error");
                    return;
                }
                break;
        }

        // Remove decimal if result is whole number
        if (result == (int) result) {
            currentNumber = String.valueOf((int) result);
        } else {
            currentNumber = String.valueOf(result);
        }

        tvDisplay.setText(currentNumber);
        isNewOperation = true;
        operation = "";
    }

    private void deleteLastDigit() {
        if (currentNumber.length() > 0) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
            tvDisplay.setText(currentNumber);
        }
    }

    private void clearCalculator() {
        currentNumber = "";
        firstNumber = 0;
        operation = "";
        isNewOperation = true;
        tvDisplay.setText("0");
    }
}