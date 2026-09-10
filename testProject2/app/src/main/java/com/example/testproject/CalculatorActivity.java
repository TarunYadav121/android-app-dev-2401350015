package com.example.testproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalculatorActivity extends AppCompatActivity {

    private TextView resultTv;
    private double firstValue = Double.NaN;
    private double secondValue;
    private String currentOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultTv = findViewById(R.id.result_tv);

        setNumericOnClickListener();
        setOperatorOnClickListener();
    }

    private void setNumericOnClickListener() {
        View.OnClickListener listener = v -> {
            Button button = (Button) v;
            if (resultTv.getText().toString().equals(getString(R.string.digit_0))) {
                resultTv.setText(button.getText());
            } else {
                resultTv.append(button.getText());
            }
        };

        findViewById(R.id.btn_0).setOnClickListener(listener);
        findViewById(R.id.btn_1).setOnClickListener(listener);
        findViewById(R.id.btn_2).setOnClickListener(listener);
        findViewById(R.id.btn_3).setOnClickListener(listener);
        findViewById(R.id.btn_4).setOnClickListener(listener);
        findViewById(R.id.btn_5).setOnClickListener(listener);
        findViewById(R.id.btn_6).setOnClickListener(listener);
        findViewById(R.id.btn_7).setOnClickListener(listener);
        findViewById(R.id.btn_8).setOnClickListener(listener);
        findViewById(R.id.btn_9).setOnClickListener(listener);
    }

    private void setOperatorOnClickListener() {
        View.OnClickListener operatorListener = v -> {
            Button b = (Button) v;
            try {
                firstValue = Double.parseDouble(resultTv.getText().toString());
                currentOperator = b.getText().toString();
                resultTv.setText(R.string.digit_0);
            } catch (NumberFormatException e) {
                // Ignore or show error
            }
        };

        findViewById(R.id.btn_plus).setOnClickListener(operatorListener);
        findViewById(R.id.btn_minus).setOnClickListener(operatorListener);
        findViewById(R.id.btn_multiply).setOnClickListener(operatorListener);
        findViewById(R.id.btn_divide).setOnClickListener(operatorListener);

        findViewById(R.id.btn_equals).setOnClickListener(v -> {
            if (currentOperator != null && !Double.isNaN(firstValue)) {
                try {
                    secondValue = Double.parseDouble(resultTv.getText().toString());
                    double result;
                    switch (currentOperator) {
                        case "+": result = firstValue + secondValue; break;
                        case "-": result = firstValue - secondValue; break;
                        case "*": result = firstValue * secondValue; break;
                        case "/": 
                            if (secondValue != 0) {
                                result = firstValue / secondValue;
                            } else {
                                resultTv.setText(R.string.error);
                                return;
                            }
                            break;
                        default:
                            result = 0;
                    }
                    resultTv.setText(String.valueOf(result));
                    currentOperator = null;
                    firstValue = result; // Allow chaining
                } catch (NumberFormatException e) {
                    // Ignore
                }
            }
        });

        findViewById(R.id.btn_clear).setOnClickListener(v -> {
            resultTv.setText(R.string.digit_0);
            firstValue = Double.NaN;
            currentOperator = null;
        });
    }
}
