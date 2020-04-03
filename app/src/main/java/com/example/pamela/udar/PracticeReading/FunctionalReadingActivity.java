package com.example.pamela.udar.PracticeReading;

import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pamela.udar.R;
import com.example.pamela.udar.Results;
import com.example.pamela.udar.extras.FunctionalUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FunctionalReadingActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private int index;
    private int number;
    private int turn;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String id;
    private String path;
    private int picture;
    private ImageView imageView;
    private int playerResult;
    private TextView textView;
    private Button one, two, three;
    private ProgressDialog progressDialog;
    private List<FunctionalUpload> uploads;
    private List<String> list;
    private int time;
    private int avrageTime, allTimes;
    private  CountDownTimer timer;
    private Calendar calendar;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functional_reading);
        progressDialog = new ProgressDialog(this);
        textView = (TextView) findViewById(R.id.task);
        imageView = (ImageView) findViewById(R.id.iv);
        one = (Button) findViewById(R.id.btn1);
        two = (Button) findViewById(R.id.btn2);
        three = (Button) findViewById(R.id.btn3);
        playerResult = getIntent().getExtras().getInt("result");
        allTimes = getIntent().getExtras().getInt("time");

        uploads = new ArrayList<>();
        list = new ArrayList<>();


        calendar= Calendar.getInstance();
        date = DateFormat.getDateInstance().format(calendar.getTime());

        progressDialog.setMessage("Trwa ładowanie...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("functional");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    FunctionalUpload upload = postSnapshot.getValue(FunctionalUpload.class);
                    uploads.add(upload);
                }
                number = uploads.size();

                pictureSet();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

    public void pictureSet() {
        picture = (int) (Math.random() * number);

        textView.setText(uploads.get(picture).task);
        Glide.with(getApplicationContext()).load(uploads.get(picture).url).into(imageView);

        list.add(uploads.get(picture).a);
        list.add(uploads.get(picture).b);
        list.add(uploads.get(picture).c);


        index = (int) (Math.random() * 3);
        one.setText(list.get(index));
        list.remove(index);

        index = (int) (Math.random() * 2);
        two.setText(list.get(index));
        list.remove(index);

        three.setText(list.get(0));
        list.remove(0);

        timeMesure();
    }

    public void btn1_Clicked(View view) {
        check(one.getText().toString());
    }

    public void btn2_Clicked(View view) {
        check(two.getText().toString());
    }

    public void btn3_Clicked(View view) {
        check(three.getText().toString());
    }

    public void check(String text) {
        timer.onFinish();
        timer.cancel();

        if (text.equals(uploads.get(picture).right)) {
            playerResult = playerResult + 1;
        }

        if (turn < 4) {
            pictureSet();
        } else {
            avrageTime = avrageTime / turn;
            allTimes = (allTimes + avrageTime) / 2;
            saveScore();
            finish();
        }

        turn = turn + 1;
    }

    public void saveScore() {

        path = "results/" + getIntent().getExtras().getString("userName") + "/read";
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference(path);

        if (TextUtils.isEmpty(id)) {
            id = mFirebaseDatabase.push().getKey();
        }
        Results result = new Results(playerResult, 10, date);

        mFirebaseDatabase.child(id).setValue(result);
    }

    public void timeMesure() {
        timer = new CountDownTimer(2000000000, 1000) {

            @Override
            public void onTick(long l) {
                time++;
            }

            @Override
            public void onFinish() {

                avrageTime = avrageTime + time;
                time = 0;
            }
        }.start();
    }
}
