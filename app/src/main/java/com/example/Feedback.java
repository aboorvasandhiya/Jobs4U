package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Feedback extends AppCompatActivity {
    private EditText etName, etEmail, etFeedback;
    private Button btnSubmit;
    private ImageButton back;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        etName = findViewById(R.id.contact_name);
        etEmail = findViewById(R.id.contact_email);
        etFeedback = findViewById(R.id.contact_complaint);
        btnSubmit = findViewById(R.id.buttonfeedback);
        dbHelper = new DBHelper(this);
        back = findViewById(R.id.backz);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String feedback = etFeedback.getText().toString();
                if (name.isEmpty() || email.isEmpty() || feedback.isEmpty()) {
                    Toast.makeText(Feedback.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean result = dbHelper.addFeedback(name, email, feedback);
                    if (result) {
                        Toast.makeText(Feedback.this, "Feedback submitted!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Feedback.this, Home.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Feedback.this, "Failed to submit feedback.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}