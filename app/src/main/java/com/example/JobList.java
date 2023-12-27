package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class JobList extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.jobs);

        bottomNavigationView.setOnItemSelectedListener(onItemSelect);

    }

    private NavigationBarView.OnItemSelectedListener onItemSelect = new NavigationBarView.OnItemSelectedListener()  {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.chat:

                    startActivity(new Intent(getApplicationContext(), Chat.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.jobs:
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