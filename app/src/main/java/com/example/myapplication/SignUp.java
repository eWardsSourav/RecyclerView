package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    TextView signtxt;
    EditText name, phone, email, password, confirmpass;
    Button submitBtn;
    LinearLayout passdetails;
    TextView uppercase, digitt, splcharr, length, lwrcase;
    TextView txt;
    ImageView img;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpass = findViewById(R.id.confirmpass);
        submitBtn = findViewById(R.id.submitbtn);
        txt = findViewById(R.id.txt);
        img = findViewById(R.id.img);
//        passdetails = findViewById(R.id.passdetails);
//        uppercase = findViewById(R.id.uprcase);
//        digitt = findViewById(R.id.digit);
//        splcharr = findViewById(R.id.splchar);
//        length = findViewById(R.id.length);
//        lwrcase = findViewById(R.id.lwrcase);


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                passdetails.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                passdetails.setVisibility(View.VISIBLE);
                Pattern upperCase = Pattern.compile("(?=.*[A-Z])");
                Pattern lowercase = Pattern.compile("[a-z]");
                Pattern digit = Pattern.compile("(?=.*[0-9])");
                Pattern splchar = Pattern.compile("(?=.*[@#$%^&*!_+?])");

                String pass = password.getText().toString();

                if (upperCase.matcher(pass).find()) {
//                    uppercase.setTextColor(Color.GREEN);

                } else {
//                    uppercase.setTextColor(Color.RED);
                    password.setError("Atleast one uppercase");

                }
                if (lowercase.matcher(pass).find()) {
//                    lwrcase.setTextColor(Color.GREEN);
                } else {
//                    lwrcase.setTextColor(Color.RED);
                    password.setError("enter lowercase");

                }
                if (digit.matcher(pass).find()) {
//                    digitt.setTextColor(Color.GREEN);
                } else {
//                    digitt.setTextColor(Color.RED);
                    password.setError("enter one digit");

                }
                if (splchar.matcher(pass).find()) {
//                    splcharr.setTextColor(Color.GREEN);
                } else {
//                    splcharr.setTextColor(Color.RED);
                    password.setError("Atleast one spl character");

                }
                if (pass.length() < 8) {
                    password.setError("Atleast leanth 8");
//                    length.setTextColor(Color.RED);
                } else {
//                    length.setTextColor(Color.GREEN);
                }

            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                passdetails.setVisibility(View.GONE);
                checkName();
                checkPhone();
                checkEmail();
                confirmpassCheck();
//                checkPassword();

                String emailId = email.getText().toString();
                String pass = password.getText().toString();



                if (checkName() && checkPhone() && checkEmail() && confirmpassCheck()) {
//                    Toast.makeText(SignUp.this, "Successful", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SignUp.this,MainActivity2.class);
//                    startActivity(intent);
                    firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(emailId, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                UserData userData = new UserData(name.getText().toString(),phone.getText().toString(),email.getText().toString(),password.getText().toString());
//                                userData.setName(name.getText().toString());
//                                userData.setPhoneNo(phone.getText().toString());
//                                userData.setEmailAddress(email.getText().toString());
//                                userData.setPassword(password.getText().toString());


                                databaseReference = firebaseDatabase.getReference(name.getText().toString());

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        databaseReference.setValue(userData);
                                        Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(SignUp.this, ""+error.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                                Intent intent = new Intent(SignUp.this,Login_Page.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUp.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }

    private boolean confirmpassCheck() {
        if (password.getText().toString().equals(confirmpass.getText().toString())) {
            return true;

        } else {
            confirmpass.setError("Enter same password");
            return false;
        }
    }

//    private boolean checkPassword() {
//        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
//        Matcher matcher = pattern.matcher(password.getText().toString());
//        if (matcher.matches()){
////            Toast.makeText(this, "valid password", Toast.LENGTH_SHORT).show();
//            return  true;
//        }
//        else {
////            Toast.makeText(this, "invalid password", Toast.LENGTH_SHORT).show();
//            password.setError("Enter Right Password");
//            return false;
//
//        }
//    }

    private boolean checkEmail() {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(email.getText().toString());
        if (matcher.matches()) {
//            Toast.makeText(this, "valid email", Toast.LENGTH_SHORT).show();
            return true;
        } else {
//            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            email.setError("Enter Right Email");
            return false;
        }
    }

    private boolean checkName() {
        Pattern pattern = Pattern.compile("^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$");
        Matcher matcher = pattern.matcher(name.getText().toString());
        if (matcher.matches()) {
//            Toast.makeText(this, "name is ok", Toast.LENGTH_SHORT).show();
            return true;
        } else {
//            Toast.makeText(this, "name is not ok", Toast.LENGTH_SHORT).show();
            name.setError("Enter valid Name");
            return false;

        }

    }

    private boolean checkPhone() {
        if (phone.getText().toString().length() <= 9) {
            phone.setError("enter 10 number");
        }
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phone.getText().toString());
        if (matcher.matches()) {
//                Toast.makeText(this, "right", Toast.LENGTH_SHORT).show();
            return true;
        } else {
//                Toast.makeText(this,"enter right number",Toast.LENGTH_LONG).show();
            phone.setError("Enter valid number");
            return false;

        }


    }

}