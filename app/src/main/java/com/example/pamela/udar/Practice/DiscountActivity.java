package com.example.pamela.udar.Practice;

import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.pamela.udar.R;
import com.example.pamela.udar.Results;
import com.example.pamela.udar.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DiscountActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private Calendar calendar;

    private ImageView imageView;
    private EditText editText;
    private int picture;
    private int number;
    private int turn;
    private int playerResult;
    private String id;
    private String path,date;
    private ProgressDialog progressDialog;
    private List<Upload> uploads;
    private int time;
    private int avrageTime, allTimes;
    private  CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        progressDialog = new ProgressDialog(this);


        calendar= Calendar.getInstance();
        date = DateFormat.getDateInstance().format(calendar.getTime());

        playerResult = getIntent().getExtras().getInt("result");
        allTimes = getIntent().getExtras().getInt("time");

        uploads = new ArrayList<>();

        imageView=(ImageView) findViewById(R.id.imageView);
        editText=(EditText) findViewById(R.id.txtResult);

        progressDialog.setMessage("Trwa ładowanie");
        mDatabase = FirebaseDatabase.getInstance().getReference("sales");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                  number = uploads.size();

                pictureSet();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    public void pictureSet(){

        progressDialog.show();
        picture= (int) (Math.random()*number);
        Glide.with(getApplicationContext()).load(uploads.get(picture).url).into(imageView);
        progressDialog.dismiss();
        timeMesure();

    }


    public void btnCheck_Clicked(View view) {

        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(DiscountActivity.this, "Udziel odpowiedzi", Toast.LENGTH_SHORT).show();
        } else {
            timer.onFinish();
            timer.cancel();
            int answer = Integer.parseInt(editText.getText().toString());
            int ans = uploads.get(picture).answer;

            if (answer == ans) {
                playerResult = playerResult + 1;
            }

            editText.setText("");

            if (turn < 4) {
                pictureSet();
            } else {
                avrageTime=avrageTime/turn;
                allTimes=(allTimes+avrageTime)/2;
                saveScore();
                finish();
            }

            turn = turn + 1;
        }
    }

    public void saveScore(){

        path="results/"+getIntent().getExtras().getString("userName")+"/math";
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference(path);

        if (TextUtils.isEmpty(id)) {
           id = mFirebaseDatabase.push().getKey();
        }

        Results result = new Results(playerResult,allTimes,date);

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
