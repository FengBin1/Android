package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {
    TextView resultTextView;
    TextView resultTextView1;
    StringBuilder expression = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);
        resultTextView1 = findViewById(R.id.resultTextView1);

        Button[] numberButtons = new Button[]{
                findViewById(R.id.button0), findViewById(R.id.button1), findViewById(R.id.button2),
                findViewById(R.id.button3), findViewById(R.id.button4), findViewById(R.id.button5),
                findViewById(R.id.button6), findViewById(R.id.button7), findViewById(R.id.button8),
                findViewById(R.id.button9)
        };

        Button buttonClear = findViewById(R.id.buttonClear);
        Button buttonDecimal = findViewById(R.id.buttonDecimal);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonEqual = findViewById(R.id.buttonEqual);
        Button buttonDelete = findViewById(R.id.buttonDelete); // 新增

        for (Button button : numberButtons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = ((Button) v).getText().toString();
                    expression.append(number);
                    resultTextView.setText(expression.toString());
                }
            });
        }

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression.setLength(0);
                resultTextView.setText("");
                resultTextView1.setText("");
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expressionString = expression.toString();
                double result = evaluateExpression(expressionString);
                String formattedResult;
                if (isInteger(result)) {
                    formattedResult = String.valueOf((int) result);
                } else {
                    formattedResult = String.valueOf(result);
                }
                resultTextView1.setText(expressionString + " = " + formattedResult);
                resultTextView.setText(formattedResult);
                expression.setLength(0);
                expression.append(formattedResult);
            }
        });

        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String operator = ((Button) v).getText().toString();
                expression.append(operator);
                resultTextView.setText(expression.toString());
            }
        };

        buttonAdd.setOnClickListener(operatorClickListener);
        buttonSubtract.setOnClickListener(operatorClickListener);
        buttonMultiply.setOnClickListener(operatorClickListener);
        buttonDivide.setOnClickListener(operatorClickListener);

        buttonDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression.append(".");
                resultTextView.setText(expression.toString());
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() { // 新增
            @Override
            public void onClick(View v) {
                if (expression.length() > 0) {
                    expression.deleteCharAt(expression.length() - 1);
                    resultTextView.setText(expression.toString());
                }
            }
        });
    }

    private double evaluateExpression(String expression) {
        Deque<Character> operators = new ArrayDeque<>();
        Deque<Double> operands = new ArrayDeque<>();
        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                StringBuilder operand = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operand.append(expression.charAt(i));
                    i++;
                }
                operands.push(Double.parseDouble(operand.toString()));
            } else if (c == '+' || c == '-' || c == '×' || c == '÷') {
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    processOperator(operands, operators);
                }
                operators.push(c);
                i++;
            } else {
                i++;
            }
        }
        while (!operators.isEmpty()) {
            processOperator(operands, operators);
        }
        return operands.pop();
    }

    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '×':
            case '÷':
                return 2;
            default:
                return -1;
        }
    }

    private void processOperator(Deque<Double> operands, Deque<Character> operators) {
        char operator = operators.pop();
        double operand2 = operands.pop();
        double operand1 = operands.pop();
        double result = 0;
        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '×':
                result = operand1 * operand2;
                break;
            case '÷':
                result = operand1 / operand2;
                break;
        }
        operands.push(result);
    }

    private boolean isInteger(double number) {
        return Math.floor(number) == number;
    }
}
