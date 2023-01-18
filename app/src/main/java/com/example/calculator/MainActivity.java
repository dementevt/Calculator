package com.example.calculator;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calculator.R;


public class MainActivity extends AppCompatActivity {
    private Button btnComma;
    private Button btnClear;
    private ImageButton btnDivide;
    private ImageButton btnMultiply;
    private ImageButton btnSubtract;
    private ImageButton btnAddition;
    private ImageButton btnEquals;
    private TextView resultView;
    private Button[] buttons = new Button[10];
    private float oldNumber;
    private char operation = '0';


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        initFields();

        Resources res = getResources();

        for(int i = 0; i < buttons.length; i++){
            String id = "btn" + i;

            buttons[i] = (Button) findViewById(res.getIdentifier(id, "id", getPackageName()));
        }

        for (int i = 0; i < buttons.length; i++){
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeViewText(view.getResources().getResourceName(view.getId()));
                }
            });
        }

        btnComma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = resultView.getText().toString();
                if( ! result.contains(".")){
                    resultView.append(".");
                }
            }
        });

        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equalsMethod(true);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                resultView.setText("0");
                operation = '0';
                oldNumber = 0;
                changeButtonState(true);
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                changeOperation('/');
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOperation('*');
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOperation('-');
            }
        });

        btnAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeOperation('+');
            }
        });

    }

    private void initFields(){
        btnComma = (Button) findViewById(R.id.btnComma);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnDivide = (ImageButton) findViewById(R.id.btnDivide);
        btnMultiply = (ImageButton) findViewById(R.id.btnMultiply);
        btnSubtract = (ImageButton) findViewById(R.id.btnSubtract);
        btnAddition = (ImageButton) findViewById(R.id.btnAddition);
        btnEquals = (ImageButton) findViewById(R.id.btnEquals);
        resultView = (TextView) findViewById(R.id.resultView);
        oldNumber = 0;
    }

    private void changeOperation(char execute){
        if(operation == '0'){
            operation = execute;
            oldNumber = Float.parseFloat(resultView.getText().toString());
            resultView.setText("0");
        } else {
            equalsMethod(false);
            operation = execute;
        }

        if( ! btnComma.isEnabled()){
            changeButtonState(true);
        }
    }

    private void changeViewText(String id){
        float total = Float.parseFloat(resultView.getText().toString());
        id = String.valueOf(id.charAt(id.length() - 1));

        appeandTextResult(id, total);
    }

    private void appeandTextResult(String id, float number){
        if (number == 0) {
            resultView.setText(id);
        } else {
            resultView.append(id);
        }
    }

    public float divide(float x, float y){
        return x / y;
    }

    public float mulitply(float x, float y){
        return x * y;
    }

    public float subtract(float x, float y){
        return x - y;
    }

    public float addition(float x, float y){
        return x + y;
    }

    public void equalsMethod(boolean isFinal){
        if (operation == '0') return;

        float current = Float.parseFloat(resultView.getText().toString());

        if(current == 0) return;

        oldNumber = getResult(current);

        if (isFinal){
            resultView.setText(String.valueOf(oldNumber));
            operation = '0';
            changeButtonState(false);
        } else {
            resultView.setText(String.valueOf("0"));
        }
    }

    public float getResult(float current){
        switch(operation){
            case '*':
                return mulitply(current, oldNumber);
            case '/':
                return divide(oldNumber, current);
            case '-':
                return subtract(oldNumber, current);
            case '+':
                return addition(current, oldNumber);
            default:
                return oldNumber;
        }
    }

    private void changeButtonState(boolean stateChange){
        for (int i = 0; i < buttons.length; i++){
            buttons[i].setEnabled(stateChange);
        }

        btnComma.setEnabled(stateChange);
    }
}
