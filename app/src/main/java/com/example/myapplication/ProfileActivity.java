package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    ImageView photo;
    TextView textView;
    TextView userName;
    TextView userPhone;
    TextView userEmail;
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
        firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//        if (firebaseUser == null){
//            Toast.makeText(this, "Somethings rong happend", Toast.LENGTH_SHORT).show();
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

        String pn = getIntent().getStringExtra("userphone");
        String uemail = getIntent().getStringExtra("useremail");

        userPhone.setText(pn);
        userEmail.setText(uemail);


        
    }
}