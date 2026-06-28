package com.Ridwan.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private double firstNumber = 0;
    private String currentOperator = "";
    private String currentExpression = "";
    private boolean isNewNumber = true;
    private boolean isDegreeMode = true;
    private StringBuilder history = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        setupNumberButtons();
        setupOperatorButtons();
        setupScientificButtons();
        setupFunctionButtons();
        setupModeToggle();
        setupHistoryButton();
    }

    private void setupNumberButtons() {
        int[] ids = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};
        for (int id : ids) {
            Button btn = findViewById(id);
            btn.setOnClickListener(v -> {
                Button b = (Button) v;
                if (isNewNumber) {
                    currentExpression = b.getText().toString();
                    tvDisplay.setText(currentExpression);
                    isNewNumber = false;
                } else {
                    currentExpression += b.getText().toString();
                    tvDisplay.setText(currentExpression);
                }
            });
        }

        Button btnDecimal = findViewById(R.id.btnDecimal);
        btnDecimal.setOnClickListener(v -> {
            if (!tvDisplay.getText().toString().contains(".")) {
                tvDisplay.append(".");
                currentExpression += ".";
            }
        });
    }

    private void setupOperatorButtons() {
        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> setOperator("×"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> setOperator("÷"));
    }

    private void setOperator(String op) {
        firstNumber = Double.parseDouble(tvDisplay.getText().toString());
        currentOperator = op;
        currentExpression = firstNumber + " " + op + " ";
        tvDisplay.setText(currentExpression);
        isNewNumber = true;
    }

    private void setupScientificButtons() {
        findViewById(R.id.btnSin).setOnClickListener(v -> applyScientific("sin"));
        findViewById(R.id.btnCos).setOnClickListener(v -> applyScientific("cos"));
        findViewById(R.id.btnTan).setOnClickListener(v -> applyScientific("tan"));
        findViewById(R.id.btnLog).setOnClickListener(v -> applyScientific("log"));
        findViewById(R.id.btnLn).setOnClickListener(v -> applyScientific("ln"));
        findViewById(R.id.btnExp).setOnClickListener(v -> applyScientific("exp"));
        findViewById(R.id.btnSqrt).setOnClickListener(v -> applyScientific("sqrt"));
        findViewById(R.id.btnSquare).setOnClickListener(v -> applyScientific("square"));
        findViewById(R.id.btnCube).setOnClickListener(v -> applyScientific("cube"));
        findViewById(R.id.btnCubeRoot).setOnClickListener(v -> applyScientific("cuberoot"));
        findViewById(R.id.btnFactorial).setOnClickListener(v -> applyScientific("factorial"));
        findViewById(R.id.btnPi).setOnClickListener(v -> applyScientific("pi"));
    }

    private void applyScientific(String function) {
        double value = Double.parseDouble(tvDisplay.getText().toString());
        double result = 0;
        String calc = "";

        switch (function) {
            case "sin":
                result = isDegreeMode ? Math.sin(Math.toRadians(value)) : Math.sin(value);
                calc = "sin(" + value + ") = " + result;
                break;
            case "cos":
                result = isDegreeMode ? Math.cos(Math.toRadians(value)) : Math.cos(value);
                calc = "cos(" + value + ") = " + result;
                break;
            case "tan":
                result = isDegreeMode ? Math.tan(Math.toRadians(value)) : Math.tan(value);
                calc = "tan(" + value + ") = " + result;
                break;
            case "log":
                if (value > 0) { result = Math.log10(value); calc = "log(" + value + ") = " + result; }
                else { tvDisplay.setText("Error"); return; }
                break;
            case "ln":
                if (value > 0) { result = Math.log(value); calc = "ln(" + value + ") = " + result; }
                else { tvDisplay.setText("Error"); return; }
                break;
            case "exp":
                result = Math.exp(value);
                calc = "e^" + value + " = " + result;
                break;
            case "sqrt":
                if (value >= 0) { result = Math.sqrt(value); calc = "√" + value + " = " + result; }
                else { tvDisplay.setText("Error"); return; }
                break;
            case "square":
                result = value * value;
                calc = value + "² = " + result;
                break;
            case "cube":
                result = value * value * value;
                calc = value + "³ = " + result;
                break;
            case "cuberoot":
                if (value >= 0) { result = Math.cbrt(value); calc = "∛" + value + " = " + result; }
                else { tvDisplay.setText("Error"); return; }
                break;
            case "factorial":
                if (value >= 0 && value == Math.floor(value) && value <= 170) {
                    result = factorial((int) value);
                    calc = (int) value + "! = " + result;
                } else { tvDisplay.setText("Error"); return; }
                break;
            case "pi":
                result = Math.PI;
                calc = "π = " + result;
                break;
        }

        tvDisplay.setText(calc);
        addToHistory(calc);
        isNewNumber = true;
        currentExpression = "";
    }

    private double factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    private void setupFunctionButtons() {
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            tvDisplay.setText("0");
            firstNumber = 0;
            currentOperator = "";
            currentExpression = "";
            isNewNumber = true;
        });
    }

    private void calculateResult() {
        double secondNumber = Double.parseDouble(tvDisplay.getText().toString());
        double result = 0;
        String calc = "";

        switch (currentOperator) {
            case "+": result = firstNumber + secondNumber; calc = firstNumber + " + " + secondNumber + " = " + result; break;
            case "-": result = firstNumber - secondNumber; calc = firstNumber + " - " + secondNumber + " = " + result; break;
            case "×": result = firstNumber * secondNumber; calc = firstNumber + " × " + secondNumber + " = " + result; break;
            case "÷":
                if (secondNumber != 0) { result = firstNumber / secondNumber; calc = firstNumber + " ÷ " + secondNumber + " = " + result; }
                else { tvDisplay.setText("Error: ÷ by 0"); return; }
                break;
            default: return;
        }

        tvDisplay.setText(calc);
        addToHistory(calc);
        isNewNumber = true;
        currentExpression = "";
    }

    private void setupModeToggle() {
        Button btn = findViewById(R.id.btnModeToggle);
        btn.setOnClickListener(v -> {
            isDegreeMode = !isDegreeMode;
            btn.setText(isDegreeMode ? "DEG" : "RAD");
            tvDisplay.setText(isDegreeMode ? "DEG Mode" : "RAD Mode");
            isNewNumber = true;
        });
    }

    private void setupHistoryButton() {
        findViewById(R.id.btnHistory).setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            intent.putExtra("history", history.toString());
            startActivity(intent);
        });
    }

    private void addToHistory(String entry) {
        if (history.length() > 0) history.append("\n");
        history.append(entry);
    }
}