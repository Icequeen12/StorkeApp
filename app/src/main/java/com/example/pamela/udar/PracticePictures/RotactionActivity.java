package com.example.pamela.udar.PracticePictures;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pamela.udar.MenuActivity;
import com.example.pamela.udar.Practice.CurrencyActivity;
import com.example.pamela.udar.Practice.DiscountActivity;
import com.example.pamela.udar.R;
import com.example.pamela.udar.Upload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RotactionActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private ImageView imageView;
    private int picture;
    private int number;

    private int turn;
    private int playerResult;

    private ProgressDialog progressDialog;

    private int time;
    private int avrageTime;
    private  CountDownTimer timer;

    private List<Upload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotaction);

        imageView = (ImageView) findViewById(R.id.picture);
        progressDialog = new ProgressDialog(this);
        playerResult=0;
        turn=0;
        uploads = new ArrayList<>();

        progressDialog.setMessage("Trwa ładowanie ...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("figures");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
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

    public void pictureSet(){

        picture= (int) (Math.random()*number);
        Glide.with(getApplicationContext()).load(uploads.get(picture).url).into(imageView);

        timeMesure();
    }

    public void next(){

        timer.onFinish();
        timer.cancel();

        if(turn<4){
            pictureSet();
        }
        else {
            avrageTime=avrageTime/turn;
            Intent i = new Intent(RotactionActivity.this, ArrowActivity.class);
            i.putExtra("userName", getIntent().getExtras().getString("userName"));
            i.putExtra("result",playerResult);
            i.putExtra("result",playerResult);
            startActivity(i);
            finish();
        }

        turn=turn+1;
    }

    public void btnYes_Clicked(View view){

        if (uploads.get(picture).answer==1){
            playerResult=playerResult+1;
        }
        next();

    }

    public void btnNo_Clicked(View view){

        if (uploads.get(picture).answer==0){
            playerResult=playerResult+1;
        }
        next();
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
