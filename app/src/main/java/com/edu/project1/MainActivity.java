package com.edu.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.edu.project1.Fragments.AccountFragment;
import com.edu.project1.Fragments.DasboardFragment;
import com.edu.project1.Fragments.ExportFragment;
import com.edu.project1.Fragments.ImportFragment;
import com.edu.project1.Fragments.ReportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.myBottomNavigationView);

        replaceFragment(new DasboardFragment());
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        replaceFragment(new DasboardFragment());
                        return true;
                    case R.id.importproduct:
                        replaceFragment(new ImportFragment());
                        return true;
                    case R.id.exportpproduct:
                        replaceFragment(new ExportFragment());
                        return true;
                    case R.id.reportproduct:
                        replaceFragment(new ReportFragment());
                        return true;
                    case R.id.logout:
                        replaceFragment(new AccountFragment());
                        return true;
                }
                return false;
            }
        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
    public String getUsername(){
        Intent ilogin=getIntent();
        String username=ilogin.getStringExtra("username");
        return username;
    }
    public String getPassword(){
        Intent ilogin=getIntent();
        String pass=ilogin.getStringExtra("pass");
        return pass;
    }
}