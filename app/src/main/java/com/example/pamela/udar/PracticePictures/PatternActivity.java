package com.example.pamela.udar.PracticePictures;

import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pamela.udar.Practice.DiscountActivity;
import com.example.pamela.udar.R;
import com.example.pamela.udar.Results;
import com.example.pamela.udar.extras.PatternUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PatternActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private List<PatternUpload> uploads;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private int turn;
    private int playerResult;
    private String id;
    private String path;
    private TextView task;

    private EditText answer;
    private int number;
    private int i;
    private int time;
    private int avrageTime, allTimes;
    private Calendar calendar;
    private  CountDownTimer timer;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);

        task=(TextView) findViewById(R.id.numbers);
        answer=(EditText) findViewById(R.id.answer);
        progressDialog = new ProgressDialog(this);

        playerResult = getIntent().getExtras().getInt("result");
        allTimes = getIntent().getExtras().getInt("time");

        calendar= Calendar.getInstance();
        date = DateFormat.getDateInstance().format(calendar.getTime());

        turn=0;

        uploads = new ArrayList<>();


        progressDialog.setMessage("Trwa ładowanie...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("pattern");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    PatternUpload upload = postSnapshot.getValue(PatternUpload.class);
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


    public void task(){

        i = (int) (Math.random()*number);
        task.setText(uploads.get(i).pattern);
        timeMesure();
    }
    public void btnCheck_Clicked(View view) {

        if (answer.getText().toString().isEmpty()) {
            Toast.makeText(PatternActivity.this, "Udziel odpowiedzi", Toast.LENGTH_SHORT).show();
        } else {
            timer.onFinish();
            timer.cancel();
            int x = Integer.parseInt(answer.getText().toString());
            int ans = uploads.get(i).answer;

            if (x == ans) {
                playerResult = playerResult + 1;
            }

            answer.setText("");

            if (turn < 4) {
                task();
            } else {
                avrageTime = avrageTime / turn;
                allTimes = (allTimes + avrageTime) / 2;
                saveScore();
                finish();

            }

            turn++;

        }
    }
    public void saveScore(){

        path="results/"+getIntent().getExtras().getString("userName")+"/objects";
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference(path);

        if (TextUtils.isEmpty(id)) {
            id = mFirebaseDatabase.push().getKey();
        }

        Results result = new Results(playerResult,10,date);

        mFirebaseDatabase.child(id).setValue(result);

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
