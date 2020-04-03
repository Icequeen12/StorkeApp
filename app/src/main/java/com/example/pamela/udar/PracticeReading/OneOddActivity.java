package com.example.pamela.udar.PracticeReading;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pamela.udar.MenuActivity;
import com.example.pamela.udar.Practice.CurrencyActivity;
import com.example.pamela.udar.Practice.DiscountActivity;
import com.example.pamela.udar.R;
import com.example.pamela.udar.Upload;
import com.example.pamela.udar.extras.OneOddUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneOddActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private int i;
    private int index;
    private int number;
    private Button one, two, three, four;
    private TextView txt;
    private String clickedAnswer;
    private String rightAnswer;
    private int turn;
    private int playerResult;
    private ProgressDialog progressDialog;
    private List<OneOddUpload> uploads;
    private List<String> list;
    private int time;
    private int avrageTime;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_odd);

        progressDialog = new ProgressDialog(this);

        one = (Button) findViewById(R.id.button1);
        two = (Button) findViewById(R.id.button2);
        three = (Button) findViewById(R.id.button3);
        four = (Button) findViewById(R.id.button4);
        txt = (TextView) findViewById(R.id.txt);
        i = 0;
        uploads = new ArrayList<>();
        list = new ArrayList<>();

        progressDialog.setMessage("Trwa ładowanie");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("oneOdd");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    OneOddUpload upload = postSnapshot.getValue(OneOddUpload.class);
                    uploads.add(upload);
                }
                number = uploads.size();


                task();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }


    public void task() {

        i = (int) (Math.random() * number);
        list.add(uploads.get(i).first);
        list.add(uploads.get(i).second);
        list.add(uploads.get(i).third);
        list.add(uploads.get(i).fourth);
        rightAnswer = uploads.get(i).right;

        index = (int) (Math.random() * 4);
        one.setText(list.get(index));
        list.remove(index);

        index = (int) (Math.random() * 3);
        two.setText(list.get(index));
        list.remove(index);

        index = (int) (Math.random() * 2);
        three.setText(list.get(index));
        list.remove(index);

        four.setText(list.get(0));

        timeMesure();
    }

    public void btnClicked1(View view) {

        clickedAnswer = one.getText().toString();
        check(clickedAnswer);
    }

    public void btnClicked2(View view) {

        clickedAnswer = two.getText().toString();
        check(clickedAnswer);

    }

    public void btnClicked3(View view) {

        clickedAnswer = three.getText().toString();
        check(clickedAnswer);

    }

    public void btnClicked4(View view) {

        clickedAnswer = four.getText().toString();
        check(clickedAnswer);

    }

    public void check(String clickedAnswer) {

        timer.onFinish();
        timer.cancel();

        if (rightAnswer.equals(clickedAnswer)) {
            playerResult = playerResult + 1;}

        list.clear();

        if (turn < 4) {
            task();
        } else {
            Intent i = new Intent(OneOddActivity.this, InstructionsActivity.class);
            i.putExtra("userName", getIntent().getExtras().getString("userName"));
            i.putExtra("result",playerResult);
            i.putExtra("time",avrageTime);
            startActivity(i);
            finish();
        }

        turn = turn + 1;
    }

    public void timeMesure(){
        timer =new CountDownTimer(2000000000,1000){

            @Override
            public void onTick(long l) {
                time++;
            }

            @Override
            public void onFinish() {

                avrageTime=avrageTime+time;
                time=0;
            }
        }.start();
    }

}
