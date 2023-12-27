package com.example;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Chat extends AppCompatActivity{
    private BottomNavigationView bottomNavigationView;
    RecyclerviewAdapter Adapter;
    ArrayList<Message> list;
    RecyclerView recyclerView;
    EditText message;
    ImageButton sendbutton;
    DatabaseReference db;
    FirebaseAuth auth;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.chat);
        bottomNavigationView.setOnItemSelectedListener(onItemSelect);
        recyclerView = findViewById(R.id.messageRecyclerView);
        list = new ArrayList<>();

        sendbutton = findViewById(R.id.sendButton);
        message = findViewById(R.id.messagerr);
        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String UID = user.getUid();

        user = auth.getCurrentUser();
        String uId = user.getUid();
        String name = user.getEmail();
        String timeStamp = new SimpleDateFormat("dd-MM-yy HH:mma").format(Calendar.getInstance().getTime());


        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = message.getText().toString();
                db.child("messages").push().setValue(new Message(name, msg, timeStamp)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        message.setText("");


                    }
                });
            }
        });


        Adapter = new RecyclerviewAdapter(this, list);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(Adapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
        reciveMessages();
    }

    private void reciveMessages(){
        db.child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    Message message = snap.getValue((Message.class));
                    list.add(message);
                    Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private NavigationBarView.OnItemSelectedListener onItemSelect = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.chat:
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.jobs:
                    startActivity(new Intent(getApplicationContext(), JobList.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.activity:
                    startActivity(new Intent(getApplicationContext(), ActivityList.class));
                    overridePendingTransition(0, 0);
                    return true;
            }

            return false;
        }
    };



}