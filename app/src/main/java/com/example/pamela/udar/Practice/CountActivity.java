package com.example.pamela.udar.Practice;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pamela.udar.R;

public class CountActivity extends AppCompatActivity {

    private TextView num1;
    private TextView num2;
    private TextView sign;
    private int result;
    private int number1;
    private int number2;
    private int number3;
    private  CountDownTimer timer;
    private int turn;
    private int playerResult;
    private EditText txtResult;
    private int time;
    private int averageTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        playerResult = 0;
        turn = 0;
        num1 = (TextView) findViewById(R.id.num1);
        num2 = (TextView) findViewById(R.id.num2);
        sign = (TextView) findViewById(R.id.add);
        txtResult = (EditText) findViewById(R.id.txtResult);

        numbersLottery();
    }

    public void btnCheck_Clicked(View view) {

        if (txtResult.getText().toString().isEmpty()) {
            Toast.makeText(this, "Udziel odpowiedzi ", Toast.LENGTH_SHORT).show();
        } else {
            timer.onFinish();
            timer.cancel();
            int answer = Integer.parseInt(txtResult.getText().toString());

            if (answer == result) {
                playerResult = playerResult + 1;
            }

            txtResult.setText("");

            if (turn < 4) {
                numbersLottery();
            } else {
                averageTime = averageTime /turn;
                Intent i = new Intent(CountActivity.this, CurrencyActivity.class);
                i.putExtra("userName", getIntent().getExtras().getString("userName"));
                i.putExtra("result",playerResult);
                i.putExtra("time", averageTime);
                startActivity(i);
                finish();
            }

            turn = turn + 1;
        }
    }

    public void numbersLottery() {

        result = 0;
        number1 = (int) (Math.random() * 30);
        number2 = (int) (Math.random() * 30);
        number3 = (int) (Math.random() * 4);

        num1.setText(String.valueOf(number1));
        num2.setText(String.valueOf(number2));
        sign.setText(String.valueOf(number3));

        if (number3 == 0) {
            sign.setText("+");
            result = number1 + number2;
        } else if (number3 == 1) {
            if (number2 > number1) {
                num1.setText(String.valueOf(number2));
                num2.setText(String.valueOf(number1));
                result = number2 - number1;
            } else {
                result = number1 - number2;
            }
            sign.setText("-");
        } else if (number3==2){

            if(number1%number2==0){
                result = number1/number2;
                sign.setText(":");
            }else{
                numbersLottery();
            }
        }else {
            sign.setText("x");
            if (number1 > 10) {
                number1 = (int) (Math.random() * 10);
                num1.setText(String.valueOf(number1));

            }
            if (number2 > 10) {
                number2 = (int) (Math.random() * 10);
                num2.setText(String.valueOf(number2));
            }

            result = number1 * number2;
        }

        timeMesure();
    }


    public void timeMesure(){
         timer =new CountDownTimer(2000000000,1000){

            @Override
            public void onTick(long l) {
                time++;
            }

            @Override
            public void onFinish() {

                averageTime = averageTime +time;
                time=0;
            }
        }.start();
    }


}
