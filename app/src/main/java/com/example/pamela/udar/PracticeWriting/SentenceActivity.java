package com.example.pamela.udar.PracticeWriting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pamela.udar.R;
import com.example.pamela.udar.extras.InstructionsUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SentenceActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;


    private int number;
    private int index,i;

    private int turn;
    private int playerResult;
    private  CountDownTimer timer;

    private ProgressDialog progressDialog;
    private TextView textView;
    private Button one,two,three;
    private int time;
    private int averageTime;

    private List<InstructionsUpload> uploads;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence);

        textView = (TextView) findViewById(R.id.tv);
        one = (Button) findViewById(R.id.btn1);
        two = (Button) findViewById(R.id.btn2);
        three= (Button) findViewById(R.id.btn3);

        progressDialog = new ProgressDialog(this);
        uploads = new ArrayList<>();
        list = new ArrayList<>();


        progressDialog.setMessage("Trwa ładowanie...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("sentence");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    InstructionsUpload upload = postSnapshot.getValue(InstructionsUpload.class);
                    uploads.add(upload);
                }
                number = uploads.size();

               sentenceSet();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    public void sentenceSet(){
        i = (int) (Math.random()*number);
        textView.setText(uploads.get(i).getQuote1());

        list.add(uploads.get(i).getQuote2());
        list.add(uploads.get(i).getQuote3());
        list.add(uploads.get(i).getQuote4());

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

    public void check(String answer){

        timer.onFinish();
        timer.cancel();

        if (answer.equals(uploads.get(i).getRight())){
            playerResult = playerResult + 1;}


        if (turn < 4) {
            sentenceSet();
        } else {
            averageTime = averageTime /turn;
            Intent i = new Intent(SentenceActivity.this, SpellWhatSeeActivity.class);
            i.putExtra("userName", getIntent().getExtras().getString("userName"));
            i.putExtra("time", averageTime);
            i.putExtra("result",playerResult);
            startActivity(i);
            finish();
        }

        turn = turn + 1;
    }

    public void btnOne_Clicked(View view){
        check(one.getText().toString());
    }

    public void btnTwo_Clicked(View view){
        check(two.getText().toString());
    }

    public void btnThree_Clicked(View view){
        check(three.getText().toString());
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
