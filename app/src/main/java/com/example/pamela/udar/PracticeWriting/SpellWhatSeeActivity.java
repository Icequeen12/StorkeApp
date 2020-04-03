package com.example.pamela.udar.PracticeWriting;

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
import com.example.pamela.udar.PracticeReading.FunctionalReadingActivity;
import com.example.pamela.udar.PracticeReading.InstructionsActivity;
import com.example.pamela.udar.R;
import com.example.pamela.udar.Upload;
import com.example.pamela.udar.extras.PicturesUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SpellWhatSeeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private ImageView imageView;
    private EditText editText;
    private int picture;
    private int number;

    private int turn;
    private int playerResult;

    private ProgressDialog progressDialog;

    private int time;
    private int avrageTime, allTimes;
    private  CountDownTimer timer;

    private List<PicturesUpload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_what_see);

        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.txtResult);

        progressDialog = new ProgressDialog(this);
        uploads = new ArrayList<>();

        playerResult = getIntent().getExtras().getInt("result");
        allTimes = getIntent().getExtras().getInt("time");

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("pictures");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    PicturesUpload upload = postSnapshot.getValue(PicturesUpload.class);
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

        progressDialog.show();
        picture = (int) (Math.random() * number);
        Glide.with(getApplicationContext()).load(uploads.get(picture).getUrl()).into(imageView);
        progressDialog.dismiss();
        timeMesure();
    }

    public void btnCheck_Clicked(View view){

        if(editText.getText().toString().isEmpty()) {
            Toast.makeText(SpellWhatSeeActivity.this, "Udziel odpowiedzi", Toast.LENGTH_SHORT).show();
        }else {
            timer.onFinish();
            timer.cancel();
        String answer = editText.getText().toString();

        if (answer.equals(uploads.get(picture).getName())){
            playerResult = playerResult + 1;}


        if (turn < 4) {
            pictureSet();
            editText.setText("");
        } else {
            avrageTime=avrageTime/turn;
            allTimes=(allTimes+avrageTime)/2;
            Intent i = new Intent(SpellWhatSeeActivity.this, PictureOrderingActivity.class);
            i.putExtra("userName", getIntent().getExtras().getString("userName"));
            i.putExtra("time",allTimes);
            i.putExtra("result",playerResult);
            startActivity(i);
            finish();
        }

        turn = turn + 1;
    }
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

