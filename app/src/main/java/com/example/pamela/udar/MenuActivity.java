package com.example.pamela.udar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pamela.udar.Practice.CountActivity;
import com.example.pamela.udar.PracticePictures.RotactionActivity;
import com.example.pamela.udar.PracticeReading.OneOddActivity;
import com.example.pamela.udar.PracticeWriting.PictureOrderingActivity;
import com.example.pamela.udar.PracticeWriting.SentenceActivity;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


    }


    public void btnWriting_Clicked(View v) {
        Intent i = new Intent(MenuActivity.this, SentenceActivity.class);
        i.putExtra("userName", getIntent().getExtras().getString("userName"));
        startActivity(i);
    }

    public void btnRead_Clicked(View v) {
        Intent i = new Intent(MenuActivity.this, OneOddActivity .class);
        i.putExtra("userName", getIntent().getExtras().getString("userName"));
        startActivity(i);
    }


    public void btnPic_Clicked(View v) {
        Intent i = new Intent(MenuActivity.this, RotactionActivity.class);
        i.putExtra("userName", getIntent().getExtras().getString("userName"));
        startActivity(i);
    }

    public void btnCal_Clicked(View v) {
        Intent i = new Intent(MenuActivity.this, CountActivity.class);
        i.putExtra("userName", getIntent().getExtras().getString("userName"));
        startActivity(i);
    }
}
