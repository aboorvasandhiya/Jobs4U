package com.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;



public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.DrawerLayout);
        navigationView = findViewById(R.id.NavigationView);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        
        bottomNavigationView.setOnItemSelectedListener(onItemSelect);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;
                switch (menuItem.getItemId()) {
                    case R.id.quiz:
                        startActivity(new Intent(getApplicationContext(), Quiz.class));
                        break;
                    case R.id.account:
                            startActivity(new Intent(getApplicationContext(), Account.class));
                        break;

                    case R.id.contact:
                        startActivity(new Intent(getApplicationContext(), Feedback.class));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + menuItem.getItemId());
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

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