package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView photo;
    TextView textView;
    TextView userName;
    TextView userPhone;
    TextView userEmail;
    TextView userId;
    LottieAnimationView lottieAnimationView;
    Button button;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        photo = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView3);
        userName = findViewById(R.id.username);
        userPhone = findViewById(R.id.userphone);
        userEmail = findViewById(R.id.useremail);
        userId = findViewById(R.id.userid);
        button = findViewById(R.id.signoutbtn);
        lottieAnimationView = findViewById(R.id.lotti);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String umail = firebaseUser.getEmail();

        if (firebaseUser !=null){
                String emaill = firebaseUser.getEmail();
                String phoneNumber = firebaseUser.getPhoneNumber();
                String userIdd = firebaseUser.getUid();
                String userNm = firebaseUser.getPhoneNumber();

                userName.setText(userNm);
                userPhone.setText(phoneNumber);
                userEmail.setText(emaill);
                userId.setText(userIdd);
                userEmail.setOnClickListener(this);
                userId.setOnClickListener(this);

//            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//            userEmail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ClipData clipData = ClipData.newPlainText("cpytxt",userEmail.getText());
//                    clipboardManager.setPrimaryClip(clipData);
//                    Toast.makeText(ProfileActivity.this, "Copied!", Toast.LENGTH_SHORT).show();
//                }
//            });
        }

        //this way you can retrieve data from firebase

//        databaseReference.child("/").child(firebaseUser.getEmail()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                String names = snapshot.child("name").getValue(String.class);
//                Toast.makeText(ProfileActivity.this, ""+names, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        if (firebaseUser == null){
//            Toast.makeText(this, "Somethings wrong hapend", Toast.LENGTH_SHORT).show();
//        }
//        else {
////            String userId=firebaseUser.getUid();
////            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("https://foodapp-50614-default-rtdb.firebaseio.com/");
////
////            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
////                @Override
////                public void onDataChange(@NonNull DataSnapshot snapshot) {
////                    String v = snapshot.toString();
////
////                }
////
////                @Override
////                public void onCancelled(@NonNull DatabaseError error) {
////
////                }
////            });
//        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity2.class));
    }

    @Override
    public void onClick(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        switch (view.getId()){

            case R.id.useremail:
                ClipData clipData = ClipData.newPlainText("cpytxt",userEmail.getText());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(ProfileActivity.this, "Copied!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.userid:ClipData cliData = ClipData.newPlainText("cpytxt",userId.getText());
                clipboardManager.setPrimaryClip(cliData);
                Toast.makeText(ProfileActivity.this, "Copied!", Toast.LENGTH_SHORT).show();
        }
    }
}