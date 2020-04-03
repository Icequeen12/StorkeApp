package com.example.pamela.udar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pamela.udar.PracticePictures.PatternActivity;
import com.example.pamela.udar.extras.UsersUpload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText txtEmailAddress;
    private EditText txtPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private String name;
    private String password;

    private int number;
    private List<UsersUpload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmailAddress = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInAnonymously();

        uploads = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UsersUpload upload = postSnapshot.getValue(UsersUpload.class);
                    uploads.add(upload);
                }

                number=uploads.size();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void btnLogin_Clicked(View view){

        if(txtPassword.getText().toString().isEmpty() || txtEmailAddress.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "Podaj dane logowania", Toast.LENGTH_SHORT).show();
        }else {
            name = txtEmailAddress.getText().toString();
            password = txtPassword.getText().toString();

            for (int i = 0; i < number; i++) {
            if (name.equals(uploads.get(i).getNick())){
                if(password.equals(uploads.get(i).getPassword())){
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("userName", name);
                    startActivity(intent);
                    break;
                }
            }
            if(i==number-1){
                Toast.makeText(MainActivity.this, "Złe dane logowania", Toast.LENGTH_SHORT).show();
            }
            }

        }
    }


}
