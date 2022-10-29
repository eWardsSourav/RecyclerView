package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Page extends AppCompatActivity {
    TextView lgtxt;
    EditText email,password;
    Button loginbtn;
    TextView signbtn;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        lgtxt = findViewById(R.id.logintxt);
        email = findViewById(R.id.emailadd);
        password = findViewById(R.id.passs);
        loginbtn = findViewById(R.id.lgbtn);
        signbtn = findViewById(R.id.sgbtn);

        String emailAdd = email.getText().toString();
        String pass = password.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(Login_Page.this, "filds not empty", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(Login_Page.this, "filds not empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login_Page.this, "login successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login_Page.this,ProfileActivity.class);
                                intent.putExtra("useremail",email.getText().toString());
                                intent.putExtra("userphone","7586912586");
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(Login_Page.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    
                }
               
            }
        });







        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Page.this,SignUp.class);
                startActivity(intent);

            }
        });
    }
}