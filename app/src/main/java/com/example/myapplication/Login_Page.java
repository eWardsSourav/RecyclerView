package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class Login_Page extends AppCompatActivity {
    TextView lgtxt;
    ImageView imgs;
    EditText email, password;
    Button loginbtn;
    TextView signbtn;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        lgtxt = findViewById(R.id.txts);
        imgs = findViewById(R.id.imgs);
        email = findViewById(R.id.emailadd);
        password = findViewById(R.id.passs);
        loginbtn = findViewById(R.id.lgbtn);
        signbtn = findViewById(R.id.sgbtn);
        lottieAnimationView = findViewById(R.id.ltyanim);

        String emailAdd = email.getText().toString();
        String pass = password.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(Login_Page.this, "filds not empty", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(Login_Page.this, "filds not empty", Toast.LENGTH_SHORT).show();
                } else {
//                    progressDialog = ProgressDialog.show(Login_Page.this,"","please wait");
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(loginbtn.getWindowToken(), 0);
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                progressDialog.dismiss();
                                lottieAnimationView.setVisibility(View.GONE);
                                Toast.makeText(Login_Page.this, "login successfully", Toast.LENGTH_SHORT).show();
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                updateUI(firebaseUser);
                                getToken();

                            } else {
//                                progressDialog.dismiss();
                                lottieAnimationView.setVisibility(View.GONE);
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
                Intent intent = new Intent(Login_Page.this, SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_rigth,R.anim.slide_to_left);
            }
        });
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()){
                    Log.e("Task is not sucess",task.getException().toString());
                }
                String token = task.getResult();
                Log.e("tokenn",token);

            }
        });

    }

    private void updateUI(FirebaseUser firebaseUser) {
        Intent intent = new Intent(Login_Page.this, MainActivity2.class);
        startActivity(intent);
        finish();
    }

}