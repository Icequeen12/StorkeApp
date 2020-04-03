 package com.example.pamela.udar.PracticeWriting;

import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.pamela.udar.DragAndDrop.DragDropAdapter;
import com.example.pamela.udar.DragAndDrop.DragDropListView;
import com.example.pamela.udar.DragAndDrop.ListItem;
import com.example.pamela.udar.R;
import com.example.pamela.udar.Results;
import com.example.pamela.udar.extras.PicturesOrderUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PictureOrderingActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private DragDropListView listView;
    private DragDropAdapter adapter;

    private int opction;
    private int number;
    private int turn;
    private int index;
    private String id;
    private String path;
    private int playerResult;

    private Calendar calendar;
    private int time;
    private int avrageTime, allTimes;
    private CountDownTimer timer;
    private String date;

    private ProgressDialog progressDialog;

    private List<PicturesOrderUpload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_ordering);
        listView = (DragDropListView) findViewById(R.id.lv);

        calendar= Calendar.getInstance();
        date = DateFormat.getDateInstance().format(calendar.getTime());

        playerResult = getIntent().getExtras().getInt("result");
        allTimes = getIntent().getExtras().getInt("time");

        progressDialog = new ProgressDialog(this);
        uploads = new ArrayList<>();

        progressDialog.setMessage("Trwa ładowanie ...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("ordering");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    PicturesOrderUpload upload = postSnapshot.getValue(PicturesOrderUpload.class);
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

        String text = text1+text2+text3+text4;

        String answer = uploads.get(opction).getRight();

        if (text.equals(answer)){
            playerResult = playerResult + 1;}


        if (turn < 4) {
            updateList();
        } else {
            avrageTime=avrageTime/turn;
            allTimes=(allTimes+avrageTime)/2;
            saveScore();
            finish();
        }

        turn = turn + 1;
    }

    private void updateList() {

        List<ListItem> listItems = new ArrayList<>();
        List<ListItem> list = new ArrayList<>();

        opction = (int) (Math.random()*number);
        list.add(new ListItem(uploads.get(opction).getName1(),uploads.get(opction).getUrl1()));
        list.add(new ListItem(uploads.get(opction).getName2(),uploads.get(opction).getUrl2()));
        list.add(new ListItem(uploads.get(opction).getName3(),uploads.get(opction).getUrl3()));
        list.add(new ListItem(uploads.get(opction).getName4(),uploads.get(opction).getUrl4()));


        for(int i=4; i>0; i--) {
            index = (int) (Math.random()*i);
            listItems.add(list.get(index));
            list.remove(index);
        }

        adapter = new DragDropAdapter(this, listItems);
        adapter.setNumber(2);
        listView.setAdapter(adapter);

        timeMesure();
    }

    public void saveScore(){

        path="results/"+getIntent().getExtras().getString("userName")+"/write";
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
