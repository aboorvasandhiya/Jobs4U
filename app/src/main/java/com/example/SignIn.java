package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity {

    TextView Signup;
    EditText Email;
    EditText Password;
    FirebaseAuth fAuth;
    TextView Signin;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Signup = findViewById(R.id.signuptextbutton);
        Email = findViewById(R.id.edit_username);
        Password = findViewById(R.id.edit_password);
        fAuth = FirebaseAuth.getInstance();
        Signin = findViewById(R.id.logintextbutton);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Email.setError("put in ur email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Password.setError("aso needed lah");
                    return;
                }

                if(password.length()<6){
                    Password.setError("needs to be 6 characters");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignIn.this, "Logging in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        } else {
                            Toast.makeText(SignIn.this, "Error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    };
                });
            }
        });

    }
}

