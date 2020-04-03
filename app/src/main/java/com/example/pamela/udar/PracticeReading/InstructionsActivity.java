package com.example.pamela.udar.PracticeReading;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pamela.udar.DragAndDrop.DragDropAdapter;
import com.example.pamela.udar.DragAndDrop.DragDropListView;
import com.example.pamela.udar.DragAndDrop.ListItem;
import com.example.pamela.udar.R;
import com.example.pamela.udar.extras.InstructionsUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InstructionsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private int opction;
    private int number;
    private DragDropListView listView;
    private DragDropAdapter adapter;
    private int turn;
    private int index;
    private int playerResult;
    private ProgressDialog progressDialog;
    private List<InstructionsUpload> uploads;
    private List<String> list;
    private String dot;
    private int time;
    private int averageTime, allTimes;
    private  CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        progressDialog = new ProgressDialog(this);
        listView = (DragDropListView) findViewById(R.id.lv);

        allTimes = getIntent().getExtras().getInt("time");
        playerResult = getIntent().getExtras().getInt("result");

        uploads = new ArrayList<>();
        list =new ArrayList<>();
        index =0;

        dot="https://firebasestorage.googleapis.com/v0/b/bazadanych-da28d.appspot.com/o/dot.png?alt=media&token=95a6eb10-c0cb-4350-9af0-a98d2ae0c154";

        progressDialog.setMessage("Trwa ładowanie...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("instructions");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    InstructionsUpload upload = postSnapshot.getValue(InstructionsUpload.class);
                    uploads.add(upload);
                }
                number = uploads.size();

                updateList();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

    public void btnCheck_Clicked(View view){

        timer.onFinish();
        timer.cancel();

        String text1 = adapter.getItem(0).getNameRes();
        String text2 = adapter.getItem(1).getNameRes();
        String text3 = adapter.getItem(2).getNameRes();
        String text4 = adapter.getItem(3).getNameRes();

        String text = text1+" "+text2+" "+text3+" "+text4;
        String answer = uploads.get(opction).getRight();

        if (text.equals(answer)){
            playerResult = playerResult + 1;}

        if (turn < 4) {
            updateList();
        } else {
            averageTime = averageTime /turn;
            allTimes=(allTimes+ averageTime)/2;
            Intent i = new Intent(InstructionsActivity.this, FunctionalReadingActivity.class);
            i.putExtra("userName", getIntent().getExtras().getString("userName"));
            i.putExtra("time",allTimes);
            i.putExtra("result",playerResult);
            startActivity(i);
            finish();
        }

        turn = turn + 1;
    }

    private void updateList() {

        List<ListItem> listItems = new ArrayList<>();

        opction = (int) (Math.random()*number);
        list.add(uploads.get(opction).getQuote1());
        list.add(uploads.get(opction).getQuote2());
        list.add(uploads.get(opction).getQuote3());
        list.add(uploads.get(opction).getQuote4());

        for(int i=4; i>0; i--) {
            index = (int) (Math.random()*i);
            listItems.add(new ListItem(list.get(index),dot));
            list.remove(index);
        }


        adapter = new DragDropAdapter(this, listItems);
        listView.setAdapter(adapter);

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
